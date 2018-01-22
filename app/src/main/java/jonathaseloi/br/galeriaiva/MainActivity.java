package jonathaseloi.br.galeriaiva;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jonathaseloi.br.galleryiva.GalleryIVAView;

public class MainActivity extends AppCompatActivity {

    private GalleryIVAView galleryIVAView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        galleryIVAView = findViewById(R.id.gallery_view);

        galleryIVAView.toolbarTitle("GalleryIVA");
        galleryIVAView.tabVideos(false);
        galleryIVAView.tabGravity(TabLayout.GRAVITY_FILL);
        galleryIVAView.setPathImages("/Pictures/");
        galleryIVAView.setNumColumns(2);

        setSupportActionBar(galleryIVAView.getToolbar());
        galleryIVAView.setupViewPager(getSupportFragmentManager());
    }
}
