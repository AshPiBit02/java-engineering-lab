# 🖥️ Introduction to AWT and Swing in Java
---

## 📚 Table of Contents

- [1. What is GUI Programming?](#1-what-is-gui-programming)
- [2. AWT — Abstract Window Toolkit](#2-awt--abstract-window-toolkit)
  - [2.1 What is AWT?](#21-what-is-awt)
  - [2.2 AWT Architecture](#22-awt-architecture)
  - [2.3 AWT Package Structure](#23-awt-package-structure)
  - [2.4 Limitations of AWT](#24-limitations-of-awt)
- [3. Swing](#3-swing)
  - [3.1 What is Swing?](#31-what-is-swing)
  - [3.2 Why Swing over AWT?](#32-why-swing-over-awt)
  - [3.3 Swing Architecture — MVC Pattern](#33-swing-architecture--mvc-pattern)
  - [3.4 Swing Package Structure](#34-swing-package-structure)
- [4. AWT vs Swing](#4-awt-vs-swing)
- [5. Applets](#5-applets)
  - [5.1 What is an Applet?](#51-what-is-an-applet)
  - [5.2 Applet Life Cycle](#52-applet-life-cycle)
  - [5.3 Simple Applet Example](#53-simple-applet-example)
- [6. Swing Class Hierarchy](#6-swing-class-hierarchy)
  - [6.1 Full Hierarchy Tree](#61-full-hierarchy-tree)
  - [6.2 Key Classes Explained](#62-key-classes-explained)
- [7. Components](#7-components)
  - [7.1 What is a Component?](#71-what-is-a-component)
  - [7.2 Types of Components](#72-types-of-components)
  - [7.3 Common Swing Components](#73-common-swing-components)
  - [7.4 Component Properties](#74-component-properties)
- [8. Containers](#8-containers)
  - [8.1 What is a Container?](#81-what-is-a-container)
  - [8.2 Types of Containers](#82-types-of-containers)
  - [8.3 Top-Level Containers](#83-top-level-containers)
  - [8.4 Intermediate Containers](#84-intermediate-containers)
- [9. Component vs Container](#9-component-vs-container)
- [10. Building a Simple Swing Application](#10-building-a-simple-swing-application)
- [11. C++ vs Java GUI](#11-c-vs-java-gui)
- [12. Summary](#12-summary)

---

## 1. What is GUI Programming?

**GUI (Graphical User Interface)** programming allows users to interact with a program through **visual elements** — windows, buttons, text fields, menus — instead of text-based commands.

```
┌──────────────────────────────────────────────────────────────┐
│              CLI  vs  GUI  Programming                       │
│                                                              │
│   CLI (Console)               GUI (Graphical)                │
│   ─────────────────           ──────────────────────────     │
│   Text input/output           Visual components              │
│   No visual elements          Windows, buttons, menus        │
│   Harder for end users        Intuitive and user-friendly    │
│   e.g. Terminal commands      e.g. Calculator, Notepad       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — CLI vs GUI Programming**

Java provides **two major GUI frameworks** built into the standard library:

```
┌──────────────────────────────────────────────────────────────┐
│               Java GUI Frameworks                            │
│                                                              │
│   ┌────────────────────┐    ┌──────────────────────┐         │
│   │       AWT          │    │      Swing           │         │
│   │ Abstract Window    │    │  (built on AWT)      │         │
│   │    Toolkit         │    │  more powerful       │         │
│   │   Java 1.0         │    │   Java 1.2+          │         │
│   │  Platform-dependent│    │  Platform-independent│         │
│   └────────────────────┘    └──────────────────────┘         │
│                                                              │
│   Both live under the java.awt and javax.swing packages      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Two Main Java GUI Frameworks**

---

## 2. AWT — Abstract Window Toolkit

### 2.1 What is AWT?

**AWT (Abstract Window Toolkit)** is Java's **original GUI framework**, introduced in **Java 1.0**. It provides a set of classes and interfaces to create **windows, buttons, menus**, and other GUI elements.

> AWT components are called **heavyweight components** because they rely on the **native OS** to render them — each AWT component is a wrapper around a native operating system GUI element.

```
┌──────────────────────────────────────────────────────────────┐
│                  How AWT Works                               │
│                                                              │
│   Java AWT Code                                              │
│        │                                                     │
│        ▼                                                     │
│   AWT Component (e.g. Button)                                │
│        │                                                     │
│        ▼                                                     │
│   Native Peer Component (OS-specific) ─────────┐             │
│        │                   │                   │             │
│        ▼                   ▼                   ▼             │
│   Windows Button    Mac Button         Linux Button          │
│   (looks like       (looks like        (looks like           │
│    Windows)          macOS)             Linux)               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — AWT Delegates Rendering to OS Native Peer**

---

### 2.2 AWT Architecture

AWT uses a **peer-based architecture**:

- Every AWT component has a corresponding **native peer** object
- The peer is an OS-specific implementation of the component
- Java communicates with the peer to display and manage the component

```
┌──────────────────────────────────────────────────────────────┐
│                  AWT Peer Architecture                       │
│                                                              │
│   ┌─────────────────────────────────────────────────────┐    │
│   │                  Java Layer                         │    │
│   │   ┌──────────┐  ┌──────────┐  ┌──────────┐          │    │
│   │   │  Button  │  │  Frame   │  │  Label   │          │    │
│   │   └────┬─────┘  └────┬─────┘  └────┬─────┘          │    │
│   └────────┼─────────────┼─────────────┼────────────────┘    │
│            │             │             │  (1:1 mapping)      │
│   ┌────────┼─────────────┼─────────────┼────────────────┐    │
│   │        ▼             ▼             ▼  Native Layer  │    │
│   │  ButtonPeer     FramePeer      LabelPeer            │    │
│   │  (OS button)   (OS window)    (OS label)            │    │
│   └─────────────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — AWT Peer Architecture (Java → Native)**

---

### 2.3 AWT Package Structure

```
java.awt
├── Component          ← base class for all AWT components
├── Container          ← can hold other components
├── Frame              ← top-level window with title & border
├── Panel              ← basic container (no title bar)
├── Dialog             ← popup window
├── Button             ← clickable button
├── Label              ← text label
├── TextField          ← single-line text input
├── TextArea           ← multi-line text input
├── Checkbox           ← checkbox
├── Choice             ← dropdown list
├── List               ← scrollable list
├── Canvas             ← drawing area
├── MenuBar            ← menu bar
├── Menu               ← menu
├── MenuItem           ← menu item
├── ScrollBar          ← scroll bar
├── Graphics           ← drawing graphics
├── Color              ← color representation
├── Font               ← font representation
├── Image              ← image handling
├── LayoutManager      ← interface for layout managers
└── event/             ← event classes
    ├── ActionEvent
    ├── MouseEvent
    └── KeyEvent
```

---

### 2.4 Limitations of AWT

| Limitation | Description |
|------------|-------------|
| **Platform-dependent look** | Components look different on Windows, Mac, Linux |
| **Limited components** | Small set of basic components |
| **Heavy weight** | Each component has a native peer — uses more memory |
| **No MVC support** | No separation of data and presentation |
| **Poor styling** | Cannot easily customize appearance |
| **Slow rendering** | OS rendering is slower than Java's own painting |

> 💡 These limitations led to the creation of **Swing**.

---

## 3. Swing

### 3.1 What is Swing?

**Swing** is Java's **advanced GUI framework**, introduced in **Java 1.2** as part of the **Java Foundation Classes (JFC)**. It is built **on top of AWT** but overcomes all its limitations by being:

- **Lightweight** — components are drawn entirely in Java, not by the OS
- **Platform-independent look** — same appearance on all platforms
- **Highly customizable** — pluggable look and feel (L&F)
- **Feature-rich** — far more components than AWT

> Swing component names start with **`J`** — `JButton`, `JFrame`, `JLabel`, `JTextField`, etc.

```
┌──────────────────────────────────────────────────────────────┐
│                  How Swing Works                             │
│                                                              │
│   Swing Code  (JButton, JFrame, etc.)                        │
│        │                                                     │
│        ▼                                                     │
│   Java 2D API   ← Swing draws components itself              │
│        │                                                     │
│        ▼                                                     │
│   OS Graphics Context  ← only uses OS for raw drawing area   │
│                                                              │
│   Result: Same look on ALL platforms ✅                     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Swing Draws Its Own Components via Java 2D**

---

### 3.2 Why Swing over AWT?

```
┌──────────────────────────────────────────────────────────────┐
│               Why Swing was Created over AWT                 │
│                                                              │
│   AWT Problem                  Swing Solution                │
│   ─────────────────────        ────────────────────────      │
│   OS-dependent look            Consistent cross-platform look│
│   Limited components           Rich set of 40+ components    │
│   No customization             Pluggable Look and Feel (L&F) │
│   Heavyweight peers            Lightweight — Java-painted    │
│   No MVC                       Built-in MVC architecture     │
│   Poor table/tree support      JTable, JTree built-in        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Why Swing Replaced AWT**

---

### 3.3 Swing Architecture — MVC Pattern

Swing is built on the **Model-View-Controller (MVC)** pattern — separating data, display, and control logic.

```
┌──────────────────────────────────────────────────────────────┐
│              Swing MVC Architecture                          │
│                                                              │
│   ┌─────────────────┐                                        │
│   │     MODEL       │  ← holds the data                      │
│   │  (data/state)   │    e.g. ButtonModel, ListModel         │
│   └────────┬────────┘                                        │
│            │  notifies                                       │
│   ┌────────▼────────┐    ┌─────────────────────┐             │
│   │      VIEW       │    │    CONTROLLER       │             │
│   │  (visual rep.)  │◄───│  (handles events)   │             │
│   │  UI Delegate    │    │  e.g. ActionListener│             │
│   └─────────────────┘    └─────────────────────┘             │
│                                                              │
│   In Swing: View + Controller merged into "UI Delegate"      │
│   → called Separable Model Architecture                      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — Swing's Separable MVC Architecture**

---

### 3.4 Swing Package Structure

```
javax.swing
├── JComponent         ← base class for all Swing components
├── JFrame             ← top-level window
├── JDialog            ← popup dialog
├── JApplet            ← applet container
├── JPanel             ← general-purpose container
├── JButton            ← button
├── JLabel             ← text/image label
├── JTextField         ← single-line input
├── JTextArea          ← multi-line input
├── JPasswordField     ← masked input
├── JCheckBox          ← checkbox
├── JRadioButton       ← radio button
├── JComboBox          ← dropdown list
├── JList              ← scrollable list
├── JTable             ← data table
├── JTree              ← hierarchical tree
├── JSlider            ← slider
├── JProgressBar       ← progress bar
├── JScrollPane        ← scrollable container
├── JTabbedPane        ← tabbed container
├── JSplitPane         ← split view
├── JToolBar           ← toolbar
├── JMenuBar           ← menu bar
├── JMenu              ← menu
├── JMenuItem          ← menu item
├── JFileChooser       ← file open/save dialog
├── JColorChooser      ← color picker
├── JOptionPane        ← message/confirm/input dialogs
├── ImageIcon          ← image icon
└── event/             ← event classes
    ├── ActionEvent
    ├── MouseEvent
    └── KeyEvent
```

---

## 4. AWT vs Swing

```
┌───────────────────────────────────────────────────────────────┐
│                    AWT  vs  Swing                             │ 
│                                                               │
│   Feature          AWT                  Swing                 │
│   ──────────────────────────────────────────────────────────  │
│   Introduced       Java 1.0             Java 1.2 (JFC)        │
│   Package          java.awt             javax.swing           │
│   Component type   Heavyweight          Lightweight           │
│   Rendering        OS / Native peer     Java 2D API           │
│   Platform look    Different per OS     Consistent all OS     │
│   Component count  ~20 basic            40+ rich components   │
│   MVC support      ❌ No               ✅ Built-in           │
│   Customizable     ❌ Limited           ✅ Pluggable L&F     │
│   Performance      Slower (OS calls)    Faster (Java renders) │
│   Double buffering ❌ No               ✅ Yes (flicker-free) │
│   Prefix           No prefix (Button)   J prefix (JButton)    │
│   Still used?      ❌ Rarely            ✅ Yes (legacy apps) │
└───────────────────────────────────────────────────────────────┘
```

> **Fig. 8 — AWT vs Swing Full Comparison**

| Feature | AWT | Swing |
|---------|-----|-------|
| Package | `java.awt` | `javax.swing` |
| Component type | Heavyweight | Lightweight |
| Rendering | OS native | Java 2D |
| Look & Feel | OS-dependent | Pluggable / consistent |
| MVC | ❌ | ✅ |
| Components | `Button`, `Frame` | `JButton`, `JFrame` |
| Thread safe | Partially | Requires EDT |

---

## 5. Applets

### 5.1 What is an Applet?

An **Applet** is a small Java program that runs **inside a web browser** or **applet viewer**. It was designed to deliver interactive content on web pages before JavaScript became dominant.

```
┌──────────────────────────────────────────────────────────────┐
│                  Applet Concept                              │
│                                                              │
│   ┌──────────────────────────────────┐                       │
│   │         Web Browser              │                       │
│   │  ┌────────────────────────────┐  │                       │
│   │  │       Web Page (HTML)      │  │                       │
│   │  │  ┌──────────────────────┐  │  │                       │
│   │  │  │   Java Applet Area   │  │  │                       │
│   │  │  │   (runs Java code)   │  │  │                       │
│   │  │  └──────────────────────┘  │  │                       │
│   │  └────────────────────────────┘  │                       │
│   └──────────────────────────────────┘                       │
│                                                              │
│   Applet is embedded in HTML using <applet> tag              │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 9 — Applet Running Inside a Web Browser**

> ⚠️ **Applets are deprecated since Java 9 and removed in Java 17.** Modern web development uses JavaScript, HTML5, and frameworks. Covered here for academic completeness.

**Key characteristics:**
- Extends `java.applet.Applet` (AWT) or `javax.swing.JApplet` (Swing)
- No `main()` method — browser controls the lifecycle
- Runs in a **sandbox** — restricted security environment
- Downloaded from server, run on client

---

### 5.2 Applet Life Cycle

```
┌──────────────────────────────────────────────────────────────┐
│                   Applet Life Cycle                          │
│                                                              │
│    Browser loads applet                                      │
│          │                                                   │
│          ▼                                                   │
│   ┌─────────────┐                                            │
│   │   init()    │  ← called once — initialize components     │
│   └──────┬──────┘                                            │
│          │                                                   │
│          ▼                                                   │
│   ┌─────────────┐                                            │
│   │   start()   │  ← called each time applet becomes visible │
│   └──────┬──────┘                                            │
│          │                                                   │
│          ▼                                                   │
│   ┌─────────────┐                                            │
│   │   paint()   │  ← called to draw/render the applet        │
│   └──────┬──────┘  ← called whenever repaint needed          │
│          │                                                   │
│          ▼                                                   │
│   ┌─────────────┐                                            │
│   │   stop()    │  ← called when applet hidden / tab switch  │
│   └──────┬──────┘                                            │
│          │                                                   │
│          ▼                                                   │
│   ┌─────────────┐                                            │
│   │  destroy()  │  ← called once — browser closes applet     │
│   └─────────────┘                                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 10 — Applet Life Cycle Methods**

| Method | When Called | Purpose |
|--------|-------------|---------|
| `init()` | Once at start | Initialize variables, components |
| `start()` | Each time visible | Start/resume animation or threads |
| `paint(Graphics g)` | On load & repaint | Draw graphics on screen |
| `stop()` | Each time hidden | Pause animation or threads |
| `destroy()` | Once at end | Cleanup resources |

---

### 5.3 Simple Applet Example

```java
import java.applet.Applet;
import java.awt.Graphics;

public class HelloApplet extends Applet {

    @Override
    public void init() {
        // initialization — runs once
        setBackground(java.awt.Color.CYAN);
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Hello from Java Applet!", 50, 100);
        g.drawRect(30, 80, 200, 30);
    }
}
```

```html
<!-- HTML to embed the applet -->
<applet code="HelloApplet.class" width="300" height="200">
</applet>
```

> 💡 In modern Java, use `JFrame` for standalone apps or web frameworks for web apps instead of Applets.

---

## 6. Swing Class Hierarchy

### 6.1 Full Hierarchy Tree

```
┌──────────────────────────────────────────────────────────────┐
│               Swing Full Class Hierarchy                     │
│                                                              │
│   java.lang.Object                                           │
│   └── java.awt.Component          ← base of all GUI elements │
│       └── java.awt.Container      ← can hold components      │
│           ├── java.awt.Window     ← top-level OS window      │
│           │   ├── java.awt.Frame  ← AWT top-level window     │
│           │   │   └── JFrame      ← Swing top-level window   │
│           │   ├── java.awt.Dialog ← AWT dialog               │
│           │   │   └── JDialog     ← Swing dialog             │
│           │   └── JWindow         ← borderless window        │
│           ├── java.awt.Panel      ← AWT container            │
│           │   └── java.applet.Applet ← AWT applet            │
│           │       └── JApplet     ← Swing applet             │
│           └── javax.swing.JComponent ← base of all Swing     │
│               ├── JLabel                                     │
│               ├── AbstractButton                             │
│               │   ├── JButton                                │
│               │   ├── JToggleButton                          │
│               │   │   ├── JCheckBox                          │
│               │   │   └── JRadioButton                       │
│               │   └── JMenuItem                              │
│               │       ├── JMenu                              │
│               │       ├── JCheckBoxMenuItem                  │
│               │       └── JRadioButtonMenuItem               │
│               ├── JTextComponent                             │
│               │   ├── JTextField                             │
│               │   │   └── JPasswordField                     │
│               │   └── JTextArea                              │
│               ├── JPanel                                     │
│               ├── JScrollPane                                │
│               ├── JTabbedPane                                │
│               ├── JSplitPane                                 │
│               ├── JComboBox                                  │
│               ├── JList                                      │
│               ├── JTable                                     │
│               ├── JTree                                      │
│               ├── JSlider                                    │
│               ├── JProgressBar                               │
│               ├── JToolBar                                   │
│               ├── JMenuBar                                   │
│               └── JOptionPane                                │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 11 — Complete Swing Class Hierarchy**

---

### 6.2 Key Classes Explained

| Class | Package | Role |
|-------|---------|------|
| `Object` | `java.lang` | Root of all Java classes |
| `Component` | `java.awt` | Base of all GUI elements — paint, size, events |
| `Container` | `java.awt` | Component that can hold other components |
| `Window` | `java.awt` | Top-level window without decorations |
| `Frame` | `java.awt` | AWT top-level window with title + border |
| `JFrame` | `javax.swing` | Swing top-level window ← most used |
| `JComponent` | `javax.swing` | Base of all Swing lightweight components |
| `JPanel` | `javax.swing` | General-purpose intermediate container |
| `JButton` | `javax.swing` | Clickable button |
| `JLabel` | `javax.swing` | Non-interactive text/image display |

---

## 7. Components

### 7.1 What is a Component?

A **Component** is any **visual element** on the screen that the user can see and interact with. All GUI elements in Java inherit from `java.awt.Component`.

```
┌──────────────────────────────────────────────────────────────┐
│                  What is a Component?                        │
│                                                              │
│   ┌────────────────────────────────────────────────────┐     │
│   │                   JFrame Window                    │     │
│   │                                                    │     │
│   │   ┌───────────┐  ┌───────────┐  ┌──────────────┐   │     │
│   │   │  JLabel   │  │JTextField │  │   JButton    │   │     │
│   │   │"Username:"│  │[        ] │  │  [  Login  ] │   │     │
│   │   └───────────┘  └───────────┘  └──────────────┘   │     │
│   │                                                    │     │
│   │   Each of these boxes is a COMPONENT               │     │
│   └────────────────────────────────────────────────────┘     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 12 — Components Inside a Window**

---

### 7.2 Types of Components

```
┌──────────────────────────────────────────────────────────────┐
│                   Types of Components                        │
│                                                              │
│   ┌─────────────────────────────────────────────────────┐    │
│   │                   Component                         │    │
│   │                      │                              │    │
│   │         ┌────────────┴────────────┐                 │    │
│   │         │                         │                 │    │
│   │  ┌──────▼──────┐          ┌───────▼──────┐          │    │
│   │  │  Atomic /   │          │  Container   │          │    │
│   │  │  Leaf       │          │  Components  │          │    │
│   │  │  Components │          │  (can hold   │          │    │
│   │  │  (no child) │          │   children)  │          │    │
│   │  └─────────────┘          └──────────────┘          │    │
│   │  JButton, JLabel,         JPanel, JFrame,           │    │
│   │  JTextField, etc.         JScrollPane, etc.         │    │
│   └─────────────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 13 — Atomic vs Container Components**

---

### 7.3 Common Swing Components

| Component | Class | Description |
|-----------|-------|-------------|
| Button | `JButton` | Clickable button that triggers action |
| Label | `JLabel` | Displays text or image (non-editable) |
| Text Field | `JTextField` | Single-line text input |
| Text Area | `JTextArea` | Multi-line text input |
| Password Field | `JPasswordField` | Masked single-line input |
| Checkbox | `JCheckBox` | Toggle on/off option |
| Radio Button | `JRadioButton` | Select one from a group |
| Combo Box | `JComboBox` | Drop-down selection list |
| List | `JList` | Scrollable list of items |
| Table | `JTable` | Grid of rows and columns |
| Tree | `JTree` | Hierarchical data display |
| Slider | `JSlider` | Select value from a range |
| Progress Bar | `JProgressBar` | Shows task completion |
| Spinner | `JSpinner` | Numeric increment/decrement input |
| Image Icon | `ImageIcon` | Displays an image |

---

### 7.4 Component Properties

Every Swing component inherits these **common properties** from `JComponent`:

| Property | Method | Description |
|----------|--------|-------------|
| Size | `setSize(w, h)` | Set width and height |
| Location | `setLocation(x, y)` | Set position on screen |
| Bounds | `setBounds(x, y, w, h)` | Set position + size together |
| Visibility | `setVisible(true/false)` | Show or hide component |
| Enabled | `setEnabled(true/false)` | Enable or disable interaction |
| Background | `setBackground(Color)` | Background color |
| Foreground | `setForeground(Color)` | Text/foreground color |
| Font | `setFont(Font)` | Set font style and size |
| Tooltip | `setToolTipText(String)` | Hover tooltip text |
| Border | `setBorder(Border)` | Add border around component |
| Cursor | `setCursor(Cursor)` | Change mouse cursor on hover |
| Preferred Size | `setPreferredSize(Dimension)` | Hint size to layout manager |

```java
JButton btn = new JButton("Click Me");

btn.setSize(150, 40);
btn.setBackground(Color.BLUE);
btn.setForeground(Color.WHITE);
btn.setFont(new Font("Arial", Font.BOLD, 14));
btn.setToolTipText("Click this button");
btn.setEnabled(true);
btn.setVisible(true);
```

---

## 8. Containers

### 8.1 What is a Container?

A **Container** is a special component that can **hold and organize other components** (including other containers). It provides the structure for building complex GUIs.

```
┌──────────────────────────────────────────────────────────────┐
│                Container Nesting                             │
│                                                              │
│   ┌──────────────────────────────────────────────────────┐   │
│   │  JFrame  (top-level container)                       │   │
│   │                                                      │   │
│   │   ┌──────────────────────────────────────────────┐   │   │
│   │   │  JPanel  (intermediate container)            │   │   │
│   │   │                                              │   │   │
│   │   │   ┌─────────┐  ┌───────────┐  ┌──────────┐   │   │   │
│   │   │   │ JLabel  │  │JTextField │  │ JButton  │   │   │   │
│   │   │   └─────────┘  └───────────┘  └──────────┘   │   │   │
│   │   └──────────────────────────────────────────────┘   │   │
│   │                                                      │   │
│   │   ┌──────────────────────────────────────────────┐   │   │
│   │   │  Another JPanel                              │   │   │
│   │   │   ┌─────────────┐  ┌──────────────────────┐  │   │   │
│   │   │   │  JCheckBox  │  │    JRadioButton      │  │   │   │
│   │   │   └─────────────┘  └──────────────────────┘  │   │   │
│   │   └──────────────────────────────────────────────┘   │   │
│   └──────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 14 — Containers Nesting Components**

---

### 8.2 Types of Containers

```
┌──────────────────────────────────────────────────────────────┐
│                   Types of Containers                        │
│                                                              │
│         ┌──────────────────────────────────┐                 │
│         │           Container              │                 │
│         └──────────────────┬───────────────┘                 │
│                            │                                 │
│           ┌────────────────┴─────────────────┐               │
│           │                                  │               │
│  ┌────────▼──────────┐            ┌──────────▼──────────┐    │
│  │   Top-Level       │            │   Intermediate      │    │
│  │   Containers      │            │   Containers        │    │
│  │                   │            │                     │    │
│  │  JFrame           │            │  JPanel             │    │
│  │  JDialog          │            │  JScrollPane        │    │
│  │  JWindow          │            │  JTabbedPane        │    │
│  │  JApplet          │            │  JSplitPane         │    │
│  │                   │            │  JToolBar           │    │
│  │  Have their own   │            │  JInternalFrame     │    │
│  │  OS window        │            │  Used inside        │    │
│  │                   │            │  top-level ones     │    │
│  └───────────────────┘            └─────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 15 — Types of Containers**

---

### 8.3 Top-Level Containers

Top-level containers are the **outermost windows** — they do not live inside another container.

#### 🔹 JFrame — Main Application Window

The most commonly used top-level container. Has title bar, border, minimize/maximize/close buttons.

```java
import javax.swing.*;

public class MyApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Application");  // title

        frame.setSize(500, 400);                      // width, height
        frame.setLocation(200, 100);                  // x, y on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);                       // must call to show
    }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│  JFrame Structure                                            │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │  Title Bar  │ My Application          [ _ ][ □ ][ X ]  │  │
│  ├────────────────────────────────────────────────────────┤  │
│  │  Menu Bar   │  File   Edit   View                      │  │
│  ├────────────────────────────────────────────────────────┤  │
│  │                                                        │  │
│  │                  Content Pane                          │  │
│  │         (where you add components)                     │  │
│  │                                                        │  │
│  ├────────────────────────────────────────────────────────┤  │
│  │  Glass Pane  (invisible layer for intercepting events) │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 16 — JFrame Internal Structure**

**JFrame Layers:**

| Layer | Description |
|-------|-------------|
| `RootPane` | Manages all other panes |
| `GlassPane` | Invisible top layer — intercepts events |
| `LayeredPane` | Manages z-ordering of components |
| `ContentPane` | Main area — where you add components |
| `MenuBar` | Optional menu bar at top |

> 💡 Always add components to the **ContentPane**, not directly to JFrame.

```java
// Correct way to add components
frame.getContentPane().add(new JButton("Click"));

// Short-hand (Java 5+) — same effect
frame.add(new JButton("Click"));
```

---

#### 🔹 JDialog — Popup Window

A secondary window — always associated with a parent `JFrame`. Used for alerts, input, confirmations.

```java
JFrame parent = new JFrame("Main");
JDialog dialog = new JDialog(parent, "Settings", true); // modal dialog

dialog.setSize(300, 200);
dialog.setVisible(true);
```

| Type | Description |
|------|-------------|
| **Modal** | Blocks parent window until closed |
| **Non-modal** | Parent remains usable while dialog is open |

---

#### 🔹 JWindow — Borderless Window

A top-level window with **no title bar or border**. Used for splash screens.

```java
JWindow splash = new JWindow();
splash.setSize(400, 300);
splash.setLocationRelativeTo(null);  // center on screen
splash.setVisible(true);
```

---

### 8.4 Intermediate Containers

Used **inside** top-level containers to organize and group components.

#### 🔹 JPanel — General Purpose Container

The most commonly used intermediate container. Groups related components together.

```java
JPanel panel = new JPanel();
panel.setBackground(Color.LIGHT_GRAY);
panel.setBorder(BorderFactory.createTitledBorder("Login"));

panel.add(new JLabel("Username:"));
panel.add(new JTextField(15));
panel.add(new JButton("Login"));

frame.add(panel);
```

---

#### 🔹 JScrollPane — Scrollable Container

Wraps a component to add **scroll bars** when content exceeds visible area.

```java
JTextArea area = new JTextArea(10, 30);
JScrollPane scrollPane = new JScrollPane(area);  // wrap in scroll pane

frame.add(scrollPane);
```

---

#### 🔹 JTabbedPane — Tabbed Container

Organizes content into **multiple tabs**.

```java
JTabbedPane tabs = new JTabbedPane();

tabs.addTab("General",  new JPanel());
tabs.addTab("Network",  new JPanel());
tabs.addTab("Advanced", new JPanel());

frame.add(tabs);
```

---

#### 🔹 JSplitPane — Split Container

Divides the container into **two resizable sections**.

```java
JSplitPane split = new JSplitPane(
    JSplitPane.HORIZONTAL_SPLIT,
    new JPanel(),    // left component
    new JPanel()     // right component
);
split.setDividerLocation(200);
frame.add(split);
```

---

## 9. Component vs Container

```
┌──────────────────────────────────────────────────────────────┐
│               Component  vs  Container                       │
│                                                              │
│   Component                      Container                   │
│   ──────────────────────         ───────────────────────     │
│   Basic visual element           Special component           │
│   Cannot hold others             CAN hold other components   │
│   Leaf node in hierarchy         Node with children          │
│   e.g. JButton, JLabel           e.g. JPanel, JFrame         │
│        JTextField                     JScrollPane            │
│   Directly interactive           Organizes layout            │
│   Inherits Component             Inherits Container          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 17 — Component vs Container**

| Feature | Component | Container |
|---------|-----------|-----------|
| Holds other elements | ❌ | ✅ |
| Directly visible | ✅ | ✅ |
| User interaction | ✅ Directly | ✅ Via children |
| Layout manager | ❌ | ✅ |
| `add()` method | ❌ | ✅ |
| Examples | `JButton`, `JLabel` | `JPanel`, `JFrame` |

---

## 10. Building a Simple Swing Application

Putting it all together — a complete Swing login form:

```java
import javax.swing.*;
import java.awt.*;

public class LoginForm {
    public static void main(String[] args) {

        // 1. Create top-level container
        JFrame frame = new JFrame("Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);   // center on screen

        // 2. Create intermediate container
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 3. Create components
        JLabel  userLabel = new JLabel("Username:");
        JLabel  passLabel = new JLabel("Password:");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn  = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        // 4. Add components to panel
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(cancelBtn);

        // 5. Add panel to frame
        frame.add(panel);

        // 6. Show frame
        frame.setVisible(true);
    }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│  Output Window                                               │
│                                                              │
│  ┌──────────────────────────────────┐                        │
│  │  Login                  [ X ]    │                        │
│  ├──────────────────────────────────┤                        │
│  │  Username: [ _________________ ] │                        │
│  │  Password: [ ················ ]  │                        │
│  │  [ Login ]          [ Cancel ]   │                        │
│  └──────────────────────────────────┘                        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 18 — Login Form Output**

---

## 11. C++ vs Java GUI

| Feature | C++ | Java |
|---------|-----|------|
| GUI Framework | Qt, wxWidgets, MFC | AWT, Swing, JavaFX |
| Built-in GUI | ❌ (3rd party needed) | ✅ (part of JDK) |
| Cross-platform | Depends on framework | ✅ Native |
| Look and Feel | OS-native (Qt) | Pluggable L&F (Swing) |
| Event handling | Signals & Slots (Qt) | Listener interfaces |
| GUI design | Code + Qt Designer | Code + IDE tools |
| Learning curve | Higher | Moderate |

> 🆚 **Key Difference** — C++ has **no built-in GUI framework** — you must use third-party libraries like Qt or wxWidgets. Java's Swing is **built directly into the JDK** — no extra installation needed.

---

## 12. Summary

| Concept | Key Point |
|---------|-----------|
| **AWT** | Original Java GUI — heavyweight, OS-dependent, `java.awt` |
| **Swing** | Advanced GUI — lightweight, platform-independent, `javax.swing` |
| **Heavyweight** | AWT components — rendered by OS native peer |
| **Lightweight** | Swing components — rendered by Java 2D itself |
| **Applet** | Java program inside browser — deprecated since Java 9 |
| **Applet lifecycle** | `init() → start() → paint() → stop() → destroy()` |
| **Component** | Any visual GUI element — button, label, text field |
| **Container** | Component that holds other components |
| **Top-level container** | `JFrame`, `JDialog`, `JWindow` — outermost windows |
| **Intermediate container** | `JPanel`, `JScrollPane`, `JTabbedPane` — used inside top-level |
| **JFrame structure** | GlassPane → LayeredPane → ContentPane |
| **MVC in Swing** | Model (data) + View (UI Delegate) + Controller (events) |
| **`J` prefix** | All Swing components start with `J` — `JButton`, `JLabel` etc. |

```
AWT (1.0)  →  heavyweight  →  OS renders  →  different look per OS
Swing (1.2) →  lightweight  →  Java renders →  same look all platforms
JavaFX      →  modern       →  GPU renders  →  best for new projects
```