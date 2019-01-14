package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "com.bignerdranch.android.criminalintent.date";

    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;

    // 프래그먼트 인자의 생성과 설정과 프래그먼트의 생성자를 대체하는 메서드
    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // 프래그먼트 인자로 data 가져오기
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        // Date 객체를 통해 Calendar 객체에서 년원일을 정수 형태로 뽑아냄
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);           // 날짜 속성들을 정수값들로 초기화

        // 제목과 DatePicker, 확인 버튼 하나를 갖는 AlertDiaglog를 생성하는 onCreateDialog 코드
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                // 버튼을 눌러 DatePicker 반환하기
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year,month,day).getTime();
                                sendResult(Activity.RESULT_OK, date);           // 목표 프래그먼트로 데이터 전달
                            }
                        })   // 대화상자가 보여주는 것을 수락할때 사용자가 누르는 버튼
                .create();
    }

    // 목표 프래그먼트 (CrimeFragment)로 intent에 저장해 데이터를 반환
    private void sendResult (int resultCode, Date date){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
