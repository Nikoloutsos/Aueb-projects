package Constraints;

import Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static Model.Info.*;
import static java.lang.Math.abs;

public class UniformLessons extends Constraint
{
    private SwapInfo swapInfo = null;
    private Random random;

    public UniformLessons(double weight)
    {
        this.weight = weight;
        random = new Random();
    }

    @Override
    public double evaluate(Info info, State state)
    {
        faults = new ArrayList<>();
        double[][][] lessonFrequency = new double[info.lessonList.size()][NUMBER_OF_GROUPS][NUMBER_OF_DAYS];

        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (state.data[i][j][k].lesson != null)
                    {
                        ++lessonFrequency[state.data[i][j][k].lesson.mId][i][j];
                    }
                }
            }
        }

        for (LessonGroup.Lesson lesson : info.lessonList.values())
        {
            for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
            {
                for (int j = 0; j < NUMBER_OF_DAYS; ++j)
                {
                    double ratio = Math.ceil(lesson.mHours / (double)NUMBER_OF_DAYS);

                    if (lessonFrequency[lesson.mId][i][j] > ratio)
                    {
                        FaultInfo faultInfo = new FaultInfo(null, lesson, i, j, -1, lessonFrequency[lesson.mId][i][j] - ratio);
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
        BlockInfo blockInfo = state.getBlockInfoByLesson(faultInfo.lesson, faultInfo.group, faultInfo.day);

        BlockInfo swapCandidate = new BlockInfo(blockInfo.group, random.nextInt(NUMBER_OF_DAYS), random.nextInt(NUMBER_OF_MAX_HOUR_PER_DAY));
        swapInfo = state.swapBlocks(new SwapInfo(blockInfo, swapCandidate));
    }

    @Override
    public void undo(Info info, State state)
    {
        state.swapBlocks(swapInfo);
        swapInfo = null;
    }
}
