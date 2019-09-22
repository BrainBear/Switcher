package me.brainbear.switcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;

public class SwitcherFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switcher_fragment);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frame_content, new SwitcherFragment());
        transaction.commit();
    }
}
