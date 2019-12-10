package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static Model.Info.*;
import static java.lang.Math.abs;

public class UniformHours extends Constraint
{
    private Random random;
    private SwapInfo swapInfo;

    public UniformHours(double weight)
    {
        this.weight = weight;
        random = new Random();
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        int[][] lessonFrequency = new int[NUMBER_OF_GROUPS][NUMBER_OF_DAYS];
        double[] totalHours = new double[NUMBER_OF_GROUPS];

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].lesson != null)
                    {
                        ++lessonFrequency[i][j];
                        ++totalHours[i];
                    }
                }
            }
        }

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            double average = totalHours[i] / NUMBER_OF_DAYS;

            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                if (lessonFrequency[i][j] > Math.ceil(average))
                {
                    FaultInfo faultInfo = new FaultInfo(null, null, i, j, -1, lessonFrequency[i][j] - Math.ceil(average));
                    faults.add(faultInfo);
                }
                else if (lessonFrequency[i][j] < Math.floor(average))
                {
                    FaultInfo faultInfo = new FaultInfo(null, null, i, j, -1, lessonFrequency[i][j] - Math.floor(average));
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
        FaultInfo faultInfo = null;
        for (int i = 0; i < faults.size(); ++i)
        {
            if (faults.get(i).offset > 0)
            {
                faultInfo = faults.get(i);
                break;
            }
        }

        BlockInfo blockInfo = null;

        if (faultInfo != null)
        {
            for (int i = NUMBER_OF_MAX_HOUR_PER_DAY - 1; i >= 0; --i)
            {
                if (state.data[faultInfo.group][faultInfo.day][i].lesson != null)
                {
                    blockInfo = new BlockInfo(faultInfo.group, faultInfo.day, i);
                    break;
                }
            }
        }
        else
        {
            faultInfo = faults.get(0);
            for (int i = NUMBER_OF_MAX_HOUR_PER_DAY - 1; i >= 0; --i)
            {
                if (state.data[faultInfo.group][faultInfo.day][i].lesson == null)
                {
                    blockInfo = new BlockInfo(faultInfo.group, faultInfo.day, i);
                    break;
                }
            }
        }

        BlockInfo swapCandidate = state.getBlock(faultInfo.group, random.nextInt(NUMBER_OF_DAYS));

        if (swapCandidate != null)
        {
            swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
        }
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
