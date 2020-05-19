import java.io.*;
import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.*;
import model.FunctionCalls;
import model.FunctionData;
import model.Types;
import visitor.myvisitor;
import visitor.myvisitor2;

import java.util.*;

public class ParserTest
{
    public static void main(String[] args)
    {
        try
        {
            Parser parser =
                    new Parser(new Lexer(new PushbackReader(new FileReader(args[0].toString()), 1024)));

            Hashtable<String, LinkedList<FunctionData>> functions = new Hashtable<>();
            Hashtable<String, Types> variables = new Hashtable<>();
            Hashtable<String, FunctionCalls> functionCalls = new Hashtable<>();

            Start ast = parser.parse();
            ast.apply(new myvisitor(functions, variables));
            ast.apply(new myvisitor2(functions, variables, functionCalls));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
