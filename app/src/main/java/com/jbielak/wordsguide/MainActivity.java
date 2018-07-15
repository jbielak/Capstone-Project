package com.jbielak.wordsguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jbielak.wordsguide.network.MusixmatchApiUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sign)
    protected void signIn(){
        if (MusixmatchApiUtils.isOnline(this)) {
            Intent homeActivityIntent = new Intent(this, HomeActivity.class);
            startActivity(homeActivityIntent);
        } else {
            Toast.makeText(this, getString(R.string.app_offline), Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
