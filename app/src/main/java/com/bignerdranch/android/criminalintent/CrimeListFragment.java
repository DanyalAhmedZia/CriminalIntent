package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ghazi on 4/4/2016.
 */
public class CrimeListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private CrimeAdapter mCrimeAdapter;
    private int mPosition;
    private boolean mSubtitleVisible;
    private Callback mCallback;


    public interface Callback{
        public void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (Callback) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_crime,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    public void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeAdapter == null){
            mCrimeAdapter = new CrimeAdapter(crimes);
            mRecyclerView.setAdapter(mCrimeAdapter);
        }
        else {
            mCrimeAdapter.setCrimes(crimes);
            mCrimeAdapter.notifyItemChanged(mPosition);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_list_fragment,menu);
        MenuItem subtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitle.setTitle(R.string.hide_subtitle);
        }
        else {
            subtitle.setTitle(R.string.show_subtitle);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime :
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                mCallback.onCrimeSelected(crime);
                updateUI();
                return true;
            case R.id.menu_item_show_subtitle :
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimes = crimeLab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural,crimes,crimes);
        if(!mSubtitleVisible){
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckedBox;
        private Crime mCrime;




        public CrimeHolder(View view){
            super(view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_textView);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_textView);
            mSolvedCheckedBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_checkbox);
            mSolvedCheckedBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCrime.setSolved(mSolvedCheckedBox.isChecked());
                    CrimeLab.get(getActivity()).updateCrime(mCrime);
                }
            });

            itemView.setOnClickListener(this);
        }

        public void bindCrime(Crime crime){
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(crime.getDate().toString());
            mSolvedCheckedBox.setChecked(crime.isSolved());

            mCrime = crime;
        }

        @Override
        public void onClick(View v) {
            mPosition = mRecyclerView.getChildAdapterPosition(v);
            mCallback.onCrimeSelected(mCrime);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime> mCrimeList;
        public CrimeAdapter(List<Crime> crimes){
            mCrimeList = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.list_item_crime,parent,false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            holder.bindCrime(mCrimeList.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimeList.size();
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimeList = crimes;
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
