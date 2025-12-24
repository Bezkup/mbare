import com.bezkup.mbare.Interpreter;
import com.bezkup.mbare.Parser;
import com.bezkup.mbare.Scanner;
import com.bezkup.mbare.Stmt;
import com.bezkup.mbare.Token;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Integration Tests - End-to-End")
class IntegrationTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private Interpreter interpreter;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        interpreter = new Interpreter();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private void runCode(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        interpreter.interpret(statements);
    }

    private String getOutput() {
        return outContent.toString().trim();
    }

    @Test
    @DisplayName("Complete program: Number categorization")
    void testNumberCategorization() {
        String source = """
            // Categorize a number
            variabbili n = 16
            variabbili isEven = falsu
            
            variabbili half = n / 2
            variabbili doubled = half * 2
            
            su (doubled == n) {
                isEven = veru
            }
            
            su (isEven) {
                stampa "Even"
            } sannunca {
                stampa "Odd"
            }
            """;
        runCode(source);
        assertEquals("Even", getOutput());
    }

    @Test
    @DisplayName("Complete program: Factorial calculator")
    void testFactorialCalculator() {
        String source = """
            variabbili n = 5
            variabbili result = 1
            variabbili counter = 1
            
            mentri (counter <= n) {
                result = result * counter
                counter = counter + 1
            }
            
            stampa result
            """;
        runCode(source);
        assertEquals("120", getOutput());
    }

    @Test
    @DisplayName("Complete program: Countdown")
    void testCountdown() {
        String source = """
            variabbili count = 5
            
            mentri (count > 0) {
                stampa count
                count = count - 1
            }
            
            stampa "Blastoff!"
            """;
        runCode(source);
        String expected = String.join("\n", "5", "4", "3", "2", "1", "Blastoff!");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Complete program: Sum of array (simulated)")
    void testSumOfArray() {
        String source = """
            variabbili sum = 0
            variabbili v1 = 10
            variabbili v2 = 20
            variabbili v3 = 30
            variabbili v4 = 40
            variabbili v5 = 50
            
            sum = sum + v1
            sum = sum + v2
            sum = sum + v3
            sum = sum + v4
            sum = sum + v5
            
            stampa sum
            """;
        runCode(source);
        assertEquals("150", getOutput());
    }

    @Test
    @DisplayName("Complete program: Maximum of three numbers")
    void testMaximumOfThree() {
        String source = """
            variabbili a = 15
            variabbili b = 42
            variabbili c = 27
            variabbili max = a
            
            su (b > max) {
                max = b
            }
            
            su (c > max) {
                max = c
            }
            
            stampa max
            """;
        runCode(source);
        assertEquals("42", getOutput());
    }

    @Test
    @DisplayName("Complete program: Power function")
    void testPowerFunction() {
        String source = """
            variabbili base = 2
            variabbili exponent = 10
            variabbili result = 1
            variabbili i = 0
            
            mentri (i < exponent) {
                result = result * base
                i = i + 1
            }
            
            stampa result
            """;
        runCode(source);
        assertEquals("1024", getOutput());
    }

    @Test
    @DisplayName("Complete program: Doubling sequence")
    void testDoublingSequence() {
        String source = """
            variabbili n = 1
            variabbili steps = 0
            
            mentri (n < 100) {
                n = n * 2
                steps = steps + 1
            }
            
            stampa steps
            """;
        runCode(source);
        assertEquals("7", getOutput());
    }

    @Test
    @DisplayName("Complete program: Triangle pattern")
    void testTrianglePattern() {
        String source = """
            variabbili rows = 3
            variabbili i = 1
            
            mentri (i <= rows) {
                variabbili j = 1
                mentri (j <= i) {
                    stampa "*"
                    j = j + 1
                }
                i = i + 1
            }
            """;
        runCode(source);
        String expected = String.join("\n", "*", "*", "*", "*", "*", "*");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Complete program: String building")
    void testStringBuilding() {
        String source = """
            variabbili greeting = "Bon"
            variabbili timeOfDay = "jornu"
            variabbili punctuation = "!"
            
            variabbili message = greeting + timeOfDay + punctuation
            stampa message
            """;
        runCode(source);
        assertEquals("Bonjornu!", getOutput());
    }

    @Test
    @DisplayName("Complete program: Scope demonstration")
    void testScopeDemo() {
        String source = """
            variabbili outer = "outer"
            
            {
                variabbili inner = "inner"
                stampa inner
                stampa outer
                
                {
                    variabbili deeper = "deeper"
                    stampa deeper
                    stampa inner
                    stampa outer
                }
            }
            
            stampa outer
            """;
        runCode(source);
        String expected = String.join("\n",
            "inner", "outer", "deeper", "inner", "outer", "outer"
        );
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Complete program: All features combined")
    void testAllFeaturesCombined() {
        String source = """
            // Sicilian Programming Language Demo
            stampa "=== Mbare Demo ==="
            
            // Variables
            variabbili x = 10
            variabbili y = 5
            
            // Arithmetic
            stampa x + y
            stampa x - y
            stampa x * y
            stampa x / y
            
            // Comparisons
            stampa x > y
            stampa x < y
            stampa x == y
            
            // Logical operators
            stampa veru e falsu
            stampa veru o falsu
            stampa !falsu
            
            // Control flow
            su (x > y) {
                stampa "x is greater"
            }
            
            // Loops
            variabbili i = 0
            mentri (i < 3) {
                stampa i
                i = i + 1
            }
            
            stampa "=== Demo Complete ==="
            """;
        runCode(source);

        String output = getOutput();
        assertTrue(output.contains("=== Mbare Demo ==="));
        assertTrue(output.contains("15"));
        assertTrue(output.contains("5"));
        assertTrue(output.contains("50"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("x is greater"));
        assertTrue(output.contains("0"));
        assertTrue(output.contains("1"));
        assertTrue(output.contains("2"));
        assertTrue(output.contains("=== Demo Complete ==="));
    }
}

