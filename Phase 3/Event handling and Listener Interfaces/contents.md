# ⚡ Event Handling and Listener Interfaces in Java


---

## 📚 Table of Contents

- [1. What is Event Handling?](#1-what-is-event-handling)
- [2. Event Handling Model](#2-event-handling-model)
- [3. Three Players in Event Handling](#3-three-players-in-event-handling)
- [4. Event Classes](#4-event-classes)
- [5. Listener Interfaces](#5-listener-interfaces)
- [6. Ways to Implement Listeners](#6-ways-to-implement-listeners)
- [7. ActionListener](#7-actionlistener)
- [8. MouseListener & MouseMotionListener](#8-mouselistener--mousemotionlistener)
- [9. KeyListener](#9-keylistener)
- [10. WindowListener & WindowAdapter](#10-windowlistener--windowadapter)
- [11. ItemListener](#11-itemlistener)
- [12. ChangeListener](#12-changelistener)
- [13. FocusListener](#13-focuslistener)
- [14. Adapter Classes](#14-adapter-classes)
- [15. Event Handling Flow](#15-event-handling-flow)
- [16. Summary](#16-summary)

---

## 1. What is Event Handling?

**Event handling** is the mechanism that allows a Java GUI program to **respond to user interactions** — mouse clicks, key presses, window close, button press, etc.

> Without event handling, a GUI is just a static display. Event handling makes it **interactive**.

```
┌──────────────────────────────────────────────────────────────┐
│                  Event Handling Concept                      │
│                                                              │
│   User Action           Program Response                     │
│   ──────────────────    ────────────────────────             │
│   Click a button    ->  Submit a form                        │
│   Press a key       ->  Add character to input               │
│   Move the mouse    ->  Highlight a component                │
│   Close the window  ->  Save and exit                        │
│   Toggle checkbox   ->  Enable/disable a field               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — User Actions Mapped to Program Responses**

---

## 2. Event Handling Model

Java uses the **Delegation Event Model** — when an event occurs, it is **delegated (passed)** to a registered listener object that handles it.

```
┌──────────────────────────────────────────────────────────────┐
│              Delegation Event Model                          │
│                                                              │
│   1. User interacts with a component (source)                │
│            │                                                 │
│            ▼                                                 │
│   2. JVM creates an Event object                             │
│      (e.g. ActionEvent, MouseEvent, KeyEvent)                │
│            │                                                 │
│            ▼                                                 │
│   3. Event is passed to all registered Listeners             │
│            │                                                 │
│            ▼                                                 │
│   4. Listener handles the event (calls a method)             │
│            │                                                 │
│            ▼                                                 │
│   5. Program responds accordingly                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Delegation Event Model Flow**

> 🆚 **C++ vs Java** — C++ (Qt) uses **Signals & Slots**. Java uses the **Delegation Model** — event source delegates handling to a registered listener object. Java's approach is more explicit and interface-based.

---

## 3. Three Players in Event Handling

```
┌──────────────────────────────────────────────────────────────┐
│              Three Players in Event Handling                 │
│                                                              │
│   ┌─────────────────┐    ┌──────────────────┐                │
│   │   Event Source  │    │   Event Object   │                │
│   │                 │    │                  │                │
│   │  Component that │    │  Carries info    │                │
│   │  generates the  │───►│  about the event │                │
│   │  event          │    │  (what, where,   │                │
│   │                 │    │   when, who)     │                │
│   │  e.g. JButton   │    │  e.g. ActionEvent│                │
│   └─────────────────┘    └────────┬─────────┘                │
│                                   │  passed to               │
│                          ┌────────▼─────────┐                │
│                          │  Event Listener  │                │
│                          │                  │                │
│                          │  Object that     │                │
│                          │  handles the     │                │
│                          │  event           │                │
│                          │  implements      │                │
│                          │listener interface|                │
│                          └──────────────────┘                │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Source, Event Object, and Listener**

| Player | Role | Example |
|--------|------|---------|
| **Event Source** | Component that generates events | `JButton`, `JTextField`, `JFrame` |
| **Event Object** | Carries event information | `ActionEvent`, `MouseEvent`, `KeyEvent` |
| **Event Listener** | Handles the event | `ActionListener`, `MouseListener` |

---

## 4. Event Classes

All event classes are in `java.awt.event` and inherit from `java.util.EventObject`.

```
┌──────────────────────────────────────────────────────────────┐
│                  Event Class Hierarchy                       │
│                                                              │
│   java.util.EventObject                                      │
│   └── java.awt.AWTEvent                                      │
│       ├── ActionEvent     <- button click, menu item, Enter  │
│       ├── MouseEvent      <- click, press, release, move     │
│       ├── KeyEvent        <- key press, release, typed       │
│       ├── WindowEvent     <- open, close, minimize, focus    │
│       ├── ItemEvent       <- checkbox, combobox toggle       │
│       ├── FocusEvent      <- gained/lost focus               │
│       ├── TextEvent       <- text changed (AWT)              │
│       └── ComponentEvent  <- resize, move, show, hide        │
│   javax.swing.event                                          │
│       ├── ChangeEvent     <- slider, spinner, tab change     │
│       ├── ListSelectionEvent <- JList selection change       │
│       └── TreeSelectionEvent <- JTree node selection         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Event Class Hierarchy**

### Common Event Object Methods

| Method | Available In | Description |
|--------|-------------|-------------|
| `getSource()` | All events | Returns the source component |
| `getActionCommand()` | `ActionEvent` | Returns command string |
| `getX()`, `getY()` | `MouseEvent` | Mouse cursor position |
| `getButton()` | `MouseEvent` | Which mouse button |
| `getKeyCode()` | `KeyEvent` | Key code (e.g. `VK_ENTER`) |
| `getKeyChar()` | `KeyEvent` | Character typed |
| `getStateChange()` | `ItemEvent` | SELECTED or DESELECTED |
| `getValue()` | `ChangeEvent` | New slider/spinner value |

---

## 5. Listener Interfaces

Every event type has a corresponding **Listener Interface** that defines the methods you must implement to handle that event.

| Listener Interface | Event Handled | Key Methods |
|-------------------|---------------|-------------|
| `ActionListener` | Button click, Enter key, menu | `actionPerformed()` |
| `MouseListener` | Click, press, release, enter, exit | 5 methods |
| `MouseMotionListener` | Move, drag | `mouseMoved()`, `mouseDragged()` |
| `KeyListener` | Key press, release, typed | 3 methods |
| `WindowListener` | Open, close, minimize, etc. | 7 methods |
| `ItemListener` | Checkbox, combobox toggle | `itemStateChanged()` |
| `ChangeListener` | Slider, spinner, tabbed pane | `stateChanged()` |
| `FocusListener` | Focus gained/lost | `focusGained()`, `focusLost()` |
| `ListSelectionListener` | JList selection | `valueChanged()` |
| `TreeSelectionListener` | JTree node click | `valueChanged()` |
| `DocumentListener` | Text change in real-time | 3 methods |
| `AdjustmentListener` | Scrollbar change | `adjustmentValueChanged()` |

---

## 6. Ways to Implement Listeners

There are **four ways** to implement an event listener in Java:

### 1. 🔹 Anonymous Inner Class *(most common)*

```java
JButton btn = new JButton("Click Me");

btn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button clicked!");
    }
});
```

---

### 2. 🔹 Lambda Expression *(Java 8+ — cleanest)*

```java
btn.addActionListener(e -> System.out.println("Button clicked!"));

// Multi-line lambda
btn.addActionListener(e -> {
    System.out.println("Clicked: " + e.getActionCommand());
    updateUI();
});
```

> 💡 Works only for **functional interfaces** (one abstract method) — `ActionListener`, `ItemListener`, `ChangeListener`, etc.

---

### 3. 🔹 Implementing in the Class Itself

```java
public class MyFrame extends JFrame implements ActionListener {

    JButton btn = new JButton("Submit");

    MyFrame() {
        btn.addActionListener(this);   // 'this' is the listener
        add(btn);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Handled by: " + e.getSource());
    }
}
```

---

### 4. 🔹 Separate Listener Class

```java
class ButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        System.out.println("Separate class handled it");
    }
}

// Usage
btn.addActionListener(new ButtonHandler());
```

```
┌──────────────────────────────────────────────────────────────┐
│           Four Ways to Implement Listeners                   │
│                                                              │
│   Anonymous class  ->  most common, self-contained           │
│   Lambda           ->  cleanest, Java 8+, single method only │
│   Implement in class -> one class handles its own events     │
│   Separate class   ->  reusable across multiple components   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Four Ways to Implement Listeners**

---

## 7. ActionListener

`ActionListener` is the **most commonly used** listener — handles button clicks, menu items, Enter key in text fields.

> 📄 *Covered in full detail in `3.7 — Handling Action Events`*

```java
// Single listener for multiple components
JButton save   = new JButton("Save");
JButton delete = new JButton("Delete");

save.setActionCommand("save");
delete.setActionCommand("delete");

ActionListener handler = e -> {
    switch (e.getActionCommand()) {
        case "save"   -> System.out.println("Saving...");
        case "delete" -> System.out.println("Deleting...");
    }
};

save.addActionListener(handler);
delete.addActionListener(handler);
```

---

## 8. MouseListener & MouseMotionListener

### MouseListener — 5 Methods

```java
component.addMouseListener(new MouseListener() {
    public void mouseClicked(MouseEvent e)  { /* click (press+release) */ }
    public void mousePressed(MouseEvent e)  { /* button pressed down    */ }
    public void mouseReleased(MouseEvent e) { /* button released        */ }
    public void mouseEntered(MouseEvent e)  { /* cursor enters area     */ }
    public void mouseExited(MouseEvent e)   { /* cursor leaves area     */ }
});
```

**Useful MouseEvent methods:**

```java
e.getX(), e.getY()          // cursor position
e.getButton()               // MouseEvent.BUTTON1/2/3
e.getClickCount()           // 1 = single, 2 = double click
e.isPopupTrigger()          // true on right-click (OS-specific)
SwingUtilities.isLeftMouseButton(e)   // check left button
SwingUtilities.isRightMouseButton(e)  // check right button
```

### MouseMotionListener — 2 Methods

```java
component.addMouseMotionListener(new MouseMotionListener() {
    public void mouseMoved(MouseEvent e)   { /* mouse moved (no button) */ }
    public void mouseDragged(MouseEvent e) { /* mouse moved + button held */ }
});
```

### Practical Example

```java
panel.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2)       // double-click
            System.out.println("Double clicked at " + e.getX() + "," + e.getY());
        if (SwingUtilities.isRightMouseButton(e))
            popup.show(e.getComponent(), e.getX(), e.getY());
    }
    public void mouseEntered(MouseEvent e) {
        panel.setBackground(Color.LIGHT_GRAY);  // hover effect
    }
    public void mouseExited(MouseEvent e) {
        panel.setBackground(Color.WHITE);
    }
});
```

---

## 9. KeyListener

`KeyListener` captures **keyboard input** at the component level. The component must be **focused** to receive key events.

### Three Methods

| Method | When Called |
|--------|-------------|
| `keyPressed(KeyEvent e)` | Key is pressed down |
| `keyReleased(KeyEvent e)` | Key is released |
| `keyTyped(KeyEvent e)` | A character key is typed (no action keys) |

```java
textField.addKeyListener(new KeyAdapter() {
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            System.out.println("Enter pressed");

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            textField.setText("");
    }

    public void keyTyped(KeyEvent e) {
        // Allow only digits
        char c = e.getKeyChar();
        if (!Character.isDigit(c))
            e.consume();   // block the character
    }
});
```

### Common Key Codes

| Key | Constant |
|-----|----------|
| Enter | `KeyEvent.VK_ENTER` |
| Escape | `KeyEvent.VK_ESCAPE` |
| Backspace | `KeyEvent.VK_BACK_SPACE` |
| Delete | `KeyEvent.VK_DELETE` |
| Arrow keys | `VK_UP`, `VK_DOWN`, `VK_LEFT`, `VK_RIGHT` |
| F1–F12 | `VK_F1` … `VK_F12` |
| Ctrl | `VK_CONTROL` |
| Shift | `VK_SHIFT` |

> 💡 For global key bindings (not tied to focus), prefer **Key Bindings** via `getInputMap()` / `getActionMap()` over `KeyListener`.

---

## 10. WindowListener & WindowAdapter

`WindowListener` handles **window lifecycle events** — opening, closing, minimizing, etc.

### Seven Methods

| Method | When Called |
|--------|-------------|
| `windowOpened()` | Window first made visible |
| `windowClosing()` | User clicked close button (X) |
| `windowClosed()` | Window has been closed |
| `windowIconified()` | Window minimized |
| `windowDeiconified()` | Window restored from minimize |
| `windowActivated()` | Window gains focus |
| `windowDeactivated()` | Window loses focus |

```java
frame.addWindowListener(new WindowAdapter() {
    // Only override what you need (WindowAdapter has empty implementations)
    public void windowClosing(WindowEvent e) {
        int choice = JOptionPane.showConfirmDialog(frame,
            "Save before exit?", "Exit",
            JOptionPane.YES_NO_CANCEL_OPTION);
        if (choice == JOptionPane.YES_OPTION)    { save(); System.exit(0); }
        else if (choice == JOptionPane.NO_OPTION){ System.exit(0); }
        // Cancel -> stay open
    }

    public void windowIconified(WindowEvent e) {
        System.out.println("Window minimized");
    }
});
```

---

## 11. ItemListener

`ItemListener` fires when the **selection state changes** in `JCheckBox`, `JRadioButton`, or `JComboBox`.

```java
JCheckBox darkMode = new JCheckBox("Dark Mode");

darkMode.addItemListener(e -> {
    if (e.getStateChange() == ItemEvent.SELECTED)
        applyDarkTheme();
    else
        applyLightTheme();
});

JComboBox<String> lang = new JComboBox<>(new String[]{"Java","Python"});

lang.addItemListener(e -> {
    if (e.getStateChange() == ItemEvent.SELECTED)
        System.out.println("Now selected: " + e.getItem());
});
```

| Constant | Meaning |
|----------|---------|
| `ItemEvent.SELECTED` | Item was checked/selected |
| `ItemEvent.DESELECTED` | Item was unchecked/deselected |

---

## 12. ChangeListener

`ChangeListener` fires when the **value changes** in `JSlider`, `JSpinner`, or `JTabbedPane`.

```java
JSlider slider = new JSlider(0, 100, 50);

slider.addChangeListener(e -> {
    JSlider src = (JSlider) e.getSource();
    if (!src.getValueIsAdjusting())    // fire only on final value
        System.out.println("Volume: " + src.getValue());
});

JTabbedPane tabs = new JTabbedPane();
tabs.addChangeListener(e ->
    System.out.println("Switched to tab: " + tabs.getSelectedIndex()));
```

---

## 13. FocusListener

`FocusListener` fires when a component **gains or loses keyboard focus**.

```java
JTextField tf = new JTextField("Click here...");

tf.addFocusListener(new FocusAdapter() {
    public void focusGained(FocusEvent e) {
        if (tf.getText().equals("Click here..."))
            tf.setText("");              // clear placeholder on focus
        tf.setForeground(Color.BLACK);
    }

    public void focusLost(FocusEvent e) {
        if (tf.getText().isEmpty()) {
            tf.setText("Click here...");  // restore placeholder
            tf.setForeground(Color.GRAY);
        }
    }
});
```

> 💡 `FocusListener` is commonly used to implement **placeholder text** in text fields.

---

## 14. Adapter Classes

Many listener interfaces have **multiple methods** to implement — even if you only care about one. **Adapter classes** provide **empty default implementations** of all methods so you only override what you need.

```
┌──────────────────────────────────────────────────────────────┐
│                Listener  vs  Adapter                         │
│                                                              │
│   MouseListener (interface)    MouseAdapter (class)          │
│   ─────────────────────────    ───────────────────────       │
│   Must implement ALL 5         Override only what you need   │
│   mouseClicked()               mouseClicked() { }  <- empty  │
│   mousePressed()               mousePressed() { }  <- empty  │
│   mouseReleased()              mouseReleased(){ }  <- empty  │
│   mouseEntered()               mouseEntered() { }  <- empty  │
│   mouseExited()                mouseExited()  { }  <- empty  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Listener Interface vs Adapter Class**

### Available Adapter Classes

| Adapter Class | Replaces Interface |
|--------------|-------------------|
| `MouseAdapter` | `MouseListener` |
| `MouseMotionAdapter` | `MouseMotionListener` |
| `KeyAdapter` | `KeyListener` |
| `WindowAdapter` | `WindowListener` |
| `FocusAdapter` | `FocusListener` |
| `ComponentAdapter` | `ComponentListener` |

> 💡 `ActionListener`, `ItemListener`, `ChangeListener` have only **one method** — no adapter needed. Use a lambda instead.

---

## 15. Event Handling Flow

```
┌──────────────────────────────────────────────────────────────┐
│              Complete Event Handling Flow                    │
│                                                              │
│   ┌───────────────────────────────────────────────────────┐  │
│   │  Step 1: Register Listener                            │  │
│   │  button.addActionListener(listener);                  │  │
│   └───────────────────────────┬───────────────────────────┘  │
│                               │                              │
│   ┌───────────────────────────▼───────────────────────────┐  │
│   │  Step 2: User Triggers Event                          │  │
│   │  User clicks the button                               │  │
│   └───────────────────────────┬───────────────────────────┘  │
│                               │                              │
│   ┌───────────────────────────▼───────────────────────────┐  │
│   │  Step 3: JVM Creates Event Object                     │  │
│   │  new ActionEvent(source, id, command)                 │  │
│   └───────────────────────────┬───────────────────────────┘  │
│                               │                              │
│   ┌───────────────────────────▼───────────────────────────┐  │
│   │  Step 4: Event Dispatched to Listener                 │  │
│   │  listener.actionPerformed(event)                      │  │
│   └───────────────────────────┬───────────────────────────┘  │
│                               │                              │
│   ┌───────────────────────────▼───────────────────────────┐  │
│   │  Step 5: Listener Handles Event                       │  │
│   │  Your code runs inside actionPerformed()              │  │
│   └───────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — Complete Event Handling Flow**

### Event Dispatch Thread (EDT)

> All Swing GUI updates must happen on the **Event Dispatch Thread (EDT)**. Long-running tasks inside listeners block the EDT and freeze the UI.

```java
// Wrong — blocks UI
btn.addActionListener(e -> {
    Thread.sleep(5000);           // FREEZES the UI!
    label.setText("Done");
});

// Correct — use SwingWorker or invokeLater
btn.addActionListener(e -> {
    new Thread(() -> {
        // do heavy work here
        SwingUtilities.invokeLater(() ->
            label.setText("Done")  // update UI on EDT
        );
    }).start();
});
```

---

## 16. Summary

| Listener | Interface | Adapter | Handles |
|----------|-----------|---------|---------|
| `ActionListener` | ✅ | ❌ | Button, menu, Enter |
| `MouseListener` | ✅ | `MouseAdapter` | Click, enter, exit |
| `MouseMotionListener` | ✅ | `MouseMotionAdapter` | Move, drag |
| `KeyListener` | ✅ | `KeyAdapter` | Key press, release, type |
| `WindowListener` | ✅ | `WindowAdapter` | Open, close, minimize |
| `ItemListener` | ✅ | ❌ | Checkbox, combobox toggle |
| `ChangeListener` | ✅ | ❌ | Slider, spinner, tabs |
| `FocusListener` | ✅ | `FocusAdapter` | Focus gain/loss |
| `ListSelectionListener` | ✅ | ❌ | JList selection |

```
Delegation Model  ->  Source generates event -> Listener handles it

4 ways to implement:
  Anonymous class  ->  most common
  Lambda           ->  cleanest (Java 8+, single-method interfaces only)
  Implement in class -> self-handling components
  Separate class   ->  reusable handler

Use Adapter classes when listener has multiple methods
but you only need to override one or two.

Never do long tasks on the EDT — use SwingWorker or new Thread()
+ SwingUtilities.invokeLater() to update UI.
```