package com.agung.android.moviedb.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.agung.android.moviedb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LauncherActivity extends AppCompatActivity {

    @BindView(R.id.pb_splash)
    ProgressBar mProgressSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        ButterKnife.bind(this);

        mProgressSplash.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor
                (getApplicationContext(), R.color.colorTextWhite), PorterDuff.Mode.SRC_IN );
        new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                toHomeActivity();
                            }
                        }.start();
    }

    public void toHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
