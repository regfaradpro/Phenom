package be.bonana.phenom;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class AddChallenge extends AppCompatActivity implements View.OnClickListener {

    private String username = null;
    private String filename = null;
    private String title = null;
    private String description = null;
    private String endate;

    private TextView fromDateEtxt;
    private TextView toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TextView text = (TextView)findViewById(R.id.addChallenge_title);
        Toolbar toolbar_1 = (Toolbar)findViewById(R.id.addchallenge_title);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null)
        {
            Toast.makeText(this,"Helo",Toast.LENGTH_LONG);
            String j =(String) bundle.get("username");
            toolbar_1.setTitle(j);
            username =  j;


            if(bundle.get("filename")!=null){
                changeImageView();
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.okkButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filename!=null) {
                    addChallenge();
                }else{
                    Toast.makeText(AddChallenge.this,"Veuillez d'abord prendre une photo avant de confirmer le défi",Toast.LENGTH_LONG).show();;
                }
            }
        });

        ImageButton photoButton = (ImageButton) findViewById(R.id.imageView_addchallenge);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhoto();
            }
        });


        fromDateEtxt = (TextView) findViewById(R.id.enddate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (TextView) findViewById(R.id.enddate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
        setDateTimeField();


    }

    public void changeImageView(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            this.filename =(String) bundle.get("filename");

            ImageButton image = (ImageButton) findViewById(R.id.imageView_addchallenge);
            image.setImageURI(Uri.parse(filename));// To display selected image in image view
        }

    }
    public void addChallenge(){

        EditText title_input = (EditText) findViewById(R.id.title_input);
        EditText description_input = (EditText) findViewById(R.id.desciption_input);
        TextView date_input = (TextView) findViewById(R.id.enddate);
        this.title = title_input.getText().toString();
        this.description = description_input.getText().toString();
        this.endate = date_input.getText().toString();

        Intent intent = new Intent(this, Profil.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        ImageButton image = (ImageButton) findViewById(R.id.imageView_addchallenge);



        Bundle bundle = getIntent().getExtras();

        if(username!=null) {
            FileManager manager = new FileManager();
            String line;
            if(filename!= null) {
                line = username+";"+ title + ";" + description + ";" + endate + ";" + filename+ ";";
            }else{
                line = username+";"+title + ";" + description + ";" + endate;
            }
            manager.writeLine(line);

            intent.putExtra("filepath",(String) bundle.get("filepath"));
            intent.putExtra("username",username);
            intent.putExtra("enddate",endate);
            startActivity(intent);
        }


    }

    public void addPhoto(){
        Intent intent = new Intent(this, CameraActivity.class);
        Bundle bundle = getIntent().getExtras();
        TextView date = (TextView) findViewById(R.id.enddate);

   //         if (username != null && bundle != null) {
                intent.putExtra("username", username);

                intent.putExtra("filepath", (String) bundle.get("filepath"));
                intent.putExtra("endDate",date.getText());
                startActivity(intent);
         //   }



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.show();
        }
    }
}
