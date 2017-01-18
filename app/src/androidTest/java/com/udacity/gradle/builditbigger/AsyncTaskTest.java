package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.util.Pair;

import com.udacity.gradle.builditbigger.classes.AsyncTaskResponse;
import com.udacity.gradle.builditbigger.classes.JokesEndpointAsyncTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created by bisho on 18-Jan-17.
 */
public class AsyncTaskTest implements AsyncTaskResponse {

    JokesEndpointAsyncTask asyncTask;
    CountDownLatch signal;

    @Before
    public void setUp() throws Exception {
        signal = new CountDownLatch(1);
        asyncTask = new JokesEndpointAsyncTask(null);
        asyncTask.taskResponse = this;
        asyncTask.execute("here is a test joke ###");
        signal.await();
    }

    @After
    public void tearDown() throws Exception {
        signal.countDown();
    }


    @Override
    public void jokeAsyncTaskResponse(String joke) {
        signal.countDown();
        assertNotNull(joke);
    }
}