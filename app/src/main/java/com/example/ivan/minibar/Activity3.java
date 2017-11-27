package com.example.ivan.minibar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ivan on 27/11/2017.
 */

public class Activity3 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);


        TextView lblView = (TextView) findViewById(R.id.numTickets);
        Button boton = (Button) findViewById(R.id.button);
        DBManager gestorDB = new DBManager( this.getApplicationContext() );


        lblView.setText(gestorDB.getTicket());
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
