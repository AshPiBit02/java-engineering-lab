# IDL and Simple CORBA Programs


## Table of Contents
1. [IDL Overview](#idl-overview)
2. [IDL Syntax & Elements](#idl-syntax--elements)
3. [IDL Data Types](#idl-data-types)
4. [Interfaces & Operations](#interfaces--operations)
5. [IDL to Java Mapping](#idl-to-java-mapping)
6. [Simple CORBA Program](#simple-corba-program)
7. [Compilation Workflow](#compilation-workflow)
8. [Key Takeaways](#key-takeaways)

---

## IDL Overview

**IDL (Interface Definition Language)** is a language-neutral specification for defining remote service interfaces. It serves as the contract between client and server before any implementation code is written.

### Purpose of IDL

| Purpose | Description |
|---|---|
| **Contract Definition** | Defines what services are available before coding |
| **Language Independence** | Same IDL compiles to Java, C++, Python, etc. |
| **Code Generation** | IDL compiler generates stubs, skeletons, helpers |
| **Platform Agnostic** | Works across Windows, Linux, macOS, etc. |
| **Documentation** | Self-documenting service interface |

### IDL vs Java Interfaces

```
Java Interface:                    IDL Interface:
───────────────                    ──────────────

public interface Account           interface Account {
  extends Remote {                   void deposit(in double amount);
  void deposit(double amount)        void withdraw(in double amount)
    throws RemoteException;            raises (InsufficientFunds);
  void withdraw(double amount)       double getBalance();
    throws RemoteException;        };
  double getBalance()
    throws RemoteException;
}

- Java-specific                    - Language-neutral
- Java types only                  - CDR-mapped types
- Compiled once                    - Compiled to multiple languages
```

---

## IDL Syntax & Elements

### Fig. 1: IDL Structure Overview

```
┌────────────────────────────────────────────────┐
│          IDL FILE STRUCTURE                    │
├────────────────────────────────────────────────┤
│                                                │
│  [Preprocessor directives]                     │
│  [Include statements]                          │
│  [Type definitions / exceptions]               │
│  [Module definitions]                          │
│    └─ [Interface definitions]                  │
│         ├─ [Attributes]                        │
│         └─ [Operations]                        │
│                                                │
└────────────────────────────────────────────────┘
```

### Basic IDL Elements

```idl
// 1. MODULE - Namespace for organization
module Banking {
    // 2. EXCEPTION - Custom error types
    exception InsufficientFunds {
        string message;
        double requested;
        double available;
    };
    
    // 3. STRUCT - Composite data type
    struct Transaction {
        string type;           // "deposit" or "withdraw"
        double amount;
        double balanceAfter;
    };
    
    // 4. INTERFACE - Service contract
    interface Account {
        // 4a. Attributes (readonly or read-write)
        readonly attribute string accountNumber;
        readonly attribute string holderName;
        
        // 4b. Operations (methods)
        void deposit(in double amount);
        void withdraw(in double amount) 
            raises (InsufficientFunds);
        double getBalance();
        
        // 4c. One-way operations (fire-and-forget)
        oneway void logTransaction(in Transaction t);
    };
};
```

---

## IDL Data Types

### Fig. 2: IDL Type Mapping

```
IDL PRIMITIVE TYPES → JAVA MAPPING
═════════════════════════════════════════════════════════════

Integer Types:
  short                 → short         (16-bit)
  long                  → int           (32-bit)
  long long             → long          (64-bit)
  unsigned short        → short
  unsigned long         → int
  unsigned long long    → long

Floating Point:
  float                 → float         (32-bit)
  double                → double        (64-bit)

Boolean & Character:
  boolean               → boolean
  char                  → char
  wchar                 → char (Unicode)

Text:
  string                → String
  wstring               → String (Unicode)

Other:
  octet                 → byte
  any                   → Any
  void                  → void (for return type only)

Constructed Types:
  struct                → Java class
  union                 → Java class
  sequence<T>           → Java array or ArrayList<T>
  enum                  → Java enum
```

### Type Declaration Examples

```idl
// Sequence (dynamic array)
typedef sequence<double> DoubleSequence;
typedef sequence<string> StringSequence;

// Array (fixed-size)
typedef double Balance[100];

// Enumeration
enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
};

// Union (one of multiple types)
union AccountInfo switch(TransactionType) {
    case DEPOSIT:
        double depositAmount;
    case WITHDRAWAL:
        double withdrawalAmount;
    case TRANSFER:
        string transferTo;
};

// Struct (composite type)
struct TimeStamp {
    long seconds;
    long milliseconds;
};
```

---

## Interfaces & Operations

### Operation Definition Syntax

```idl
interface Account {
    // 1. SIMPLE OPERATION
    void deposit(in double amount);
    //   └─ direction: in (pass to server)
    
    // 2. OPERATION WITH RETURN VALUE
    double getBalance();
    //      └─ Returns double to client
    
    // 3. OPERATION WITH MULTIPLE PARAMETERS
    void transfer(
        in Account destination,  // in: pass to server
        in double amount,
        out string transactionId // out: return from server
    );
    
    // 4. OPERATION WITH EXCEPTION
    void withdraw(in double amount)
        raises (InsufficientFunds, AccessDenied);
    
    // 5. ONE-WAY OPERATION (no response expected)
    oneway void notifyClient(in string message);
    
    // 6. ATTRIBUTE (property-like access)
    readonly attribute string accountNumber;
    attribute string accountHolder;  // read-write
};
```

### Parameter Directions

| Direction | Usage | Example |
|---|---|---|
| **in** | Pass data to server | `in double amount` |
| **out** | Return data from server | `out string result` |
| **inout** | Pass and return | `inout double value` |
| (none specified) | Attribute access | `attribute string name` |

---

## IDL to Java Mapping

### Fig. 3: IDL Compilation Process

```
Account.idl
   │
   ├─► idlj -fall Account.idl
   │
   ▼
Generated Files:
├─ Account.java              (Interface definition)
├─ AccountPOA.java           (Server skeleton - extends)
├─ AccountHelper.java        (Helper for narrowing)
├─ AccountHolder.java        (Holder for out/inout params)
├─ AccountOperations.java    (Operations interface)
└─ Banking/
    └─ (module directory structure)

Developer Creates:
├─ AccountImpl.java           (extends AccountPOA)
├─ AccountServer.java        (server startup)
└─ AccountClient.java        (client code)
```

### IDL to Java Class Mapping Example

```idl
// In Account.idl:
module Banking {
    interface Account {
        void deposit(in double amount);
        double getBalance();
        readonly attribute string accountNumber;
    };
};
```

```java
// Generated Account.java (interface)
package Banking;
public interface Account extends 
    org.omg.CORBA.Object, AccountOperations {
    // Interface contract
}

// Generated AccountPOA.java (skeleton - developer extends this)
package Banking;
public abstract class AccountPOA 
    extends org.omg.PortableServer.Servant
    implements AccountOperations {
    
    public abstract void deposit(double amount);
    public abstract double getBalance();
    public abstract String accountNumber();
    // Developer overrides these methods
}

// Developer writes AccountImpl.java
public class AccountImpl extends AccountPOA {
    private double balance = 0;
    
    public void deposit(double amount) {
        balance += amount;  // Implementation
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String accountNumber() {
        return "ACC001";
    }
}
```

---

## Simple CORBA Program

### Complete Working Example: Simple Calculator

**Step 1: Define IDL (Calculator.idl)**
```idl
module SimpleCalc {
    interface Calculator {
        long add(in long a, in long b);
        long subtract(in long a, in long b);
        exception DivisionByZero { string reason; };
        long divide(in long a, in long b) 
            raises (DivisionByZero);
    };
};
```

**Step 2: Compile IDL**
```bash
idlj -fall Calculator.idl
# Generates: Calculator.java, CalculatorPOA.java, etc.
```

**Step 3: Implement Servant (CalculatorImpl.java)**
```java
package SimpleCalc;

public class CalculatorImpl extends CalculatorPOA {
    public long add(long a, long b) {
        return a + b;
    }
    
    public long subtract(long a, long b) {
        return a - b;
    }
    
    public long divide(long a, long b) 
        throws DivisionByZero {
        if (b == 0) {
            DivisionByZero ex = new DivisionByZero();
            ex.reason = "Cannot divide by zero";
            throw ex;
        }
        return a / b;
    }
}
```

**Step 4: Server (CalculatorServer.java)**
```java
import SimpleCalc.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootPoa = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA"));
            
            CalculatorImpl servant = new CalculatorImpl();
            rootPoa.activate_object(servant);
            
            org.omg.CORBA.Object ref = rootPoa.servant_to_reference(servant);
            Calculator calcRef = CalculatorHelper.narrow(ref);
            
            String ior = orb.object_to_string(calcRef);
            System.out.println(ior);  // Print IOR for client
            
            rootPoa.the_POAManager().activate();
            System.out.println("Server ready...");
            orb.run();
            
        } catch (Exception e) { e.printStackTrace(); }
    }
}
```

**Step 5: Client (CalculatorClient.java)**
```java
import SimpleCalc.*;
import org.omg.CORBA.*;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            
            // Use IOR from server output
            Object obj = orb.string_to_object(args[0]);
            Calculator calc = CalculatorHelper.narrow(obj);
            
            System.out.println("5 + 3 = " + calc.add(5, 3));
            System.out.println("10 - 4 = " + calc.subtract(10, 4));
            System.out.println("20 / 5 = " + calc.divide(20, 5));
            
            try {
                calc.divide(10, 0);
            } catch (SimpleCalc.DivisionByZero ex) {
                System.out.println("Error: " + ex.reason);
            }
            
        } catch (Exception e) { e.printStackTrace(); }
    }
}
```

**Step 6: Run**
```bash
# Terminal 1: Start server (captures IOR)
java CalculatorServer > ior.txt

# Terminal 2: Run client (passes IOR)
java CalculatorClient `cat ior.txt`
```

---

## Compilation Workflow

### Fig. 4: Complete Build Process

```
IDL Definition
(Calculator.idl)
    │
    ▼
IDL Compiler
(idlj -fall Calculator.idl)
    │
    ├─► Calculator.java
    ├─► CalculatorPOA.java
    ├─► CalculatorHelper.java
    ├─► CalculatorHolder.java
    └─► CalculatorOperations.java
    │
    ▼
Java Compilation
(javac)
    │
    ├─► Compile generated files
    ├─► Compile CalculatorImpl.java
    ├─► Compile CalculatorServer.java
    └─► Compile CalculatorClient.java
    │
    ▼
Ready to Run
    │
    ├─► Start Server: java CalculatorServer
    └─► Start Client: java CalculatorClient <IOR>
```

### Build Commands

```bash
# 1. Generate code from IDL
idlj -fall Calculator.idl

# 2. Compile all Java files
javac -d . SimpleCalc/*.java *.java

# 3. Run server in background
java CalculatorServer > ior.txt &

# 4. Run client with server's IOR
java CalculatorClient $(cat ior.txt)

# Alternative: Use Makefile
# Makefile can automate steps 1-4
```

---

## Key Takeaways

### IDL Best Practices

| Practice | Reason |
|---|---|
| **Use modules** | Organize interfaces logically |
| **Define exceptions** | Client code can handle specific errors |
| **Use readonly attributes** | Immutable data is safer |
| **Name operations clearly** | Self-documenting interface |
| **Document with comments** | // and /* */ supported |
| **Avoid complex structures** | Keep data types simple |
| **Use sequences carefully** | Unbounded arrays cause memory issues |

### Common IDL Patterns

```idl
// Pattern 1: Status return
interface Service {
    boolean performAction(in string data);
};

// Pattern 2: Exception-based errors
interface Service {
    exception OperationFailed { string details; };
    void performAction(in string data)
        raises (OperationFailed);
};

// Pattern 3: Out parameter for results
interface Service {
    void performAction(in string data, out string result);
};

// Pattern 4: Complex data structure
struct Result {
    boolean success;
    string message;
    long value;
};

interface Service {
    Result performAction(in string data);
};
```

### Important Notes

1. **IDL Compilation Order**
   - Always compile IDL first
   - Generated files are dependencies for Java code

2. **Helper Classes**
   - `CalculatorHelper.narrow()` casts object references
   - Essential for type-safe narrowing

3. **IOR Distribution**
   - Pass IOR string from server to client
   - Can be stored in files or printed to stdout

4. **Exception Handling**
   - Define custom exceptions in IDL
   - Client can catch specific exceptions

5. **One-Way Operations**
   - Use sparingly (fire-and-forget semantics)
   - No guarantee of delivery

6. **Attributes vs Operations**
   - `readonly attribute` = getter only
   - `attribute` = getter + setter
   - Can also use explicit get/set operations

---
