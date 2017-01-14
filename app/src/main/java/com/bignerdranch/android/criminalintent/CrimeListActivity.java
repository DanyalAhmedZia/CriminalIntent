package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by ghazi on 4/4/2016.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callback , CrimeFragment.Callback {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResourceID() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if(findViewById(R.id.detail_fragment_container ) == null){
            Intent intent = CrimePagerActivity.newIntent(getApplicationContext(),crime.getId());
            startActivity(intent);
        }
        else {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.detail_fragment_container,CrimeFragment.newInstance(crime.getId()))
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment =(CrimeListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}
