import com.bezkup.mbare.Scanner;
import com.bezkup.mbare.Token;
import com.bezkup.mbare.TokenType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Scanner Tests")
public class ScannerTest {

    @Test
    @DisplayName("Empty source returns only EOF token")
    public void scanTokens_withEmptySource_returnsEOFToken() {
        Scanner scanner = new Scanner("");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.getFirst().type);
    }

    @Test
    @DisplayName("Single character tokens are scanned correctly")
    public void scanTokens_withSingleCharacterTokens_returnsCorrectTokens() {
        Scanner scanner = new Scanner("(){},.-+;*");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(11, tokens.size());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).type);
        assertEquals(TokenType.LEFT_BRACE, tokens.get(2).type);
        assertEquals(TokenType.RIGHT_BRACE, tokens.get(3).type);
        assertEquals(TokenType.COMMA, tokens.get(4).type);
        assertEquals(TokenType.DOT, tokens.get(5).type);
        assertEquals(TokenType.MINUS, tokens.get(6).type);
        assertEquals(TokenType.PLUS, tokens.get(7).type);
        assertEquals(TokenType.SEMICOLON, tokens.get(8).type);
        assertEquals(TokenType.STAR, tokens.get(9).type);
        assertEquals(TokenType.EOF, tokens.get(10).type);
    }

    @Test
    @DisplayName("String literals are scanned with correct value")
    public void scanTokens_withStringLiteral_returnsStringToken() {
        Scanner scanner = new Scanner("\"hello\"");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(2, tokens.size());
        assertEquals(TokenType.STRING, tokens.get(0).type);
        assertEquals("hello", tokens.get(0).literal);
    }

    @Test
    @DisplayName("Unterminated strings are handled gracefully")
    public void scanTokens_withUnterminatedString_reportsError() {
        Scanner scanner = new Scanner("\"hello");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    @DisplayName("Comments are ignored during scanning")
    public void scanTokens_withComments_ignoresComments() {
        Scanner scanner = new Scanner("// this is a comment\n()");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).type);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    @DisplayName("Whitespace is ignored during scanning")
    public void scanTokens_withWhitespace_ignoresWhitespace() {
        Scanner scanner = new Scanner(" \t\r\n()");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).type);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    @DisplayName("Unexpected characters are handled with error")
    public void scanTokens_withUnexpectedCharacter_reportsError() {
        Scanner scanner = new Scanner("@");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    @DisplayName("Sicilian keywords are recognized correctly")
    public void scanTokens_withSicilianKeywords_returnsKeywordTokens() {
        Scanner scanner = new Scanner("variabbili stampa su sannunca mentri veru falsu nenti e macari o");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(11, tokens.size()); // 10 keywords + EOF
        assertEquals(TokenType.VAR, tokens.get(0).type);
        assertEquals(TokenType.PRINT, tokens.get(1).type);
        assertEquals(TokenType.IF, tokens.get(2).type);
        assertEquals(TokenType.ELSE, tokens.get(3).type);
        assertEquals(TokenType.WHILE, tokens.get(4).type);
        assertEquals(TokenType.TRUE, tokens.get(5).type);
        assertEquals(TokenType.FALSE, tokens.get(6).type);
        assertEquals(TokenType.NIL, tokens.get(7).type);
        assertEquals(TokenType.AND, tokens.get(8).type);
        assertEquals(TokenType.OR, tokens.get(9).type);
        assertEquals(TokenType.EOF, tokens.get(10).type);
    }

    @Test
    @DisplayName("Number literals are scanned correctly")
    public void scanTokens_withNumbers_returnsNumberTokens() {
        Scanner scanner = new Scanner("123 456.789");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.NUMBER, tokens.get(0).type);
        assertEquals(123.0, tokens.get(0).literal);
        assertEquals(TokenType.NUMBER, tokens.get(1).type);
        assertEquals(456.789, tokens.get(1).literal);
    }

    @Test
    @DisplayName("Comparison operators are scanned correctly")
    public void scanTokens_withComparisonOperators_returnsCorrectTokens() {
        Scanner scanner = new Scanner("== != < <= > >=");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(7, tokens.size());
        assertEquals(TokenType.EQUAL_EQUAL, tokens.get(0).type);
        assertEquals(TokenType.BANG_EQUAL, tokens.get(1).type);
        assertEquals(TokenType.LESS, tokens.get(2).type);
        assertEquals(TokenType.LESS_EQUAL, tokens.get(3).type);
        assertEquals(TokenType.GREATER, tokens.get(4).type);
        assertEquals(TokenType.GREATER_EQUAL, tokens.get(5).type);
    }

    @Test
    @DisplayName("Identifiers are scanned correctly")
    public void scanTokens_withIdentifiers_returnsIdentifierTokens() {
        Scanner scanner = new Scanner("foo bar baz123 _underscore");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(5, tokens.size()); // 4 identifiers + EOF
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(2).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).type);
        assertEquals(TokenType.EOF, tokens.get(4).type);
    }

    @Test
    @DisplayName("Complete program is scanned correctly")
    public void scanTokens_withCompleteProgram_returnsAllTokens() {
        Scanner scanner = new Scanner("variabbili x = 5\nstampa x");
        List<Token> tokens = scanner.scanTokens();
        // Should have: variabbili, x, =, 5, stampa, x, EOF = 7 tokens
        assertEquals(7, tokens.size());
        assertEquals(TokenType.VAR, tokens.get(0).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(1).type);
        assertEquals(TokenType.EQUAL, tokens.get(2).type);
        assertEquals(TokenType.NUMBER, tokens.get(3).type);
        assertEquals(TokenType.PRINT, tokens.get(4).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(5).type);
        assertEquals(TokenType.EOF, tokens.get(6).type);
    }
}


