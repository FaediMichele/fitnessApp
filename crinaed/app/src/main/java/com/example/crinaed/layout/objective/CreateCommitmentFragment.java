package com.example.crinaed.layout.objective;

import android.content.Context;
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
import com.example.crinaed.database.entity.MyStep;

import java.util.ArrayList;
import java.util.List;

public class CreateCommitmentFragment extends Fragment {
    private StepAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_objective, container, false);

        RecyclerView recyclerSteps = view.findViewById(R.id.recycler_steps);
        recyclerSteps.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new CreateCommitmentFragment.StepAdapter(getContext());
        recyclerSteps.setAdapter(adapter);
        return view;
    }


    private class StepAdapter extends RecyclerView.Adapter<CreateCommitmentFragment.StepVH> {
        List<MyStep> steps = new ArrayList<>();
        Context context;
        public StepAdapter(Context context){
            this.context=context;
        }

        public void addStep(MyStep step){
            steps.add(step);
        }

        @NonNull
        @Override
        public CreateCommitmentFragment.StepVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull CreateCommitmentFragment.StepVH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return steps.size();
        }

    }

     private class StepVH extends RecyclerView.ViewHolder{

        public StepVH(@NonNull View itemView) {
            super(itemView);
        }
    }

}
