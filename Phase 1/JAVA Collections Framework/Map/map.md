# 🗺️ Map in Java

> *Part of Java Collections Framework — `java.util`*

---

## 📌 What is a Map?

A **Map** stores data as **key-value pairs**. Each key is **unique** — no two entries can have the same key. Values can be duplicated.

| Property | Value |
|----------|-------|
| Structure | Key → Value pairs |
| Duplicate keys | ❌ Not allowed |
| Duplicate values | ✅ Allowed |
| Null keys | Depends on implementation |
| Part of Collection interface | ❌ Separate hierarchy |

```
Key        Value
────────────────
"name"  →  "Aasii"
"age"   →  21
"city"  →  "Pokhara"
```

---

## 🗂️ Map Implementations

| Class | Order | Null Key | Thread-Safe | Best For |
|-------|-------|----------|-------------|---------|
| `HashMap` | ❌ No order | ✅ One | ❌ | Fast key-based lookup |
| `LinkedHashMap` | ✅ Insertion order | ✅ One | ❌ | Ordered key-value pairs |
| `TreeMap` | ✅ Sorted by key | ❌ | ❌ | Sorted keys |
| `Hashtable` | ❌ No order | ❌ | ✅ | Legacy thread-safe map |
| `ConcurrentHashMap` | ❌ No order | ❌ | ✅ | Modern thread-safe map |

---

## ⚙️ Common Map Methods

| Method | Description | Example |
|--------|-------------|---------|
| `put(k, v)` | Inserts / updates key-value pair | `map.put("name", "Aasii")` |
| `get(k)` | Returns value for key, `null` if not found | `map.get("name")` |
| `getOrDefault(k, def)` | Returns value or default if key absent | `map.getOrDefault("x", 0)` |
| `remove(k)` | Removes entry by key | `map.remove("name")` |
| `containsKey(k)` | Checks if key exists | `map.containsKey("age")` |
| `containsValue(v)` | Checks if value exists | `map.containsValue(21)` |
| `size()` | Number of key-value pairs | `map.size()` |
| `isEmpty()` | Checks if map is empty | `map.isEmpty()` |
| `clear()` | Removes all entries | `map.clear()` |
| `keySet()` | Returns all keys as a `Set` | `map.keySet()` |
| `values()` | Returns all values as a `Collection` | `map.values()` |
| `entrySet()` | Returns all key-value pairs as `Set<Entry>` | `map.entrySet()` |
| `putIfAbsent(k, v)` | Inserts only if key not already present | `map.putIfAbsent("x", 5)` |
| `replace(k, v)` | Replaces value for existing key | `map.replace("age", 22)` |

---

## 🔹 HashMap

```java
import java.util.HashMap;

Map<String, Integer> map = new HashMap<>();

map.put("Aasii", 21);
map.put("Ram",   19);
map.put("Sita",  20);
map.put("Aasii", 22);    // updates existing key

System.out.println(map.get("Aasii"));          // 22
System.out.println(map.containsKey("Ram"));    // true
System.out.println(map.size());                // 3

map.remove("Sita");
```

### Iterating a HashMap

```java
// 1. Iterate keys
for (String key : map.keySet()) {
    System.out.println(key + " → " + map.get(key));
}

// 2. Iterate entries (most efficient)
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " → " + entry.getValue());
}

// 3. Iterate values only
for (int value : map.values()) {
    System.out.println(value);
}
```

> 💡 `HashMap` uses **hashing** internally — O(1) average for put, get, remove.

---

## 🔹 LinkedHashMap

```java
import java.util.LinkedHashMap;

Map<String, Integer> map = new LinkedHashMap<>();

map.put("Banana", 3);
map.put("Apple",  5);
map.put("Mango",  2);

System.out.println(map);
// {Banana=3, Apple=5, Mango=2}  ← insertion order maintained ✅
```

> 💡 Use `LinkedHashMap` when you need **HashMap performance + insertion order**.

---

## 🔹 TreeMap

```java
import java.util.TreeMap;

TreeMap<String, Integer> map = new TreeMap<>();

map.put("Banana", 3);
map.put("Apple",  5);
map.put("Mango",  2);

System.out.println(map);
// {Apple=5, Banana=3, Mango=2}  ← sorted by key ✅

System.out.println(map.firstKey());   // Apple
System.out.println(map.lastKey());    // Mango
```

### Extra TreeMap Methods

| Method | Description |
|--------|-------------|
| `firstKey()` | Smallest key |
| `lastKey()` | Largest key |
| `headMap(k)` | Entries with keys strictly less than `k` |
| `tailMap(k)` | Entries with keys greater than or equal to `k` |
| `subMap(from, to)` | Entries between `from` (inclusive) and `to` (exclusive) |
| `floorKey(k)` | Greatest key ≤ `k` |
| `ceilingKey(k)` | Smallest key ≥ `k` |

> 💡 `TreeMap` uses a **Red-Black Tree** — O(log n) for put, get, remove.

---

## 🔄 Frequency Counter Pattern

A very common use of `Map` — counting occurrences:

```java
String[] words = {"java", "python", "java", "go", "python", "java"};

Map<String, Integer> freq = new HashMap<>();

for (String word : words) {
    freq.put(word, freq.getOrDefault(word, 0) + 1);
}

System.out.println(freq);
// {java=3, python=2, go=1}
```

---

## ⚡ HashMap vs LinkedHashMap vs TreeMap

| Feature | HashMap | LinkedHashMap | TreeMap |
|---------|---------|---------------|---------|
| Order | ❌ None | ✅ Insertion | ✅ Sorted by key |
| Null key | ✅ One | ✅ One | ❌ No |
| Performance | O(1) | O(1) | O(log n) |
| Internal structure | Hash table | Hash table + LinkedList | Red-Black Tree |
| Range queries | ❌ | ❌ | ✅ |

---

## 💡 When to Use What

| Use Case | Best Choice |
|----------|------------|
| Fast key-value lookup | `HashMap` |
| Maintain insertion order | `LinkedHashMap` |
| Keys always sorted | `TreeMap` |
| Range queries on keys | `TreeMap` |
| Thread-safe modern | `ConcurrentHashMap` |
| Frequency / counting | `HashMap` with `getOrDefault` |

---

*`Map` — Key-Value Pairs · Unique Keys · Separate from Collection Hierarchy*