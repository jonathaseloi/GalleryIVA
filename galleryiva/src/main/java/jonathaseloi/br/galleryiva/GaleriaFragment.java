package jonathaseloi.br.galleryiva;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.io.File;

import jonathaseloi.br.galleryiva.adapters.ImagensAdapter;
import jonathaseloi.br.galleryiva.adapters.TextoAdapter;
import jonathaseloi.br.galleryiva.adapters.VideosAdapter;
import jonathaseloi.br.galleryiva.utils.SpaceColUtils;

public class GaleriaFragment extends Fragment implements ImagensAdapter.ItemCaminho,
        VideosAdapter.ItemCaminho, TextoAdapter.ItemCaminho{

    ImagensAdapter imagensAdapter;
    VideosAdapter videosAdapter;
    TextoAdapter textoAdapter;

    MenuItem compartilhar;
    MenuItem rotate;

    RecyclerView recyclerView;
    String type;
    String[] path;
    int layout, recycleview, numColumns;

    private String caminho;

    public static GaleriaFragment newInstance(String type, String[] path, int layout, int recycleview, int numColumns) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putStringArray("path", path);
        bundle.putInt("layout", layout);
        bundle.putInt("recycleview", recycleview);
        bundle.putInt("numColumns", numColumns);

        GaleriaFragment fragment = new GaleriaFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            type = bundle.getString("type");
            path = bundle.getStringArray("path");
            layout = bundle.getInt("layout");
            recycleview = bundle.getInt("recycleview");
            numColumns = bundle.getInt("numColumns");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        imagensAdapter.setItemCaminho(this);
        textoAdapter.setItemCaminho(this);
        videosAdapter.setItemCaminho(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readBundle(getArguments());
        imagensAdapter = new ImagensAdapter();
        textoAdapter = new TextoAdapter();
        videosAdapter = new VideosAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(recycleview);

//        int mNoOfColumns = calculateNoOfColumns(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numColumns);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpaceColUtils(2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initAdapters();
    }

    private void initAdapters() {
        switch (type){
            case "Imagens":
                imagensAdapter.clear();
                imagensAdapter.redImages(numColumns);
                imagensAdapter.setPath(path[0]);
                recyclerView.setAdapter(imagensAdapter);

                if (!permissaoAcessoArquivo(getActivity())) {
                    requestPermission(getActivity(), 0);
                }
                break;

            case "Videos":
                videosAdapter.redImages(numColumns);
                recyclerView.setAdapter(videosAdapter);
                break;

            default:
                textoAdapter.redImages(numColumns);
                recyclerView.setAdapter(textoAdapter);
                break;
        }

        if (permissaoAcessoArquivo(getActivity())) {
            String pathDir = path[0].replace("%", "");
            createDirIfNotExists(pathDir);
            searchfilestype(path, type, imagensAdapter, videosAdapter, textoAdapter);
        }

    }

    //Criar diretorios se nao existirem, ou se foram deletados
    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating folder + " + path);
                ret = false;
            }
        }
        return ret;
    }

    //Permissoes
    public static void requestPermission(final Activity activity, final int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            final String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0]) || ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[1])) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            }
        }
    }
    //Permissoes
    public static boolean permissaoAcessoArquivo(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int writePermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            return (writePermission == PackageManager.PERMISSION_GRANTED) && (readPermission == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        initAdapters();
    }

    //Search files, Images, Videos
    public void searchfilestype(String[] path, String type, ImagensAdapter iadapter, VideosAdapter vadapter, TextoAdapter oadapter){

        String[] projection;
        int columnIndex = 0;

        switch (type){
            case "Imagens":
                projection = new String[]{MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media.DATA + " like ? ",
                        path,
                        null);

                columnIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                if(cursor.getCount()>0)
                    for(int i =0;i<cursor.getCount();i++){
                        cursor.moveToPosition(i);

                        iadapter.addImagem(cursor.getString(columnIndex));
                    }
                cursor.close();
                break;

            case "Videos":
                projection = new String[]{MediaStore.Video.Media.DATA};

                Cursor cursor2 = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Video.Media.DATA + " like ? ",
                        path,
                        null);

                columnIndex = cursor2.getColumnIndex(MediaStore.Video.Thumbnails.DATA);

                if(cursor2.getCount()>0)
                    for(int i =0;i<cursor2.getCount();i++){
                        cursor2.moveToPosition(i);

                        vadapter.addVideo(cursor2.getString(columnIndex));
                    }
                cursor2.close();
                break;

            default:
                ContentResolver cr = getActivity().getContentResolver();
                Uri uri = MediaStore.Files.getContentUri("external");

                projection = null;

                String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                        + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE + " AND " + MediaStore.Files.FileColumns.DATA + " like ? ";

                String sortOrder = null;
                Cursor allNonMediaFiles = cr.query(uri, projection, selection, path, sortOrder);

                if(allNonMediaFiles.getCount()>0)
                    for(int i =0;i<allNonMediaFiles.getCount();i++){
                        allNonMediaFiles.moveToPosition(i);

                        oadapter.addOutro(allNonMediaFiles.getString(allNonMediaFiles.getColumnIndex(MediaStore.Files.FileColumns.DATA)));
                    }
                allNonMediaFiles.close();
                break;
        }
    }

    //Calcular quantos itens serao exibidos por linha (tamanho fixo)
    public int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 120);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu items for use in the action bar
        inflater.inflate(R.menu.menu_compartilhar, menu);

        compartilhar = menu.findItem(R.id.menu_compartilhar);

        compartilhar.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.menu_compartilhar && caminho != null) {
            Log.e("compartilhar", "COMPARTILHAR");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+caminho));
            if (type.equals("Imagens"))
                shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Send To"));

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void getItemCaminho(String caminho){
        Log.e("abrir icone", caminho);
        this.caminho = caminho;
        if (compartilhar != null)
            compartilhar.setVisible(true);
    }

    @Override
    public void EsconderIcone() {
        if (compartilhar != null)
            compartilhar.setVisible(false);
    }
}

