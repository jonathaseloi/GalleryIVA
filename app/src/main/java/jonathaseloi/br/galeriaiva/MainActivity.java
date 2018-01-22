package jonathaseloi.br.galeriaiva;

import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jonathaseloi.br.galleryiva.GalleryActivityView;
import jonathaseloi.br.galleryiva.StringUtils;

public class MainActivity extends AppCompatActivity {

    private GalleryActivityView galleryActivityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        galleryActivityView = findViewById(R.id.gallery_view);

        galleryActivityView.toolbarTitle("Galeria IVA");
        galleryActivityView.tabVideos(false);
        galleryActivityView.tabGravity(TabLayout.GRAVITY_FILL);
        galleryActivityView.setPathImages("/Pictures/");

        setSupportActionBar(galleryActivityView.getToolbar());
        galleryActivityView.setupViewPager(getSupportFragmentManager());
    }
}
