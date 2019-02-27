/*
 * Copyright (c) AppDynamics, Inc., and its affiliates
 * 2019
 * All Rights Reserved
 * THIS IS UNPUBLISHED PROPRIETARY CODE OF APPDYNAMICS, INC.
 * The copyright notice above does not evidence any actual or intended publication of such source code
 */
package com.appdynamics.crawler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {

    private HttpClient client;

    public void setClient(HttpClient client) {
        this.client = client;
    }

    public List<Page> run(List<URI> uris) {
        List<Page> pages = new ArrayList<>();
        for (URI uri: uris) {
            HttpResponse responseBody;
            try {
                responseBody = client.execute(new HttpGet(uri));
                pages.add(createPage(responseBody));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pages;
    }

    public List<Page> run(URI uri) {
        return run(Arrays.asList(uri));
    }

    private Page createPage(HttpResponse response) throws IOException {
        Page page = new Page();
        page.setBody(EntityUtils.toString(response.getEntity()));
        setPageTitle(page);
        setPageLinks(page);
        setPageGoogleAnalytics(page);
        return page;
    }

    private void setPageTitle(Page page) {
        Pattern pattern = Pattern.compile("<title>(.+?)</title>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(page.getBody());
        matcher.find();
        page.setTitle(matcher.group(1));
    }

    private void setPageLinks(Page page) {
        Pattern pattern = Pattern.compile("<a\\s+href=\"([^\"]+)\"[^>]*>(.+?)</a>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(page.getBody());
        while (matcher.find()) {
                page.getLinks().add(URI.create(matcher.group(1)));
        }
    }

    private void setPageGoogleAnalytics(Page page) {
        page.setHasGoogleAnalytics(page.getBody().contains(".google-analytics.com/ga.js"));
    }

}
