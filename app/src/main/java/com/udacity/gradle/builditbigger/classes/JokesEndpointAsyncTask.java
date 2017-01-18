package com.udacity.gradle.builditbigger.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;

import com.example.beshoy.androidlib.JokeTeller;
import com.example.bisho.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by bisho on 15-Jan-17.
 */

public class JokesEndpointAsyncTask extends AsyncTask<Void,Void,String> {

    private static MyApi myApiService = null;
    private Context context;
    private AsyncTaskResponse taskResponse = null;
    private ProgressDialog progressDialog = null;

    public JokesEndpointAsyncTask(Context context){
        this.context = context;
        if(context != null)
            progressDialog = new ProgressDialog(context);
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
    protected  String doInBackground(Void... voids) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-155701.appspot.com/_ah/api/");
            // end options for devappserver
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(taskResponse != null)
            taskResponse.jokeAsyncTaskResponse(result);
        else {
            Intent intent = new Intent(context, JokeTeller.class);
            intent.putExtra("joke",result);
            context.startActivity(intent);
        }
        if(progressDialog != null)
            progressDialog.dismiss();

    }

}
