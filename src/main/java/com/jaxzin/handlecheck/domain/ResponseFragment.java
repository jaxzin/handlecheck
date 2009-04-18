package com.jaxzin.handlecheck.domain;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import javax.jdo.annotations.*;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ResponseFragment implements Comparable<ResponseFragment> {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private int order;

    @Persistent
    private Text fragment;

    public ResponseFragment(int order, String fragment) {
        this.order = order;
        this.fragment = new Text(fragment);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getFragment() {
        return fragment.getValue();
    }

    public void setFragment(String fragment) {
        this.fragment = new Text(fragment);
    }

    public int compareTo(ResponseFragment o) {
        return this.order - o.order;
    }
}
