package com.example.crinaed.layout.learning;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crinaed.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class LearningNotBoughtDetailsFragment extends Fragment {

    private String idCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning_not_buy_details, container, false);
        Bundle dataLearning = getArguments();
        idCourse = dataLearning.getString(LearningBuySearchFragment.KEY_ID_COURSE);

        final SliderView sliderView = view.findViewById(R.id.slider_course);
        SliderViewAdapter adapter = new SliderCourseAdapter(getActivity(),idCourse);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setIndicatorSelectedColor(ContextCompat.getColor(getContext(),R.color.redPrimary));
        //sliderView.setScrollTimeInSec(3);
        //sliderView.setAutoCycle(false);
        //sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        //costruzione della view modello---------------------------------------------------------------------------
        TextView title = view.findViewById(R.id.title_lesson);
        TextView reviewTopCard = view.findViewById(R.id.review_top_card);
        TextView priceTopCard = view.findViewById(R.id.price_top_card);
        TextView description_course = view.findViewById(R.id.description_course);
        Button buttonBuy = view.findViewById(R.id.text_congratulation);
        title.setText("Come non imparare mai nulla");
        reviewTopCard.setText("3,5/5");
        priceTopCard.setText("1'000€");
        buttonBuy.setText("BUY FOR 1'000€");
        description_course.setText("In questo corso non capire niente e non vi servirà a niente. " +
                "insengo da diverso tempo come non campire mai ninete e rimanere ignoranti per ciò se non capire niente delle lezioni" +
                "che ho caricato vuol dire che site gia ad un buon livello di incomprensione e il corso vi è servito semplicemnte come ripasso" +
                "se invece riuscite a capire qualcosa vi consiglio di ripetere il corso fino a quando non ci capirete più nulla");
        buttonBuy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //il corso si sblocca ed è acquistato
                return false;
            }
        });

        //manager RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LearningNotBoughtDetailsFragment.AdapterReview adapterReview = new LearningNotBoughtDetailsFragment.AdapterReview();
        recyclerView.setAdapter(adapterReview);

        return view;
    }

    private class AdapterReview extends RecyclerView.Adapter<LearningNotBoughtDetailsFragment.ReviewVH>{

        private final int nummero_recensioni = 10; //da toglire-----------------------------------------------------------
        @NonNull
        @Override
        public LearningNotBoughtDetailsFragment.ReviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,parent,false);
            return new LearningNotBoughtDetailsFragment.ReviewVH(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LearningNotBoughtDetailsFragment.ReviewVH holder, int position) {
            //qui serve il modello------------------------------------------------------------------
            // bisogna che venga caricata una lista di recensioni di questo corso (id del corso viene
            // caricato dall'fragmente nel metoodo on create non so se ti serve)
            // il position e l'elemento della lista di cui bisogna fare la onBindViewHolder
            holder.imageProfile.setImageDrawable(getContext().getDrawable(R.drawable.simple_people));
            holder.nameLastName.setText("Nome Cognome");
            holder.value.setText("3,5/5");
            holder.date.setText("15/12/2020");
            holder.review.setText("Sono davvero contento di aver comprato questo corso perché non imparato niente proprio come da descrzione, " +
                    "dentro viene spiegato molto bene come non campire niente anche se in realtà non capito bene come si fa");
        }

        @Override
        public int getItemCount() {
            return nummero_recensioni;
        }
    }

    private class ReviewVH extends RecyclerView.ViewHolder {

        ImageView imageProfile;
        TextView nameLastName;
        TextView value;
        TextView date;
        TextView review;

        public ReviewVH(@NonNull View itemView) {
            super(itemView);
            this.imageProfile = itemView.findViewById(R.id.image_profile);
            this.nameLastName = itemView.findViewById(R.id.name_and_last_name);
            this.value = itemView.findViewById(R.id.stars_review);
            this.date = itemView.findViewById(R.id.date_review);
            this.review = itemView.findViewById(R.id.review);
        }
    }
}

