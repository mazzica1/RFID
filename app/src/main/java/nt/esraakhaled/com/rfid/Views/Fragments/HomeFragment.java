package nt.esraakhaled.com.rfid.Views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import nt.esraakhaled.com.rfid.Controllers.Adapters.HomeListAdapter;
import nt.esraakhaled.com.rfid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private HomeListAdapter mAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        mRecyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
        mAdapter = new HomeListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(onItemClickListener);

        return view;
    }

    HomeListAdapter.OnItemClickListener onItemClickListener = new HomeListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            LocatorFragment fragment =new LocatorFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        }
    };

}
