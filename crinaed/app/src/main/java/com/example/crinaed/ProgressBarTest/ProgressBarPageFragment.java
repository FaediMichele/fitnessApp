package com.example.crinaed.ProgressBarTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private   ProgressBarView progressBar;

    private int progress = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_progress_bar,container, false);
        ProgressBarView progressBar = view.findViewById(R.id.progressBarView);

//        progressBar.setForegroundColor(this.foregroundColor);
//        progressBar.setBackgroundColor(this.backgroundColor);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=(progress+10)%100;
                ((ProgressBarView) view.findViewById(R.id.progressBarView)).setProgress(progress);
            }
        });
        return view;
    }

    public ProgressBarPageFragment setForegroundColor(int color){
        progressBar.setForegroundColor(ContextCompat.getColor(getContext(),color));
        return this;
    }
}
