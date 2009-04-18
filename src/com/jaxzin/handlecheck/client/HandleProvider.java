package com.jaxzin.handlecheck.client;

/**
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public enum HandleProvider {
    TWITTER("twitter", "http://twitter.com/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode != 200;
        }
    },
    ESPN("ESPN", "http://sportsnation.espn.go.com/fans/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return body.contains("Error: File Not Found");
        }
    },
    SLASHDOT("Slashdot", "http://slashdot.org/~${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return body.contains("The user you requested does not exist");
        }
    },
    LINKEDIN("LinkedIn", "http://www.linkedin.com/in/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return body.contains("Profile Not Found");
        }
    },
    YOUTUBE("YouTube", "http://www.youtube.com/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode == 404;
        }
    },
    FLICKR("flickr", "http://www.flickr.com/people/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode == 404;
        }
    },
    MOBILEME("MobileMe", "http://public.me.com/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return body.contains("Account Error");
        }
    },
    DELICIOUS("del.icio.us", "http://delicious.com/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode == 404;
        }
    },
    REDDIT("reddit", "http://reddit.com/user/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode == 404;
        }
    },
    FARK("fark", "http://www.fark.com/cgi/users.pl?login=${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return body.contains("No such user");
        }
    },
    FRIENDFEED("friendfeed", "http://friendfeed.com/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode == 404;
        }
    },
    TUMBLR("tumblr", "http://${handle}.tumblr.com/") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return httpResponseCode == 404 || body.contains("We'll be back shortly!");
        }
    },
    DIGG("digg", "http://digg.com/users/${handle}") {
        public boolean determineAvailability(String handle, int httpResponseCode, String body) {
            return body.contains("Oops, what you're looking for isn't here!");
        }
    }
    ;


    private String displayName;
    private String providerUri;
    private int displayOrder;
    HandleProvider(String displayName, String providerUri) {
        this.displayName = displayName;
        this.providerUri = providerUri;
        this.displayOrder = this.ordinal();
    }
    
    HandleProvider(String displayName, String providerUri, int displayOrder) {
        this.displayName = displayName;
        this.providerUri = providerUri;
        this.displayOrder = displayOrder;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProviderUri() {
        return providerUri;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public String getProviderUri(String handle) {
        // todo: encode the handle properly here in a JS/Java neutral way
        return providerUri.replace("${handle}", handle);
    }

    abstract public boolean determineAvailability(String handle, int httpResponseCode, String body);
}
