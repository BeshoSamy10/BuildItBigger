package com.udacity.gradle.builditbigger.free;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.classes.JokesEndpointAsyncTask;

public class MainActivity extends AppCompatActivity {

    AdView mAdView;
    JokesEndpointAsyncTask jokesAsyncTask;
    InterstitialAd mInterstitialAd;
    Button tellJoke_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tellJoke_button = (Button) findViewById(R.id.tellJoke_button);

        jokesAsyncTask = new JokesEndpointAsyncTask(this);

        mAdView = (AdView) findViewById(R.id.adView);
        mInterstitialAd = new InterstitialAd(this);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                tellJoke();
            }
        });

        tellJoke_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jokesAsyncTask = null;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    tellJoke();
                }
            }
        });

        requestNewInterstitial();

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
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

    public void tellJoke() {
        if(jokesAsyncTask == null){
            jokesAsyncTask = new JokesEndpointAsyncTask(this);
        }
        jokesAsyncTask.execute();
    }

}