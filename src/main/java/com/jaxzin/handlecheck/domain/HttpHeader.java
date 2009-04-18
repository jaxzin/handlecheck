package com.jaxzin.handlecheck.domain;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.*;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class HttpHeader {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

    @Persistent
    private String key;

    @Persistent
    private String value;

    public HttpHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Key getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpHeader that = (HttpHeader) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
