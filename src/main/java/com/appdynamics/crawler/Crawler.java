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

public class Crawler {

    private HttpClient client;

    public Crawler(HttpClient client) {
        this.client = client;
    }

    public List<Page> run(List<URI> uris) {
        List<Page> pages = new ArrayList<>();
        for (URI uri: uris) {
            HttpResponse responseBody;
            try {
                responseBody = client.execute(new HttpGet(uri));
                pages.add(new Page(EntityUtils.toString(responseBody.getEntity())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pages;
    }

    public List<Page> run(URI uri) {
        return run(Arrays.asList(uri));
    }
}
