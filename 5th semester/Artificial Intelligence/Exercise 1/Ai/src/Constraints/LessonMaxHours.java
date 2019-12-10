package Constraints;

import Model.*;
import Model.State;

import java.util.ArrayList;
import java.util.Collections;

import static Model.Info.*;
import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class LessonMaxHours extends Constraint
{
    private SwapInfo swapInfo;

    public LessonMaxHours(double weight)
    {
        this.weight = weight;
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        int[][] lessonFrequency = new int[info.lessonList.size()][NUMBER_OF_GROUPS];

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            String className;
            if (i < 3)
            {
                className = "A";
            }
            else if (i < 6)
            {
                className = "B";
            }
            else
            {
                className = "C";
            }

            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].lesson != null)
                    {
                        ++lessonFrequency[state.data[i][j][k].lesson.mId][i];
                    }
                }
            }

            for (LessonGroup.Lesson lesson : info.lessonList.values())
            {
                if (lesson.mClass.equals(className) && lesson.mHours != lessonFrequency[lesson.mId][i])
                {
                    FaultInfo faultInfo = new FaultInfo(null, lesson, i, -1, -1, lessonFrequency[lesson.mId][i] - lesson.mHours);
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
        for (FaultInfo fault : faults)
        {
            if (fault.offset > 0)
            {
                faultInfo = fault;
                break;
            }
        }
        BlockInfo blockInfo = state.getBlockInfoByLesson(faultInfo.lesson, faultInfo.group);

        int groupOffset;
        if (faultInfo.group < 3)
        {
            groupOffset = 0;
        }
        else if (faultInfo.group < 6)
        {
            groupOffset = 3;
        }
        else
        {
            groupOffset = 6;
        }


        BlockInfo swapCandidate = null;
        for (int i = 0; i < faults.size(); ++i)
        {
            if (faults.get(i).group != faultInfo.group && faults.get(i).group >= groupOffset && faults.get(i).group < groupOffset + NUMBER_OF_GROUPS_PER_CLASS)
            {
                if (faults.get(i).lesson.mId == faultInfo.lesson.mId && signum(faults.get(i).offset) != signum(faultInfo.offset))
                {
                    swapCandidate = state.getBlockInfoOfEmpty(faults.get(i).group);
                    break;
                }
            }
        }

        if (swapCandidate == null)
        {
            for (int i = 0; i < faults.size(); ++i)
            {
                if (faults.get(i).group != faultInfo.group && faults.get(i).group >= groupOffset && faults.get(i).group < groupOffset + NUMBER_OF_GROUPS_PER_CLASS)
                {
                    if (faults.get(i).lesson.mId != faultInfo.lesson.mId && signum(faults.get(i).offset) == signum(faultInfo.offset))
                    {
                        swapCandidate = state.getBlockInfoByLesson(faults.get(i).lesson, faults.get(i).group);
                        break;
                    }
                }
            }
        }

        swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
    }

    @Override
    public void undo(Info info, State state)
    {
        state.swapBlocks(swapInfo);
    }
}
