package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // Intent의 엑스트라 데이터를 가져옴
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);

        // 뷰페이저를 id로 가져옴
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        // 싱글톤 인스턴스인 CrimeLab에서 mCrimes을 가져옴
        mCrimes = CrimeLab.get(this).getCrimes();
        // FragmentManager를 인스턴스를 생성한후
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 페이저 어댑터로 FragmentStatePagerAdapter의 내부 클래스 인스턴스를 설정하고 생성함
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {      // FragmentManager를 생성자 인자로 줌
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // CrimeListFragment로부터 가져온 인텐트 엑스트라 데이터 crimeId와 일치하는 것을 찾아 인덱스로 설정
        for (int i=0; i<mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
