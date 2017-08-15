package nt.esraakhaled.com.rfid.Controllers.Service;

/**
 * Created by OSX on 8/15/17.
 */

public interface RequestCallBack<T> {
    public void onSuccess(T responce);
    public void onFailure(Exception e);
}
