package nt.esraakhaled.com.rfid.Views.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import nt.esraakhaled.com.rfid.R;


public class ExpireDateFragment extends Fragment {
    private LinearLayout Linear_base_header;
    private TextView txt_date;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    public ExpireDateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.base_fragment_list, container, false);

        Linear_base_header = (LinearLayout) view.findViewById(R.id.headerLayout);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//put header in root layout
        View header = getLayoutInflater(savedInstanceState).inflate(R.layout.expire_date_header, Linear_base_header, false);
        Linear_base_header.addView(header);

        txt_date = (TextView) header.findViewById(R.id.Current_date);


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        //getting the current date
        Calendar c = Calendar.getInstance();
        final String formattedDate = dateFormatter.format(c.getTime());
        txt_date.setText(formattedDate);

        //show date picker dialog when the text pressed
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txt_date.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //show dialog when pressed
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

    }
}
