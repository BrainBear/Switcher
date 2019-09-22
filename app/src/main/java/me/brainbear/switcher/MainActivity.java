package me.brainbear.switcher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import me.brainbear.Switcher;

public class MainActivity extends AppCompatActivity {

    {
        Switcher.initDefaultAdapter(new GlobalSwitcherAdapter());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void withActivity(View v) {
        startActivity(new Intent(this, SwitcherActivity.class));
    }

    public void withFragment(View v) {
        startActivity(new Intent(this, SwitcherFragmentActivity.class));
    }

}
