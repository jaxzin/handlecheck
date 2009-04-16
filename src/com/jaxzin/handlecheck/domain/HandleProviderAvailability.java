package com.jaxzin.handlecheck.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jaxzin.handlecheck.client.HandleProvider;

import javax.jdo.annotations.*;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class HandleProviderAvailability {

    @PrimaryKey
    @Persistent
    private Key key;

    @Persistent
    @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
    private Key handleKey;

    @Persistent
    private String providerName;

    @Persistent
    private boolean available;

    public HandleProviderAvailability(Key key, HandleProvider provider, boolean available) {
        this.key = key;
        this.providerName = provider.name();
        setAvailable(available);
    }

    public Key getKey() {
        return key;
    }

    public Key getHandleKey() {
        return handleKey;
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

    public static Key createKey(Handle handle, HandleProvider provider) {
        KeyFactory.Builder builder = new KeyFactory.Builder(Handle.class.getSimpleName(), handle.getHandle());
        builder.addChild(HandleProviderAvailability.class.getSimpleName(), provider.name());
        return builder.getKey();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandleProviderAvailability that = (HandleProviderAvailability) o;

        if (available != that.available) return false;
        if (handleKey != null ? !handleKey.equals(that.handleKey) : that.handleKey != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (providerName != null ? !providerName.equals(that.providerName) : that.providerName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (handleKey != null ? handleKey.hashCode() : 0);
        result = 31 * result + (providerName != null ? providerName.hashCode() : 0);
        result = 31 * result + (available ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HandleProviderAvailability{" +
                "key=" + key +
                ", handleKey=" + handleKey +
                ", providerName='" + providerName + '\'' +
                ", available=" + available +
                '}';
    }
}
