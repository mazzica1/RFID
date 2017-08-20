package nt.esraakhaled.com.rfid.Models;

/**
 * Created by Esraa Khaled on 8/16/2017.
 */

public class BaseAdapterItem {
    String title,subTitle;
    boolean flag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
