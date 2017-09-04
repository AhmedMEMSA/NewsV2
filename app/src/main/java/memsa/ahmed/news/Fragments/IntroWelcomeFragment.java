package memsa.ahmed.news.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import memsa.ahmed.news.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroWelcomeFragment extends Fragment {


    public IntroWelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_welcome, container, false);
    }

}
