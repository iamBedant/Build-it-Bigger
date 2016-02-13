package com.iambedant.nanodegree.jokedisplay;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayJokeFragment extends Fragment {
    public static String JOKE_KEY = "Joke key";
    public DisplayJokeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_joke, container, false);
        TextView jokeView = (TextView) view.findViewById(R.id.the_joke);
        jokeView.setText(getActivity().getIntent().getStringExtra(JOKE_KEY));
        return view;
    }
}
