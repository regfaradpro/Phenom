package be.bonana.phenom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    TextView text;
    ImageView imageview;
    QuickContactBadge contactBadge;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve data from MainActivity on GridView item click
        Intent i = getIntent();

        // Get the position
        int position = i.getExtras().getInt("position");
        String profilPic = i.getExtras().getString("profilPicPath");


        // Get String arrays FilePathStrings
        String[] filepath = i.getStringArrayExtra("filepath");

        // Get String arrays FileNameStrings
        String[] titles = i.getStringArrayExtra("titles");
        String[] descriptions = i.getStringArrayExtra("descriptions");
        String[] enddates = i.getStringArrayExtra("enddates");

        text = (TextView) findViewById(R.id.CTitle);
        text.setText(titles[position]);
        // Locate the TextView in view_image.xml
     //   text = (TextView) findViewById(R.id.CDescription);

        // Load the text into the TextView followed by the position
     //   text.setText(titles[position]);

        text = (TextView) findViewById(R.id.CDescription);
        // Load the text into the TextView followed by the position
        text.setText(descriptions[position]);

        // Locate the ImageView in view_image.xml
        imageview = (ImageView) findViewById(R.id.challenge_image);

        // Decode the filepath with BitmapFactory followed by the position
        Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);

        // Set the decoded bitmap into ImageView
        imageview.setImageBitmap(bmp);


        imageview = (ImageView) findViewById(R.id.user_profil);

        // Decode the filepath with BitmapFactory followed by the position
        bmp = BitmapFactory.decodeFile(profilPic);

        // Set the decoded bitmap into ImageView
        imageview.setImageBitmap(bmp);

        text = (TextView) findViewById(R.id.timeLeft);

        Date start = new Date();
        long millisStart = start.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = i.getExtras().getString("endate");
        Date end = new Date();
        try {
            end = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long period = end.getTime() - start.getTime();

        new CountDownTimer(period, 1000) {

            public void onTick(long millisUntilFinished) {
             //   TextView mTextField = (TextView) findViewById(R.id.textView2);
                text.setText("Temps restant: " + String.format("%tjdays %1$tHhours %1$tMminutes", millisUntilFinished));
            }

            public void onFinish() {
               // TextView mTextField = (TextView) findViewById(R.id.textView2);
                text.setText("Temps écoulé!");
            }
        }.start();


    }
}
