package com.example.studytracker.view.courseRegistration.selectCoursesForCourseRegistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.LayoutModalSelectCoursesBinding;
import com.example.studytracker.domain.Course;
import com.example.studytracker.util.AndroidUtil;
import com.example.studytracker.view.adapter.CourseSelectListAdapter;
import com.example.studytracker.view.courseRegistration.addEditDeleteCourseRegistration.CourseRegistrationAddEditActivity;

import java.util.List;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Course}
 * to select {@link Course} used in {@link CourseRegistrationAddEditActivity}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class SelectCoursesActivity extends AppCompatActivity implements SelectCourseView {
    LayoutModalSelectCoursesBinding binding;
    SelectCoursePresenter presenter;
    CourseSelectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_modal_select_courses);
        presenter = new SelectCoursePresenter(this, App.memoryInitializer.getCourseDAO());

        setClickListeners();
        setUpRecyclerView();

        presenter.loadAllCourses();
    }

    private void setUpRecyclerView() {
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setClickListeners() {
        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddCoursesToCourseRegistration();
            }
        });
    }

    @Override
    public List<Course> getSelectedCourses() {
        return adapter.selectedCourses;
    }

    @Override
    public void loadAllCourses(List<Course> courseList) {
        adapter = new CourseSelectListAdapter(courseList, this);
        binding.recyclerView2.setAdapter(adapter);
    }

    @Override
    public void onClickAddCoursesInCourseRegistration(List<Course> courseList) {
        CourseRegistrationAddEditActivity.listOfSelectedCourse = courseList;
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void showNoCourseSelected() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        AndroidUtil.showDialog(this,
                "No courses selected",
                "Please select at least 1 course",
                "Okay",
                runnable);
    }
}
