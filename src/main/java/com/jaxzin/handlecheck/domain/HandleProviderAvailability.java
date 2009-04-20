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
import com.jaxzin.handlecheck.client.HandleProvider;

import javax.jdo.annotations.*;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class HandleProviderAvailability {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent
    private String providerName;

    @Persistent
    private boolean available;

    public HandleProviderAvailability(HandleProvider provider, boolean available) {
        this.providerName = provider.name();
        setAvailable(available);
    }

    public Key getKey() {
        return id;
    }

    public HandleProvider getProvider() {
        return HandleProvider.valueOf(providerName);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandleProviderAvailability that = (HandleProviderAvailability) o;

        if (providerName != null ? !providerName.equals(that.providerName) : that.providerName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (providerName != null ? providerName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HandleProviderAvailability{" +
                "key=" + id +
                ", providerName='" + providerName + '\'' +
                ", available=" + available +
                '}';
    }
}
