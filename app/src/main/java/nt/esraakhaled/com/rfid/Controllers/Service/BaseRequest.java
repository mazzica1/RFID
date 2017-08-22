package nt.esraakhaled.com.rfid.Controllers.Service;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by OSX on 8/15/17.
 */

public class BaseRequest {
    static RequestQueue mRequestQueue = null;
    static ProgressDialog pDialog=null;
    static int subscribers=0;

    private static void showDialog(Context context){
        if(pDialog == null ) pDialog = new ProgressDialog(context);
        if(!pDialog.isShowing()){
            pDialog.setMessage("Loading...");
            pDialog.show();
        }
        subscribers++;
    }

    private static void hideDialog(){
        subscribers--;
        if (subscribers ==0) {
            pDialog.hide();
        }
    }

    private static <T> void doRequest(Context context, String url, final Map<String, String> params, final Type type, final RequestCallBack<T> requestCallBack, int method){
        if (mRequestQueue == null) mRequestQueue=Volley.newRequestQueue(context);

        showDialog(context);

        if (method == Request.Method.GET && params!=null){
            url+="?";
            for(Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                url+=key+"="+value+"&";

            }
        }

        StringRequest stringRequest = new StringRequest(method,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (requestCallBack!=null){
                            try {

                                requestCallBack.onSuccess((T) new Gson().fromJson(response, type));
                            }catch(Exception e){
                                requestCallBack.onFailure(e);
                            }
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> res=new HashMap<>();
                return res;
            }
        };

        // Adding request to request queue
        mRequestQueue.add(stringRequest);
    }

    public static <T> void doPostRequestJson(Context context, String url, final JsonObject params, final Type type, final RequestCallBack<T> requestCallBack){
        if (mRequestQueue == null) mRequestQueue=Volley.newRequestQueue(context);

        showDialog(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (requestCallBack!=null){
                            try {

                                requestCallBack.onSuccess((T) new Gson().fromJson(response, type));
                            }catch(Exception e){
                                requestCallBack.onFailure(e);
                            }
                        }
                        hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        }){
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return params== null ? null : params.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;

            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> res=new HashMap<>();
                return res;
            }
        };

        // Adding request to request queue
        mRequestQueue.add(stringRequest);
    }


    public  static <T> void doGet(Context context, String url, final Map<String, String> params,Type type, RequestCallBack<T> requestCallBack){

        doRequest(context,url,params,type,requestCallBack,Request.Method.GET);

    }

    public  static <T> void doPost(Context context,String url, final Map<String, String> params,Type type, RequestCallBack<T> requestCallBack){
        doRequest(context,url,params,type,requestCallBack,Request.Method.POST);
    }

}
