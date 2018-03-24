package com.example.faradre.megagame;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText txtNumber;
    private Button btnCompare;
    private TextView lblResult;
    private ProgressBar pgbScore;
    private TextView lblOutput;

    private int searchedValue;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumber = (EditText) findViewById(R.id.txtNumber);
        btnCompare = (Button) findViewById(R.id.btnCompare);
        lblResult = (TextView) findViewById(R.id.lblResult);
        pgbScore = (ProgressBar) findViewById(R.id.pgbScore);
        lblOutput = (TextView) findViewById(R.id.txtHistory);

        btnCompare.setOnClickListener(btnCompareListener);

        init();;
    }

    private void init() {
        score= 0;
        searchedValue = (int) (Math.random() * 100);
        Log.i("DEBUG", "Valeur cherchée : " +searchedValue);

        txtNumber.setText("");
        pgbScore.setProgress(score);
        lblResult.setText("");
        lblOutput.setText("");

        txtNumber.requestFocus();
    }

    private void congrats() {
        lblResult.setText(R.string.strCongrats);
        AlertDialog retryAlert = new AlertDialog.Builder(this).create();
        retryAlert.setTitle(R.string.app_name);
        retryAlert.setMessage(getString(R.string.strMessage, score));

        retryAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.strYes), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                init();
            }
        });

        retryAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.strNo), new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        retryAlert.show();
    }

    private View.OnClickListener btnCompareListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("DEBUG", "BOUTON CLIQUE");
            String txtNumberZone = txtNumber.getText().toString();
            if (txtNumber.equals("")) return;

            lblOutput.append(txtNumberZone +"\r\n");
            pgbScore.incrementProgressBy(1);
            score ++;

            int enteredValue = Integer.parseInt(txtNumberZone);
            if (enteredValue == searchedValue) {
                congrats();
            } else if (enteredValue < searchedValue) {
                lblResult.setText(R.string.strGreater);
            } else {
                lblResult.setText(R.string.strLower);
            }
        }
    };


}















