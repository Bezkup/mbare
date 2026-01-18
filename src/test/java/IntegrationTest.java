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
            stampa veru e macari falsu
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

    @Test
    @DisplayName("Functions: Simple function with no parameters")
    void testSimpleFunctionNoParams() {
        String source = """
            funzioni greet() {
                stampa "Ciao!"
            }
            greet()
            """;
        runCode(source);
        assertEquals("Ciao!", getOutput());
    }

    @Test
    @DisplayName("Functions: Function with single parameter")
    void testFunctionWithSingleParam() {
        String source = """
            funzioni double(n) {
                ritorna n * 2
            }
            stampa double(5)
            """;
        runCode(source);
        assertEquals("10", getOutput());
    }

    @Test
    @DisplayName("Functions: Function with multiple parameters")
    void testFunctionWithMultipleParams() {
        String source = """
            funzioni add(a, b) {
                ritorna a + b
            }
            stampa add(3, 7)
            """;
        runCode(source);
        assertEquals("10", getOutput());
    }

    @Test
    @DisplayName("Functions: Function with 5 parameters using 'e' as parameter name")
    void testFunctionWithFiveParams() {
        String source = """
            funzioni sum5(a, b, c, d, e) {
                ritorna a + b + c + d + e
            }
            stampa sum5(1, 2, 3, 4, 5)
            """;
        runCode(source);
        assertEquals("15", getOutput());
    }

    @Test
    @DisplayName("Functions: Recursion - Factorial")
    void testRecursionFactorial() {
        String source = """
            funzioni factorial(n) {
                su (n <= 1) {
                    ritorna 1
                }
                ritorna n * factorial(n - 1)
            }
            stampa factorial(5)
            """;
        runCode(source);
        assertEquals("120", getOutput());
    }

    @Test
    @DisplayName("Functions: Recursion - Fibonacci")
    void testRecursionFibonacci() {
        String source = """
            funzioni fib(n) {
                su (n <= 1) {
                    ritorna n
                }
                ritorna fib(n - 1) + fib(n - 2)
            }
            stampa fib(6)
            """;
        runCode(source);
        assertEquals("8", getOutput());
    }

    @Test
    @DisplayName("Functions: Mutual recursion - isEven/isOdd")
    void testMutualRecursion() {
        String source = """
            funzioni isEven(n) {
                su (n == 0) {
                    ritorna veru
                }
                ritorna isOdd(n - 1)
            }
            
            funzioni isOdd(n) {
                su (n == 0) {
                    ritorna falsu
                }
                ritorna isEven(n - 1)
            }
            
            stampa isEven(4)
            stampa isOdd(4)
            stampa isEven(7)
            stampa isOdd(7)
            """;
        runCode(source);
        String expected = String.join("\n", "true", "false", "false", "true");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Functions: Closure - Counter")
    void testClosure() {
        String source = """
            funzioni makeCounter() {
                variabbili count = 0
                funzioni increment() {
                    count = count + 1
                    ritorna count
                }
                ritorna increment
            }
            
            variabbili counter = makeCounter()
            stampa counter()
            stampa counter()
            stampa counter()
            """;
        runCode(source);
        String expected = String.join("\n", "1", "2", "3");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Functions: Multiple independent closures")
    void testMultipleClosures() {
        String source = """
            funzioni makeCounter() {
                variabbili count = 0
                funzioni increment() {
                    count = count + 1
                    ritorna count
                }
                ritorna increment
            }
            
            variabbili counter1 = makeCounter()
            variabbili counter2 = makeCounter()
            stampa counter1()
            stampa counter1()
            stampa counter2()
            stampa counter1()
            """;
        runCode(source);
        String expected = String.join("\n", "1", "2", "1", "3");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Functions: Higher-order function - multiplier")
    void testHigherOrderFunction() {
        String source = """
            funzioni makeMultiplier(factor) {
                funzioni multiply(n) {
                    ritorna n * factor
                }
                ritorna multiply
            }
            
            variabbili triple = makeMultiplier(3)
            variabbili double = makeMultiplier(2)
            stampa triple(5)
            stampa double(5)
            """;
        runCode(source);
        String expected = String.join("\n", "15", "10");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Functions: GCD using recursion")
    void testGCD() {
        String source = """
            funzioni gcd(a, b) {
                su (b == 0) {
                    ritorna a
                }
                ritorna gcd(b, a - (a / b) * b)
            }
            stampa gcd(48, 18)
            stampa gcd(100, 35)
            """;
        runCode(source);
        String expected = String.join("\n", "18", "35");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Functions: Return without value")
    void testReturnWithoutValue() {
        String source = """
            funzioni test(n) {
                su (n > 0) {
                    stampa "Positive"
                    ritorna
                }
                stampa "Not positive"
            }
            test(5)
            test(-3)
            """;
        runCode(source);
        String expected = String.join("\n", "Positive", "Not positive");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("Functions: Nested function calls")
    void testNestedFunctionCalls() {
        String source = """
            funzioni add(a, b) {
                ritorna a + b
            }
            funzioni multiply(a, b) {
                ritorna a * b
            }
            stampa multiply(add(2, 3), add(4, 6))
            """;
        runCode(source);
        assertEquals("50", getOutput());
    }

    @Test
    @DisplayName("Functions: Function with logical operators using 'e macari'")
    void testFunctionWithLogicalOperators() {
        String source = """
            funzioni allPositive(a, b, c) {
                ritorna a > 0 e macari b > 0 e macari c > 0
            }
            stampa allPositive(1, 2, 3)
            stampa allPositive(1, -2, 3)
            """;
        runCode(source);
        String expected = String.join("\n", "true", "false");
        assertEquals(expected, getOutput());
    }
}

