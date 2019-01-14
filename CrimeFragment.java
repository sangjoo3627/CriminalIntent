package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";      // 프래그먼트 인자 첨부를 위한 키값
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    // 프래그먼트 인자 첨부를 위한 static method
    public static CrimeFragment newInstance(UUID crimeId){

        // UUID를 인자로 받아 번들인스턴스 생성하고
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        // 프래그먼트 인스턴스를 생성한 후 그 인자를 프래그먼트에 첨부
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // CrimeActivity의 인텐트에 엑세스 (직접말고 프래그먼트 인자 번들을 통해서)
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        // 입력창 컨트롤
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // nothing
            }
        });


        // Date 버튼 컨트롤
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        updateDate();

        DateFormat df = new DateFormat();


        mDateButton.setText(df.format("EEE, MMM d, yyyy",mCrime.getDate()).toString());
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 프래그먼트 매니저와 DatePickerFragment 인스턴스를 생성한 후 show()로 화면에 보여줌
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);             // DatePickerFragment 인스턴스의 목표 프래그먼트로 CrimeFragment를 설
                dialog.show(manager, DIALOG_DATE);
            }
        });


        // 체크박스 컨트롤
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
