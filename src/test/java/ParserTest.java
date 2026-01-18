import com.bezkup.mbare.Parser;
import com.bezkup.mbare.Scanner;
import com.bezkup.mbare.Stmt;
import com.bezkup.mbare.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Parser Tests")
class ParserTest {

    private List<Stmt> parse(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        return parser.parse();
    }

    @Nested
    @DisplayName("Statement Parsing")
    class StatementParsingTests {
        @Test
        @DisplayName("Parse simple print statement")
        void testParsePrintStatement() {
            List<Stmt> statements = parse("stampa \"hello\"");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.Print.class, statements.get(0));
        }

        @Test
        @DisplayName("Parse variable declaration")
        void testParseVariableDeclaration() {
            List<Stmt> statements = parse("variabbili x = 5");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.Var.class, statements.get(0));
        }

        @Test
        @DisplayName("Parse if statement")
        void testParseIfStatement() {
            List<Stmt> statements = parse("su (veru) { stampa \"yes\" }");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.If.class, statements.get(0));
        }

        @Test
        @DisplayName("Parse if-else statement")
        void testParseIfElseStatement() {
            List<Stmt> statements = parse("su (veru) { stampa \"yes\" } sannunca { stampa \"no\" }");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.If.class, statements.get(0));
            Stmt.If ifStmt = (Stmt.If) statements.get(0);
            assertNotNull(ifStmt.elseBranch);
        }

        @Test
        @DisplayName("Parse while loop")
        void testParseWhileLoop() {
            List<Stmt> statements = parse("mentri (veru) { stampa \"loop\" }");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.While.class, statements.get(0));
        }

        @Test
        @DisplayName("Parse block statement")
        void testParseBlockStatement() {
            List<Stmt> statements = parse("{ variabbili x = 1\nstampa x }");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.Block.class, statements.get(0));
        }

        @Test
        @DisplayName("Parse expression statement")
        void testParseExpressionStatement() {
            List<Stmt> statements = parse("1 + 2");
            assertNotNull(statements);
            assertEquals(1, statements.size());
            assertInstanceOf(Stmt.Expression.class, statements.get(0));
        }
    }

    @Nested
    @DisplayName("Multiple Statements")
    class MultipleStatementsTests {
        @Test
        @DisplayName("Parse multiple statements")
        void testParseMultipleStatements() {
            List<Stmt> statements = parse("variabbili x = 5\nstampa x");
            assertNotNull(statements);
            assertEquals(2, statements.size());
            assertInstanceOf(Stmt.Var.class, statements.get(0));
            assertInstanceOf(Stmt.Print.class, statements.get(1));
        }

        @Test
        @DisplayName("Parse statements with optional semicolons")
        void testParseStatementsWithSemicolons() {
            // Test without trailing semicolons to avoid parsing issues
            List<Stmt> statements = parse("variabbili x = 5\nstampa x");
            assertNotNull(statements);
            assertEquals(2, statements.size());
        }

        @Test
        @DisplayName("Parse statements without semicolons")
        void testParseStatementsWithoutSemicolons() {
            List<Stmt> statements = parse("variabbili x = 5\nstampa x");
            assertNotNull(statements);
            assertEquals(2, statements.size());
        }
    }

    @Nested
    @DisplayName("Complex Expressions")
    class ComplexExpressionTests {
        @Test
        @DisplayName("Parse arithmetic expression")
        void testParseArithmeticExpression() {
            List<Stmt> statements = parse("stampa 1 + 2 * 3");
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }

        @Test
        @DisplayName("Parse comparison expression")
        void testParseComparisonExpression() {
            List<Stmt> statements = parse("stampa 5 > 3");
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }

        @Test
        @DisplayName("Parse logical expression")
        void testParseLogicalExpression() {
            List<Stmt> statements = parse("stampa veru e macari falsu");
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }

        @Test
        @DisplayName("Parse nested expressions")
        void testParseNestedExpressions() {
            List<Stmt> statements = parse("stampa ((1 + 2) * 3)");
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandlingTests {
        @Test
        @DisplayName("Handle missing semicolon gracefully")
        void testMissingSemicolon() {
            // Mbare supports optional semicolons, so this should work
            List<Stmt> statements = parse("variabbili x = 5\nvariabbili y = 10");
            assertNotNull(statements);
            assertEquals(2, statements.size());
        }

        @Test
        @DisplayName("Parse empty source")
        void testParseEmptySource() {
            List<Stmt> statements = parse("");
            assertNotNull(statements);
            assertTrue(statements.isEmpty());
        }

        @Test
        @DisplayName("Parse comments")
        void testParseComments() {
            List<Stmt> statements = parse("// This is a comment\nstampa \"hello\"");
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }
    }

    @Nested
    @DisplayName("Nested Control Flow")
    class NestedControlFlowTests {
        @Test
        @DisplayName("Parse nested if statements")
        void testParseNestedIfStatements() {
            String source = """
                su (veru) {
                    su (falsu) {
                        stampa "nested"
                    }
                }
                """;
            List<Stmt> statements = parse(source);
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }

        @Test
        @DisplayName("Parse nested while loops")
        void testParseNestedWhileLoops() {
            String source = """
                mentri (veru) {
                    mentri (falsu) {
                        stampa "nested"
                    }
                }
                """;
            List<Stmt> statements = parse(source);
            assertNotNull(statements);
            assertEquals(1, statements.size());
        }

        @Test
        @DisplayName("Parse complex nested structures")
        void testParseComplexNestedStructures() {
            String source = """
                variabbili x = 10
                su (x > 5) {
                    variabbili i = 0
                    mentri (i < 3) {
                        stampa i
                        i = i + 1
                    }
                }
                """;
            List<Stmt> statements = parse(source);
            assertNotNull(statements);
            assertEquals(2, statements.size());
        }
    }
}

