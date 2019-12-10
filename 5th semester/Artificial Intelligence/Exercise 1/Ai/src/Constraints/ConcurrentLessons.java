package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static Model.Info.*;
import static java.lang.Math.abs;

public class ConcurrentLessons extends Constraint
{
    private UpdateInfo updateInfo = null;
    private SwapInfo swapInfo = null;
    private Random random;

    public ConcurrentLessons(double weight)
    {
        this.weight = weight;
        random = new Random();
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        int[][][] teacherFrequency = new int[info.teacherList.size()][NUMBER_OF_DAYS][NUMBER_OF_MAX_HOUR_PER_DAY];

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].teacher != null)
                    {
                        ++teacherFrequency[state.data[i][j][k].teacher.id][j][k];
                    }
                }
            }
        }

        for (TeacherGroup.Teacher teacher : info.teacherList.values())
        {
            for (int i = 0; i < NUMBER_OF_DAYS; ++i)
            {
                for (int j = 0; j < NUMBER_OF_MAX_HOUR_PER_DAY; ++j)
                {
                    if (teacherFrequency[teacher.id][i][j] > 1)
                    {
                        FaultInfo faultInfo = new FaultInfo(teacher, null, -1, i, j, teacherFrequency[teacher.id][i][j] - 1);
                        faults.add(faultInfo);
                    }
                }
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
        swapInfo = null;
        updateInfo = null;
        FaultInfo faultInfo = faults.get(0);
        BlockInfo blockInfo = state.getBlockInfoByTeacher(faultInfo.teacher, faultInfo.day, faultInfo.hour);

        LessonGroup.Lesson lesson = state.data[blockInfo.group][blockInfo.day][blockInfo.hour].lesson;
        List<TeacherGroup.Teacher> teacherList = info.subjectTeachers.get(lesson.mId);

        if (teacherList.size() == 1)
        {
            BlockInfo swapCandidate = new BlockInfo(blockInfo.group, random.nextInt(NUMBER_OF_DAYS), random.nextInt(NUMBER_OF_MAX_HOUR_PER_DAY));
            swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
        }
        else
        {
            TeacherGroup.Teacher updateCandidate = teacherList.get(random.nextInt(teacherList.size()));

            for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
            {
                if (state.data[i][faultInfo.day][faultInfo.hour].teacher != null && state.data[i][faultInfo.day][faultInfo.hour].teacher.id == updateCandidate.id)
                {
                    BlockInfo swapCandidate = new BlockInfo(blockInfo.group, random.nextInt(NUMBER_OF_DAYS), random.nextInt(NUMBER_OF_MAX_HOUR_PER_DAY));
                    swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
                    return;
                }
            }

            updateInfo = state.updateBlock(new UpdateInfo(faultInfo.teacher, updateCandidate, blockInfo));
        }
    }

    @Override
    public void undo(Info info, State state)
    {
        if (swapInfo == null && updateInfo != null)
        {
            state.updateBlock(updateInfo);
            updateInfo = null;
        }
        else if (updateInfo == null && swapInfo != null)
        {
            state.swapBlocks(swapInfo);
            swapInfo = null;
        }
    }
}
