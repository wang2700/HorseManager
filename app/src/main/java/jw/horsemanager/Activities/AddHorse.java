package jw.horsemanager.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jw.horsemanager.Objects.Horse;
import jw.horsemanager.Objects.SerializedBitmap;
import jw.horsemanager.R;

public class AddHorse extends AppCompatActivity {

    static final int EDIT = 1;
    static final int ADD = 2;
    static final int datePickerId = 253;

    private EditText nameEditText, breedEditText;
    private Button birthdayBtn;
    private ImageView horsePicImageView;
    private Calendar calendar;
    private Date birthday;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
    private int editMode;
    private Bitmap horsePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_horse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_horse_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //receive data from activity
        Bundle extras = getIntent().getExtras();
        editMode = (int) extras.get("mode");

        //initialize all of the items on UI
        nameEditText = (EditText) findViewById(R.id.name_edit_text_add_horse);
        breedEditText = (EditText) findViewById(R.id.breed_edit_text_add_horse);
        birthdayBtn = (Button) findViewById(R.id.birthday_btn_add_horse);
        horsePicImageView = (ImageView) findViewById(R.id.horse_pic_image_view_add_horse);

        //get current date and assign to the button
        DateFormat currentDate = DateFormat.getDateInstance();
        birthdayBtn.setText(currentDate.format(new Date()));

        //date picker
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String monthString = new DateFormatSymbols().getMonths()[month];
                if (month == 9) {
                    monthString = monthString.substring(0, 4);
                } else {
                    monthString = monthString.substring(0, 3);
                }
                String choseDate = monthString + " " + day + ", " + year;
                birthdayBtn.setText(choseDate);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    birthday = sdf.parse(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
                } catch (ParseException e) {
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
            horsePic = (Bitmap) extras.get("data");
            horsePicImageView.setImageBitmap(horsePic);
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
        if (id == R.id.save_action_add_horse) {
            if (editMode == ADD) {
                String name = String.valueOf(nameEditText.getText());
                String breed = String.valueOf(breedEditText.getText());
                SerializedBitmap horsePicSerialized = new SerializedBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                horsePic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                horsePicSerialized.bitmapImage = stream.toByteArray();
                Horse newHorse = new Horse(name, horsePicSerialized, birthday, breed, null);
                HomeScreen.horseArrayList.add(newHorse);
                try {
                    FileOutputStream horseListFos = new FileOutputStream(HomeScreen.horseListFilePath);
                    ObjectOutputStream horseListOos = new ObjectOutputStream(horseListFos);
                    final long initialPosition = horseListFos.getChannel().position();
                    horseListFos.getChannel().position(initialPosition);
                    horseListOos.reset();
                    horseListOos.writeObject(HomeScreen.horseArrayList);
                    horseListOos.flush();
                    horseListFos.close();
                    stream.close();
                    horseListOos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else if (editMode == EDIT) {
                // TODO: add actions when the user is edit the horse profile
            }
            finish();
        }

        //when back button is pressed
        if (id == android.R.id.home) {
            /*
              TODO: add what happened when the back is pressed
              if any field is changed show dialog for confirm returning to previous screen
             */

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
        //TODO: maybe need to add full image
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
