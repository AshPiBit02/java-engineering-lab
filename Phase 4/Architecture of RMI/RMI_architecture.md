# Architecture of RMI (Remote Method Invocation)

## What is RMI?
**Remote Method Invocation (RMI)** is a Java API that allows a program running on one JVM to **invoke methods on an object residing in another JVM** — on the same machine or across a network.

> RMI is Java's native mechanism for **distributed computing** — it makes a remote object appear as if it were local. It works exclusively with Java (unlike CORBA which is language-neutral).

---

## Key Concepts

| Term | Description |
|---|---|
| **Remote Object** | An object whose methods can be called from another JVM |
| **Remote Interface** | Java interface that declares which methods are remotely accessible |
| **Stub** | Client-side proxy — looks like the real object but forwards calls over network |
| **Skeleton** | Server-side proxy — receives network calls and dispatches to actual object (Java < 1.2; auto-handled after) |
| **RMI Registry** | A naming service — maps names to remote objects (like a phone directory) |
| **Marshalling** | Converting method arguments into a byte stream for network transmission |
| **Unmarshalling** | Reconstructing objects from a byte stream on the other side |
| **Serialization** | Java mechanism used for marshalling — objects must implement `Serializable` |

---

## RMI Architecture — Layered View

```
+---------------------------------------------------------------+
|                        CLIENT JVM                             |
|                                                               |
|   Client Code                                                 |
|   remoteObj.add(5, 3)  <-- looks like a local call            |
|         |                                                     |
|      [ STUB ]          <-- client-side proxy                  |
|         |                                                     |
+---------|-----------------------------------------------------+
          |  marshals args → byte stream
          |
+---------|-----------------------------------------------------+
|         |           NETWORK (TCP/IP)                          |
+---------|-----------------------------------------------------+
          |
          |  byte stream → unmarshals args
+---------|-----------------------------------------------------+
|      [ SKELETON ]      <-- server-side dispatcher             |
|         |                                                     |
|   [ Remote Object ]    <-- actual implementation              |
|   add(5, 3) → 8                                               |
|                                                               |
|                        SERVER JVM                             |
+---------------------------------------------------------------+
```

---

## RMI Three-Layer Architecture

RMI is internally structured into **three layers**:

```
CLIENT SIDE                              SERVER SIDE
+-------------------+                  +-------------------+
|   Application     |                  |   Application     |
|   (Client Code)   |                  |   (Server Code)   |
+--------+----------+                  +----------+--------+
         |                                        |
+--------+----------+                  +----------+--------+
|   Stub / Proxy    |                  |  Skeleton/Dispatch|
| (Remote Ref Layer)|                  | (Remote Ref Layer)|
+--------+----------+                  +----------+--------+
         |                                        |
+--------+----------+                  +----------+--------+
| Transport Layer   |<================>| Transport Layer   |
| (TCP/IP)          |   Network        | (TCP/IP)          |
+-------------------+                  +-------------------+
```

### Layer Descriptions

| Layer | Location | Responsibility |
|---|---|---|
| **Application Layer** | Both sides | Client calls stub methods; server implements remote interface |
| **Stub / Skeleton Layer** (Remote Reference Layer) | Client / Server | Marshals/unmarshals params; manages remote references |
| **Transport Layer** | Both sides | Manages TCP connections; handles data transmission over network |

---

## RMI Registry — Naming Service

The **RMI Registry** is a separate process (or embedded service) that acts as a **lookup directory** for remote objects.

```
+------------------+          bind("Calculator", obj)        +------------------+
|   SERVER         |  -------------------------------------> |   RMI Registry   |
|                  |                                         |                  |
|  Creates Remote  |                                         |  "Calculator" -> |
|  Object          |                                         |   stub reference |
+------------------+                                         +--------+---------+
                                                                      |
+------------------+          lookup("Calculator")                    |
|   CLIENT         |  <---------------------------------------------  |
|                  |                                                  |
|  Gets Stub       |         returns stub                             |
|  Calls methods   |                                                  |
+------------------+                                                  |
                                                             +------------------+
                                                             | Default Port: 1099|
                                                             +------------------+
```

---

## Complete RMI Call Flow — Step by Step

```
SERVER STARTUP                          CLIENT CALL
      |                                      |
1. Define Remote Interface            7. Lookup registry:
   (extends Remote)                      Naming.lookup("rmi://host/Calc")
      |                                      |
2. Implement Remote Object            8. Receives STUB from registry
   (extends UnicastRemoteObject)             |
      |                                9. Client calls stub method:
3. Start RMI Registry                        stub.add(5, 3)
   (port 1099)                               |
      |                               10. STUB marshals args (5, 3)
4. Create remote object instance             |  serializes → byte stream
      |                               11. Sends over TCP to server
5. Bind to registry:                         |
   Naming.bind("Calc", obj)          12. SKELETON unmarshals (5, 3)
      |                                      |
6. Server READY                       13. Invokes actual method:
      |                                    remoteObj.add(5, 3) → 8
      |                                      |
      |                               14. Return value (8) marshalled
      |                                      |  sent back over TCP
      |                               15. STUB unmarshals result
      |                                      |
      |                               16. Client receives 8
      |                                      |
      +---------- running ----------->+
```

---

## Stub and Skeleton in Detail

### Stub (Client-Side Proxy)

```
+------------------------------------------+
|                  STUB                    |
|                                          |
|  Implements same Remote Interface        |
|  as the actual remote object             |
|                                          |
|  When client calls stub.method():        |
|    1. Marshal method name + args         |
|    2. Open TCP connection to server      |
|    3. Send marshalled data               |
|    4. Wait for response                  |
|    5. Unmarshal return value             |
|    6. Return result to client            |
+------------------------------------------+
```

### Skeleton (Server-Side Dispatcher)

```
+------------------------------------------+
|                SKELETON                  |
|  (auto-handled by JVM in Java 1.2+)      |
|                                          |
|  Listens on TCP port for incoming calls  |
|                                          |
|  When request arrives:                   |
|    1. Unmarshal method name + args       |
|    2. Invoke method on remote object     |
|    3. Marshal return value / exception   |
|    4. Send response back to stub         |
+------------------------------------------+
```

> From **Java 1.2 onwards**, skeletons are **no longer required** as separate classes — the JVM handles dispatching automatically via reflection. Stubs are also dynamically generated in **Java 5+** — no need to run `rmic` (RMI compiler).

---

## RMI vs Regular (Local) Method Call

```
LOCAL CALL                               REMOTE CALL (RMI)
                                         
obj.method(arg)                         stub.method(arg)
     |                                       |
     |  direct memory call                   |  marshal arg
     v                                       |  TCP send
  method executes                            v
     |                               server receives
     |                                       |
     v                                  unmarshal
  return value                               |
     |                               execute method
     v                                       |
  caller gets result                   marshal result
                                             |
                                       TCP send back
                                             |
                                      unmarshal result
                                             |
                                      caller gets result
```

---

## RMI Key Interfaces and Classes

### `java.rmi` Package

| Interface / Class | Type | Description |
|---|---|---|
| `Remote` | Interface | Marker interface — every remote interface must extend this |
| `RemoteException` | Exception | Must be declared in all remote method signatures |
| `Naming` | Class | Static methods to bind/lookup objects in RMI registry |
| `RMISecurityManager` | Class | Sets security policy for RMI (needed when loading remote stubs) |

### `java.rmi.server` Package

| Class | Description |
|---|---|
| `UnicastRemoteObject` | Base class for remote objects — exports object over TCP; handles stub generation |
| `RemoteObject` | Base class for `UnicastRemoteObject`; overrides `equals()`, `hashCode()`, `toString()` for remote semantics |
| `RemoteServer` | Abstract superclass of `UnicastRemoteObject` |

### `java.rmi.registry` Package

| Class / Method | Description |
|---|---|
| `LocateRegistry` | Locates or creates an RMI registry |
| `LocateRegistry.createRegistry(int port)` | Starts an embedded registry on given port |
| `LocateRegistry.getRegistry(String host, int port)` | Gets reference to existing registry |
| `Registry` | Interface — `bind()`, `rebind()`, `unbind()`, `lookup()`, `list()` |

### `Naming` Class Methods

| Method | Description |
|---|---|
| `Naming.bind(String name, Remote obj)` | Registers object under name — fails if name already taken |
| `Naming.rebind(String name, Remote obj)` | Registers object — overwrites if name already exists |
| `Naming.lookup(String name)` | Returns stub for the named remote object |
| `Naming.unbind(String name)` | Removes binding from registry |
| `Naming.list(String url)` | Returns array of all registered names in registry |

> **URL format for Naming:** `"rmi://hostname:port/objectName"` or just `"//hostname/objectName"`  
> Default port is **1099**.

---

## Marshalling and Serialization

```
METHOD CALL: calculator.add(5, 3)

MARSHALLING (at Stub)
+--------------------+
| Method ID / Name   |  "add"
+--------------------+
| Arg 1 type + value |  int, 5
+--------------------+
| Arg 2 type + value |  int, 3
+--------------------+
         |
    serialized to byte stream
         |
    sent over TCP
         |
    received at Skeleton
         |
UNMARSHALLING (at Skeleton)
+--------------------+
| Extract method     |  "add"
+--------------------+
| Reconstruct Arg 1  |  5
+--------------------+
| Reconstruct Arg 2  |  3
+--------------------+
         |
  invoke: obj.add(5, 3) = 8
         |
RETURN MARSHALLING
  8 → serialized → TCP → stub → 8 returned to client
```

> All argument and return types must be either:
> - **Primitive types** (`int`, `double`, etc.) — passed by value
> - **Serializable objects** — passed by value (copy sent)
> - **Remote objects** — passed by reference (stub sent)

---

## RMI Security

- RMI can **dynamically load classes** (stubs, parameters) from remote locations — requires a **Security Manager**.
- A **policy file** grants permissions for what RMI code can do (read files, open sockets, etc.).
- Without a security manager, dynamic class loading is disabled — classes must be on local classpath.

```java
if (System.getSecurityManager() == null) {
    System.setSecurityManager(new SecurityManager());
}
```

> In modern Java (9+), `RMISecurityManager` and dynamic class loading are deprecated.  
> For local development, security manager is often omitted.

---

## Advantages and Limitations of RMI

### Advantages
| | Description |
|---|---|
| **Pure Java** | No need for separate IDL or language bindings — use Java interfaces directly |
| **Transparent** | Remote calls look almost identical to local calls |
| **Serialization built-in** | Java's serialization handles marshalling automatically |
| **Garbage Collection** | Distributed GC — server knows when clients are done with remote objects |
| **Integration** | Works with Java security, threading, and exception handling natively |

### Limitations
| | Description |
|---|---|
| **Java-only** | Cannot communicate with non-Java systems (unlike CORBA, REST, gRPC) |
| **Tight coupling** | Client and server must share the same remote interface class |
| **Firewall issues** | Uses dynamic ports — harder to configure through firewalls |
| **Performance** | Serialization overhead — slower than direct local calls |
| **Versioning** | Changing remote interface can break existing clients |

---

## RMI vs CORBA — Quick Comparison

| Feature | RMI | CORBA |
|---|---|---|
| Language | Java only | Language neutral (Java, C++, Python...) |
| Interface Definition | Java interface | IDL (Interface Definition Language) |
| Protocol | JRMP (Java Remote Method Protocol) | IIOP (Internet Inter-ORB Protocol) |
| Complexity | Simpler | More complex |
| Interoperability | Java ↔ Java | Any language ↔ Any language |
| Use today | Rare (replaced by REST/gRPC) | Legacy enterprise systems |

---

## Important Notes

- Every **remote interface** must extend `java.rmi.Remote` — this is a marker interface (no methods).
- Every method in a remote interface must declare `throws RemoteException` — network failures are checked exceptions.
- Remote objects should extend `UnicastRemoteObject` OR manually call `UnicastRemoteObject.exportObject(this, 0)` in the constructor.
- `Naming.bind()` fails if name is already registered — use `Naming.rebind()` during development to avoid `AlreadyBoundException`.
- The RMI Registry must be **running before** the server binds — start it with `rmiregistry` command or `LocateRegistry.createRegistry(1099)` in code.
- **Arguments are passed by value** (deep copy via serialization) — changes to an object on the server do not affect the client's copy.
- **Remote objects are passed by reference** (stub is sent) — the client gets a proxy to the actual server-side object.
- `serialVersionUID` should be explicitly declared in all `Serializable` classes used in RMI to avoid `InvalidClassException` when classes evolve.
- From **Java 5+**, stubs are generated dynamically — no need to run `rmic` compiler manually.