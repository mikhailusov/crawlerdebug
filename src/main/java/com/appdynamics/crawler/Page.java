/*
 * Copyright (c) AppDynamics, Inc., and its affiliates
 * 2019
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF APPDYNAMICS, INC.
 * The copyright notice above does not evidence any actual or intended publication of such source code
 */
package com.appdynamics.crawler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Page {

    private String title;
    private List<URI> links = new ArrayList<>();
    private boolean hasGoogleAnalytics;
    private String body;

    public String getTitle() {
        return title;
    }

    public List<URI> getLinks() {
        return links;
    }

    public boolean isHasGoogleAnalytics() {
        return hasGoogleAnalytics;
    }

    public String getBody() {
        return body;
    }

    public void setHasGoogleAnalytics(boolean hasGoogleAnalytics) {
        this.hasGoogleAnalytics = hasGoogleAnalytics;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
