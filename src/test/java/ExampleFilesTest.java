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
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Example Files Tests")
class ExampleFilesTest {
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

    private void runFile(String filename) throws IOException {
        String path = "examples/" + filename;
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String source = new String(bytes);

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();
        interpreter.interpret(statements);
    }

    private String getOutput() {
        return outContent.toString().trim();
    }

    private boolean fileExists(String filename) {
        Path path = Paths.get("examples/" + filename);
        return Files.exists(path);
    }

    @Test
    @DisplayName("test_simple.mbare - Prints a simple greeting")
    void testSimpleExample() throws IOException {
        runFile("test_simple.mbare");
        assertEquals("Hello", getOutput());
    }

    @Test
    @DisplayName("test_variables.mbare - Variable declaration and arithmetic")
    void testVariablesExample() throws IOException {
        runFile("test_variables.mbare");
        assertEquals("15", getOutput());
    }

    @Test
    @DisplayName("test_if.mbare - If/else conditional statement")
    void testIfExample() throws IOException {
        runFile("test_if.mbare");
        assertEquals("Grande", getOutput());
    }

    @Test
    @DisplayName("test_while.mbare - While loop with counter")
    void testWhileExample() throws IOException {
        runFile("test_while.mbare");
        String expected = String.join("\n", "0", "1", "2", "3", "4", "Fattu!");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("test_logical.mbare - Logical AND and OR operations")
    void testLogicalExample() throws IOException {
        runFile("test_logical.mbare");
        String expected = String.join("\n", "Tutti dui positivi", "Nenti negativu");
        assertEquals(expected, getOutput());
    }

    @Test
    @DisplayName("fibonacci.mbare - Fibonacci sequence generator")
    void testFibonacciExample() throws IOException {
        runFile("fibonacci.mbare");
        String output = getOutput();

        // Check that output contains header and footer
        assertTrue(output.contains("Fibonacci in Sicilianu:"), "Should contain Fibonacci header");
        assertTrue(output.contains("Fattu!"), "Should contain completion message");

        // Check that all expected Fibonacci numbers are present
        String[] expectedNumbers = {"0", "1", "2", "3", "5", "8", "13", "21", "34"};
        for (String num : expectedNumbers) {
            assertTrue(output.contains(num), "Should contain Fibonacci number: " + num);
        }
    }

    @Test
    @DisplayName("basic.mbare - Expression examples without print statements")
    void testBasicExample() throws IOException {
        // The basic.mbare file contains expressions without print statements
        // Just verify it doesn't throw an error
        runFile("basic.mbare");
        // Since basic.mbare has no print statements, output should be empty
        assertEquals("", getOutput());
    }

    @Test
    @DisplayName("quick_demo.mbare - Quick demonstration of language features")
    void testQuickDemoExample() throws IOException {
        assumeTrue(fileExists("quick_demo.mbare"), "quick_demo.mbare file must exist");

        runFile("quick_demo.mbare");
        String output = getOutput();

        // Verify expected outputs based on quick_demo.mbare content
        assertNotNull(output, "Output should not be null");
        assertTrue(output.contains("10"), "Should output variable x value");
        assertTrue(output.contains("Grande!"), "Should contain Grande message");
        assertTrue(output.contains("Mbare è Turing complete!"), "Should contain Turing complete message");
    }



    @Test
    @DisplayName("All example files exist and are readable")
    void testExampleFilesExist() {
        String[] requiredFiles = {
            "test_simple.mbare",
            "test_variables.mbare",
            "test_if.mbare",
            "test_while.mbare",
            "test_logical.mbare",
            "fibonacci.mbare",
            "basic.mbare"
        };

        for (String filename : requiredFiles) {
            assertTrue(fileExists(filename), "Required example file should exist: " + filename);
        }
    }
}

