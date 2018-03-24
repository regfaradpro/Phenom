package be.bonana.phenom;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 100;
    ImageButton image;
    ImageView subImage;
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private String[] Descriptions;
    private File[] listFile;
    private String[] endDates;
    private String profilPicFileName = "/sdcard/Phenom/Profils/Profil.png" ;
    GridView grid;
    GridViewAdapter adapter;
    LinearLayout profil_layout;
    String currentUser="lironjerry";
    String currentUserProfil;
    GestureDetectorCompat gestureDetectorCompat;
    PhenomGestureListener gListiner;





    private File file =null;
    // JSON Node names
    private static final String TAG_USER_INFO = "userinfo";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_USER_CHALLENGE_NAME = "challenge_name";
    private static final String TAG_USER_CHALLENGE_DESCP = "challenge_description";
    private static final String TAG_USER_CHALLENGE_END = "endDate";
    // URL to get contacts JSON
    private static String url = "https://drive.google.com/file/d/0BwO6-Dt_inDiSGhtUlk1UGh0T0E/view?usp=sharing";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   navToMyPofil(currentUser);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                  //  mTextMessage.setText(R.string.title_notifications);
                    addChallenge();
                    return true;
            }
            return false;
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        TextView textView = ((TextView) findViewById (R.id.username));
        currentUser = (String) textView.getText();
        this.setTitle(currentUser);


        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            file = new File("/sdcard/Phenom/Profils");

            // Create a new folder if no folder named /sdcard/Phenom/Profils exist
            file.mkdirs();
        }

        image = (ImageButton) findViewById(R.id.imageView5);
        image.setImageURI(Uri.parse(profilPicFileName));// To display selected image in image view

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                createGriedView();
        }else{
            Toast.makeText(this,"Storage access needed",Toast.LENGTH_LONG).show();
        }



        profil_layout = (LinearLayout) findViewById(R.id.profil_layout);
        profil_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToMyPofil(currentUser);
            }
        });

        Date start = new Date();
        long millisStart = start.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = "09-09-2017";
        Date end = new Date();
        try {
             end = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long period = end.getTime() - start.getTime();

        new CountDownTimer(period, 1000) {

            public void onTick(long millisUntilFinished) {
               TextView mTextField = (TextView) findViewById(R.id.textView2);
                mTextField.setText("Temps restant: " + String.format("%tjdays %1$tHhours %1$tMminutes", millisUntilFinished));
            }

            public void onFinish() {
                TextView mTextField = (TextView) findViewById(R.id.textView2);
                mTextField.setText("Temps écoulé!");
            }
        }.start();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_main);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        gListiner = new PhenomGestureListener();
        gestureDetectorCompat = new GestureDetectorCompat(this, gListiner);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        if(gListiner.isLeftSwipe()){
            navToMyPofil(currentUser);
        }

        return super.onTouchEvent(event);
    }

    private void addChallenge(){
        Intent intent = new Intent(this, CameraActivity.class);
        TextView text = (TextView) findViewById(R.id.username);
        String message = (String) text.getText();
        intent.putExtra("username", message);
        profilPicFileName = "/sdcard/Phenom/Profils/Profil.png";
        intent.putExtra("filepath",profilPicFileName);
        startActivity(intent);
    }

    private void navToMyPofil(String user){
        Intent intent = new Intent(this, Profil.class);
        Bundle bundle = getIntent().getExtras();
        TextView editText = (TextView) findViewById(R.id.username);
        profilPicFileName = "/sdcard/Phenom/Profils/Profil.png";
        intent.putExtra("username",(String)editText.getText());
        intent.putExtra("filepath", profilPicFileName);
        startActivity(intent);

    }

    public void pickAImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);

                        File source = new File(selectedImage.getPath());
                        File destination = new File(Environment.getExternalStorageDirectory(), "/Phenom/Profils/Profil.png");
                        if (source.exists()) {
                            FileChannel src = new FileInputStream(source).getChannel();
                            FileChannel dst = new FileOutputStream(destination).getChannel();
                            dst.transferFrom(src, 0, src.size());       // copy the first file to second.....
                            src.close();
                            dst.close();
                            Toast.makeText(getApplicationContext(), "La photo de profil a été chnagée", Toast.LENGTH_LONG).show();

                            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        }
                        image.setImageURI(selectedImage);// To display selected image in image view
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    private void createGriedView(){

        if (file.isDirectory()) {
            listFile = file.listFiles();
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];
            endDates = new String[listFile.length];

            // Create a String array for description
            Descriptions = new String[listFile.length];
            String profilPicFile;

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
            //    FilePathStrings[i] = profilPicFile;
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
                Descriptions[i] = "Descriptions from " +listFile[i].getName();
                endDates[i] = "09/09/2017";
            }
        }

        // Locate the GridView in gridview_main.xml
        grid = (GridView) findViewById(R.id.gridview);
        // Pass String arrays to LazyAdapter Class
        adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings, Descriptions,endDates,true);
        // Set the LazyAdapter to the GridView
        grid.setAdapter(adapter);

        // Capture gridview item click
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, Profil.class);
                intent.putExtra("username", FileNameStrings[position]);
                //Pass String arrays FilePathStrings
                intent.putExtra("filepath", FilePathStrings[position]);
                intent.putExtra("enddate",endDates[position]);
                startActivity(intent);
            }

        });
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
        View v = (View) findViewById(R.id.gridview);
        Snackbar.make(v, "@Author : Liron Bonana", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        return super.onOptionsItemSelected(item);
    }

}
