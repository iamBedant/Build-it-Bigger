package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Kuliza-193 on 2/13/2016.
 */
public class JokeDownloader {
    private static MyApi myApiService = null;
    private DownloadCompleteListener downloadListener;

    public JokeDownloader(DownloadCompleteListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void downloadJoke() {
        new EndpointsAsyncTask().execute();
    }

    class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://[YOUR_PROJECT_ID].appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            try {
                return myApiService.giveMeJoke().execute().getData();

            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            downloadListener.downloadCompleted(result);
        }
    }
}
