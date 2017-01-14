package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

/**
 * Created by ghazi on 4/4/2016.
 */
public class CrimePagerActivity extends AppCompatActivity implements CrimeFragment.Callback{

    private List<Crime> mCrimes;
    private ViewPager mViewPager;
    private static String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context context, UUID id){
        Intent intent = new Intent(context,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,id);
        return intent;
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID id = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        setContentView(R.layout.crime_pager_activity);
        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fm  = getSupportFragmentManager();

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = CrimeFragment.newInstance(mCrimes.get(position).getId());
                return fragment;
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i=0 ; i<mCrimes.size() ; i++){
            if(mCrimes.get(i).getId().equals(id)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {

    }
}
