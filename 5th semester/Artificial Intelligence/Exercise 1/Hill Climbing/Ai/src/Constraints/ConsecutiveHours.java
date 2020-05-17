package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static Model.Info.*;

public class ConsecutiveHours extends Constraint
{
    private SwapInfo swapInfo = null;
    private Random random;

    public ConsecutiveHours(double weight)
    {
        this.weight = weight;
        random = new Random();
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        int[][][] teacherFrequency = new int[info.teacherList.size()][NUMBER_OF_DAYS][NUMBER_OF_MAX_HOUR_PER_DAY];

        for (TeacherGroup.Teacher teacher : info.teacherList.values())
        {
            for (int i = 0; i < NUMBER_OF_DAYS; ++i)
            {
                for (int j = 0; j < NUMBER_OF_MAX_HOUR_PER_DAY; ++j)
                {
                    teacherFrequency[teacher.id][i][j] = -1;
                }
            }
        }

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].teacher != null )
                    {
                        teacherFrequency[state.data[i][j][k].teacher.id][j][k] = i;
                    }
                }
            }
        }

        for (TeacherGroup.Teacher teacher : info.teacherList.values())
        {
            for (int i = 0; i < NUMBER_OF_DAYS; ++i)
            {
                for (int j = 0; j < NUMBER_OF_MAX_HOUR_PER_DAY - 2; ++j)
                {
                    if (teacherFrequency[teacher.id][i][j] != -1 && teacherFrequency[teacher.id][i][j + 1] != -1 && teacherFrequency[teacher.id][i][j + 2] != -1)
                    {
                        int randomHour = random.nextInt(2);
                        FaultInfo faultInfo = new FaultInfo(teacher, null, teacherFrequency[teacher.id][i][j + randomHour], i, j + randomHour, -1);
                        faults.add(faultInfo);
                    }
                }
            }
        }

        Collections.shuffle(faults);

        return faults.size() * weight;
    }

    @Override
    public void resolve(Info info, State state)
    {
        swapInfo = null;
        FaultInfo faultInfo = faults.get(0);

        BlockInfo blockInfo = new BlockInfo(faultInfo.group, faultInfo.day, faultInfo.hour);
        BlockInfo swapCandidate = new BlockInfo(faultInfo.group, random.nextInt(NUMBER_OF_DAYS), random.nextInt(NUMBER_OF_MAX_HOUR_PER_DAY));

        swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
    }

    @Override
    public void undo(Info info, State state)
    {
        if (swapInfo != null)
        {
            state.swapBlocks(swapInfo);
            swapInfo = null;
        }
    }
}
