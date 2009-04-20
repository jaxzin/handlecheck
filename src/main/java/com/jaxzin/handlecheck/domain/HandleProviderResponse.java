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
import com.google.appengine.api.datastore.Link;
import com.jaxzin.handlecheck.client.HandleProvider;

import javax.jdo.annotations.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class HandleProviderResponse {


    private static final int ONE_MEGABYTE = 1024 * 1024;

    // Since characters in Unicode can be up to 4 bytes in length,
    // and the maximum entity size is 1 MiB,
    // fragments can be up to 256K chars in length
    private static final int FRAGMENT_SIZE = 1024 * 256;



    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Date receivedOn;

    @Persistent
    @Embedded
    private Requestor requestor;

    @Persistent
    private boolean available;

    @Persistent
    private String providerName;

    @Persistent
    private Link providerUrl;

    @Persistent
    private int httpResponseCode;

    @Persistent
    private String httpResponseMessage;

//    @NotPersistent
//    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="key asc, value asc"))
//    private List<HttpHeader> httpHeaders = new ArrayList<HttpHeader>();

//    @NotPersistent
//    @Order(extensions = @Extension(vendorName = "datanucleus", key = "list-ordering", value = "order asc"))
//    private List<ResponseFragment> httpResponse = new LinkedList<ResponseFragment>();

    public HandleProviderResponse(Date receivedOn, Requestor requestor, boolean available, HandleProvider provider, String providerUrl, int httpResponseCode, String httpResponseMessage, List<HttpHeader> httpHeaders, String content) {
        this.receivedOn = receivedOn;
        this.requestor = requestor;
        this.available = available;
        this.providerName = provider.name();
        this.providerUrl = new Link(providerUrl);
        this.httpResponseCode = httpResponseCode;
        this.httpResponseMessage = httpResponseMessage;
//        this.httpHeaders = httpHeaders;

//        this.httpResponse = splitContent(content);
    }

    private List<ResponseFragment> splitContent(String content) {
        List<ResponseFragment> fragments = new ArrayList<ResponseFragment>();
        if(content != null) {
            if(content.getBytes().length < ONE_MEGABYTE) {
                fragments.add(new ResponseFragment(0, content));
            } else {
                StringBuilder builder = new StringBuilder(content);
                int i = 0;
                while(builder.length() > 0) {
                    int fragmentLength = Math.min(FRAGMENT_SIZE, builder.length());
                    fragments.add(new ResponseFragment(i++, builder.substring(0, fragmentLength)));
                    builder.delete(0, fragmentLength);
                }
            }
        }
        return fragments;
    }

    public Key getKey() {
        return key;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Date receivedOn) {
        this.receivedOn = receivedOn;
    }

    public Requestor getRequestor() {
        return requestor;
    }

    public void setRequestor(Requestor requestor) {
        this.requestor = requestor;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public HandleProvider getHandleProvider() {
        return HandleProvider.valueOf(providerName);
    }

    public String getProviderUrl() {
        return providerUrl != null ? providerUrl.getValue() : null;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = new Link(providerUrl);
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public String getHttpResponseMessage() {
        return httpResponseMessage;
    }

    public void setHttpResponseMessage(String httpResponseMessage) {
        this.httpResponseMessage = httpResponseMessage;
    }

    public void addHttpHeader(String key, String value) {
//        httpHeaders.add(new HttpHeader(key, value));
    }

    public String getHttpContent() {
//        StringBuilder builder = new StringBuilder();
//        for (ResponseFragment responseFragment : httpResponse) {
//            builder.append(responseFragment.getFragment());
//        }
//        return builder.toString();
        return "";
    }

}
