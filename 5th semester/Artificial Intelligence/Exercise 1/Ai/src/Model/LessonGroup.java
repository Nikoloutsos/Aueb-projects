package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LessonGroup {

    @SerializedName("lessons")
    @Expose
    public List<Lesson> lessonList;

    public class Lesson
    {
        @SerializedName("id")
        @Expose
        public int mId;

        @SerializedName("title")
        @Expose
        public String mTitle;

        @SerializedName("class")
        @Expose
        public String mClass;

        @SerializedName("hours")
        @Expose
        public int mHours;
    }
}
