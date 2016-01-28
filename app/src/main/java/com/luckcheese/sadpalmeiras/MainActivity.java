package com.luckcheese.sadpalmeiras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.refresh).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupView();
    }

    private void setupView() {
        ((TextView) findViewById(R.id.titlesCount)).setText("0");
        ((TextView) findViewById(R.id.date)).setText(getDateFormatted());
    }

    private String getDateFormatted() {
        StringBuilder dateString = new StringBuilder();

        Date currentTime = new Date();
        dateString.append(DateFormat.getDateInstance(DateFormat.SHORT).format(currentTime));
        dateString.append(" ");
        dateString.append(getString(R.string.date_preposition));
        dateString.append(" ");
        dateString.append(DateFormat.getTimeInstance(DateFormat.SHORT).format(currentTime));
        dateString.append(",");

        return dateString.toString();
    }

    // ----- View.OnClickListener ---------------------------------------------

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.refresh) {
            setupView();
        }
    }
}
