package com.example.crinaed.layout.objective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crinaed.R;

public class ObjectivesManagementFragment extends Fragment {

    public static final String TAG_CREATE = "OBJECTIVE_MANAGEMENT_FRAGMENT_TO_OBJECTIVE_CREATE_FRAGMENT";
    public static final String TAG_DELETE = "OBJECTIVE_MANAGEMENT_FRAGMENT_TO_OBJECTIVE_DELETE_FRAGMENT";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_management, container, false);

        view.findViewById(R.id.create_objective).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveCreateFragment objectiveCreateFragment = new ObjectiveCreateFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, objectiveCreateFragment,TAG_CREATE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        view.findViewById(R.id.delete_objective).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectiveDeleteFragment objectiveDeleteFragment = new ObjectiveDeleteFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, objectiveDeleteFragment,TAG_DELETE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
