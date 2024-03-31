package com.example.mobile_w5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements MainCallbacks{
    FragmentTransaction ft; DetailsFragment detailsFragment; ListFragment listFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLDatabase dbClass = new SQLDatabase(this);
        dbClass.addClass("A1");
        dbClass.addClass("A2");
        dbClass.addClass("A3");
        dbClass.addClass("A4");

        dbClass.addStudent("9829","Nguyen Thi H", 9.10, 1);
        dbClass.addStudent("1809","Le Thi A", 8.00, 1);
        dbClass.addStudent("3509","Van Thi B", 7.10, 2);
        dbClass.addStudent("3100","Le Thi C", 6.00, 2);
        dbClass.addStudent("1120","Van Thi D", 5.13, 1);
        dbClass.addStudent("4120","Le Thi E", 4.00, 4);
        dbClass.addStudent("8100","Van Thi F", 7.23, 2);
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        ft = getSupportFragmentManager().beginTransaction(); listFragment = ListFragment.newInstance("first-blue");
        ft.replace(R.id.list_holder, listFragment); ft.commit();
        ft = getSupportFragmentManager().beginTransaction(); detailsFragment = DetailsFragment.newInstance("first-red");
        ft.replace(R.id.details_holder, detailsFragment); ft.commit();
    }
    @Override
    public void onMsgFromFragToMain(String sender, int strValue) {
        if (sender.equals("RED-FRAG")) {
            StudentAdapter.selectViewHolder(strValue);
        }
        if (sender.equals("BLUE-FRAG")) {
            try {
                detailsFragment.onMsgFromMainToFragment(strValue);

            }
            catch (Exception e) { Log.e("ERROR", "onStrFromFragToMain " + e.getMessage()); }
        }
    }
}
