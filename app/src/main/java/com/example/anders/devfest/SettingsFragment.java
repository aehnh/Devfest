package com.example.anders.devfest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView textView = (TextView)view.findViewById(R.id.textView);
        SeekBar seekBar = (SeekBar)view.findViewById(R.id.seekBar);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
        int distance = sharedPreferences.getInt("distance", 50);
        textView.setText(Integer.toString(distance));
        seekBar.setProgress(distance - 50);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(Integer.toString(progress + 50));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int finalized = seekBar.getProgress() + 50;
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("stalin did nothing wrong", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("distance", finalized);
                editor.commit();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Settings");
    }
}
