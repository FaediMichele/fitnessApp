package com.example.crinaed.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.crinaed.ProgressBar.SliderProgressBarAdapterNew;
import com.example.crinaed.R;
import com.example.crinaed.database.entity.MyCommitment;
import com.github.mikephil.charting.charts.LineChart;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveCharterAdapter extends SliderViewAdapter<ObjectiveCharterAdapter.ObjectiveCharterVH> {

    List<MyCommitment> myCommitments;

    final private static int SOCIAL  = 0;
    final private static int MENTAL  = 1;
    final private static int PHYSICAL= 2;

    public ObjectiveCharterAdapter() {
        this.myCommitments = getModel();
    }

    @Override
    public ObjectiveCharterVH onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objective_charter, null);
        return new ObjectiveCharterVH(itemView);
    }

    @Override
    public void onBindViewHolder(ObjectiveCharterVH viewHolder, int position) {
        MyCommitment myCommitment = myCommitments.get(position);
        int primaryColor;
        int secondaryColor;
        switch(position % 3){//--------------------------------------------------------------------------------------non so come prendere il tipo dall'obbiettivo
            case SOCIAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.greenSecondary);
                break;
            case MENTAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.bluSecondary);
                break;
            case PHYSICAL:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redSecondary);
                break;
            default:
                primaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redPrimary);
                secondaryColor = ContextCompat.getColor(viewHolder.itemView.getContext(),R.color.redSecondary);
                break;
        }

        viewHolder.title.setText(myCommitment.name);
        viewHolder.title.setTextColor(primaryColor);
        viewHolder.week.setBackgroundColor(primaryColor);
        viewHolder.month.setBackgroundColor(primaryColor);
        viewHolder.year.setBackgroundColor(primaryColor);
        viewHolder.description.setText(myCommitment.desc);

        /**
         * manca
         *
         * - impostare il grafico
         * - impostare il listener dei bottoni
         */
    }

    @Override
    public int getCount() {
        return myCommitments.size();
    }

    public class ObjectiveCharterVH extends SliderViewAdapter.ViewHolder {
        TextView title;
        Button week;
        Button month;
        Button year;
        LineChart chart;
        TextView description;

        public ObjectiveCharterVH(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.week = itemView.findViewById(R.id.button_week);
            this.month = itemView.findViewById(R.id.button_month);
            this.year = itemView.findViewById(R.id.button_year);
            this.chart = itemView.findViewById(R.id.lineChart);
            this.description = itemView.findViewById(R.id.description_objective);
        }
    }


    static public List<MyCommitment> getModel() {
        List<MyCommitment> myCommitments = new ArrayList<>();
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        myCommitments.add(new MyCommitment(-1, "Title objective", "descrizione lunga per vedere se quando va a capo da danni molto brutti testa mvediamo se da problemi", -1, -1));
        return myCommitments;
    }
}
