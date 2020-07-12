package com.example.crinaed.ProgressBar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.crinaed.R;
import com.example.crinaed.database.entity.MyCommitment;
import com.example.crinaed.view.ProgressBarView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderProgressBarAdapterNew extends SliderViewAdapter<SliderProgressBarAdapterNew.SliderProgressBarVH> {

    public final static String TAG = "LAUNCH_DETAIL_FRAGMENT";
    private Context context;
    private List<MyCommitment> myCommitments;

    final private static int SOCIAL  = 0;
    final private static int MENTAL  = 1;
    final private static int PHYSICAL= 2;

    public SliderProgressBarAdapterNew(Context context) {
        this.context = context;
        this.myCommitments = getModel();
    }

    @Override
    public SliderProgressBarVH onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar_new, null);
        return new SliderProgressBarVH(itemView);
    }

    @Override
    public void onBindViewHolder(final SliderProgressBarVH viewHolder, final int position) {
        final MyCommitment myCommitment = myCommitments.get(position);
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
        viewHolder.progressBarView.setForegroundColor(primaryColor);
        viewHolder.progressBarView.setBackgroundColor(secondaryColor);
        viewHolder.progressBarView.setProgress(50.3f);//------------------------------------------------------------------bisogna prendere la lista di step collegati a uno obbiettivo e costruire la percentuale di completamento dell'obbiettivo
        viewHolder.progressBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-------------------------------------------------------------------------------------------------questa è come lanciavi la dialog nel vecchio adapter non so se ti puo essere utile
//                if(!viewHolder.status){
//                    DetailsProgressBarDialog dialog = new DetailsProgressBarDialog(context, context.getString(getCategoryForPosition(position).getRes()), getCategoryForPosition(position), owner);
//                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            viewHolder.status = false;
//                        }
//                    });
//                    viewHolder.status=true;
//                    dialog.show();
//                }
            }
        });
        viewHolder.description.setText(myCommitment.desc);
        viewHolder.title.setText(myCommitment.name);
        viewHolder.title.setTextColor(primaryColor);
    }

    @Override
    public int getCount() {
        return myCommitments.size();
    }

    class SliderProgressBarVH extends SliderViewAdapter.ViewHolder {

        TextView title;
        ProgressBarView progressBarView;
        TextView description;

        public SliderProgressBarVH(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.progressBarView = itemView.findViewById(R.id.progressBarItem);
            this.description =  itemView.findViewById(R.id.description_objective);
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
