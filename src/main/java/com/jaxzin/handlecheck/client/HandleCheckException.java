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

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class HandleCheckException extends Exception implements IsSerializable {

    private String handle;
    @SuppressWarnings({"GwtInconsistentSerializableClass"})
    private HandleProvider provider;

    public HandleCheckException(String handle, HandleProvider provider) {
        this.handle = handle;
        this.provider = provider;
    }

    public HandleCheckException(String message, String handle, HandleProvider provider) {
        super(message);
        this.handle = handle;
        this.provider = provider;
    }

    public HandleCheckException(String message, Throwable cause, String handle, HandleProvider provider) {
        super(message, cause);
        this.handle = handle;
        this.provider = provider;
    }

    public HandleCheckException(Throwable cause, String handle, HandleProvider provider) {
        super(cause);
        this.handle = handle;
        this.provider = provider;
    }

    public HandleCheckException() {
    }

    public String getHandle() {
        return handle;
    }

    public HandleProvider getProvider() {
        return provider;
    }

    @Override
    public String toString() {
        return "HandleCheckException{" +
                "handle='" + handle + '\'' +
                ", provider=" + provider +
                "} " + super.toString();
    }
}
