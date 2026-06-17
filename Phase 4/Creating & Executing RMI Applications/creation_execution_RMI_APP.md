# Creating and Executing RMI Applications

---

## Table of Contents
1. [Overview](#overview)
2. [Key Characteristics](#key-characteristics)
3. [RMI Application — 5-Step Development Model](#rmi-application--5-step-development-model)
4. [Class & Interface Hierarchy](#class--interface-hierarchy)
5. [Step-by-Step Implementation](#step-by-step-implementation)
   - [Step 1: Define the Remote Interface](#step-1-define-the-remote-interface)
   - [Step 2: Implement the Remote Interface](#step-2-implement-the-remote-interface)
   - [Step 3: Create the Server](#step-3-create-the-server)
   - [Step 4: Create the Client](#step-4-create-the-client)
   - [Step 5: Compile and Run](#step-5-compile-and-run)
6. [Important Classes & Methods](#important-classes--methods)
7. [Full Working Example](#full-working-example)
8. [Execution Flow Diagram](#execution-flow-diagram)
9. [Stub Generation: rmic vs Java 5+](#stub-generation-rmic-vs-java-5)
10. [RMI Registry](#rmi-registry)
11. [Passing Objects in RMI](#passing-objects-in-rmi)
12. [Common Exceptions](#common-exceptions)
13. [Important Notes](#important-notes)

---

## Overview

An **RMI Application** consists of two programs:
- **Server** — creates remote objects, registers them with the RMI registry
- **Client** — looks up remote objects via registry and calls methods on them as if they were local

RMI relies on **stub** (client-side proxy) and **skeleton** (server-side dispatcher, deprecated after Java 1.2) to marshal/unmarshal method calls over the network using Java serialization.

---

## Key Characteristics

| Feature | Detail |
|---|---|
| Language | Java only (unlike CORBA) |
| Protocol | Java Remote Method Protocol (JRMP) |
| Registry Port | Default `1099` |
| Serialization | Java Object Serialization |
| Stub | Auto-generated client-side proxy |
| Skeleton | Server-side dispatcher (removed in Java 5+) |
| Security | Requires `SecurityManager` for dynamic class loading |
| Transport | TCP (underneath) |

---

## RMI Application — 5-Step Development Model

```
┌─────────────────────────────────────────────────────────────────────┐
│                  RMI Development Steps                              │
│                                                                     │
│  1. Define Remote Interface  (extends java.rmi.Remote)              │
│           │                                                         │
│           ▼                                                         │
│  2. Implement Remote Interface  (extends UnicastRemoteObject)       │
│           │                                                         │
│           ▼                                                         │
│  3. Create Server  (binds object to RMI Registry)                   │
│           │                                                         │
│           ▼                                                         │
│  4. Create Client  (looks up registry, calls methods)               │
│           │                                                         │
│           ▼                                                         │
│  5. Compile → [rmic if needed] → Start Registry → Run Server+Client │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Class & Interface Hierarchy

```
java.rmi.Remote  (marker interface)
    └── <<Your Remote Interface>>  (declares remote methods)
            └── <<Your Implementation Class>>
                    └── extends java.rmi.server.UnicastRemoteObject
                                │
                                ├── exports object over TCP automatically
                                └── provides default equals/hashCode/toString

java.rmi.registry.Registry
    └── java.rmi.registry.LocateRegistry   (factory — creates/locates registry)

java.rmi.Naming
    ├── bind(String url, Remote obj)
    ├── rebind(String url, Remote obj)
    ├── lookup(String url) : Remote
    ├── unbind(String url)
    └── list(String url) : String[]
```

---

## Step-by-Step Implementation

### Step 1: Define the Remote Interface

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

// Every remote interface must extend Remote
public interface Calculator extends Remote {

    // Every method MUST declare RemoteException (network can always fail)
    int add(int a, int b) throws RemoteException;
    int multiply(int a, int b) throws RemoteException;
}
```

**Rules for Remote Interface:**
- Must `extend java.rmi.Remote`
- Every method must `throws RemoteException`
- Parameters and return types must be **primitives** or **Serializable** objects

---

### Step 2: Implement the Remote Interface

```java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Extend UnicastRemoteObject to auto-export over JRMP
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

    // Constructor must also throw RemoteException (UnicastRemoteObject requires it)
    public CalculatorImpl() throws RemoteException {
        super(); // exports the object on an anonymous port
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b; // actual business logic runs on SERVER side
    }

    @Override
    public int multiply(int a, int b) throws RemoteException {
        return a * b;
    }
}
```

> **Alternative:** If you cannot extend `UnicastRemoteObject` (due to another superclass),
> call `UnicastRemoteObject.exportObject(this, 0)` manually in the constructor.

---

### Step 3: Create the Server

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorServer {

    public static void main(String[] args) {
        try {
            CalculatorImpl calc = new CalculatorImpl(); // create remote object

            // Start RMI registry on port 1099 (or use: rmiregistry command)
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the remote object to a name in the registry
            registry.rebind("CalculatorService", calc);
            // rebind() replaces existing binding; bind() throws if name exists

            System.out.println("Server ready. Waiting for client...");

        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

### Step 4: Create the Client

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {

    public static void main(String[] args) {
        try {
            // Connect to registry on the server (use server IP in real network)
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Look up the remote object by name → returns a Stub
            Calculator calc = (Calculator) registry.lookup("CalculatorService");

            // Call methods on stub — looks local, executes on server
            System.out.println("3 + 4 = " + calc.add(3, 4));
            System.out.println("3 * 4 = " + calc.multiply(3, 4));

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

---

### Step 5: Compile and Run

```bash
# 1. Compile all source files
javac Calculator.java CalculatorImpl.java CalculatorServer.java CalculatorClient.java

# 2. [Java < 5 only] Generate stub class using rmic
rmic CalculatorImpl
# Produces: CalculatorImpl_Stub.class (and CalculatorImpl_Skel.class for old JVMs)

# Java 5+ → stubs generated dynamically at runtime, rmic NOT needed

# 3. Start the server (registry is started inside server code via createRegistry)
java CalculatorServer

# 4. In a separate terminal, run the client
java CalculatorClient

# Expected output:
# 3 + 4 = 7
# 3 * 4 = 12
```

> If using external `rmiregistry` command instead of `createRegistry()`:
> ```bash
> start rmiregistry   # Windows
> rmiregistry &       # Linux/macOS
> java CalculatorServer
> ```

---

## Important Classes & Methods

### `java.rmi.Remote`
| Item | Description |
|---|---|
| Marker interface | No methods — just tags a class as remotely accessible |

---

### `java.rmi.server.UnicastRemoteObject`
| Method | Description |
|---|---|
| `UnicastRemoteObject()` | Constructor — exports object on anonymous port |
| `UnicastRemoteObject(int port)` | Exports on specific port |
| `exportObject(Remote obj, int port)` | Static — manually export an object (if not extending this class) |
| `unexportObject(Remote obj, boolean force)` | Removes object from RMI runtime |

---

### `java.rmi.registry.LocateRegistry`
| Method | Description |
|---|---|
| `createRegistry(int port)` | Creates and starts a registry on given port (in-process) |
| `getRegistry(String host, int port)` | Gets reference to an already-running registry |
| `getRegistry(int port)` | Gets reference on localhost at given port |

---

### `java.rmi.registry.Registry`
| Method | Description |
|---|---|
| `bind(String name, Remote obj)` | Binds object; throws `AlreadyBoundException` if name exists |
| `rebind(String name, Remote obj)` | Binds or replaces — preferred in servers |
| `lookup(String name)` | Returns remote reference (stub) by name |
| `unbind(String name)` | Removes binding |
| `list()` | Returns all registered names |

---

### `java.rmi.Naming` (older API — wraps Registry)
| Method | Equivalent URL Format |
|---|---|
| `Naming.bind("//host:port/Name", obj)` | `rmi://localhost:1099/CalculatorService` |
| `Naming.lookup("//host/Name")` | Returns Remote stub |
| `Naming.rebind(...)` | Preferred over bind |

---

## Full Working Example

**File structure:**
```
rmi_demo/
├── Calculator.java          ← Remote Interface
├── CalculatorImpl.java      ← Implementation
├── CalculatorServer.java    ← Server
└── CalculatorClient.java    ← Client
```

All four files shown in steps above constitute a **complete, runnable RMI application**.

Additional example — passing a **Serializable object**:

```java
// Serializable object to pass as argument/return value
import java.io.Serializable;

public class MathResult implements Serializable {
    private static final long serialVersionUID = 1L; // always define this!
    public int value;
    public String operation;

    public MathResult(int value, String operation) {
        this.value = value;
        this.operation = operation;
    }
}

// Updated interface
public interface Calculator extends Remote {
    MathResult addDetailed(int a, int b) throws RemoteException; // returns Serializable
}

// Usage in client:
MathResult result = calc.addDetailed(3, 4);
System.out.println(result.operation + " = " + result.value);
// Output: 3+4 = 7
```

---

## Execution Flow Diagram

```
CLIENT SIDE                        NETWORK                    SERVER SIDE
──────────────────────────────────────────────────────────────────────────

[CalculatorClient]
       │
       │  LocateRegistry.getRegistry("localhost", 1099)
       │─────────────────────────────────────────────────► [RMI Registry]
       │◄──────────────────────────────────────────────── returns Registry ref
       │
       │  registry.lookup("CalculatorService")
       │─────────────────────────────────────────────────► [RMI Registry]
       │◄──────────────────────────────────────────────── returns Stub object
       │
       │  calc.add(3, 4)          ┌─────────────────────► [CalculatorImpl]
  [Stub]──── marshals args ───────┤                              │
       │                          │  JRMP over TCP               │ executes add()
       │◄─────── unmarshals ──────┘◄──────────────── return 7 ──┘
       │  result = 7
       │
  prints: "3 + 4 = 7"

──────────────────────────────────────────────────────────────────────────

STARTUP SEQUENCE:

  Server Starts
       │
       ├─1─► new CalculatorImpl()          → object created & exported on TCP port
       │
       ├─2─► LocateRegistry.createRegistry(1099)   → registry starts on port 1099
       │
       └─3─► registry.rebind("CalculatorService", calc)  → name registered

  Client Starts
       │
       ├─1─► getRegistry("localhost", 1099)   → connects to registry
       │
       ├─2─► lookup("CalculatorService")      → gets stub
       │
       └─3─► stub.add(3,4)                   → remote call → result returned
```

---

## Stub Generation: rmic vs Java 5+

| Aspect | Java < 5 (rmic required) | Java 5+ (Dynamic Stubs) |
|---|---|---|
| Tool | `rmic ClassName` | Not needed |
| Stub class | `ClassName_Stub.class` generated on disk | Generated at runtime by JVM |
| Skeleton | `ClassName_Skel.class` generated | Removed entirely |
| Distribution | Stub `.class` must be given to client | Client only needs interface `.class` |
| Modern practice | Avoid | Use this |

---

## RMI Registry

| Concept | Detail |
|---|---|
| What it is | A simple name-to-remote-object directory service |
| Start method 1 | `LocateRegistry.createRegistry(1099)` inside server code |
| Start method 2 | `rmiregistry` command (external process, before server starts) |
| Default port | `1099` |
| Scope | Flat namespace — just string → stub mapping |
| Not a full JNDI | Cannot do hierarchical lookups like LDAP/JNDI |

> **Important:** If using the external `rmiregistry` command, the stub class must be on the classpath of the registry process **or** a codebase URL must be set.

---

## Passing Objects in RMI

| Type | How it's passed | Requirement |
|---|---|---|
| Primitives (`int`, `double`, etc.) | By value (copied) | None |
| Remote objects | By reference (stub returned) | Must implement `Remote` |
| Non-remote objects | By value (serialized copy) | Must implement `Serializable` |

```
Remote Object passed between client/server:
┌────────────┐     stub ref     ┌────────────┐
│   Client   │◄────────────────►│   Server   │
└────────────┘                  └────────────┘
  (holds stub)                 (holds real object)

Serializable Object passed:
┌────────────┐  serialize/copy  ┌────────────┐
│   Client   │◄────────────────►│   Server   │
└────────────┘                  └────────────┘
  (own copy)                      (own copy)
```

---

## Common Exceptions

| Exception | Cause | Fix |
|---|---|---|
| `RemoteException` | Network/marshaling failure during remote call | Catch on client; always declare in interface |
| `NotBoundException` | `lookup()` name not found in registry | Check spelling; ensure server ran `bind/rebind` first |
| `AlreadyBoundException` | `bind()` called when name already exists | Use `rebind()` instead |
| `ConnectException` | Registry not running or wrong port | Start registry; verify port |
| `ClassNotFoundException` | Stub/interface class not on client classpath | Add interface `.class` to client classpath |
| `java.io.NotSerializableException` | Passed object doesn't implement Serializable | Implement `Serializable` on the object class |
| `StubNotFoundException` | Stub class missing (Java < 5) | Run `rmic`; put stub on client classpath |
| `AccessException` | Security policy denied operation | Configure `SecurityManager` and policy file |

---

## Important Notes

1. **RemoteException is mandatory** — every method in a remote interface must declare `throws RemoteException`. Missing it causes a compile error.

2. **`rebind()` over `bind()`** — in server code always use `rebind()` so restarting the server doesn't throw `AlreadyBoundException`.

3. **`serialVersionUID`** — always explicitly define it on any `Serializable` class passed through RMI. Without it, JVM auto-generates one that can change with recompilation, breaking deserialization.

4. **Interface must be shared** — the client only needs the **interface** `.class` file (not the implementation). The server needs both. This is a fundamental RMI design rule.

5. **`UnicastRemoteObject` alternative** — if your implementation already has a superclass, do not extend `UnicastRemoteObject`. Instead call:
   ```java
   UnicastRemoteObject.exportObject(this, 0); // in constructor
   ```

6. **Port `0` = anonymous port** — `new UnicastRemoteObject()` / `super()` exports the object on a random available port. The registry separately runs on port `1099`. These are two different ports.

7. **createRegistry vs rmiregistry** — `createRegistry()` is simpler (in-process, no external command), but the registry dies when the server JVM exits. The external `rmiregistry` command stays alive independently.

8. **Dynamic stub loading (Java 5+)** — `rmic` is no longer needed. Stubs are generated by the JVM using `java.lang.reflect.Proxy` internally. Skeleton classes were removed entirely.

9. **SecurityManager** — required only when using dynamic class loading (codebase). For simple local RMI (interface on classpath), `SecurityManager` is optional in modern Java.

10. **RMI over network** — for client connecting to a remote machine, replace `"localhost"` in `getRegistry()` with the server's hostname/IP. Firewall must allow port `1099` and the object's export port.