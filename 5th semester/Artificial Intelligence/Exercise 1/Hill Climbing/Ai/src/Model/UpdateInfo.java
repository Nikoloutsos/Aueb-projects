package Model;

public class UpdateInfo
{
    public TeacherGroup.Teacher firstTeacher, secondTeacher;
    public BlockInfo blockInfo;

    public UpdateInfo(TeacherGroup.Teacher firstTeacher, TeacherGroup.Teacher secondTeacher, BlockInfo blockInfo)
    {
        this.firstTeacher = firstTeacher;
        this.secondTeacher = secondTeacher;
        this.blockInfo = blockInfo;
    }

    public UpdateInfo invert()
    {
        return new UpdateInfo(secondTeacher, firstTeacher, blockInfo);
    }
}
