package com.example.gio.lecture12contentprovider;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;




public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Cursor cursor;
    String[] Projection;
    TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS,android.Manifest.permission.WRITE_CONTACTS},0);
            }
        }
        else{
            getLoaderManager().initLoader(0, null, this);
            Projection=new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,Projection,null,null,null);
        }
        txt1=(TextView) findViewById(R.id.textview1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(i==0)
            return new CursorLoader(this,ContactsContract.CommonDataKinds.Phone.CONTENT_URI,Projection,null,null,null);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(loader.getId()==0){
            Cursor c=cursor;
            while(c.moveToNext()){
               String number= c.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String displayName= c.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                txt1.setText(number +" "+displayName);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
