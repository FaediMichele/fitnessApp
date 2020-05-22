package com.example.crinaed.ProgressBar;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.crinaed.ProgressBar.TestModel.ListProgressBar;
import com.example.crinaed.ProgressBar.TestModel.ProgressBarModel;
import com.example.crinaed.R;

import java.util.List;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class ProgressBarPagerAdapter extends FragmentStatePagerAdapter {

    private List<ProgressBarModel> listProgressBar;

    public ProgressBarPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.listProgressBar =  ListProgressBar.getList();
    }

    @Override
    public Fragment getItem(int position) {
        int foreground = R.color.colorPrimary;
        int background = R.color.colorPrimaryDark;
        Log.d("ciao","vedo l'oggetto colore :" + foreground);
        //return new ProgressBarPageFragment(R.color.colorRedPrimary,R.color.colorRedSecondary,0.0);
        return new ProgressBarPageFragment();
        // devi scrivere la view e settarla
    }



    @Override
    public int getCount() {
        return listProgressBar.size();
    }
}