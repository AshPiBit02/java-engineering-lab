# Reading and Processing Forms

---

## Table of Contents
1. [HTML Form Basics](#html-form-basics)
2. [Form Submission Methods](#form-submission-methods)
3. [Form Input Types](#form-input-types)
4. [Reading Form Data](#reading-form-data)
5. [Form Validation](#form-validation)
6. [Processing Common Form Patterns](#processing-common-form-patterns)
7. [File Upload Handling](#file-upload-handling)
8. [Security Considerations](#security-considerations)
9. [Code Examples](#code-examples)
10. [Important Notes](#important-notes)

---

## HTML Form Basics

### Form Structure

```html
<form action="/app/servlet" method="POST" enctype="application/x-www-form-urlencoded">
   │      │              │       │                │
   │      │              │       │                └─ Encoding type for data
   │      │              │       └────────────────── HTTP method
   │      └──────────────────────────────────────── Form submission target
   └──────────────────────────────────────────────── Form container
   
   <input type="text" name="username" required />
   <input type="password" name="password" required />
   <input type="email" name="email" />
   <input type="checkbox" name="interests" value="java" />
   <input type="radio" name="experience" value="beginner" />
   <button type="submit">Submit</button>
</form>
```

### Form Enctype Values

| Enctype | When to Use | How Data Sent |
|---------|-------------|---------------|
| **application/x-www-form-urlencoded** | Regular forms (default) | name=value&name2=value2 |
| **multipart/form-data** | File uploads | Binary chunks with boundaries |
| **text/plain** | Plain text only (rarely used) | Unencoded text |

---

## Form Submission Methods

### GET vs POST

| Aspect | GET | POST |
|--------|-----|------|
| **Data Location** | Query string in URL | Request body |
| **Data Visibility** | Visible in URL bar | Hidden from user |
| **Data Size Limit** | ~2000 characters | Large (usually MB) |
| **Caching** | Browser caches | Not cached |
| **Security** | Not secure (URL visible) | More secure (body hidden) |
| **Use Case** | Search, filtering, pagination | Login, file upload, sensitive data |
| **Idempotent** | Yes (safe to repeat) | No (may create duplicates) |
| **Browser Back** | Safe (no resubmit) | May prompt to resubmit |
| **Bookmarkable** | Yes (URL contains data) | No (data in body) |

### Form Request Flow

```
GET Method:
┌────────────────────────────────────┐
│ HTML Form (method="GET")           │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│ Query String: ?name=john&age=25    │
│ URL: /app/search?name=john&age=25  │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│ Servlet receives via:              │
│ request.getParameter("name")       │
│ request.getParameter("age")        │
└────────────────────────────────────┘

POST Method:
┌────────────────────────────────────┐
│ HTML Form (method="POST")          │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│ Request Body:                      │
│ name=john&age=25&email=john@...    │
│ (Content-Type: application/...)    │
└────────────────────────────────────┘
              ↓
┌────────────────────────────────────┐
│ Servlet receives via:              │
│ request.getParameter("name")       │
│ request.getParameter("age")        │
│ request.getParameter("email")      │
└────────────────────────────────────┘
```

---

## Form Input Types

### Common HTML Input Types and Mapping

| HTML Input Type | HTML Example | Request Parameter | Java Type | Notes |
|-----------------|--------------|-------------------|-----------|-------|
| **text** | `<input type="text" name="username" />` | String | String | Single line text |
| **password** | `<input type="password" name="pwd" />` | String | String | Masked input |
| **email** | `<input type="email" name="email" />` | String | String | Validated by browser |
| **number** | `<input type="number" name="age" />` | String | Integer (parse) | Must convert to int |
| **checkbox** | `<input type="checkbox" name="interests" value="java" />` | String[] | String[] | Use getParameterValues() |
| **radio** | `<input type="radio" name="gender" value="M" />` | String | String | Single selection |
| **select/dropdown** | `<select name="country"><option>...</option></select>` | String | String | Single or multiple |
| **textarea** | `<textarea name="message"></textarea>` | String | String | Multi-line text |
| **file** | `<input type="file" name="upload" />` | File stream | Part/MultipartFile | Requires multipart/form-data |
| **hidden** | `<input type="hidden" name="csrf" value="token123" />` | String | String | Invisible to user |
| **date** | `<input type="date" name="birthdate" />` | String | String | Format: YYYY-MM-DD |
| **time** | `<input type="time" name="start" />` | String | String | Format: HH:MM |

---

## Reading Form Data

### Single Value Parameters

```java
// Get single string value
String name = request.getParameter("username");

// Get with default if missing
String name = request.getParameter("username");
if (name == null) {
    name = "Guest";
}

// Get and convert to int
String ageStr = request.getParameter("age");
int age = 0;
try {
    age = Integer.parseInt(ageStr);
} catch (NumberFormatException e) {
    age = 0;  // Default
}
```

### Multiple Value Parameters (Checkboxes)

```java
// Get all values for a parameter name
String[] interests = request.getParameterValues("interests");

if (interests != null && interests.length > 0) {
    for (String interest : interests) {
        System.out.println(interest);  // "java", "python", etc.
    }
}
```

### Get All Parameters

```java
// Get all parameter names
java.util.Enumeration<String> paramNames = 
    request.getParameterNames();

while (paramNames.hasMoreElements()) {
    String paramName = paramNames.nextElement();
    String paramValue = request.getParameter(paramName);
    System.out.println(paramName + " = " + paramValue);
}

// Alternative: get as Map
java.util.Map<String, String[]> paramMap = 
    request.getParameterMap();

for (String key : paramMap.keySet()) {
    String[] values = paramMap.get(key);
    System.out.println(key + " = " + java.util.Arrays.toString(values));
}
```

---

## Form Validation

### Client-Side vs Server-Side

```
┌─────────────────────────────────────────────────┐
│  CLIENT-SIDE (HTML5)                            │
│  <input type="email" required />                │
│  <input type="number" min="0" max="120" />      │
│  └─ Fast, immediate feedback, BUT easy to bypass│
└─────────────────────────────────────────────────┘
              ↓
┌─────────────────────────────────────────────────┐
│  SERVER-SIDE (Java/Servlet) ← ALWAYS REQUIRED   │
│  - Validate data format, ranges, constraints    │
│  - Cannot be bypassed by user                   │
│  - Security critical                            │
└─────────────────────────────────────────────────┘
```

### Validation Checklist

| Validation | Java Check | Example |
|-----------|-----------|---------|
| **Not Empty** | `string == null \|\| string.isEmpty()` | Name field required |
| **Length** | `string.length() < min \|\| length() > max` | 3-50 characters |
| **Pattern/Regex** | `string.matches(regex)` | Email, phone format |
| **Range** | `num < min \|\| num > max` | Age 18-120 |
| **Exists** | `database.findById(id) != null` | Valid user ID |
| **Unique** | `database.findByEmail(email) == null` | New email not taken |
| **Type** | `Integer.parseInt()` with try-catch | Number field |

---

## Processing Common Form Patterns

### Login Form Pattern

```
HTML Form                    Request Parameters        Server Processing
┌─────────────────┐       ┌──────────────────┐       ┌──────────────────┐
│ username field  │──────>│ username:string  │──────>│ 1. Validate empty│
│ password field  │──────>│ password:string  │       │ 2. DB lookup     │
└─────────────────┘       └──────────────────┘       │ 3. Auth check    │
                                                     │ 4. Session/Cookie│
                                                     │ 5. Redirect      │
                                                     └──────────────────┘
```

### Search/Filter Form Pattern

```
HTML Form                    Request Parameters        Server Processing
┌─────────────────┐       ┌──────────────────┐       ┌──────────────────┐
│ search text     │──────>│ query:string     │──────>│ 1. Validate      │
│ category select │──────>│ category:string  │       │ 2. DB query      │
│ sort dropdown   │──────>│ sortBy:string    │       │ 3. Filter results│
└─────────────────┘       └──────────────────┘       │ 4. Display page  │
                                                     └──────────────────┘
```

### Multi-Checkbox Form Pattern

```
HTML Form                    Request Parameters        Server Processing
┌─────────────────┐       ┌──────────────────┐       ┌──────────────────┐
│ java (checked)  │──────>│ interests:array  │──────>│ 1. Get all values│
│ python (checked)│       │ [0]="java"       │       │ 2. Store in DB   │
│ cpp (unchecked) │       │ [1]="python"     │       │ 3. Confirm save  │
└─────────────────┘       └──────────────────┘       └──────────────────┘
                          (unchecked items NOT sent)
```

---

## File Upload Handling

### File Upload Requirements

```html
<!-- MUST use enctype="multipart/form-data" -->
<form action="/app/upload" method="POST" enctype="multipart/form-data">
    <input type="file" name="document" accept=".pdf,.doc,.docx" required />
    <button type="submit">Upload</button>
</form>
```

### Reading File Upload (Basic Method - Not Recommended)

```java
// WARNING: Basic approach, no library
// Use Apache Commons FileUpload or servlet library for production

@Override
protected void doPost(HttpServletRequest request, 
                     HttpServletResponse response) 
        throws ServletException, IOException {
    
    String contentType = request.getContentType();
    if (contentType == null || !contentType.contains("multipart/form-data")) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        return;
    }
    
    ServletInputStream input = request.getInputStream();
    // Parse multipart boundary and extract file
    // (Complex: split by boundary, decode parts, etc.)
}
```

### Reading File Upload (Servlet 3.0+ Annotation)

```java
@MultipartConfig(
    maxFileSize = 1024 * 1024 * 5,      // 5MB
    maxRequestSize = 1024 * 1024 * 10   // 10MB
)
@WebServlet("/upload")
public class FileUploadServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get file from request
        Part filePart = request.getPart("document");
        
        if (filePart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                             "No file uploaded");
            return;
        }
        
        // Validate file
        String fileName = filePart.getSubmittedFileName();
        long fileSize = filePart.getSize();
        String contentType = filePart.getContentType();
        
        if (fileSize == 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                             "Empty file");
            return;
        }
        
        if (fileSize > 5 * 1024 * 1024) {  // 5MB limit
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                             "File too large");
            return;
        }
        
        // Validate file type
        if (!contentType.equals("application/pdf")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                             "Only PDF files allowed");
            return;
        }
        
        // Save file
        String uploadDir = getServletContext().getRealPath("/uploads");
        String filePath = uploadDir + File.separator + fileName;
        
        try (InputStream is = filePart.getInputStream();
             FileOutputStream fos = new FileOutputStream(filePath)) {
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            
            response.getWriter().println("File uploaded: " + fileName);
        }
    }
}
```

---

## Security Considerations

### 1. Cross-Site Scripting (XSS) Prevention

```java
// VULNERABLE: User input echoed directly
String name = request.getParameter("name");
response.getWriter().println("<p>Hello, " + name + "</p>");
// If name = "<script>alert('hacked')</script>" → Script executes!

// SECURE: Escape HTML
String name = request.getParameter("name");
String escaped = org.apache.commons.text.StringEscapeUtils
    .escapeHtml4(name);
response.getWriter().println("<p>Hello, " + escaped + "</p>");
```

### 2. SQL Injection Prevention

```java
// VULNERABLE: String concatenation
String username = request.getParameter("user");
String query = "SELECT * FROM users WHERE username = '" + username + "'";
// If username = "' OR '1'='1" → Returns all users!

// SECURE: Prepared statements
String username = request.getParameter("user");
String query = "SELECT * FROM users WHERE username = ?";
PreparedStatement stmt = conn.prepareStatement(query);
stmt.setString(1, username);  // Escaped automatically
ResultSet rs = stmt.executeQuery();
```

### 3. Cross-Site Request Forgery (CSRF) Prevention

```java
// Form sends hidden token
String csrfToken = UUID.randomUUID().toString();
session.setAttribute("csrf_token", csrfToken);

// Validate on submission
String submittedToken = request.getParameter("csrf_token");
String sessionToken = (String) session.getAttribute("csrf_token");

if (submittedToken == null || !submittedToken.equals(sessionToken)) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
    return;
}
```

### 4. File Upload Security

```java
// DON'T trust filename
String fileName = filePart.getSubmittedFileName();  // User-controlled!

// SECURE: Generate safe filename
String safeFileName = "upload_" + System.currentTimeMillis() 
                    + "_" + UUID.randomUUID();
String originalExt = fileName.substring(fileName.lastIndexOf("."));
String finalFileName = safeFileName + originalExt;

// Whitelist allowed extensions
String[] allowedExts = {".pdf", ".doc", ".docx", ".txt"};
boolean allowed = false;
for (String ext : allowedExts) {
    if (originalExt.equalsIgnoreCase(ext)) {
        allowed = true;
        break;
    }
}
if (!allowed) {
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                     "File type not allowed");
}
```

---

## Code Examples

### Example 1: Basic Form Registration

```java
public class RegistrationServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Read form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // Validation
        if (username == null || username.trim().isEmpty()) {
            out.println("<h2 style='color:red'>Error: Username required</h2>");
            return;
        }
        
        if (username.length() < 3 || username.length() > 20) {
            out.println("<h2 style='color:red'>Error: Username 3-20 chars</h2>");
            return;
        }
        
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            out.println("<h2 style='color:red'>Error: Invalid email</h2>");
            return;
        }
        
        if (password == null || password.length() < 6) {
            out.println("<h2 style='color:red'>Error: Password min 6 chars</h2>");
            return;
        }
        
        if (!password.equals(confirm)) {
            out.println("<h2 style='color:red'>Error: Passwords don't match</h2>");
            return;
        }
        
        // All validation passed
        out.println("<h2 style='color:green'>Registration Successful!</h2>");
        out.println("<p>Username: " + username + "</p>");
        out.println("<p>Email: " + email + "</p>");
        out.println("<a href='/app/login'>Go to Login</a>");
    }
}
```

### Example 2: Handling Checkboxes

```java
public class PreferencesServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, 
                         HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get all selected checkboxes
        String[] interests = request.getParameterValues("interests");
        String[] languages = request.getParameterValues("languages");
        String newsletter = request.getParameter("newsletter");  // Single checkbox
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Your Preferences</h2>");
        
        // Display interests
        out.println("<h3>Interests:</h3>");
        if (interests != null && interests.length > 0) {
            out.println("<ul>");
            for (String interest : interests) {
                out.println("<li>" + interest + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No interests selected</p>");
        }
        
        // Display languages
        out.println("<h3>Languages:</h3>");
        if (languages != null && languages.length > 0) {
            out.println("<ul>");
            for (String lang : languages) {
                out.println("<li>" + lang + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No languages selected</p>");
        }
        
        // Display newsletter
        out.println("<h3>Newsletter:</h3>");
        if ("on".equals(newsletter)) {
            out.println("<p>✓ Subscribed to newsletter</p>");
        } else {
            out.println("<p>Not subscribed</p>");
        }
        
        out.close();
    }
}
```

### Example 3: Search Form with Validation

```java
public class SearchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        String query = request.getParameter("q");
        String category = request.getParameter("category");
        String page = request.getParameter("page");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<h2>Search Results</h2>");
        
        // Validate query
        if (query == null || query.trim().isEmpty()) {
            out.println("<p>Please enter a search term</p>");
            return;
        }
        
        query = query.trim();
        if (query.length() < 2) {
            out.println("<p>Search term must be at least 2 characters</p>");
            return;
        }
        
        // Validate page number
        int pageNum = 1;
        if (page != null) {
            try {
                pageNum = Integer.parseInt(page);
                if (pageNum < 1) pageNum = 1;
            } catch (NumberFormatException e) {
                pageNum = 1;  // Default
            }
        }
        
        // Validate category
        String[] validCategories = {"all", "articles", "tutorials", "tools"};
        if (category == null) category = "all";
        boolean validCategory = false;
        for (String cat : validCategories) {
            if (cat.equals(category)) {
                validCategory = true;
                break;
            }
        }
        if (!validCategory) category = "all";
        
        // Process search
        out.println("<p>Searching for: <strong>" + query + "</strong></p>");
        out.println("<p>Category: " + category + "</p>");
        out.println("<p>Page: " + pageNum + "</p>");
        
        // Simulate results
        out.println("<ul>");
        for (int i = 0; i < 5; i++) {
            out.println("<li>Result " + (i + 1) + " for '" + query + "'</li>");
        }
        out.println("</ul>");
        
        out.close();
    }
}
```

---

## Important Notes

### 1. **Always Validate on Server**
- Client-side validation (HTML5) is for UX, not security
- Attackers can bypass browser validation
- Always validate in servlet before processing

### 2. **getParameterValues() Returns Null if Not Present**
```java
// WRONG: Assumes always present
for (String val : request.getParameterValues("checkbox")) {  // NPE!
    // ...
}

// CORRECT: Check for null
String[] values = request.getParameterValues("checkbox");
if (values != null) {
    for (String val : values) {
        // ...
    }
}
```

### 3. **Unchecked Checkboxes Are Not Sent**
```html
<!-- Form with unchecked checkbox -->
<input type="checkbox" name="agree" value="yes" />
<!-- If unchecked: getParameter("agree") returns NULL -->
<!-- If checked: getParameter("agree") returns "yes" -->
```

### 4. **Content-Type Matters for File Upload**
```html
<!-- Works for regular forms -->
<form method="POST">  <!-- Default: application/x-www-form-urlencoded -->

<!-- REQUIRED for file upload -->
<form method="POST" enctype="multipart/form-data">
```

### 5. **Always Encode Responses**
```java
response.setContentType("text/html; charset=UTF-8");  // Set encoding
response.setCharacterEncoding("UTF-8");
```

### 6. **Form Processing Flow**
```
1. Receive request
2. Validate all data
3. If invalid → Display errors, don't process
4. If valid → Process (DB insert, etc.)
5. Send response (success page or redirect)
```

### 7. **Submit Button Value**
```html
<!-- Regular submit - no value sent -->
<button type="submit">Submit</button>

<!-- Named submit - value sent if clicked -->
<button type="submit" name="action" value="save">Save</button>
<button type="submit" name="action" value="delete">Delete</button>

<!-- In servlet: -->
String action = request.getParameter("action");  // "save" or "delete"
```

### 8. **Multiple Forms on One Page**
```html
<form action="/app/search">
    <input type="hidden" name="action" value="search" />
</form>

<form action="/app/login">
    <input type="hidden" name="action" value="login" />
</form>

<!-- In servlet: -->
String action = request.getParameter("action");
if ("search".equals(action)) { ... }
else if ("login".equals(action)) { ... }
```

---
