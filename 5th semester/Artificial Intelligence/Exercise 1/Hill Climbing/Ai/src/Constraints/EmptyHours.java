package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;

import static Model.Info.*;
import static java.lang.Math.abs;

public class EmptyHours extends Constraint
{
    private SwapInfo swapInfo = null;

    public EmptyHours(double weight)
    {
        this.weight = weight;
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                boolean found = false;
                int lastlesson = NUMBER_OF_MAX_HOUR_PER_DAY - 1;

                for (int k = NUMBER_OF_MAX_HOUR_PER_DAY - 1; k >= 0; --k)
                {
                    if (!found && state.data[i][j][k].lesson != null)
                    {
                        found = true;
                        lastlesson = k;
                    }
                    else if (found && state.data[i][j][k].lesson == null)
                    {
                        FaultInfo faultInfo = new FaultInfo(null, null, i, j, k, lastlesson - k);
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
        FaultInfo faultInfo = faults.get(0);

        BlockInfo blockInfo = new BlockInfo(faultInfo.group, faultInfo.day, faultInfo.hour);
        BlockInfo swapCandidate = new BlockInfo(faultInfo.group, faultInfo.day, faultInfo.hour + (int)faultInfo.offset);

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
