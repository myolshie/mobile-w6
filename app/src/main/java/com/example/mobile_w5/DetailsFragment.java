package com.example.mobile_w5;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DetailsFragment extends Fragment implements FragmentCallbacks {
    MainActivity main; Button first_button; Button previous_button; Button next_button; Button last_button;
    TextView txtId; TextView txtName; TextView txtClass; TextView txtGrade;
    private ArrayList<Info> infos;
    public int currentPosition = 0;
    public static DetailsFragment newInstance(String strArg1) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle(); bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }
    public static class Info {
        String id;
        String avatar;
        String name;
        String classroom;
        Double grade;
        public Info(String id, String avatar, String name, String classroom, Double grade) {
            this.id = id;
            this.avatar = avatar;
            this.name = name;
            this.classroom = classroom;
            this.grade = grade;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infos = new ArrayList<>();
        SQLDatabase dbHelper = new SQLDatabase(getActivity());

        // Retrieve data from SQLite database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getAllStudents();

        // Parse the retrieved data into Student objects and add them to the ArrayList
        if (cursor.moveToFirst()) {
            do {
                int studentIdIndex = cursor.getColumnIndex(SQLDatabase.STUDENT_ID);
                int studentNameIndex = cursor.getColumnIndex(SQLDatabase.STUDENT_NAME);
                int classNameIndex = cursor.getColumnIndex(SQLDatabase.CLASS_NAME);
                int scoreIndex = cursor.getColumnIndex(SQLDatabase.STUDENT_SCORE);

                if (studentIdIndex >= 0 && studentNameIndex >= 0 && classNameIndex >= 0 && scoreIndex >= 0) {
                    String studentId = cursor.getString(studentIdIndex);
                    String studentName = cursor.getString(studentNameIndex);
                    String className = cursor.getString(classNameIndex);
                    double score = cursor.getDouble(scoreIndex);
                    studentId = className + "_" + studentId;
                    infos.add(new Info(studentId, "", studentName, className, score));
                } else {
                    // Handle the case where one or more column indices are invalid
                    Log.e("DB", "One or more column indices are invalid");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (MainActivity) getActivity(); // use this reference to invoke main callbacks
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConstraintLayout layout_details = (ConstraintLayout) inflater.inflate(R.layout.fragment_details, null);
        txtId = layout_details.findViewById(R.id.details_id);
        txtName = layout_details.findViewById(R.id.details_name);
        txtClass = layout_details.findViewById(R.id.details_classroom);
        txtGrade = layout_details.findViewById(R.id.details_grade);
        try { Bundle arguments = getArguments(); }
        catch (Exception e) { Log.e("ERROR – ", "" + e.getMessage()); }

        first_button = layout_details.findViewById(R.id.first_button);
        previous_button = layout_details.findViewById(R.id.previous_button);
        next_button = layout_details.findViewById(R.id.next_button);
        last_button = layout_details.findViewById(R.id.last_button);
        previous_button.setEnabled(false);
        next_button .setEnabled(false);

        first_button.setOnClickListener(v -> {
            onMsgFromMainToFragment(0);
            main.onMsgFromFragToMain("RED-FRAG", 0);
            first_button.setEnabled(false);
            previous_button.setEnabled(false);
            last_button.setEnabled(true);
            next_button.setEnabled(true);
        });
        previous_button.setOnClickListener(v -> {
            if (currentPosition > 0) {
                onMsgFromMainToFragment(currentPosition - 1);
                main.onMsgFromFragToMain("RED-FRAG", currentPosition);
                if (currentPosition == 0)
                {
                    first_button.setEnabled(false);
                    previous_button.setEnabled(false);
                }
                last_button.setEnabled(true);
                next_button.setEnabled(true);
            }
        });
        next_button.setOnClickListener((v -> {
            if (currentPosition < infos.size() - 1) {
                onMsgFromMainToFragment(currentPosition + 1);
                main.onMsgFromFragToMain("RED-FRAG", currentPosition);
                first_button.setEnabled(true);
                previous_button.setEnabled(true);
                if (currentPosition == infos.size() - 1){
                    last_button.setEnabled(false);
                    next_button.setEnabled(false);
                }
            }
        }));
        last_button.setOnClickListener(v -> {
            onMsgFromMainToFragment(infos.size()-1);
            main.onMsgFromFragToMain("RED-FRAG", infos.size()-1);
            first_button.setEnabled(true);
            previous_button.setEnabled(true);
            last_button.setEnabled(false);
            next_button.setEnabled(false);
        });
        return layout_details;
    }
    private int getCurrentPosition(int position) {
        return position;
    };

    @Override
    public void onMsgFromMainToFragment(int strValue) {
        Info info = infos.get(strValue);
        currentPosition = getCurrentPosition(strValue);
        txtId.setText(info.id);
        txtClass.setText("Lớp: " + info.classroom);
        txtName.setText("Họ tên: " + info.name);
        txtGrade.setText("Điểm trung bình: " + info.grade.toString());
        if (currentPosition == 0 )
        {
            first_button.setEnabled(false);
            previous_button.setEnabled(false);
        }
        else {
            first_button.setEnabled(true);
            previous_button.setEnabled(true);
        }
        if (currentPosition == infos.size()-1)
        {
            last_button.setEnabled(false);
            next_button.setEnabled(false);
        }
        else {
            last_button.setEnabled(true);
            next_button.setEnabled(true);
        }
    }
}