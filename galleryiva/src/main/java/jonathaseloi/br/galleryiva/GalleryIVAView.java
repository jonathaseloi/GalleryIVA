package jonathaseloi.br.galleryiva;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import jonathaseloi.br.galleryiva.adapters.ViewPagerAdapter;

/**
 * Created by Jonathas Eloi on 22/01/18.
 */

public class GalleryIVAView extends DrawerLayout {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    private int tabGravity = TabLayout.GRAVITY_FILL;
    private int tabMode = TabLayout.MODE_FIXED;

    private Boolean images = true;
    private Boolean videos = true;
    private Boolean files = true;

    private String tabImagesTitle = "Images";
    private String tabVideosTitle = "Videos";
    private String tabFilesTitle = "Files";

    private String[] pathImages = {"%/Galeria/images%"};
    private String[] pathVideos = {"%/Galeria/videos%"};
    private String[] pathFiles = {"%/Galeria/files%"};

    private int numColumns = 2;
    private int numColumnsImagens = numColumns;
    private int numColumnsVideos = numColumns;
    private int numColumnsFiles = numColumns;

    public GalleryIVAView(Context context) {
        super(context);
        inflate(context, R.layout.activity_gallery, this);
        init();
    }

    public GalleryIVAView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.activity_gallery, this);
        init();
    }

    public GalleryIVAView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.activity_gallery, this);
        init();
    }

    //Init View
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_galeria);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_galeria);
        appBarLayout.setExpanded(true, true);

        viewPager = (ViewPager) findViewById(R.id.viewpager_galeria);

        tabLayout = (TabLayout) findViewById(R.id.tabs_galeria);
        tabGravity(tabGravity);
        tabMode(tabMode);
    }

    //Edit Toolbar Title
    public void toolbarTitle(String name) {
        if (toolbar != null)
            toolbar.setTitle(name);
    }

    //Tab Configs
    public void tabGravity(int gravity) {
        if (tabLayout != null)
            tabLayout.setTabGravity(gravity);
    }

    public void tabMode(int mode) {
        if (tabLayout != null)
            tabLayout.setTabMode(mode);
    }

    public void setupViewPager(FragmentManager fragmentManager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager);

        if (images)
            adapter.addFrag(new GaleriaFragment().newInstance("Imagens", pathImages, R.layout.fragment_imagens, R.id.recycler_view_imagens, numColumnsImagens), tabImagesTitle);

        if (videos)
            adapter.addFrag(new GaleriaFragment().newInstance("Videos", pathVideos, R.layout.fragment_videos, R.id.recycler_view_videos, numColumnsVideos), tabVideosTitle);

        if (files)
            adapter.addFrag(new GaleriaFragment().newInstance("Outros", pathFiles, R.layout.fragment_outros, R.id.recycler_view_outros, numColumnsFiles), tabFilesTitle);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    public void tabImages(Boolean cond) {
        this.images = cond;
    }

    public void tabImagesTitle(String tabImagesTitle) {
        this.tabImagesTitle = tabImagesTitle;
    }

    public void tabVideos(Boolean cond) {
        this.videos = cond;
    }

    public void tabVideosTitle(String tabVideosTitle) {
        this.tabVideosTitle = tabVideosTitle;
    }

    public void tabFiles(Boolean cond) {
        this.files = cond;
    }

    public void tabFilesTitle(String tabFilesTitle) {
        this.tabFilesTitle = tabFilesTitle;
    }

    //Config Paths
    public void setPathImages(String path) {
        pathImages = new String[] {"%" + path + "%"};
    }

    public void setPathVideos(String path) {
        pathVideos = new String[] {"%" + path + "%"};
    }

    public void setPathFiles(String path) {
        pathFiles = new String[] {"%" + path + "%"};
    }

    //Config number of Columns
    public void setNumColumnsImagens(int numColumns) {
        this.numColumnsImagens = numColumns;
    }

    public void setNumColumnsVideos(int numColumns) {
        this.numColumnsVideos = numColumns;
    }

    public void setNumColumnsFiles(int numColumns) {
        this.numColumnsFiles = numColumns;
    }

    public void setNumColumnsAll(int numColumns) {
        this.numColumnsVideos = numColumns;
        this.numColumnsImagens = numColumns;
        this.numColumnsFiles = numColumns;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }
}
