package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static Model.Info.*;
import static java.lang.Math.abs;

public class UniformTeacherHours extends Constraint
{
    private Random random;
    private UpdateInfo updateInfo;

    public UniformTeacherHours(double weight)
    {
        this.weight = weight;
        random = new Random();
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        int[] teacherFrequency = new int[info.teacherList.size()];
        double totalHours = 0;

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].teacher != null)
                    {
                        ++teacherFrequency[state.data[i][j][k].teacher.id];
                        ++totalHours;
                    }
                }
            }
        }

        double average = Math.ceil(totalHours / info.teacherList.size());

        for (TeacherGroup.Teacher teacher : info.teacherList.values())
        {
            if (teacherFrequency[teacher.id] > average)
            {
                FaultInfo faultInfo = new FaultInfo(teacher, null, -1, -1, -1, teacherFrequency[teacher.id] - average);
                faults.add(faultInfo);
            }
        }

        Collections.shuffle(faults);

        double score = 0;

        for (FaultInfo faultInfo : faults)
        {
            score += abs(faultInfo.offset);
        }

        return score * weight;
    }

    @Override
    public void resolve(Info info, State state)
    {
        updateInfo = null;
        FaultInfo faultInfo = faults.get(0);
        BlockInfo blockInfo = state.getBlockInfoByTeacher(faultInfo.teacher);

        LessonGroup.Lesson lesson = state.data[blockInfo.group][blockInfo.day][blockInfo.hour].lesson;
        List<TeacherGroup.Teacher> teacherList = info.subjectTeachers.get(lesson.mId);

        updateInfo = state.updateBlock(new UpdateInfo(faultInfo.teacher, teacherList.get(random.nextInt(teacherList.size())), blockInfo));
    }

    @Override
    public void undo(Info info, State state)
    {
        if (updateInfo != null)
        {
            state.updateBlock(updateInfo);
            updateInfo = null;
        }
    }
}
