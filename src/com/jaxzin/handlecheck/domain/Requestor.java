package com.jaxzin.handlecheck.domain;

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
    private boolean anonymous;

    @Persistent
    private String email;

    @Persistent
    private String userName;

    @Persistent
    private String remoteHost;

    public Requestor(boolean anonymous, String email, String userName, String remoteHost) {
        this.anonymous = anonymous;
        this.email = email;
        this.userName = userName;
        this.remoteHost = remoteHost;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

        if (anonymous != requestor.anonymous) return false;
        if (email != null ? !email.equals(requestor.email) : requestor.email != null) return false;
        if (remoteHost != null ? !remoteHost.equals(requestor.remoteHost) : requestor.remoteHost != null) return false;
        if (userName != null ? !userName.equals(requestor.userName) : requestor.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (anonymous ? 1 : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (remoteHost != null ? remoteHost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Requestor{" +
                "anonymous=" + anonymous +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", remoteHost='" + remoteHost + '\'' +
                '}';
    }
}
