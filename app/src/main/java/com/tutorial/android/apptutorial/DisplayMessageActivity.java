package com.tutorial.android.apptutorial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class DisplayMessageActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALERY_RESULT = 2888;
    private ImageView imageView;
    public String photoFileName = "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        this.imageView = (ImageView) findViewById(R.id.Photo_ImageView);
/*        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);*/
    }

    public void clickPhoto(View view) {
        // Do something in response to button
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
        /*
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        */
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void selectPhoto(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALERY_RESULT);
    }

    /*
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case GALERY_RESULT: {
                    if (resultCode == RESULT_OK && null != data) {

                        Uri URI = data.getData();
                        String[] FILE = { MediaStore.Images.Media.DATA };


                        Cursor cursor = getContentResolver().query(URI,
                                FILE, null, null, null);

                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(FILE[0]);
                        String ImageDecode = cursor.getString(columnIndex);
                        cursor.close();

                        imageView.setImageBitmap(BitmapFactory
                                .decodeFile(ImageDecode));

                    }
                }
                break;
                case CAMERA_REQUEST: {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        /*Bitmap photo = (Bitmap) data.getExtras().get("data");
                        /*imageView.setImageBitmap(photo);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        Bitmap resizedPhoto = scaleToFitWidth(photo, 379);
                        imageView.setImageBitmap(resizedPhoto);*/
                        Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                        Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                        Bitmap resizedImage = scaleToFitWidth(takenImage, 379);
                        imageView.setImageBitmap(resizedImage);

                    }
                }
                break;
            }

        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MyCustomApp");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d("MyCustomApp", "failed to create directory");
            }

            // Return the file target for the photo based on filename
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            // wrap File object into a content provider
            // required for API >= 24
            // See https://guides.codepath.com/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
            return FileProvider.getUriForFile(DisplayMessageActivity.this, "com.codepath.fileprovider", file);
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    // scale and keep aspect ratio
    public static Bitmap scaleToFitWidth(Bitmap b, int width)
    {
        float factor = width / (float) b.getWidth();
        return Bitmap.createScaledBitmap(b, width, (int) (b.getHeight() * factor), true);
    }
}
