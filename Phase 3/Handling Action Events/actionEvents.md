# 🎯 Handling Action Events in Java


---

## 📚 Table of Contents

- [1. What is an Action Event?](#1-what-is-an-action-event)
- [2. ActionListener Interface](#2-actionlistener-interface)
- [3. ActionEvent Object](#3-actionevent-object)
- [4. Components That Fire ActionEvents](#4-components-that-fire-actionevents)
- [5. Ways to Handle Action Events](#5-ways-to-handle-action-events)
- [6. Action Command](#6-action-command)
- [7. Shared Listener — One Handler, Many Components](#7-shared-listener--one-handler-many-components)
- [8. Enabling and Disabling Actions](#8-enabling-and-disabling-actions)
- [9. Action Interface — Reusable Actions](#9-action-interface--reusable-actions)
- [10. Practical Patterns](#10-practical-patterns)
- [11. Summary](#11-summary)

---

## 1. What is an Action Event?

An **ActionEvent** is fired when the user performs a **primary action** on a component — clicking a button, selecting a menu item, pressing Enter in a text field, or choosing a combo box item.

```
┌──────────────────────────────────────────────────────────────┐
│              What Triggers an ActionEvent?                   │
│                                                              │
│   Component         Trigger                                  │
│   ─────────────     ──────────────────────────────────────   │
│   JButton       ->  clicked (mouse press + release)          │
│   JMenuItem     ->  selected from menu                       │
│   JTextField    ->  Enter key pressed                        │
│   JComboBox     ->  item selected from drop-down             │
│   JCheckBox*    ->  clicked (*also fires ItemEvent)          │
│   JRadioButton* ->  clicked (*also fires ItemEvent)          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Components and Their ActionEvent Triggers**

---

## 2. ActionListener Interface

`ActionListener` is a **functional interface** in `java.awt.event` with exactly **one abstract method**:

```java
public interface ActionListener extends EventListener {
    void actionPerformed(ActionEvent e);
}
```

> Because it has only one method, it works perfectly with **lambda expressions** (Java 8+).

### Registering a Listener

```java
component.addActionListener(ActionListener listener);
component.removeActionListener(ActionListener listener);  // unregister
```

---

## 3. ActionEvent Object

When an action event fires, Java creates an `ActionEvent` object and passes it to `actionPerformed()`. It carries useful information about the event.

```
┌──────────────────────────────────────────────────────────────┐
│                   ActionEvent Object                         │
│                                                              │
│   ┌──────────────────────────────────────────────────────┐   │
│   │  ActionEvent                                         │   │
│   │                                                      │   │
│   │  getSource()        -> the component that fired      │   │
│   │  getActionCommand() -> command string identifier     │   │
│   │  getModifiers()     -> Ctrl/Shift/Alt held?          │   │
│   │  getWhen()          -> timestamp of event (ms)       │   │
│   │  getID()            -> event type ID                 │   │
│   └──────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — ActionEvent Object Contents**

| Method | Return Type | Description |
|--------|-------------|-------------|
| `getSource()` | `Object` | The component that fired the event |
| `getActionCommand()` | `String` | Command string (default = button label) |
| `getModifiers()` | `int` | Modifier keys held (Ctrl, Shift, Alt) |
| `getWhen()` | `long` | Timestamp of the event in milliseconds |

### Checking Modifier Keys

```java
btn.addActionListener(e -> {
    int mods = e.getModifiers();

    if ((mods & ActionEvent.CTRL_MASK) != 0)
        System.out.println("Ctrl was held during click");

    if ((mods & ActionEvent.SHIFT_MASK) != 0)
        System.out.println("Shift was held during click");
});
```

---

## 4. Components That Fire ActionEvents

```
┌──────────────────────────────────────────────────────────────┐
│         Component     Register Method                        │
│                                                              │
│   JButton         addActionListener(ActionListener)          │
│   JMenuItem       addActionListener(ActionListener)          │
│   JTextField      addActionListener(ActionListener)          │
│   JPasswordField  addActionListener(ActionListener)          │
│   JComboBox       addActionListener(ActionListener)          │
│   JCheckBox       addActionListener(ActionListener)          │
│   JRadioButton    addActionListener(ActionListener)          │
│   Timer           addActionListener(ActionListener)          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Components and Their Registration Methods**

### JTextField — Enter Key

```java
JTextField searchTF = new JTextField(20);

searchTF.addActionListener(e ->
    System.out.println("Searching: " + searchTF.getText())
);
```

### JComboBox — Selection Change

```java
JComboBox<String> combo = new JComboBox<>(
    new String[]{"Java", "Python", "C++"});

combo.addActionListener(e ->
    System.out.println("Selected: " + combo.getSelectedItem())
);
```

### javax.swing.Timer — Repeated Actions

```java
// Fire every 1000ms (1 second)
Timer timer = new Timer(1000, e ->
    System.out.println("Tick: " + System.currentTimeMillis())
);

timer.start();
// timer.stop();   // to stop
// timer.restart(); // to reset and restart
```

---

## 5. Ways to Handle Action Events

### 1. Lambda *(cleanest — Java 8+)*

```java
JButton btn = new JButton("Submit");
btn.addActionListener(e -> handleSubmit());
```

---

### 2. Anonymous Inner Class

```java
btn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        handleSubmit();
    }
});
```

---

### 3. Class Implements ActionListener

```java
public class LoginForm extends JFrame implements ActionListener {
    JButton loginBtn = new JButton("Login");
    JButton cancelBtn = new JButton("Cancel");

    LoginForm() {
        loginBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn)
            System.out.println("Logging in...");
        else if (e.getSource() == cancelBtn)
            System.exit(0);
    }
}
```

---

### 4. Method Reference *(Java 8+)*

```java
btn.addActionListener(this::handleSubmit);

// where handleSubmit is:
void handleSubmit(ActionEvent e) {
    System.out.println("Submitted!");
}
```

---

## 6. Action Command

The **action command** is a `String` identifier attached to a component — used to distinguish which component fired the event when a shared listener is used.

```
┌──────────────────────────────────────────────────────────────┐
│                  Action Command                              │
│                                                              │
│   Default action command = component label text              │
│   e.g. new JButton("Save") -> command = "Save"               │
│                                                              │
│   Set custom command:                                        │
│   btn.setActionCommand("save_file");                         │
│                                                              │
│   Read in listener:                                          │
│   e.getActionCommand()  ->  "save_file"                      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Action Command Flow**

> 💡 Always set **explicit action commands** instead of relying on label text — labels can change, commands should stay stable.

```java
JButton save   = new JButton("Save File");
JButton delete = new JButton("Delete File");
JButton rename = new JButton("Rename File");

save.setActionCommand("save");
delete.setActionCommand("delete");
rename.setActionCommand("rename");
```

---

## 7. Shared Listener — One Handler, Many Components

A **single ActionListener** can handle events from **multiple components** — identified by action command or source reference.

### Using Action Command *(preferred)*

```java
ActionListener handler = e -> {
    switch (e.getActionCommand()) {
        case "new"    -> createNewFile();
        case "open"   -> openFile();
        case "save"   -> saveFile();
        case "exit"   -> System.exit(0);
    }
};

JButton newBtn  = new JButton("New");   newBtn.setActionCommand("new");
JButton openBtn = new JButton("Open");  openBtn.setActionCommand("open");
JButton saveBtn = new JButton("Save");  saveBtn.setActionCommand("save");
JMenuItem exitItem = new JMenuItem("Exit"); exitItem.setActionCommand("exit");

newBtn.addActionListener(handler);
openBtn.addActionListener(handler);
saveBtn.addActionListener(handler);
exitItem.addActionListener(handler);
```

### Using getSource() *(when you have references)*

```java
public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();

    if (src == saveBtn)        saveFile();
    else if (src == deleteBtn) deleteFile();
    else if (src == exitItem)  System.exit(0);
}
```

```
┌──────────────────────────────────────────────────────────────┐
│             Shared Listener Pattern                          │
│                                                              │
│   JButton  "Save"    ─────┐                                  │
│   JButton  "Delete"  ─────┼──► ActionListener                │
│   JMenuItem "Exit"   ─────┘    actionPerformed(e) {          │
│                                   switch(e.getCommand())     │
│                                     case "save"   -> ...     │
│                                     case "delete" -> ...     │
│                                     case "exit"   -> ...     │
│                                 }                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Shared Listener with Action Commands**

---

## 8. Enabling and Disabling Actions

Components can be **enabled or disabled** dynamically — a disabled component does not fire events.

```java
JTextField userTF = new JTextField(15);
JTextField passTF = new JTextField(15);
JButton    loginBtn = new JButton("Login");

loginBtn.setEnabled(false);  // disabled by default

// Enable only when both fields have text
DocumentListener dl = new DocumentListener() {
    public void insertUpdate(DocumentEvent e)  { checkFields(); }
    public void removeUpdate(DocumentEvent e)  { checkFields(); }
    public void changedUpdate(DocumentEvent e) { checkFields(); }

    void checkFields() {
        boolean ready = !userTF.getText().isEmpty()
                     && !passTF.getText().isEmpty();
        loginBtn.setEnabled(ready);
    }
};

userTF.getDocument().addDocumentListener(dl);
passTF.getDocument().addDocumentListener(dl);
```

---

## 9. Action Interface — Reusable Actions

The `Action` interface (extends `ActionListener`) lets you **bundle command behavior, text, icon, and enabled state together** — attach the same action to both a button and a menu item.

```java
Action saveAction = new AbstractAction("Save") {
    {
        putValue(Action.SHORT_DESCRIPTION, "Save the file (Ctrl+S)");
        putValue(Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        putValue(Action.SMALL_ICON, new ImageIcon("save.png"));
    }

    public void actionPerformed(ActionEvent e) {
        saveFile();
    }
};

// Same Action object — shared behavior and state
JButton    saveBtn  = new JButton(saveAction);
JMenuItem  saveMI   = new JMenuItem(saveAction);
JToolBar   toolBar  = new JToolBar();
toolBar.add(saveAction);

// Disable everywhere at once
saveAction.setEnabled(false);   // button + menu item both grey out
```

```
┌──────────────────────────────────────────────────────────────┐
│              Action Interface — One Object, Many Uses        │
│                                                              │
│   AbstractAction("Save")                                     │
│       ├── text       = "Save"                                │
│       ├── icon       = save.png                              │
│       ├── tooltip    = "Save the file (Ctrl+S)"              │
│       ├── accelerator= Ctrl+S                                │
│       └── actionPerformed() = saveFile()                     │
│                │                                             │
│       ┌────────┼──────────────┐                              │
│       ▼        ▼              ▼                              │
│   JButton   JMenuItem    JToolBar                            │
│   [Save]    File > Save  [💾]                               │
│   (all share same state — disable one, all disabled)         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Action Interface Shared Across Components**

### Key Action Properties (`putValue` / `getValue`)

| Key Constant | Description |
|--------------|-------------|
| `Action.NAME` | Text label |
| `Action.SMALL_ICON` | Icon (16×16) |
| `Action.SHORT_DESCRIPTION` | Tooltip text |
| `Action.LONG_DESCRIPTION` | Long description |
| `Action.ACCELERATOR_KEY` | Keyboard shortcut |
| `Action.MNEMONIC_KEY` | Alt+key shortcut |
| `Action.SELECTED_KEY` | For toggle actions |

---

## 10. Practical Patterns

### Pattern 1 — Login Form Validation

```java
JTextField  userTF  = new JTextField(15);
JPasswordField passPF = new JPasswordField(15);
JLabel      errLabel = new JLabel(" ");
JButton     loginBtn = new JButton("Login");

loginBtn.addActionListener(e -> {
    String user = userTF.getText().trim();
    String pass = new String(passPF.getPassword());

    if (user.isEmpty() || pass.isEmpty()) {
        errLabel.setText("Fields cannot be empty!");
        errLabel.setForeground(Color.RED);
        return;
    }

    if (user.equals("admin") && pass.equals("1234")) {
        JOptionPane.showMessageDialog(null, "Welcome, " + user + "!");
    } else {
        errLabel.setText("Invalid credentials!");
        errLabel.setForeground(Color.RED);
        passPF.setText("");
    }
});
```

---

### Pattern 2 — Timer-based Animation / Countdown

```java
JLabel countLabel = new JLabel("10", JLabel.CENTER);
int[]  count      = {10};     // array trick for lambda mutation

Timer timer = new Timer(1000, e -> {
    count[0]--;
    countLabel.setText(String.valueOf(count[0]));

    if (count[0] <= 0) {
        ((Timer) e.getSource()).stop();
        countLabel.setText("Time's up!");
    }
});

JButton startBtn = new JButton("Start Countdown");
startBtn.addActionListener(e -> {
    count[0] = 10;
    timer.start();
    startBtn.setEnabled(false);
});
```

---

### Pattern 3 — Toolbar + Menu Item Share One Action

```java
Action cutAction = new AbstractAction("Cut") {
    public void actionPerformed(ActionEvent e) {
        // cut selected text
    }
};

JMenuBar  mb   = new JMenuBar();
JMenu     edit = new JMenu("Edit");
edit.add(new JMenuItem(cutAction));
mb.add(edit);

JToolBar toolbar = new JToolBar();
toolbar.add(new JButton(cutAction));
```

---

## 11. Summary

```
┌──────────────────────────────────────────────────────────────┐
│                 Action Events at a Glance                    │
│                                                              │
│   Fired by:  JButton, JMenuItem, JTextField (Enter),         │
│              JComboBox, JCheckBox, JRadioButton, Timer       │
│                                                              │
│   Interface:  ActionListener -> actionPerformed(ActionEvent) │
│                                                              │
│   Key info from event:                                       │
│     getSource()        -> which component fired              │
│     getActionCommand() -> string identifier                  │
│     getModifiers()     -> Ctrl/Shift/Alt held?               │
│     getWhen()          -> timestamp                          │
│                                                              │
│   Best practices:                                            │
│     Set explicit setActionCommand() — don't rely on labels   │
│     Use Action interface for toolbar + menu shared behavior  │
│     Use lambda for single-method, simple handlers            │
│     Disable components to prevent unwanted events            │
└──────────────────────────────────────────────────────────────┘
```

| Concept | Key Point |
|---------|-----------|
| `ActionListener` | Single-method interface — `actionPerformed()` |
| `ActionEvent` | Carries source, command, modifiers, timestamp |
| `getActionCommand()` | String to identify which component fired |
| `setActionCommand()` | Set explicit command — don't rely on label |
| Shared listener | One listener handles many components via switch |
| `getSource()` | Returns the component object that fired |
| `Action` interface | Bundle label + icon + tooltip + handler together |
| `AbstractAction` | Extend this — reusable across button, menu, toolbar |
| `Timer` | Fires `ActionEvent` repeatedly at fixed interval |
| `setEnabled(false)` | Prevents component from firing events |