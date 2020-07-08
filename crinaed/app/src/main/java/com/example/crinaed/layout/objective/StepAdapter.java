package com.example.crinaed.layout.objective;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.database.entity.MyStep;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepVH> {
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
    public StepVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull StepVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    static class StepVH extends RecyclerView.ViewHolder{

        public StepVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
