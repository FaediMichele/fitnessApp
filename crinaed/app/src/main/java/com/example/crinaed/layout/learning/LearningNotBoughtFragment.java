package com.example.crinaed.layout.learning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.crinaed.R;

public class LearningNotBoughtFragment extends Fragment {

    private String idCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_not_bought, container, false);

        Bundle dataLearning = getArguments();
        idCourse = dataLearning.getString(LearningBuySearchFragment.KEY_ID_COURSE);

        Button buttonBuy = view.findViewById(R.id.button_buy);
        buttonBuy.setText(getText(R.string.buy_for_view_video));
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //comprare ------------------------------------
            }
        });

        return view;
    }
}
