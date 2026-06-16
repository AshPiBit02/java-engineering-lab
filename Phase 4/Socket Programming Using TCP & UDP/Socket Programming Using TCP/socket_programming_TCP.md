# TCP Socket Programming in Java

## What is TCP?
**Transmission Control Protocol (TCP)** is a **connection-oriented, reliable, byte-stream** transport layer protocol.  
Before any data is exchanged, a **3-way handshake** must be completed to establish a connection between client and server.

> TCP trades speed for **reliability and ordering** — ideal when every byte must arrive correctly and in sequence.

---

## Key Characteristics of TCP

| Characteristic | Description |
|---|---|
| **Connection-oriented** | Requires a handshake before data transfer — both sides must be ready |
| **Reliable** | Guarantees delivery — lost segments are automatically retransmitted |
| **Ordered** | Data arrives in the exact order it was sent (sequence numbers) |
| **Error-checked** | Checksum on every segment; corrupted data is discarded and re-requested |
| **Flow control** | Sliding window prevents sender from overwhelming a slow receiver |
| **Congestion control** | Slows transmission when network is overloaded (Slow Start, AIMD) |
| **Full-duplex** | Both sides can send and receive simultaneously on the same connection |
| **Byte-stream** | No message boundaries — data is a continuous stream of bytes |
| **Connection teardown** | Graceful close via FIN/ACK exchange — no data loss on close |

---

## TCP Segment Structure

Each TCP segment carries a header with control information:

```
+------------------+------------------+
|   Source Port    | Destination Port |  2 bytes each
+------------------+------------------+
|         Sequence Number             |  4 bytes — position of first byte in stream
+-------------------------------------+
|       Acknowledgment Number         |  4 bytes — next expected byte from other side
+--------+--------+-------------------+
| Offset | Flags  |    Window Size    |  Flags: SYN, ACK, FIN, RST, PSH, URG
+--------+--------+-------------------+
|     Checksum    |  Urgent Pointer   |  2 bytes each
+-----------------+-------------------+
|         Options (if any)            |  Variable
+-------------------------------------+
|              Data                   |  Variable (payload)
+-------------------------------------+
```

- **Sequence Number** — tracks byte position; ensures ordering
- **Acknowledgment Number** — confirms receipt; triggers retransmission if missing
- **Window Size** — advertises receive buffer space; drives flow control
- **Flags** — `SYN` (connect), `ACK` (acknowledge), `FIN` (close), `RST` (reset/abort)
- **Min header size** — 20 bytes; max 60 bytes with options
- **Max segment size (MSS)** — typically ~1460 bytes on Ethernet (MTU 1500 − 40 byte headers)

---

## TCP 3-Way Handshake (Connection Setup)

```
CLIENT                                    SERVER
  |                                          |
  |-------- SYN (seq=x) -----------------> |   Step 1: Client requests connection
  |                                          |
  |<------- SYN + ACK (seq=y, ack=x+1) --- |   Step 2: Server acknowledges + syncs
  |                                          |
  |-------- ACK (ack=y+1) ---------------> |   Step 3: Client confirms
  |                                          |
  |<============ Data Transfer ============>|   Connection Established
  |                                          |
  |-------- FIN -------------------------> |   Step 4: Client initiates close
  |<------- ACK -------------------------- |   Step 5: Server acknowledges
  |<------- FIN -------------------------- |   Step 6: Server closes its side
  |-------- ACK -------------------------> |   Step 7: Client confirms — connection closed
```

> The 4-step close (FIN/ACK/FIN/ACK) allows both sides to close **independently** — one side can stop sending while the other continues (**half-close**).

---

## TCP Socket Communication Flow

```
+----------------------+                        +----------------------+
|        SERVER        |                        |        CLIENT        |
+----------------------+                        +----------------------+
|                      |                        |                      |
| ServerSocket(port)   |                        |                      |
|   bind to port       |                        |                      |
|         |            |                        |                      |
|      accept()        |<---- SYN ------------- | Socket(host, port)   |
|      [BLOCKS]        |---- SYN+ACK ---------> |                      |
|         |            |<---- ACK ------------- |                      |
|         |            |  (3-way handshake)     |                      |
|      Socket          |                        |                      |
|   getInputStream()   |                        |  getOutputStream()   |
|   getOutputStream()  |                        |  getInputStream()    |
|         |            |                        |         |            |
|      read/write      |<======= Data =========>|      read/write      |
|         |            |                        |         |            |
|      close()         |---- FIN/ACK ---------->|      close()         |
+----------------------+                        +----------------------+
```

---

## Core Classes

### `ServerSocket` — Server Side

Used exclusively on the server to **listen** for incoming client connections.

| Constructor | Description |
|---|---|
| `ServerSocket(int port)` | Binds to given port; default backlog = 50 |
| `ServerSocket(int port, int backlog)` | `backlog` = max number of queued (pending) connections |
| `ServerSocket(int port, int backlog, InetAddress addr)` | Binds to specific network interface + port |
| `ServerSocket()` | Unbound — must call `bind()` manually before use |

| Method | Description |
|---|---|
| `Socket accept()` | **Blocks** until a client connects; returns a new `Socket` for that client |
| `void close()` | Closes the server socket; pending `accept()` throws `SocketException` |
| `void bind(SocketAddress endpoint)` | Binds an unbound server socket to a port |
| `int getLocalPort()` | Returns the port the server is listening on |
| `InetAddress getInetAddress()` | Returns the local address the server socket is bound to |
| `boolean isBound()` | Returns true if socket is bound to a port |
| `boolean isClosed()` | Returns true if server socket has been closed |
| `void setSoTimeout(int ms)` | Sets timeout on `accept()` — throws `SocketTimeoutException` after ms |
| `void setReuseAddress(boolean)` | Allows reuse of port in TIME_WAIT state (useful after crash/restart) |
| `void setReceiveBufferSize(int)` | Sets the default receive buffer size for accepted sockets |

> `backlog` — OS queues incoming connection requests while `accept()` is busy.  
> Connections beyond backlog limit are **refused** by the OS automatically.

---

### `Socket` — Client Side (also returned by `accept()` on server)

Represents **one end** of a TCP connection — used on both client and server side.

| Constructor | Description |
|---|---|
| `Socket(String host, int port)` | Connects to server at hostname:port |
| `Socket(InetAddress addr, int port)` | Connects using an `InetAddress` object |
| `Socket(String host, int port, InetAddress localAddr, int localPort)` | Specifies local interface + port to connect from |
| `Socket()` | Unconnected socket — call `connect()` manually |

| Method | Description |
|---|---|
| `InputStream getInputStream()` | Returns stream to **read** data sent by remote side |
| `OutputStream getOutputStream()` | Returns stream to **write** data to remote side |
| `void close()` | Closes socket and both associated streams |
| `void connect(SocketAddress endpoint)` | Connects an unconnected socket |
| `void connect(SocketAddress endpoint, int timeout)` | Connect with timeout in ms |
| `void shutdownInput()` | Closes only the input side — remote side gets EOF |
| `void shutdownOutput()` | Closes only the output side — signals FIN to remote; can still receive |
| `InetAddress getInetAddress()` | Returns the remote host's `InetAddress` |
| `InetAddress getLocalAddress()` | Returns the local interface address |
| `int getPort()` | Returns the remote port number |
| `int getLocalPort()` | Returns the local port assigned by OS |
| `boolean isConnected()` | True if socket has been connected (even if later closed) |
| `boolean isClosed()` | True if socket has been closed |
| `boolean isInputShutdown()` | True if input half has been shut down |
| `boolean isOutputShutdown()` | True if output half has been shut down |
| `void setSoTimeout(int ms)` | Timeout on `read()` — throws `SocketTimeoutException` |
| `void setTcpNoDelay(boolean)` | Disables Nagle's algorithm — sends small packets immediately |
| `void setKeepAlive(boolean)` | Enables TCP keep-alive probes to detect dead connections |
| `void setSoLinger(boolean, int secs)` | If true, `close()` blocks until data sent or timeout expires |
| `void setReuseAddress(boolean)` | Allows reuse of local port in TIME_WAIT state |
| `void setSendBufferSize(int)` | Sets OS-level send buffer size |
| `void setReceiveBufferSize(int)` | Sets OS-level receive buffer size |
| `void setOOBInline(boolean)` | If true, urgent (out-of-band) data is received inline |

---

### `InetAddress`

Represents an IP address (IPv4 or IPv6). Used to identify hosts.

| Method | Description |
|---|---|
| `InetAddress.getByName(String host)` | Resolves hostname or IP string → `InetAddress` |
| `InetAddress.getLocalHost()` | Returns local machine's `InetAddress` |
| `InetAddress.getAllByName(String host)` | Returns all IPs for a hostname (DNS round-robin) |
| `InetAddress.getLoopbackAddress()` | Returns `127.0.0.1` loopback address |
| `String getHostAddress()` | Returns IP as string e.g. `"192.168.1.5"` |
| `String getHostName()` | Returns hostname (may trigger reverse DNS lookup) |
| `boolean isReachable(int timeout)` | Pings the address; returns true if reachable within timeout ms |
| `boolean isLoopbackAddress()` | True if address is `127.x.x.x` |
| `boolean isSiteLocalAddress()` | True if private address (`10.x`, `172.16.x`, `192.168.x`) |

---

### `SocketAddress` / `InetSocketAddress`

Combines an IP address + port into a single object — used with `connect()` and `bind()`.

| Constructor / Method | Description |
|---|---|
| `new InetSocketAddress(String host, int port)` | Creates address+port pair for a remote host |
| `new InetSocketAddress(int port)` | Wildcard address (any interface) + port — for server binding |
| `getAddress()` | Returns the `InetAddress` part |
| `getPort()` | Returns the port number part |
| `isUnresolved()` | True if hostname could not be resolved |

---

## Stream Wrappers (Used with Sockets)

Raw `InputStream`/`OutputStream` work with bytes. Always wrap for practical use:

| Wrapper | Direction | Use When |
|---|---|---|
| `BufferedReader(InputStreamReader(in))` | Read | Reading text line by line (`readLine()`) |
| `PrintWriter(out, true)` | Write | Writing text with auto-flush on `println()` |
| `BufferedInputStream(in)` | Read | Buffered byte reading — reduces system calls |
| `BufferedOutputStream(out)` | Write | Buffered byte writing — reduces system calls |
| `DataInputStream(in)` | Read | Reading Java primitives (`readInt()`, `readDouble()`, `readBoolean()`) |
| `DataOutputStream(out)` | Write | Writing Java primitives (`writeInt()`, `writeDouble()`) |
| `ObjectInputStream(in)` | Read | Deserializing Java objects (class must implement `Serializable`) |
| `ObjectOutputStream(out)` | Write | Serializing Java objects (create `ObjectOutputStream` **first** before `ObjectInputStream`) |

> **Important:** When using `ObjectOutputStream`/`ObjectInputStream`, always create `ObjectOutputStream` first on **both** sides before `ObjectInputStream` — otherwise both sides deadlock waiting for the stream header.

---

## Complete Example — Single Client TCP Echo

### Server
```java
import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started on port 5000. Waiting for client...");

        Socket socket = serverSocket.accept();             // blocks until client connects
        System.out.println("Client connected: " + socket.getInetAddress().getHostAddress()
                           + ":" + socket.getPort());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // auto-flush

        String message;
        while ((message = in.readLine()) != null) {        // null = client closed connection
            System.out.println("Client: " + message);
            out.println("Echo: " + message);
        }

        System.out.println("Client disconnected.");
        socket.close();
        serverSocket.close();
    }
}
```

### Client
```java
import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 5000);
        System.out.println("Connected to server.");

        BufferedReader in      = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter    out     = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String userInput;
        System.out.print("Enter message: ");
        while ((userInput = console.readLine()) != null) {
            out.println(userInput);                        // send to server
            System.out.println("Server replied: " + in.readLine());
            if (userInput.equalsIgnoreCase("exit")) break;
            System.out.print("Enter message: ");
        }

        socket.close();
    }
}
```

---

## Multi-Client TCP Server (Using Threads)

`accept()` handles one client at a time. For concurrent clients, spawn a **new thread per accepted socket**.

```
                       +----------------+
                       |  ServerSocket  |
                       |   accept()     |
                       +-------+--------+
                               |
           +-------------------+-------------------+
           |                   |                   |
    ClientThread-1      ClientThread-2      ClientThread-3
    (Socket-1)          (Socket-2)          (Socket-3)
    reads/writes        reads/writes        reads/writes
    independently       independently       independently
```

```java
import java.net.*;
import java.io.*;

public class MultiClientTCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000, 10); // backlog = 10
        System.out.println("Server running on port 5000...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client: " + clientSocket.getInetAddress());
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    ClientHandler(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out   = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("[" + socket.getPort() + "] " + msg);
                out.println("Echo: " + msg);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
```

---

## Sending Java Objects over TCP (`Serializable`)

```java
// Model class — must implement Serializable
class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    String content;
    int id;
    Message(int id, String content) { this.id = id; this.content = content; }
}

// --- Server sends an object ---
Socket socket = serverSocket.accept();
ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
objOut.writeObject(new Message(1, "Hello from server"));
objOut.flush();

// --- Client receives the object ---
Socket socket = new Socket("localhost", 5000);
ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
Message msg = (Message) objIn.readObject();
System.out.println("ID: " + msg.id + " Content: " + msg.content);
```

---

## Half-Close (Shutdown)

TCP allows closing **one direction** while keeping the other open — useful for signaling end of data while still receiving a response.

```java
// Client: done sending, but still wants to receive server's response
socket.shutdownOutput();                                   // sends FIN to server

// Server: detects EOF on read, processes, sends final reply
String line;
while ((line = in.readLine()) != null) { /* process */ }
out.println("Final response");                            // server still sends

// Client: reads the final response
System.out.println(in.readLine());
socket.close();
```

---

## TCP with Timeout

`accept()` and `read()` block indefinitely by default. Set timeouts to prevent deadlock:

```java
// Server accept timeout
ServerSocket serverSocket = new ServerSocket(5000);
serverSocket.setSoTimeout(5000);                           // 5 seconds

try {
    Socket socket = serverSocket.accept();
} catch (SocketTimeoutException e) {
    System.out.println("No client connected within 5 seconds.");
}

// Read timeout on an accepted socket
socket.setSoTimeout(3000);                                 // 3 seconds
try {
    String line = in.readLine();
} catch (SocketTimeoutException e) {
    System.out.println("Client took too long to send data.");
}
```

---

## Try-With-Resources (Best Practice)

Automatically closes all resources even if an exception is thrown — no need for `finally` block.

```java
try (
    ServerSocket serverSocket = new ServerSocket(5000);
    Socket socket             = serverSocket.accept();
    BufferedReader in         = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out           = new PrintWriter(socket.getOutputStream(), true)
) {
    String msg;
    while ((msg = in.readLine()) != null) {
        out.println("Echo: " + msg);
    }
}  // all resources auto-closed in reverse order
```

---

## Common Exceptions

| Exception | Cause |
|---|---|
| `ConnectException` | Server not running / wrong host or port |
| `BindException` | Port already in use when starting `ServerSocket` |
| `SocketTimeoutException` | `accept()` or `read()` exceeded the timeout set by `setSoTimeout()` |
| `SocketException` | Connection reset by peer / broken pipe / socket closed unexpectedly |
| `UnknownHostException` | Hostname could not be resolved to an IP address |
| `EOFException` | Stream ended unexpectedly — client closed connection mid-read |
| `StreamCorruptedException` | Mismatch in `ObjectInputStream` header (wrong stream or order) |
| `ClassNotFoundException` | Object received via `ObjectInputStream` but class not found locally |

---

## Important Notes

- `accept()` and `readLine()` are **blocking** — thread halts until a connection arrives or data comes in; always use timeouts in production.
- `PrintWriter(out, true)` — the `true` flag enables **auto-flush** on `println()`; without it, data stays in the buffer and the other side never receives it.
- `readLine()` returns `null` when the remote side closes the connection — this is your **loop exit condition**.
- Always close **streams before sockets** (or use try-with-resources) to ensure all buffered data is flushed before the connection drops.
- `setTcpNoDelay(true)` disables **Nagle's algorithm** which batches small writes — enable this for low-latency applications like games or interactive tools.
- `setReuseAddress(true)` on `ServerSocket` lets you restart a crashed server without waiting for the OS `TIME_WAIT` period (default ~60–120 seconds) to expire.
- `shutdownOutput()` sends a **FIN** to signal end of data without closing the socket — the other side sees `null` on `readLine()` but can still send back a response.
- For **multiple clients**, always use threads (or `ExecutorService`) — a single-threaded server blocks on one client until it disconnects.
- Port numbers must match exactly between client and server. Ports `0–1023` are reserved — use `1024+` for custom applications.