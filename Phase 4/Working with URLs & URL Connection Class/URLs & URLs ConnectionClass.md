# Working with URLs and URLConnection Class in Java

## What is a URL?
A **Uniform Resource Locator (URL)** is a reference (address) to a resource on the internet or a local network.  
It specifies **where** the resource is and **how** to retrieve it (which protocol to use).

> In Java, the `java.net.URL` class encapsulates a URL and provides methods to parse its components and open connections to the resource it points to.

---

## URL Structure

```
protocol://userInfo@host:port/path?query#fragment
    |          |        |     |    |      |        |
  http      user:pwd  host  8080  /page  ?id=1   #section
```

### Full Example Breakdown
```
https://user:pass@www.example.com:8080/docs/index.html?lang=en&page=2#intro
  |         |              |        |         |               |          |
protocol  userInfo        host     port      path           query    fragment
```

| Component | Description | Example |
|---|---|---|
| **Protocol** | How to access the resource | `http`, `https`, `ftp`, `file` |
| **UserInfo** | Optional credentials | `user:password` |
| **Host** | Domain name or IP address | `www.example.com`, `192.168.1.1` |
| **Port** | Port number (optional) | `8080`; defaults: http=80, https=443, ftp=21 |
| **Path** | Location of resource on server | `/docs/index.html` |
| **Query** | Key-value parameters after `?` | `lang=en&page=2` |
| **Fragment** | Anchor within the page (client-side only) | `#intro` |

> **Fragment** (`#`) is never sent to the server — it's handled entirely by the browser/client.

---

## Java URL & URLConnection Class Hierarchy

```
java.lang.Object
       |
  java.net.URL                   <- Parses and represents a URL
       |
       | .openConnection()
       v
  java.net.URLConnection         <- Abstract base class for all connections
       |
       +-- HttpURLConnection     <- HTTP/HTTPS specific (GET, POST, headers, response codes)
       |
       +-- JarURLConnection      <- For accessing JAR file entries via URL
       |
       +-- FileURLConnection     <- For file:// protocol (internal use)
```

---

## Working Sequence — URL to Response

```
+------------------+
|   Create URL     |   new URL("https://example.com/data")
+--------+---------+
         |
         v
+------------------+
| Parse Components |   url.getHost(), url.getPath(), url.getPort() ...
+--------+---------+
         |
         v
+------------------+
| openConnection() |   URLConnection conn = url.openConnection()
+--------+---------+
         |
         v
+------------------+
| Set Properties   |   setRequestMethod(), setRequestProperty(), setDoOutput() ...
| (before connect) |
+--------+---------+
         |
         v
+------------------+
|   connect()      |   Establishes TCP connection + sends HTTP request
+--------+---------+
         |
         v
+------------------+
| Read Response    |   getInputStream(), getResponseCode(), getHeaderField() ...
+--------+---------+
         |
         v
+------------------+
| Close Stream     |   inputStream.close() -> disconnect()
+------------------+
```

---

## HTTP Request-Response Flow

```
CLIENT (Java)                              SERVER
     |                                        |
 new URL(urlString)                           |
 url.openConnection()                         |
 conn.setRequestMethod("GET")                 |
 conn.connect()          ---- HTTP GET -----> |
                                              |  Process request
                         <--- HTTP 200 ------ |  Send response headers + body
 conn.getResponseCode()                       |
 conn.getInputStream()                        |
 read response body                           |
 stream.close()                               |
 conn.disconnect()       ---- TCP FIN ------> |
                                              |
```

---

## Core Classes

### `URL` Class (`java.net.URL`)

Parses a URL string into its components and provides a gateway to open connections.

| Constructor | Description |
|---|---|
| `URL(String spec)` | Parses a complete URL string e.g. `"https://example.com/path"` |
| `URL(String protocol, String host, int port, String file)` | Builds URL from individual components |
| `URL(String protocol, String host, String file)` | Builds URL with default port for the protocol |
| `URL(URL context, String spec)` | Resolves a relative URL against a base URL |

| Method | Return Type | Description |
|---|---|---|
| `getProtocol()` | `String` | Returns protocol e.g. `"https"` |
| `getHost()` | `String` | Returns hostname e.g. `"www.example.com"` |
| `getPort()` | `int` | Returns explicit port; `-1` if not specified |
| `getDefaultPort()` | `int` | Returns default port for the protocol (80 for http) |
| `getPath()` | `String` | Returns path component e.g. `"/docs/index.html"` |
| `getFile()` | `String` | Returns path + query string combined |
| `getQuery()` | `String` | Returns query string e.g. `"lang=en&page=2"` |
| `getRef()` | `String` | Returns fragment/anchor e.g. `"intro"` |
| `getUserInfo()` | `String` | Returns `"user:password"` if present |
| `toExternalForm()` | `String` | Returns full URL as a string |
| `toURI()` | `URI` | Converts to a `URI` object |
| `openStream()` | `InputStream` | Opens connection and returns input stream directly (shortcut) |
| `openConnection()` | `URLConnection` | Opens a connection — returns `URLConnection` for full control |
| `openConnection(Proxy proxy)` | `URLConnection` | Opens connection through a proxy server |

---

### `URLConnection` Class (`java.net.URLConnection`)

Abstract base class representing an active connection to a URL. Obtained via `url.openConnection()`.

**Must set all properties BEFORE calling `connect()` or reading data.**

| Method | Description |
|---|---|
| `connect()` | Explicitly opens the connection (implicitly called on first read/write) |
| `getInputStream()` | Returns stream to read the response body |
| `getOutputStream()` | Returns stream to send data (requires `setDoOutput(true)`) |
| `getContentType()` | Returns MIME type e.g. `"text/html; charset=UTF-8"` |
| `getContentLength()` | Returns content size in bytes; `-1` if unknown |
| `getContentLengthLong()` | Returns content size as `long` (for large files > 2GB) |
| `getDate()` | Returns response date (milliseconds since epoch) |
| `getExpiration()` | Returns expiration time of the resource |
| `getLastModified()` | Returns last-modified timestamp of the resource |
| `getHeaderField(String name)` | Returns value of a specific response header by name |
| `getHeaderField(int n)` | Returns nth header value |
| `getHeaderFieldKey(int n)` | Returns nth header name |
| `getHeaderFields()` | Returns `Map<String, List<String>>` of all response headers |
| `setRequestProperty(String key, String value)` | Sets a request header (must be before `connect()`) |
| `addRequestProperty(String key, String value)` | Adds (not replaces) a value to a request header |
| `getRequestProperty(String key)` | Gets a previously set request header value |
| `setDoInput(boolean)` | If true (default), enables reading response via `getInputStream()` |
| `setDoOutput(boolean)` | If true, enables sending a body via `getOutputStream()` (needed for POST) |
| `setUseCaches(boolean)` | If false, forces a fresh request bypassing any cache |
| `setConnectTimeout(int ms)` | Max time (ms) to establish connection before `SocketTimeoutException` |
| `setReadTimeout(int ms)` | Max time (ms) to wait for data during a read operation |
| `getURL()` | Returns the URL this connection points to |

---

### `HttpURLConnection` Class (`java.net.HttpURLConnection`)

Extends `URLConnection` with HTTP-specific features. Cast from `URLConnection` when protocol is `http`/`https`.

```java
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
```

| Method | Description |
|---|---|
| `setRequestMethod(String)` | Sets HTTP method: `"GET"`, `"POST"`, `"PUT"`, `"DELETE"`, `"HEAD"` |
| `getRequestMethod()` | Returns the currently set request method |
| `getResponseCode()` | Returns HTTP status code e.g. `200`, `404`, `500` |
| `getResponseMessage()` | Returns status message e.g. `"OK"`, `"Not Found"` |
| `getErrorStream()` | Returns error response body when `getResponseCode() >= 400` |
| `disconnect()` | Closes the connection and releases all resources |
| `setFollowRedirects(boolean)` | Static — enables/disables redirect following for ALL instances |
| `setInstanceFollowRedirects(boolean)` | Enables/disables redirect following for this instance only |
| `setChunkedStreamingMode(int chunkSize)` | Streams POST body in chunks (when content length is unknown) |
| `setFixedLengthStreamingMode(long length)` | Streams POST body of known length without buffering entire body |

#### Common HTTP Status Codes

| Code | Meaning |
|---|---|
| `200` | OK — request succeeded |
| `201` | Created — resource created (POST response) |
| `204` | No Content — success but no response body |
| `301` / `302` | Redirect — resource moved permanently / temporarily |
| `400` | Bad Request — malformed request syntax |
| `401` | Unauthorized — authentication required |
| `403` | Forbidden — server refuses action, no permission |
| `404` | Not Found — resource doesn't exist |
| `500` | Internal Server Error — server-side failure |
| `503` | Service Unavailable — server overloaded or down |

---

## Complete Examples

### 1. Parse URL Components
```java
import java.net.*;

public class URLParser {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://user:pass@www.example.com:8080/docs/page.html?lang=en#intro");

        System.out.println("Protocol : " + url.getProtocol());   // https
        System.out.println("UserInfo : " + url.getUserInfo());   // user:pass
        System.out.println("Host     : " + url.getHost());       // www.example.com
        System.out.println("Port     : " + url.getPort());       // 8080
        System.out.println("Path     : " + url.getPath());       // /docs/page.html
        System.out.println("Query    : " + url.getQuery());      // lang=en
        System.out.println("Fragment : " + url.getRef());        // intro
        System.out.println("File     : " + url.getFile());       // /docs/page.html?lang=en
    }
}
```

---

### 2. Read Web Page Content (`openStream()` shortcut)
```java
import java.net.*;
import java.io.*;

public class URLRead {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://example.com");

        // openStream() is a shortcut for openConnection().getInputStream()
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}
```

---

### 3. HTTP GET with `HttpURLConnection`
```java
import java.net.*;
import java.io.*;

public class HTTPGet {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set request properties BEFORE connect()
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(5000);                          // 5s to connect
        conn.setReadTimeout(5000);                             // 5s to read

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode); // 200

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            System.out.println("Response: " + response.toString());
        } else {
            // read error body for codes >= 400
            BufferedReader err = new BufferedReader(
                new InputStreamReader(conn.getErrorStream()));
            String line;
            while ((line = err.readLine()) != null) System.out.println(line);
            err.close();
        }

        conn.disconnect();
    }
}
```

---

### 4. HTTP POST with `HttpURLConnection`
```java
import java.net.*;
import java.io.*;

public class HTTPPost {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);                                // enable sending body

        String jsonBody = "{\"title\":\"Test\",\"body\":\"Hello\",\"userId\":1}";
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode); // 201 Created

        BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) System.out.println(line);
        in.close();

        conn.disconnect();
    }
}
```

---

### 5. Reading Response Headers
```java
import java.net.*;
import java.util.*;

public class ReadHeaders {
    public static void main(String[] args) throws Exception {
        HttpURLConnection conn = (HttpURLConnection)
            new URL("https://example.com").openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        System.out.println("Status      : " + conn.getResponseCode());
        System.out.println("Content-Type: " + conn.getContentType());
        System.out.println("Content-Len : " + conn.getContentLength());
        System.out.println("Last-Modified: " + conn.getLastModified());

        // Print ALL response headers
        Map<String, List<String>> headers = conn.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        conn.disconnect();
    }
}
```

---

### 6. Connecting Through a Proxy
```java
import java.net.*;

public class ProxyConnection {
    public static void main(String[] args) throws Exception {
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                       new InetSocketAddress("proxy.example.com", 8080));

        URL url = new URL("https://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
        conn.setRequestMethod("GET");

        System.out.println("Response Code: " + conn.getResponseCode());
        conn.disconnect();
    }
}
```

---

## `URL` vs `URI` vs `URLConnection`

| Class | Purpose |
|---|---|
| `URL` | Represents a URL; can open a connection to fetch the resource |
| `URI` | Represents any Uniform Resource Identifier (broader than URL); no connection support |
| `URLConnection` | Represents an active connection to a URL resource; used to read/write data |

> `URI` is more standards-compliant. Convert: `url.toURI()` or `uri.toURL()`.

---

## Common Exceptions

| Exception | Cause |
|---|---|
| `MalformedURLException` | URL string is syntactically invalid (subclass of `IOException`) |
| `SocketTimeoutException` | Connect or read exceeded the timeout set via `setConnectTimeout` / `setReadTimeout` |
| `ConnectException` | Server refused connection or is unreachable |
| `UnknownHostException` | Hostname could not be resolved to an IP address |
| `IOException` | General I/O failure during read/write |
| `ProtocolException` | Invalid HTTP method or protocol-level violation |
| `SSLHandshakeException` | HTTPS certificate validation failed (self-signed or expired cert) |
| `IllegalStateException` | Request property set after `connect()` was already called |

---

## Important Notes

- All request properties (`setRequestProperty`, `setRequestMethod`, `setDoOutput`) **must be set before** calling `connect()` or reading/writing streams — calling them after throws `IllegalStateException`.
- `url.openStream()` is a shortcut for `url.openConnection().getInputStream()` — use it for simple GET reads; use `HttpURLConnection` when you need headers, POST body, or status codes.
- `getInputStream()` throws `IOException` if the response code is `>= 400`; always check `getResponseCode()` first and use `getErrorStream()` to read error bodies.
- `disconnect()` hints the JVM to close the underlying TCP connection — HTTP/1.1 keep-alive may otherwise reuse it. Always call it when done.
- `setDoOutput(true)` implicitly **changes the method to POST** if `setRequestMethod()` has not been called — always set the method explicitly.
- `getContentLength()` returns `-1` for chunked transfer encoding or when the server omits the `Content-Length` header — use `getContentLengthLong()` for large files.
- `getPort()` returns `-1` if no port is in the URL — use `getDefaultPort()` to get the protocol's standard port.
- For HTTPS, Java validates SSL certs against the JVM's default `TrustStore` — self-signed certs throw `SSLHandshakeException` unless explicitly added to the trust store.
- `setFollowRedirects(false)` is **static** and affects all `HttpURLConnection` instances in the JVM — prefer `setInstanceFollowRedirects(false)` for per-connection control.
- `getHeaderFields()` returns `null` as the key for the HTTP status line (e.g. `HTTP/1.1 200 OK`) — account for this when iterating all headers.