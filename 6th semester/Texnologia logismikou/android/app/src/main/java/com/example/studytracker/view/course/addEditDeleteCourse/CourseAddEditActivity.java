package com.example.studytracker.view.course.addEditDeleteCourse;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityCourseAddEditBinding;
import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.Student;
import com.example.studytracker.util.AndroidUtil;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Student}
 * to create/delete/edit {@link Course}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CourseAddEditActivity extends AppCompatActivity implements CourseAddEditView {
    public static final String MODE_KEY = "MODE_KEY";
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;

    ActivityCourseAddEditBinding binding;
    CourseAddEditPresenter presenter;
    Integer courseIdThatWillBeEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_add_edit);
        presenter = new CourseAddEditPresenter(this, App.memoryInitializer.getCourseDAO());
        presenter.setUpMode();

        setClickListeners();

    }

    private void setClickListeners() {
        binding.saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSaveCourse();
            }
        });

        binding.deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickDeleteCourse();
            }
        });
    }


    @Override
    public void onSuccessfulAddCourse() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course added successfully",
                getCourseName() + " added in your courses list",
                "OKAY",
                runnable);
    }

    @Override
    public void onSuccessfulEditCourse() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course Edited successfully",
                getCourseName() + " is  in your courses list",
                "OKAY",
                runnable);
    }

    @Override
    public void onSuccessfulDeleteCourse() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course deleted successfully",
                getCourseName() + " deleted from courses list",
                "OKAY",
                runnable);
    }

    @Override
    public String getCourseName() {
        return binding.nameEt.getText().toString();

    }

    @Override
    public void setCourseName(String courseName) {
        binding.nameEt.setText(courseName);
    }

    @Override
    public String getCourseDescription() {
        return binding.descriptionEt.getText().toString();
    }

    @Override
    public void setCourseDescription(String courseDescription) {
        binding.descriptionEt.setText(courseDescription);
    }

    @Override
    public int getCourseECTS() {
        String ects = binding.ectsEt.getText().toString();
        return ects.isEmpty() ? -1 : Integer.parseInt(ects);
    }

    @Override
    public void setCourseECTS(int courseECTS) {
        binding.ectsEt.setText("" + courseECTS);
    }

    @Override
    public int getCourseSemester() {
        String semester = binding.semesterEt.getText().toString();
        return semester.isEmpty() ? -1 : Integer.parseInt(semester);
    }

    @Override
    public void setCourseSemester(int courseSemester) {
        binding.semesterEt.setText("" + courseSemester);
    }

    @Override
    public String getSaveButtonText() {
        return binding.saveEditBtn.getText().toString();
    }

    @Override
    public void setSaveButtonText(String buttonText) {
        binding.saveEditBtn.setText(buttonText);
    }

    @Override
    public String getToolbarTitle() {
        return getTitle().toString();
    }

    @Override
    public void setToolbarTitle(String title) {
        setTitle(title);
    }

    @Override
    public Integer getSelectedCourseId() {
        return getIntent().getExtras().getInt("id", -1);
    }

    @Override
    public void setSelectedCourseId(Integer courseId) {
        //Nothing to do here (Only for tests)
    }

    @Override
    public void showEmptyFieldsDetected() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        AndroidUtil.showDialog(this,
                "Empty fields",
                "Make sure you have fill all fields",
                "OKAY",
                runnable);
    }

    @Override
    public void setUpAddMode() {
        setTitle("Add course");
        setSaveButtonText("ADD COURSE");

        binding.deleteCourseBtn.setVisibility(View.GONE);
    }

    @Override
    public void setUpEditMode(Course course) {
        setTitle("Edit course");
        setSaveButtonText("EDIT COURSE");

        setCourseName(course.getName());
        setCourseDescription(course.getDescription());
        setCourseECTS(course.getEcts());
        setCourseSemester(course.getSemester());

        binding.deleteCourseBtn.setVisibility(View.VISIBLE);
    }
}
