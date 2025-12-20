# Mbare Quick Reference

## Literals

```javascript
veru        // true
falsu       // false
nenti       // nil
42          // number
"testo"     // string
```

## Operators

### Arithmetic
```javascript
+   // Addition
-   // Subtraction
*   // Multiplication
/   // Division
```

### Comparison
```javascript
>   // Greater than
<   // Less than
>=  // Greater than or equal
<=  // Less than or equal
```

### Equality
```javascript
==  // Equal
!=  // Not equal
```

### Logical
```javascript
!   // NOT (currently implemented)
e   // AND (keyword defined, not yet implemented)
o   // OR (keyword defined, not yet implemented)
```

## Comments

```javascript
// Single line comment
```

## Grouping

```javascript
(expression)  // Parentheses for grouping
```

## Future Keywords (Not Yet Implemented)

```javascript
variabbili x = 10;           // Variable declaration
stampa x;                    // Print statement

si (condition) {             // If statement
    // code
} senno {                    // Else clause
    // code
}

mentri (condition) {         // While loop
    // code
}

pi (variabbili i = 0; i < 10; i = i + 1) {  // For loop
    // code
}

funzioni saluta(nomu) {      // Function definition
    ritorna "Ciao " + nomu;  // Return statement
}

classi Persona {             // Class definition
    init(nomu) {
        chistu.nomu = nomu;  // This reference
    }
}
```

## Running Programs

### REPL
```bash
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox
```

### Execute File
```bash
java -cp target/mbare-1.0-SNAPSHOT.jar com.bezkup.mbare.Lox file.mbare
```

## Common Sicilian Phrases in Code

```javascript
// Greetings
"Bonjournu"     // Good morning
"Bonasira"      // Good evening
"Ciao"          // Hello/Bye
"Saluti"        // Greetings

// Common words
"veru"          // true
"falsu"         // false
"nenti"         // nothing
"tuttu"         // all/everything
```
