package com.udacity.gradle.builditbigger.paid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.beshoy.androidlib.JokeTeller;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.classes.AsyncTaskResponse;
import com.udacity.gradle.builditbigger.classes.JokesEndpointAsyncTask;

public class MainActivity extends AppCompatActivity implements AsyncTaskResponse{

    JokesEndpointAsyncTask jokesAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokesAsyncTask = new JokesEndpointAsyncTask(this);
        jokesAsyncTask.taskResponse = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if(jokesAsyncTask == null){
            jokesAsyncTask = new JokesEndpointAsyncTask(this);
            jokesAsyncTask.taskResponse = this;
        }
        jokesAsyncTask.execute("here is a paid joke $_$");
    }


    @Override
    public void jokeAsyncTaskResponse(String joke) {
        Intent intent = new Intent(this, JokeTeller.class);
        intent.putExtra("joke",joke);
        startActivity(intent);
        jokesAsyncTask = null;
    }
}
