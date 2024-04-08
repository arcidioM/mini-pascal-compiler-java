package model;

import java.util.ArrayList;

public class Lexer {

    private ArrayList<Token> tokens;
    private int currentPosition;

    public Lexer() {
        tokens = new ArrayList<>();
        currentPosition = 0;
    }

    public void tokenize(String code) {
        String[] lines = code.split("\n");

        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            String line = lines[lineNumber];
            processLine(line, lineNumber + 1);
        }
    }

    private void processLine(String line, int lineNumber) {
        StringBuilder lexeme = new StringBuilder();
        int index = 0;
        int lineLength = line.length();

        while (index < lineLength) {
            char currentChar = line.charAt(index);

            if (Character.isWhitespace(currentChar)) {
                index++;
                continue;
            }

            if (isOperator(currentChar)) {
                lexeme.append(currentChar);
                index++;

                if (index < lineLength && isTwoCharSymbol(currentChar, line.charAt(index))) {
                    lexeme.append(line.charAt(index));
                    index++;
                }

                addToken(lexeme.toString(), "OPERATOR", lineNumber);
                lexeme.setLength(0);
                continue;
            }

            if (isSymbol(currentChar)) {
                lexeme.append(currentChar);
                index++;

                if (index < lineLength && isTwoCharSymbol(currentChar, line.charAt(index))) {
                    lexeme.append(line.charAt(index));
                    index++;
                }

                addToken(lexeme.toString(), "SYMBOL", lineNumber);
                lexeme.setLength(0);
                continue;
            }

            if (Character.isDigit(currentChar)) {
                lexeme.append(currentChar);
                index++;
                while (index < lineLength && Character.isDigit(line.charAt(index))) {
                    lexeme.append(line.charAt(index));
                    index++;
                }
                addToken(lexeme.toString(), "NUMBER", lineNumber);
                lexeme.setLength(0);
                continue;
            }

            if (currentChar == '\'') {
                lexeme.append(currentChar);
                index++;
                while (index < lineLength && line.charAt(index) != '\'') {
                    lexeme.append(line.charAt(index));
                    index++;
                }
                if (index < lineLength && line.charAt(index) == '\'') {
                    lexeme.append(line.charAt(index));
                    index++;
                }
                addToken(lexeme.toString(), "CHARACTER_CONSTANT", lineNumber);
                lexeme.setLength(0);
                continue;
            }

            if (Character.isLetter(currentChar)) {
                lexeme.append(currentChar);
                index++;
                while (index < lineLength && (Character.isLetterOrDigit(line.charAt(index)) || line.charAt(index) == '_')) {
                    lexeme.append(line.charAt(index));
                    index++;
                }
                if (isKeyword(lexeme.toString())) {
                    addToken(lexeme.toString(), "KEYWORD", lineNumber);
                } else {
                    addToken(lexeme.toString(), isValidIdentifier(lexeme.toString()) ? "IDENTIFIER" : "INVALID_IDENTIFIER", lineNumber);
                }
                lexeme.setLength(0);
                continue;
            }

            if (currentChar == '/') {
                index++;
                if (index < lineLength && line.charAt(index) == '*') {
                    while (index < lineLength - 1 && !(line.charAt(index) == '*' && line.charAt(index + 1) == '/')) {
                        index++;
                    }
                    index += 2;
                    continue;
                } else {
                    lexeme.append('/');
                    addToken(lexeme.toString(), "OPERATOR", lineNumber);
                    lexeme.setLength(0);
                    continue;
                }
            }

            if (currentChar == '\n') {
                lexeme.append("\\n");
                addToken(lexeme.toString(), "NEWLINE", lineNumber);
                lexeme.setLength(0);
                index++;
                continue;
            }

            lexeme.append(currentChar);
            index++;
        }
    }

    private boolean isOperator(char c) {
        return "+-*/=<>".indexOf(c) != -1;
    }

    private boolean isSymbol(char c) {
        return ";.,:()[]".indexOf(c) != -1;
    }

    private boolean isTwoCharSymbol(char firstChar, char secondChar) {
        String symbol = "" + firstChar + secondChar;
        String[] twoCharSymbols = {":="};
        for (String s : twoCharSymbols) {
            if (s.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean isKeyword(String lexeme) {
        String[] keywords = {
            "program", "var", "array", "of", "begin", "end",
            "if", "then", "else", "while", "do", "read",
            "write", "div", "or", "and", "not", "true",
            "false", "char", "integer", "boolean", "writeln"
        };
        for (String keyword : keywords) {
            if (keyword.equals(lexeme)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidIdentifier(String identifier) {
        if (identifier.isEmpty()) {
            return false;
        }

        char firstChar = identifier.charAt(0);
        if (!((firstChar >= 'a' && firstChar <= 'z') || (firstChar >= 'A' && firstChar <= 'Z'))) {
            return false;
        }

        for (int i = 1; i < identifier.length(); i++) {
            char currentChar = identifier.charAt(i);
            if (!((currentChar >= 'a' && currentChar <= 'z') || (currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= '0' && currentChar <= '9') || currentChar == '_')) {
                return false;
            }
        }

        return true;
    }

    private void addToken(String lexeme, String tokenType, int lineNumber) {
        tokens.add(new Token(lexeme, tokenType, lineNumber));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void printTokens() {
        for (Token token : tokens) {
            System.out.println("Line " + token.getLineNumber() + ": " + token.getLexeme() + " : " + token.getTokenType());
        }
    }

    public void clearTokens() {
        tokens.clear();
    }
}
