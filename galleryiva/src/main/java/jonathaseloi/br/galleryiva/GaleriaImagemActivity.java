package jonathaseloi.br.galleryiva;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import jonathaseloi.br.galleryiva.utils.GaleriaImagemUtils;


public class GaleriaImagemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_imagem);

        toolbar = findViewById(R.id.toolbar_galeria_activity);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        String caminho = getIntent().getStringExtra("caminho");

        GaleriaImagemUtils imgDisplay = findViewById(R.id.action_infolinks_splash);
        //imgDisplay.setMaxZoom(2f);
        Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        imgDisplay.setImageBitmap(bitmap);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setAttributes(attributes);

        //FIXME: Add share, edit, etc
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getSupportActionBar().hide();

//        imgDisplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (getSupportActionBar().isShowing()) {
//                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    getSupportActionBar().hide();
//                } else {
//
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                    getSupportActionBar().show();
//                }
//            }
//        });

    }
}
