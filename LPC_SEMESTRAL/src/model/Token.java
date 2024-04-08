package model;

public class Token {

    private String lexeme;
    private String tokenType;
    private int lineNumber;

    public Token(String lexeme, String tokenType, int lineNumber) {
        this.lexeme = lexeme;
        this.tokenType = tokenType;
        this.lineNumber = lineNumber;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getLineNumber() {
        return lineNumber;
    }

}
