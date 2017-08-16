package nt.esraakhaled.com.rfid.Controllers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Models.BaseAdapterItem;
import nt.esraakhaled.com.rfid.R;

/**
 * Created by Esraa Khaled on 8/16/2017.
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {

    public enum ListItemType {
        Single,
        TwoLines
    }

    ArrayList<BaseAdapterItem> items;
    Context mContext;
    ListItemType mType;

    public BaseAdapter(Context mContext, ListItemType type,ArrayList<BaseAdapterItem> items) {
        this.items = items;
        this.mContext = mContext;
        this.mType=type;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (mType) {
            case Single:
                view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single_line, parent, false);

                break;
            case TwoLines:
                view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_two_lines, parent, false);

                break;
        }
        return new BaseAdapter.BaseHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.titleText.setText(items.get(position).getTitle());
        if (mType == ListItemType.TwoLines){
            holder.subTitleText.setText(items.get(position).getSubTitle());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class BaseHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        TextView subTitleText;

        public BaseHolder(View itemView) {
            super(itemView);

            titleText = (TextView) itemView.findViewById(R.id.txt_title);
            if (mType == ListItemType.TwoLines) {
                subTitleText = (TextView) itemView.findViewById(R.id.txt_sub_title);
            }
        }
    }
}
