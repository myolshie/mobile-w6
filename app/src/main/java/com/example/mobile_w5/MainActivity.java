package com.example.mobile_w5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements MainCallbacks{
    FragmentTransaction ft; DetailsFragment detailsFragment; ListFragment listFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
// create a new BLUE fragment - show it
        ft = getSupportFragmentManager().beginTransaction(); listFragment = ListFragment.newInstance("first-blue");
        ft.replace(R.id.list_holder, listFragment); ft.commit();
// create a new RED fragment - show it
        ft = getSupportFragmentManager().beginTransaction(); detailsFragment = DetailsFragment.newInstance("first-red");
        ft.replace(R.id.details_holder, detailsFragment); ft.commit();
    }
    // MainCallback implementation (receiving messages coming from Fragments)
    @Override
    public void onMsgFromFragToMain(String sender, int strValue) {
// show message arriving to MainActivity
        Toast.makeText(getApplication(), " MAIN GOT>> " + sender + "\n" + strValue, Toast.LENGTH_LONG).show();
        if (sender.equals("RED-FRAG")) {
            StudentAdapter.selectViewHolder(strValue);
        }
        if (sender.equals("BLUE-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                detailsFragment.onMsgFromMainToFragment(strValue);
            }
            catch (Exception e) { Log.e("ERROR", "onStrFromFragToMain " + e.getMessage()); }
        }
    }
}
