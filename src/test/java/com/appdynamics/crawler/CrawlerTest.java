package com.appdynamics.crawler;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CrawlerTest {

    private Crawler target;
    private List<URI> URIs;

    @Before
    public void setUp() {
        target = new Crawler();
        target.setClient(HttpClientBuilder.create().build());
        URIs = loadURIs();
    }

    @Test
    public void run() {
        List<Page> pages = target.run(URIs);
        assertEquals(5, pages.size());

    }

    @Test
    public void runSetsPageFieldsCorrectly() {
        Page page = target.run(URI.create("http://google.com")).get(0);
        assertTrue(page.getTitle().equals("Google"));
        assertEquals(9, page.getLinks().size());
        assertTrue(page.isHasGoogleAnalytics());
    }

    private List<URI> loadURIs() {
        List<URI> uris = new ArrayList<>();

        try {
            uris = Files.lines(Paths.get("urls.txt"))
                    .map(URI::create)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uris;
    }
}