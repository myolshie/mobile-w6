package com.example.mobile_w5;

import android.os.Bundle;

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
        infos.add(new Info("A1_9829", "https://picsum.photos/200","Nguyen Thi H", "A1", 9.10));
        infos.add(new Info("A1_1809", "https://picsum.photos/200","Le Thi A", "A1", 8.00));
        infos.add(new Info("A2_3509", "https://picsum.photos/200","Van Thi B", "A2", 7.16));
        infos.add(new Info("A2_3100", "https://picsum.photos/200","Duong Thi C", "A3", 8.17));
        infos.add(new Info("A1_1120", "https://picsum.photos/200","Ly Thi D", "A1", 9.12));
        infos.add(new Info("A4_4120", "https://picsum.photos/200","Tran Thi E", "A4", 6.10));
        infos.add(new Info("A2_8100", "https://picsum.photos/200","Truong Thi F", "A2", 5.50));
        infos.add(new Info("A4_1160", "https://picsum.photos/200","Nguyen Thi G", "A4", 4.10));
// Activities containing this fragment must implement interface: MainCallbacks
        if (!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (MainActivity) getActivity(); // use this reference to invoke main callbacks
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// inflate res/layout_red.xml which includes a textview and a button
        LinearLayout layout_details = (LinearLayout) inflater.inflate(R.layout.fragment_details, null);
        txtId = layout_details.findViewById(R.id.details_id);
        txtName = layout_details.findViewById(R.id.details_name);
        txtClass = layout_details.findViewById(R.id.details_classroom);
        txtGrade = layout_details.findViewById(R.id.details_grade);
// show string argument supplied by constructor (if any!)
        try { Bundle arguments = getArguments(); }
        catch (Exception e) { Log.e("RED BUNDLE ERROR – ", "" + e.getMessage()); }

        first_button = layout_details.findViewById(R.id.first_button);
        previous_button = layout_details.findViewById(R.id.previous_button);
        next_button = layout_details.findViewById(R.id.next_button);
        last_button = layout_details.findViewById(R.id.last_button);

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