package com.example.crinaed.ProgressBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.crinaed.R;
import com.example.crinaed.view.ProgressBarView;

public class ProgressBarPageFragment extends Fragment {

    /**
     *
     * color bg
     * color fg
     * percent complete
     *
     */


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_progress_bar,container, false);
        //ProgressBarView progressBar = view.findViewById(R.id.progressBarView);
//        int color = ContextCompat.getColor(getContext(),R.color.redPrimary);
//        progressBar.setBackgroundColor(color);

        return view;
    }
}
