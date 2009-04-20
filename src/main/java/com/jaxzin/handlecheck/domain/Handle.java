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

package com.jaxzin.handlecheck.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.jdo.annotations.*;
import java.util.*;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Handle {

    @PrimaryKey
    @Persistent
    private Key handle;

    @Persistent
    private Date lastCheckedOn;

    @Persistent
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="providerName asc"))
    private List<HandleProviderAvailability> availability = new LinkedList<HandleProviderAvailability>();

    @Persistent
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="receivedOn desc"))
    private List<HandleProviderResponse> history = new ArrayList<HandleProviderResponse>();


    public static Key createKey(String handle) {
        return KeyFactory.createKey(Handle.class.getSimpleName(), handle);
    }

    public static Key createKey(Handle handle) {
        return KeyFactory.createKey(Handle.class.getSimpleName(), handle.getHandle());
    }
    
    public Handle(String handle, Date lastCheckedOn) {
        this.handle = Handle.createKey(handle);
        this.lastCheckedOn = lastCheckedOn;
    }

    public String getHandle() {
        return handle.getName();
    }

    public Date getLastCheckedOn() {
        return lastCheckedOn;
    }

    public void setLastCheckedOn(Date lastCheckedOn) {
        this.lastCheckedOn = lastCheckedOn;
    }

    public List<HandleProviderAvailability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<HandleProviderAvailability> availability) {
        this.availability = availability;
    }

    public List<HandleProviderResponse> getHistory() {
        return history;
//        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Handle handle1 = (Handle) o;

        if (availability != null ? !availability.equals(handle1.availability) : handle1.availability != null)
            return false;
        if (handle != null ? !handle.equals(handle1.handle) : handle1.handle != null) return false;
        if (history != null ? !history.equals(handle1.history) : handle1.history != null) return false;
        if (lastCheckedOn != null ? !lastCheckedOn.equals(handle1.lastCheckedOn) : handle1.lastCheckedOn != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = handle != null ? handle.hashCode() : 0;
        result = 31 * result + (lastCheckedOn != null ? lastCheckedOn.hashCode() : 0);
        result = 31 * result + (availability != null ? availability.hashCode() : 0);
        result = 31 * result + (history != null ? history.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Handle{" +
                "handle='" + handle + '\'' +
                ", lastCheckedOn=" + lastCheckedOn +
                ", availability=" + availability +
                ", history=" + history +
                '}';
    }
}
