package com.jaxzin.handlecheck.server;

import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jaxzin.handlecheck.client.HandleCheckException;
import com.jaxzin.handlecheck.client.HandleCheckResult;
import com.jaxzin.handlecheck.client.HandleCheckService;
import com.jaxzin.handlecheck.client.HandleProvider;
import com.jaxzin.handlecheck.domain.*;
import org.datanucleus.exceptions.NucleusDataStoreException;

import javax.jdo.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class HandleCheckServiceImpl extends RemoteServiceServlet implements HandleCheckService {

    private static final Logger LOG = Logger.getLogger(HandleCheckServiceImpl.class.getName());
    private static final int RETRIES = 3;

    public HandleCheckResult checkHandle(String handle, HandleProvider provider) throws HandleCheckException {
        LOG.info("Checking handle '" + handle + "' for provider '" + provider + "'");
        boolean available;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(provider.getProviderUri(URLEncoder.encode(handle,"UTF-8")));

            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.connect();

            final int code = conn.getResponseCode();

            final String contentBody = readContentBody(conn);

            available = provider.determineAvailability(handle, code, contentBody);
            LOG.info("Received HTTP response code " + code + " from " + url);


            // Now get a few more bits and persist all the information about this request
            final String message = conn.getResponseMessage();

            persistRequestInfo(handle, provider, available, url, code, message);


        } catch (MalformedURLException e) {
            LOG.log(Level.SEVERE, "Error for handle '" + handle + "' for provider '" + provider + "'", e);
            throw new HandleCheckException(e, handle, provider);
        } catch (FileNotFoundException e) {
            // 404 error
            LOG.info(e.getMessage());
            available = true;
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error for handle '" + handle + "' for provider '" + provider + "'", e);
            throw new HandleCheckException(e, handle, provider);
        } catch (Throwable t) {
            LOG.log(Level.SEVERE, "Error for handle '" + handle + "' for provider '" + provider + "'", t);
            throw new HandleCheckException(t, handle, provider);
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }

        return new HandleCheckResult(handle, provider, available);
    }





    private String readContentBody(HttpURLConnection conn) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return builder.toString();
    }




    private void persistRequestInfo(String handle, HandleProvider provider, boolean available, URL url, int code, String message) {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            for (int attempt = 1; attempt <= RETRIES; attempt++) {
                Transaction tx = pm.currentTransaction();
                try {
                    tx.begin();

                    Handle h;
                    try {
                        // First see if it already exists
                        h = pm.getObjectById(Handle.class, Handle.createKey(handle));
                        LOG.fine("Found handle for '" + handle + "'");
                    } catch (JDOObjectNotFoundException e) {
//                        h = new Handle(handle, pm.getServerDate());
                        h = new Handle(handle, new Date());
                        pm.makePersistent(h);
                        LOG.info("Creating handle for '" + handle + "'");
                    }


                    HandleProviderAvailability availability = new HandleProviderAvailability(provider, available);

                    // Add or update the availability
                    if(!h.getAvailability().add(availability)) {
                        h.getAvailability().remove(availability);
                        h.getAvailability().add(availability);
                    }

                    // Add history
                    final HandleProviderResponse providerResponse = new HandleProviderResponse(
//                                pm.getServerDate(),
                            new Date(),
                            new Requestor(
                                    UserServiceFactory.getUserService().getCurrentUser(),
                                    getThreadLocalRequest().getRemoteHost()
                            ),
                            available,
                            provider,
                            url.toString(),
                            code,
                            message,
                            null,
                            ""
                    );

                    h.getHistory().add(providerResponse);



                    tx.commit();

                    break;

                } catch (JDOCanRetryException e) {
                    if (attempt == RETRIES) {
                        LOG.log(Level.SEVERE, "Attempt "+attempt+" failed, giving up.", e);
                        throw e;
                    } else {
                        LOG.log(Level.WARNING, "Attempt "+attempt+" failed, retrying.", e);
                    }
                } catch (JDOException e) {
                    // NucleusDataStoreExceptions are retryable
                    // (The developers should have wrapped in JDOCanRetryException, oh well...)
                    if(e.getCause() instanceof NucleusDataStoreException) {
                            if (attempt == RETRIES) {
                                LOG.log(Level.SEVERE, "Attempt "+attempt+" failed, giving up.", e);
                                throw e;
                            } else {
                                LOG.log(Level.WARNING, "Attempt "+attempt+" failed, retrying.", e);
                            }
                    } else {
                        throw e;
                    }
                } finally {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                }
            }
        } finally {
            pm.close();
        }
    }



}
