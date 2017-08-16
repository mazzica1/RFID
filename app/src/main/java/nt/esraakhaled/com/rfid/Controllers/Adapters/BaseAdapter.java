package nt.esraakhaled.com.rfid.Controllers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.R;

/**
 * Created by Esraa Khaled on 8/16/2017.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {

    public enum ListItemType {
        Single,
        TwoLines
    }

    ArrayList<String> tags;
    Context mContext;
    ListItemType mType;

    public BaseAdapter(Context mContext, ListItemType type,ArrayList<String> tags) {
        this.tags = tags;
        this.mContext = mContext;
        this.mType=type;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (mType) {
            case Single:
                view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_locator_inventory, parent, false);

                break;
            case TwoLines:
                view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_locator_inventory, parent, false);

                break;
        }
        return new BaseAdapter.BaseHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.tagText.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }


    public class BaseHolder extends RecyclerView.ViewHolder {
        TextView tagText;
        public BaseHolder(View itemView) {
            super(itemView);
            tagText = (TextView) itemView.findViewById(R.id.tag_text);
        }
    }
}
