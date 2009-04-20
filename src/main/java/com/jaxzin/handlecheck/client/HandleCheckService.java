/*
 * Copyright (c) 2009. Brian R. Jackson.  All Rights Reserved. http://www.jaxzin.com
 *     This file is part of Handlecheck.
 *
 *     Handlecheck is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Handlecheck is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Handlecheck.  If not, see <http://www.gnu.org/licenses/>.
 */

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
