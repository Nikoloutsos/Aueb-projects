package Logic;

import Constraints.Constraint;
import Model.Info;
import Model.State;

import java.util.List;

public class Evaluator
{
    public List<Constraint> listConstraints;

    public Evaluator(List<Constraint> listConstraints)
    {
        this.listConstraints = listConstraints;
    }

    public double evaluateState(Info info, State state)
    {
        double score = 0;
        for (Constraint listConstraint : listConstraints)
        {
            score += listConstraint.evaluate(info, state);
        }
        return score;
    }
}
