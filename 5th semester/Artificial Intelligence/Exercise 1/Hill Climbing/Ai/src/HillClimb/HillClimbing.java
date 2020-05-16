package HillClimb;

import Constraints.*;
import Logic.Evaluator;
import Model.Info;
import Model.State;
import Utils.InitialRandomState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HillClimbing
{
    private State state;
    private Info info;
    private List<Constraint> constraints;
    private Evaluator evaluator;

    public HillClimbing(Info info)
    {
        this.info = info;

        if (info == null)
        {
            System.err.println("Null value in info.");
            System.exit(-1);
        }

        InitialRandomState stateGenerator= new InitialRandomState();
        state = stateGenerator.generateRandomState(info);
        constraints = new ArrayList<>();
        //Hard constraints
        constraints.add(new LessonMaxHours(5));
        constraints.add(new ConcurrentLessons(4));
        constraints.add(new TeacherMaxHoursWeekly(2));
        constraints.add(new TeacherMaxHoursDaily(1));
        //Soft constraints
        constraints.add(new UniformLessons(0.5));
        constraints.add(new UniformHours(0.2));
        constraints.add(new EmptyHours(0.1));
        constraints.add(new ConsecutiveHours(0.02));
        constraints.add(new UniformTeacherHours(0.01));
    }

    public State run(double threshold, int numberOfSteps)
    {
        evaluator = new Evaluator(constraints);

        double rating = evaluator.evaluateState(info, state);
        for (int i = 0; i < numberOfSteps && rating > threshold; ++i)
        {
            Collections.shuffle(evaluator.listConstraints.subList(4, 9));

            for (Constraint constraint : evaluator.listConstraints)
            {
                if (constraint.faults.size() > 0)
                {
                    constraint.resolve(info, state);
                    double temp = evaluator.evaluateState(info, state);

                    if (temp < rating)
                    {
                        rating = temp;
                    }
                    else
                    {
                        constraint.undo(info, state);
                        evaluator.evaluateState(info, state);
                    }

                    break;
                }
            }
            System.out.println("Step : " + i + ", rating: " + rating);
        }

        //If the there are remaining unsolved hard constraints return null
        for (int i = 0; i < 4; ++i)
        {
            if (evaluator.listConstraints.get(i).faults.size() > 0)
            {
                return null;
            }
        }

        for (int i = 0; i < evaluator.listConstraints.size(); ++i)
        {
            System.out.println(evaluator.listConstraints.get(i).getClass() + " " + evaluator.listConstraints.get(i).faults.size());
        }

        return state;
    }
}
