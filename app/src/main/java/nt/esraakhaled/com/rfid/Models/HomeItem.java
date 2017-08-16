package nt.esraakhaled.com.rfid.Models;

import android.content.Context;

/**
 * Created by megha on 15-03-06.
 */
public class HomeItem {

  public String id;
  public String name;
  public String imageName;
  public boolean isFav;

  public int getImageResourceId(Context context) {
    return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
  }
}
