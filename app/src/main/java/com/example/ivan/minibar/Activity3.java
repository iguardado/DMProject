package com.example.ivan.minibar;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ivan on 27/11/2017.
 */

public class Activity3 extends AppCompatActivity {

    private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private static TicketsAdapter adapter ;
    private DBManager gestorDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        gestorDB = new DBManager( this.getApplicationContext() );

        tickets = gestorDB.getTickets();

        adapter = new TicketsAdapter(this, tickets);
        ListView lvtickets = (ListView) findViewById( R.id.lvTickets );
        lvtickets.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu( menu );
        this.getMenuInflater().inflate( R.menu.listaticket_menu, menu );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        boolean toret = false;
        switch( menuItem.getItemId() ) {
            case R.id.btCarpetaTickets:
                if(ContextCompat.checkSelfPermission(Activity3.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Activity3.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }else {
                    Log.i("","tfyghjiklpñ");
                    Uri selectedUri = Uri.parse(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(selectedUri, "resource/folder");

                    if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
                        startActivity(intent);
                    }
                }
                toret = true;
                break;
        }
        return toret;
    }
}
