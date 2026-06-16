# 🌐 TCP, UDP, IP Address and Ports

---

## 📚 Table of Contents

- [1. Networking Fundamentals](#1-networking-fundamentals)
- [2. IP Address](#2-ip-address)
  - [2.1 What is an IP Address?](#21-what-is-an-ip-address)
  - [2.2 IPv4 vs IPv6](#22-ipv4-vs-ipv6)
  - [2.3 Types of IP Addresses](#23-types-of-ip-addresses)
  - [2.4 InetAddress Class](#24-inetaddress-class)
- [3. Ports](#3-ports)
  - [3.1 What is a Port?](#31-what-is-a-port)
  - [3.2 Port Ranges](#32-port-ranges)
  - [3.3 Why IP + Port Together?](#33-why-ip--port-together)
- [4. The TCP/IP Protocol Stack](#4-the-tcpip-protocol-stack)
- [5. TCP — Transmission Control Protocol](#5-tcp--transmission-control-protocol)
  - [5.1 What is TCP?](#51-what-is-tcp)
  - [5.2 TCP Three-Way Handshake](#52-tcp-three-way-handshake)
  - [5.3 How TCP Ensures Reliability](#53-how-tcp-ensures-reliability)
  - [5.4 TCP Connection Termination](#54-tcp-connection-termination)
- [6. UDP — User Datagram Protocol](#6-udp--user-datagram-protocol)
  - [6.1 What is UDP?](#61-what-is-udp)
  - [6.2 UDP Communication Flow](#62-udp-communication-flow)
- [7. TCP vs UDP](#7-tcp-vs-udp)
- [8. Java Classes for Networking](#8-java-classes-for-networking)
- [9. Complete Working Flow — Client-Server](#9-complete-working-flow--client-server)
- [10. C++ vs Java Networking](#10-c-vs-java-networking)
- [11. Summary](#11-summary)

---

## 1. Networking Fundamentals

Computer networking allows two or more devices to **exchange data**. For any communication to happen, a device needs to know:

```
┌──────────────────────────────────────────────────────────────┐
│            What's Needed for Network Communication           │
│                                                              │
│   1. WHO to talk to        ->  IP Address                    │
│   2. WHICH application     ->  Port Number                   │
│   3. HOW to talk            ->  Protocol (TCP or UDP)        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Three Requirements for Network Communication**

This unit covers the **foundation** of Java networking — found in the `java.net` package — before moving to actual socket programming (4.2).

---

## 2. IP Address

### 2.1 What is an IP Address?

An **IP (Internet Protocol) Address** is a unique numerical identifier assigned to every device on a network. It works like a **postal address** for data packets.

```
┌──────────────────────────────────────────────────────────────┐
│                  IP Address Analogy                          │
│                                                              │
│   Postal Mail System          Computer Network               │
│   ──────────────────          ─────────────────────          │
│   House Address           ->  IP Address                     │
│   Apartment Number        ->  Port Number                    │
│   Letter                  ->  Data Packet                    │
│   Postal Service          ->  Internet/Network               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — IP Address as a Postal Address Analogy**

---

### 2.2 IPv4 vs IPv6

```
┌──────────────────────────────────────────────────────────────┐
│                   IPv4  vs  IPv6                             │
│                                                              │
│   IPv4                          IPv6                         │
│   ─────────────────────         ─────────────────────────    │
│   32-bit address                128-bit address              │
│   4 octets, dotted decimal      8 groups, hexadecimal        │
│   192.168.1.1                   2001:0db8:0000:0000:0000:    │
│                                  0000:0000:0001              │
│   ~4.3 billion addresses        ~340 undecillion addresses   │
│   Running out of addresses      Solves IPv4 exhaustion       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — IPv4 vs IPv6 Comparison**

| Feature | IPv4 | IPv6 |
|---------|------|------|
| Bit length | 32-bit | 128-bit |
| Format | `192.168.1.1` | `2001:0db8::1` |
| Address space | ~4.3 billion | ~340 undecillion |
| Header size | 20 bytes | 40 bytes |
| Adoption | Still dominant | Growing |

---

### 2.3 Types of IP Addresses

| Type | Range/Example | Description |
|------|---------------|-------------|
| **Loopback** | `127.0.0.1` | Refers to the local machine itself |
| **Private** | `192.168.x.x`, `10.x.x.x` | Used within local networks (LAN) |
| **Public** | Assigned by ISP | Globally unique, internet-routable |
| **Static** | Fixed | Manually assigned, doesn't change |
| **Dynamic** | Via DHCP | Auto-assigned, can change over time |

```
┌──────────────────────────────────────────────────────────────┐
│                Loopback vs LAN vs Internet                   │
│                                                              │
│   This Machine          Local Network          Internet      │
│   127.0.0.1             192.168.1.x            Public IP     │
│   (localhost)           (router-assigned)      (ISP-assigned)│
│       │                       │                      │       │
│       └───────────────────────┴──────────────────────┘       │
│              all reachable via different IP scopes           │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — IP Address Scope Levels**

---

### 2.4 InetAddress Class

Java represents an IP address using the **`InetAddress`** class (`java.net` package).

```java
import java.net.InetAddress;

// Get IP of a hostname
InetAddress addr = InetAddress.getByName("www.google.com");
System.out.println("Host Name: " + addr.getHostName());
System.out.println("IP Address: " + addr.getHostAddress());

// Get local machine's address
InetAddress local = InetAddress.getLocalHost();
System.out.println("Local IP: " + local.getHostAddress());

// Loopback address
InetAddress loopback = InetAddress.getLoopbackAddress();
System.out.println("Loopback: " + loopback.getHostAddress()); // 127.0.0.1

// Get ALL IPs for a host (a host can have multiple)
InetAddress[] all = InetAddress.getAllByName("www.google.com");
for (InetAddress a : all)
    System.out.println(a.getHostAddress());
```

### Key InetAddress Methods

| Method | Description |
|--------|-------------|
| `getByName(String host)` | Resolves hostname to IP |
| `getAllByName(String host)` | Returns all IPs for a host |
| `getLocalHost()` | Returns local machine's address |
| `getLoopbackAddress()` | Returns `127.0.0.1` |
| `getHostName()` | Returns hostname |
| `getHostAddress()` | Returns IP as string |
| `isReachable(int timeout)` | Pings the address |

---

## 3. Ports

### 3.1 What is a Port?

A **port** is a **logical number (0–65535)** that identifies a specific **application or service** running on a device. While an IP address identifies the *machine*, a port identifies the *application* on that machine.

```
┌──────────────────────────────────────────────────────────────┐
│                IP Address + Port = Socket Address            │
│                                                              │
│   IP: 192.168.1.10                                           │
│   │                                                          │
│   ├── Port 80    -> Web Server (HTTP)                        │
│   ├── Port 443   -> Web Server (HTTPS)                       │
│   ├── Port 3306  -> MySQL Database                           │
│   ├── Port 25    -> Mail Server (SMTP)                       │
│   └── Port 8080  -> Custom Java App                          │
│                                                              │
│   One machine, many applications -- each on its own port     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — One IP, Multiple Ports for Multiple Services**

---

### 3.2 Port Ranges

| Range | Category | Examples |
|-------|----------|---------|
| 0 – 1023 | **Well-known ports** | HTTP=80, HTTPS=443, FTP=21, SSH=22, SMTP=25, DNS=53 |
| 1024 – 49151 | **Registered ports** | MySQL=3306, PostgreSQL=5432, Tomcat=8080, RMI=1099 |
| 49152 – 65535 | **Dynamic / Ephemeral ports** | Temporarily assigned to client sockets |

```
┌──────────────────────────────────────────────────────────────┐
│                    Port Range Chart                          │
│                                                              │
│   0 ────────── 1023 ───────── 49151 ─────────────── 65535    │
│   │  Well-known  │  Registered   │   Dynamic/Private  │      │
│   │  (system)    │  (app-defined)│   (client-side)    │      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Port Number Ranges**

### Common Well-Known Ports

| Port | Protocol | Service |
|------|----------|---------|
| 20/21 | TCP | FTP |
| 22 | TCP | SSH |
| 23 | TCP | Telnet |
| 25 | TCP | SMTP (Email sending) |
| 53 | TCP/UDP | DNS |
| 80 | TCP | HTTP |
| 110 | TCP | POP3 (Email receiving) |
| 443 | TCP | HTTPS |
| 1099 | TCP | Java RMI Registry |

---

### 3.3 Why IP + Port Together?

```
┌──────────────────────────────────────────────────────────────┐
│              Socket Address = IP + Port                      │
│                                                              │
│   IP Address alone   ->  identifies the DEVICE only          │
│   Port alone         ->  meaningless without a device        │
│   IP + Port together ->  uniquely identifies an application  │
│                          endpoint  =  SOCKET ADDRESS         │
│                                                              │
│   Example: 192.168.1.10:8080                                 │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — Socket Address = IP + Port**

```java
InetSocketAddress socketAddr = new InetSocketAddress("192.168.1.10", 8080);
System.out.println(socketAddr.getAddress());  // /192.168.1.10
System.out.println(socketAddr.getPort());     // 8080
```

---

## 4. The TCP/IP Protocol Stack

```
┌──────────────────────────────────────────────────────────────┐
│                  TCP/IP Layered Model                        │
│                                                              │
│   Application Layer    ->  HTTP, FTP, SMTP, Java apps        │
│   Transport Layer       ->  TCP / UDP   <- this unit's focus │
│   Network Layer         ->  IP (IP Address)                  │
│   Data Link Layer       ->  Ethernet, WiFi (MAC Address)     │
│   Physical Layer        ->  Cables, radio signals            │
│                                                              │
│   Data flows DOWN when sending, UP when receiving            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 8 — TCP/IP Protocol Stack**

> 💡 **TCP** and **UDP** both operate at the **Transport Layer** — they decide *how* data is delivered, while **IP** (Network Layer) decides *where* it goes.

---

## 5. TCP — Transmission Control Protocol

### 5.1 What is TCP?

**TCP** is a **connection-oriented**, **reliable** transport protocol. Before any data is sent, a connection must be established between sender and receiver.

```
┌──────────────────────────────────────────────────────────────┐
│                    TCP Core Properties                       │
│                                                              │
│   ✅ Connection-oriented  -> handshake required before data  │
│   ✅ Reliable             -> guaranteed delivery             │
│   ✅ Ordered              -> packets arrive in correct order │
│   ✅ Error-checked        -> retransmits lost/corrupt packets│
│   ✅ Flow controlled      -> prevents overwhelming receiver  │
│   ❌ Slower               -> overhead of all the above       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 9 — TCP Core Properties**

---

### 5.2 TCP Three-Way Handshake

Before data transfer, TCP establishes a connection using a **3-step handshake**:

```
┌──────────────────────────────────────────────────────────────┐
│              TCP Three-Way Handshake                         │
│                                                              │
│   CLIENT                                    SERVER           │
│      │                                         │             │
│      │  Step 1:  SYN  (synchronize) ──────────►│             │
│      │           "Can we connect?"             │             │
│      │                                         │             │
│      │◄────────── Step 2:  SYN-ACK  ───────────│             │
│      │           "Yes, let's connect"          │             │
│      │                                         │             │
│      │  Step 3:  ACK  (acknowledge) ──────────►│             │
│      │           "Confirmed, starting"         │             │
│      │                                         │             │
│      │◄═══════ Connection Established ═══════► │             │
│      │                                         │             │
│      │◄──────── Data Transfer Begins ─────────►│             │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 10 — TCP Three-Way Handshake**

| Step | Direction | Flag | Meaning |
|------|-----------|------|---------|
| 1 | Client → Server | `SYN` | "I want to connect" |
| 2 | Server → Client | `SYN-ACK` | "I acknowledge, I want to connect too" |
| 3 | Client → Server | `ACK` | "Acknowledged, connection ready" |

---

### 5.3 How TCP Ensures Reliability

```
┌──────────────────────────────────────────────────────────────┐
│              TCP Reliability Mechanisms                      │
│                                                              │
│   Sequence Numbers   -> every byte numbered, ensures order   │
│   Acknowledgments    -> receiver confirms each packet        │
│   Retransmission     -> resend if ACK not received in time   │
│   Checksums          -> detect corrupted data                │
│   Flow Control       -> receiver tells sender how much to    │
│                          send (sliding window)               │
│   Congestion Control -> slows down if network is congested   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 11 — TCP Reliability Mechanisms**

```
┌──────────────────────────────────────────────────────────────┐
│            Packet Loss & Retransmission Example              │
│                                                              │
│   Sender                                    Receiver         │
│      │── Packet 1 ──────────────────────────► │              │
│      │◄───────────────────── ACK 1 ────────── │              │
│      │── Packet 2 ──────X (lost in transit)   │              │
│      │     (timeout, no ACK received)         │              │
│      │── Packet 2 (resent) ──────────────────►│              │
│      │◄───────────────────── ACK 2 ────────── │              │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 12 — TCP Retransmission on Packet Loss**

---

### 5.4 TCP Connection Termination

TCP closes connections using a **four-way termination** (FIN/ACK exchange):

```
┌──────────────────────────────────────────────────────────────┐
│              TCP Connection Termination                      │
│                                                              │
│   CLIENT                                    SERVER           │
│      │  FIN  (finish) ───────────────────────► │             │
│      │◄──────────────── ACK ───────────────────│             │
│      │◄──────────────── FIN ───────────────────│             │
│      │  ACK ──────────────────────────────────►│             │
│      │                                         │             │
│      │◄═══════ Connection Closed ═══════════►  │             │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 13 — TCP Connection Termination**

---

## 6. UDP — User Datagram Protocol

### 6.1 What is UDP?

**UDP** is a **connectionless**, **unreliable** transport protocol — there's no handshake, no guarantee of delivery, and no ordering. In exchange, it's **much faster** than TCP.

```
┌──────────────────────────────────────────────────────────────┐
│                    UDP Core Properties                       │
│                                                              │
│   ❌ Connectionless        -> no handshake, just send        │
│   ❌ Unreliable            -> no delivery guarantee          │
│   ❌ Unordered             -> packets may arrive out of order│
│   ❌ No error recovery     -> lost packets are simply lost   │
│   ✅ Fast                  -> minimal overhead               │
│   ✅ Lightweight           -> smaller header (8 bytes)       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 14 — UDP Core Properties**

---

### 6.2 UDP Communication Flow

```
┌──────────────────────────────────────────────────────────────┐
│                  UDP Communication Flow                      │
│                                                              │
│   SENDER                                    RECEIVER         │
│      │                                         │             │
│      │── Datagram 1 ─────────────────────────►│  (arrives)   │
│      │── Datagram 2 ─────────X (lost) ──────   │  (never     │
│      │                                            arrives)   │
│      │── Datagram 3 ─────────────────────────►│  (arrives)   │
│      │                                                       │
│   No handshake. No acknowledgment. No retransmission.        │
│   Sender just "fires and forgets" each packet.               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 15 — UDP "Fire and Forget" Communication**

> 💡 In UDP, each unit of data is called a **datagram** — a self-contained packet with no relationship to others.

---

## 7. TCP vs UDP

```
┌──────────────────────────────────────────────────────────────┐
│                      TCP  vs  UDP                            │
│                                                              │
│   Feature           TCP                    UDP               │
│   ───────────────   ──────────────────     ───────────────   │
│   Connection        Connection-oriented    Connectionless    │
│   Reliability        Reliable               Unreliable       │
│   Ordering           Guaranteed ordered      Not guaranteed  │
│   Speed              Slower                 Faster           │
│   Header size        20 bytes               8 bytes          │
│   Handshake          3-way handshake        None             │
│   Error checking     Yes + retransmission   Checksum only    │
│   Flow control       Yes                    No               │
│   Data unit          Stream of bytes        Datagrams        │
│   Java class         Socket/ServerSocket    DatagramSocket   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 16 — Full TCP vs UDP Comparison**

| Feature | TCP | UDP |
|---------|-----|-----|
| Type | Connection-oriented | Connectionless |
| Reliability | ✅ Guaranteed | ❌ Best-effort |
| Speed | Slower | Faster |
| Ordering | ✅ In order | ❌ May be out of order |
| Use cases | Web, Email, File Transfer | Streaming, Gaming, DNS, VoIP |
| Java class | `Socket`, `ServerSocket` | `DatagramSocket`, `DatagramPacket` |

> 📄 *Full socket programming implementation covered in `4.2 — Socket Programming using TCP and UDP`*

---

## 8. Java Classes for Networking

All these classes live in the **`java.net`** package:

| Class | Purpose |
|-------|---------|
| `InetAddress` | Represents an IP address |
| `InetSocketAddress` | Represents IP + Port together |
| `Socket` | TCP client-side connection |
| `ServerSocket` | TCP server-side listener |
| `DatagramSocket` | UDP socket (both client and server) |
| `DatagramPacket` | UDP data packet |
| `URL` | Represents a web resource address |
| `URLConnection` | Connection to a URL resource |

```java
// Quick reference snippet
InetAddress       ip   = InetAddress.getByName("localhost");
InetSocketAddress sock = new InetSocketAddress(ip, 8080);

System.out.println("IP: " + ip.getHostAddress() + ", Port: " + sock.getPort());
```

---

## 9. Complete Working Flow — Client-Server

```
┌──────────────────────────────────────────────────────────────┐
│         Complete IP + Port + Protocol Working Flow           │
│                                                              │
│   1. SERVER starts and BINDS to an IP + Port                 │
│      ServerSocket server = new ServerSocket(5000);           │
│            │                                                 │
│            ▼                                                 │
│   2. SERVER LISTENS for incoming connections                 │
│      server.accept();   <- blocks until client connects      │
│            │                                                 │
│            ▼                                                 │
│   3. CLIENT specifies SERVER's IP + Port to connect          │
│      Socket client = new Socket("192.168.1.10", 5000);       │
│            │                                                 │
│            ▼                                                 │
│   4. TCP HANDSHAKE happens automatically (SYN/SYN-ACK/ACK)   │
│            │                                                 │
│            ▼                                                 │
│   5. CONNECTION ESTABLISHED — data flows both ways           │
│      Streams opened: getInputStream() / getOutputStream()    │
│            │                                                 │
│            ▼                                                 │
│   6. CONNECTION CLOSED (FIN/ACK exchange)                    │
│      socket.close();  server.close();                        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 17 — Complete Networking Working Flow**

### Identifying Components in Code

```java
// SERVER — binds IP (implicit, all interfaces) + Port
ServerSocket server = new ServerSocket(5000);   // listens on port 5000

// CLIENT — must know SERVER's IP + Port
Socket client = new Socket("192.168.1.10", 5000); // IP + Port

// Verify connection details
System.out.println("Connected to: " + client.getInetAddress());
System.out.println("On port: "      + client.getPort());
System.out.println("Local port: "   + client.getLocalPort());
```

> 📄 *Full client-server socket programs covered in `4.2 — Socket Programming using TCP and UDP`*

---

## 10. C++ vs Java Networking

| Feature | C++ | Java |
|---------|-----|------|
| Socket API | Berkeley sockets (`socket()`, `bind()`) | `Socket`, `ServerSocket` classes |
| IP representation | `struct sockaddr_in` | `InetAddress` class |
| Platform dependency | OS-specific (Winsock vs POSIX) | Platform-independent |
| Error handling | Return codes / `errno` | Exceptions (`IOException`) |
| Memory management | Manual buffer management | Automatic (GC + Streams) |
| Abstraction level | Low-level, more control | High-level, easier to use |

> 🆚 **Key Difference** — C++ networking requires OS-specific APIs (Winsock on Windows, Berkeley sockets on Linux/Mac) with manual buffer and error handling. Java abstracts all of this into a clean, **platform-independent** `java.net` API with **exception-based** error handling.

---

## 11. Summary

```
┌──────────────────────────────────────────────────────────────┐
│                    Quick Recap                               │
│                                                              │
│   IP Address  ->  identifies the DEVICE on a network         │
│   Port        ->  identifies the APPLICATION on that device  │
│   Protocol    ->  decides HOW data is transmitted            │
│                                                              │
│   TCP  ->  reliable, ordered, connection-oriented (slower)   │
│   UDP  ->  unreliable, unordered, connectionless (faster)    │
│                                                              │
│   IP + Port + Protocol  =  complete network endpoint         │
└──────────────────────────────────────────────────────────────┘
```

| Concept | Key Point |
|---------|-----------|
| **IP Address** | Unique device identifier — IPv4 (32-bit) or IPv6 (128-bit) |
| **Loopback** | `127.0.0.1` — refers to local machine |
| **Port** | 0–65535, identifies application on a device |
| **Well-known ports** | 0–1023, reserved (HTTP=80, FTP=21) |
| **Socket Address** | IP + Port combination — uniquely identifies an endpoint |
| **TCP** | Connection-oriented, reliable, 3-way handshake, ordered |
| **UDP** | Connectionless, unreliable, no handshake, fast |
| **`InetAddress`** | Java class representing an IP address |
| **`Socket`/`ServerSocket`** | TCP communication classes |
| **`DatagramSocket`** | UDP communication class |

```
Choose TCP when:  reliability and order matter (web, email, file transfer)
Choose UDP when:  speed matters more than guaranteed delivery (streaming, gaming)
```