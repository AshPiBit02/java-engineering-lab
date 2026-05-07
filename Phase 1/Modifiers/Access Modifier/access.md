# 🔒 Access Modifiers in Java


---

## 📌 What are Access Modifiers?

Access modifiers define the **visibility and accessibility** of classes, methods, and fields from other parts of the program.

Java has **4 access modifiers:**

| Modifier | Keyword | Scope |
|----------|---------|-------|
| Public | `public` | Accessible from everywhere |
| Protected | `protected` | Same package + subclasses |
| Default | *(no keyword)* | Same package only |
| Private | `private` | Same class only |

---

## 📊 Access Levels at a Glance

| Modifier | Same Class | Same Package | Subclass (diff pkg) | Everywhere |
|----------|:----------:|:------------:|:-------------------:|:----------:|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `default` | ✅ | ✅ | ❌ | ❌ |j
| `private` | ✅ | ❌ | ❌ | ❌ |

---

## 🔹 1. `public`
Accessible from **any class, any package**.

```java
public class Student {
    public String name;       // accessible everywhere
    public void display() { System.out.println(name); }
}
```

---

## 🔹 2. `protected`
Accessible within the **same package** and by **subclasses** (even in different packages).

```java
class Animal {
    protected String type = "Mammal";   // accessible in subclasses
}

class Dog extends Animal {
    void show() { System.out.println(type); }  // ✅ works
}
```

---

## 🔹 3. `default` *(package-private)*
No keyword is written. Accessible **only within the same package**.

```java
class Helper {
    int value = 10;        // default access
    void display() { }     // default access
}
```

> 💡 If no modifier is written, Java assigns **default** automatically.

---

## 🔹 4. `private`
Accessible **only within the same class**. Most restrictive.

```java
class BankAccount {
    private double balance;    // hidden from outside

    public double getBalance() {   // controlled access via getter
        return balance;
    }
}
```

> 💡 Use `private` for fields and expose them via `public` **getters/setters** — this is the principle of **Encapsulation**.

---

## 🏷️ Where Can Modifiers Be Applied?

| Target | `public` | `protected` | `default` | `private` |
|--------|:--------:|:-----------:|:---------:|:---------:|
| Class (top-level) | ✅ | ❌ | ✅ | ❌ |
| Class (inner) | ✅ | ✅ | ✅ | ✅ |
| Field | ✅ | ✅ | ✅ | ✅ |
| Method | ✅ | ✅ | ✅ | ✅ |
| Constructor | ✅ | ✅ | ✅ | ✅ |

> ⚠️ A **top-level class** can only be `public` or `default` — never `private` or `protected`.

---

## 🆚 C++ vs Java

| Feature | C++ | Java |
|---------|-----|------|
| Modifier syntax | Block-style (`public:`) | Per-member (`public void`) |
| Default access (class) | `private` | `default` (package-private) |
| `protected` | Same class + subclass | Same package + subclass |
| Package-level access | ❌ No concept | ✅ `default` modifier |
| `friend` keyword | ✅ Exists | ❌ Not available |

---

## 💡 Summary

```
private  ──►  default  ──►  protected  ──►  public
(least)                                    (most)
        increasing accessibility →
```

| Modifier | Best Used For |
|----------|--------------|
| `private` | Fields (encapsulation), internal helpers |
| `default` | Package-internal utilities |
| `protected` | Inherited members for subclasses |
| `public` | APIs, constructors, main methods |

---
