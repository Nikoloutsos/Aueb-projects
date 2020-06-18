package com.example.studytracker.view.course.watchCourseList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityCoursesBinding;
import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.Student;
import com.example.studytracker.view.adapter.CoursesListAdapter;
import com.example.studytracker.view.course.addEditDeleteCourse.CourseAddEditActivity;

import java.util.List;

import static com.example.studytracker.view.course.addEditDeleteCourse.CourseAddEditActivity.ADD_MODE;
import static com.example.studytracker.view.course.addEditDeleteCourse.CourseAddEditActivity.MODE_KEY;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Student}
 * to see all {@link Course}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CoursesActivity extends AppCompatActivity implements CourseView {
    ActivityCoursesBinding binding;
    CoursePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My courses");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_courses);
        presenter = new CoursePresenter(this,
                App.memoryInitializer.getCourseDAO());


        setClickListeners();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        binding.courseRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setClickListeners() {
        binding.fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddCourse();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllCourses();
    }

    @Override
    public void onFetchCourses(List<Course> courseList) {
        binding.courseRv.setAdapter(new CoursesListAdapter(courseList, this));
    }

    @Override
    public void onAddCourse() {
        Intent intent = new Intent(CoursesActivity.this, CourseAddEditActivity.class);
        intent.putExtra(MODE_KEY, ADD_MODE);
        startActivity(intent);
    }
}
