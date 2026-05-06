# 📦 Packages in Java


---

## 📌 What is a Package?

A **package** is a **namespace** that organizes a group of related classes, interfaces, and sub-packages — similar to folders in a file system.

> Think of a package like a **folder on your computer** — it groups related files together and prevents naming conflicts.

```
┌──────────────────────────────────────────────────────────┐
│                  Package = Folder Analogy                │
│                                                          │
│   📁 com/                                                |
│     📁 university/                                       │
│       📁 student/                                        │
│         📄 Student.java       ← class                    │
│         📄 Enrollment.java    ← class                    │
│       📁 teacher/                                        │
│         📄 Teacher.java       ← class                    │
│         📄 Subject.java       ← class                    │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Package Structure as Folder Hierarchy**

---

## 🎯 Why Use Packages?

| Purpose | Description |
|---------|-------------|
| 🗂️ **Organization** | Groups related classes and interfaces together |
| 🔒 **Access Control** | Controls visibility using access modifiers |
| 🚫 **Naming Conflict** | Two classes with the same name can exist in different packages |
| ♻️ **Reusability** | Classes in packages can be reused across projects |
| 🛡️ **Encapsulation** | Hides internal implementation from outside packages |

---

## 🗂️ Types of Packages

```
┌──────────────────────────────────────────────────────────┐
│                    Types of Packages                     │
│                                                          │
│         ┌──────────────────────────────────┐             │
│         │          Java Packages           │             │
│         └────────────────┬─────────────────┘             │
│                          │                               │
│           ┌──────────────┴──────────────┐                │
│           │                             │                │
│  ┌────────▼────────┐          ┌─────────▼──────────┐     │
│  │  Built-in       │          │   User-defined     │     │
│  │  Packages       │          │   Packages         │     │
│  │  (java.*)       │          │   (e.g.: com.xyz)  │     │
│  └─────────────────┘          └────────────────────┘     │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Types of Packages**

---

## 1. 📚 Built-in Packages (Java API Packages)

Java provides a rich set of ready-to-use packages as part of the **Java Standard Library (Java SE)**:

| Package | Contents | Common Classes |
|---------|----------|----------------|
| `java.lang` | Core language classes | `String`, `Math`, `Object`, `System` |
| `java.util` | Utility classes & collections | `ArrayList`, `HashMap`, `Scanner`, `Date` |
| `java.io` | Input/Output operations | `File`, `FileReader`, `BufferedReader` |
| `java.net` | Networking | `Socket`, `URL`, `HttpURLConnection` |
| `java.awt` | GUI components (legacy) | `Frame`, `Button`, `Color` |
| `javax.swing` | Modern GUI components | `JFrame`, `JButton`, `JLabel` |
| `java.sql` | Database connectivity (JDBC) | `Connection`, `Statement`, `ResultSet` |
| `java.math` | Math operations | `BigInteger`, `BigDecimal` |

> 💡 `java.lang` is the **only package imported automatically** — you never need to write `import java.lang.*`.

---

## 2. 🛠️ User-defined Packages

Packages that **you create yourself** to organize your own code.

### Declaring a Package

The `package` statement must be the **very first line** of the source file (before imports and class definition):

```java
package com.university.student;

public class Student {
    String name;
    int rollNo;

    public void display() {
        System.out.println(name + " - " + rollNo);
    }
}
```

> ⚠️ **Rule** — The package name must match the **folder/directory structure** exactly.

---

## 📁 Package Naming Convention

Java follows a **reverse domain name** convention to ensure globally unique package names:

```
┌──────────────────────────────────────────────────────────┐
│              Package Naming Convention                   │
│                                                          │
│   com  .  university  .  department  .  classname        │
│    │          │              │              │            │
│  domain    org name      sub-module      feature         │
│                                                          │
│   Examples:                                              │
│   com.google.gson                                        │
│   org.apache.commons                                     │
│   com.university.student                                 │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Package Naming Convention**

| Rule | Example |
|------|---------|
| All **lowercase** | `com.example.util` ✅ |
| Use **reverse domain** name | `com.google.maps` |
| Separate levels with `.` | `java.util.regex` |
| No hyphens or spaces | `com.my-app` ❌ |

---

## 📥 Importing Packages

To use a class from another package, you must **import** it.

### Three Ways to Import

#### 🔹 1. Import a specific class
```java
import java.util.ArrayList;   // only ArrayList is imported

ArrayList<String> list = new ArrayList<>();
```

#### 🔹 2. Import all classes from a package (wildcard)
```java
import java.util.*;   // all classes in java.util

ArrayList<String> list = new ArrayList<>();
Scanner sc = new Scanner(System.in);
```

#### 🔹 3. Fully Qualified Name (no import needed)
```java
java.util.ArrayList<String> list = new java.util.ArrayList<>();
```

> 💡 Prefer **specific imports** over wildcards — keeps code readable and avoids ambiguity.

```
┌──────────────────────────────────────────────────────────┐
│                    Import Resolution                     │
│                                                          │
│   import java.util.ArrayList;                            │
│          │      │       │                                │
│       domain  module  class                              │
│                                                          │
│   JVM looks in:                                          │
│   java/ → util/ → ArrayList.class ✅                    │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 4 — How Import Resolves to a Class File**

---

## ⚡ Static Import

Allows importing **static members** (fields and methods) of a class directly — without the class name prefix:

```java
// without static import
System.out.println(Math.sqrt(16));   // 4.0
System.out.println(Math.PI);         // 3.14159

// with static import
import static java.lang.Math.*;

System.out.println(sqrt(16));        // 4.0
System.out.println(PI);              // 3.14159
```

> ⚠️ Use sparingly — overuse makes code harder to read (unclear where the method comes from).

---

## 🏗️ Creating & Using a User-defined Package

### Step 1 — Create the package folder structure

```
project/
├── com/
│   └── university/
│       └── student/
│           └── Student.java
└── Main.java
```

### Step 2 — Declare the package in `Student.java`

```java
package com.university.student;

public class Student {
    public String name;
    public int rollNo;

    public void display() {
        System.out.println(name + " | Roll: " + rollNo);
    }
}
```

### Step 3 — Import and use in `Main.java`

```java
import com.university.student.Student;

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        s.name   = "Aasii";
        s.rollNo = 101;
        s.display();
    }
}
```

### Step 4 — Compile and Run

```bash
# Compile (from project root)
javac com/university/student/Student.java
javac Main.java

# Run
java Main
```

---

## 🔒 Package-level Access Control

Access modifiers behave differently across packages:

```
┌──────────────────────────────────────────────────────────────┐
│                  Access Across Packages                      │
│                                                              │
│   Package A                      Package B                   │
│   ┌─────────────────┐            ┌─────────────────┐         │
│   │ class Foo       │            │ class Bar       │         │
│   │                 │            │                 │         │
│   │ public    m1() ─┼────────────┼──► accessible   │         │
│   │ protected m2() ─┼── subclass ┼──► accessible   │         │
│   │ default   m3() ─┼────────────┼──► ❌ blocked   │         │
│   │ private   m4()  │            │    ❌ blocked   │         │
│   └─────────────────┘            └─────────────────┘         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Access Modifiers Across Packages**

| Modifier | Same Package | Different Package | Subclass (diff pkg) |
|----------|:----------:|:-----------------:|:-------------------:|
| `public` | ✅ | ✅ | ✅ |
| `protected` | ✅ | ❌ | ✅ |
| `default` | ✅ | ❌ | ❌ |
| `private` | ❌ | ❌ | ❌ |

---

## 🔗 Sub-packages

A **sub-package** is a package inside another package. They are **independent** — importing a parent package does NOT import its sub-packages.

```
java.util          ← package
java.util.regex    ← sub-package (must be imported separately)
java.util.stream   ← sub-package (must be imported separately)
```

```java
import java.util.*;         // does NOT include java.util.regex
import java.util.regex.*;   // must import explicitly
```

```
┌──────────────────────────────────────────────────────────┐
│                    Sub-package Structure                 │
│                                                          │
│   java/                                                  │
│   └── util/                    ← java.util               │
│       ├── ArrayList.class                                │
│       ├── HashMap.class                                  │
│       ├── regex/               ← java.util.regex         │
│       │   ├── Pattern.class                              │
│       │   └── Matcher.class                              │
│       └── stream/              ← java.util.stream        │
│           └── Stream.class                               │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Sub-package Directory Structure**

---

## 🌐 CLASSPATH & Packages

The **classpath** tells the JVM where to find your package folders:

```bash
# If your packages are in /myproject/src
javac -cp /myproject/src Main.java
java  -cp /myproject/src Main
```

```
┌──────────────────────────────────────────────────────────┐
│         Package Resolution via CLASSPATH                 │
│                                                          │
│   import com.university.student.Student;                 │
│                 │                                        │
│                 ▼                                        │
│   CLASSPATH root  +  com/university/student/Student.class│
│                 │                                        │
│                 ▼                                        │
│        JVM loads Student.class  ✅                       │
└──────────────────────────────────────────────────────────┘
```

> **Fig. 7 — CLASSPATH Package Resolution**

---

## 🆚 Java vs C++ — Packages & Namespaces

| Feature | Java (Packages) | C++ (Namespaces) |
|---------|-----------------|-----------------|
| Keyword | `package` | `namespace` |
| Declaration | Top of file | Wraps code block |
| Import | `import pkg.Class` | `using namespace std` |
| Maps to filesystem | ✅ Yes (folders) | ❌ No |
| Access control | ✅ Yes (default modifier) | ❌ No |
| Nested | Sub-packages | Nested namespaces |
| Standard library | `java.lang`, `java.util` etc. | `std::` |

> 🆚 **Key Difference** — In C++, namespaces are purely for **name scoping** and don't map to any folder structure. In Java, packages **must match** the directory/folder structure on disk — the compiler enforces this.

---

## ✅ Best Practices

- Always use **reverse domain name** convention (`com.companyname.module`)
- Keep package names **all lowercase**
- Group **related classes** in the same package
- Avoid using **wildcard imports** (`import java.util.*`) in large projects
- Use **static imports** sparingly
- Match your **folder structure** exactly to your package name
- Use **sub-packages** to further organize large codebases

---

## 💡 Summary

| Concept | Key Point |
|---------|-----------|
| **Package** | Namespace to organize related classes |
| **Built-in** | Provided by Java API (`java.lang`, `java.util` etc.) |
| **User-defined** | Created by the developer using `package` keyword |
| **`import`** | Brings a class or package into scope |
| **Static import** | Imports static members directly |
| **Sub-package** | Package inside a package — imported separately |
| **Naming** | Reverse domain, all lowercase, matches folder structure |
| **Access** | `default` modifier restricts access to same package only |
| **CLASSPATH** | JVM uses it to locate package directories |

---
