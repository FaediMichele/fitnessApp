package com.example.crinaed.layout.objective;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
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
import com.example.crinaed.util.Lambda;
import com.example.crinaed.util.Period;
import com.example.crinaed.util.TypeOfStep;

public class ObjectiveStepCreateFragment extends Dialog {
    TextView mission_text;
    EditText max;
    Spinner unit;
    private Context context;
    MyStep result;

    public ObjectiveStepCreateFragment(@NonNull Context context) {
        super(context, R.style.DialogSlideTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_objective_step_create);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        final Spinner type = findViewById(R.id.spinner_type);
        final Spinner repeat = findViewById(R.id.spinner_repeat);
        unit = findViewById(R.id.spinner_unit);
        mission_text = findViewById(R.id.mission_header);
        max = findViewById(R.id.result);
        final EditText name = findViewById(R.id.name_step);

        context = getContext();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, TypeOfStep.toLocalized(context));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        type.setAdapter(adapter);

        adapter = new ArrayAdapter<>(context,  R.layout.spinner_item, Period.toRepetition(context));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        repeat.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context, R.array.unit_measure,  R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        unit.setAdapter(adapter1);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setVisibility(position!=TypeOfStep.CHECKLIST.ordinal());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Button confirm = findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().length()==0){
                    name.setError(context.getString(R.string.error_name));
                }
                if(type.getSelectedItemPosition()!=TypeOfStep.CHECKLIST.ordinal()){
                    if(max.getText().length()>=6 || max.getText().length()==0){
                        max.setError(context.getString(R.string.error_max));
                    }
                }
                result= new MyStep(-1, -1, name.getText().toString(),
                        unit.getSelectedItem().toString(),
                        type.getSelectedItemPosition()==TypeOfStep.CHECKLIST.ordinal()?1:Integer.parseInt(max.getText().toString()),
                        Period.getRepetition(repeat.getSelectedItemPosition()).getDay(),
                        TypeOfStep.values()[type.getSelectedItemPosition()].getType(),
                        Category.SPORT);
                dismiss();
            }
        });
    }

    public MyStep getResult() {
        return result;
    }

    private void setVisibility(boolean visibility){
        mission_text.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);
        max.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);
        unit.setVisibility(visibility?View.VISIBLE:View.INVISIBLE);
    }
}
