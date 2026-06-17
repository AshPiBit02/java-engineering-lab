# 4.7 Architecture of CORBA

## Table of Contents
1. [Overview](#overview)
2. [Key Characteristics](#key-characteristics)
3. [CORBA Architecture Components](#corba-architecture-components)
4. [Architecture Layers](#architecture-layers)
5. [Core Classes & Interfaces](#core-classes--interfaces)
6. [Communication Flow](#communication-flow)
7. [Working Sequence](#working-sequence)
8. [Code Examples](#code-examples)
9. [Exception Handling](#exception-handling)
10. [Important Notes](#important-notes)

---

## Overview

CORBA (Common Object Request Broker Architecture) is a distributed computing middleware that enables communication between heterogeneous applications across networks. Unlike RMI (Java-specific), CORBA is language and platform-independent through the use of Interface Definition Language (IDL).

### What is CORBA?
- **Object Request Broker (ORB)**: Central middleware handling object communication
- **Language-Neutral**: IDL abstracts implementation details
- **Platform-Independent**: Works across different OS and architectures
- **Standards-Based**: OMG (Object Management Group) specification

---

## Key Characteristics

| Characteristic | Description |
|---|---|
| **ORB-Centric** | All communication flows through the Object Request Broker |
| **IDL-Based** | Interface Definition Language defines service contracts |
| **Stubs & Skeletons** | Generated from IDL; client uses stubs, server uses skeletons |
| **Language Independent** | Clients and servers can use different programming languages |
| **Network Transparent** | Location of objects is transparent to clients |
| **Synchronous Communication** | Default request-reply model |
| **Object References** | Clients use object references (similar to RMI remote objects) |
| **Standard Ports** | Default IIOP (Internet Inter-ORB Protocol) uses port 7777 |

---

## CORBA Architecture Components

### Fig. 1: CORBA System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        CORBA ARCHITECTURE                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────────────┐             ┌────────────────────┐    │
│  │   CLIENT SIDE        │             │   SERVER SIDE      │    │
│  │                      │             │                    │    │
│  │  ┌────────────────┐  │             │  ┌────────────────┐│    │
│  │  │  Client Code   │  │             │  │  Servant Code  ││    │
│  │  └────────┬───────┘  │             │  └────────┬───────┘│    │
│  │           │          │             │           │        │    │
│  │  ┌────────▼───────┐  │             │  ┌────────▼───────┐│    │
│  │  │  IDL Stub      │  │             │  │ IDL Skeleton   ││    │
│  │  │ (Proxy)        │  │             │  │ (Wrapper)      ││    │
│  │  └────────┬───────┘  │             │  └───────┬────────┘│    │
│  │           │          │             │          │         │    │
│  │  ┌────────▼───────┐  │ IIOP  ┌─────┬──────────▼───────┐ │    │    
│  │  │  ORB (Client)  │  │◄─────►│ ORB │      (Server)    │_│    │    
│  │  └────────────────┘  │       └─────┴──────────────────┘      │    
│  └──────────────────────┘                                       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘

Key Components:
- Stub: Client-side proxy (marshals/unmarshals)
- Skeleton: Server-side wrapper (unmarshals/marshals)
- ORB: Handles communication, thread management, object location
- IIOP: Wire protocol (TCP/IP based)
```

### 1. **Object Request Broker (ORB)**
- **Responsibility**: Intercepts method calls, locates objects, manages communication
- **Functions**:
  - Method invocation delegation
  - Object reference management
  - Thread pooling
  - Name service integration
  - Activation/deactivation of servants

### 2. **IDL (Interface Definition Language)**
- **Purpose**: Defines remote interface contracts
- **Language**: Syntax similar to C++
- **Output**: Generates stubs (client) and skeletons (server)
- **Example**:
```idl
module Banking {
  interface Account {
    void deposit(in double amount);
    double getBalance();
    exception InsufficientFunds {};
  };
};
```

### 3. **Stubs (Client-Side)**
- Generated proxy on client
- Marshals arguments into bytes
- Sends request via ORB to skeleton
- Unmarshals return values

### 4. **Skeletons (Server-Side)**
- Generated wrapper on server
- Unmarshals incoming requests
- Dispatches to servant (actual implementation)
- Marshals return values back to stub

### 5. **Naming Service**
- Registry for object references
- Clients look up objects by name
- Part of OMG Service Interface

### 6. **IIOP (Internet Inter-ORB Protocol)**
- **Wire Protocol**: TCP/IP based
- **Default Port**: 7777
- **Handles**: Marshaling format, communication semantics
- **Platform-Independent**: Enables heterogeneous ORBs to communicate

---

## Architecture Layers

### Fig. 2: CORBA Layered Architecture

```
┌────────────────────────────────────────────────────────────┐
│  APPLICATION LAYER                                         │
│  (Client Code, Servant Implementation)                     │
├────────────────────────────────────────────────────────────┤
│  IDL STUB / SKELETON LAYER                                 │
│  (Auto-generated from IDL; handles marshaling)             │
├────────────────────────────────────────────────────────────┤
│  ORB CORE LAYER                                            │
│  (Object location, method invocation, threading)           │
├────────────────────────────────────────────────────────────┤
│  IIOP TRANSPORT LAYER                                      │
│  (TCP/IP communication, serialization)                     │
├────────────────────────────────────────────────────────────┤
│  NETWORK LAYER                                             │
│  (Sockets, DNS, routing)                                   │
└────────────────────────────────────────────────────────────┘

Layer Breakdown:
1. Application: Custom business logic
2. Stub/Skeleton: Method marshaling/unmarshaling
3. ORB Core: Request routing, object management
4. IIOP: Wire protocol for network communication
5. Network: Low-level socket communication
```

---

## Core Classes & Interfaces

### **Java ORB Class**
```java
// Located in org.omg.CORBA package
public class ORB {
    // Create and initialize ORB
    public static ORB init(String[] args, java.util.Properties props);
    
    // Get object reference from string
    public org.omg.CORBA.Object string_to_object(String str);
    
    // Convert object to string (for persistence/transmission)
    public String object_to_string(org.omg.CORBA.Object obj);
    
    // Start ORB event loop
    public void run();
    
    // Shutdown ORB
    public void shutdown(boolean wait_for_completion);
    
    // Get root POA (Portable Object Adapter)
    public POA resolve_initial_references(String identifier);
}
```

### **POA (Portable Object Adapter)**
```java
// Manages servant activation, lifecycle, threading
public abstract class POA {
    // Activate servant
    public byte[] activate_object(Servant servant);
    
    // Create object reference from servant ID
    public Object id_to_reference(byte[] oid);
    
    // Get servant from object reference
    public Servant reference_to_servant(Object reference);
    
    // Deactivate servant
    public void deactivate_object(byte[] oid);
    
    // Set threading model
    public void set_thread_pool_size(int pool_size);
}
```

### **Servant (Server-Side Implementation)**
```java
// Base class for CORBA servants
public abstract class Servant {
    // Default implementation of _primary_interface
    public String[] _all_interfaces(POA poa, byte[] objectId);
    
    // Return POA
    public POA _poa();
    
    // Return object reference
    public Object _this_object();
}
```

---

## Communication Flow

### Fig. 3: Request-Reply Communication Sequence

```
CLIENT SIDE                    NETWORK                    SERVER SIDE
═════════════════════════════════════════════════════════════════════

1. Client calls stub method
   ┌─────────────────┐
   │ account.deposit │
   │    (1000.0)     │
   └────────┬────────┘
            │
2. Stub marshals arguments
   ┌────────▼────────┐
   │ serialize args  │
   │ create request  │
   └────────┬────────┘
            │
3. Send via ORB to server
   ├─────────IIOP─────────────────►
            (TCP/IP)
                              4. Skeleton receives request
                                 ┌──────────────────┐
                                 │ receive data     │
                                 │ deserialize args │
                                 └────────┬─────────┘
                                          │
                              5. Call servant method
                                 ┌────────▼─────────┐
                                 │ servant.deposit  │
                                 │   (1000.0)       │
                                 └────────┬─────────┘
                                          │
                              6. Get return value
                                 ┌────────▼─────────┐
                                 │ serialize result │
                                 │ create reply     │
                                 └────────┬─────────┘
                                          │
            Send back to client ◄─────IIOP────────┤
                                 (TCP/IP)
7. Stub receives reply
   ┌────────────────┐
   │ deserialize    │
   │ get result     │
   └────────┬───────┘
            │
8. Return to client code
   ┌────────▼────────┐
   │ print success   │
   └─────────────────┘
```

---

## Working Sequence

### Fig. 4: CORBA Application Initialization Flow

```
┌─────────────────────────────────────────────────────────────────┐
│ STEP-BY-STEP CORBA COMMUNICATION WORKFLOW                       │
└─────────────────────────────────────────────────────────────────┘

SERVER STARTUP:
═════════════════════════════════════════════════════════════════

1. Initialize ORB
   ├─ ORB orb = ORB.init(args, props)
   
2. Get Root POA
   ├─ POA rootPoa = orb.resolve_initial_references("RootPOA")
   
3. Create servant instance
   ├─ Servant servant = new AccountImpl()
   
4. Activate servant in POA
   ├─ byte[] objectId = rootPoa.activate_object(servant)
   
5. Get object reference
   ├─ Object objRef = rootPoa.id_to_reference(objectId)
   
6. Convert to string (for client distribution)
   ├─ String iorString = orb.object_to_string(objRef)
   
7. Register with Naming Service (or return to client)
   ├─ Naming Service stores: "Account" → IOR string
   
8. Start ORB event loop
   ├─ orb.run()  // Wait for client requests

─────────────────────────────────────────────────────────────────

CLIENT STARTUP:
═════════════════════════════════════════════════════════════════

1. Initialize ORB
   ├─ ORB orb = ORB.init(args, props)
   
2. Look up object reference
   ├─ From Naming Service: Account acct = lookup("Account")
   │  OR
   ├─ From string: Object obj = orb.string_to_object(iorString)
   
3. Narrow reference to concrete type
   ├─ Account acct = AccountHelper.narrow(obj)
   
4. Call remote methods through stub
   ├─ acct.deposit(1000.0)  // Invokes stub
   ├─ double balance = acct.getBalance()
   
5. Handle responses
   ├─ Stub unmarshals return values
   ├─ Returns to application
```

---

## Code Examples

### **Example 1: IDL Definition**
```idl
// Account.idl - Interface definition
module Banking {
  exception InsufficientFunds {
    string message;
  };
  
  interface Account {
    // Attributes
    readonly attribute string accountNumber;
    readonly attribute string holderName;
    
    // Operations
    void deposit(in double amount);
    void withdraw(in double amount) raises (InsufficientFunds);
    double getBalance();
    void transfer(in Account toAccount, in double amount) 
      raises (InsufficientFunds);
  };
};
```

### **Example 2: Server-Side Servant Implementation**
```java
import Banking.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

// Servant: Actual implementation of Account
public class AccountImpl extends AccountPOA {
    private String accountNumber;
    private String holderName;
    private double balance;
    
    public AccountImpl(String accNumber, String name) {
        this.accountNumber = accNumber;
        this.holderName = name;
        this.balance = 0.0;
    }
    
    // IDL operation: deposit
    @Override
    public void deposit(double amount) {
        // Validate amount
        if (amount <= 0) {
            throw new org.omg.CORBA.BAD_PARAM("Amount must be positive");
        }
        balance += amount;
        System.out.println("Deposit: " + amount + " | New Balance: " + balance);
    }
    
    // IDL operation: withdraw
    @Override
    public void withdraw(double amount) throws InsufficientFunds {
        // Check balance before withdrawal
        if (amount > balance) {
            InsufficientFunds ex = new InsufficientFunds();
            ex.message = "Insufficient funds. Current: " + balance;
            throw ex;
        }
        balance -= amount;
        System.out.println("Withdrawal: " + amount + " | New Balance: " + balance);
    }
    
    // IDL operation: getBalance
    @Override
    public double getBalance() {
        return balance;
    }
    
    // IDL operation: transfer
    @Override
    public void transfer(Account toAccount, double amount) 
            throws InsufficientFunds {
        // Withdraw from this account
        this.withdraw(amount);  // May throw InsufficientFunds
        // Deposit to target account
        toAccount.deposit(amount);
    }
    
    // IDL readonly attributes
    @Override
    public String accountNumber() {
        return accountNumber;
    }
    
    @Override
    public String holderName() {
        return holderName;
    }
}
```

### **Example 3: Server Startup**
```java
import Banking.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

public class AccountServer {
    public static void main(String[] args) {
        try {
            // 1. Initialize ORB
            ORB orb = ORB.init(args, System.getProperties());
            
            // 2. Get Root POA (Portable Object Adapter)
            POA rootPoa = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA")
            );
            
            // 3. Create servant instance
            AccountImpl servant = new AccountImpl("ACC001", "John Doe");
            
            // 4. Activate servant in POA
            byte[] objectId = rootPoa.activate_object(servant);
            
            // 5. Get object reference from servant ID
            org.omg.CORBA.Object objRef = rootPoa.id_to_reference(objectId);
            Account accountRef = AccountHelper.narrow(objRef);
            
            // 6. Convert object reference to string (IOR - Interoperable Object Reference)
            String ior = orb.object_to_string(accountRef);
            System.out.println("Account IOR: " + ior);
            
            // 7. Activate POA Manager (required before handling requests)
            rootPoa.the_POAManager().activate();
            
            // 8. Print ready message
            System.out.println("Account Server ready and waiting for requests...");
            
            // 9. Start ORB event loop (blocks until shutdown)
            orb.run();
            
            // Cleanup (rarely reached in normal operation)
            orb.destroy();
            
        } catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

### **Example 4: Client Code**
```java
import Banking.*;
import org.omg.CORBA.*;

public class AccountClient {
    public static void main(String[] args) {
        Account account = null;
        
        try {
            // 1. Initialize ORB
            ORB orb = ORB.init(args, System.getProperties());
            
            // 2. Get object reference from IOR string
            // (In real scenarios, use Naming Service)
            String iorString = args[0];  // IOR passed from server
            org.omg.CORBA.Object objRef = orb.string_to_object(iorString);
            
            // 3. Narrow reference to Account interface
            account = AccountHelper.narrow(objRef);
            
            if (account == null) {
                System.err.println("Could not narrow Account reference");
                System.exit(1);
            }
            
            // 4. Invoke remote methods through stub
            System.out.println("===== Remote Method Invocations =====");
            
            // Call deposit
            System.out.println("\n1. Deposit $500");
            account.deposit(500.0);  // Stub marshals and sends to skeleton
            
            // Call getBalance
            System.out.println("\n2. Check Balance");
            double balance = account.getBalance();
            System.out.println("Current Balance: $" + balance);
            
            // Call withdraw
            System.out.println("\n3. Withdraw $200");
            account.withdraw(200.0);
            System.out.println("New Balance: $" + account.getBalance());
            
            // Call withdraw with insufficient funds
            System.out.println("\n4. Try to withdraw $500 (insufficient funds)");
            try {
                account.withdraw(500.0);
            } catch (Banking.InsufficientFunds ex) {
                System.out.println("Caught exception: " + ex.message);
            }
            
            System.out.println("\n===== Test Complete =====");
            
        } catch (org.omg.CORBA.SystemException ex) {
            System.err.println("CORBA System Exception: " + ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
```

### **Example 5: Using Naming Service**
```java
import Banking.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;

public class AccountClientWithNaming {
    public static void main(String[] args) {
        try {
            // 1. Initialize ORB
            ORB orb = ORB.init(args, System.getProperties());
            
            // 2. Get Naming Service root context
            org.omg.CORBA.Object namingContextObj = 
                orb.resolve_initial_references("NameService");
            NamingContext namingContext = 
                NamingContextHelper.narrow(namingContextObj);
            
            // 3. Create name array for lookup
            NameComponent[] name = new NameComponent[1];
            name[0] = new NameComponent("Account", "Banking");
            
            // 4. Look up Account object
            org.omg.CORBA.Object objRef = namingContext.resolve(name);
            Account account = AccountHelper.narrow(objRef);
            
            // 5. Use remote object
            account.deposit(1000.0);
            System.out.println("Balance: $" + account.getBalance());
            
        } catch (NotFound ex) {
            System.err.println("Name not found in Naming Service");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
```

---

## Exception Handling

### CORBA System Exceptions

| Exception | Cause | Handling |
|---|---|---|
| `org.omg.CORBA.COMM_FAILURE` | Network communication error | Retry logic, check ORB connectivity |
| `org.omg.CORBA.INV_OBJREF` | Invalid object reference | Validate IOR, re-lookup from Naming Service |
| `org.omg.CORBA.OBJECT_NOT_EXIST` | Remote object no longer exists | Try alternative servant, reconnect |
| `org.omg.CORBA.BAD_PARAM` | Invalid argument to method | Validate arguments before sending |
| `org.omg.CORBA.UNKNOWN` | Unknown server-side exception | Log and retry, check server logs |
| `org.omg.CORBA.NO_IMPLEMENT` | Operation not implemented | Check IDL definition, server capability |

### User-Defined Exceptions (IDL)
```java
// From Example 2: AccountImpl
try {
    account.withdraw(10000.0);
} catch (Banking.InsufficientFunds ex) {
    // Custom exception with message attribute
    System.err.println("Withdrawal failed: " + ex.message);
}

// Generic CORBA handling
catch (org.omg.CORBA.SystemException ex) {
    System.err.println("CORBA system error: " + ex.getMessage());
    ex.printStackTrace();
}
```

---

## Important Notes

### ✓ Key Points

1. **IDL is the Contract**
   - Defines interface before implementation
   - Enables language-independent development
   - Both client and server must respect the interface

2. **Stubs & Skeletons are Generated**
   - IDL compiler (idlj) generates these automatically
   - Developers write IDL and servant implementation
   - Never manually edit generated code

3. **ORB is the Communication Engine**
   - Handles method invocation routing
   - Manages object lifecycle
   - Provides threading and synchronization

4. **POA (Portable Object Adapter)**
   - Bridges servants and ORB
   - Manages servant activation strategies (Transient, Persistent, etc.)
   - Thread policies can be configured

5. **Object References (IOR)**
   - Unique identifier for remote objects
   - Can be serialized to string for distribution
   - Contains host, port, and object ID information

6. **IIOP Protocol**
   - TCP/IP-based wire protocol
   - Default port: 7777
   - Enables heterogeneous ORB communication

7. **RMI vs CORBA**
   - CORBA is language-independent (via IDL)
   - RMI is Java-specific (uses serialization)
   - CORBA has more complex setup but better interoperability

### ⚠ Common Pitfalls

- **Not activating POAManager**: Request handling won't work
- **Mismatched narrowing**: Always use Helper.narrow() for type safety
- **Forgetting ORB initialization**: Both client and server need ORB instance
- **Incorrect IOR format**: Verify IOR string when passing between processes
- **Network firewall blocking**: Default IIOP port 7777 may need opening

### 🔧 Compilation Workflow

```bash
# 1. Compile IDL to generate stubs and skeletons
idlj -fall Account.idl
# Generates: Account.java, AccountHelper.java, AccountPOA.java, etc.

# 2. Compile Java implementation
javac -cp . AccountImpl.java AccountServer.java AccountClient.java

# 3. Start Naming Service (if using)
orbd -ORBInitialPort 1050 &

# 4. Start server
java -cp . AccountServer

# 5. Start client (pass IOR from server output)
java -cp . AccountClient "IOR:..."
```

---
