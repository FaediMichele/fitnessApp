package com.example.crinaed.layout.learning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.crinaed.R;
import com.example.crinaed.database.ServerManager;
import com.example.crinaed.util.Lambda;

public class LearningNotBoughtFragment extends Fragment {

    private long idCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_not_bought, container, false);

        Bundle dataLearning = getArguments();
        idCourse = dataLearning.getLong(LearningBuySearchFragment.KEY_ID_COURSE);

        Button buttonBuy = view.findViewById(R.id.button_buy);
        buttonBuy.setText(getText(R.string.buy_for_view_video));
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerManager.getInstance(getContext()).buyCourse(idCourse, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Toast.makeText(getContext(), R.string.course_bought, Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                        return new Object[0];
                    }
                }, new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Toast.makeText(getContext(), R.string.unknown_error, Toast.LENGTH_LONG).show();
                        return new Object[0];
                    }
                });
            }
        });

        return view;
    }
}
