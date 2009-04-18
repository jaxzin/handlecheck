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
