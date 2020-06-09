package com.example.studytracker.view.courseRegistration.addEditDeleteCourseRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityCourseRegistrationBinding;
import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.Student;
import com.example.studytracker.util.AndroidUtil;
import com.example.studytracker.view.courseRegistration.selectCoursesForCourseRegistration.SelectCoursesActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A subclass of {@link AppCompatActivity} used for {@link Student}
 * to create/delete/edit {@link CoursesRegistration}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CourseRegistrationAddEditActivity extends AppCompatActivity implements CourseRegistrationAddEditView {
    public static final String MODE_TYPE = "MODE_TYPE";
    public static final String MODE_ADD = "MODE_ADD";
    public static final String MODE_EDIT = "MODE_EDIT";
    public static final int REQUEST_CODE_SELECT_COURSE = 0X001;

    public static List<Course> listOfSelectedCourse = new ArrayList<>();
    ActivityCourseRegistrationBinding binding;
    CourseRegistrationAddEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_registration);
        presenter = new CourseRegistrationAddEditPresenter(this, App.memoryInitializer.getCourseRegistrationDAO());
        listOfSelectedCourse.clear();

        presenter.setUpView();

        setClickListeners();
    }

    private void setClickListeners() {
        binding.addACourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddCourses();
            }
        });


        binding.createCourseRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSaveCourseRegistration();
            }
        });


        binding.deleteCourseRegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickDeleteCourseRegistration();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_COURSE) {
            setSelectedCourseString(listOfSelectedCourse);
        }
    }


    private void setSelectedCourseString(List<Course> listOfSelectedCourse) {
        String str = "";
        for (Course course : listOfSelectedCourse) {
            str += "- " + course.getName() + "\n";
        }
        binding.selectedCoursesTv.setText(str);
    }

    @Override
    public String getMode() {
        return getIntent().getExtras().getString(MODE_TYPE);
    }

    @Override
    public Integer getAcademicPeriodYear() {
        return Integer.parseInt(binding.semesterValueEt.getText().toString());
    }

    @Override
    public void setAcademicPeriodYear(int year) {
        binding.semesterValueEt.setText("" + year);
    }

    @Override
    public String getAcademicPeriodType() {
        return binding.semesterValue2Et.getText().toString();
    }

    @Override
    public void setAcademicPeriodType(String periodType) {
        binding.semesterValue2Et.setText(periodType);
    }

    @Override
    public String getDescription() {
        return binding.descriptionEt.getText().toString();
    }

    @Override
    public void setDescription(String descriptionText) {
        binding.descriptionEt.setText(descriptionText);
    }

    @Override
    public String getSaveButtonText() {
        return binding.createCourseRegistrationBtn.getText().toString();
    }

    @Override
    public void setSaveButtonText(String buttonText) {
        binding.createCourseRegistrationBtn.setText(buttonText);
    }

    @Override
    public Integer getIdForCourseRegistration() {
        return getIntent().getExtras().getInt("id");
    }

    @Override
    public List<Course> getSelectedRegisteredCourses() {
        return listOfSelectedCourse;
    }

    @Override
    public void setSelectedCourses(String selectedCourses) {
        binding.selectedCoursesTv.setText(selectedCourses);
    }

    @Override
    public void setToolbarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void hideDeleteButton() {
        binding.deleteCourseRegistrationBtn.setVisibility(View.GONE);
    }

    @Override
    public void showDeleteButton() {
        binding.deleteCourseRegistrationBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickAddCourse() {
        startActivityForResult(new Intent(this, SelectCoursesActivity.class),
                REQUEST_CODE_SELECT_COURSE);
    }

    @Override
    public void onClickDeleteCourse() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course registration deleted",
                "Course registration was deleted successfully",
                "Okay",
                runnable);
    }

    @Override
    public void showCourseRegistrationSaved() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course registration Added",
                "Course registration was added successfully",
                "Okay",
                runnable);
    }

    @Override
    public void showCourseRegistrationEdited() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course registration Edited",
                "Course registration was edited successfully",
                "Okay",
                runnable);
    }

    @Override
    public void showCourseRegistrationDeleted() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Course registration deleted",
                "Course registration was deleted successfully",
                "Okay",
                runnable);
    }

    @Override
    public void showEmptyDescription() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        AndroidUtil.showDialog(this,
                "Empty description",
                "Please write a description to continue",
                "Okay",
                runnable);
    }
}
