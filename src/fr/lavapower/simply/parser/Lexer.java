package fr.lavapower.simply.parser;

import fr.lavapower.simply.Error;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer
{
    public static String[] allsymbols = new String[] { ";", "(", ")", "'", "\""};
    public static String[][] symbols = new String[][] {{"", "[;]", ""}, {"", "[(]", ""}, {"", "[)]", ""}, {"", "[']", ""}, {"", "[\"]", ""}};
    public static String[] others_symbols = new String[] {};

    public static ArrayList<Token> tokenize(String program) {
        program = prepareProgram(program);
        ArrayList<String> lexical_units = new ArrayList<>(Arrays.asList(program.split(" ")));
        ArrayList<Token> tokens = new ArrayList<>();

        while(lexical_units.size() > 0) {
            String lexical_unit = lexical_units.remove(0);
            if(!lexical_unit.isEmpty()) {
                switch(lexical_unit) {
                    case "(":
                        tokens.add(new Token(lexical_unit, TokenType.PAREN_OPEN));
                        break;
                    case ")":
                        tokens.add(new Token(lexical_unit, TokenType.PAREN_CLOSE));
                        break;
                    case "print":
                    case "println":
                        tokens.add(new Token(lexical_unit, TokenType.KEYWORD));
                        break;
                    case ";":
                        tokens.add(new Token(lexical_unit, TokenType.SEMI_COLON));
                        break;
                    case "\"":
                        StringBuilder textDouble = new StringBuilder();
                        lexical_unit = lexical_units.remove(0);
                        while(!lexical_unit.equals("\"")) {
                            if(textDouble.length() == 0)
                                textDouble.append(lexical_unit);
                            else
                                textDouble.append(" ").append(lexical_unit);
                            if(lexical_units.size() == 0)
                                new Error("SyntaxError", "Unexpected End Of Program. Missing '\"'");
                            lexical_unit = lexical_units.remove(0);
                        }
                        String stringDouble = textDouble.toString();
                        for(String symbol: allsymbols)
                            stringDouble = stringDouble.replace(" "+symbol+" ", symbol);
                        tokens.add(new Token(stringDouble, TokenType.STRING));
                        break;
                    case "'":
                        StringBuilder textSimple = new StringBuilder();
                        lexical_unit = lexical_units.remove(0);
                        while(!lexical_unit.equals("'")) {
                            if(textSimple.length() == 0)
                                textSimple.append(lexical_unit);
                            else
                                textSimple.append(" ").append(lexical_unit);
                            if(lexical_units.size() == 0)
                                new Error("SyntaxError", "Unexpected End Of Program. Missing \"'\"");
                            lexical_unit = lexical_units.remove(0);
                        }
                        String stringSimple = textSimple.toString();
                        for(String symbol: allsymbols)
                            stringSimple = stringSimple.replace(" "+symbol+" ", symbol);
                        tokens.add(new Token(stringSimple, TokenType.STRING));
                        break;
                    default:
                        try {
                            Integer.parseInt(lexical_unit);
                            tokens.add(new Token(lexical_unit, TokenType.INTEGER));
                        }
                        catch(NumberFormatException ignored) {
                            new Error("SyntaxError", "Token Unknown.\n - Text : "+lexical_unit);
                        };
                }
            }
        }
        return tokens;
    }

    private static String prepareProgram(String program) {
        program = program.replace("\n", "").replace("\t", "");
        for(String[] symbol: symbols) {
            if(!symbol[0].isEmpty() && !symbol[2].isEmpty()) { program = program.replaceAll("("+symbol[0]+")("+symbol[1]+")("+symbol[1]+")", "$1 $2 $3"); }
            else if(!symbol[0].isEmpty()) { program = program.replaceAll("("+symbol[0]+")("+symbol[1]+")", "$1 $2 "); }
            else if(!symbol[2].isEmpty()) { program = program.replaceAll("("+symbol[1]+")("+symbol[2]+")", " $1 $2"); }
            else { program = program.replaceAll("("+symbol[1]+")", " $1 "); }
        }
        for(String symbol: others_symbols) {
            program = program.replace(symbol, " "+symbol+" ");
        }
        return program;
    }
}
