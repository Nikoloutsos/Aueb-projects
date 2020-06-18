package com.example.studytracker.view.courseRegistration.watchCourseRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityCourseRegistration2Binding;
import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.Student;
import com.example.studytracker.view.adapter.CourseRegistrationListAdapter;
import com.example.studytracker.view.courseRegistration.addEditDeleteCourseRegistration.CourseRegistrationAddEditActivity;

import java.util.List;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Student}
 * to see all {@link CoursesRegistration}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CourseRegistrationActivity extends AppCompatActivity implements CourseRegistrationView {

    ActivityCourseRegistration2Binding binding;
    CourseRegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My course registrations");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_registration2);
        presenter = new CourseRegistrationPresenter(this, App.memoryInitializer.getCourseRegistrationDAO());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setClickListeners();
    }

    private void setClickListeners() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddCourseRegistration();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllCourseRegistration();
    }


    @Override
    public void loadAllCourseRegistration(List<CoursesRegistration> coursesRegistrationList) {
        binding.recyclerView.setAdapter(new CourseRegistrationListAdapter(coursesRegistrationList, this));
    }

    @Override
    public void onAddCourseRegistration() {
        Intent intent = new Intent(CourseRegistrationActivity.this, CourseRegistrationAddEditActivity.class);
        intent.putExtra(CourseRegistrationAddEditActivity.MODE_TYPE, CourseRegistrationAddEditActivity.MODE_ADD);
        startActivity(intent);
    }
}
