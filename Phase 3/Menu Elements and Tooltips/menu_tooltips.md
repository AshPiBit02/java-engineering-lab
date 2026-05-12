# 🍔 Menu Elements and Tooltips in Java Swing


---

## 📚 Table of Contents

- [1. Menu System Overview](#1-menu-system-overview)
- [2. JMenuBar](#2-jmenubar)
- [3. JMenu](#3-jmenu)
- [4. JMenuItem](#4-jmenuitem)
- [5. JCheckBoxMenuItem](#5-jcheckboxmenuitem)
- [6. JRadioButtonMenuItem](#6-jradiobuttonmenuitem)
- [7. JSeparator](#7-jseparator)
- [8. Submenus (Nested Menus)](#8-submenus-nested-menus)
- [9. Keyboard Mnemonics and Accelerators](#9-keyboard-mnemonics-and-accelerators)
- [10. JPopupMenu](#10-jpopupmenu)
- [11. Tooltips](#11-tooltips)
- [12. Summary](#12-summary)

---

## 1. Menu System Overview

Java Swing's menu system is built from a **hierarchy of components** — all attached to a `JFrame` via a `JMenuBar`.

```
┌──────────────────────────────────────────────────────────────┐
│                   Menu System Hierarchy                      │
│                                                              │
│   JFrame                                                     │
│   └── JMenuBar                    <- menu bar on frame       │
│       ├── JMenu ("File")          <- top-level menu          │
│       │   ├── JMenuItem ("New")   <- clickable item          │
│       │   ├── JMenuItem ("Open")                             │
│       │   ├── JSeparator          <- horizontal divider      │
│       │   ├── JCheckBoxMenuItem   <- toggleable item         │
│       │   ├── JMenu ("Export") <- submenu (nested JMenu)     │
│       │   │   ├── JMenuItem ("PDF")                          │
│       │   │   └── JMenuItem ("CSV")                          │
│       │   └── JMenuItem ("Exit")                             │
│       └── JMenu ("Edit")                                     │
│           ├── JMenuItem ("Cut")                              │
│           └── JRadioButtonMenuItem <- radio-style item       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Swing Menu System Hierarchy**

---

## 2. JMenuBar

A **JMenuBar** is the **horizontal bar** that sits at the top of a `JFrame` and holds all top-level menus.

```java
JMenuBar menuBar = new JMenuBar();

// Attach to frame
frame.setJMenuBar(menuBar);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(JMenu menu)` | Add a menu to the bar |
| `remove(JMenu menu)` | Remove a menu |
| `getMenu(int index)` | Get menu at index |
| `getMenuCount()` | Total number of menus |

> 💡 Use `frame.setJMenuBar()` — not `frame.add()` — to attach the menu bar.

---

## 3. JMenu

A **JMenu** is a **clickable label on the menu bar** that opens a drop-down list of items when clicked.

```java
JMenu fileMenu = new JMenu("File");
JMenu editMenu = new JMenu("Edit");
JMenu helpMenu = new JMenu("Help");

menuBar.add(fileMenu);
menuBar.add(editMenu);
menuBar.add(helpMenu);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(JMenuItem item)` | Add item to menu |
| `add(JMenu submenu)` | Add submenu |
| `addSeparator()` | Add a divider line |
| `remove(int index)` | Remove item at index |
| `getItem(int index)` | Get item at index |
| `getItemCount()` | Total items in menu |
| `setEnabled(boolean)` | Disable entire menu |
| `addMenuListener(MenuListener)` | Listen for open/close events |

---

## 4. JMenuItem

A **JMenuItem** is a **clickable item inside a menu**. It fires an `ActionEvent` when selected.

```java
JMenuItem newItem  = new JMenuItem("New");
JMenuItem openItem = new JMenuItem("Open");
JMenuItem saveItem = new JMenuItem("Save");
JMenuItem exitItem = new JMenuItem("Exit");

fileMenu.add(newItem);
fileMenu.add(openItem);
fileMenu.add(saveItem);
fileMenu.addSeparator();
fileMenu.add(exitItem);

// Event handling
exitItem.addActionListener(e -> System.exit(0));

newItem.addActionListener(e ->
    System.out.println("New file created"));
```

### Key Methods

| Method | Description |
|--------|-------------|
| `setText(String)` | Set item label |
| `setIcon(Icon)` | Set icon on item |
| `setEnabled(boolean)` | Enable/disable item |
| `setActionCommand(String)` | Set command for identification |
| `addActionListener(...)` | Click handler |

---

## 5. JCheckBoxMenuItem

A **JCheckBoxMenuItem** is a menu item with a **toggle checkbox** — stays checked/unchecked across interactions.

```java
JCheckBoxMenuItem autosave = new JCheckBoxMenuItem("Autosave", true);
JCheckBoxMenuItem toolbar  = new JCheckBoxMenuItem("Show Toolbar", true);
JCheckBoxMenuItem statusBar= new JCheckBoxMenuItem("Show Status Bar");

viewMenu.add(autosave);
viewMenu.add(toolbar);
viewMenu.add(statusBar);

autosave.addItemListener(e ->
    System.out.println("Autosave: " + autosave.isSelected()));
```

```
┌──────────────────────────────────────────────────────────────┐
│  View                                                        │
│  ├── ☑ Autosave          <- checked (true)                  │
│  ├── ☑ Show Toolbar      <- checked (true)                  │
│  └── ☐ Show Status Bar   <- unchecked (false)               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — JCheckBoxMenuItem in a Menu**

| Method | Description |
|--------|-------------|
| `isSelected()` | Returns `true` if checked |
| `setSelected(boolean)` | Set checked state |
| `addItemListener(...)` | Fires on toggle |

---

## 6. JRadioButtonMenuItem

A **JRadioButtonMenuItem** provides **mutually exclusive** menu items — only one can be selected at a time within a `ButtonGroup`.

```java
ButtonGroup themeGroup = new ButtonGroup();

JRadioButtonMenuItem light  = new JRadioButtonMenuItem("Light",  true);
JRadioButtonMenuItem dark   = new JRadioButtonMenuItem("Dark");
JRadioButtonMenuItem system = new JRadioButtonMenuItem("System Default");

themeGroup.add(light);
themeGroup.add(dark);
themeGroup.add(system);

viewMenu.addSeparator();
viewMenu.add(light);
viewMenu.add(dark);
viewMenu.add(system);

dark.addActionListener(e ->
    System.out.println("Dark theme applied"));
```

```
┌──────────────────────────────────────────────────────────────┐
│  View → Theme                                                │
│  ├── ◉ Light           <- selected                          │
│  ├── ○ Dark                                                  │
│  └── ○ System Default                                        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — JRadioButtonMenuItem Exclusive Selection**

---

## 7. JSeparator

A **JSeparator** is a **horizontal dividing line** inside a menu that visually groups related items.

```java
fileMenu.add(newItem);
fileMenu.add(openItem);
fileMenu.add(saveItem);
fileMenu.addSeparator();           // <-- divider line
fileMenu.add(exitItem);
```

```
┌──────────────────────────────────────────────────────────────┐
│  File                                                        │
│  ├── New                                                     │
│  ├── Open                                                    │
│  ├── Save                                                    │
│  ├── ─────────────────     <- JSeparator                     │
│  └── Exit                                                    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — JSeparator Dividing Menu Items**

---

## 8. Submenus (Nested Menus)

A **submenu** is created by adding a `JMenu` inside another `JMenu` — it shows an arrow and expands on hover.

```java
JMenu exportMenu = new JMenu("Export As");
exportMenu.add(new JMenuItem("PDF"));
exportMenu.add(new JMenuItem("CSV"));
exportMenu.add(new JMenuItem("Excel"));

fileMenu.add(exportMenu);          // add submenu to parent menu
```

```
┌──────────────────────────────────────────────────────────────┐
│  File                                                        │
│  ├── New                                                     │
│  ├── Open                                                    │
│  ├── Export As  ▶   ┌──────────────────┐                    │
│  │                  │  PDF             │                     │
│  │                  │  CSV             │                     │
│  │                  │  Excel           │                     │
│  │                  └──────────────────┘                     │
│  └── Exit                                                    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Nested Submenu**

---

## 9. Keyboard Mnemonics and Accelerators

### Mnemonics

A **mnemonic** allows opening a menu or triggering a menu item using **Alt + key**.

```java
JMenu fileMenu = new JMenu("File");
fileMenu.setMnemonic(KeyEvent.VK_F);      // Alt+F opens File menu

JMenuItem saveItem = new JMenuItem("Save");
saveItem.setMnemonic(KeyEvent.VK_S);      // Alt+S triggers Save
```

The matching letter is **underlined** in the menu label automatically.

---

### Accelerators (Keyboard Shortcuts)

An **accelerator** triggers a menu item directly using a **key combination** (e.g., Ctrl+S) without opening the menu.

```java
saveItem.setAccelerator(
    KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

newItem.setAccelerator(
    KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

undoItem.setAccelerator(
    KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
```

```
┌──────────────────────────────────────────────────────────────┐
│  Mnemonic  vs  Accelerator                                   │
│                                                              │
│   Mnemonic           Accelerator                             │
│   ───────────────    ──────────────────────────              │
│   Alt + key          Ctrl/Shift/Alt + key combo              │
│   Opens menu first   Works without opening menu              │
│   Shows underline    Shows shortcut on menu item             │
│   setMnemonic(key)   setAccelerator(KeyStroke)               │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Mnemonic vs Accelerator**

---

## 10. JPopupMenu

A **JPopupMenu** is a **context menu** that appears at the cursor position — typically on **right-click**.

```java
JPopupMenu popup = new JPopupMenu();

popup.add(new JMenuItem("Cut"));
popup.add(new JMenuItem("Copy"));
popup.add(new JMenuItem("Paste"));
popup.addSeparator();
popup.add(new JMenuItem("Select All"));

// Attach to a component
textArea.addMouseListener(new MouseAdapter() {
    public void mousePressed(MouseEvent e)  { showPopup(e); }
    public void mouseReleased(MouseEvent e) { showPopup(e); }

    void showPopup(MouseEvent e) {
        if (e.isPopupTrigger())
            popup.show(e.getComponent(), e.getX(), e.getY());
    }
});
```

> 💡 Always handle **both** `mousePressed` and `mouseReleased` — the popup trigger differs between Windows (released) and macOS/Linux (pressed).

```
┌──────────────────────────────────────────────────────────────┐
│                 JPopupMenu — Right Click                     │
│                                                              │
│  ┌────────────────────────────────────┐                      │
│  │  Main Content Area                 │                      │
│  │              ┌───────────────────┐ │                      │
│  │  right-click │  Cut              │ │                      │
│  │  here  --->  │  Copy             │ │                      │
│  │              │  Paste            │ │                      │
│  │              │  ─────────────    │ │                      │
│  │              │  Select All       │ │                      │
│  │              └───────────────────┘ │                      │
│  └────────────────────────────────────┘                      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — JPopupMenu on Right-Click**

### JPopupMenu Key Methods

| Method | Description |
|--------|-------------|
| `add(JMenuItem item)` | Add item |
| `addSeparator()` | Add divider |
| `show(Component c, int x, int y)` | Display at position |
| `setLabel(String)` | Set popup title (optional) |
| `isPopupTrigger()` | Check if event is right-click |

---

## 11. Tooltips

A **tooltip** is a **small pop-up hint** that appears when the user **hovers** over a component. Built directly into `JComponent` — no extra class needed.

```java
JButton save = new JButton("Save");
save.setToolTipText("Save the current file (Ctrl+S)");

JTextField name = new JTextField(15);
name.setToolTipText("Enter your full name");

JSlider volume = new JSlider(0, 100, 50);
volume.setToolTipText("Drag to adjust volume");
```

```
┌──────────────────────────────────────────────────────────────┐
│                      Tooltip Behavior                        │
│                                                              │
│   ┌───────────────────────────────────┐                      │
│   │  [  Save  ]   [  Open  ]          │                      │
│   │       ^                           │                      │
│   │       | hover                     │                      │
│   │  ┌────────────────────────────┐   │                      │
│   │  │ Save the current file      │   │ <- tooltip appears   │
│   │  │ (Ctrl+S)                   │   │                      │
│   │  └────────────────────────────┘   │                      │
│   └───────────────────────────────────┘                      │
└──────────────────────────────────────────────────────────────┘
``` 

> **Fig. 8 — Tooltip on Hover**

### Customizing Tooltip Timing

```java
ToolTipManager ttm = ToolTipManager.sharedInstance();
ttm.setInitialDelay(300);    // ms before tooltip appears (default 750)
ttm.setDismissDelay(5000);   // ms before tooltip disappears (default 4000)
ttm.setReshowDelay(100);     // ms to show tooltip again after moving away
```

### HTML in Tooltips

```java
btn.setToolTipText(
    "<html><b>Save</b><br>Saves the current file<br>" +
    "<i>Shortcut: Ctrl+S</i></html>");
```

> 💡 Tooltips support **HTML formatting** — useful for multi-line or styled hints.

---

## 12. Summary

```
┌──────────────────────────────────────────────────────────────┐
│                Menu System at a Glance                       │
│                                                              │
│   JMenuBar           ->  horizontal bar on JFrame            │
│   JMenu              ->  drop-down menu on bar               │
│   JMenuItem          ->  clickable item, fires ActionEvent   │
│   JCheckBoxMenuItem  ->  toggleable item (stays checked)     │
│   JRadioButtonMenuItem -> exclusive item (use ButtonGroup)   │
│   JSeparator         ->  horizontal dividing line            │
│   Submenu            ->  JMenu added inside JMenu            │
│   JPopupMenu         ->  right-click context menu            │
│   Mnemonic           ->  Alt+key opens menu/item             │
│   Accelerator        ->  Ctrl+key triggers without opening   │
│   Tooltip            ->  hover hint on ANY JComponent        │
└──────────────────────────────────────────────────────────────┘
```

| Element | Class | Key Feature |
|---------|-------|-------------|
| Menu bar | `JMenuBar` | Attach with `setJMenuBar()` |
| Menu | `JMenu` | Groups related items |
| Item | `JMenuItem` | Click → `ActionEvent` |
| Toggle item | `JCheckBoxMenuItem` | Remembers checked state |
| Exclusive item | `JRadioButtonMenuItem` | Use with `ButtonGroup` |
| Divider | `addSeparator()` | Visual grouping line |
| Submenu | `JMenu` inside `JMenu` | Nested with arrow indicator |
| Context menu | `JPopupMenu` | Right-click — handle both press + release |
| Shortcut | `setAccelerator(KeyStroke)` | Works without opening menu |
| Alt shortcut | `setMnemonic(KeyEvent.VK_X)` | Opens menu/item via Alt+key |
| Hover hint | `setToolTipText(String)` | On any `JComponent` — supports HTML |