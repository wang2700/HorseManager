package jw.horsemanager.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jw.horsemanager.R;

public class AddHorse extends AppCompatActivity {

    static final int datePickerId = 253;

    private EditText nameEditText, breedEditText;
    private Button birthdayBtn;
    private ImageView horsePicImageView;
    private Calendar calendar;
    private Date birthday;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_horse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_horse_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize all of the items on UI
        nameEditText = (EditText) findViewById(R.id.name_edit_text_add_horse);
        breedEditText = (EditText)findViewById(R.id.breed_edit_text_add_horse);
        birthdayBtn = (Button)findViewById(R.id.birthday_btn_add_horse);
        horsePicImageView = (ImageView) findViewById(R.id.horse_pic_image_view_add_horse);

        //get current date and assign to the button
        DateFormat currentDate = DateFormat.getDateInstance();
        birthdayBtn.setText(currentDate.format(new Date()));

        //date picker
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String monthString = new DateFormatSymbols().getMonths()[month];
                if (month == 9){
                    monthString = monthString.substring(0,4);
                } else {
                    monthString = monthString.substring(0,3);
                }
                String choseDate = monthString + " " + day + ", " + year;
                birthdayBtn.setText(choseDate);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    birthday = sdf.parse(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        };

        calendar = Calendar.getInstance();
        birthdayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddHorse.this, dateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //open camera when the camera icon is pressed
        horsePicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            horsePicImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_horse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //when save button is pressed
        if (id == R.id.save_action_add_horse){
            //TODO: add what happened when the save button is pressed
            return true;
        }

        //when back button is pressed
        if (id == android.R.id.home){
            //TODO: add what happened when the back is pressed
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    /**
//     * This method starts the intent for taking picture of the horse
//     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (photoFile != null){
//                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
               startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
    }

//    private File createImageFile() throws IOException {
//        String timeStamp = new SimpleDateFormat("yyyyHHdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp;
//        File stroageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,
//                ".jpg",
//                stroageDir);
//        mCurrentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
}
