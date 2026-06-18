# JDBC Driver Types and Configuration

## Table of Contents
1. [JDBC Driver Types](#jdbc-driver-types)
2. [Driver Loading Mechanisms](#driver-loading-mechanisms)
3. [Driver Registration](#driver-registration)
4. [JDBC URL Formats](#jdbc-url-formats)
5. [Driver Configuration Properties](#driver-configuration-properties)
6. [Database-Specific Drivers](#database-specific-drivers)
7. [Driver Selection Criteria](#driver-selection-criteria)
8. [Common Configuration Issues](#common-configuration-issues)
9. [Important Notes](#important-notes)

---

## JDBC Driver Types

### Fig. 1: Driver Type Architecture & Characteristics

```
┌────────────────────────────────────────────────────────────────┐
│                  JDBC DRIVER TYPES                             │
└────────────────────────────────────────────────────────────────┘

TYPE 1: JDBC-ODBC BRIDGE
═══════════════════════════════════════════════════════════════════

Architecture:
    Java App → JDBC API → JDBC-ODBC Bridge → ODBC Driver → DB

Characteristics:
├─ Translates JDBC to ODBC calls
├─ Requires ODBC installation on client
├─ Requires native libraries
├─ Two-layer translation overhead
└─ Cross-platform support

Advantages:
  ✓ Access legacy systems via ODBC
  ✓ Works with many databases
  ✓ Established technology

Disadvantages:
  ✗ Slow (double translation)
  ✗ ODBC installation required
  ✗ Platform-dependent
  ✗ Not suitable for production
  ✗ REMOVED from Java 8+

Status: ❌ DEPRECATED & REMOVED
Usage: Legacy applications only (if using Java < 8)

─────────────────────────────────────────────────────────────────

TYPE 2: NATIVE API PARTIALLY JAVA
═══════════════════════════════════════════════════════════════════

Architecture:
    Java App → JDBC API → JNI Bridge → Native Libs → DB Protocol → DB

                      (C/C++ code on client)

Characteristics:
├─ Mixes Java and native code (JNI)
├─ Calls database vendor's native libraries
├─ Requires native libs on client machine
├─ Direct connection to database
└─ Platform-specific implementation

Advantages:
  ✓ Better performance than Type 1
  ✓ Direct database protocol
  ✓ Full database feature access
  ✓ Fewer translation layers

Disadvantages:
  ✗ Native libraries required
  ✗ Platform-dependent (OS-specific)
  ✗ Complex installation
  ✗ JNI overhead
  ✗ Distribution challenges
  ✗ Rarely used in practice

Examples:
  - Oracle OCI (Oracle Call Interface) Driver
  - Informix JSQL Driver
  - IBM DB2 CLI Driver

Status: ⚠️ LEGACY
Usage: Specialized scenarios with performance needs

─────────────────────────────────────────────────────────────────

TYPE 3: JDBC-MIDDLEWARE (Network Protocol)
═══════════════════════════════════════════════════════════════════

Architecture:
    Java App → JDBC API → Network Protocol → Middleware Server
                                                   ↓
                                          DB-Specific Lib → Database

Characteristics:
├─ Client connects to middleware server
├─ Middleware translates to DB protocol
├─ All Java on client
├─ Centralized database connectivity
└─ Uses generic network protocol

Advantages:
  ✓ Database-independent
  ✓ All Java (portable)
  ✓ Firewall-friendly (single connection point)
  ✓ Centralized connection management
  ✓ Can switch databases easily

Disadvantages:
  ✗ Middleware server required & maintained
  ✗ Extra network hop (performance hit)
  ✗ Single point of failure
  ✗ Additional licensing costs
  ✗ Installation complexity
  ✗ Server administration overhead

Examples:
  - IDS/ODBC Gateway
  - Informix WebDrive
  - Some enterprise application servers

Status: ⚠️ LEGACY
Usage: Enterprise environments with centralized DB access

─────────────────────────────────────────────────────────────────

TYPE 4: PURE JAVA DRIVER (Thin Driver) ⭐ RECOMMENDED
═══════════════════════════════════════════════════════════════════

Architecture:
    Java App → JDBC API → Pure Java Driver → DB Protocol → Database
                         (100% Java)        (TCP/IP)

Characteristics:
├─ Fully implemented in Java
├─ No native libraries required
├─ Direct database connection
├─ Cross-platform execution
├─ Platform-independent
└─ Database-specific protocol implementation

Advantages:
  ✓ 100% Pure Java (no native code)
  ✓ Cross-platform compatibility
  ✓ Easy deployment (just add JAR)
  ✓ No installation on client
  ✓ Best performance
  ✓ Direct connection (no middleware)
  ✓ Supports all database features
  ✓ Widely available
  ✓ Standard for modern applications
  ✓ Thread-safe
  ✓ Scalable

Disadvantages:
  ✗ Database-specific driver needed
  ✗ Driver for each database type

Examples:
  ✓ MySQL Connector/J (com.mysql.cj.jdbc.Driver)
  ✓ PostgreSQL (org.postgresql.Driver)
  ✓ Oracle JDBC (oracle.jdbc.driver.OracleDriver)
  ✓ SQL Server (com.microsoft.sqlserver.jdbc.SQLServerDriver)
  ✓ H2 (org.h2.Driver)
  ✓ Derby (org.apache.derby.jdbc.EmbeddedDriver)

Status: ✅ STANDARD & RECOMMENDED
Usage: All modern applications

─────────────────────────────────────────────────────────────────

COMPARISON TABLE:
═══════════════════════════════════════════════════════════════════

Aspect          │ Type 1   │ Type 2   │ Type 3    │ Type 4
────────────────┼──────────┼──────────┼───────────┼──────────
Java Only       │    ✗     │    ✗     │    ✓      │    ✓
Platform Indep. │    ✓     │    ✗     │    ✓      │    ✓
Performance     │   Poor   │  Medium  │  Medium   │  Excellent
Setup Ease      │   Hard   │   Hard   │   Medium  │   Easy
Production Use  │    ✗     │    ✗     │    ~      │    ✓
Deployability   │   Poor   │   Poor   │   Medium  │  Excellent
Scalability     │   Poor   │  Medium  │  Medium   │  Excellent
Standards       │    ✗     │    ✗     │    ✗      │    ✓
Status          │ Removed  │ Legacy   │  Legacy   │ Standard
```

---

## Driver Loading Mechanisms

### Fig. 2: Driver Class Loading Process

```
METHOD 1: EXPLICIT CLASS.FORNAME()
═══════════════════════════════════════════════════════════════

try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    // Loads driver class into memory
    // Calls static initializer
    // Driver auto-registers with DriverManager
    
    Connection conn = DriverManager.getConnection(url, user, pwd);
    
} catch (ClassNotFoundException e) {
    System.err.println("Driver not found!");
}

Flow:
    1. Class.forName() loads class bytecode
    2. JVM executes static initializer block
    3. Driver registers itself with DriverManager
    4. DriverManager can now find driver
    5. DriverManager.getConnection() locates driver
    6. Driver.connect() creates connection

Pros: ✓ Explicit control, ✓ Works with older JDBC
Cons: ✗ Extra code, ✗ Throws checked exception

─────────────────────────────────────────────────────────────────

METHOD 2: AUTOMATIC DISCOVERY (Java 6+)
═══════════════════════════════════════════════════════════════

Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/mydb",
    "user",
    "password"
);

Flow:
    1. DriverManager.getConnection() called
    2. Scans classpath for drivers
    3. Uses Service Provider Interface (SPI)
    4. Finds META-INF/services/java.sql.Driver files
    5. Auto-loads driver classes
    6. Driver registers with DriverManager
    7. Connection created

Pros: ✓ No explicit Class.forName(), ✓ Cleaner code
Cons: ✗ Less explicit, ✗ Classpath must be correct

─────────────────────────────────────────────────────────────────

METHOD 3: DATASOURCE (RECOMMENDED FOR POOLING)
═══════════════════════════════════════════════════════════════

Using javax.sql.DataSource:

DataSource ds = new MysqlDataSource();
((MysqlDataSource) ds).setURL("jdbc:mysql://localhost:3306/mydb");
((MysqlDataSource) ds).setUser("user");
((MysqlDataSource) ds).setPassword("password");

Connection conn = ds.getConnection();

Or with Connection Pool:

HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
config.setUsername("user");
config.setPassword("password");
config.setMaximumPoolSize(10);

HikariDataSource ds = new HikariDataSource(config);
Connection conn = ds.getConnection();

Pros: ✓ Connection pooling, ✓ Resource management, ✓ Performance
Cons: ✗ Requires pool library, ✗ More configuration

Driver loading happens automatically via DataSource.
```

---

## Driver Registration

### Fig. 3: Driver Registration & Discovery

```
DRIVER REGISTRATION FLOW
═══════════════════════════════════════════════════════════════

1. JAR File in Classpath
   └─ Contains: Driver class + META-INF/services/java.sql.Driver

2. JVM Startup
   └─ Loads CLASSPATH
   └─ (Driver class NOT loaded yet)

3. Class.forName("com.mysql.cj.jdbc.Driver")
   ├─ Loads driver class bytecode
   ├─ Executes static initializer:
   │  ├─ new DriverImpl()
   │  └─ DriverManager.registerDriver(this)
   └─ Driver now registered

4. DriverManager Registry
   ├─ Maintains list of registered drivers
   ├─ Each driver has:
   │  ├─ Implementation class
   │  ├─ Version info
   │  ├─ acceptsURL() method (URL matching)
   │  └─ connect() method
   └─ Searchable by URL pattern

5. DriverManager.getConnection(url, user, pwd)
   ├─ Iterates registered drivers
   ├─ Calls driver.acceptsURL(url)
   │  ├─ "jdbc:mysql://..." → MySQL driver accepts
   │  ├─ "jdbc:postgresql://..." → PostgreSQL driver accepts
   │  └─ Selects first matching driver
   ├─ Calls driver.connect()
   └─ Returns Connection

─────────────────────────────────────────────────────────────────

DRIVER CLASS STATIC INITIALIZER
═══════════════════════════════════════════════════════════════

Example: MySQL Driver

public class Driver extends NonRegisteringDriver 
    implements java.sql.Driver {
    
    static {  // ← Executes when class is loaded
        try {
            java.sql.DriverManager.registerDriver(
                new Driver()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error registering driver", e);
        }
    }
}

When Class.forName("com.mysql.cj.jdbc.Driver") runs:
    1. JVM loads Driver.class bytecode
    2. Executes static {} block
    3. Creates new Driver instance
    4. Calls DriverManager.registerDriver()
    5. Driver is now available for connections

─────────────────────────────────────────────────────────────────

SERVICE PROVIDER INTERFACE (SPI)
═══════════════════════════════════════════════════════════════

File: META-INF/services/java.sql.Driver
(Inside driver JAR)

Contents:
    com.mysql.cj.jdbc.Driver

When DriverManager.getConnection() is called:
    1. Searches classpath for META-INF/services/java.sql.Driver
    2. Finds all registered driver class names
    3. Loads each class automatically
    4. Static initializer registers driver
    5. Selects appropriate driver for URL
```

---

## JDBC URL Formats

### Fig. 4: JDBC URL Components & Database-Specific Formats

```
STANDARD JDBC URL FORMAT
═══════════════════════════════════════════════════════════════

jdbc:subprotocol:subname[?properties]
 ↑    ↑           ↑         ↑
 │    │           │         └─ Optional properties
 │    │           └─ Database-specific locator
 │    └─ Driver type identifier
 └─ Protocol prefix (always "jdbc")

Components:
├─ Protocol: "jdbc" (fixed)
├─ Subprotocol: Database type (mysql, postgresql, oracle, etc.)
├─ Subname: Host, port, database, SID
└─ Properties: Key=value pairs (?key1=value1&key2=value2)

─────────────────────────────────────────────────────────────────

DATABASE-SPECIFIC URL FORMATS
═══════════════════════════════════════════════════════════════

MySQL (Type 4 - Connector/J)
────────────────────────────
Format: jdbc:mysql://[host]:[port]/[database][?properties]

Examples:
  jdbc:mysql://localhost:3306/mydb
  jdbc:mysql://localhost:3306/mydb?useSSL=false
  jdbc:mysql://server.example.com:3306/production?serverTimezone=UTC
  jdbc:mysql://localhost:3306/mydb?allowPublicKeyRetrieval=true

URL Encoding: No special encoding needed for standard characters

─────────────────────────────────────────────────────────────────

PostgreSQL (Type 4)
───────────────────
Format: jdbc:postgresql://[host]:[port]/[database]

Examples:
  jdbc:postgresql://localhost:5432/mydb
  jdbc:postgresql://localhost:5432/mydb?sslmode=require
  jdbc:postgresql://server.example.com/production
  jdbc:postgresql://localhost/mydb?user=postgres&password=secret

Default Port: 5432

─────────────────────────────────────────────────────────────────

Oracle (Type 4 - Thin Driver)
──────────────────────────────
Format: jdbc:oracle:thin:@[host]:[port]:[SID]
     or jdbc:oracle:thin:@//[host]:[port]/[service_name]

Examples:
  jdbc:oracle:thin:@localhost:1521:ORCL
  jdbc:oracle:thin:@//localhost:1521/orcl
  jdbc:oracle:thin:@(description=...)  [TNS format]

Default Port: 1521

─────────────────────────────────────────────────────────────────

Microsoft SQL Server
─────────────────────
Format: jdbc:sqlserver://[host]:[port];databaseName=[db]

Examples:
  jdbc:sqlserver://localhost:1433;databaseName=mydb
  jdbc:sqlserver://localhost:1433;databaseName=mydb;integratedSecurity=true
  jdbc:sqlserver://sqlserver.example.com;databaseName=production

Default Port: 1433

─────────────────────────────────────────────────────────────────

SQLite (Embedded Database)
──────────────────────────
Format: jdbc:sqlite:[file_path]

Examples:
  jdbc:sqlite:/path/to/database.db
  jdbc:sqlite:C:\\databases\\mydb.db  [Windows]
  jdbc:sqlite::memory:  [In-memory database]

No server required (file-based)

─────────────────────────────────────────────────────────────────

H2 (In-Memory & Embedded)
──────────────────────────
Format: jdbc:h2:[memory|file_path|tcp://host:port/path]

Examples:
  jdbc:h2:mem:test  [In-memory]
  jdbc:h2:/path/to/database  [File-based]
  jdbc:h2:tcp://localhost:9092/mydb  [Server mode]

Default Port: 9092

─────────────────────────────────────────────────────────────────

Derby (Embedded Apache Derby)
──────────────────────────────
Format: jdbc:derby:[path/database][;properties]

Examples:
  jdbc:derby:mydb;create=true
  jdbc:derby:/path/to/mydb
  jdbc:derby:memory:testdb;create=true
```

---

## Driver Configuration Properties

### Fig. 5: Connection Configuration Properties

```
COMMON PROPERTIES (All Databases)
═══════════════════════════════════════════════════════════════

user / username
├─ Database user account
├─ Example: "root", "postgres", "sa"
└─ Connection string: url?user=root&password=secret

password
├─ User account password
├─ Never hardcode in production
└─ Use environment variables or config files

connectTimeout
├─ Connection establishment timeout (seconds)
├─ Default: 0 (infinite)
└─ Example: connectTimeout=10

loginTimeout
├─ Login attempt timeout (seconds)
├─ Set via DriverManager.setLoginTimeout()
└─ Default: 0 (infinite)

autoReconnect
├─ Automatically reconnect if connection lost
├─ Default: false
├─ Example: autoReconnect=true
└─ Note: Can mask underlying issues

─────────────────────────────────────────────────────────────────

MYSQL-SPECIFIC PROPERTIES
═══════════════════════════════════════════════════════════════

jdbc:mysql://host:port/db?property1=value1&property2=value2

useSSL
├─ Use SSL for connection
├─ Default: true (Java 8+)
├─ Example: useSSL=false  [for development]
└─ Production: useSSL=true

serverTimezone
├─ Set timezone for server
├─ Default: none (may cause warning)
├─ Example: serverTimezone=UTC
└─ Required for Java 8+ with certain MySQL versions

allowPublicKeyRetrieval
├─ Allow public key retrieval
├─ Default: false
├─ Example: allowPublicKeyRetrieval=true
└─ Set true if using MySQL 8+ with RSA key authentication

characterEncoding
├─ Character set encoding
├─ Default: UTF-8
├─ Example: characterEncoding=utf8mb4
└─ Important for Unicode support

maxPoolSize / minPoolSize
├─ Connection pool size
├─ Default: varies by driver
├─ Example: maxPoolSize=10
└─ Tune based on application needs

cachePrepStmts
├─ Cache prepared statements
├─ Default: false
├─ Example: cachePrepStmts=true
└─ Performance improvement

prepStmtCacheSize
├─ Number of prepared statements to cache
├─ Default: 250
├─ Example: prepStmtCacheSize=500
└─ Used with cachePrepStmts=true

─────────────────────────────────────────────────────────────────

POSTGRESQL-SPECIFIC PROPERTIES
═══════════════════════════════════════════════════════════════

jdbc:postgresql://host:port/db?property1=value1

sslmode
├─ SSL mode: disable, allow, prefer, require
├─ Default: prefer
├─ Example: sslmode=require
└─ Security setting

application_name
├─ Application identifier
├─ Appears in pg_stat_activity
├─ Example: application_name=MyApp
└─ For monitoring/debugging

loggerLevel
├─ Driver logging level (OFF, DEBUG, INFO, etc.)
├─ Default: OFF
├─ Example: loggerLevel=DEBUG
└─ For troubleshooting

keepalives
├─ Enable TCP keepalive
├─ Default: false
├─ Example: keepalives=true
└─ Detects broken connections

─────────────────────────────────────────────────────────────────

ORACLE-SPECIFIC PROPERTIES
═══════════════════════════════════════════════════════════════

Connection String:
  jdbc:oracle:thin:@host:port:SID

v$session.process
├─ OS process ID
├─ Available in Oracle V$SESSION

db_recovery_file_dest
├─ Archive destination
├─ For backup/recovery

Control File Properties
├─ Read from tnsnames.ora
├─ Control file location

─────────────────────────────────────────────────────────────────

SQL SERVER-SPECIFIC PROPERTIES
═══════════════════════════════════════════════════════════════

jdbc:sqlserver://host:port;databaseName=db;property=value

integratedSecurity
├─ Windows authentication
├─ Default: false
├─ Example: integratedSecurity=true
└─ Requires native library on Windows

authentication
├─ SQL Server authentication type
├─ Values: SqlPassword, ActiveDirectoryPassword, etc.
├─ Default: SqlPassword
└─ For AD/Azure integration

encrypt
├─ Encrypt connection
├─ Default: false
├─ Example: encrypt=true
└─ Requires trustServerCertificate=true if self-signed

trustServerCertificate
├─ Trust server certificate
├─ Default: false
├─ Example: trustServerCertificate=true
└─ For self-signed certificates

hostNameInCertificate
├─ Expected hostname in certificate
├─ For hostname verification
└─ Example: hostNameInCertificate=*.example.com
```

---

## Database-Specific Drivers

### Fig. 6: Driver Dependency Examples

```
MAVEN DEPENDENCIES
═══════════════════════════════════════════════════════════════

MySQL Connector/J (Type 4)
──────────────────────────
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

Driver Class: com.mysql.cj.jdbc.Driver
Default Port: 3306
URL Pattern: jdbc:mysql://host:port/database

─────────────────────────────────────────────────────────────────

PostgreSQL JDBC Driver (Type 4)
───────────────────────────────
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.6.0</version>
</dependency>

Driver Class: org.postgresql.Driver
Default Port: 5432
URL Pattern: jdbc:postgresql://host:port/database

─────────────────────────────────────────────────────────────────

Oracle JDBC Driver (Type 4 Thin)
────────────────────────────────
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <version>21.8.0.0</version>
</dependency>

Driver Class: oracle.jdbc.driver.OracleDriver
Default Port: 1521
URL Pattern: jdbc:oracle:thin:@host:port:SID

─────────────────────────────────────────────────────────────────

SQL Server JDBC Driver (Type 4)
──────────────────────────────
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>12.2.0.jre11</version>
</dependency>

Driver Class: com.microsoft.sqlserver.jdbc.SQLServerDriver
Default Port: 1433
URL Pattern: jdbc:sqlserver://host:port;databaseName=db

─────────────────────────────────────────────────────────────────

H2 Database (Type 4 - Embedded/In-Memory)
───────────────────────────────────────────
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.1.214</version>
    <scope>test</scope>
</dependency>

Driver Class: org.h2.Driver
Default Port: 9092
URL Pattern: jdbc:h2:mem:test (in-memory)
URL Pattern: jdbc:h2:file:/path/db (file-based)

─────────────────────────────────────────────────────────────────

SQLite JDBC Driver (Type 4 - Embedded)
───────────────────────────────────────
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.43.0.0</version>
</dependency>

Driver Class: org.sqlite.JDBC
No Server Required (File-based)
URL Pattern: jdbc:sqlite:/path/to/db.db

─────────────────────────────────────────────────────────────────

Apache Derby (Type 4 - Embedded)
────────────────────────────────
<dependency>
    <groupId>org.apache.derby</groupId>
    <artifactId>derbyclient</artifactId>
    <version>10.16.1.1</version>
</dependency>

Driver Class: org.apache.derby.jdbc.ClientDriver
Default Port: 1527
URL Pattern: jdbc:derby://host:port/database
```

---

## Driver Selection Criteria

### Fig. 7: Driver Selection Decision Tree

```
Choose JDBC Driver
═══════════════════════════════════════════════════════════════

                    ┌─ Database Type?
                    │
        ┌───────────┼────────┬──────────┬───────────┐
        │           │        │          │           │
      MySQL      PostgreSQL Oracle   SQL Server   SQLite
        │           │        │          │           │
        ▼           ▼        ▼          ▼           ▼
    Connector/J  PostgreSQL Oracle  SQL Server   sqlite-jdbc
    Type 4      JDBC Driver JDBC     JDBC         Type 4
    Type 4      Type 4      Type 4    Type 4

                    ┌─ Deployment Environment?
                    │
        ┌───────────┼─────────────┬──────────┐
        │           │             │          │
    Web App    Embedded App    Desktop    Mobile
   (Server)    (Standalone)    App        App
        │           │             │          │
        ▼           ▼             ▼          ▼
    Pool Mgmt   FileDB or    Local DB   Remote/
    (HikariCP)  H2/Derby      SQLite     Cloud DB

─────────────────────────────────────────────────────────────────

SELECTION CRITERIA
═════════════════════════════════════════════════════════════

1. Database System
   ├─ MySQL → MySQL Connector/J (com.mysql.cj.jdbc.Driver)
   ├─ PostgreSQL → PostgreSQL JDBC (org.postgresql.Driver)
   ├─ Oracle → Oracle JDBC Thin (oracle.jdbc.driver.OracleDriver)
   ├─ SQL Server → MSSQL JDBC (com.microsoft.sqlserver.jdbc.SQLServerDriver)
   └─ Other → Find corresponding Type 4 driver

2. Performance Requirements
   ├─ High throughput → Type 4 (pure Java)
   ├─ Connection pooling needed → HikariCP + Type 4
   └─ Complex queries → Use PreparedStatement caching

3. Deployment Model
   ├─ Server application → Type 4 + connection pool
   ├─ Embedded database → H2, Derby, SQLite
   ├─ Desktop application → Local database + Type 4
   └─ Distributed system → Type 4 + middleware optional

4. Security Requirements
   ├─ SSL/TLS needed → Configure useSSL/sslmode properties
   ├─ Certificate validation → Set trustServerCertificate
   ├─ Authentication → Windows/AD/SQL auth options
   └─ Encryption → Enable encrypt property

5. Maintenance & Support
   ├─ Open source preference → PostgreSQL, MySQL
   ├─ Commercial support needed → Oracle, SQL Server
   ├─ Zero maintenance → H2, SQLite (embedded)
   └─ Enterprise requirement → Use enterprise drivers

6. Development vs Production
   ├─ Development → H2/SQLite (no server needed)
   ├─ Testing → H2 in-memory (fast, isolated)
   └─ Production → Real database + Type 4 driver
```

---

## Common Configuration Issues

### Issue Resolution Table

| Issue | Cause | Solution |
|---|---|---|
| **ClassNotFoundException** | Driver JAR not in classpath | Add driver JAR to pom.xml or classpath |
| **No suitable driver found** | URL format incorrect | Verify JDBC URL format for database |
| **Connection refused** | Database server not running | Start database server, verify host/port |
| **Timeout errors** | Connection slow/hanging | Increase connectTimeout property |
| **Authentication failed** | Wrong credentials | Verify user/password in connection string |
| **Timezone errors** | MySQL 8 timezone mismatch | Add serverTimezone=UTC to URL |
| **SSL errors** | Certificate validation failed | Set useSSL=false for development |
| **Connection pool exhausted** | All connections in use | Increase maxPoolSize, check for leaks |
| **Memory leaks** | Unclosed connections/statements | Use try-with-resources for cleanup |
| **Character encoding issues** | Wrong charset specified | Set characterEncoding=utf8mb4 |
| **Slow queries** | Missing query plan caching | Enable cachePrepStmts=true |
| **Out of memory** | ResultSet too large | Use fetch size to limit results per fetch |

---

## Important Notes

### ✓ Best Practices

1. **Always Use Type 4 Drivers**
   - Pure Java implementation
   - Best performance and portability
   - No native dependencies
   - Standard for all modern applications

2. **URL Format Consistency**
   - Use standardized JDBC URL format
   - Include all required properties
   - Comment complex configuration

3. **Property Management**
   - Never hardcode sensitive properties
   - Use configuration files or environment variables
   - Store passwords securely (encrypted)

4. **Connection Pooling**
   - Use DataSource with pooling in production
   - Not just for single connections
   - Use HikariCP (industry standard)

5. **Driver Version Management**
   - Use latest stable driver version
   - Check compatibility with Java version
   - Use dependency management (Maven/Gradle)

6. **Error Handling**
   - Catch SQLException specifically
   - Log SQL state and error code
   - Provide meaningful error messages

### ⚠ Common Mistakes

- Hardcoding database URLs in code
- Not using connection pooling
- Forgetting to close connections/statements
- Using old JDBC driver versions
- Mixing multiple driver versions
- Not validating connection properties
- Using String concatenation for SQL (SQL injection risk)

### 🔧 Quick Reference: Driver & URL Combinations

```
MySQL 8.0+
  Driver: com.mysql.cj.jdbc.Driver
  URL: jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&useSSL=false

PostgreSQL 12+
  Driver: org.postgresql.Driver
  URL: jdbc:postgresql://localhost:5432/mydb

Oracle 19c+
  Driver: oracle.jdbc.driver.OracleDriver
  URL: jdbc:oracle:thin:@localhost:1521:ORCL

SQL Server 2019+
  Driver: com.microsoft.sqlserver.jdbc.SQLServerDriver
  URL: jdbc:sqlserver://localhost:1433;databaseName=mydb

Development/Testing
  Driver: org.h2.Driver
  URL: jdbc:h2:mem:test (or) jdbc:h2:~/test
```

---
