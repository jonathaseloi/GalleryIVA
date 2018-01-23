package jonathaseloi.br.galleryiva;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

import jonathaseloi.br.galleryiva.utils.GaleriaImagemUtils;


public class GaleriaImagemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String caminho;
    private String finalPath;

    private Bitmap bitmap;
    private GaleriaImagemUtils imgDisplay;

    private int CROP_PIC_REQUEST_CODE = 11;

    private MenuItem save;

    private Bitmap resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_imagem);

        toolbar = findViewById(R.id.toolbar_galeria_activity);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        caminho = getIntent().getStringExtra("caminho");
        finalPath = getIntent().getStringExtra("path");

        imgDisplay = findViewById(R.id.action_infolinks_splash);
        //imgDisplay.setMaxZoom(2f);
        bitmap = BitmapFactory.decodeFile(caminho);
        imgDisplay.setImageBitmap(bitmap);

        //Set image in all screen
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setAttributes(attributes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_file, menu);

        save = menu.findItem(R.id.menu_save);
        save.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.menu_compartilhar) {
            Log.e("compartilhar", "COMPARTILHAR");

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+caminho));
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Send To"));

            return true;
        } else if (i == R.id.menu_crop) {
            CropImage.activity(Uri.fromFile(new File(caminho)))
                    .start(this);
            return true;
        } else if (i == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (i == R.id.menu_save) {
            if (resultUri != null) {
                SaveImage(resultUri);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri uriImage = result.getUri();
                resultUri = getBitmapFromUri(this, uriImage);
                imgDisplay.setImageBitmap(resultUri);

                save.setVisible(true);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + finalPath.replace("%", ""));
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpeg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            galleryAddPic(file);

            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Can`t Save", Toast.LENGTH_SHORT).show();
        }
    }

    private void galleryAddPic(File image) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(image);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
