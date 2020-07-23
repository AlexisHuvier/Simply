package fr.lavapower.simply.parser;

import fr.lavapower.simply.Error;
import fr.lavapower.simply.instructions.Instruction;
import fr.lavapower.simply.instructions.PrintExpression;
import fr.lavapower.simply.instructions.PrintlnExpression;
import fr.lavapower.simply.objects.Expression;
import fr.lavapower.simply.objects.ExpressionType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Parser
{
    private static ArrayList<Token> tokens;
    private static int cursor;
    private static Token current;
    private static Queue<Instruction> instructions;

    public static Queue<Instruction> parse(String program) {
        instructions = new ArrayDeque<>();
        tokens = Lexer.tokenize(program);
        cursor = -1;
        current = null;
        statements();
        if(cursor != tokens.size() - 1)
            syntaxError("Unexpected token");
        return instructions;
    }

    //Utilities
    private static void forwardCursor(String expected) {
        cursor++;
        if(tokens.size() > cursor)
            current = tokens.get(cursor);
        else
        {
            current = null;
            if(expected != null)
                syntaxError("Excepted " + expected);
        }
    }

    private static void syntaxError(String message) {
        new Error("SyntaxError", message+"\n - Token : "+current+"\n - Cursor : "+cursor);
    }

    //Parsing
    private static void statements() {
        statement();
        while(current.getType() == TokenType.SEMI_COLON) {
            if(tokens.size()-1 > cursor)
                statement();
            else
                break;
        }
    }

    private static void statement() {
        forwardCursor("keyword");
        if(current.getType() == TokenType.KEYWORD) {
            if(current.getValue().equals("print"))
                functionStatement(current.getValue());
            else if(current.getValue().equals("println"))
                functionStatement(current.getValue());
            else
                syntaxError("Unknown keyword.");
        }
        else
            syntaxError( "Expected keyword.");
    }

    private static void functionStatement(String function) {
        forwardCursor("open parenthesis");
        if(current.getType() == TokenType.PAREN_OPEN) {
            Expression expression = expression();
            if(expression == null) {
                if(current.getType() != TokenType.PAREN_CLOSE)
                    syntaxError("Expected close parenthesis or Expression");
            }
            else
            {
                if(function.equals("print"))
                    instructions.add(new PrintExpression(expression));
                else if(function.equals("println"))
                    instructions.add(new PrintlnExpression(expression));
                forwardCursor("close parenthesis");
                if(current.getType() != TokenType.PAREN_CLOSE)
                    syntaxError("Expected close parenthesis");
            }
            forwardCursor("semi colon");
            if(current.getType() != TokenType.SEMI_COLON)
                syntaxError("Expected semi colon");
        }
        else
            syntaxError("Expected open parenthesis");
    }

    private static Expression expression() {
        forwardCursor("close parenthesis");
        return simpleExpression();
    }

    private static Expression simpleExpression() {
        if(current.getType() == TokenType.INTEGER)
            return new Expression(current.getValue(), ExpressionType.INT);
        else if(current.getType() == TokenType.STRING)
            return new Expression(current.getValue(), ExpressionType.STRING);
        return null;
    }
}
