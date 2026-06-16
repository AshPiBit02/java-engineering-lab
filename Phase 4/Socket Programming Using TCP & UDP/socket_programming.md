# Socket Programming in Java

## What is a Socket?
A **socket** is one endpoint of a two-way communication link between two programs running on a network.  
It is bound to a **port number** so the TCP/UDP layer can identify the application data is destined for.

```
Client                          Server
  |                               |
  |------- Connection Request --->|
  |<------ Accept + Response -----|
  |<===== Data Exchange =========>|
  |------- Close ---------------->|
```

---

## Socket Communication Model

```
+-------------------+                    +-------------------+
|      CLIENT       |                    |      SERVER       |
|                   |                    |                   |
|  Socket(host,port)|--- TCP/UDP/IP ---->|  ServerSocket /   |
|  InputStream      |<==================>|  DatagramSocket   |
|  OutputStream     |                    |  InputStream      |
|  close()          |                    |  OutputStream     |
+-------------------+                    +-------------------+
         |                                        |
         +------------ Network Layer -------------+
                    (IP + Port Addressing)
```

---

## Two Types of Socket Programming

| Feature | TCP Socket | UDP Socket |
|---|---|---|
| Connection | Connection-oriented | Connectionless |
| Reliability | Guaranteed delivery | No guarantee |
| Order | In-order delivery | May arrive out of order |
| Speed | Slower (overhead) | Faster |
| Classes Used | `Socket`, `ServerSocket` | `DatagramSocket`, `DatagramPacket` |
| Use Case | File transfer, HTTP, Chat | DNS, Video streaming, Gaming |

---

## Java Packages Required

```java
import java.net.*;   // Socket, ServerSocket, DatagramSocket, InetAddress
import java.io.*;    // InputStream, OutputStream, BufferedReader, PrintWriter
```

---

## TCP Socket — Quick Overview

TCP provides a **reliable, ordered, connection-based** communication channel.

### Flow
```
SERVER                          CLIENT
  |                               |
ServerSocket(port)                |
  |                               |
accept() [blocks]        Socket(host, port)
  |<----- SYN / SYN-ACK / ACK -->|   ← 3-way handshake
  |                               |
  |<======= read / write ========>|
  |                               |
close()                        close()
```

### Minimal Example

**Server:**
```java
ServerSocket server = new ServerSocket(5000);
Socket socket = server.accept();                        // waits for client

BufferedReader in = new BufferedReader(
    new InputStreamReader(socket.getInputStream()));
String msg = in.readLine();
System.out.println("Client says: " + msg);

socket.close();
server.close();
```

**Client:**
```java
Socket socket = new Socket("localhost", 5000);

PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
out.println("Hello Server!");

socket.close();
```

> Detailed TCP implementation → see `4.2a_TCP_Socket.md`

---

## UDP Socket — Quick Overview

UDP sends **independent packets (datagrams)** with no connection setup — fast but unreliable.

### Flow
```
SENDER                          RECEIVER
  |                               |
DatagramSocket()         DatagramSocket(port)
  |                               |
  |--- DatagramPacket(data) ----->|   ← no handshake
  |                               |
  |<--- DatagramPacket(reply) ----|
  |                               |
close()                        close()
```

### Minimal Example

**Sender:**
```java
DatagramSocket socket = new DatagramSocket();
byte[] data = "Hello UDP!".getBytes();
InetAddress address = InetAddress.getByName("localhost");

DatagramPacket packet = new DatagramPacket(data, data.length, address, 6000);
socket.send(packet);
socket.close();
```

**Receiver:**
```java
DatagramSocket socket = new DatagramSocket(6000);
byte[] buffer = new byte[1024];

DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
socket.receive(packet);                                 // blocks until data arrives

String msg = new String(packet.getData(), 0, packet.getLength());
System.out.println("Received: " + msg);
socket.close();
```

> Detailed UDP implementation → see `4.2b_UDP_Socket.md`

---

## Key Classes at a Glance

| Class | Package | Purpose |
|---|---|---|
| `Socket` | `java.net` | TCP client-side socket |
| `ServerSocket` | `java.net` | TCP server — listens for connections |
| `DatagramSocket` | `java.net` | UDP socket (send & receive) |
| `DatagramPacket` | `java.net` | UDP data container (packet wrapper) |
| `InetAddress` | `java.net` | Represents IP address |

---

## Important Notes

- **Port range:** 0–65535. Ports 0–1023 are reserved (use 1024+ for custom apps).
- Always **close sockets** in a `finally` block or use **try-with-resources** to avoid resource leaks.
- TCP `accept()` and UDP `receive()` are **blocking calls** — they halt execution until data/connection arrives.
- For handling **multiple clients** in TCP → use **multithreading** (one thread per client).

---
