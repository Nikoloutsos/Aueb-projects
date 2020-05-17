package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static Model.Info.*;
import static java.lang.Math.abs;

public class TeacherMaxHoursDaily extends Constraint
{
    private UpdateInfo updateInfo = null;
    private SwapInfo swapInfo = null;
    private Random random;

    public TeacherMaxHoursDaily(double weight)
    {
        this.weight = weight;
        random = new Random();
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        int[][] teacherFrequency = new int[info.teacherList.size()][NUMBER_OF_DAYS];

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].teacher != null)
                    {
                        ++teacherFrequency[state.data[i][j][k].teacher.id][j];
                    }
                }
            }
        }

        for (TeacherGroup.Teacher teacher : info.teacherList.values())
        {
            for (int i = 0; i < NUMBER_OF_DAYS; ++i)
            {
                if (teacher.hoursDaily < teacherFrequency[teacher.id][i])
                {
                    FaultInfo faultInfo = new FaultInfo(teacher, null, -1, i, -1, teacher.hoursDaily - teacherFrequency[teacher.id][i]);
                    faults.add(faultInfo);
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
        BlockInfo blockInfo = state.getBlockInfoByTeacher(faultInfo.teacher, faultInfo.day);

        BlockInfo swapCandidate = new BlockInfo(blockInfo.group, random.nextInt(NUMBER_OF_DAYS), random.nextInt(NUMBER_OF_MAX_HOUR_PER_DAY));
        swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
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
