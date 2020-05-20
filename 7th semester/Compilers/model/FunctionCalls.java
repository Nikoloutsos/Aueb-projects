package model;

import java.util.LinkedList;

public class FunctionCalls
{
    public LinkedList<Types> args;
    public String name;

    public FunctionCalls(String name)
    {
        this.name = name;
        this.args = new LinkedList<>();
    }
}
