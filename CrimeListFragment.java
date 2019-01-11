package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int lastPositionClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containier, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, containier, false);

        // RecyclerView를 연결
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        // recyclerview가 생성된 후에는 LayoutManger를 설정해주어야 동작함
        // LayoutManager가 TextView 항목들의 화면 위치를 처리하고 스크롤 동작도 정의함
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));        // LinearLayoutManager는 수직 방향으로 리스트 항목을 배치시킴

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }


    // 어댑터를 RecyclerView에 연결
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }

        // 디테일 화면에서 변경사항이 있을때 리스트 화면으로 돌아갈때 변경값을 알림
        else {
            mAdapter.notifyItemChanged(lastPositionClicked);
        }

    }


    // ViewHolder
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Crime mCrime;
        private int position;

        public CrimeHolder (View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime, int position){
            mCrime = crime;
            this.position = position;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v){
            CrimeListFragment.this.lastPositionClicked = position;
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }


    // Adapter
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter (List<Crime> crimes){
            mCrimes = crimes;
        }

        // 이 메서드는 RecyclerView에 의해 호출되고 리스트 항목으로 보여줄 새로운 View가 필요할 경우이다
        // 여기서는 View를 생성하고 ViewHolder에 넣는다. 하지만 이 시점에서는 RecyclerView는 데이터가 채워졌다고 생각하지 않는다
        @Override
        public CrimeHolder onCreateViewHolder (ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);       // 안드로이드 표준 라이브러리로부터 simple_list_item_1이라는 레이아웃을 인플레이트함
            return new CrimeHolder(view);
        }

        // ViewHolder와 위치(ArrayList에 저장된 Crime의 인덱스 값)를 인자로 받아와서
        // 위치를 이용해 Crime을 찾은 후 그것의 제목을 ViewHolder의 TextView에 변경한다
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position){
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime, position);
        }

        @Override
        public int getItemCount(){
            return mCrimes.size();
        }

    }



}
