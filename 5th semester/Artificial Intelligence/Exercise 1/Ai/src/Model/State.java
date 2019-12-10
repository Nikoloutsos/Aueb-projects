package Model;

import Utils.ParserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Model.Info.*;

public class State
{

    public Block[][][] data;
    private Random random;

    public State()
    {
        data = new Block[NUMBER_OF_GROUPS][NUMBER_OF_DAYS][NUMBER_OF_MAX_HOUR_PER_DAY];
        random = new Random();
    }

    public static class Block
    {
        public LessonGroup.Lesson lesson;
        public TeacherGroup.Teacher teacher;

        public Block(LessonGroup.Lesson lesson, TeacherGroup.Teacher teacher)
        {
            this.lesson = lesson;
            this.teacher = teacher;
        }
    }

    public BlockInfo getBlock(int group, int day)
    {
        ArrayList<BlockInfo> blockInfos = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_MAX_HOUR_PER_DAY; ++i)
        {
            if (data[group][day][i].lesson != null)
            {
                blockInfos.add(new BlockInfo(group, day, i));
            }
        }

        if (blockInfos.size() > 0)
        {
            return blockInfos.get(random.nextInt(blockInfos.size()));
        }

        return null;
    }

    public BlockInfo getBlockInfoOfEmpty(int group)
    {
        for (int i = 0; i < NUMBER_OF_DAYS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_MAX_HOUR_PER_DAY; ++j)
            {
                if (data[group][i][j].lesson == null)
                {
                    return new BlockInfo(group, i, j);
                }
            }
        }

        return null;
    }

    public BlockInfo getBlockInfoByLesson(LessonGroup.Lesson lesson, int group)
    {
        for (int i = 0; i < NUMBER_OF_DAYS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_MAX_HOUR_PER_DAY; ++j)
            {
                if (data[group][i][j].lesson != null && lesson.mId == data[group][i][j].lesson.mId)
                {
                    return new BlockInfo(group, i, j);
                }
            }
        }

        return null;
    }

    public BlockInfo getBlockInfoByLesson(LessonGroup.Lesson lesson, int group, int day)
    {
        List<BlockInfo> blockInfos = new ArrayList<>();

        for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
        {
            if (data[group][day][k].lesson != null && lesson.mId == data[group][day][k].lesson.mId)
            {
                blockInfos.add(new BlockInfo(group, day, k));
            }
        }


        if (blockInfos.size() > 0)
        {
            return blockInfos.get(random.nextInt(blockInfos.size()));
        }

        return null;
    }

    public BlockInfo getBlockInfoByTeacher(TeacherGroup.Teacher teacher, int day, int hour)
    {
        List<BlockInfo> blockInfos = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            if (data[i][day][hour].teacher != null && teacher.id == data[i][day][hour].teacher.id)
            {
                    blockInfos.add(new BlockInfo(i, day, hour));
            }
        }

        if (blockInfos.size() > 0)
        {
            return blockInfos.get(random.nextInt(blockInfos.size()));
        }

        return null;
    }

    public BlockInfo getBlockInfoByTeacher(TeacherGroup.Teacher teacher, int day)
    {
        List<BlockInfo> blockInfos = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_MAX_HOUR_PER_DAY; ++j)
            {
                if (data[i][day][j].teacher != null && teacher.id == data[i][day][j].teacher.id)
                {
                    blockInfos.add(new BlockInfo(i, day, j));
                }
            }
        }

        if (blockInfos.size() > 0)
        {
            return blockInfos.get(random.nextInt(blockInfos.size()));
        }

        return null;
    }

    public BlockInfo getBlockInfoByTeacher(TeacherGroup.Teacher teacher)
    {

        List<BlockInfo> blockInfos = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_GROUPS; ++i)
        {
            for (int j = 0; j < NUMBER_OF_DAYS; ++j)
            {
                for (int k = 0; k < NUMBER_OF_MAX_HOUR_PER_DAY; ++k)
                {
                    if (data[i][j][k].teacher != null && teacher.id == data[i][j][k].teacher.id)
                    {
                        blockInfos.add(new BlockInfo(i, j, k));
                    }
                }
            }
        }

        if (blockInfos.size() > 0)
        {
            return blockInfos.get(random.nextInt(blockInfos.size()));
        }

        return null;
    }

    public SwapInfo swapBlocks(SwapInfo swapInfo)
    {
        try  {
            Block temp = data[swapInfo.first.group][swapInfo.first.day][swapInfo.first.hour];
            data[swapInfo.first.group][swapInfo.first.day][swapInfo.first.hour] = data[swapInfo.second.group][swapInfo.second.day][swapInfo.second.hour];
            data[swapInfo.second.group][swapInfo.second.day][swapInfo.second.hour] = temp;
        }
        catch (Exception c)
        {
            System.out.println();
        }

        return swapInfo.invert();
    }

    public UpdateInfo updateBlock(UpdateInfo updateInfo)
    {
        data[updateInfo.blockInfo.group][updateInfo.blockInfo.day][updateInfo.blockInfo.hour].teacher = updateInfo.secondTeacher;

        return updateInfo.invert();
    }
}
