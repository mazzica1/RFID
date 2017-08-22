package nt.esraakhaled.com.rfid.Views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Controllers.Adapters.BaseAdapter;
import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;
import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.Models.BaseAdapterItem;
import nt.esraakhaled.com.rfid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends BaseFragment {


    public InventoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new BaseAdapter(getActivity(), BaseAdapter.ListItemType.Single,tags,false);
        return inflater.inflate(R.layout.base_fragment_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void tagEPCRead(final String epc) {
        super.tagEPCRead(epc);

        boolean isNew=true;
        for(BaseAdapterItem item:tags){

            if(item.getTitle().equalsIgnoreCase(epc)){
                isNew=false;
                break;
            }
        }

        if (isNew) {
            //
            tags.add(new BaseAdapterItem() {{
                setTitle(epc);
            }});
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void tagEPCDataRead(String epc, String data) {

    }


}
