package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        // CrimeFragment를 호스팅
        FragmentManager fm = getSupportFragmentManager();       // 프래그먼트 매니저 생성
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);       // 프래그먼트 참조

        if(fragment == null){
            fragment = createFragment();
            // 트랜잭션 생성하고 이 트랜잭션 인스턴스에 프래그먼트 추가한 후 커밋
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

}
