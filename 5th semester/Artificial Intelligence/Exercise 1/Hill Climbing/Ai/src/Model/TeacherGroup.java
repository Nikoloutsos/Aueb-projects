package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import static Model.Info.NUMBER_OF_DAYS;
import static Model.Info.NUMBER_OF_MAX_HOUR_PER_DAY;

public class TeacherGroup {

    @SerializedName("teachers")
    @Expose
    public List<Teacher> teachersList;

    public class Teacher {

        @SerializedName("teacher_id")
        @Expose
        public int id;

        @SerializedName("teacher_name")
        @Expose
        public String name;

        @SerializedName("lessons_teached")
        @Expose
        public List<Integer> lessons;

        @SerializedName("hours_daily")
        @Expose
        public int hoursDaily;

        @SerializedName("hours_weekly")
        @Expose
        public int hoursWeekly;
    }
}
