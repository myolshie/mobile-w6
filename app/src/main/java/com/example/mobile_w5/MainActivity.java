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
        long classId1 = dbClass.addClass("A1");
        long classId2 = dbClass.addClass("A2");
        long classId3 = dbClass.addClass("A3");

        long studentId1 = dbClass.addStudent("Nguyen Thi H", 9.10, 1);
        long studentId2 = dbClass.addStudent("Le Thi A", 8.00, 2);
        long studentId3 = dbClass.addStudent("Van Thi B", 7.10, 3);
        long studentId4 = dbClass.addStudent("Le Thi C", 6.00, 2);
        long studentId5 = dbClass.addStudent("Van Thi D", 5.13, 3);
        long studentId6 = dbClass.addStudent("Le Thi E", 4.00, 2);
        long studentId7 = dbClass.addStudent("Van Thi F", 7.23, 3);
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
