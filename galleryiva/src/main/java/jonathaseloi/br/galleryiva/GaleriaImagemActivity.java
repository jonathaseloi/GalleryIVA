package jonathaseloi.br.galleryiva;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import jonathaseloi.br.galleryiva.utils.GaleriaImagemUtils;


public class GaleriaImagemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria_imagem);

        String caminho = getIntent().getStringExtra("caminho");

        GaleriaImagemUtils imgDisplay = findViewById(R.id.action_infolinks_splash);
        //imgDisplay.setMaxZoom(2f);
        Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        imgDisplay.setImageBitmap(bitmap);
    }
}
