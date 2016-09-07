package com.sample.drawer.fragments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.sample.drawer.CalendarList.ReportActivity;
import com.sample.drawer.OnBackPressedListener;
import com.sample.drawer.R;


/**
 * Created by Daniyar_Amangeldy on 08/12/15.
 */
public class Fragment3 extends Fragment implements OnBackPressedListener{
    MaterialCalendarView calendar;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
    /*    android.support.v7.app.ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();*/
        getActivity().setTitle(R.string.info_fr5);
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        calendar = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //Send Data to ReportActivity
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                String report = String.valueOf(date.getDay())+String.valueOf(date.getMonth())+String.valueOf(date.getYear());
                intent.putExtra("report",report);
                intent.putExtra("month",date.getMonth());
                intent.putExtra("day",date.getDay());
                intent.putExtra("year",date.getYear());
                startActivity(intent);

            }
        });


        return view;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.info_fr4)
                .setMessage(R.string.exit_confirm)
                .setIcon(R.drawable.help)
                .setCancelable(false)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
