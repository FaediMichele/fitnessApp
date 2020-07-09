package com.example.crinaed.layout.learning;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.crinaed.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class LearningPagerFragment extends Fragment {
    private static final int NUM_PAGES = 2;
    private static final int DETAIL_FRAGMENT = 0;
    private static final int BOUGHT_FRAGMENT = 1;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private ModelloLearnginPager course;
    private Bundle dataLearning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_learning_pager, container, false);

        //manager model
        dataLearning = getArguments();
        String id = dataLearning.getString(LearningBuySearchFragment.KEY_ID_COURSE);
        this.course = findCourseByID(id,ModelloLearnginPager.getModello());

        //configure viewPagger2
        viewPager = view.findViewById(R.id.container_page);
        pagerAdapter = new LearningPagerAdapter(getChildFragmentManager(),getLifecycle());
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
                    //bind delle pagine
                    break;
                case BOUGHT_FRAGMENT:
                    //bind delle pagine
                    break;
            }
        }
    }

    private class LearningPagerAdapter extends FragmentStateAdapter{

        public LearningPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch (position) {
                case DETAIL_FRAGMENT:
                    if(course.isBought){
                        fragment = new LearningBoughtDetailsFragment();
                        fragment.setArguments(dataLearning);
                    }else {
                        fragment = new LearningNotBoughtDetailsFragment();
                        fragment.setArguments(dataLearning);
                    }
                    break;
                case BOUGHT_FRAGMENT:
                    if(course.isBought){
                        fragment = new LearningBoughtFragment();
                        fragment.setArguments(dataLearning);
                    }else{
                        fragment = new LearningNotBoughtFragment();
                        fragment.setArguments(dataLearning);
                    }
                    break;
                default:
                    fragment = new LearningNotBoughtDetailsFragment();
                    fragment.setArguments(dataLearning);
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    //modelo da eliminare-----------------------------------------------------------------------------------------------------------------------

    private ModelloLearnginPager findCourseByID(String id, List<ModelloLearnginPager> list){
        for (ModelloLearnginPager m: list) {
            if(m.idCourse.equals(id)){
                return m;
            }
        }
        return null;
    }


    public static class ModelloLearnginPager {
        String idCourse;
        boolean isBought;

        public ModelloLearnginPager(String idCourse, boolean isBought) {
            this.idCourse = idCourse;
            this.isBought = isBought;
        }

        static public List<ModelloLearnginPager> getModello(){
            List<ModelloLearnginPager> list = new ArrayList<>();
            list.add(new ModelloLearnginPager("id_corso",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            list.add(new ModelloLearnginPager("id_course_false",true));
            return list;
        }
    }


}

