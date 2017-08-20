package nt.esraakhaled.com.rfid.Views.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Controllers.Adapters.BaseAdapter;
import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;
import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.Models.BaseAdapterItem;
import nt.esraakhaled.com.rfid.R;

/**
 * Created by Lenovo on 20/08/17.
 */

public class BaseFragment extends Fragment implements UHFReaderDelegate , View.OnClickListener{

    boolean isFirstClick=true;

    ArrayList<BaseAdapterItem> tags=new ArrayList<>();;
    BaseAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.base_list);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);

        recyclerView.setAdapter(adapter);


        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstClick) {
                    UHFReader.getInstange(null).subscribeEPCRead(BaseFragment.this);
                    UHFReader.getInstange(null).startReading();

                    view.setTag("start");
                    ((FloatingActionButton) view).setImageResource(R.drawable.stop);

                } else {
                    UHFReader.getInstange(null).stopReading();
                    UHFReader.getInstange(null).unSubscribeEPCRead(BaseFragment.this);
                    view.setTag("stop");
                    ((FloatingActionButton) view).setImageResource(R.drawable.play);

                }

                isFirstClick = !isFirstClick;

            }
        });
        view.findViewById(R.id.floatingActionButton).setOnClickListener(this);

    }

    @Override
    public void tagEPCRead(String epc) {

    }

    @Override
    public void tagEPCDataRead(String epc, String data) {

    }

    @Override
    public void onClick(View view) {

    }
}
