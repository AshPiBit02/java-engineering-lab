# 🌐 Distributed Network Programming
---

## 📚 Table of Contents

- [1. What is Distributed Network Programming?](#1-what-is-distributed-network-programming)
- [2. TCP, UDP, IP Address and Ports](#2-tcp-udp-ip-address-and-ports)
- [3. Socket Programming using TCP and UDP](#3-socket-programming-using-tcp-and-udp)
- [4. Working with URLs and URL Connection Class](#4-working-with-urls-and-url-connection-class)
- [5. Email Handling using Java Mail API](#5-email-handling-using-java-mail-api)
- [6. Architecture of RMI](#6-architecture-of-rmi)
- [7. Creating and Executing RMI Applications](#7-creating-and-executing-rmi-applications)
- [8. Architecture of CORBA](#8-architecture-of-corba)
- [9. RMI vs CORBA](#9-rmi-vs-corba)
- [10. IDL and Simple CORBA Program](#10-idl-and-simple-corba-program)
- [11. Summary](#11-summary)

---

## 1. What is Distributed Network Programming?

**Distributed Network Programming** refers to writing programs that **communicate over a network** — enabling multiple machines or processes to exchange data, share resources, or invoke methods on remote systems.

```
┌──────────────────────────────────────────────────────────────┐
│           Distributed Network Programming                    │
│                                                              │
│   Machine A                       Machine B                  │
│   ┌─────────────────┐             ┌─────────────────┐        │
│   │  Java Program   │◄──Network──►│  Java Program   │        │
│   │  (Client)       │             │  (Server)       │        │
│   └─────────────────┘             └─────────────────┘        │
│                                                              │
│   Communication can be via:                                  │
│   ├── Sockets (TCP/UDP)       <- low level                   │
│   ├── URL/HTTP                <- web-based                   │
│   ├── Java Mail API           <- email                       │
│   ├── RMI                     <- Java-to-Java remote calls   │
│   └── CORBA                   <- cross-language remote calls │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Distributed Network Programming Overview**

Java's networking support lives primarily in:
- `java.net` — Sockets, URLs, InetAddress
- `java.rmi` — Remote Method Invocation
- `javax.mail` — Java Mail API
- `org.omg.CORBA` — CORBA support

---

## 2. TCP, UDP, IP Address and Ports

### IP Address

An **IP Address** uniquely identifies a device on a network.

| Type | Format | Example |
|------|--------|---------|
| IPv4 | 32-bit, dotted decimal | `192.168.1.1` |
| IPv6 | 128-bit, hexadecimal | `2001:0db8::1` |

```java
InetAddress addr = InetAddress.getByName("www.google.com");
System.out.println(addr.getHostAddress());  // IP address
System.out.println(addr.getHostName());     // hostname

InetAddress local = InetAddress.getLocalHost();
System.out.println(local.getHostAddress()); // this machine's IP
```

### Ports

A **port** is a logical endpoint (0–65535) on a device — identifies which application handles incoming data.

| Range | Type | Examples |
|-------|------|---------|
| 0–1023 | Well-known ports | HTTP=80, HTTPS=443, FTP=21, SMTP=25 |
| 1024–49151 | Registered ports | MySQL=3306, Tomcat=8080 |
| 49152–65535 | Dynamic/private | Used by client sockets |

### TCP vs UDP

```
┌──────────────────────────────────────────────────────────────┐
│                    TCP  vs  UDP                              │
│                                                              │
│   TCP (Transmission Control Protocol)                        │
│   ─────────────────────────────────────────────────          │
│   Connection-oriented  → handshake before data               │
│   Reliable             → guaranteed delivery, ordering       │
│   Slower               → overhead for reliability            │
│   Use: HTTP, FTP, Email, File Transfer                       │
│                                                              │
│   UDP (User Datagram Protocol)                               │
│   ─────────────────────────────────────────────────          │
│   Connectionless       → no handshake                        │
│   Unreliable           → no guarantee of delivery            │
│   Faster               → low overhead                        │
│   Use: Video streaming, DNS, Gaming, VoIP                    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — TCP vs UDP**

---

## 3. Socket Programming using TCP and UDP

A **Socket** is an endpoint for communication between two machines.

### TCP Socket Model

```
┌──────────────────────────────────────────────────────────────┐
│                  TCP Socket Communication                    │
│                                                              │
│   SERVER                           CLIENT                    │
│   ┌───────────────────┐             ┌──────────────────┐     │
│   │ ServerSocket(port)│             │ Socket(host,port)│     │
│   │ accept()  ◄───────┼─────────────┼── connect        │     │
│   │ getInputStream()  │◄────data───►│ getOutputStream()│     │
│   │ getOutputStream() │             │ getInputStream() │     │
│   │ close()           │             │ close()          │     │
│   └───────────────────┘             └──────────────────┘     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — TCP Socket Client-Server Model**

**TCP Server:**
```java
ServerSocket server = new ServerSocket(5000);
Socket client = server.accept();              // blocks until client connects

BufferedReader in  = new BufferedReader(
    new InputStreamReader(client.getInputStream()));
PrintWriter   out  = new PrintWriter(client.getOutputStream(), true);

String msg = in.readLine();
out.println("Echo: " + msg);
client.close();
server.close();
```

**TCP Client:**
```java
Socket socket = new Socket("localhost", 5000);

PrintWriter   out = new PrintWriter(socket.getOutputStream(), true);
BufferedReader in = new BufferedReader(
    new InputStreamReader(socket.getInputStream()));

out.println("Hello Server!");
System.out.println(in.readLine());  // Echo: Hello Server!
socket.close();
```

### UDP Socket Model

```java
// UDP Server
DatagramSocket server = new DatagramSocket(6000);
byte[] buf = new byte[1024];
DatagramPacket packet = new DatagramPacket(buf, buf.length);
server.receive(packet);                        // blocks
String msg = new String(packet.getData(), 0, packet.getLength());

// UDP Client
DatagramSocket client = new DatagramSocket();
byte[] data = "Hello".getBytes();
InetAddress addr = InetAddress.getByName("localhost");
DatagramPacket packet = new DatagramPacket(data, data.length, addr, 6000);
client.send(packet);
```

---

## 4. Working with URLs and URL Connection Class

A **URL (Uniform Resource Locator)** identifies a resource on the internet.

```
┌──────────────────────────────────────────────────────────────┐
│                  URL Structure                               │
│                                                              │
│   https://www.example.com:8080/path/page.html?q=java#top     │
│   │       │               │    │              │      │       │
│   protocol  host          port  path          query  anchor  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — URL Structure**

```java
URL url = new URL("https://www.example.com:8080/index.html?lang=java");

System.out.println(url.getProtocol());   // https
System.out.println(url.getHost());       // www.example.com
System.out.println(url.getPort());       // 8080
System.out.println(url.getPath());       // /index.html
System.out.println(url.getQuery());      // lang=java
```

### URLConnection

```java
URL url = new URL("https://api.example.com/data");
URLConnection conn = url.openConnection();
conn.setRequestProperty("Accept", "application/json");

BufferedReader in = new BufferedReader(
    new InputStreamReader(conn.getInputStream()));

String line;
while ((line = in.readLine()) != null)
    System.out.println(line);
in.close();
```

### HttpURLConnection

```java
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
int responseCode = conn.getResponseCode();   // 200, 404, etc.
```

---

## 5. Email Handling using Java Mail API

The **Java Mail API** (`javax.mail`) allows sending and receiving emails programmatically via **SMTP, POP3, IMAP** protocols.

```
┌──────────────────────────────────────────────────────────────┐
│               Java Mail API — Send Email Flow                │
│                                                              │
│   Java App                                                   │
│      │                                                       │
│      ▼                                                       │
│   Session (SMTP config + auth)                               │
│      │                                                       │
│      ▼                                                       │
│   MimeMessage (To, From, Subject, Body)                      │
│      │                                                       │
│      ▼                                                       │
│   Transport.send(message)                                    │
│      │                                                       │
│      ▼                                                       │
│   SMTP Server (Gmail / custom) ──► Recipient's inbox         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Java Mail API Send Flow**

```java
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");
props.put("mail.smtp.auth", "true");
props.put("mail.smtp.starttls.enable", "true");

Session session = Session.getInstance(props, new Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("sender@gmail.com", "password");
    }
});

Message msg = new MimeMessage(session);
msg.setFrom(new InternetAddress("sender@gmail.com"));
msg.setRecipient(Message.RecipientType.TO,
    new InternetAddress("receiver@example.com"));
msg.setSubject("Hello from Java!");
msg.setText("This is the email body.");
Transport.send(msg);
```

| Class | Role |
|-------|------|
| `Session` | Represents mail session with config |
| `MimeMessage` | Email message (To, From, Subject, Body) |
| `Transport` | Sends the message via SMTP |
| `Store` | Connects to mailbox (POP3/IMAP) |
| `Folder` | Represents inbox / folder |

---

## 6. Architecture of RMI

**RMI (Remote Method Invocation)** allows a Java program to **invoke methods on objects running in another JVM** — on the same or different machine.

```
┌──────────────────────────────────────────────────────────────┐
│                    RMI Architecture                          │
│                                                              │
│   CLIENT SIDE                    SERVER SIDE                 │
│   ┌──────────────────┐           ┌───────────────────────┐   │
│   │   Client         │           │   Remote Object       │   │
│   │   Program        │           │   (implements Remote) │   │
│   └────────┬─────────┘           └──────────┬────────────┘   │
│            │                                │                │
│   ┌────────▼─────────┐           ┌──────────▼────────────┐   │
│   │   Stub           │◄──────────│   Skeleton            │   │
│   │ (proxy object)   │  network  │ (receives calls)      │   │
│   └────────┬─────────┘           └───────────────────────┘   │
│            │                                                 │
│   ┌────────▼──────────────────────────────────────────────┐  │
│   │              RMI Registry (port 1099)                 │  │
│   │   Naming.bind("Hello", obj)  /  Naming.lookup("Hello")│  │
│   └───────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — RMI Architecture**

### Key Components

| Component | Role |
|-----------|------|
| **Remote Interface** | Declares methods callable remotely |
| **Remote Object** | Implements the Remote Interface |
| **Stub** | Client-side proxy — forwards calls to skeleton |
| **Skeleton** | Server-side — receives calls, invokes real object |
| **RMI Registry** | Name service — maps names to remote objects |

---

## 7. Creating and Executing RMI Applications

### Step 1 — Define Remote Interface

```java
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String sayHello(String name) throws RemoteException;
}
```

### Step 2 — Implement Remote Object

```java
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class HelloImpl extends UnicastRemoteObject implements Hello {
    public HelloImpl() throws RemoteException { super(); }

    public String sayHello(String name) throws RemoteException {
        return "Hello, " + name + " from Server!";
    }
}
```

### Step 3 — Server (Register Object)

```java
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(1099);       // start RMI registry
        Hello obj = new HelloImpl();
        Naming.rebind("Hello", obj);               // register
        System.out.println("Server ready...");
    }
}
```

### Step 4 — Client (Look up and Call)

```java
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) throws Exception {
        Hello stub = (Hello) Naming.lookup("rmi://localhost/Hello");
        System.out.println(stub.sayHello("Aasii")); // remote call
    }
}
```

### Execution Steps

```
1. javac *.java
2. Start rmiregistry (or use LocateRegistry.createRegistry())
3. java Server
4. java Client
```

---

## 8. Architecture of CORBA

**CORBA (Common Object Request Broker Architecture)** is a standard that allows objects written in **different languages** to communicate over a network via an **ORB (Object Request Broker)**.

```
┌──────────────────────────────────────────────────────────────┐
│                  CORBA Architecture                          │
│                                                              │
│  CLIENT (any language)         SERVER (any language)         │
│  ┌───────────────────┐         ┌───────────────────────┐     │
│  │  Client Program   │         │  Servant Object       │     │
│  └────────┬──────────┘         └──────────┬────────────┘     │
│           │                               │                  │
│  ┌────────▼──────────┐         ┌──────────▼────────────┐     │
│  │  Stub (IDL-gen)   │         │  Skeleton (IDL-gen)   │     │
│  └────────┬──────────┘         └──────────┬────────────┘     │
│           │                               │                  │
│  ┌────────▼───────────────────────────────▼────────────┐     │
│  │           ORB (Object Request Broker)               │     │
│  │      routes calls between client and server         │     │
│  └─────────────────────────────────────────────────────┘     │
│                                                              │
│           ┌──────────────────────────────┐                   │
│           │   Naming Service (CosNaming) │                   │
│           │   bind() / resolve()         │                   │
│           └──────────────────────────────┘                   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — CORBA Architecture**

### Key Components

| Component | Role |
|-----------|------|
| **IDL** | Interface Definition Language — defines interface in language-neutral way |
| **ORB** | Routes method calls between client and server |
| **Stub** | Client-side proxy (generated from IDL) |
| **Skeleton** | Server-side dispatcher (generated from IDL) |
| **Naming Service** | Maps names to CORBA objects (like RMI Registry) |
| **POA** | Portable Object Adapter — manages servant objects |

---

## 9. RMI vs CORBA

```
┌───────────────────────────────────────────────────────────────┐
│                    RMI  vs  CORBA                             │
│                                                               │
│   Feature          RMI                  CORBA                 │
│   ──────────────   ──────────────────   ───────────────────   │
│   Language         Java only            Language-neutral      │
│   Interface def    Java interface        IDL file             │
│   Protocol         JRMP / IIOP          IIOP                  │
│   Complexity       Simple               Complex               │
│   Setup            Easy                 Difficult             │
│   Performance      Good (Java-Java)      Overhead             │
│   Interop          Java-Java only       Any language          │
│   Registry         RMI Registry         Naming Service        │
│   Stub gen         rmic tool            idlj tool             │
│   Use case         Java microservices   Multi-language systems│
└───────────────────────────────────────────────────────────────┘
```

> **Fig. 8 — RMI vs CORBA Comparison**

| Feature | RMI | CORBA |
|---------|-----|-------|
| Language support | Java only | Any language |
| Interface | Java `interface` | IDL |
| Protocol | JRMP (default) / IIOP | IIOP |
| Complexity | Low | High |
| Best for | Java-to-Java distributed apps | Cross-language distributed systems |

---

## 10. IDL and Simple CORBA Program

**IDL (Interface Definition Language)** is a language-neutral way to define interfaces for CORBA objects. The `idlj` compiler generates Java stubs and skeletons from an IDL file.

### IDL File

```idl
// Hello.idl
module HelloApp {
    interface Hello {
        string sayHello(in string name);
    };
};
```

### Generate Java Code from IDL

```bash
idlj -fall Hello.idl     # generates stub + skeleton + helper classes
```

### Server Implementation

```java
public class HelloServant extends HelloPOA {
    public String sayHello(String name) {
        return "Hello, " + name + " from CORBA!";
    }
}

public class HelloServer {
    public static void main(String[] args) throws Exception {
        ORB orb = ORB.init(args, null);
        POA rootPOA = POAHelper.narrow(
            orb.resolve_initial_references("RootPOA"));
        rootPOA.the_POAManager().activate();

        HelloServant servant = new HelloServant();
        org.omg.CORBA.Object ref = rootPOA.servant_to_reference(servant);

        NamingContextExt ncRef = NamingContextExtHelper.narrow(
            orb.resolve_initial_references("NameService"));
        ncRef.bind(ncRef.to_name("Hello"), ref);

        orb.run();
    }
}
```

### Client

```java
public class HelloClient {
    public static void main(String[] args) throws Exception {
        ORB orb = ORB.init(args, null);
        NamingContextExt ncRef = NamingContextExtHelper.narrow(
            orb.resolve_initial_references("NameService"));

        Hello hello = HelloHelper.narrow(ncRef.resolve_str("Hello"));
        System.out.println(hello.sayHello("Aasii"));
    }
}
```

### Execution Steps

```
1. idlj -fall Hello.idl        // generate stubs
2. javac *.java HelloApp/*.java
3. orbd -ORBInitialPort 1050   // start naming service
4. java HelloServer -ORBInitialPort 1050
5. java HelloClient -ORBInitialPort 1050
```

---

## 11. Summary

```
┌──────────────────────────────────────────────────────────────┐
│          Phase 4 — Distributed Network Programming           │
│                                                              │
│   Topic              Key Technology        Package           │
│   ─────────────────  ──────────────────    ──────────────    │
│   IP & Ports         InetAddress           java.net          │
│   TCP Sockets        Socket/ServerSocket   java.net          │
│   UDP Sockets        DatagramSocket        java.net          │
│   URLs               URL, URLConnection    java.net          │
│   Email              SMTP via JavaMail     javax.mail        │
│   RMI                Remote, Naming        java.rmi          │
│   CORBA              ORB, IDL, POA         org.omg.CORBA     │
└──────────────────────────────────────────────────────────────┘
```

| Topic | Key Class/API | Protocol | Use Case |
|-------|--------------|----------|---------|
| TCP Sockets | `Socket`, `ServerSocket` | TCP | Reliable client-server comm |
| UDP Sockets | `DatagramSocket`, `DatagramPacket` | UDP | Fast, no-guarantee comm |
| URL | `URL`, `HttpURLConnection` | HTTP/HTTPS | Web resource access |
| Email | `Session`, `MimeMessage`, `Transport` | SMTP | Send/receive emails |
| RMI | `Remote`, `UnicastRemoteObject`, `Naming` | JRMP | Java-Java remote calls |
| CORBA | `ORB`, `POA`, IDL-generated stubs | IIOP | Cross-language remote calls |

```
Low-level  ->  Sockets (TCP/UDP) — full control, more code
Mid-level  ->  URL/HTTP — simple web access
High-level ->  RMI  — Java-to-Java, easy setup
Enterprise ->  CORBA — any language, complex setup
```