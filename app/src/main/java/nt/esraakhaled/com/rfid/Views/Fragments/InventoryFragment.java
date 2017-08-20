package nt.esraakhaled.com.rfid.Views.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Controllers.Adapters.BaseAdapter;
import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;
import nt.esraakhaled.com.rfid.Models.BaseAdapterItem;
import nt.esraakhaled.com.rfid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment implements UHFReaderDelegate{

    ArrayList<BaseAdapterItem> tags;
    BaseAdapter adapter;


    public InventoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tags=new ArrayList<>();
        adapter = new BaseAdapter(getActivity(), BaseAdapter.ListItemType.Single,tags,false);
        return inflater.inflate(R.layout.base_fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.base_list);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void tagEPCRead(final String epc) {

        boolean isNew=true;
        for(BaseAdapterItem item:tags){

            if(item.getTitle().equalsIgnoreCase(epc)){
                isNew=false;
                break;
            }
        }

        if (isNew){
            //
            tags.add(new BaseAdapterItem(){{setTitle(epc); }});
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void tagEPCDataRead(String data) {

    }
}