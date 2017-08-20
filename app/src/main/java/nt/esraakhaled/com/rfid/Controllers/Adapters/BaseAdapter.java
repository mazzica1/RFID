package nt.esraakhaled.com.rfid.Controllers.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.AlertDialogLayout;
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

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.BaseHolder> implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View v) {

        final int pos= (int) v.getTag();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setMessage("Are you sure to delete this item?");
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                items.remove(pos);
                notifyItemRemoved(pos);
            }
        });



        return false;
    }

    public enum ListItemType {
        Single,
        TwoLines
    }

    ArrayList<BaseAdapterItem> items;
    Context mContext;
    ListItemType mType;
    boolean canDelete;

    public BaseAdapter(Context mContext, ListItemType type,ArrayList<BaseAdapterItem> items,boolean canDelete) {
        this.items = items;
        this.mContext = mContext;
        this.mType=type;
        this.canDelete=canDelete;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (mType) {
            case Single:

                view =LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single_line, parent, false);
                if(canDelete)
                {
                    view.setOnLongClickListener(this);
                }


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
        holder.itemView.setTag(position);
        holder.itemView.setBackgroundColor(Color.CYAN);
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
