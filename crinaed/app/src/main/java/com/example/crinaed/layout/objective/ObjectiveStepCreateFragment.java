package com.example.crinaed.layout.objective;

import android.content.Context;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.example.crinaed.R;
import com.example.crinaed.database.entity.MyStep;
import com.example.crinaed.util.Category;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.TypeOfStep;

public class ObjectiveStepCreateFragment extends Fragment {
    TextView mission_text;
    EditText max;
    Spinner unit;

    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_objective_step_create, container, false);
        final Spinner type = view.findViewById(R.id.spinner_type);
        final Spinner cat = view.findViewById(R.id.spinner_cat);
        final Spinner repeat = view.findViewById(R.id.spinner_repeat);
        unit = view.findViewById(R.id.spinner_unit);
        mission_text = view.findViewById(R.id.mission_header);
        max = view.findViewById(R.id.result);
        final EditText name = view.findViewById(R.id.name_step);

        context = getContext();
        if(context!=null){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, TypeOfStep.toLocalized(context));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
            type.setAdapter(adapter);

            adapter = new ArrayAdapter<>(context,  R.layout.spinner_item, Category.toLocalized(context));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
            cat.setAdapter(adapter);

            adapter = new ArrayAdapter<>(context,  R.layout.spinner_item, Period.toRepetition(context));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
            repeat.setAdapter(adapter);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context, R.array.unit_measure,  R.layout.spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
            unit.setAdapter(adapter1);
        }
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setVisibility(position!=TypeOfStep.CHECKLIST.ordinal());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button confirm = view.findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0){
                    name.setError(getString(R.string.error_name));
                }
                if(type.getSelectedItemPosition()!=TypeOfStep.CHECKLIST.ordinal()){
                    if(max.getText().length()>=6 || max.getText().length()==0){
                        max.setError(getString(R.string.error_max));
                    }
                }
                MyStep s = new MyStep(-1, -1, name.getText().toString(),
                        unit.getSelectedItem().toString(),
                        Integer.parseInt(max.getText().toString()),
                        Period.getRepetition(repeat.getSelectedItemPosition()).getDay(),
                        TypeOfStep.values()[type.getSelectedItemPosition()].getType(),
                        Category.values()[cat.getSelectedItemPosition()]);

                /* TODO return the step created */
            }
        });
        return view;
    }

    private void setVisibility(boolean visibility){
        mission_text.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);
        max.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);
        unit.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);
    }
}
