package Model;

import java.util.*;

/**
 * This class contains all the info about the imported data.
 * It manipulates the data so that it is easier for us to do the heavy work.
 */
public class Info
{
    public HashMap<Integer, LessonGroup.Lesson> lessonList;
    public HashMap<Integer, TeacherGroup.Teacher> teacherList;
    public HashMap<String, List<LessonGroup.Lesson>> classLessons;
    public HashMap<Integer, List<TeacherGroup.Teacher>> subjectTeachers;

    public LessonGroup lessonGroup;
    public TeacherGroup teacherGroup;

    public static final int NUMBER_OF_GROUPS = 9;
    public static final int NUMBER_OF_GROUPS_PER_CLASS = 3;
    public static final int NUMBER_OF_DAYS = 5;
    public static final int NUMBER_OF_MAX_HOUR_PER_DAY = 7;


    public Info(LessonGroup l, TeacherGroup t) {
        this.lessonGroup = l;
        this.teacherGroup = t;
        generateLessonList();
        generateTeacherList();
        generateClassLessons();
        generateSubjectTeachers();
    }

    private void generateLessonList()
    {
        lessonList = new HashMap<>();
        for (LessonGroup.Lesson lesson: lessonGroup.lessonList)
        {
            if (!lessonList.containsKey(lesson.mId))
            {
                lessonList.put(lesson.mId, lesson);
            }
        }
    }

    private void generateTeacherList()
    {
        teacherList = new HashMap<>();
        for (TeacherGroup.Teacher teacher : teacherGroup.teachersList)
        {
            if (!teacherList.containsKey(teacher.id))
            {
                teacherList.put(teacher.id, teacher);
            }
        }
    }

    private void generateClassLessons()
    {
        classLessons = new HashMap<>();
        for (LessonGroup.Lesson lesson: lessonGroup.lessonList)
        {
            if (!classLessons.containsKey(lesson.mClass))
            {
                classLessons.put(lesson.mClass, new ArrayList<>());
            }
            classLessons.get(lesson.mClass).add(lesson);
        }
    }

    private void generateSubjectTeachers()
    {
        subjectTeachers = new HashMap<>();
        for(TeacherGroup.Teacher teacher: teacherGroup.teachersList)
        {
            for(Integer lessonId: teacher.lessons)
            {
                if(!subjectTeachers.containsKey(lessonId))
                {
                    subjectTeachers.put(lessonId, new ArrayList<>());
                }
                subjectTeachers.get(lessonId).add(teacher);
            }
        }
    }
}
