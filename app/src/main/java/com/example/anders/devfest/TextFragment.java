package com.example.anders.devfest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by q on 2017-01-20.
 */

public class TextFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.text, container, false );
        return view;
    }
}