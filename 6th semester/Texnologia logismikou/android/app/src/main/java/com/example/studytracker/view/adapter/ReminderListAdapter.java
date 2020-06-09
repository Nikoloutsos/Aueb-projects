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
import com.example.studytracker.domain.Reminder;
import com.example.studytracker.view.reminder.addEditDeleteReminder.ReminderAddEditActivity;
import com.example.studytracker.view.reminder.watchReminderList.RemindersActivity;

import java.util.List;

/**
 * A subclass of {@link RecyclerView.Adapter} used in {@link RemindersActivity}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class ReminderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Reminder> data;
    Context context;

    public ReminderListAdapter(List<Reminder> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Reminder reminder = data.get(position);

        TextView timeReminderTV = holder.itemView.findViewById(R.id.reminder_time_title);
        TextView reminderTitleTV = holder.itemView.findViewById(R.id.title_reminder_tv);
        timeReminderTV.setText(reminder.getTimeToRemind().getReadableTime());
        reminderTitleTV.setText(reminder.getTitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReminderAddEditActivity.class);
                intent.putExtra(ReminderAddEditActivity.MODE_KEY, ReminderAddEditActivity.EDIT_MODE);
                intent.putExtra("id", reminder.getId());
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
