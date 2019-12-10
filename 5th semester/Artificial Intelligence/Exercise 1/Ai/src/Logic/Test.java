package Logic;

import HillClimb.HillClimbing;
import Model.*;
import Utils.ParserUtil;

import java.io.IOException;

public class Test {

    public static void main(String[] args)
    {
        ParserUtil parserUtil = ParserUtil.getInstance();
        try
        {
            LessonGroup l = parserUtil.parseIt(args[0], LessonGroup.class);
            TeacherGroup t = parserUtil.parseIt(args[1], TeacherGroup.class);
            Info info = new Info(l, t);

            HillClimbing c = new HillClimbing(info);
            State state = c.run(Integer.parseInt(args[2]), Integer.parseInt(args[3]));

            if (state == null)
            {
                System.out.println("Hard constraints could not be solved. Make sure the data are valid and try to increase maximum steps.");
            }
            else
            {
                parserUtil.saveStateToFile(state);
            }

        }
        catch (IOException e)
        {
            System.out.println("Error at reading the file");
            e.printStackTrace();
        }

    }
}
