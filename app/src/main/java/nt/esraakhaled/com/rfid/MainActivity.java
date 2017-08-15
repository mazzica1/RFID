package nt.esraakhaled.com.rfid;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import nt.esraakhaled.com.rfid.Controllers.Sensors.UHFReader;
import nt.esraakhaled.com.rfid.Fragments.LoginFragment;
import nt.esraakhaled.com.rfid.Interfaces.IKeyDown;

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
