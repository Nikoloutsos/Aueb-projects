package model;

import minipython.node.PExpression;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

public class FunctionData
{
    public String name;
    public Hashtable<String, Types> args;
    public Types ret;
    public PExpression returnExpression = null;

    public FunctionData(String name)
    {
        this.name = name;
        args = new Hashtable<>();
        ret = Types.VOID;
    }

    public int getMinArguments()
    {
        int ret = 0;
        for (Types t : args.values())
        {
            if(t == Types.NAN)
            {
                ret++;
            }
        }
        return ret;
    }
}
