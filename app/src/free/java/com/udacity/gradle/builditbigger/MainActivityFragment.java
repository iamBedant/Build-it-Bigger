package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.iambedant.nanodegree.jokedisplay.DisplayJoke;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements DownloadCompleteListener {

    ProgressBar mProgressBar;
    Button mButtonTellJoke;
    public static String JOKE_KEY = "Joke key";
    private PublisherInterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mButtonTellJoke = (Button) root.findViewById(R.id.tell_joke);
        mProgressBar = (ProgressBar) root.findViewById(R.id.spinner);

        mInterstitialAd = new PublisherInterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                new JokeDownloader(MainActivityFragment.this).downloadJoke();
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        requestNewInterstitial();


        mButtonTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    new JokeDownloader(MainActivityFragment.this).downloadJoke();
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
         AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        return root;
    }
    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    public void downloadCompleted(String joke) {
        mProgressBar.setVisibility(View.GONE);
        Intent mIntent = new Intent(getActivity(), DisplayJoke.class);
        mIntent.putExtra(JOKE_KEY, joke);
        startActivity(mIntent);
    }
}
