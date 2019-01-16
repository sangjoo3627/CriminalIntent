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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME =
            "com.bignerdranch.android.criminalintent.time";
    private static final String ARG_TIME = "time";
    private TimePicker mTimePicker;

    // 프래그먼트 인자의 생성과 설정과 프래그먼트의 생성자를 대체하는 메서드
    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){
        Date date = (Date) getArguments().getSerializable(ARG_TIME);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(min);
        }
        else{
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(min);
        }


        // 제목과 TimePicker, 확인 버튼 하나를 갖는 AlertDiaglog를 생성하는 onCreateDialog 코드
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                // 버튼을 눌러 TimePicker 반환하기
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hours = mTimePicker.getCurrentHour();
                                int mins = mTimePicker.getCurrentMinute();
                                Date date = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hours,mins).getTime();
                                sendResult(Activity.RESULT_OK, date);           // 목표 프래그먼트로 데이터 전달
                            }
                        })   // 대화상자가 보여주는 것을 수락할때 사용자가 누르는 버튼
                .create();
    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(TimePickerFragment.EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
