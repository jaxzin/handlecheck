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

import com.google.appengine.api.users.User;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable
@EmbeddedOnly
public class Requestor {

    @Persistent
    private User user;

    @Persistent
    private String remoteHost;

    public Requestor(User user, String remoteHost) {
        this.user = user;
        this.remoteHost = remoteHost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requestor requestor = (Requestor) o;

        if (remoteHost != null ? !remoteHost.equals(requestor.remoteHost) : requestor.remoteHost != null) return false;
        if (user != null ? !user.equals(requestor.user) : requestor.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (remoteHost != null ? remoteHost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Requestor{" +
                "user=" + user +
                ", remoteHost='" + remoteHost + '\'' +
                '}';
    }
}
