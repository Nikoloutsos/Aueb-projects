package Model;

public class FaultInfo
{
    public TeacherGroup.Teacher teacher;
    public LessonGroup.Lesson lesson;
    public int group;
    public int day;
    public int hour;
    public double offset;

    public FaultInfo(TeacherGroup.Teacher teacher, LessonGroup.Lesson lesson, int group, int day, int hour, double offset)
    {
        this.teacher = teacher;
        this.lesson = lesson;
        this.group = group;
        this.day = day;
        this.hour = hour;
        this.offset = offset;
    }

    @Override
    public boolean equals(Object obj)
    {
        FaultInfo info = (FaultInfo)obj;
        if (info.teacher == teacher && info.lesson == lesson && info.group == group && info.day == day && info.hour == hour) return true;
        return false;
    }
}
