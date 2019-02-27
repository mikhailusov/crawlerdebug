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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Page {

    private String title;
    private List<URI> links = new ArrayList<>();
    private boolean hasGoogleAnalytics;
    private String body;

    public Page(String body) {
        this.body = body;
        setTitle();
        setLinks();
        setGoogleAnalytics();
    }

    private void setTitle() {
        Pattern pattern = Pattern.compile("<title>(.+?)</title>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);
        matcher.find();
        title = matcher.group(1);
    }

    private void setLinks() {
        Pattern pattern = Pattern.compile("<a\\s+href=\"([^\"]+)\"[^>]*>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            if (!matcher.group(1).startsWith("javascript")) {
                this.links.add(URI.create(matcher.group(1)));
            }
        }
    }

    private void setGoogleAnalytics() {
        this.hasGoogleAnalytics = body.contains(".google-analytics.com/ga.js");
    }

    public String getTitle() {
        return title;
    }

    public List<URI> getLinks() {
        return links;
    }

    public boolean isHasGoogleAnalytics() {
        return hasGoogleAnalytics;
    }
}
