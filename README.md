# GalleryIVA
Gallery with display images, videos and files in separated fragments.

# Usage

1. MainActivity.class
```
public class MainActivity extends AppCompatActivity {

    private GalleryIVAView galleryIVAView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        galleryIVAView = findViewById(R.id.gallery_view);
        setSupportActionBar(galleryIVAView.getToolbar());
        galleryIVAView.setupViewPager(getSupportFragmentManager());
        ...
    }
...
}
```

2. Layout
```
<jonathaseloi.br.galleryiva.GalleryIVAView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/gallery_view" />  
```

# Installation
Gradle:

1. Add it in your root build.gradle at the end of repositories:
```
  maven { url 'https://www.jitpack.io' }
```
2. Add the dependency
```
  compile 'com.github.jonathaseloi:galleryIVA:v1.0.1'
```

Maven:

1. Add the Repository

```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://www.jitpack.io</url>
  </repository>
</repositories>
```
2. Add the dependency
```
<dependency>
    <groupId>com.github.jonathaseloi</groupId>
    <artifactId>galleryIVA</artifactId>
    <version>v1.0.1</version>
</dependency>
```

# Libraries
[Picasso](http://square.github.io/picasso/) - For remote image loading
