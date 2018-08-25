package com.example.elias.industrialads;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private InterstitialAd mInterstitialAd;
    private TextView text_point;
    private int point = 0;
    private AdListener adListener;
    private FirebaseAuth firebaseAuth;
    private Button btn_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_point = findViewById(R.id.text_point);

        firebaseAuth = FirebaseAuth.getInstance();



        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(MainActivity.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        btn = findViewById(R.id.btn_click);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                        Toast.makeText(MainActivity.this, "Ads loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.

                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                        point = point + 5;
                        text_point.setText("Point " + point);

                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when when the interstitial ad is closed.
                        Toast.makeText(MainActivity.this, "Close the ads", Toast.LENGTH_SHORT).show();
                        text_point.setText("point 00");
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });
        btn_logOut = findViewById(R.id.btn_logOut);
        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                goStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser;

        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null){
            goStart();
        }

    }

    private void goStart() {
        Intent startIntent  = new Intent(MainActivity.this,StartActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startIntent);
        finish();
    }
}
