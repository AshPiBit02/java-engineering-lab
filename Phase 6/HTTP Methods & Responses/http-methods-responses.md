# HTTP Methods and Responses


---

## Table of Contents
1. [HTTP Request Structure](#http-request-structure)
2. [HTTP Methods](#http-methods)
3. [HTTP Response Structure](#http-response-structure)
4. [Status Codes](#status-codes)
5. [Headers in HTTP](#headers-in-http)
6. [Working Sequence](#working-sequence)
7. [Code Examples](#code-examples)
8. [Common Exceptions](#common-exceptions)
9. [Important Notes](#important-notes)

---

## HTTP Request Structure

An HTTP request consists of:

```
┌─────────────────────────────────────────┐
│  REQUEST LINE                           │
│  METHOD URI HTTP-VERSION                │
│  GET /index.html HTTP/1.1               │
├─────────────────────────────────────────┤
│  REQUEST HEADERS                        │
│  Header-Name: Header-Value              │
│  Host: www.example.com                  │
│  Content-Type: application/json         │
├─────────────────────────────────────────┤
│  BLANK LINE                             │
├─────────────────────────────────────────┤
│  REQUEST BODY (Optional)                │
│  Form data, JSON, XML, etc.             │
│  (only for POST, PUT, PATCH)            │
└─────────────────────────────────────────┘
```

---

## HTTP Methods

| Method | Purpose | Body | Idempotent | Cacheable | Safe |
|--------|---------|------|-----------|-----------|------|
| **GET** | Retrieve resource | No | Yes | Yes | Yes |
| **POST** | Submit data, create resource | Yes | No | Conditional | No |
| **PUT** | Replace entire resource | Yes | Yes | No | No |
| **DELETE** | Remove resource | Optional | Yes | No | No |
| **HEAD** | Like GET, no response body | No | Yes | Yes | Yes |
| **OPTIONS** | Describe communication options | Optional | Yes | No | Yes |
| **PATCH** | Partial resource update | Yes | No | No | No |

### Key Characteristics

- **Idempotent:** Multiple identical requests produce same result
- **Safe:** Request doesn't modify resource on server
- **Cacheable:** Response can be cached

---

## HTTP Response Structure

```
┌─────────────────────────────────────────┐
│  STATUS LINE                            │
│  HTTP-VERSION STATUS-CODE REASON        │
│  HTTP/1.1 200 OK                        │
├─────────────────────────────────────────┤
│  RESPONSE HEADERS                       │
│  Header-Name: Header-Value              │
│  Content-Type: text/html                │
│  Content-Length: 1234                   │
├─────────────────────────────────────────┤
│  BLANK LINE                             │
├─────────────────────────────────────────┤
│  RESPONSE BODY                          │
│  HTML, JSON, XML, binary data, etc.     │
└─────────────────────────────────────────┘
```

---

## Status Codes

### 1xx – Informational (Request received, processing)
| Code | Meaning |
|------|---------|
| 100 | Continue |
| 101 | Switching Protocols |

### 2xx – Success
| Code | Meaning |
|------|---------|
| 200 | OK (Request successful) |
| 201 | Created (Resource created) |
| 202 | Accepted (Processing) |
| 204 | No Content (Success, no body) |

### 3xx – Redirection
| Code | Meaning |
|------|---------|
| 301 | Moved Permanently |
| 302 | Found (Temporary redirect) |
| 304 | Not Modified (Use cache) |
| 307 | Temporary Redirect |

### 4xx – Client Error
| Code | Meaning |
|------|---------|
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 405 | Method Not Allowed |
| 409 | Conflict |

### 5xx – Server Error
| Code | Meaning |
|------|---------|
| 500 | Internal Server Error |
| 502 | Bad Gateway |
| 503 | Service Unavailable |
| 504 | Gateway Timeout |

---

## Headers in HTTP

### Common Request Headers

| Header | Purpose | Example |
|--------|---------|---------|
| **Host** | Target domain | `Host: www.example.com` |
| **User-Agent** | Client info | `User-Agent: Mozilla/5.0` |
| **Content-Type** | Body format | `Content-Type: application/json` |
| **Content-Length** | Body size in bytes | `Content-Length: 256` |
| **Accept** | Preferred response format | `Accept: application/json` |
| **Accept-Encoding** | Compression support | `Accept-Encoding: gzip, deflate` |
| **Cookie** | Session/tracking data | `Cookie: sessionId=abc123` |
| **Authorization** | Authentication | `Authorization: Bearer token123` |

### Common Response Headers

| Header | Purpose | Example |
|--------|---------|---------|
| **Content-Type** | Body format | `Content-Type: text/html; charset=UTF-8` |
| **Content-Length** | Body size | `Content-Length: 512` |
| **Set-Cookie** | Store cookie on client | `Set-Cookie: sessionId=abc; Path=/` |
| **Cache-Control** | Caching rules | `Cache-Control: no-cache, max-age=3600` |
| **Location** | Redirect target | `Location: /new-page.html` |
| **Server** | Server software | `Server: Apache/2.4` |
| **Date** | Response date/time | `Date: Mon, 23 May 2024 22:38:34 GMT` |

---

## Working Sequence

```
CLIENT                          SERVER
  │                               │
  │  (1) Send HTTP Request        │
  ├──────────────────────────────>│
  │                               │
  │                      (2) Parse Request
  │                      (3) Process Logic
  │                      (4) Generate Response
  │                               │
  │  (5) Receive HTTP Response    │
  │<──────────────────────────────┤
  │                               │
  (6) Parse Response & Render
  │
  │
```

### Step-by-Step Flow

1. **Client sends request** → Browser/client constructs HTTP request with method, URI, headers, body
2. **Server receives request** → Servlet container receives and parses request
3. **Server processes** → Appropriate servlet method (doGet/doPost) executes business logic
4. **Server generates response** → Servlet sets status code, headers, and body
5. **Client receives response** → Browser receives complete response
6. **Client renders** → Browser parses headers and renders body content

---

## Code Examples

### Example 1: Handling GET Request

```java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class GetMethodServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set content type before writing
        response.setContentType("text/html; charset=UTF-8");
        
        // Get parameter from query string (e.g., ?name=John)
        String name = request.getParameter("name");
        if (name == null) {
            name = "Guest";
        }
        
        // Get writer to send response body
        PrintWriter out = response.getWriter();
        
        // Write HTML response
        out.println("<html><body>");
        out.println("<h1>Welcome, " + name + "!</h1>");
        out.println("</body></html>");
        
        // Auto-closed by servlet container
        out.close();
    }
}
```

**Usage:** `http://localhost:8080/app/get?name=John`

### Example 2: Handling POST Request

```java
public class PostMethodServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json; charset=UTF-8");
        
        // Read form parameters from request body
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        
        // Simple validation
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            
            // Set error status code
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\":\"Missing fields\"}");
            return;
        }
        
        // Success: set status to 201 Created
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        // Send JSON response
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("  \"message\": \"User registered\",");
        out.println("  \"username\": \"" + username + "\",");
        out.println("  \"email\": \"" + email + "\"");
        out.println("}");
        out.close();
    }
}
```

### Example 3: Working with Response Headers

```java
public class HeaderServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Set response headers
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("X-Custom-Header", "MyValue");
        response.setHeader("Cache-Control", "no-cache, max-age=3600");
        
        // Set cookies
        Cookie sessionCookie = new Cookie("sessionId", "abc123xyz");
        sessionCookie.setMaxAge(3600); // 1 hour
        sessionCookie.setPath("/");    // Available to entire app
        response.addCookie(sessionCookie);
        
        // Set redirect location
        response.setHeader("Location", "/new-page.html");
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY); // 302
        
        PrintWriter out = response.getWriter();
        out.println("<html><body>Redirecting...</body></html>");
        out.close();
    }
}
```

### Example 4: Handling Multiple HTTP Methods

```java
public class FlexibleServlet extends HttpServlet {
    
    // GET: Retrieve
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println("GET request received");
    }
    
    // POST: Create
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_CREATED); // 201
        response.getWriter().println("Resource created");
    }
    
    // PUT: Replace (override)
    protected void doPut(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println("Resource updated");
    }
    
    // DELETE: Remove
    protected void doDelete(HttpServletRequest request, 
                           HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/plain");
        response.getWriter().println("Resource deleted");
    }
}
```

### Example 5: Reading Request Headers

```java
public class RequestHeaderServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get specific headers
        String userAgent = request.getHeader("User-Agent");
        String host = request.getHeader("Host");
        String contentType = request.getHeader("Content-Type");
        
        out.println("<html><body>");
        out.println("<h2>Request Headers</h2>");
        out.println("<p>User-Agent: " + userAgent + "</p>");
        out.println("<p>Host: " + host + "</p>");
        out.println("<p>Content-Type: " + contentType + "</p>");
        
        // Iterate all headers
        out.println("<h3>All Headers:</h3>");
        java.util.Enumeration<String> headerNames = 
            request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            out.println(headerName + ": " + headerValue + "<br/>");
        }
        
        out.println("</body></html>");
        out.close();
    }
}
```

---

## Common Exceptions

| Exception | Cause | Solution |
|-----------|-------|----------|
| **IOException** | I/O error while writing response | Wrap in try-catch, log error |
| **IllegalStateException** | Called after response committed | Check if response already sent |
| **ServletException** | Request/response handling error | Check servlet configuration |
| **NullPointerException** | Null parameter not checked | Validate parameters before use |

---

## Important Notes

### 1. **Content-Type Must Be Set Before Writing**
```java
// CORRECT: Set type first
response.setContentType("application/json");
response.getWriter().println("{...}");

// WRONG: Too late, type already set to default
response.getWriter().println("{...}");
response.setContentType("application/json"); // Ignored
```

### 2. **Status Code Should Be Set Before Body**
```java
// CORRECT
response.setStatus(HttpServletResponse.SC_CREATED);
response.getWriter().println("Created");

// RISKY: Depends on buffer flushing
response.getWriter().println("Created");
response.setStatus(HttpServletResponse.SC_CREATED);
```

### 3. **GET Request – No Body Sent**
- Only headers and query parameters
- Use `request.getParameter("name")` for query strings
- Example: `GET /search?q=java`

### 4. **POST Request – Body Sent**
- Form data, JSON, XML in request body
- Use `request.getInputStream()` for raw body
- Use `request.getParameter()` for form fields

### 5. **Idempotency**
- GET/PUT/DELETE should be idempotent (same result on repeat)
- POST is not idempotent (may create duplicate resources)

### 6. **Common Content-Types**
```
text/html                           → HTML page
text/plain                          → Plain text
application/json                   → JSON data
application/xml                    → XML data
application/x-www-form-urlencoded → Form submission
multipart/form-data                → File upload
image/png, image/jpeg              → Image files
```

### 7. **Response Buffer**
- Servlet uses buffered output (typically 8KB)
- Once buffer fills/flushes, status and headers are locked
- Cannot change status code after buffer flush

### 8. **Methods Dispatch Flow**
```
HttpServlet.service(request, response)
    ↓
Determines request method
    ↓
Calls appropriate method:
├─ doGet()
├─ doPost()
├─ doPut()
├─ doDelete()
├─ doHead()
├─ doOptions()
└─ doTrace()
```

---
