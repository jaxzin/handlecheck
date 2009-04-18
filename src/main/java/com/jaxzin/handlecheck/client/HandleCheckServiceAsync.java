package com.jaxzin.handlecheck.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface HandleCheckServiceAsync {

    void checkHandle(String handle, HandleProvider provider, AsyncCallback async);
}
