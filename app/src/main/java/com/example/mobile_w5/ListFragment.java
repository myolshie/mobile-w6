package com.example.mobile_w5;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
        MainActivity main;
        Context context = null;
        private ArrayList<Student> students;
        public static ListFragment newInstance(String strArg) {
                ListFragment fragment = new ListFragment();
                Bundle args = new Bundle();
                args.putString("strArg1", strArg);
                fragment.setArguments(args);
                return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                students = new ArrayList<>();
                students.add(new Student("A1_9829", "https://picsum.photos/200","Nguyen Thi H", "A1", 9.10));
                students.add(new Student("A1_1809", "https://picsum.photos/200","Le Thi A", "A1", 8.00));
                students.add(new Student("A2_3509", "https://picsum.photos/200","Van Thi B", "A2", 7.16));
                students.add(new Student("A2_3100", "https://picsum.photos/200","Duong Thi C", "A3", 8.17));
                students.add(new Student("A1_1120", "https://picsum.photos/200","Ly Thi D", "A1", 9.12));
                students.add(new Student("A4_4120", "https://picsum.photos/200","Tran Thi E", "A4", 6.10));
                students.add(new Student("A2_8100", "https://picsum.photos/200","Truong Thi F", "A2", 5.50));
                students.add(new Student("A4_1160", "https://picsum.photos/200","Nguyen Thi G", "A4", 4.10));
                try {
                        context = getActivity();
                        main = (MainActivity) getActivity();
                } catch (IllegalStateException e) {
                        throw new IllegalStateException("MainActivity must implement callbacks");
                }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                LinearLayout layout_list = (LinearLayout) inflater.inflate(R.layout.fragment_list, null);
                final TextView selector = layout_list.findViewById(R.id.selector);
                RecyclerView recyclerView = layout_list.findViewById(R.id.recyclerView);
                recyclerView.setBackgroundColor(Color.parseColor("#ffccddff"));
                StudentAdapter adapter = new StudentAdapter(context, students, selector, this.main);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                return layout_list;
        }
}
class Student {
        String id;
        String avatar;
        String name;
        String classroom;
        Double grade;

        public Student(String id, String avatar, String name, String classroom, Double grade) {
                this.id = id;
                this.avatar = avatar;
                this.name = name;
                this.classroom = classroom;
                this.grade = grade;
        }
}
class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
        private Context context;
        static MainActivity main;
        private static ArrayList<Student> students = null;
        private static final ArrayList<ConstraintLayout> containers = new ArrayList<>();
        private static TextView selector = null;

        public StudentAdapter(Context context, ArrayList<Student> students, TextView selector, MainActivity main) {
                this.context = context;
                this.students = students;
                this.selector = selector;
                this.main = main;
        }

        public static class StudentViewHolder extends RecyclerView.ViewHolder {
                private final ConstraintLayout container;
                private final ImageView avatar;
                private final TextView id;

                public StudentViewHolder(View view) {
                        super(view);
                        container = view.findViewById(R.id.info_container);
                        avatar = view.findViewById(R.id.info_avatar);
                        id = view.findViewById(R.id.info_id);
                }
        }
        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                this.context = viewGroup.getContext();
                View view = LayoutInflater.from(this.context).inflate(R.layout.component_info, viewGroup, false);
                return new StudentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StudentViewHolder viewHolder, final int position) {
                Student student = students.get(position);
                viewHolder.id.setText(student.id);
                this.containers.add(viewHolder.container);

                viewHolder.container.setOnClickListener(v -> {
                        String selectorText = "Mã số: " + student.id;
                        this.selector.setText(selectorText);
                        main.onMsgFromFragToMain("BLUE-FRAG", position);

                        for (int i = 0; i < students.size(); i++) {
                                this.containers.get(i).setBackgroundResource(R.color.white);
                        }

                        this.containers.get(position).setBackgroundResource(R.color.black);
                });

        }
        @Override
        public int getItemCount() {
                return students.size();
        }
        public static void selectViewHolder(int position) {
                String selectorText = "Mã số: " + students.get(position).id;
                selector.setText(selectorText);
                main.onMsgFromFragToMain("BLUE-FRAG", position);

                for (int i = 0; i < students.size(); i++) {
                        containers.get(i).setBackgroundResource(R.color.white);
                }
                containers.get(position).setBackgroundResource(R.color.black);
        }
}
