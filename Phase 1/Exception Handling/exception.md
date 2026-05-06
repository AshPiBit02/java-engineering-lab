# ⚠️ Exception Handling in Java


---

## 📌 What is an Exception?

An **exception** is an **unexpected event** that occurs during program execution and disrupts the normal flow of the program. It is an object that represents an error condition.

```
┌──────────────────────────────────────────────────────────┐
│                  Normal vs Exception Flow                |
│                                                          │
│   Normal Flow:                                           │
│   Statement 1 → Statement 2 → Statement 3 → END          │
│                                                          │
│   With Exception (unhandled):                            │
│   Statement 1 → Statement 2 → ❌ EXCEPTION → CRASH      |
│                                                          │
│   With Exception (handled):                              │
│   Statement 1 → Statement 2 → ❌ EXCEPTION              │
│                                     │                    │
│                               catch block                │
│                                     │                    │
│                               Statement 3 → END ✅      │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Normal Flow vs Exception Flow**

---

## 🌳 Exception Hierarchy

All exceptions in Java are **objects** — they inherit from the `Throwable` class.

```
┌──────────────────────────────────────────────────────────┐
│                   Exception Hierarchy                    │
│                                                          │
│                   ┌─────────────┐                        │
│                   │  Throwable  │                        │
│                   └──────┬──────┘                        │
│                          │                               │
│           ┌──────────────┴──────────────┐                │
│           │                             │                │
│    ┌──────▼──────┐               ┌──────▼──────┐         │
│    │    Error    │               │  Exception  │         │
│    │  (serious,  │               │  (handleable│         │
│    │  don't      │               │   by prog)  │         │
│    │  handle)    │               └──────┬──────┘         │
│    └─────────────┘                      │                │
│    e.g. OutOfMemory                      │               │
│         StackOverflow      ┌────────────┴────────────┐   │
│                            │                         │   │
│                  ┌─────────▼──────┐    ┌─────────────▼┐  │
│                  │    Checked     │    │   Unchecked  │  │
│                  │   Exceptions   │    │  Exceptions  │  │
│                  │ (compile-time) │    │  (runtime)   │  │
│                  └────────────────┘    └──────────────┘  │
│                  e.g. IOException       e.g. NullPointer │
│                       SQLException          ArithmeticEx │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Java Exception Class Hierarchy**

---

## 🗂️ Types of Exceptions

### 1. 🔴 Checked Exceptions
Exceptions checked at **compile time**. The compiler forces you to handle them.

| Exception | Cause |
|-----------|-------|
| `IOException` | File read/write failure |
| `SQLException` | Database error |
| `FileNotFoundException` | File not found |
| `ClassNotFoundException` | Class not found at runtime |

```java
// Compiler forces you to handle this
FileReader f = new FileReader("file.txt");  // ❌ won't compile without try-catch
```

---

### 2. 🟠 Unchecked Exceptions (Runtime Exceptions)
Exceptions that occur at **runtime** — compiler does not force handling.

| Exception | Cause |
|-----------|-------|
| `NullPointerException` | Using a null reference |
| `ArrayIndexOutOfBoundsException` | Accessing invalid array index |
| `ArithmeticException` | Division by zero |
| `ClassCastException` | Invalid type casting |
| `NumberFormatException` | Invalid string-to-number conversion |
| `StackOverflowError` | Infinite recursion |

```java
int[] arr = new int[3];
arr[5] = 10;   // ❌ ArrayIndexOutOfBoundsException at runtime
```

---

### 3. 🔵 Errors
Serious problems caused by the **JVM environment** — not meant to be handled by the program.

```
OutOfMemoryError    →  JVM runs out of heap memory
StackOverflowError  →  infinite recursion fills the stack
VirtualMachineError →  JVM internal error
```

---

## 🛡️ Exception Handling Keywords

```
┌──────────────────────────────────────────────────────────┐
│             Exception Handling Keywords                  │
│                                                          │
│   ┌─────────┐  ┌─────────┐  ┌─────────┐                  │
│   │   try   │  │  catch  │  │ finally │                  │
│   └─────────┘  └─────────┘  └─────────┘                  │
│   ┌─────────┐  ┌─────────┐                               │
│   │  throw  │  │ throws  │                               │
│   └─────────┘  └─────────┘                               │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Exception Handling Keywords**

---

## 🔧 `try-catch` Block

The core mechanism for handling exceptions.

### Syntax

```java
try {
    // code that may throw an exception
} catch (ExceptionType e) {
    // handle the exception
}
```

### Example

```java
try {
    int result = 10 / 0;             // throws ArithmeticException
    System.out.println(result);
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage());  // Error: / by zero
}
```

```
┌──────────────────────────────────────────────────────────┐
│                  try-catch Flow                          │
│                                                          │
│   ┌─────────────────────┐                                │
│   │      try block      │                                │
│   │   execute code      │                                │
│   └──────────┬──────────┘                                │
│              │                                           │
│    ┌─────────┴──────────┐                                │
│  no exception         exception                          │
│    │                   │                                 │
│    │            ┌──────▼──────────┐                      │
│    │            │   catch block   │                      │
│    │            │  handle error   │                      │
│    │            └──────┬──────────┘                      │
│    └──────────┬─────────┘                                │
│               ▼                                          │
│         rest of program                                  │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 4 — `try-catch` Flow**

---

## 🔧 Multiple `catch` Blocks

A single `try` block can have **multiple catch blocks** — each handling a different exception type. Only the **first matching** catch block executes.

```java
try {
    int[] arr = new int[3];
    arr[5] = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Arithmetic error: " + e.getMessage());
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("General error: " + e.getMessage());  // catches anything else
}
```

> ⚠️ Always place **more specific** exceptions before **more general** ones. Placing `Exception` first would catch everything and make lower catches unreachable.

```
┌──────────────────────────────────────────────────────────┐
│              Multiple catch Block Order                  │
│                                                          │
│   ✅ Correct Order          ❌ Wrong Order              │
│                                                          │
│   catch(ArithmeticEx)       catch(Exception)  ← too broad│
│   catch(NullPointerEx)      catch(ArithmeticEx)← never   │
│   catch(Exception)          catch(NullPointerEx) reached │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 5 — catch Block Ordering**

---

## 🔧 `finally` Block

Executes **always** — whether an exception occurred or not. Used for cleanup (closing files, DB connections).

```java
try {
    int result = 10 / 2;
    System.out.println(result);
} catch (ArithmeticException e) {
    System.out.println("Error!");
} finally {
    System.out.println("Always runs!");  // ✅ runs regardless
}
```

```
┌──────────────────────────────────────────────────────────┐
│                    finally Block Flow                    │
│                                                          │
│         try block executes                               │
│              │                                           │
│    ┌─────────┴──────────┐                                │
│  no exception        exception thrown                    │
│    │                      │                              │
│    │                 catch block runs                    │
│    │                      │                              │
│    └──────────┬───────────┘                              │
│               ▼                                          │
│        finally block  ← ALWAYS runs                      │
│               ▼                                          │
│         rest of program                                  │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 6 — `finally` Block Always Executes**

> 🆚 **C++ vs Java** — C++ has no `finally` block. Cleanup in C++ is done via **destructors** (RAII). Java uses `finally` since there are no destructors.

---

## 🔧 `throw` Keyword

Used to **manually throw** an exception from your code.

```java
class AgeValidator {
    static void validate(int age) {
        if (age < 18) {
            throw new ArithmeticException("Age must be 18+");  // manually thrown
        }
        System.out.println("Valid age: " + age);
    }
}

AgeValidator.validate(15);   // throws ArithmeticException
```

> 💡 `throw` is followed by an **instance** of an exception object.

---

## 🔧 `throws` Keyword

Declares that a method **may throw** a checked exception — responsibility passed to the caller.

```java
void readFile(String path) throws IOException {   // declares possible exception
    FileReader f = new FileReader(path);
}

// Caller must handle it
try {
    readFile("data.txt");
} catch (IOException e) {
    System.out.println("File error: " + e.getMessage());
}
```

```
┌──────────────────────────────────────────────────────────┐
│               throw  vs  throws                          │
│                                                          │
│   throw                      throws                      │
│   ─────────────────          ────────────────────        │
│   Used inside method         Used in method signature    │
│   Throws an instance         Declares possible exception │
│   Transfers control          Passes responsibility       │
│   to catch block             to the caller               │
│                                                          │
│   throw new IOException()    void m() throws IOException │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 7 — `throw` vs `throws`**

---

## 🔧 Custom (User-defined) Exceptions

You can create your own exception by **extending** `Exception` (checked) or `RuntimeException` (unchecked).

```java
// Custom checked exception
class InvalidAgeException extends Exception {
    InvalidAgeException(String message) {
        super(message);
    }
}

class Voter {
    static void checkAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age " + age + " is not eligible.");
        }
        System.out.println("Eligible to vote!");
    }
}

// Usage
try {
    Voter.checkAge(15);
} catch (InvalidAgeException e) {
    System.out.println("Custom Exception: " + e.getMessage());
}
// Output: Custom Exception: Age 15 is not eligible.
```

```
┌──────────────────────────────────────────────────────────┐
│            Creating Custom Exceptions                    │
│                                                          │
│   For Checked Exception:                                 │
│   class MyException extends Exception { }                │
│                                                          │
│   For Unchecked Exception:                               │
│   class MyException extends RuntimeException { }         │
│                                                          │
│   Always call super(message) in constructor              │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 8 — Custom Exception Structure**

---

## 🔗 Exception Propagation

If an exception is **not caught** in the current method, it **propagates up** the call stack to the calling method.

```
┌──────────────────────────────────────────────────────────┐
│               Exception Propagation                      │
│                                                          │
│   main() calls methodA()                                 │
│   methodA() calls methodB()                              │
│   methodB() throws exception ──► not caught here         │
│                │                                         │
│                ▼                                         │
│   propagates to methodA() ──► not caught here            │
│                │                                         │
│                ▼                                         │
│   propagates to main() ──► caught here ✅               │
│                │                                         │
│                ▼                                         │
│   if not caught in main() → JVM terminates program ❌    │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 9 — Exception Propagation up the Call Stack**

---

## 📋 Key Differences — C++ vs Java

| Feature | C++ | Java |
|---------|-----|------|
| Exception base class | `std::exception` | `Throwable` |
| Checked exceptions | ❌ No concept | ✅ Compiler enforced |
| `finally` block | ❌ Not available | ✅ Available |
| Custom exceptions | Extend `std::exception` | Extend `Exception` or `RuntimeException` |
| `throw` syntax | `throw exceptionObject;` | `throw new ExceptionType();` |
| `throws` declaration | `noexcept` / `throw()` (C++11) | `throws ExceptionType` |
| Catch all | `catch(...)` | `catch(Exception e)` |

> 🆚 **Key Difference** — Java has **checked exceptions** which the compiler enforces — C++ has no such concept. Java also has `finally` which C++ lacks (C++ uses destructors/RAII for cleanup instead).

---

## 💡 Summary

| Keyword | Purpose |
|---------|---------|
| `try` | Wraps code that might throw an exception |
| `catch` | Handles a specific exception type |
| `finally` | Always executes — used for cleanup |
| `throw` | Manually throws an exception object |
| `throws` | Declares that a method may throw a checked exception |

```
Checked     →  must handle at compile time  (IOException, SQLException)
Unchecked   →  occurs at runtime            (NullPointerException, ArithmeticException)
Error       →  serious JVM issues           (OutOfMemoryError) — don't handle
```

---
