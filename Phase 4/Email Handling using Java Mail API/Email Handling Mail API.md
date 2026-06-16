# Email Handling using Java Mail API

## What is JavaMail API?
**JavaMail API** is a Java framework that provides a platform-independent way to send, receive, and manage emails.  
It abstracts the underlying email protocols (SMTP, POP3, IMAP) and provides a unified API to work with them.

> JavaMail is not part of the standard JDK — it must be added as a dependency (`javax.mail` / `jakarta.mail`).

---

## Email Protocols Overview

| Protocol | Full Name | Port | Use |
|---|---|---|---|
| **SMTP** | Simple Mail Transfer Protocol | 25 / 587 (TLS) / 465 (SSL) | **Sending** emails |
| **POP3** | Post Office Protocol v3 | 110 / 995 (SSL) | **Receiving** — downloads & deletes from server |
| **IMAP** | Internet Message Access Protocol | 143 / 993 (SSL) | **Receiving** — syncs with server; keeps mail on server |

---

## JavaMail Architecture

```
+----------------------------------------------------------+
|                    JavaMail API Layer                    |
|  (javax.mail / jakarta.mail)                             |
+----------------------------------------------------------+
         |               |                  |
    +----+----+    +------+------+    +------+------+
    |  SMTP   |    |    POP3     |    |    IMAP     |
    | Provider|    |  Provider   |    |  Provider   |
    +----+----+    +------+------+    +------+------+
         |               |                  |
+----------------------------------------------------------+
|                   Network / Internet                     |
+----------------------------------------------------------+
         |               |                  |
    +----+----+    +------+------+    +------+------+
    |  SMTP   |    |  POP3       |    |  IMAP       |
    |  Server |    |  Server     |    |  Server     |
    +---------+    +-------------+    +-------------+
```

---

## Core Classes and Their Roles

```
javax.mail
     |
     +-- Session              <- Entry point; holds config + auth properties
     |
     +-- Message (abstract)
     |       |
     |       +-- MimeMessage  <- Actual email object (subject, body, attachments)
     |
     +-- Transport (abstract) <- Sends the message via SMTP
     |
     +-- Store (abstract)     <- Connects to mail server to retrieve emails
     |       |
     |       +-- Folder       <- Represents a mailbox folder (INBOX, SENT, etc.)
     |
     +-- Address (abstract)
     |       |
     |       +-- InternetAddress  <- Represents email address (RFC 822 format)
     |
     +-- Authenticator        <- Provides credentials (username/password)
```

---

## Sending Email — Working Sequence

```
+-------------------------+
|  1. Set SMTP Properties |   host, port, auth, TLS/SSL settings
+----------+--------------+
           |
           v
+-------------------------+
|  2. Create Session      |   Session.getInstance(props, authenticator)
+----------+--------------+
           |
           v
+-------------------------+
|  3. Create MimeMessage  |   new MimeMessage(session)
+----------+--------------+
           |
           v
+-------------------------+
|  4. Set Message Fields  |   setFrom(), addRecipient(), setSubject(), setContent()
+----------+--------------+
           |
           v
+-------------------------+
|  5. Transport.send()    |   Opens SMTP connection, sends, closes connection
+----------+--------------+
           |
           v
+-------------------------+
|  6. Email Delivered     |   SMTP server relays to recipient's mail server
+-------------------------+
```

---

## Sending Email — SMTP Flow Diagram

```
JAVA APP                        SMTP SERVER                  RECIPIENT SERVER
    |                               |                               |
    | --- CONNECT (TCP) ----------> |                               |
    | <-- 220 Ready --------------- |                               |
    |                               |                               |
    | --- EHLO localhost ---------->|                               |
    | <-- 250 Capabilities ---------|                               |
    |                               |                               |
    | --- STARTTLS / AUTH LOGIN --> |                               |
    | <-- 235 Auth OK ------------- |                               |
    |                               |                               |
    | --- MAIL FROM:<sender> -----> |                               |
    | <-- 250 OK ------------------ |                               |
    |                               |                               |
    | --- RCPT TO:<recipient> ----> |                               |
    | <-- 250 OK ------------------ |                               |
    |                               |                               |
    | --- DATA ------------------>  |                               |
    | --- [Headers + Body] -------> |                               |
    | --- . (end of data) --------> |                               |
    | <-- 250 Message Queued ------ |                               |
    |                               |                               |
    | --- QUIT ------------------>  |                               |
    | <-- 221 Bye ----------------  |                               |
    |                               | --- relay email ----------->  |
    |                               |                               |
```

---

## Receiving Email — Working Sequence

```
+-------------------------+
|  1. Set Store Props     |   host, port, protocol (pop3/imap), SSL settings
+----------+--------------+
           |
           v
+-------------------------+
|  2. Create Session      |   Session.getInstance(props, authenticator)
+----------+--------------+
           |
           v
+-------------------------+
|  3. Get Store           |   session.getStore("imaps") or ("pop3s")
+----------+--------------+
           |
           v
+-------------------------+
|  4. store.connect()     |   Authenticates and opens connection to mail server
+----------+--------------+
           |
           v
+-------------------------+
|  5. Get Folder          |   store.getFolder("INBOX")
+----------+--------------+
           |
           v
+-------------------------+
|  6. folder.open()       |   Folder.READ_ONLY or Folder.READ_WRITE
+----------+--------------+
           |
           v
+-------------------------+
|  7. folder.getMessages()|   Returns Message[] array
+----------+--------------+
           |
           v
+-------------------------+
|  8. Read Each Message   |   getSubject(), getFrom(), getContent() ...
+----------+--------------+
           |
           v
+-------------------------+
|  9. Close Folder+Store  |   folder.close(expunge) -> store.close()
+-------------------------+
```

---

## Core Classes — Detailed

### `Session` (`javax.mail.Session`)

The **entry point** of JavaMail. Holds configuration and authentication for mail servers.

| Method | Description |
|---|---|
| `Session.getInstance(Properties, Authenticator)` | Creates a new Session (non-shared) — preferred |
| `Session.getDefaultInstance(Properties, Authenticator)` | Returns a shared default session (avoid in multi-user apps) |
| `setDebug(boolean)` | If true, prints SMTP/IMAP conversation to console — useful for debugging |
| `getProperties()` | Returns the `Properties` object backing this session |
| `getStore(String protocol)` | Returns a `Store` for the given protocol (`"imap"`, `"imaps"`, `"pop3"`) |
| `getTransport(String protocol)` | Returns a `Transport` for the given protocol (`"smtp"`, `"smtps"`) |

---

### `MimeMessage` (`javax.mail.internet.MimeMessage`)

Represents the actual **email message** — wraps headers, body, and attachments.

| Method | Description |
|---|---|
| `setFrom(Address)` | Sets the sender's address |
| `setFrom(String)` | Sets sender address from a string |
| `addRecipient(RecipientType, Address)` | Adds one recipient — types: `TO`, `CC`, `BCC` |
| `addRecipients(RecipientType, Address[])` | Adds multiple recipients of one type |
| `setRecipients(RecipientType, String)` | Sets recipients from comma-separated string |
| `setSubject(String)` | Sets the email subject line |
| `setSubject(String, String charset)` | Sets subject with explicit character encoding |
| `setText(String)` | Sets plain text body |
| `setContent(Object, String mimeType)` | Sets body with explicit MIME type e.g. `"text/html"` |
| `setContent(Multipart)` | Sets multipart body (used for attachments) |
| `setSentDate(Date)` | Sets the sent date header |
| `setHeader(String name, String value)` | Sets a custom email header |
| `getSubject()` | Returns the subject |
| `getFrom()` | Returns array of sender addresses |
| `getAllRecipients()` | Returns all TO + CC + BCC recipients |
| `getContent()` | Returns message body (plain text or `Multipart` object) |
| `getContentType()` | Returns MIME type of the body |
| `getSentDate()` | Returns sent date |
| `getReceivedDate()` | Returns date the server received the message |
| `isMimeType(String)` | Returns true if message is of given MIME type |

#### `Message.RecipientType` Constants

| Constant | Meaning |
|---|---|
| `Message.RecipientType.TO` | Primary recipient |
| `Message.RecipientType.CC` | Carbon copy — visible to all |
| `Message.RecipientType.BCC` | Blind carbon copy — hidden from other recipients |

---

### `Transport` (`javax.mail.Transport`)

Handles **sending** the message via SMTP.

| Method | Description |
|---|---|
| `Transport.send(Message)` | Static — sends message using session's default transport |
| `Transport.send(Message, Address[])` | Sends to specific addresses overriding message recipients |
| `transport.connect(host, port, user, pass)` | Manually opens SMTP connection |
| `transport.sendMessage(Message, Address[])` | Sends after manual `connect()` |
| `transport.close()` | Closes the SMTP connection |

---

### `Store` and `Folder` (`javax.mail.Store`, `javax.mail.Folder`)

Used for **receiving** emails.

| Method | Description |
|---|---|
| `session.getStore("imaps")` | Returns an IMAP-over-SSL Store |
| `store.connect(host, user, password)` | Authenticates and connects to mail server |
| `store.getFolder("INBOX")` | Returns the INBOX folder |
| `store.getDefaultFolder()` | Returns root folder |
| `store.close()` | Disconnects from mail server |
| `folder.open(Folder.READ_ONLY)` | Opens folder for reading only |
| `folder.open(Folder.READ_WRITE)` | Opens folder for reading and modifying |
| `folder.getMessages()` | Returns all `Message[]` in the folder |
| `folder.getMessage(int n)` | Returns the nth message (1-indexed) |
| `folder.getMessageCount()` | Returns total message count |
| `folder.getUnreadMessageCount()` | Returns count of unseen messages |
| `folder.getNewMessageCount()` | Returns count of new messages since last open |
| `folder.close(boolean expunge)` | Closes folder; if `expunge=true`, permanently deletes flagged messages |
| `folder.list()` | Returns array of sub-folders |

---

### `InternetAddress` (`javax.mail.internet.InternetAddress`)

Represents an **email address** in RFC 822 format.

| Constructor / Method | Description |
|---|---|
| `new InternetAddress("user@example.com")` | Simple address |
| `new InternetAddress("user@example.com", "Display Name")` | Address with display name |
| `InternetAddress.parse("a@x.com, b@y.com")` | Parses comma-separated address string into array |
| `getAddress()` | Returns the raw email address string |
| `getPersonal()` | Returns the display name |
| `validate()` | Throws `AddressException` if address format is invalid |

---

### `MimeBodyPart` and `MimeMultipart` (for Attachments)

Used to build emails with **multiple parts** (text + attachments).

```
MimeMultipart
     |
     +-- MimeBodyPart (text body)
     |
     +-- MimeBodyPart (attachment 1)
     |
     +-- MimeBodyPart (attachment 2)
```

| Class / Method | Description |
|---|---|
| `new MimeBodyPart()` | Creates one part of a multipart message |
| `bodyPart.setText(String)` | Sets plain text content for this part |
| `bodyPart.setContent(String, "text/html")` | Sets HTML content for this part |
| `bodyPart.attachFile(File)` | Attaches a file to this part |
| `bodyPart.setFileName(String)` | Sets the displayed filename for attachment |
| `bodyPart.setDataHandler(DataHandler)` | Sets raw data source for attachment |
| `new MimeMultipart()` | Creates multipart container (default: `"mixed"`) |
| `new MimeMultipart("alternative")` | For text + HTML alternative versions |
| `multipart.addBodyPart(MimeBodyPart)` | Adds a part to the multipart |
| `message.setContent(multipart)` | Sets the multipart as the message body |

---

## Complete Examples

### 1. Send Plain Text Email (Gmail SMTP + TLS)
```java
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmail {
    public static void main(String[] args) throws Exception {

        final String senderEmail    = "youremail@gmail.com";
        final String senderPassword = "your_app_password";    // use App Password, not account password
        final String recipientEmail = "recipient@example.com";

        // Step 1: SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");        // TLS encryption

        // Step 2: Create session with authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        session.setDebug(false);                               // set true to see SMTP log

        // Step 3: Create MimeMessage
        MimeMessage message = new MimeMessage(session);

        // Step 4: Set message fields
        message.setFrom(new InternetAddress(senderEmail, "My App"));
        message.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(recipientEmail));
        message.setSubject("Test Email from JavaMail");
        message.setText("Hello! This is a plain text email sent via JavaMail API.");

        // Step 5: Send
        Transport.send(message);
        System.out.println("Email sent successfully.");
    }
}
```

---

### 2. Send HTML Email
```java
MimeMessage message = new MimeMessage(session);
message.setFrom(new InternetAddress(senderEmail));
message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
message.setSubject("HTML Email");

// Set HTML body
String htmlBody = "<h1>Hello!</h1><p>This is an <b>HTML</b> email.</p>";
message.setContent(htmlBody, "text/html; charset=utf-8");

Transport.send(message);
```

---

### 3. Send Email with Attachment
```java
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;

MimeMessage message = new MimeMessage(session);
message.setFrom(new InternetAddress(senderEmail));
message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
message.setSubject("Email with Attachment");

// Part 1: text body
MimeBodyPart textPart = new MimeBodyPart();
textPart.setText("Please find the attached file.");

// Part 2: file attachment
MimeBodyPart attachPart = new MimeBodyPart();
attachPart.attachFile(new File("/path/to/document.pdf"));

// Combine into multipart
MimeMultipart multipart = new MimeMultipart();
multipart.addBodyPart(textPart);
multipart.addBodyPart(attachPart);

message.setContent(multipart);
Transport.send(message);
System.out.println("Email with attachment sent.");
```

---

### 4. Send to Multiple Recipients (TO, CC, BCC)
```java
message.addRecipient(Message.RecipientType.TO,  new InternetAddress("to@example.com"));
message.addRecipient(Message.RecipientType.CC,  new InternetAddress("cc@example.com"));
message.addRecipient(Message.RecipientType.BCC, new InternetAddress("bcc@example.com"));

// OR parse comma-separated list
message.setRecipients(Message.RecipientType.TO,
    InternetAddress.parse("a@x.com, b@y.com, c@z.com"));
```

---

### 5. Read Emails via IMAP (Gmail)
```java
import javax.mail.*;
import java.util.Properties;

public class ReadEmails {
    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");

        Session session = Session.getInstance(props);

        // Step 3-4: Get store and connect
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", "youremail@gmail.com", "your_app_password");

        // Step 5-6: Open INBOX
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        System.out.println("Total messages : " + inbox.getMessageCount());
        System.out.println("Unread messages: " + inbox.getUnreadMessageCount());

        // Step 7-8: Read last 5 messages
        int total = inbox.getMessageCount();
        Message[] messages = inbox.getMessages(total - 4, total);  // last 5

        for (Message msg : messages) {
            System.out.println("-----------------------------");
            System.out.println("From   : " + msg.getFrom()[0]);
            System.out.println("Subject: " + msg.getSubject());
            System.out.println("Date   : " + msg.getSentDate());

            // Read body
            if (msg.isMimeType("text/plain")) {
                System.out.println("Body: " + msg.getContent());
            } else if (msg.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) msg.getContent();
                for (int i = 0; i < mp.getCount(); i++) {
                    BodyPart part = mp.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        System.out.println("Body: " + part.getContent());
                    }
                }
            }
        }

        // Step 9: Close
        inbox.close(false);   // false = don't expunge (delete flagged messages)
        store.close();
    }
}
```

---

## SMTP Properties Reference

| Property | Value | Description |
|---|---|---|
| `mail.smtp.host` | `smtp.gmail.com` | SMTP server hostname |
| `mail.smtp.port` | `587` (TLS) / `465` (SSL) | SMTP port |
| `mail.smtp.auth` | `"true"` | Enable authentication |
| `mail.smtp.starttls.enable` | `"true"` | Upgrade plain connection to TLS (port 587) |
| `mail.smtp.ssl.enable` | `"true"` | Use SSL from start (port 465) |
| `mail.smtp.ssl.trust` | `"smtp.gmail.com"` | Trust this host's SSL cert (bypass strict validation) |
| `mail.smtp.connectiontimeout` | `"5000"` | Max ms to establish TCP connection |
| `mail.smtp.timeout` | `"5000"` | Max ms to wait for SMTP response |
| `mail.smtp.writetimeout` | `"5000"` | Max ms to wait while writing data |

---

## Common Mail Servers — SMTP / IMAP Settings

| Provider | SMTP Host | SMTP Port | IMAP Host | IMAP Port |
|---|---|---|---|---|
| Gmail | `smtp.gmail.com` | 587 (TLS) | `imap.gmail.com` | 993 |
| Outlook | `smtp.office365.com` | 587 | `outlook.office365.com` | 993 |
| Yahoo | `smtp.mail.yahoo.com` | 587 | `imap.mail.yahoo.com` | 993 |

---

## Common Exceptions

| Exception | Cause |
|---|---|
| `MessagingException` | Base exception for all JavaMail errors |
| `AuthenticationFailedException` | Wrong credentials or App Password not used |
| `SendFailedException` | One or more recipients were rejected by server |
| `AddressException` | Malformed email address |
| `NoSuchProviderException` | Invalid protocol string passed to `getStore()` or `getTransport()` |
| `FolderNotFoundException` | Requested folder doesn't exist on server |
| `IllegalStateException` | Operation on a closed folder or store |

---

## Important Notes

- JavaMail is **not bundled with JDK** — add `javax.mail` (Java EE) or `jakarta.mail` (Jakarta EE) as a Maven/Gradle dependency.
- **Gmail requires App Password** (not your Google account password) when 2FA is enabled — generate one at `myaccount.google.com/apppasswords`.
- Always use **TLS (port 587)** or **SSL (port 465)** — plain SMTP on port 25 is blocked by most providers for sending.
- `Transport.send()` (static) opens a new SMTP connection per call — for sending **bulk emails**, use `transport.connect()` + `transport.sendMessage()` in a loop to reuse the connection.
- `folder.close(true)` with `expunge=true` **permanently deletes** messages flagged with `Flags.Flag.DELETED` — use `false` if you just want to close without deleting.
- **IMAP vs POP3** — prefer IMAP for reading: it syncs with the server and doesn't remove messages. POP3 downloads and typically deletes from server.
- `session.setDebug(true)` prints the full SMTP/IMAP protocol conversation — invaluable for debugging auth or connection issues.
- `MimeMultipart("alternative")` is used when providing both `text/plain` and `text/html` versions of the same email — mail clients show whichever they support best.
- `message.setSentDate(new Date())` should be set explicitly — some servers reject messages without a `Date` header.