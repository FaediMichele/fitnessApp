package com.example.crinaed.layout.objective;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.TypeOfStep;
import com.example.crinaed.util.Util;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ObjectiveCreateFragment extends Fragment {
    private StepAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_create, container, false);
        final EditText name = view.findViewById(R.id.title_objective);
        final EditText desc = view.findViewById(R.id.description_objective);
        final Spinner cat = view.findViewById(R.id.spinner_cat);
        view.findViewById(R.id.add_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectiveStepCreateFragment objectiveStepCreateFragment = new ObjectiveStepCreateFragment(getContext());
                objectiveStepCreateFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MyStep result = objectiveStepCreateFragment.getResult();
                        if(result!=null) {
                            result.category=Category.values()[cat.getSelectedItemPosition()];
                            adapter.addStep(result);
                        }
                    }
                });
                objectiveStepCreateFragment.show();
            }
        });
        final Button button= view.findViewById(R.id.confirm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(name.getText().length()==0){
                    name.setError(getString(R.string.error_name));
                    return;
                }
                if(desc.getText().length()==0){
                    desc.setError(getString(R.string.error_description));
                    return;
                }
                if(adapter.steps.size()==0){
                    button.setError(getString(R.string.no_step));
                    return;
                }
                MyCommitment commitment = new MyCommitment(-1, name.getText().toString(), desc.getText().toString(), new Date().getTime(), Util.getInstance().getIdUser());
                DatabaseUtil.getInstance().getRepositoryManager().getCommitmentRepository().insert(commitment, adapter.steps.toArray(new MyStep[0]), new Lambda() {
                    @Override
                    public Object[] run(Object... paramether) {
                        Snackbar.make(view, getString(R.string.objective_added), BaseTransientBottomBar.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                        getActivity().onBackPressed();
                        return new Object[0];
                    }
                });
            }
        });


        ArrayAdapter<String> arrayAdapteradapter = new ArrayAdapter<>(getContext(),  R.layout.spinner_item, Category.toLocalized(getContext()));
        arrayAdapteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        cat.setAdapter(arrayAdapteradapter);

        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for(MyStep s: adapter.steps){
                    s.category=Category.values()[position];
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerSteps = view.findViewById(R.id.recycler_steps);
        recyclerSteps.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new ObjectiveCreateFragment.StepAdapter(getContext());
        recyclerSteps.setAdapter(adapter);
        return view;
    }


    private class StepAdapter extends RecyclerView.Adapter<ObjectiveCreateFragment.StepVH> {
        private final List<MyStep> steps = new ArrayList<>();
        private final Context context;

        public StepAdapter(Context context){
            this.context=context;
        }

        public void addStep(MyStep step){
            steps.add(step);
            Log.d("naed", "n° step: " + (steps.size()-1));
            notifyItemInserted(steps.size()-1);
        }

        @NonNull
        @Override
        public StepVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objective_step, parent, false);
            return new StepVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ObjectiveCreateFragment.StepVH holder, int position) {
            MyStep step = this.steps.get(position);
            switch (step.category) {
                case SOCIAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_social));
                    break;
                case MENTAL:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_learning));
                    break;
                case SPORT:
                    holder.itemView.findViewById(R.id.step_item).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.course_physical));
                    break;
            }

            holder.title.setText(step.name);
            holder.repetition.setText(Objects.requireNonNull(Period.fromDays(step.repetitionDay)).getResId());
            if (step.getType()== TypeOfStep.PROGRESSION) {
                holder.description.setText(getString(R.string.progressive_format,(int) step.max, step.unitMeasure));
            }else {
                holder.description.setText(getString(R.string.checklist));
            }
        }
        @Override
        public int getItemCount() {
            return steps.size();
        }

    }

     private class StepVH extends RecyclerView.ViewHolder{

        TextView title;
        TextView repetition;
        TextView description;

         public StepVH(@NonNull View itemView) {
             super(itemView);
             this.title = itemView.findViewById(R.id.title_step);
             this.repetition = itemView.findViewById(R.id.repetition_step);
             this.description = itemView.findViewById(R.id.description_step);
         }
     }

}
