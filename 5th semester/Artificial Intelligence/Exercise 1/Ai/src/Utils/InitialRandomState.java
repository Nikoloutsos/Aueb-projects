package Utils;

import Model.Info;
import Model.LessonGroup;
import Model.State;
import Model.State.*;
import Model.TeacherGroup;

import java.util.*;

import static Model.Info.*;

/**
 * This class is used to create the initial state
 */
public class InitialRandomState
{
    private Random random;

    public State generateRandomState(Info info)
    {
        random = new Random();
        State state = new State();


        //Iterate for every class
        int classIndex = 0;
        for (Map.Entry<String, List<LessonGroup.Lesson>> entry : info.classLessons.entrySet())
        {
            ArrayList<Block> blockList = new ArrayList<>();

            //Generate the pool of all the lessons
            for (LessonGroup.Lesson lesson : entry.getValue())
            {
                for (int i = 0; i < NUMBER_OF_GROUPS_PER_CLASS * lesson.mHours; i++)
                {
                    blockList.add(generateBlockFromLesson(info, lesson));
                }
            }

            //Add empty hours if needed
            if (blockList.size() < NUMBER_OF_GROUPS_PER_CLASS * NUMBER_OF_DAYS * NUMBER_OF_MAX_HOUR_PER_DAY)
            {
                int emptyHours = NUMBER_OF_GROUPS_PER_CLASS * NUMBER_OF_DAYS * NUMBER_OF_MAX_HOUR_PER_DAY - blockList.size();

                for (int i = 0; i < emptyHours; ++i)
                {
                    blockList.add(new Block(null, null));
                }
            }

            //Iterate for every group
            for (int j = 0; j < NUMBER_OF_GROUPS_PER_CLASS; ++j)
            {
                //Iterate for every day
                for (int k = 0; k < NUMBER_OF_DAYS; ++k)
                {
                    //Iterate for every hours of each day
                    for (int l = 0; l < NUMBER_OF_MAX_HOUR_PER_DAY; ++l)
                    {
                        //For that hours add a random lesson or empty hour
                        int randomBlock = random.nextInt(blockList.size());
                        Block block = blockList.remove(randomBlock);

                        state.data[classIndex * NUMBER_OF_GROUPS_PER_CLASS +j][k][l] = block;
                    }
                }
            }

            ++classIndex;
        }

        return state;
    }

    private Block generateBlockFromLesson(Info info, LessonGroup.Lesson lesson)
    {
        List<TeacherGroup.Teacher> teacherList = info.subjectTeachers.get(lesson.mId);
        TeacherGroup.Teacher teacher = teacherList.get(random.nextInt(teacherList.size()));

        return new Block(lesson, teacher);
    }
}
