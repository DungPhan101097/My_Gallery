package com.example.dungit.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvWrapper;
    private RecyclerView.Adapter rvWapperAdapter;
    private RecyclerView.LayoutManager rvWapperLayoutManager;
    private static final int MY_PERMISSION_EXTERNAL_STORAGE = 1;
    private ArrayList<ListPhotoSameDate> al_images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                Toast.makeText(this, "Application have to need permission to access photo!",
                        Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_EXTERNAL_STORAGE);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_EXTERNAL_STORAGE);
            }
        }
        else{
            fn_imagespath();
            initViews();
        }
    }


    public ArrayList<ListPhotoSameDate> fn_imagespath() {
        al_images.clear();

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Images.Media.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        final String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null,
                null, orderBy);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        //column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);

        int i = 0;
        ListPhotoSameDate lstPhoto = new ListPhotoSameDate();

        while (cursor.moveToNext()) {
            i++;
            absolutePathOfImage = cursor.getString(column_index_data);
            //String myDate = cursor.getString(date);

            /*Photo curPhoto = new Photo();
            curPhoto.setUrl(absolutePathOfImage);
            curPhoto.setDate("Ngay " + i);
            if(i % 10 == 0){
                al_images.add(lstPhoto);
                lstPhoto.setDate(lstPhoto.getLstPhotoHaveSameDate().get(0).getDate());
                lstPhoto = new ListPhotoSameDate();
            }
            else{
                lstPhoto.addPhoto(curPhoto);
            }*/

            //al_images.add(curPhoto);

            /*File file = new File(absolutePathOfImage);
            Date lastModDate = new Date(file.lastModified());*/
            try {
                ExifInterface exifInterface = new ExifInterface(absolutePathOfImage);
                String tmp = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                Log.e("Date",  tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //Log.e("Column", absolutePathOfImage);
            //Log.e("Date", new Date(date).toString());
            //Log.e("Folder", cursor.getString(column_index_folder_name));

            /*for (int i = 0; i < al_images.size(); i++) {
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);

            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);
            }*/


        }


        /*for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }
        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(),al_images);
        gv_folder.setAdapter(obj_adapter);*/
        return al_images;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_PERMISSION_EXTERNAL_STORAGE:
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fn_imagespath();
                    initViews();
                }
                else{
                    Toast.makeText(this, "Application don't access any data to show!",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void initViews() {
        rvWrapper = findViewById(R.id.rv_wrapper);
        rvWrapper.setHasFixedSize(true);

        rvWapperLayoutManager = new LinearLayoutManager(this);
        rvWrapper.setLayoutManager(rvWapperLayoutManager);

        AdapterRecyclerView adapterRecyclerView = new AdapterRecyclerView(this, al_images);
        rvWrapper.setAdapter(adapterRecyclerView);
    }
}
