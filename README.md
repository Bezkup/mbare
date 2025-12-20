# Mbare - The Southern Programming Language 🇮🇹

**Mbare** is a **Turing-complete** programming language that uses **Sicilian** keywords instead of English. It's based on the Lox language from "Crafting Interpreters" but with a Southern Italian twist!

## 🎉 Turing Complete!

Mbare is officially Turing complete, meaning it can compute anything that's computable! It supports:
- ✅ Variables
- ✅ Control flow (if/else, while loops)
- ✅ Logical operators
- ✅ Arbitrary computation

## Sicilian Keywords

Here are all the keywords in Mbare and their English equivalents:

| Sicilian | English | Usage | Status |
|----------|---------|-------|--------|
| `variabbili` | `var` | Variable declaration | ✅ Working |
| `stampa` | `print` | Print statement | ✅ Working |
| `si` | `if` | If statement | ✅ Working |
| `senno` | `else` | Else clause | ✅ Working |
| `mentri` | `while` | While loop | ✅ Working |
| `e` | `and` | Logical AND | ✅ Working |
| `o` | `or` | Logical OR | ✅ Working |
| `veru` | `true` | Boolean true | ✅ Working |
| `falsu` | `false` | Boolean false | ✅ Working |
| `nenti` | `nil` | Null/nil value | ✅ Working |
| `pi` | `for` | For loop | ⏳ Not yet |
| `funzioni` | `fun` | Function definition | ⏳ Not yet |
| `ritorna` | `return` | Return statement | ⏳ Not yet |
| `classi` | `class` | Class definition | ⏳ Not yet |
| `chistu` | `this` | This reference | ⏳ Not yet |
| `supiru` | `super` | Superclass reference | ⏳ Not yet |

## Building the Project

```bash
# Compile the project
mvn clean compile

# Build the JAR
mvn package
```

## Running Mbare Programs

### Interactive Mode (REPL)

```bash
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox
```

Then type statements:
```javascript
> variabbili x = 10;
> stampa x;
10
> si (x > 5) { stampa "Grande!"; }
Grande!
```

### Running a Script File

```bash
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox yourfile.mbare
```

## Examples

### Variables and Print

```javascript
variabbili a = 5;
variabbili b = 10;
stampa a + b;  // 15
```

### Control Flow

```javascript
variabbili x = 10;

si (x > 5) {
    stampa "Grande";
} senno {
    stampa "Nicu";
}
```

### While Loops

```javascript
variabbili i = 0;

mentri (i < 5) {
    stampa i;
    i = i + 1;
}
```

### Fibonacci Sequence (Turing Completeness Proof!)

```javascript
// Proves Turing completeness: variables + loops = universal computation
variabbili a = 0;
variabbili b = 1;
variabbili n = 10;
variabbili i = 0;

stampa "Fibonacci in Sicilianu:";

mentri (i < n) {
    stampa a;
    variabbili temp = a + b;
    a = b;
    b = temp;
    i = i + 1;
}

stampa "Fattu!";
```

**Output:**
```
Fibonacci in Sicilianu:
0
1
1
2
3
5
8
13
21
34
Fattu!
```

### Logical Operators

```javascript
variabbili x = 5;
variabbili y = 10;

si (x > 0 e y > 0) {
    stampa "Tutti dui positivi";
}

si (x < 0 o y < 0) {
    stampa "Almenu unu negativu";
} senno {
    stampa "Nenti negativu";
}
```

## Language Features

### ✅ Currently Working

- **Variables** - `variabbili` keyword for declarations
- **Print** - `stampa` keyword for output
- **Arithmetic** - `+`, `-`, `*`, `/`
- **Comparison** - `>`, `<`, `>=`, `<=`, `==`, `!=`
- **Logical operators** - `e` (and), `o` (or), `!` (not)
- **Control flow** - `si`/`senno` (if/else)
- **Loops** - `mentri` (while)
- **Blocks** - `{ }` for scoping
- **String operations** - Concatenation with `+`
- **Boolean values** - `veru`, `falsu`, `nenti`

### ⏳ Future Enhancements

- **Functions** - `funzioni` and `ritorna`
- **For loops** - `pi`
- **Classes** - `classi`, `chistu`, `supiru`
- **Recursion** - Via functions

## Turing Completeness

Mbare satisfies all requirements for Turing completeness:

1. **✅ Arbitrary memory** - Variables (`variabbili`)
2. **✅ Conditional branching** - If/else (`si`/`senno`)
3. **✅ Iteration** - While loops (`mentri`)

This means mbare can theoretically compute anything that's computable!

## Testing

Run example programs:
```bash
# Variables
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox examples/test_variables.mbare

# Control flow
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox examples/test_if.mbare

# Loops
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox examples/test_while.mbare

# Fibonacci (Turing completeness proof)
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox examples/fibonacci.mbare

# Logical operators
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox examples/test_logical.mbare
```

## Language Philosophy

Mbare celebrates the rich linguistic heritage of Sicily while making programming more accessible to Sicilian speakers. The name "mbare" itself is Sicilian, adding authenticity to this Southern programming language.

## Grammar

```
program        → declaration* EOF ;

declaration    → varDecl
               | statement ;

varDecl        → "variabbili" IDENTIFIER ( "=" expression )? ";" ;

statement      → exprStmt
               | ifStmt
               | printStmt
               | whileStmt
               | block ;

exprStmt       → expression ";" ;
ifStmt         → "si" "(" expression ")" statement
                 ( "senno" statement )? ;
printStmt      → "stampa" expression ";" ;
whileStmt      → "mentri" "(" expression ")" statement ;
block          → "{" declaration* "}" ;

expression     → assignment ;
assignment     → IDENTIFIER "=" assignment
               | logic_or ;
logic_or       → logic_and ( "o" logic_and )* ;
logic_and      → equality ( "e" equality )* ;
equality       → comparison ( ( "!=" | "==" ) comparison )* ;
comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term           → factor ( ( "-" | "+" ) factor )* ;
factor         → unary ( ( "/" | "*" ) unary )* ;
unary          → ( "!" | "-" ) unary
               | primary ;
primary        → "veru" | "falsu" | "nenti"
               | NUMBER | STRING
               | IDENTIFIER
               | "(" expression ")" ;
```

## Contributing

Feel free to contribute by:
- Adding more Sicilian expressions and idioms
- Implementing the remaining language features (functions, classes)
- Improving documentation
- Creating example programs

## License

See LICENSE file for details.

---

**Bon travagghiu!** (Happy coding!) 🇮🇹
