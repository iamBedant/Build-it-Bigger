package com.udacity.gradle.builditbigger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.iambedant.nanodegree.jokedisplay.DisplayJoke;
import com.udacity.gradle.builditbigger.DownloadCompleteListener;
import com.udacity.gradle.builditbigger.JokeDownloader;
import com.udacity.gradle.builditbigger.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements DownloadCompleteListener {

    ProgressBar mProgressBar;
    Button mButtonTellJoke;
    public static String JOKE_KEY = "Joke key";
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_activity, container, false);
        mButtonTellJoke = (Button) root.findViewById(R.id.tell_joke);
        mProgressBar = (ProgressBar) root.findViewById(R.id.spinner);

        mButtonTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JokeDownloader(MainActivityFragment.this).downloadJoke();
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    @Override
    public void downloadCompleted(String joke) {
        mProgressBar.setVisibility(View.GONE);
        Intent mIntent = new Intent(getActivity(),DisplayJoke.class);
        mIntent.putExtra(JOKE_KEY, joke);
        startActivity(mIntent);
    }
}