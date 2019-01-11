package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 싱글톤으로 구현
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    // 생성자 호출할 때 무조건 이 get함수를 통해 호출 (인스턴스를 한개만 존재하도록 체크)
    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    // 생성자가 private라 get()을 통해서만 호출가능
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();

        for (int i=0; i<100; i++){
            Crime crime = new Crime();
            crime.setTitle("범죄 #" + i);
            crime.setSolved(i%2 == 0);      //  짝수번째 요소에는 true를 임의 설정
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }

}
