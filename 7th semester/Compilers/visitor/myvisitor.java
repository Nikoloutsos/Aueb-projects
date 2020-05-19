package visitor;

import minipython.analysis.*;
import minipython.node.*;
import model.FunctionData;
import model.Types;

import java.util.*;

//This visitor checks for Function Duplicates
//and prepares the functionData table for visitor2
public class myvisitor extends DepthFirstAdapter
{
	private Hashtable<String, LinkedList<FunctionData>> functions;
	private Hashtable<String, Types> variables;

	private FunctionData curFunc;

	public myvisitor(Hashtable<String, LinkedList<FunctionData>> functions, Hashtable<String, Types> variables)
	{
		this.functions = functions;
		this.variables = variables;
	}

	private void alreadyDefined(int line, String name)
	{
		name = name.substring(0, name.lastIndexOf(' '));
		System.err.println("Line " + line + ": " + "Function" + ' ' + name + " is already defined");
	}

	//We just got in a function so we create a new model.FunctionData object
	@Override
	public void inAFunction(AFunction node)
	{
		curFunc = new FunctionData(node.getId().toString());
	}

	//We now exit the function declaration so, since we have gathered all required info,
	//we need to check for duplicates
	@Override
	public void outAFunction(AFunction node)
	{
		//If a function(s) with the same name exists
		if(functions.containsKey(curFunc.name))
		{
			//Get all of these functions
			for(FunctionData f : functions.get(curFunc.name))
			{
				//And check whether or not there is a function with the same arguments
				if((f.getMinArguments() == curFunc.getMinArguments()) || f.args.size() == curFunc.args.size())
				{
					alreadyDefined(node.getId().getLine(), curFunc.name);
					return;
				}
			}
			functions.get(curFunc.name).add(curFunc);
		}
		LinkedList<FunctionData> temp = new LinkedList<>();
		temp.add(curFunc);
		functions.put(curFunc.name, temp);
	}

	//Keep the expression of the return statement
	@Override
	public void inARetStatement(ARetStatement node)
	{
		curFunc.returnExpression = node.getExpression();
	}

	//We found a parameter of the curFunction with a default value
	@Override
	public void inASinglevArgument(ASinglevArgument node)
	{
		TId id = node.getId();
		curFunc.args.put(node.getId().toString(), getValueType(node.getValue()));

		variables.put(node.getId().toString(), getValueType(node.getValue()));
	}

	//We found a parameter of the curFunction without default value
	@Override
	public void inASingleArgument(ASingleArgument node)
	{
		TId id = node.getId();
		curFunc.args.put(node.getId().toString(), Types.NAN);

		variables.put(node.getId().toString(), Types.NAN);
	}

	private Types getValueType(PValue value)
	{
		//if this value is an instance of a String then it is a String
		if(value instanceof AStrngLtrlValue)
		{
			return Types.STRING;
		}
		//if this value is an instance of Null then it is Null
		else if (value instanceof ANullValue)
		{
			return Types.NULL;
		}
		//if it is an instance of a functionCall then print an error
		else if(value instanceof AFCallValue)
		{
			AFCallValue retFunc = ((AFCallValue) value);
			System.err.println("Line " + retFunc.getId().getLine() + ": Cannot have function call as a default value of parameter.");
			return Types.NAN;
		}
		//In any other case it is a Number
		else
		{
			return Types.NUMERIC;
		}
	}
}
