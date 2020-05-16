package Constraints;

import Model.*;
import java.util.List;

public abstract class Constraint
{
    public double weight;
    public List<FaultInfo> faults;


    public abstract double evaluate(Info info, State state);
    public abstract void resolve(Info info, State state);
    public abstract void undo(Info info, State state);
}
