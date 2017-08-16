package nt.esraakhaled.com.rfid.Controllers.Sensors;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rscja.deviceapi.RFIDWithUHF;

/**
 * Created by OSX on 8/15/17.
 */

public class UHFReader {

    private RFIDWithUHF mReader=null;
    private Context mContext;
    private boolean loopStarted=false;
    static private UHFReader instance=null;


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
    public static UHFReader getInstange(Context context){
        if (instance == null ){
            instance = new UHFReader(context);
        }
        return instance;
    }
    public class InitTask extends AsyncTask<String, Integer, Boolean> {
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


    public void closing() {

        if (mReader != null) {
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

    AsyncTask<Integer,String,Void> asyncTask = new AsyncTask<Integer, String, Void>() {

        String last=null;
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(last==null || !last.equalsIgnoreCase(values[0])){
                Toast.makeText(mContext, values[0], Toast.LENGTH_SHORT).show();
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
}
