package com.example.studytracker.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studytracker.R;
import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.view.courseRegistration.addEditDeleteCourseRegistration.CourseRegistrationAddEditActivity;
import com.example.studytracker.view.courseRegistration.watchCourseRegistration.CourseRegistrationActivity;

import java.util.List;

/**
 * A subclass of {@link RecyclerView.Adapter} used in {@link CourseRegistrationActivity}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CourseRegistrationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CoursesRegistration> data;
    Context context;

    public CourseRegistrationListAdapter(List<CoursesRegistration> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_course_registration, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CoursesRegistration coursesRegistration = data.get(position);
        ViewHolder customeHolder = (ViewHolder) holder;
        TextView courseRegistrationTitle = customeHolder.rootView.findViewById(R.id.course_registration_title);
        TextView courseRegistrationAcademicPeriod = customeHolder.rootView.findViewById(R.id.course_registration_academic_period_title);

        courseRegistrationTitle.setText(coursesRegistration.getTitle());
        courseRegistrationAcademicPeriod.setText(coursesRegistration.getAcademicPeriod().toString());

        customeHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseRegistrationAddEditActivity.class);
                intent.putExtra("id", coursesRegistration.getId());
                intent.putExtra(CourseRegistrationAddEditActivity.MODE_TYPE, CourseRegistrationAddEditActivity.MODE_EDIT);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
        }
    }
}
