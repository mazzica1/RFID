package nt.esraakhaled.com.rfid.Views.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Controllers.Adapters.BaseAdapter;
import nt.esraakhaled.com.rfid.Controllers.Interfaces.IKeyDown;
import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;
import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.Models.BaseAdapterItem;
import nt.esraakhaled.com.rfid.R;

/**
 * Created by Lenovo on 20/08/17.
 */

public class BaseFragment extends Fragment implements UHFReaderDelegate, IKeyDown {

    boolean isFirstClick = true;
    ArrayList<BaseAdapterItem> tags = new ArrayList<>();
    ArrayList<String> allTags = new ArrayList<>();

    BaseAdapter adapter;
    RecyclerView recyclerView;
    long lastRead=-1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StaggeredGridLayoutManager mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        recyclerView = (RecyclerView) view.findViewById(R.id.base_list);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);

        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                didClickSubmit();
            }
        });

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {

            AsyncTask<Integer,Void,Integer> myAsyncTask=null;
            @Override
            public void onClick(View view) {

                getView().findViewById(R.id.btnSubmit).setVisibility(View.GONE);
                if (isFirstClick) {
                    myAsyncTask=new AsyncTask<Integer, Void, Integer>() {

                        @Override
                        protected Integer doInBackground(Integer... integers) {
                            lastRead = System.currentTimeMillis();
                            while (!isCancelled() && (System.currentTimeMillis() - lastRead ) < 10000) try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Integer integer) {
                            super.onPostExecute(integer);
                            if(!isCancelled()){
                                getView().findViewById(R.id.fab).performClick();
                                didFinishReading();
                            }
                        }
                    };

                    myAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,0);

                    UHFReader.getInstange(null).subscribeEPCRead(BaseFragment.this);
                    UHFReader.getInstange(null).startReading();

                    view.setTag("start");
                    ((FloatingActionButton) view).setImageResource(R.drawable.stop);

                } else {
                    myAsyncTask.cancel(false);
                    UHFReader.getInstange(null).stopReading();
                    UHFReader.getInstange(null).unSubscribeEPCRead(BaseFragment.this);
                    view.setTag("stop");
                    ((FloatingActionButton) view).setImageResource(R.drawable.play);

                }

                isFirstClick = !isFirstClick;

            }
        });
        final View fab = view.findViewById(R.id.fab);


        fab.post(new Runnable() {
            @Override
            public void run() {
                fab.requestLayout();
            }
        });

    }

    @Override
    public void tagEPCRead(String epc) {

        if (!allTags.contains(epc)) {
            allTags.add(epc);
            lastRead = System.currentTimeMillis();
        }
    }

    @Override
    public void tagEPCDataRead(String epc, String data) {

    }

    @Override
    public void myOnKeyDwon() {
        getView().findViewById(R.id.fab).performClick();
    }

    protected void didFinishReading()
    {
        getView().findViewById(R.id.btnSubmit).setVisibility(View.VISIBLE);
    }

    protected void didClickSubmit()
    {
    }
}
