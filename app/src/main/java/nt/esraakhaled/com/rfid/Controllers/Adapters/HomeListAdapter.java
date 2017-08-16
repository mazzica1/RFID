package nt.esraakhaled.com.rfid.Controllers.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nt.esraakhaled.com.rfid.Models.HomeItem;
import nt.esraakhaled.com.rfid.R;

/**
 * Created by megha on 15-03-06.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

  ArrayList<HomeItem> homeItems;
  Context mContext;
  OnItemClickListener mItemClickListener;

  public HomeListAdapter(Context context) {
    this.mContext = context;
    homeItems=new ArrayList<>();

    HomeItem inventory=new HomeItem();
    inventory.name="Inventory";
    inventory.imageName="inventory";

    homeItems.add(inventory);


    HomeItem locator=new HomeItem();
    locator.name="Locator";
    locator.imageName="locator";

    homeItems.add(locator);


    HomeItem expired=new HomeItem();
    expired.name="Expired";
    expired.imageName="expired";

    homeItems.add(expired);


    HomeItem logOut=new HomeItem();
    logOut.name="Log Out";
    logOut.imageName="logout";

    homeItems.add(logOut);


  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final HomeItem homeItem = homeItems.get(position);

    holder.Name.setText(homeItem.name);
    Picasso.with(mContext).load(homeItem.getImageResourceId(mContext)).into(holder.Image);

    Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), homeItem.getImageResourceId(mContext));

    Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
      public void onGenerated(Palette palette) {
        int mutedLight = palette.getMutedColor(mContext.getResources().getColor(android.R.color.black));
        holder.NameHolder.setBackgroundColor(mutedLight);
      }
    });
  }

  @Override
  public int getItemCount() {
    return homeItems.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public LinearLayout Holder;
    public LinearLayout NameHolder;
    public TextView Name;
    public ImageView Image;

    public ViewHolder(View itemView) {
      super(itemView);
      Holder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
      Name = (TextView) itemView.findViewById(R.id.Name);
      NameHolder = (LinearLayout) itemView.findViewById(R.id.NameHolder);
      Image = (ImageView) itemView.findViewById(R.id.Image);
      Holder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      if (mItemClickListener != null) {
        mItemClickListener.onItemClick(itemView, getPosition());
      }
    }
  }

  public interface OnItemClickListener {
    void onItemClick(View view, int position);
  }

  public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
    this.mItemClickListener = mItemClickListener;
  }

}
