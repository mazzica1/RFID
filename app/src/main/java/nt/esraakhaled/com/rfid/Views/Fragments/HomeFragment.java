package nt.esraakhaled.com.rfid.Views.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new InventoryFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                            .addToBackStack(null).commit();
                    break;
                case 1:
                    fragment = new LocatorFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                            .addToBackStack(null).commit();
                    break;
                case 2:
                    fragment = new ExpireDateFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                            .addToBackStack(null).commit();
                    break;
                case 3:
                    fragment = new LoginFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                            .addToBackStack(null).commit();
            }

        }
    };

}
