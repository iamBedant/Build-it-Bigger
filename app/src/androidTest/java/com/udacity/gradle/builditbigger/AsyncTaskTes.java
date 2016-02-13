package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kuliza-193 on 2/13/2016.
 */
public class AsyncTaskTes extends AndroidTestCase implements DownloadCompleteListener {

    CountDownLatch signal;
    String joke = "";
    JokeDownloader downloader;

    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
        downloader = new JokeDownloader(this);
    }

    @UiThreadTest
    public void testAsync() throws InterruptedException {
        downloader.downloadJoke();
        signal.await(30, TimeUnit.SECONDS);
        assertTrue("Valid joke is returned", joke != null);
    }

    @Override
    public void downloadCompleted(String joke) {
        this.joke = joke;
        signal.countDown();
    }
}
