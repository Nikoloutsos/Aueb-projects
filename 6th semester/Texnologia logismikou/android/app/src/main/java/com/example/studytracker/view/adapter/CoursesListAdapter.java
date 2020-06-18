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
import com.example.studytracker.domain.Course;
import com.example.studytracker.view.course.addEditDeleteCourse.CourseAddEditActivity;
import com.example.studytracker.view.course.watchCourseList.CoursesActivity;

import java.util.List;


/**
 * A subclass of {@link RecyclerView.Adapter} used in {@link CoursesActivity}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CoursesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Course> data;
    Context context;

    public CoursesListAdapter(List<Course> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final Course course = data.get(position);
        ViewHolder customHolder = (ViewHolder) holder;

        TextView courseTitleTV = customHolder.rootView.findViewById(R.id.course_title_tv);
        TextView courseDescTV = customHolder.rootView.findViewById(R.id.course_desc_tv);

        courseTitleTV.setText(course.getName());
        courseDescTV.setText(course.getDescription());

        customHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseAddEditActivity.class);
                intent.putExtra("id", course.getId());
                intent.putExtra(CourseAddEditActivity.MODE_KEY, CourseAddEditActivity.EDIT_MODE);
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
