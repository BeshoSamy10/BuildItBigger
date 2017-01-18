package com.udacity.gradle.builditbigger.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.bisho.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by bisho on 15-Jan-17.
 */

public class JokesEndpointAsyncTask extends AsyncTask<String,Void,String> {

    private static MyApi myApiService = null;
    private Context context;
    public AsyncTaskResponse taskResponse = null;
    ProgressDialog progressDialog = null;

    public JokesEndpointAsyncTask(Activity activity){
        if(activity != null)
            progressDialog = new ProgressDialog(activity);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressDialog != null){
            progressDialog.setMessage("loading...");
            progressDialog.show();
        }
    }

    @Override
    protected  String doInBackground(String... strings) {

        String joke = strings[0];
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-155701.appspot.com/_ah/api/");
            // end options for devappserver
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke(joke).execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        taskResponse.jokeAsyncTaskResponse(result);
        if(progressDialog != null)
            progressDialog.dismiss();
    }

}
