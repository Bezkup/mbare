import com.mbare.lox.Scanner;
import com.mbare.lox.Token;
import com.mbare.lox.TokenType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class ScannerTest {

    @Test
    public void scanTokens_withEmptySource_returnsEOFToken() {
        Scanner scanner = new Scanner("");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
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
    public void scanTokens_withStringLiteral_returnsStringToken() {
        Scanner scanner = new Scanner("\"hello\"");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(2, tokens.size());
        assertEquals(TokenType.STRING, tokens.get(0).type);
        assertEquals("hello", tokens.get(0).literal);
    }

    @Test
    public void scanTokens_withUnterminatedString_reportsError() {
        Scanner scanner = new Scanner("\"hello");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    public void scanTokens_withComments_ignoresComments() {
        Scanner scanner = new Scanner("// this is a comment\n()");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).type);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void scanTokens_withWhitespace_ignoresWhitespace() {
        Scanner scanner = new Scanner(" \t\r\n()");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(3, tokens.size());
        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).type);
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).type);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void scanTokens_withUnexpectedCharacter_reportsError() {
        Scanner scanner = new Scanner("@");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.get(0).type);
    }

    @Test
    public void scanTokens_multiLineComment_ignore() {
        Scanner scanner = new Scanner("/* \n Ciao */");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(1, tokens.size());
        assertEquals(TokenType.EOF, tokens.getFirst().type);
    }
}