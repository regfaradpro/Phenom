package be.bonana.phenom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Profil extends AppCompatActivity {

    private GridViewAdapter adapter;
    private String[] titles;
    private String[] descriptions;
    private String[] filenames;
    private String[] endDates;
    private String username = "UserNotFound";
    private String pofilPicPath;
    private String endDate;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    navBackToHome();
                    return true;
                case R.id.navigation_notifications:
                    //  mTextMessage.setText(R.string.title_notifications);
                    addChallenge();
                    return true;
            }
            return false;
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView userNameTB = (TextView) findViewById(R.id.username);
        // Get the widgets reference from XML layout
        GridView gv = (GridView) findViewById(R.id.grid);
        FileManager manager = new FileManager();
        Bundle bundle = getIntent().getExtras();


       if(bundle!=null) {
           this.username = (String) bundle.get("username");
           userNameTB.setText(this.username);
           this.setTitle(username);

           String[] stringList = manager.readFile();
           Boolean allTablesFilled = false;
           if (stringList != null)
               allTablesFilled = fillTables(stringList);

           if (allTablesFilled) {

               // Pass String arrays to LazyAdapter Class
               adapter = new GridViewAdapter(this, filenames, titles, descriptions, endDates, false);
               // Set the LazyAdapter to the GridView
               gv.setAdapter(adapter);
           }
       }

        ImageView image = (ImageView) findViewById(R.id.profil_pic);
        this.pofilPicPath = (String) bundle.get("filepath");
        image.setImageURI(Uri.parse(pofilPicPath));


        // Capture gridview item click
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

               // Intent i = new Intent(Profil.this, ViewImage.class);
                Intent i = new Intent(Profil.this, DetailActivity.class);
                // Pass String arrays FilePathStrings
                i.putExtra("filepath", filenames);
                // Pass String arrays FileNameStrings
                i.putExtra("titles", titles);
                i.putExtra("descriptions", descriptions);
                // Pass click position
                i.putExtra("position", position);
                i.putExtra("profilPicPath",pofilPicPath);
                i.putExtra("enddates",endDates);
                startActivity(i);

            }

        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_profil);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public Boolean fillTables(String[] data) {
        List<String> tList = new ArrayList<String>();
        List<String> dList = new ArrayList<String>();
        List<String> fList = new ArrayList<String>();
        List<String> dateList = new ArrayList<String>();
        Boolean allTableFilled = false;



       for (int i = 0; i < data.length; i++) {
           String line = data[i];


           String user = line.substring(0,line.indexOf(';'));
           line = line.substring(line.indexOf(';')+1);

           if(user.equals(this.username)) {

               String title = line.substring(0, line.indexOf(';'));
               line = line.substring(line.indexOf(';') + 1);
               tList.add(title);

               String description = line.substring(0, line.indexOf(';'));
               line = line.substring(line.indexOf(';') + 1);
               dList.add(description);

               String end = line.substring(0, line.indexOf(';'));
               line = line.substring(line.indexOf(';') + 1);
               dateList.add(end);

               String filepath = line.substring(0);
               line = line.substring(line.indexOf(';') + 1);
               fList.add(filepath);
            }


           }

        //convert list to tables
        this.titles = new String[tList.size()];
        this.titles = tList.toArray(this.titles);
        this.descriptions = new String[dList.size()];
        this.descriptions = dList.toArray(this.descriptions);
        this.filenames = new String[fList.size()];
        this.filenames = fList.toArray(this.filenames);
        this.endDates = new String[dateList.size()];
        this.endDates = dateList.toArray(this.endDates);

        if(this.titles!=null && this.descriptions!=null && this.filenames!=null )
            allTableFilled = true;

        return allTableFilled;

    }

    public void addChallenge(){
        Intent intent = new Intent(this, CameraActivity.class);
        Bundle bundle = getIntent().getExtras();

        if(username!=null && bundle !=null) {
            intent.putExtra("username", this.username);
            intent.putExtra("filepath",(String) bundle.get("filepath"));
            intent.putExtra("enddate",(String) bundle.get("enddate"));
            startActivity(intent);
        }

 /*       Intent intent = new Intent(this, CameraActivity.class);
        TextView text = (TextView) findViewById(R.id.username);
        String message = (String) text.getText();
        intent.putExtra("username", message);
        profilPicFileName = "/sdcard/Phenom/Profils/Profil.png";
        intent.putExtra("filepath",profilPicFileName);
        startActivity(intent);*/
    }

    public void navBackToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = getIntent().getExtras();

        intent.putExtra("username", this.username);
        intent.putExtra("filepath",(String) bundle.get("filepath"));
        intent.putExtra("enddate",(String) bundle.get("enddate"));
        startActivity(intent);
    }
}
