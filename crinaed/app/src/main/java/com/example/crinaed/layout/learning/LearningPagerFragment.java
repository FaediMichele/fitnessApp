package com.example.crinaed.layout.learning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crinaed.R;
import com.example.crinaed.database.DatabaseUtil;
import com.example.crinaed.database.entity.join.CourseWithExercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class LearningPagerFragment extends Fragment {
    private static final int NUM_PAGES = 2;
    private static final int DETAIL_FRAGMENT = 0;
    private static final int BOUGHT_FRAGMENT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_learning_pager, container, false);

        //manager model
        Bundle dataLearning = getArguments();
        if(dataLearning == null){
            Snackbar.make(view, R.string.unknown_error, BaseTransientBottomBar.LENGTH_LONG).show();
            return view;
        }
        if(!dataLearning.containsKey(LearningBuySearchFragment.IS_BOUGHT)){
            dataLearning.putBoolean(LearningBuySearchFragment.IS_BOUGHT, true);
        }

        //configure viewPager2
        ViewPager2 viewPager = view.findViewById(R.id.container_page);
        FragmentStateAdapter pagerAdapter = new LearningPagerAdapter(getChildFragmentManager(), getLifecycle(), dataLearning);
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new LearningPagerFragment.PageChangeListener());

        //configure tab layout
        TabLayout tabs = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)   {
                    case DETAIL_FRAGMENT:
                        tab.setText(R.string.tab_learnign_details);
                        break;
                    case BOUGHT_FRAGMENT:
                        tab.setText(R.string.tab_learnign_bought);
                        break;
                    default:
                        tab.setText("undefine");
                        break;
                }
            }
        }).attach();
        return view;
    }

    private class PageChangeListener extends ViewPager2.OnPageChangeCallback{

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            FloatingActionButton fab = getActivity().findViewById(R.id.floating_action_button);
            switch (position) {
                case DETAIL_FRAGMENT:
                    break;
                case BOUGHT_FRAGMENT:
                    break;
            }
        }
    }

    private static class LearningPagerAdapter extends FragmentStateAdapter{
        private boolean isBought;
        private Bundle dataLearning;

        public LearningPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle dataLearning) {
            super(fragmentManager, lifecycle);
            isBought = dataLearning.getBoolean(LearningBuySearchFragment.IS_BOUGHT);
            this.dataLearning=dataLearning;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch (position) {
                case DETAIL_FRAGMENT:
                    if(isBought){
                        fragment = new LearningBoughtDetailsFragment();
                    }else {
                        fragment = new LearningNotBoughtDetailsFragment();
                    }
                    break;
                case BOUGHT_FRAGMENT:
                    if(isBought){
                        fragment = new LearningBoughtFragment();
                    }else{
                        fragment = new LearningNotBoughtFragment();
                    }
                    break;
                default:
                    fragment = new LearningNotBoughtDetailsFragment();
                    break;
            }
            fragment.setArguments(dataLearning);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

