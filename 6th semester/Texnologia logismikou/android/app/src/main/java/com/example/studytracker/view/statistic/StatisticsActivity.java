package com.example.studytracker.view.statistic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityStatisticsBinding;
import com.example.studytracker.domain.Student;

/**
 * A subclass of {@link AppCompatActivity} used for
 * showing {@link Student} statistics
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class StatisticsActivity extends AppCompatActivity implements StatisticsView {
    ActivityStatisticsBinding binding;
    StatisticsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My statistics");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics);
        presenter = new StatisticsPresenter(this, App.memoryInitializer.getCourseRegistrationDAO());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateStatistics();
    }

    @Override
    public void setTotalEcts(Integer ects) {
        binding.totalEctsValueTv.setText(ects + " ECTS");
    }

    @Override
    public void setMeanScore(Double meanScore) {
        binding.meanScoreValueTv.setText(meanScore + "/10.0");
    }

    @Override
    public void setMeanScorePerSemester(String string) {
        binding.meanScorePerSemesterValueTv.setText(string);
    }


}
