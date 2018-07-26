package com.jbielak.wordsguide;

import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import butterknife.ButterKnife;

public class ChartsActivity extends AppCompatActivity {

    private String currentCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        ButterKnife.bind(this);

        currentCountryCode = getCountryCode();

    }

    private static String getCountryCode() {
        String localeCountry;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            localeCountry = Resources.getSystem().getConfiguration()
                    .getLocales().get(0).getCountry().toLowerCase();
        } else {
            localeCountry = Resources.getSystem().getConfiguration()
                    .locale.getCountry().toLowerCase();
        }
        return localeCountry;
    }
}
