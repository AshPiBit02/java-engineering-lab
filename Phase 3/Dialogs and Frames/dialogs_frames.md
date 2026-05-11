# 🖼️ Dialogs and Frames in Java


---

## 📚 Table of Contents

- [1. Frames](#1-frames)
  - [1.1 JFrame](#11-jframe)
  - [1.2 JFrame Structure](#12-jframe-structure)
  - [1.3 JFrame Key Properties](#13-jframe-key-properties)
  - [1.4 Multiple Frames](#14-multiple-frames)
  - [1.5 JInternalFrame](#15-jinternalframe)
- [2. Dialogs](#2-dialogs)
  - [2.1 What is a Dialog?](#21-what-is-a-dialog)
  - [2.2 Modal vs Non-Modal](#22-modal-vs-non-modal)
  - [2.3 JDialog](#23-jdialog)
  - [2.4 JOptionPane](#24-joptionpane)
  - [2.5 JFileChooser](#25-jfilechooser)
  - [2.6 JColorChooser](#26-jcolorchooser)
- [3. Frame vs Dialog](#3-frame-vs-dialog)
- [4. Summary](#4-summary)

---

## 1. Frames

### 1.1 JFrame

A **JFrame** is the **main top-level window** in a Swing application. It has a title bar, border, and standard OS window controls (minimize, maximize, close).

```
┌──────────────────────────────────────────────────────────────┐
│  ┌────────────────────────────────────────────────────────┐  │
│  │  🪟  My Application                    [ _ ][ □ ][ X ]│  │
│  ├────────────────────────────────────────────────────────┤  │
│  │  Menu Bar  (optional)                                  │  │
│  ├────────────────────────────────────────────────────────┤  │
│  │                                                        │  │
│  │                  Content Pane                          │  │
│  │            (add your components here)                  │  │
│  │                                                        │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — JFrame Structure**

```java
JFrame frame = new JFrame("My Application");
frame.setSize(500, 400);
frame.setLocationRelativeTo(null);          // center on screen
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setVisible(true);
```

---

### 1.2 JFrame Structure

A `JFrame` is built from several **layered panes**:

```
┌──────────────────────────────────────────────────────────────┐
│                    JFrame Layers (top to bottom)             │
│                                                              │
│   GlassPane      <- invisible top layer, intercepts events   │
│   LayeredPane    <- manages z-order (stacking) of components │
│       +-- ContentPane  <- where YOU add your components      │
│       +-- MenuBar      <- optional, sits above content pane  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — JFrame Internal Layers**

> 💡 Always add components to the **ContentPane** — using `frame.add()` in Java 5+ automatically targets the ContentPane.

---

### 1.3 JFrame Key Properties

| Property | Method | Common Values |
|----------|--------|---------------|
| Title | `setTitle(String)` | Any string |
| Size | `setSize(int w, int h)` | pixels |
| Location | `setLocation(int x, int y)` | screen coords |
| Center | `setLocationRelativeTo(null)` | centers on screen |
| Resizable | `setResizable(boolean)` | `true` / `false` |
| Close behavior | `setDefaultCloseOperation(int)` | see below |
| Icon | `setIconImage(Image)` | window icon |
| Auto-size | `pack()` | fits to components |
| Layout | `setLayout(LayoutManager)` | any layout manager |

### Close Operation Constants

| Constant | Effect |
|----------|--------|
| `DO_NOTHING_ON_CLOSE` | Nothing happens |
| `HIDE_ON_CLOSE` | Window hides (default) |
| `DISPOSE_ON_CLOSE` | Window + resources freed |
| `EXIT_ON_CLOSE` | Terminates the JVM |

```java
// Intercept close with custom logic
frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
frame.addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
        int choice = JOptionPane.showConfirmDialog(frame,
            "Save before exit?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION)     { System.exit(0); }
        else if (choice == JOptionPane.NO_OPTION) { System.exit(0); }
        // CANCEL -> stay open
    }
});
```

---

### 1.4 Multiple Frames

An application can have **more than one JFrame**. Common pattern — main window launching a secondary window:

```java
class MainFrame extends JFrame {
    MainFrame() {
        setTitle("Main Window");
        setSize(500, 400);
        JButton openSettings = new JButton("Open Settings");
        openSettings.addActionListener(e -> new SettingsFrame());
        add(openSettings);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class SettingsFrame extends JFrame {
    SettingsFrame() {
        setTitle("Settings");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // don't kill main app
        setVisible(true);
    }
}
```

> ⚠️ Use `DISPOSE_ON_CLOSE` for secondary frames — `EXIT_ON_CLOSE` would terminate the entire application.

---

### 1.5 JInternalFrame

A **JInternalFrame** is a **mini window that lives inside** a `JDesktopPane` — used to create **MDI (Multiple Document Interface)** applications.

```
┌──────────────────────────────────────────────────────────────┐
│  Main JFrame                                                 │
│  ┌──────────────────────────────────────────────────────┐    │
│  │  JDesktopPane (desktop area)                         │    │
│  │  ┌────────────────────┐  ┌──────────────────────┐    │    │
│  │  │ JInternalFrame 1   │  │ JInternalFrame 2     │    │    │
│  │  │ [ _ ][ max ][ X ]  │  │ [ _ ][ max ][ X ]    │    │    │
│  │  │   Document 1       │  │   Document 2         │    │    │
│  │  └────────────────────┘  └──────────────────────┘    │    │
│  └──────────────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — JInternalFrame inside JDesktopPane (MDI)**

```java
JDesktopPane desktop = new JDesktopPane();

JInternalFrame iFrame = new JInternalFrame(
    "Document 1", // title
    true,         // resizable
    true,         // closable
    true,         // maximizable
    true          // iconifiable
);
iFrame.setSize(300, 200);
iFrame.setLocation(30, 30);
iFrame.setVisible(true);

desktop.add(iFrame);
frame.setContentPane(desktop);
```

| Feature | JFrame | JInternalFrame |
|---------|--------|----------------|
| Lives in | OS desktop | `JDesktopPane` |
| Taskbar icon | ✅ | ❌ |
| Modal support | ❌ | ✅ (via `JDesktopPane`) |
| Use case | Main window | MDI child windows |

---

## 2. Dialogs

### 2.1 What is a Dialog?

A **Dialog** is a **secondary popup window** used to interact with the user — deliver messages, collect input, or ask for confirmation.

```
┌──────────────────────────────────────────────────────────────┐
│                   Dialog Interaction Flow                    │
│                                                              │
│   Main Frame running                                         │
│         │                                                    │
│   event triggers dialog                                      │
│         │                                                    │
│         ▼                                                    │
│   ┌──────────────────────┐                                   │
│   │       Dialog         │  <- user interacts                │
│   │  "Are you sure?"     │                                   │
│   │  [ Yes ]   [ No ]    │                                   │
│   └──────────┬───────────┘                                   │
│              │  user responds                                │
│              ▼                                               │
│   Main Frame continues                                       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Dialog Interaction Flow**

---

### 2.2 Modal vs Non-Modal

```
┌──────────────────────────────────────────────────────────────┐
│                  Modal  vs  Non-Modal                        │
│                                                              │
│   Modal Dialog                   Non-Modal Dialog            │
│   ──────────────────────         ──────────────────────      │
│   Blocks parent window           Parent remains usable       │
│   User must respond first        Can interact with both      │
│   Input/confirm dialogs          Progress, find/replace      │
│   JOptionPane (default)          Custom tool windows         │
│                                                              │
│   new JDialog(parent,"T", true)  new JDialog(parent,"T",false)
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Modal vs Non-Modal Dialogs**

---

### 2.3 JDialog

**JDialog** is the base class for creating **fully custom dialogs**. Use it when `JOptionPane` is not flexible enough.

```java
JDialog dialog = new JDialog(parentFrame, "Settings", true); // modal=true
dialog.setSize(350, 250);
dialog.setLocationRelativeTo(parentFrame);
dialog.setLayout(new BorderLayout());

// --- Add components ---
JPanel content = new JPanel(new GridLayout(2, 2, 10, 10));
content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
content.add(new JLabel("Theme:"));
content.add(new JComboBox<>(new String[]{"Light", "Dark"}));
content.add(new JLabel("Language:"));
content.add(new JComboBox<>(new String[]{"English", "Nepali"}));

JPanel buttons = new JPanel();
JButton save  = new JButton("Save");
JButton close = new JButton("Cancel");
save.addActionListener(e  -> dialog.dispose());
close.addActionListener(e -> dialog.dispose());
buttons.add(save);
buttons.add(close);

dialog.add(content, BorderLayout.CENTER);
dialog.add(buttons, BorderLayout.SOUTH);
dialog.setVisible(true);
```

**JDialog Key Methods:**

| Method | Description |
|--------|-------------|
| `setModal(boolean)` | Set modal / non-modal |
| `setLocationRelativeTo(Component)` | Position relative to component |
| `dispose()` | Close and release resources |
| `setDefaultCloseOperation(int)` | Close behavior |
| `setResizable(boolean)` | Allow / disallow resize |

---

### 2.4 JOptionPane

**JOptionPane** provides **ready-made standard dialogs** — fastest way to show messages, ask questions, or get input without building a full `JDialog`.

```
┌──────────────────────────────────────────────────────────────┐
│                  JOptionPane Dialog Types                    │
│                                                              │
│  showMessageDialog  -> info / warning / error message        │
│  showConfirmDialog  -> Yes / No / Cancel question            │
│  showInputDialog    -> collect text string from user         │
│  showOptionDialog   -> fully custom buttons and content      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — JOptionPane Four Dialog Types**

**1. Message Dialog**
```java
JOptionPane.showMessageDialog(frame,
    "File saved successfully!",
    "Success",
    JOptionPane.INFORMATION_MESSAGE);
```

**2. Confirm Dialog**
```java
int result = JOptionPane.showConfirmDialog(frame,
    "Delete this file?", "Confirm",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.WARNING_MESSAGE);

if (result == JOptionPane.YES_OPTION) { /* delete */ }
```

**3. Input Dialog**
```java
String name = JOptionPane.showInputDialog(frame,
    "Enter your name:", "Input", JOptionPane.QUESTION_MESSAGE);
```

**4. Option Dialog (custom buttons)**
```java
String[] options = {"Save", "Discard", "Cancel"};
int choice = JOptionPane.showOptionDialog(frame,
    "Unsaved changes.", "Exit",
    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
    null, options, options[0]);
```

**Message Type Constants:**

| Constant | Icon | Use |
|----------|------|-----|
| `INFORMATION_MESSAGE` | ℹ️ | General info |
| `WARNING_MESSAGE` | ⚠️ | Caution |
| `ERROR_MESSAGE` | ❌ | Error / failure |
| `QUESTION_MESSAGE` | ❓ | Asking user |
| `PLAIN_MESSAGE` | — | No icon |

**Confirm Dialog Return Values:**

| Constant | Value | Meaning |
|----------|-------|---------|
| `YES_OPTION` | 0 | User clicked Yes / OK |
| `NO_OPTION` | 1 | User clicked No |
| `CANCEL_OPTION` | 2 | User clicked Cancel |
| `CLOSED_OPTION` | -1 | Dialog closed without choosing |

---

### 2.5 JFileChooser

A **JFileChooser** shows a **cross-platform file / directory selection dialog**.

```java
JFileChooser fc = new JFileChooser();
fc.setDialogTitle("Open File");
fc.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));

int result = fc.showOpenDialog(frame);   // or showSaveDialog()

if (result == JFileChooser.APPROVE_OPTION) {
    File selected = fc.getSelectedFile();
    System.out.println("Chosen: " + selected.getAbsolutePath());
}
```

| Method | Description |
|--------|-------------|
| `showOpenDialog(parent)` | Open file mode |
| `showSaveDialog(parent)` | Save file mode |
| `setMultiSelectionEnabled(true)` | Allow multiple file selection |
| `setFileSelectionMode(int)` | `FILES_ONLY`, `DIRECTORIES_ONLY`, `FILES_AND_DIRECTORIES` |
| `getSelectedFile()` | Returns selected `File` |
| `getSelectedFiles()` | Returns `File[]` for multi-select |

---

### 2.6 JColorChooser

A **JColorChooser** shows a **color picking dialog** with swatches, HSB, and RGB panels.

```java
Color chosen = JColorChooser.showDialog(
    frame,
    "Choose Color",
    panel.getBackground()     // initial color
);

if (chosen != null) {
    panel.setBackground(chosen);  // null if user cancelled
}
```

---

## 3. Frame vs Dialog

```
┌──────────────────────────────────────────────────────────────┐
│                  Frame  vs  Dialog                           │
│                                                              │
│   JFrame                         JDialog                     │
│   ──────────────────────         ──────────────────────      │
│   Independent window             Attached to parent frame    │
│   Main application window        Secondary popup window      │
│   Has taskbar icon               No separate taskbar entry   │
│   No parent required             Requires parent frame       │
│   Cannot be modal                Can be modal or non-modal   │
│   Has its own lifecycle          Shares parent lifecycle     │
│   EXIT_ON_CLOSE = kills app      DISPOSE_ON_CLOSE = closes   │
│   Used for main content          Used for interaction/input  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — Frame vs Dialog Side by Side**

| Feature | JFrame | JDialog |
|---------|--------|---------|
| Parent required | ❌ | ✅ |
| Can be modal | ❌ | ✅ |
| Taskbar entry | ✅ | ❌ |
| Main use | Primary window | Temporary interaction |
| Default close | `HIDE_ON_CLOSE` | `HIDE_ON_CLOSE` |
| Extends | `java.awt.Frame` | `java.awt.Dialog` |

---

## 4. Summary

| Concept | Key Point |
|---------|-----------|
| **JFrame** | Main application window — top-level, independent |
| **JFrame layers** | GlassPane -> LayeredPane -> ContentPane |
| **Close operation** | `EXIT_ON_CLOSE` for main, `DISPOSE_ON_CLOSE` for secondary |
| **JInternalFrame** | Mini window inside `JDesktopPane` — MDI pattern |
| **JDialog** | Custom popup — needs parent, can be modal |
| **Modal** | Blocks parent window until closed |
| **Non-modal** | Parent and dialog usable simultaneously |
| **JOptionPane** | Quick standard dialogs — message, confirm, input, option |
| **JFileChooser** | Cross-platform file / directory picker |
| **JColorChooser** | Color selection dialog |

```
Frame   ->  independent  ->  main window   ->  no modal support
Dialog  ->  child frame  ->  popup window  ->  modal / non-modal

JOptionPane  ->  fastest for standard dialogs
JDialog      ->  full custom control
```