package nt.esraakhaled.com.rfid.Controllers.Sensors;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHF;

import java.util.ArrayList;
import java.util.List;

import nt.esraakhaled.com.rfid.Controllers.Interfaces.UHFReaderDelegate;

/**
 * Created by OSX on 8/15/17.
 */

public class UHFReader {

    private RFIDWithUHF mReader=null;
    private Context mContext;
    private boolean loopStarted=false;
    private List<UHFReaderDelegate> listeners=new ArrayList<>();
    static private UHFReader instance=null;


    public static UHFReader getInstange(Context context){
        if (instance == null ){
            instance = new UHFReader(context);
        }
        return instance;
    }

    private UHFReader(Context context){
        try {
            mContext=context;
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {

            Toast.makeText(context, ex.getMessage(),
                    Toast.LENGTH_SHORT).show();

            return;
        }

        if (mReader != null) {
            new InitTask().execute();
        }
    }

    public void subscribeEPCRead(UHFReaderDelegate listener){
        if (!listeners.contains(listener)){
            listeners.add(listener);
        }
    }

    public void unSubscribeEPCRead(UHFReaderDelegate listener){
        if (listeners.contains(listener)){
            listeners.remove(listener);
        }
    }

    public void closing() {

        if (mReader != null) {
            stopReading();
            mReader.free();
            mReader=null;
            instance=null;
        }
    }

    public void toogleReading() {
        if(loopStarted){
            stopReading();
        }else{
            startReading();
        }
    }

    public void startReading(){
        if (mReader!=null){
            loopStarted=true;
            mReader.startInventoryTag(0,0);
            asyncTask.execute(10);
        }
    }

    public void stopReading(){
        if (mReader!=null && loopStarted){
            mReader.stopInventory();
            asyncTask.cancel(true);
            loopStarted=false;
        }
    }

    public void getEPCTagData(String EPCTag){

        new AsyncTask<String,Void,String>(){

            boolean needToRestartReader=false;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                needToRestartReader=loopStarted;
                if(needToRestartReader) {
                    stopReading();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String res= mReader.readData("00000000", RFIDWithUHF.BankEnum.RESERVED, 0, 4, params[0]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return  res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                for (UHFReaderDelegate listener : listeners) {
                    listener.tagEPCDataRead(s);
                }
                if(needToRestartReader) {
                    startReading();
                }
            }
        }.execute(EPCTag);
    }

    AsyncTask<Integer,String,Void> asyncTask = new AsyncTask<Integer, String, Void>() {

        String last=null;
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(last==null || !last.equalsIgnoreCase(values[0])){
                //Toast.makeText(mContext, values[0], Toast.LENGTH_SHORT).show();
                for (UHFReaderDelegate listener : listeners) {
                    listener.tagEPCRead(values[0]);
                }
                last=values[0];
            }
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            while (loopStarted){
                String[] res=mReader.readTagFromBuffer();
                if(res!=null && res.length >0){
                    String resItem=res[1];
                    if (!resItem.matches("[0]+")){
                        publishProgress(new String[]{resItem});
                    }
                }
                try {
                    Thread.sleep(10);
                }catch (Exception e){

                }
            }

            return null;
        }
    };

    private class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (!result) {
                Toast.makeText(mContext, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(mContext);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

    }
}
