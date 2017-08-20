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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;
import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.R;


public class ExpireDateFragment extends Fragment implements UHFReaderDelegate {
    private LinearLayout Linear_base_header;
    private TextView txt_date;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ArrayList<String> AllTags, ExpiredTag;
    private UHFReader uhfReader;
    private Calendar expireDate;


    public ExpireDateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllTags = new ArrayList<>();
        ExpiredTag = new ArrayList<>();
        uhfReader = UHFReader.getInstange(getActivity());
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
        expireDate = Calendar.getInstance();
        final String formattedDate = dateFormatter.format(expireDate.getTime());
        txt_date.setText(formattedDate);

        //show date picker dialog when the text pressed
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                expireDate = Calendar.getInstance();
                expireDate.set(year, monthOfYear, dayOfMonth);
                txt_date.setText(dateFormatter.format(expireDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

    }

    @Override
    public void tagEPCRead(String epc) {
        if (!AllTags.contains(epc)) {
            AllTags.add(epc);
            uhfReader.getEPCTagData(epc);
        }
    }

    @Override
    public void tagEPCDataRead(String epc, String data) {
        if (data != null || !data.equals("")) {
            String year = data.substring(0, 3);
            String month = data.substring(4, 5);
            String day = data.substring(6, 7);

            Calendar TagTime = Calendar.getInstance();
            TagTime.set(Calendar.YEAR, Integer.parseInt(year));
            TagTime.set(Calendar.MONTH, Integer.parseInt(month));
            TagTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));

            expireDate.set(Calendar.MINUTE, 0);
            expireDate.set(Calendar.HOUR, 0);
            expireDate.set(Calendar.SECOND, 0);
            expireDate.set(Calendar.MILLISECOND, 0);

            TagTime.set(Calendar.MINUTE, 0);
            TagTime.set(Calendar.HOUR, 0);
            TagTime.set(Calendar.SECOND, 0);
            TagTime.set(Calendar.MILLISECOND, 0);

            if (TagTime.getTime().compareTo(expireDate.getTime()) <= 0) {
                if (!ExpiredTag.contains(epc)) {
                    ExpiredTag.add(epc);
                }
            }
        }

    }
}
