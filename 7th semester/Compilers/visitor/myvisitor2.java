package visitor;

import minipython.analysis.*;
import minipython.node.*;
import model.FunctionCalls;
import model.FunctionData;
import model.Types;

import java.util.*;

//This visitor:
// Saves all variables and their types.
// Checks declaration when a variable is used
//

public class myvisitor2 extends DepthFirstAdapter
{
    private Hashtable<String, LinkedList<FunctionData>> functions;
    private Hashtable<String, FunctionCalls> functionCalls;
    private Hashtable<String, Types> operations;

    private FunctionCalls curFunc = null;

    private boolean inPrint = false;
    private boolean inReturn = false;

    private int curLine = 0;

    public myvisitor2(Hashtable<String, LinkedList<FunctionData>> functions, Hashtable<String, Types> variables, Hashtable<String, FunctionCalls> functionCalls)
    {
        this.functions = functions;
        this.operations = variables;
        this.functionCalls = functionCalls;
    }

    private void notDefined(int line, String type, String name)
    {
        System.err.println("Line " + line + ": " + type + ' ' + name + "is not defined");
    }

    private boolean checkVariableDefinition(TId node, boolean print)
    {
        String vName = node.toString();
        if (!operations.containsKey(vName))
        {
            if(print)
            {
                int line = node.getLine();
                notDefined(line, "Variable", vName);
            }
            return false;
        }
        return true;
    }

    //We just found a use of an array so we check if it has been declared
    @Override
    public void inAArrExpression(AArrExpression node)
    {
        checkVariableDefinition(node.getId(), true);
    }

    //We just found a use of an identifier so we check if it has been declared
    @Override
    public void inAIdentifierExpression(AIdentifierExpression node)
    {
        checkVariableDefinition(node.getId(), true);
    }

    //Here we declare a new variable se need to keep track of what value type it is.
    @Override
    public void inAAssignStatement(AAssignStatement node)
    {
        String vName = node.getId().toString();
        operations.put(vName, getExpressionType(node.getExpression()));
    }

    //Here we declare a new array se need to keep track of what value type it is.
    @Override
    public void inAArrStatement(AArrStatement node)
    {
        String vName = node.getId().toString();
        operations.put(vName, getExpressionType(node.getItem()));
    }

    //Found a new FunctionCall so we create a new functionData
    @Override
    public void inAFunctionCall(AFunctionCall node)
    {
        curFunc = new FunctionCalls(node.getId().toString());
    }

    //Save function's argument model.Types
    @Override
    public void inASingleArglist(ASingleArglist node)
    {
        if(curFunc != null)
        {
            curFunc.args.add(getExpressionType(node.getExpression()));
        }
    }

    //Save function's argument model.Types
    @Override
    public void inAMultiArglist(AMultiArglist node)
    {
        if(curFunc != null)
        {
            curFunc.args.add(getExpressionType(node.getExpression()));
        }
    }

    //We are now leaving the function so we need to check if the arguments are correct and get its return Type
    @Override
    public void outAFunctionCall(AFunctionCall node)
    {
        //Get functionData and print error if not found
        FunctionData f = getFunctionData(node, true);
        if(f != null)
        {
            //Make String array of all parameter names of found function that we are calling
            String[] fTypes = f.args.keySet().toArray(new String[f.args.size()]);

            //Foreach parameter set its type equal to the functionCall parameter types
            for(int i = 0; i < curFunc.args.size(); i++)
            {
                operations.put(fTypes[i], curFunc.args.get(i));
            }
            //Get its type
            f.ret = getExpressionType(f.returnExpression);
        }
    }

    //When we leave a numeric operation we check if it is correct
    @Override
    public void outAAddExpression(AAddExpression node)
    {
        PExpression a = node.getA();
        PExpression b = node.getB();
        getOperationType(a, b);
    }

    @Override
    public void outASubExpression(ASubExpression node)
    {
        PExpression a = node.getA();
        PExpression b = node.getB();
        getOperationType(a, b);
    }

    @Override
    public void outAMultExpression(AMultExpression node)
    {
        PExpression a = node.getA();
        PExpression b = node.getB();
        getOperationType(a, b);
    }

    @Override
    public void outADivExpression(ADivExpression node)
    {
        PExpression a = node.getA();
        PExpression b = node.getB();
        getOperationType(a, b);
    }

    @Override
    public void outAModExpression(AModExpression node)
    {
        PExpression a = node.getA();
        PExpression b = node.getB();
        getOperationType(a, b);
    }

    @Override
    public void outAPowExpression(APowExpression node)
    {
        PExpression a = node.getA();
        PExpression b = node.getB();
        getOperationType(a, b);
    }

    @Override
    public void outAPlusoneExpression(APlusoneExpression node)
    {
        checkSingleOperation(node.getExpression());
    }

    @Override
    public void outAMinusoneExpression(AMinusoneExpression node)
    {
        checkSingleOperation(node.getExpression());
    }

    @Override
    public void outAOneplusExpression(AOneplusExpression node)
    {
        checkSingleOperation(node.getExpression());
    }

    @Override
    public void outAOneminusExpression(AOneminusExpression node)
    {
        checkSingleOperation(node.getExpression());
    }

    //When we meet a token we keep track of current line
    @Override
    public void caseTId(TId node)
    {
        super.caseTId(node);
        curLine = node.getLine();
    }

    @Override
    public void caseTNumber(TNumber node)
    {
        super.caseTNumber(node);
        curLine = node.getLine();
    }

    @Override
    public void caseTNull(TNull node)
    {
        super.caseTNull(node);
        curLine = node.getLine();
    }

    @Override
    public void caseTString(TString node)
    {
        super.caseTString(node);
        curLine = node.getLine();
    }

    //Flag for when we are in a return statement
    @Override
    public void inARetStatement(ARetStatement node)
    {
        inReturn = true;
    }

    @Override
    public void outARetStatement(ARetStatement node)
    {
        inReturn = false;
    }

    private void getOperationType(PExpression a, PExpression b)
    {
        //If we are in a return statement ignore the check since we don't know what each variable might be
        if (!inReturn)
        {
            Types at, bt;
            //Get a Type
            if (operations.containsKey(a.toString()))
            {
                at = operations.get(a.toString());
            } else
            {
                at = getExpressionType(a);
            }

            //Get b Type
            if (operations.containsKey(b.toString()))
            {
                bt = operations.get(b.toString());
            } else
            {
                bt = getExpressionType(b);
            }

            //If model.Types are the both Numeric or NAN it's fine
            if ((at == Types.NUMERIC && bt == Types.NUMERIC) || (at == Types.NAN && bt == Types.NAN))
            {
                operations.put(a.parent().toString(), at);
            }
            //Else print error and set it's type as Numeric to avoid further errors
            else
            {
                System.err.println(String.format("Line %d: Numeric operations cannot be performed between %s type and %s type", curLine, at.toString(), bt.toString()));
                operations.put(a.parent().toString(), Types.NUMERIC);
            }
        }
    }

    private FunctionData getFunctionData(AFunctionCall node, boolean print)
    {
        //if we cannot find it then print error
        if(!functions.containsKey(node.getId().toString()))
        {
            if(print)
            {
                notDefined(node.getId().getLine(), "Function", node.getId().toString());
            }
        }
        else
        {
            //Check all function with same name
            for(FunctionData f : functions.get(node.getId().toString()))
            {
                //If they have the same parameters then we are calling this one so return it
                if(f.args.size() >= curFunc.args.size() && f.getMinArguments() <= curFunc.args.size())
                {
                    f.ret = getExpressionType(f.returnExpression);
                    return f;
                }
            }
            //If nothing found print an error
            System.err.println("Line " + node.getId().getLine() + ": Arguments for function " + curFunc.name + " do not match any overload");
        }
        return null;
    }

    private Types getExpressionType(PExpression node)
    {
        //Cast node to the appropriate PExpression and then return its type
        if (node instanceof AValueExpression)
        {
            PValue value = ((AValueExpression) node).getValue();
            return getValueType(value);
        } else if (node instanceof AFCallExpression)
        {
            FunctionData f = getFunctionData(((AFunctionCall) ((AFCallExpression) node).getFunctionCall()), false);
            return f == null ? Types.NUMERIC : f.ret;
        } else if (node instanceof AIdentifierExpression)
        {
            TId id = ((AIdentifierExpression) node).getId();
            if (!checkVariableDefinition(id, false))
            {
                return Types.NAN;
            }
            return operations.get(id.toString());
        } else if (node instanceof AArrExpression)
        {
            TId id = ((AArrExpression) node).getId();
            if (!checkVariableDefinition(id, false))
            {
                return Types.NAN;
            }
            return operations.get(id.toString());
        }
        //If its nothing of the above then it must be NUMERIC
        else
        {
            return Types.NUMERIC;
        }
    }

    private Types getValueType(PValue value)
    {
        if(value instanceof AStrngLtrlValue)
        {
            return Types.STRING;
        }
        else if (value instanceof ANullValue)
        {
            return Types.NULL;
        }
        else if(value instanceof AFCallValue)
        {
            String retFunc = ((AFCallValue) value).getId().toString();
            return operations.get(retFunc);
        }
        else
        {
            return Types.NUMERIC;
        }
    }

    private void checkSingleOperation(PExpression node)
    {
        //Same as getOperationType but for single operations (eg. i++)
        Types t = getExpressionType(node);
        if(t != Types.NUMERIC)
        {
            System.err.println(String.format("Line %d: This type of operations must only be performed on Numeric variables", curLine));
        }
        operations.put(node.parent().toString(), Types.NUMERIC);
    }
}
