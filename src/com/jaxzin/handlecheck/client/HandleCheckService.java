package com.jaxzin.handlecheck.client;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@RemoteServiceRelativePath("check")
public interface HandleCheckService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use HandleCheckService.App.getInstance() to access static instance of HandleCheckServiceAsync
     */
    public static class App {
        private static final HandleCheckServiceAsync ourInstance;

        static {
            ourInstance = (HandleCheckServiceAsync) GWT.create(HandleCheckService.class);
            ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "com.jaxzin.handlecheck.HandleCheck/HandleCheckService");
        }

        public static HandleCheckServiceAsync getInstance() {
            return ourInstance;
        }
    }

    @SuppressWarnings({"NonSerializableServiceParameters"})
    HandleCheckResult checkHandle(String handle, HandleProvider provider) throws HandleCheckException;
}
