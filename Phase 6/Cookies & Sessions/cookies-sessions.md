# Cookies and Sessions

---

## Table of Contents
1. [Cookies Overview](#cookies-overview)
2. [Cookie Attributes and Methods](#cookie-attributes-and-methods)
3. [Session Overview](#session-overview)
4. [HttpSession Methods](#httpsession-methods)
5. [Cookies vs Sessions](#cookies-vs-sessions)
6. [Session Tracking Mechanisms](#session-tracking-mechanisms)
7. [Working Sequences](#working-sequences)
8. [Security Considerations](#security-considerations)
9. [Code Examples](#code-examples)
10. [Important Notes](#important-notes)

---

## Cookies Overview

### Purpose
- Store small data on **client side** (browser)
- Sent with every HTTP request to that domain
- Persistent across browser sessions (if set to persist)
- Used for tracking, preferences, authentication tokens

### Cookie Structure

```
Set-Cookie: sessionId=abc123xyz; Path=/; Domain=example.com; Max-Age=3600; Secure; HttpOnly
             │              │       │     │                  │           │         │
             │              │       │     │                  │           │         └─ Accessible only via HTTP (not JavaScript)
             │              │       │     │                  │           └─ HTTPS only
             │              │       │     │                  └─ Lifetime in seconds
             │              │       │     └─ Domain cookie applies to
             │              │       └─ URL path cookie applies to
             │              └─ Cookie value
             └─ Cookie name
```

### Cookie Lifecycle Diagram

```
┌─────────────┐
│  Server     │
│ creates     │
│ cookie      │
└─────────────┘
       │
       ▼
┌─────────────────────────────────┐
│ Set-Cookie: name=value          │
│ Max-Age: 3600 (1 hour)          │
└─────────────────────────────────┘ (Response Header)
       │
       ▼
┌─────────────┐
│  Browser    │
│ stores      │
│ cookie      │
└─────────────┘
       │
       ▼
Every subsequent request to same domain:
┌─────────────────────────────────┐
│ Cookie: name=value              │
└─────────────────────────────────┘ (Request Header)
       │
       ▼
┌─────────────┐
│  Server     │
│ reads       │
│ cookie      │
└─────────────┘
       │
       ▼
After Max-Age expires:
┌─────────────┐
│  Browser    │
│ deletes     │
│ cookie      │
└─────────────┘
```

---

## Cookie Attributes and Methods

### Cookie Creation and Attributes

| Attribute | Method | Purpose | Example |
|-----------|--------|---------|---------|
| **Name** | `new Cookie(name, value)` | Cookie identifier | `new Cookie("userId", "123")` |
| **Value** | `getValue()` / `setValue()` | Cookie data | `cookie.setValue("newValue")` |
| **Path** | `setPath(String)` | URL path where cookie applies | `cookie.setPath("/app")` |
| **Domain** | `setDomain(String)` | Domain where cookie applies | `cookie.setDomain(".example.com")` |
| **Max-Age** | `setMaxAge(int)` | Lifetime in seconds (-1 = session, 0 = delete) | `cookie.setMaxAge(3600)` |
| **Expires** | `setExpires(Date)` | Expiration timestamp | `cookie.setExpires(futureDate)` |
| **Secure** | `setSecure(boolean)` | HTTPS only | `cookie.setSecure(true)` |
| **HttpOnly** | `setHttpOnly(boolean)` | Not accessible via JavaScript | `cookie.setHttpOnly(true)` |
| **SameSite** | Browser support | CSRF protection | None, Lax, Strict |

### Cookie Methods

```java
// Creating
Cookie cookie = new Cookie("sessionId", "abc123");

// Setting attributes
cookie.setMaxAge(3600);           // 1 hour
cookie.setPath("/");              // All paths
cookie.setDomain(".example.com"); // All subdomains
cookie.setSecure(true);           // HTTPS only
cookie.setHttpOnly(true);         // No JavaScript access

// Sending to client
response.addCookie(cookie);

// Receiving from client
Cookie[] cookies = request.getCookies();
for (Cookie c : cookies) {
    String name = c.getName();
    String value = c.getValue();
}

// Deleting
cookie.setMaxAge(0);              // Expire immediately
response.addCookie(cookie);
```

---

## Session Overview

### Purpose
- Store data on **server side** (session object)
- Associated with specific user via Session ID (usually in cookie)
- Persists for session duration (or user idle timeout)
- More secure than cookies (data not sent to client)

### Session Lifecycle

```
┌──────────────────┐
│  User visits app │
│  No session yet  │
└──────────────────┘
        │
        ▼
┌──────────────────────────────────┐
│ request.getSession(true)         │
│ Creates new session on server    │
│ Generates unique sessionId       │
└──────────────────────────────────┘
        │
        ▼
┌──────────────────────────────────┐
│ Set-Cookie: JSESSIONID=xyz123    │
│ Sent to browser                  │
└──────────────────────────────────┘
        │
        ▼
┌──────────────────┐
│  User makes      │
│  requests        │
│  (30 min idle)   │
└──────────────────┘
        │
        ▼
┌──────────────────────────────────┐
│ Server tracks session            │
│ Each request: Cookie with        │
│ JSESSIONID sent to server        │
└──────────────────────────────────┘
        │
        ▼
┌──────────────────────────────────┐
│ After 30 minutes idle:           │
│ Session timeout occurs           │
│ Session object destroyed         │
│ Browser still has cookie (stale) │
└──────────────────────────────────┘
```

---

## HttpSession Methods

| Method | Purpose | Return |
|--------|---------|--------|
| **setAttribute(String name, Object value)** | Store object in session | void |
| **getAttribute(String name)** | Retrieve object from session | Object |
| **removeAttribute(String name)** | Remove object from session | void |
| **getAttributeNames()** | Get all attribute names | Enumeration |
| **getId()** | Get unique session ID | String |
| **isNew()** | Check if newly created | boolean |
| **getCreationTime()** | Get creation timestamp | long |
| **getLastAccessedTime()** | Get last access timestamp | long |
| **getMaxInactiveInterval()** | Get timeout in seconds | int |
| **setMaxInactiveInterval(int)** | Set timeout in seconds | void |
| **invalidate()** | Destroy session (logout) | void |

---

## Cookies vs Sessions

### Comparison

| Aspect | Cookies | Sessions |
|--------|---------|----------|
| **Storage Location** | Client (browser) | Server (memory/disk) |
| **Data Sent** | All cookie data sent to server | Only Session ID sent |
| **Security** | Data visible to browser/hacker | Data protected on server |
| **Size Limit** | ~4KB per cookie | Limited by server memory |
| **Persistence** | Can be persistent (years) | Expires after timeout |
| **Performance** | Lightweight, no server load | Server-side overhead |
| **Access** | JavaScript can read (unless HttpOnly) | Only server-side |
| **Use Case** | Non-sensitive data (preferences) | User authentication, sensitive data |
| **Multi-Domain** | Can be shared across domains | Single domain only |
| **Browser Closure** | Persists (if Max-Age set) | Expires on logout/timeout |

### Visual Comparison

```
COOKIES (Client-Side Storage):
┌──────────────────────────────────────┐
│  Response (Server → Client)          │
│  Set-Cookie: userId=123              │
│  Set-Cookie: preferences=dark_mode   │
└──────────────────────────────────────┘
         ↓
┌──────────────────────────────────────┐
│  Browser Storage                     │
│  [userId:123]                        │
│  [preferences:dark_mode]             │
│  Visible to JavaScript               │
└──────────────────────────────────────┘
         ↓
┌──────────────────────────────────────┐
│  Next Request (Client → Server)      │
│  Cookie: userId=123                  │
│  Cookie: preferences=dark_mode       │
└──────────────────────────────────────┘


SESSIONS (Server-Side Storage):
┌──────────────────────────────────────┐
│  Response (Server → Client)          │
│  Set-Cookie: JSESSIONID=abc123       │
└──────────────────────────────────────┘
         ↓
┌──────────────────────────────────────┐
│  Browser Storage (only ID)           │
│  [JSESSIONID:abc123]                 │
│  Sent with every request             │
└──────────────────────────────────────┘
         ↓
┌──────────────────────────────────────┐
│  Server Storage (Session Object)     │
│  abc123: {                           │
│    userId: 123,                      │
│    userName: "john",                 │
│    loginTime: 1234567890             │
│  }                                   │
└──────────────────────────────────────┘
```

---

## Session Tracking Mechanisms

### Mechanism 1: Cookies (Default, Recommended)

```
Server → Browser: Set-Cookie: JSESSIONID=abc123; Path=/
                  ↓
Browser → Server: (All requests) Cookie: JSESSIONID=abc123
                  ↓
Server reads JSESSIONID → Looks up session object
```

### Mechanism 2: URL Rewriting (Fallback if Cookies Disabled)

```
Server generates URL with session ID:
/app/page.jsp;jsessionid=abc123

Browser requests:
GET /app/page.jsp;jsessionid=abc123

Server reads jsessionid from URL → Looks up session
```

### Mechanism 3: Hidden Form Field (Rarely Used)

```html
<form method="POST">
    <input type="hidden" name="jsessionid" value="abc123" />
    <input type="submit" />
</form>
```

---

## Working Sequences

### Login Flow with Session

```
1. User accesses login page (GET)
   ├─ Server: request.getSession(true)  ← New session created
   ├─ Server: Set-Cookie: JSESSIONID=new_id
   └─ Display login form

2. User submits form (POST)
   ├─ Browser sends: Cookie: JSESSIONID=id
   ├─ Server: request.getSession()  ← Retrieves existing session
   ├─ Verify credentials
   ├─ session.setAttribute("userId", 123)
   └─ Redirect to dashboard

3. User accesses dashboard (GET)
   ├─ Browser sends: Cookie: JSESSIONID=id
   ├─ Server: request.getSession()  ← Same session
   ├─ Get userId: session.getAttribute("userId")  → 123
   └─ Display user-specific content

4. User clicks logout (GET)
   ├─ Server: session.invalidate()  ← Destroy session
   ├─ Server: Set-Cookie: JSESSIONID=; Max-Age=0  (Delete cookie)
   └─ Redirect to login page
```

### Session Timeout Flow

```
User makes requests
        │
        ▼
Session active (last access updated)
        │
        ▼
User stops making requests
        │
        ▼
30 minutes pass (default timeout)
        │
        ▼
Next request arrives
        │
        ▼
Server checks: Last access + timeout < Current time?
        │
        ├─ YES → Session expired, create new
        │        Browser still has old JSESSIONID
        │        Server doesn't recognize it
        │
        └─ NO → Session still active
```

---

## Security Considerations

### 1. HttpOnly Cookie Flag

```java
// VULNERABLE: JavaScript can steal session ID
Cookie session = new Cookie("JSESSIONID", id);
response.addCookie(session);  // No HttpOnly
// window.document.cookie → Attacker steals it!

// SECURE: HttpOnly blocks JavaScript access
Cookie session = new Cookie("JSESSIONID", id);
session.setHttpOnly(true);    // JavaScript cannot read
response.addCookie(session);
```

### 2. Secure Cookie Flag (HTTPS Only)

```java
// VULNERABLE: Sent over HTTP (can be intercepted)
Cookie session = new Cookie("JSESSIONID", id);
response.addCookie(session);  // Sent over HTTP too

// SECURE: HTTPS only
Cookie session = new Cookie("JSESSIONID", id);
session.setSecure(true);      // Only sent over HTTPS
response.addCookie(session);
```

### 3. Session Fixation Attack Prevention

```java
// VULNERABLE: Reuse old session ID after login
session = request.getSession();
// User logs in...
session.setAttribute("userId", 123);
// Session ID same as before login → Fixation attack!

// SECURE: Create new session after login
session.invalidate();  // Destroy old session
session = request.getSession(true);  // Create new session
session.setAttribute("userId", 123);
// New session ID, old one invalid
```

### 4. Session Timeout

```java
// Default: 30 minutes
// Set in web.xml:
<session-config>
    <cookie-config>
        <secure>true</secure>
        <http-only>true</http-only>
    </cookie-config>
    <tracking-mode>COOKIE</tracking-mode>
    <timeout>30</timeout>  <!-- In minutes -->
</session-config>

// Or programmatically:
session.setMaxInactiveInterval(1800);  // 30 minutes in seconds
```

---

## Code Examples

### Example 1: Creating and Reading Cookies

```java
public class CookieServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // 1. CREATE and send cookies
        Cookie userCookie = new Cookie("userId", "12345");
        userCookie.setMaxAge(24 * 60 * 60);  // 1 day
        userCookie.setPath("/");
        userCookie.setHttpOnly(true);
        response.addCookie(userCookie);
        
        Cookie themeCookie = new Cookie("theme", "dark");
        themeCookie.setMaxAge(30 * 24 * 60 * 60);  // 30 days
        themeCookie.setPath("/");
        response.addCookie(themeCookie);
        
        // 2. READ cookies from request
        out.println("<h2>Cookies Received:</h2>");
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null && cookies.length > 0) {
            out.println("<ul>");
            for (Cookie cookie : cookies) {
                out.println("<li>" + cookie.getName() + " = " + 
                           cookie.getValue() + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No cookies found</p>");
        }
        
        out.println("<p>Cookies sent! Refresh page to see them.</p>");
        out.close();
    }
}
```

### Example 2: Session-Based Login

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    // Display login form (GET)
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><body>");
        out.println("<h2>Login</h2>");
        out.println("<form method='POST'>");
        out.println("  <label>Username:</label>");
        out.println("  <input type='text' name='username' required /><br/>");
        out.println("  <label>Password:</label>");
        out.println("  <input type='password' name='password' required /><br/>");
        out.println("  <button type='submit'>Login</button>");
        out.println("</form>");
        out.println("</body></html>");
        
        out.close();
    }
    
    // Process login (POST)
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Mock authentication
        if ("admin".equals(username) && "password".equals(password)) {
            // 1. Invalidate old session (prevent fixation)
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            
            // 2. Create new session
            HttpSession session = request.getSession(true);
            
            // 3. Store user data in session
            session.setAttribute("userId", 1);
            session.setAttribute("username", username);
            session.setAttribute("loginTime", System.currentTimeMillis());
            
            // 4. Set session timeout
            session.setMaxInactiveInterval(30 * 60);  // 30 minutes
            
            out.println("<h2>Login Successful!</h2>");
            out.println("<p>Session ID: " + session.getId() + "</p>");
            out.println("<p>Welcome, " + username + "</p>");
            out.println("<a href='/app/dashboard'>Go to Dashboard</a>");
            
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("<h2>Login Failed</h2>");
            out.println("<p><a href='/app/login'>Try Again</a></p>");
        }
        
        out.close();
    }
}
```

### Example 3: Session Usage (Dashboard)

```java
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get existing session (don't create new)
        HttpSession session = request.getSession(false);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Check if user is logged in
        if (session == null || 
            session.getAttribute("userId") == null) {
            
            // Not logged in
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("<h2>Access Denied</h2>");
            out.println("<p>Please <a href='/app/login'>login</a></p>");
            out.close();
            return;
        }
        
        // User is logged in
        Integer userId = (Integer) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        Long loginTime = (Long) session.getAttribute("loginTime");
        
        out.println("<h2>Dashboard</h2>");
        out.println("<p>Welcome, " + username + " (ID: " + userId + ")</p>");
        out.println("<p>Logged in at: " + new java.util.Date(loginTime) + "</p>");
        out.println("<p><a href='/app/logout'>Logout</a></p>");
        
        // Display user-specific content
        out.println("<h3>Your Data</h3>");
        out.println("<p>Session ID: " + session.getId() + "</p>");
        out.println("<p>Session expires in: " + 
                   session.getMaxInactiveInterval() + " seconds</p>");
        
        out.close();
    }
}
```

### Example 4: Logout (Session Invalidation)

```java
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get current session (don't create new)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Log activity (optional)
            String username = (String) session.getAttribute("username");
            System.out.println("User " + username + " logged out");
            
            // 1. Remove all session attributes
            session.removeAttribute("userId");
            session.removeAttribute("username");
            session.removeAttribute("loginTime");
            
            // 2. Invalidate session (destroy completely)
            session.invalidate();
        }
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Logged Out</h2>");
        out.println("<p>You have been successfully logged out.</p>");
        out.println("<p><a href='/app/login'>Login Again</a></p>");
        
        out.close();
    }
}
```

### Example 5: Cookie Deletion

```java
public class CookieDeletionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Find and delete specific cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    // Set Max-Age to 0 to delete
                    Cookie deleteCookie = new Cookie("userId", "");
                    deleteCookie.setMaxAge(0);
                    deleteCookie.setPath("/");
                    response.addCookie(deleteCookie);
                    
                    out.println("<p>Cookie 'userId' deleted</p>");
                    break;
                }
            }
        }
        
        // Alternative: Delete all cookies
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                Cookie expireCookie = new Cookie(cookie.getName(), "");
                expireCookie.setMaxAge(0);
                expireCookie.setPath("/");
                response.addCookie(expireCookie);
            }
            out.println("<p>All cookies deleted</p>");
        }
        
        out.close();
    }
}
```

---

## Important Notes

### 1. **Always Check Session Before Using**
```java
// WRONG: Assumes session exists
HttpSession session = request.getSession();
String userId = (String) session.getAttribute("userId");
// NPE if userId is null!

// CORRECT: Check if logged in
HttpSession session = request.getSession(false);  // Don't create
if (session == null || session.getAttribute("userId") == null) {
    // Not logged in, redirect to login
    response.sendRedirect("/login");
    return;
}
String userId = (String) session.getAttribute("userId");
```

### 2. **getSession() vs getSession(false)**
```java
// getSession() or getSession(true) - Create if not exists
HttpSession session = request.getSession();  // Always returns session

// getSession(false) - Don't create if not exists
HttpSession session = request.getSession(false);  // May return null
```

### 3. **Cookie vs Session Security**
```java
// INSECURE: Store sensitive data in cookies
Cookie password = new Cookie("password", userPassword);
response.addCookie(password);  // User can see it!

// SECURE: Store in session
session.setAttribute("password", userPassword);  // Server-side only
```

### 4. **Session Attributes Should Be Serializable**
```java
// For distributed/persistent sessions, use serializable objects
public class UserData implements Serializable {
    public int userId;
    public String username;
}

session.setAttribute("user", new UserData());  // Safe
```

### 5. **Cookie Domain and Path**
```java
// Sent to same domain and path
Cookie cookie = new Cookie("key", "value");
cookie.setPath("/app");        // Only /app/* paths
cookie.setDomain(".example.com");  // Subdomains too

// If not set:
// Path defaults to current request path
// Domain is current domain only
```

### 6. **Session Timeout Configuration**
```xml
<!-- web.xml -->
<session-config>
    <timeout>30</timeout>  <!-- 30 minutes -->
</session-config>

<!-- Or programmatically -->
session.setMaxInactiveInterval(1800);  // seconds
```

### 7. **Browser Cleanup After Logout**
```java
// Session invalidated on server
session.invalidate();

// But browser may still have cookie
// Let browser clean it up by expiring
Cookie sessionCookie = new Cookie("JSESSIONID", "");
sessionCookie.setMaxAge(0);
response.addCookie(sessionCookie);
```

### 8. **Cookies in Protected vs Public Data**
```java
// PUBLIC DATA (Preferences, theme, language)
Cookie theme = new Cookie("theme", "dark");
theme.setMaxAge(365 * 24 * 60 * 60);  // 1 year, ok to persist

// PRIVATE DATA (User ID, login token)
// Use Session instead, or
// Use HttpOnly cookie with short expiration
Cookie token = new Cookie("authToken", generateToken());
token.setHttpOnly(true);
token.setSecure(true);
token.setMaxAge(1800);  // 30 minutes only
response.addCookie(token);
```

---
