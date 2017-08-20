package nt.esraakhaled.com.rfid.Controllers.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(mContext, R.drawable.ic_clear_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) mContext.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
               /* BaseAdapter testAdapter = (BaseAdapter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }*/
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                BaseAdapter adapter = (BaseAdapter) recyclerView.getAdapter();
                /*boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
                }*/
                adapter.onLongClick(viewHolder.itemView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onLongClick(View v) {

        final int pos= (int) v.getTag();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);
        alertDialogBuilder.setMessage("Are you sure to delete this item?");
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyDataSetChanged();
            }
        });
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                items.remove(pos);
                notifyItemRemoved(pos);
            }
        }).show();



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
    RecyclerView recyclerView;

    public BaseAdapter(Context mContext, ListItemType type,ArrayList<BaseAdapterItem> items,boolean canDelete) {
        this.items = items;
        this.mContext = mContext;
        this.mType=type;
        this.canDelete=canDelete;
    }

    public  void setRecyclerView(RecyclerView rc){
        recyclerView=rc;
        setUpItemTouchHelper();
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
        if(canDelete)
        {
            view.setOnLongClickListener(this);
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
        if (items.get(position).isFlag()){
            holder.itemView.setBackgroundColor(Color.CYAN);
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
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
