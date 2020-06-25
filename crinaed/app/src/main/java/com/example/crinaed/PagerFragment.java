package com.example.crinaed;

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

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PagerFragment extends Fragment {

    private static final int NUM_PAGES = 3;
    private static final int OBJECTIVE_FRAGMENT = 0;
    private static final int SOCIAL_FRAGMENT = 1;
    private static final int LEARNING_FRAGMENT = 2;

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        //configure viewPagger2
        viewPager = view.findViewById(R.id.container_page);
        pagerAdapter = new HomePagerAdapter(getChildFragmentManager(),getLifecycle());
        viewPager.setAdapter(pagerAdapter);

        //configure tab layout
        TabLayout tabs = view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                 switch (position)   {
                     case OBJECTIVE_FRAGMENT:
                         tab.setText(R.string.tab_objective);
                         break;
                     case SOCIAL_FRAGMENT:
                         tab.setText(R.string.tab_social);
                         break;
                     case LEARNING_FRAGMENT:
                         tab.setText(R.string.tab_learning);
                         break;
                     default:
                         tab.setText("undefine");
                         break;
                 }
            }
        }).attach();
        return view;
    }




    private class HomePagerAdapter extends FragmentStateAdapter{

        public HomePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;

            switch (position) {
                case OBJECTIVE_FRAGMENT:
                    fragment = new ObjectiveFragment();
                    break;
                case SOCIAL_FRAGMENT:
                    fragment = new SocialFragment();
                    break;
                case LEARNING_FRAGMENT:
                    fragment = new LearningFragment();
                    break;
                default:
                    fragment = new ObjectiveFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
