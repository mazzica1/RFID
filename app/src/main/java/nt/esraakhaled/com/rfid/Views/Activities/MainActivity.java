package nt.esraakhaled.com.rfid.Views.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import nt.esraakhaled.com.rfid.Controllers.Interfaces.IKeyDown;
import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.R;
import nt.esraakhaled.com.rfid.Views.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {


    Toolbar mToolbar=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // toolbar
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment())
                .commit();

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("Are you want to exit ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Warning");
            alertDialog.show();

        } else {
            super.onBackPressed();
        }

        showBackButton(count>1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        UHFReader.getInstange(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 139) {
            if (event.getRepeatCount() == 0) {
                UHFReader.getInstange(this).toogleReading();
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

                if (f != null && f instanceof IKeyDown) {
                    ((IKeyDown)f).myOnKeyDwon();
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UHFReader.getInstange(this).closing();
    }

    public void showBackButton(boolean show){

        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
        if (show){
           mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.onBackPressed();
                }
            });
        }
    }
}
