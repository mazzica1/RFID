package nt.esraakhaled.com.rfid.Views.Fragments;


import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Controllers.Adapters.BaseAdapter;
import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;
import nt.esraakhaled.com.rfid.Models.BaseAdapterItem;
import nt.esraakhaled.com.rfid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocatorFragment extends BaseFragment {

    EditText tagEditText;
    Button addToList;

    public LocatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new BaseAdapter(getActivity(), BaseAdapter.ListItemType.Single, tags, true);
        return inflater.inflate(R.layout.base_fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        adapter.setRecyclerView(recyclerView);
        View v = getLayoutInflater(savedInstanceState).inflate(R.layout.locator_part, (ViewGroup) view, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.viewA);
        linearLayout.addView(v);


        tagEditText = (EditText) view.findViewById(R.id.tag_edittext);
        addToList = (Button) view.findViewById(R.id.add_to_list_button);
        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tagEditText.getText().toString().isEmpty()) {
                    tagEditText.setError("Tag can't be empty!");
                } else {
                    boolean alreadyExists = false;
                    for (int i = 0; i < tags.size(); i++) {
                        if (tagEditText.getText().toString() == tags.get(i).getTitle()) {
                            alreadyExists = true;
                        }
                    }
                    if (alreadyExists) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Tag Already Exists!")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        tags.add(new BaseAdapterItem() {{
                            setTitle(tagEditText.getText().toString());
                        }});
                        adapter.notifyDataSetChanged();
                        tagEditText.setText("");
                    }
                }
            }
        });

    }

    @Override
    public void tagEPCRead(String epc) {
        super.tagEPCRead(epc);
        for (int i = 0; i < tags.size(); i++) {
            if (epc.equalsIgnoreCase(tags.get(i).getTitle())) {
                tags.get(i).setFlag(true);
                adapter.notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    public void tagEPCDataRead(String epc, String data) {

    }

}
