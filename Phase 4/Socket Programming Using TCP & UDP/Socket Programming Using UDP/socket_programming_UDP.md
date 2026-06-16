# UDP Socket Programming in Java

## What is UDP?
**User Datagram Protocol (UDP)** is a **connectionless, unreliable, message-oriented** transport layer protocol.  
Unlike TCP, UDP sends independent packets called **datagrams** with no prior handshake, no acknowledgment, and no guarantee of delivery or ordering.

> UDP trades reliability for **speed and simplicity** — ideal when low latency matters more than guaranteed delivery.

---

## Key Characteristics of UDP

| Characteristic | Description |
|---|---|
| **Connectionless** | No handshake — sender just fires packets; receiver just listens |
| **Unreliable** | No guarantee of delivery; lost packets are NOT retransmitted |
| **Unordered** | Packets may arrive out of order or not at all |
| **Message-oriented** | Each send = one discrete datagram (unlike TCP's byte stream) |
| **No flow control** | Sender can overwhelm receiver — no window management |
| **No congestion control** | Does not slow down based on network load |
| **Low overhead** | Header is only 8 bytes vs TCP's 20+ bytes |
| **Full-duplex** | Both sides can send and receive using the same socket |
| **Broadcast/Multicast support** | Can send one packet to multiple recipients |

---

## UDP vs TCP — Side by Side

```
TCP                                  UDP
 |                                    |
 |-- SYN --------> Server            |-- Datagram ---> Server (no setup)
 |<- SYN+ACK ------                  |
 |-- ACK ------->                     No acknowledgment
 |                                    |
 |<== Ordered Stream ===============>|<== Independent Datagrams ===========>|
 |                                    |
 |-- FIN ------->  (graceful close)  |  close() (no negotiation)
```

| Feature | TCP | UDP |
|---|---|---|
| Connection setup | 3-way handshake | None |
| Delivery guarantee | Yes | No |
| Ordering | Yes | No |
| Header size | 20–60 bytes | 8 bytes |
| Speed | Slower | Faster |
| Use case | HTTP, FTP, SSH, Email | DNS, VoIP, Video, Gaming |

---

## UDP Datagram Structure

Each UDP packet (datagram) carries its own header:

```
+------------------+------------------+
|   Source Port    | Destination Port |  2 bytes each
+------------------+------------------+
|     Length       |    Checksum      |  2 bytes each
+------------------+------------------+
|                                     |
|            Data (Payload)           |  Variable length
|                                     |
+-------------------------------------+
```

- **Source Port** — sender's port (optional; 0 if not used)
- **Destination Port** — receiver's port (mandatory)
- **Length** — total datagram length (header + data), minimum 8 bytes
- **Checksum** — error detection (optional in IPv4, mandatory in IPv6)
- **Max datagram size** — 65,507 bytes (65,535 − 8 byte UDP header − 20 byte IP header)
- Practical safe size — keep payload under **512 bytes** to avoid IP fragmentation

---

## UDP Communication Flow

```
+-------------------+                        +-------------------+
|      SENDER       |                        |     RECEIVER      |
+-------------------+                        +-------------------+
|                   |                        |                   |
| DatagramSocket()  |                        | DatagramSocket(p) |
|                   |                        |  receive() BLOCKS |
|                   |                        |        |          |
| InetAddress.      |                        |        |          |
|  getByName(host)  |                        |        |          |
|                   |                        |        |          |
| DatagramPacket(   |                        |        |          |
|  data, len,       |------- Datagram ------>|        |          |
|  addr, port)      |    (no handshake)      |        v          |
|                   |                        |  packet.getData() |
| socket.send(pkt)  |                        |  packet.getLength |
|                   |                        |  packet.getAddress|
| socket.close()    |                        |  socket.close()   |
+-------------------+                        +-------------------+
```

> Key difference from TCP: **no `accept()`**, no persistent connection.  
> Every datagram is **independent** — receiver doesn't know if more are coming.

---

## Core Classes

### `DatagramSocket`

The primary class for both **sending and receiving** UDP datagrams.

| Constructor | Description |
|---|---|
| `DatagramSocket()` | Creates socket bound to any available port (used by sender) |
| `DatagramSocket(int port)` | Creates socket bound to specific port (used by receiver) |
| `DatagramSocket(int port, InetAddress addr)` | Binds to specific port AND network interface |

| Method | Description |
|---|---|
| `send(DatagramPacket p)` | Sends a datagram packet |
| `receive(DatagramPacket p)` | **Blocks** until a datagram is received; fills the packet |
| `close()` | Closes the socket and releases the port |
| `setSoTimeout(int ms)` | Sets timeout on `receive()` — throws `SocketTimeoutException` |
| `getLocalPort()` | Returns the port this socket is bound to |
| `getLocalAddress()` | Returns the local address the socket is bound to |
| `connect(InetAddress, int port)` | Restricts socket to only send/receive from one remote address |
| `disconnect()` | Removes restriction set by `connect()` |
| `isClosed()` | Returns true if socket is closed |
| `setBroadcast(boolean)` | Enables sending broadcast datagrams |
| `setReceiveBufferSize(int)` | Sets OS-level receive buffer size |
| `setSendBufferSize(int)` | Sets OS-level send buffer size |

> `connect()` in UDP does **not** establish a real connection — it just filters packets  
> to/from a specific remote address at the OS level (improves performance + security).

---

### `DatagramPacket`

A **container** that wraps the byte array being sent or received along with addressing info.

| Constructor | Used For | Description |
|---|---|---|
| `DatagramPacket(byte[] buf, int length)` | **Receiving** | Empty buffer for incoming data |
| `DatagramPacket(byte[] buf, int length, InetAddress addr, int port)` | **Sending** | Data + destination address + port |
| `DatagramPacket(byte[] buf, int offset, int length, InetAddress addr, int port)` | **Sending** | With offset into buffer |

| Method | Description |
|---|---|
| `getData()` | Returns the raw byte array (entire buffer, not just received data) |
| `getLength()` | Returns the **actual** number of bytes received (use this, not buffer size) |
| `getOffset()` | Returns offset into the data buffer |
| `getAddress()` | Returns sender's `InetAddress` (useful on receiver to send reply) |
| `getPort()` | Returns sender's port (useful on receiver to send reply) |
| `setData(byte[])` | Replaces the data buffer |
| `setAddress(InetAddress)` | Sets destination address |
| `setPort(int)` | Sets destination port |
| `setLength(int)` | Adjusts length of data in packet |

> **Critical:** `getData()` returns the **full buffer**, not just the received bytes.  
> Always use `new String(packet.getData(), 0, packet.getLength())` to get actual message.

---

### `InetAddress`

Represents an IP address (IPv4 or IPv6). Used to specify destination in `DatagramPacket`.

| Method | Description |
|---|---|
| `InetAddress.getByName(String host)` | Resolves hostname/IP string → `InetAddress` |
| `InetAddress.getLocalHost()` | Returns local machine's address |
| `InetAddress.getAllByName(String host)` | Returns all IPs for a hostname (DNS) |
| `getHostAddress()` | Returns IP as string e.g. `"192.168.1.10"` |
| `getHostName()` | Returns hostname |
| `isReachable(int timeout)` | Pings the address — returns true if reachable |
| `isLoopbackAddress()` | Returns true if address is `127.x.x.x` |
| `isMulticastAddress()` | Returns true if address is in multicast range (`224.x.x.x – 239.x.x.x`) |

---

## Complete Example — UDP Sender & Receiver

### Receiver (run first — it must be ready before sender fires)
```java
import java.net.*;

public class UDPReceiver {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(6000);   // bind to port 6000
        byte[] buffer = new byte[1024];

        System.out.println("Receiver ready on port 6000...");

        // receive loop
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);                         // BLOCKS here

            // extract only the actual received bytes
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("From " + packet.getAddress() + ":" + packet.getPort()
                               + " => " + message);

            if (message.equals("exit")) break;             // stop condition
        }

        socket.close();
    }
}
```

### Sender
```java
import java.net.*;

public class UDPSender {
    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();      // OS assigns a random port
        InetAddress address = InetAddress.getByName("localhost");

        String[] messages = {"Hello", "UDP", "Socket", "exit"};

        for (String msg : messages) {
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, 6000);
            socket.send(packet);
            System.out.println("Sent: " + msg);
            Thread.sleep(500);                             // small delay between packets
        }

        socket.close();
    }
}
```

---

## Two-Way UDP Communication (Request-Reply)

UDP can do request-reply by including **sender's address and port** in the received packet,  
then using those to send the reply back.

```java
// --- Server (Receiver + Responder) ---
DatagramSocket socket = new DatagramSocket(6000);
byte[] buffer = new byte[1024];

DatagramPacket request = new DatagramPacket(buffer, buffer.length);
socket.receive(request);                                   // receive request

String received = new String(request.getData(), 0, request.getLength());
System.out.println("Request: " + received);

// reply back to sender using their address and port from the packet
String replyMsg   = "ACK: " + received;
byte[] replyData  = replyMsg.getBytes();
DatagramPacket reply = new DatagramPacket(
    replyData, replyData.length,
    request.getAddress(),                                  // sender's IP
    request.getPort()                                      // sender's port
);
socket.send(reply);
socket.close();
```

```java
// --- Client (Sender + Awaits Reply) ---
DatagramSocket socket = new DatagramSocket();              // random local port
InetAddress serverAddr = InetAddress.getByName("localhost");

byte[] data = "Hello Server".getBytes();
DatagramPacket request = new DatagramPacket(data, data.length, serverAddr, 6000);
socket.send(request);

// wait for reply
byte[] replyBuf = new byte[1024];
DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length);
socket.receive(reply);

System.out.println("Server replied: " + new String(reply.getData(), 0, reply.getLength()));
socket.close();
```

---

## UDP Broadcast

Send one packet to **all hosts** on a subnet using broadcast address.

```java
DatagramSocket socket = new DatagramSocket();
socket.setBroadcast(true);                                 // mandatory for broadcast

byte[] data = "Broadcast message".getBytes();
InetAddress broadcastAddr = InetAddress.getByName("255.255.255.255");

DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddr, 6000);
socket.send(packet);
socket.close();
```

> Broadcast is limited to the **local network** — routers do not forward broadcast packets.

---

## UDP Multicast (`MulticastSocket`)

Send one packet to a **group of subscribed hosts** across networks.  
Multicast addresses: `224.0.0.0` – `239.255.255.255`

```java
// Sender
MulticastSocket socket = new MulticastSocket();
InetAddress group = InetAddress.getByName("230.0.0.1");   // multicast group address
byte[] data = "Multicast Hello".getBytes();
DatagramPacket packet = new DatagramPacket(data, data.length, group, 6000);
socket.send(packet);
socket.close();

// Receiver
MulticastSocket socket = new MulticastSocket(6000);
InetAddress group = InetAddress.getByName("230.0.0.1");
socket.joinGroup(group);                                   // subscribe to group

byte[] buf = new byte[1024];
DatagramPacket packet = new DatagramPacket(buf, buf.length);
socket.receive(packet);

System.out.println(new String(packet.getData(), 0, packet.getLength()));
socket.leaveGroup(group);
socket.close();
```

---

## UDP with Timeout

Since `receive()` blocks forever by default, always set a timeout in production:

```java
DatagramSocket socket = new DatagramSocket(6000);
socket.setSoTimeout(3000);                                 // 3 seconds

try {
    byte[] buf = new byte[1024];
    DatagramPacket packet = new DatagramPacket(buf, buf.length);
    socket.receive(packet);                                // throws if no data in 3s
} catch (SocketTimeoutException e) {
    System.out.println("No data received within timeout.");
} finally {
    socket.close();
}
```

---

## Common Exceptions

| Exception | Cause |
|---|---|
| `SocketException` | Error creating/accessing socket; port already in use |
| `SocketTimeoutException` | `receive()` timed out (after `setSoTimeout()`) |
| `UnknownHostException` | Hostname could not be resolved to IP |
| `PortUnreachableException` | Destination port is not listening (ICMP port unreachable) |
| `SecurityException` | Security manager blocks the socket operation |

---

## Important Notes

- **Receiver must start first** — UDP has no retry; if receiver isn't ready, the datagram is lost permanently.
- `getData()` returns the **entire buffer** — always slice with `getLength()` to get the actual message.
- UDP has **no built-in message fragmentation** — if your payload exceeds MTU (~1500 bytes on Ethernet), the IP layer fragments it; any fragment loss = entire datagram lost.
- For reliable UDP (with custom ACK/retry), you'd implement your own logic — this is how protocols like **QUIC** and **RTP** work on top of UDP.
- `DatagramSocket.connect()` in UDP is **not** a real connection — it just tells the OS to filter packets; the socket still sends/receives datagrams.
- Broadcast requires `setBroadcast(true)` explicitly — it is **off by default**.
- Multicast requires joining the group with `joinGroup()` and leaving with `leaveGroup()` — always leave to free OS resources.
- There is **no "connection closed"** signal in UDP — if the other side stops, you only know via timeout.