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
public class HandleCheckResult implements IsSerializable {

    private String handle;
    @SuppressWarnings({"GwtInconsistentSerializableClass"})
    private HandleProvider provider;

    private boolean available;

    public HandleCheckResult(String handle, HandleProvider provider, boolean available) {
        this.handle = handle;
        this.provider = provider;
        this.available = available;
    }

    public HandleCheckResult() {
    }

    public String getHandle() {
        return handle;
    }

    public HandleProvider getProvider() {
        return provider;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandleCheckResult that = (HandleCheckResult) o;

        if (available != that.available) return false;
        if (handle != null ? !handle.equals(that.handle) : that.handle != null) return false;
        if (provider != that.provider) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = handle != null ? handle.hashCode() : 0;
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HandleCheckResult{" +
                "handle='" + handle + '\'' +
                ", provider=" + provider +
                ", available=" + available +
                '}';
    }
}
