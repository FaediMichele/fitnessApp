package com.example.crinaed.layout.objective;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;

public class CreateCommitment extends Fragment {
    StepAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.create_objective, container, false);
        RecyclerView recyclerSteps = view.findViewById(R.id.recycler_steps);
        recyclerSteps.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter=new StepAdapter(getContext());
        recyclerSteps.setAdapter(adapter);
        return view;
    }
}
