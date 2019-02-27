package com.appdynamics.crawler;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        Page page = target.run(URI.create("https://www.w3.org/Provider/Style/URI")).get(0);
        assertTrue(page.getTitle().equals("Hypertext Style: Cool URIs don't change."));
        assertEquals(6, page.getLinks().size());
        assertFalse(page.isHasGoogleAnalytics());
    }

    private List<URI> loadURIs() {
        List<URI> uris = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("urls.txt"));
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                uris.add(URI.create(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uris;
    }
}