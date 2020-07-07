package com.example.crinaed.layout.learning;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.example.crinaed.R;
import com.example.crinaed.view.FullScreenMediaController;

public class LessonFragment extends Fragment {

    private String nameFile = "video_lesson_simple.mp4";
    private VideoView mVideoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        mVideoView = view.findViewById(R.id.video_lesson);

        String path = "android.resource://"+ getActivity().getPackageName() + "/" + R.raw.video_simple ;  // if your video is not .mp4 change it your video extension
        Uri uri = Uri.parse(path);
        mVideoView.setVideoURI(uri);
//        mVideoView.start();

        FullScreenMediaController controller = new FullScreenMediaController(getContext());
        mVideoView.setMediaController(controller);
        controller.setAnchorView(mVideoView);
        controller.setMediaPlayer(mVideoView);

        return view;
    }
}
