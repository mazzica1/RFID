package nt.esraakhaled.com.rfid.Views.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import nt.esraakhaled.com.rfid.Controllers.Interfaces.IKeyDown;
import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.R;
import nt.esraakhaled.com.rfid.Views.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoginFragment()).commit();

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
}
