# 🧩 AWT Components in Java

---

## 📚 Table of Contents

- [1. What are AWT Components?](#1-what-are-awt-components)
- [2. AWT Component Hierarchy](#2-awt-component-hierarchy)
- [3. Common Component Methods](#3-common-component-methods)
- [4. Label](#4-label)
- [5. Button](#5-button)
- [6. TextField](#6-textfield)
- [7. TextArea](#7-textarea)
- [8. Checkbox](#8-checkbox)
- [9. CheckboxGroup (Radio Buttons)](#9-checkboxgroup-radio-buttons)
- [10. Choice](#10-choice)
- [11. List](#11-list)
- [12. Scrollbar](#12-scrollbar)
- [13. Canvas](#13-canvas)
- [14. Panel](#14-panel)
- [15. Frame](#15-frame)
- [16. Dialog](#16-dialog)
- [17. FileDialog](#17-filedialog)
- [18. MenuBar, Menu & MenuItem](#18-menubar-menu--menuitem)
- [19. PopupMenu](#19-popupmenu)
- [20. Complete AWT Components Reference Table](#20-complete-awt-components-reference-table)
- [21. AWT vs Swing Components](#21-awt-vs-swing-components)
- [22. Full Working AWT Example](#22-full-working-awt-example)
- [23. Summary](#23-summary)

---

## 1. What are AWT Components?

AWT **Components** are the basic visual building blocks used to create a GUI in Java. Every visible element — a button, a text box, a label — is a component.

> All AWT components are **heavyweight** — each is backed by a native OS peer object that handles rendering.

```
┌──────────────────────────────────────────────────────────────┐
│                   AWT Component Types                        │
│                                                              │
│         ┌──────────────────────────────────┐                 │
│         │      java.awt.Component          │                 │
│         └──────────────────┬───────────────┘                 │
│                            │                                 │
│           ┌────────────────┴──────────────────┐              │
│           │                                   │              │
│  ┌────────▼────────┐                ┌─────────▼────────┐     │
│  │  Basic/Atomic   │                │    Container     │     │
│  │  Components     │                │    Components    │     │
│  │                 │                │                  │     │
│  │  Label          │                │  Panel           │     │
│  │  Button         │                │  Frame           │     │
│  │  TextField      │                │  Dialog          │     │
│  │  TextArea       │                │  FileDialog      │     │
│  │  Checkbox       │                │  Window          │     │
│  │  Choice         │                │  ScrollPane      │     │
│  │  List           │                │                  │     │
│  │  Scrollbar      │                │                  │     │
│  │  Canvas         │                │                  │     │
│  └─────────────────┘                └──────────────────┘     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — AWT Component Types**

---

## 2. AWT Component Hierarchy

```
┌──────────────────────────────────────────────────────────────┐
│                 AWT Class Hierarchy                          │
│                                                              │
│   java.lang.Object                                           │
│   └── java.awt.Component                                     │
│       ├── Button                                             │
│       ├── Canvas                                             │
│       ├── Checkbox                                           │
│       ├── Choice                                             │
│       ├── Label                                              │
│       ├── List                                               │
│       ├── Scrollbar                                          │
│       ├── TextComponent                                      │
│       │   ├── TextField                                      │
│       │   └── TextArea                                       │
│       └── Container                                          │
│           ├── Panel                                          │
│           │   └── Applet                                     │
│           ├── ScrollPane                                     │
│           └── Window                                         │
│               ├── Frame                                      │
│               │   └── FileDialog (special)                   │
│               └── Dialog                                     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Full AWT Class Hierarchy**

---

## 3. Common Component Methods

Every AWT component inherits these methods from `java.awt.Component`:

| Method | Description |
|--------|-------------|
| `setSize(int w, int h)` | Set width and height |
| `setLocation(int x, int y)` | Set position on screen |
| `setBounds(int x, int y, int w, int h)` | Set position + size |
| `setVisible(boolean b)` | Show or hide component |
| `setEnabled(boolean b)` | Enable or disable |
| `setBackground(Color c)` | Set background color |
| `setForeground(Color c)` | Set text/foreground color |
| `setFont(Font f)` | Set font |
| `getWidth()` | Get width |
| `getHeight()` | Get height |
| `getName()` / `setName(String)` | Get/set component name |
| `repaint()` | Trigger repaint |
| `addMouseListener(...)` | Listen for mouse events |
| `addKeyListener(...)` | Listen for keyboard events |

---

## 4. Label

A **Label** displays a **read-only, non-editable** text string on the screen. Users cannot interact with it.

### Syntax

```java
Label label = new Label();                          // empty label
Label label = new Label(String text);               // with text
Label label = new Label(String text, int alignment);// with alignment
```

### Alignment Constants

| Constant | Value | Description |
|----------|-------|-------------|
| `Label.LEFT` | 0 | Left-align text (default) |
| `Label.CENTER` | 1 | Center-align text |
| `Label.RIGHT` | 2 | Right-align text |

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` | Returns the label text |
| `setText(String text)` | Sets/updates the label text |
| `getAlignment()` | Returns current alignment |
| `setAlignment(int align)` | Sets text alignment |

### Example

```java
import java.awt.*;

public class LabelDemo extends Frame {
    LabelDemo() {
        setLayout(null);

        Label l1 = new Label("Username:");
        l1.setBounds(30, 50, 100, 30);

        Label l2 = new Label("Status: Active", Label.CENTER);
        l2.setBounds(30, 100, 150, 30);
        l2.setForeground(Color.GREEN);
        l2.setFont(new Font("Arial", Font.BOLD, 14));

        add(l1);
        add(l2);

        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) { new LabelDemo(); }
}
```

### Use Cases
- Form field labels (`Username:`, `Password:`)
- Status messages
- Headings and descriptions
- Displaying dynamic text values

---

## 5. Button

A **Button** is a clickable component that **triggers an action** when pressed. It fires an `ActionEvent` when clicked.

### Syntax

```java
Button btn = new Button();              // empty button
Button btn = new Button(String label);  // with label text
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getLabel()` | Returns button text |
| `setLabel(String label)` | Sets button text |
| `addActionListener(ActionListener l)` | Registers click handler |
| `removeActionListener(ActionListener l)` | Removes click handler |
| `setEnabled(boolean b)` | Enable/disable button |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class ButtonDemo extends Frame {
    ButtonDemo() {
        setLayout(null);

        Button submit = new Button("Submit");
        submit.setBounds(50, 80, 100, 35);
        submit.setBackground(Color.BLUE);
        submit.setForeground(Color.WHITE);

        // Event handling
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked!");
            }
        });

        add(submit);
        setSize(300, 200);
        setVisible(true);
    }

    public static void main(String[] args) { new ButtonDemo(); }
}
```

### Use Cases
- Form submit / cancel buttons
- Navigation (Next, Back, Finish)
- Triggering operations (Save, Delete, Search)
- Dialog confirmation (OK, Yes, No)

---

## 6. TextField

A **TextField** is a **single-line** text input component. The user can type, edit, and submit text through it.

### Syntax

```java
TextField tf = new TextField();             // empty
TextField tf = new TextField(int columns);  // with column width
TextField tf = new TextField(String text);  // with default text
TextField tf = new TextField(String text, int columns); // both
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` | Returns current text |
| `setText(String t)` | Sets text content |
| `setColumns(int cols)` | Sets visible width |
| `setEchoChar(char c)` | Masks input (e.g. password `'*'`) |
| `setEditable(boolean b)` | Allow/disallow editing |
| `select(int start, int end)` | Selects text range |
| `selectAll()` | Selects all text |
| `addActionListener(...)` | Fires on Enter key press |
| `addTextListener(...)` | Fires on every character change |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class TextFieldDemo extends Frame {
    TextFieldDemo() {
        setLayout(null);

        Label   lbl  = new Label("Name:");
        lbl.setBounds(30, 50, 60, 25);

        TextField tf = new TextField(20);
        tf.setBounds(100, 50, 150, 25);

        // Password field — echo char
        TextField pass = new TextField(20);
        pass.setBounds(100, 90, 150, 25);
        pass.setEchoChar('*');

        Button btn = new Button("Submit");
        btn.setBounds(100, 130, 80, 30);
        btn.addActionListener(e ->
            System.out.println("Name: " + tf.getText())
        );

        add(lbl); add(tf); add(pass); add(btn);
        setSize(350, 220);
        setVisible(true);
    }

    public static void main(String[] args) { new TextFieldDemo(); }
}
```

### Use Cases
- Login forms (username/password)
- Search bars
- Data entry forms
- Input fields requiring single-line text

---

## 7. TextArea

A **TextArea** is a **multi-line** text input component. Used when the user needs to enter or display large amounts of text.

### Syntax

```java
TextArea ta = new TextArea();                           // empty
TextArea ta = new TextArea(int rows, int cols);         // dimensions
TextArea ta = new TextArea(String text);                // with default text
TextArea ta = new TextArea(String text, int rows, int cols); // both
TextArea ta = new TextArea(String text, int rows, int cols, int scrollbars);
```

### Scrollbar Constants

| Constant | Description |
|----------|-------------|
| `TextArea.SCROLLBARS_BOTH` | Horizontal + Vertical (default) |
| `TextArea.SCROLLBARS_VERTICAL_ONLY` | Only vertical scrollbar |
| `TextArea.SCROLLBARS_HORIZONTAL_ONLY` | Only horizontal scrollbar |
| `TextArea.SCROLLBARS_NONE` | No scrollbars |

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` | Returns all text |
| `setText(String t)` | Replaces all text |
| `append(String t)` | Appends text at end |
| `insert(String t, int pos)` | Inserts text at position |
| `replaceRange(String t, int s, int e)` | Replaces text in range |
| `getRows()` / `getColumns()` | Get dimensions |
| `setEditable(boolean b)` | Allow/disallow editing |
| `getCaretPosition()` | Returns cursor position |

### Example

```java
import java.awt.*;

public class TextAreaDemo extends Frame {
    TextAreaDemo() {
        setLayout(null);

        Label lbl = new Label("Description:");
        lbl.setBounds(30, 30, 100, 25);

        TextArea ta = new TextArea("Enter text here...", 6, 30,
                                    TextArea.SCROLLBARS_VERTICAL_ONLY);
        ta.setBounds(30, 60, 280, 120);

        Button clear = new Button("Clear");
        clear.setBounds(30, 195, 80, 30);
        clear.addActionListener(e -> ta.setText(""));

        add(lbl); add(ta); add(clear);
        setSize(350, 280);
        setVisible(true);
    }

    public static void main(String[] args) { new TextAreaDemo(); }
}
```

### Use Cases
- Comment / description fields
- Log/output display areas
- Code editors
- Message composition (email body, notes)

---

## 8. Checkbox

A **Checkbox** is a toggle component — it can be either **checked (true)** or **unchecked (false)**. Used for boolean on/off selections.

### Syntax

```java
Checkbox cb = new Checkbox();                            // unchecked
Checkbox cb = new Checkbox(String label);                // with label
Checkbox cb = new Checkbox(String label, boolean state); // with default state
Checkbox cb = new Checkbox(String label, boolean state, CheckboxGroup group);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getLabel()` | Returns checkbox label |
| `setLabel(String label)` | Sets checkbox label |
| `getState()` | Returns `true` if checked |
| `setState(boolean state)` | Set checked/unchecked |
| `getCheckboxGroup()` | Returns associated group |
| `addItemListener(ItemListener l)` | Fires when state changes |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class CheckboxDemo extends Frame {
    CheckboxDemo() {
        setLayout(null);

        Checkbox java   = new Checkbox("Java",   true);
        Checkbox python = new Checkbox("Python", false);
        Checkbox cpp    = new Checkbox("C++",    false);

        java.setBounds(30, 50, 100, 25);
        python.setBounds(30, 80, 100, 25);
        cpp.setBounds(30, 110, 100, 25);

        java.addItemListener(e ->
            System.out.println("Java: " + java.getState())
        );

        add(java); add(python); add(cpp);
        setSize(250, 200);
        setVisible(true);
    }

    public static void main(String[] args) { new CheckboxDemo(); }
}
```

### Use Cases
- Feature toggles (Enable notifications, Dark mode)
- Multi-select options (Skills, Interests)
- Terms & Conditions acceptance
- Settings panels

---

## 9. CheckboxGroup (Radio Buttons)

A **CheckboxGroup** makes checkboxes behave like **radio buttons** — only **one can be selected** at a time within the group.

### Syntax

```java
CheckboxGroup group = new CheckboxGroup();
Checkbox cb = new Checkbox(String label, boolean state, CheckboxGroup group);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getSelectedCheckbox()` | Returns currently selected Checkbox |
| `setSelectedCheckbox(Checkbox cb)` | Programmatically selects a checkbox |

### Example

```java
import java.awt.*;

public class RadioDemo extends Frame {
    RadioDemo() {
        setLayout(null);

        Label lbl = new Label("Select Gender:");
        lbl.setBounds(30, 30, 120, 25);

        CheckboxGroup group = new CheckboxGroup();

        Checkbox male   = new Checkbox("Male",   true,  group);
        Checkbox female = new Checkbox("Female", false, group);
        Checkbox other  = new Checkbox("Other",  false, group);

        male.setBounds(30,  60, 100, 25);
        female.setBounds(30, 90, 100, 25);
        other.setBounds(30, 120, 100, 25);

        Button btn = new Button("Submit");
        btn.setBounds(30, 155, 80, 30);
        btn.addActionListener(e ->
            System.out.println("Selected: " +
                group.getSelectedCheckbox().getLabel())
        );

        add(lbl); add(male); add(female); add(other); add(btn);
        setSize(250, 230);
        setVisible(true);
    }

    public static void main(String[] args) { new RadioDemo(); }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│      Checkbox  vs  CheckboxGroup (Radio)                     │
│                                                              │
│   Checkbox (independent)      CheckboxGroup (radio)          │
│   ─────────────────────       ──────────────────────────     │
│   ☑ Java                      ◉ Male                        │
│   ☑ Python   ← multiple ok    ○ Female  ← only one          │
│   ☐ C++                       ○ Other                       │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Checkbox vs CheckboxGroup**

### Use Cases
- Gender selection
- Payment method (Cash, Card, UPI)
- Subscription plan (Basic, Pro, Enterprise)
- Priority selection (Low, Medium, High)

---

## 10. Choice

A **Choice** is a **drop-down list** (combo box) that shows one item at a time and expands when clicked to reveal all options.

### Syntax

```java
Choice choice = new Choice();
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(String item)` | Adds item to list |
| `insert(String item, int index)` | Inserts item at index |
| `remove(String item)` | Removes item by name |
| `remove(int index)` | Removes item by index |
| `removeAll()` | Removes all items |
| `getItem(int index)` | Returns item at index |
| `getSelectedItem()` | Returns selected item text |
| `getSelectedIndex()` | Returns selected item index |
| `getItemCount()` | Returns total items |
| `select(int index)` | Programmatically selects item |
| `addItemListener(ItemListener l)` | Fires on selection change |

### Example

```java
import java.awt.*;

public class ChoiceDemo extends Frame {
    ChoiceDemo() {
        setLayout(null);

        Label lbl = new Label("Country:");
        lbl.setBounds(30, 50, 70, 25);

        Choice country = new Choice();
        country.add("Nepal");
        country.add("India");
        country.add("USA");
        country.add("UK");
        country.add("Japan");
        country.setBounds(110, 50, 150, 25);

        Button btn = new Button("Get Selected");
        btn.setBounds(30, 100, 120, 30);
        btn.addActionListener(e ->
            System.out.println("Selected: " + country.getSelectedItem())
        );

        add(lbl); add(country); add(btn);
        setSize(320, 200);
        setVisible(true);
    }

    public static void main(String[] args) { new ChoiceDemo(); }
}
```

### Use Cases
- Country / State / City selection
- Category filters
- Sort order selection
- Font or color pickers

---

## 11. List

A **List** is a **scrollable list** that displays multiple items and allows **single or multiple selections**. Unlike Choice, it shows several items at once.

### Syntax

```java
List list = new List();                         // single select
List list = new List(int rows);                 // visible rows
List list = new List(int rows, boolean multiSelect); // multi-select
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(String item)` | Adds item at end |
| `add(String item, int index)` | Adds item at index |
| `remove(String item)` | Removes item by name |
| `remove(int index)` | Removes item by index |
| `removeAll()` | Clears all items |
| `getItem(int index)` | Returns item at index |
| `getSelectedItem()` | Returns selected item (single) |
| `getSelectedItems()` | Returns all selected items (multi) |
| `getSelectedIndex()` | Returns selected index |
| `getSelectedIndexes()` | Returns all selected indexes |
| `getItemCount()` | Total number of items |
| `isIndexSelected(int i)` | Check if index is selected |
| `addActionListener(...)` | Fires on double-click |
| `addItemListener(...)` | Fires on selection change |

### Example

```java
import java.awt.*;

public class ListDemo extends Frame {
    ListDemo() {
        setLayout(null);

        Label lbl = new Label("Select Skills:");
        lbl.setBounds(30, 30, 100, 25);

        // Multi-select list
        List skills = new List(5, true);
        skills.add("Java");
        skills.add("Python");
        skills.add("JavaScript");
        skills.add("SQL");
        skills.add("HTML/CSS");
        skills.add("Spring Boot");
        skills.setBounds(30, 60, 180, 100);

        Button btn = new Button("Get Selected");
        btn.setBounds(30, 175, 120, 30);
        btn.addActionListener(e -> {
            String[] selected = skills.getSelectedItems();
            for (String s : selected)
                System.out.println("Selected: " + s);
        });

        add(lbl); add(skills); add(btn);
        setSize(280, 260);
        setVisible(true);
    }

    public static void main(String[] args) { new ListDemo(); }
}
```

```
┌──────────────────────────────────────────────────────────────┐
│        Choice  vs  List                                      │
│                                                              │
│   Choice (dropdown)          List (scrollable)               │
│   ──────────────────         ──────────────────────          │
│   Shows 1 item               Shows multiple items            │
│   Expands on click           Always expanded                 │
│   Single select only         Single or Multi-select          │
│   Space-efficient            Takes more space                │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Choice vs List**

### Use Cases
- Multi-select skill pickers
- File/folder browser lists
- Contact lists
- Cart item selection

---

## 12. Scrollbar

A **Scrollbar** is a slider-like component used to **scroll content** or **select a numeric value** within a defined range.

### Syntax

```java
Scrollbar sb = new Scrollbar();                             // default vertical
Scrollbar sb = new Scrollbar(int orientation);              // H or V
Scrollbar sb = new Scrollbar(int orientation, int value,
                              int visible, int min, int max); // full
```

### Orientation Constants

| Constant | Description |
|----------|-------------|
| `Scrollbar.HORIZONTAL` | Horizontal scrollbar |
| `Scrollbar.VERTICAL` | Vertical scrollbar (default) |

### Key Methods

| Method | Description |
|--------|-------------|
| `getValue()` | Returns current value |
| `setValue(int v)` | Sets current value |
| `getMinimum()` | Returns min value |
| `getMaximum()` | Returns max value |
| `setMinimum(int min)` | Sets min value |
| `setMaximum(int max)` | Sets max value |
| `setUnitIncrement(int v)` | Small step (arrow click) |
| `setBlockIncrement(int v)` | Large step (track click) |
| `addAdjustmentListener(...)` | Fires when value changes |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class ScrollbarDemo extends Frame {
    Label valueLabel;

    ScrollbarDemo() {
        setLayout(null);

        // Volume control scrollbar (0 to 100)
        Scrollbar sb = new Scrollbar(Scrollbar.HORIZONTAL, 50, 10, 0, 110);
        sb.setBounds(30, 60, 250, 20);
        sb.setUnitIncrement(1);
        sb.setBlockIncrement(10);

        valueLabel = new Label("Volume: 50");
        valueLabel.setBounds(30, 30, 150, 25);

        sb.addAdjustmentListener(e ->
            valueLabel.setText("Volume: " + sb.getValue())
        );

        add(valueLabel); add(sb);
        setSize(330, 160);
        setVisible(true);
    }

    public static void main(String[] args) { new ScrollbarDemo(); }
}
```

### Use Cases
- Volume / brightness sliders
- Zoom controls
- Scrolling through content
- Range input (age, price filter)

---

## 13. Canvas

A **Canvas** is a **blank rectangular area** used for **custom drawing and rendering** — graphics, shapes, images, animations.

### Syntax

```java
Canvas canvas = new Canvas();
```

> You must **subclass Canvas** and override its `paint(Graphics g)` method to draw on it.

### Key Methods

| Method | Description |
|--------|-------------|
| `paint(Graphics g)` | Override to draw custom graphics |
| `repaint()` | Triggers a repaint |
| `getGraphics()` | Returns Graphics context |
| `setSize(int w, int h)` | Set canvas size |
| `addMouseListener(...)` | Mouse events on canvas |

### Graphics Drawing Methods

| Method | Description |
|--------|-------------|
| `g.drawLine(x1,y1,x2,y2)` | Draw a line |
| `g.drawRect(x,y,w,h)` | Draw rectangle outline |
| `g.fillRect(x,y,w,h)` | Draw filled rectangle |
| `g.drawOval(x,y,w,h)` | Draw oval/circle outline |
| `g.fillOval(x,y,w,h)` | Draw filled oval/circle |
| `g.drawString(str,x,y)` | Draw text |
| `g.drawImage(img,x,y,obs)` | Draw image |
| `g.setColor(Color c)` | Set drawing color |
| `g.setFont(Font f)` | Set text font |

### Example

```java
import java.awt.*;

class MyCanvas extends Canvas {
    @Override
    public void paint(Graphics g) {
        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw shapes
        g.setColor(Color.RED);
        g.fillOval(30, 30, 80, 80);          // filled circle

        g.setColor(Color.BLUE);
        g.drawRect(150, 30, 100, 80);        // rectangle outline

        g.setColor(Color.GREEN);
        g.fillRect(290, 30, 80, 80);         // filled rectangle

        g.setColor(Color.BLACK);
        g.drawLine(30, 150, 370, 150);       // horizontal line

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("AWT Canvas Drawing", 80, 200);
    }
}

public class CanvasDemo extends Frame {
    CanvasDemo() {
        MyCanvas canvas = new MyCanvas();
        canvas.setSize(420, 230);
        add(canvas);
        setSize(440, 270);
        setVisible(true);
    }

    public static void main(String[] args) { new CanvasDemo(); }
}
```

### Use Cases
- Custom charts and graphs
- Drawing applications (paint tools)
- Game rendering (simple 2D games)
- Image display and manipulation
- Signature/drawing pads

---

## 14. Panel

A **Panel** is the simplest **container** in AWT — used to **group and organize** other components. It has no title bar or border by default.

### Syntax

```java
Panel panel = new Panel();                          // default FlowLayout
Panel panel = new Panel(LayoutManager layout);      // custom layout
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(Component c)` | Adds a component |
| `remove(Component c)` | Removes a component |
| `setLayout(LayoutManager lm)` | Sets layout manager |
| `getLayout()` | Returns layout manager |
| `getComponentCount()` | Returns number of children |
| `getComponent(int i)` | Returns child at index |

### Example

```java
import java.awt.*;

public class PanelDemo extends Frame {
    PanelDemo() {
        setLayout(new BorderLayout());

        // Top panel — title
        Panel topPanel = new Panel();
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.add(new Label("  Student Registration Form  "));

        // Center panel — form fields
        Panel formPanel = new Panel(new GridLayout(3, 2, 10, 10));
        formPanel.add(new Label("Name:"));
        formPanel.add(new TextField(15));
        formPanel.add(new Label("Age:"));
        formPanel.add(new TextField(5));
        formPanel.add(new Label("Email:"));
        formPanel.add(new TextField(20));

        // Bottom panel — buttons
        Panel btnPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(new Button("Submit"));
        btnPanel.add(new Button("Cancel"));

        add(topPanel,  BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(btnPanel,  BorderLayout.SOUTH);

        setSize(400, 220);
        setVisible(true);
    }

    public static void main(String[] args) { new PanelDemo(); }
}
```

### Use Cases
- Grouping related form fields
- Creating sections in a window
- Building complex multi-zone layouts
- Organizing buttons in a toolbar-style row

---

## 15. Frame

A **Frame** is a **top-level window** with a title bar, border, and the standard minimize / maximize / close buttons. It is the **main window** for AWT applications.

### Syntax

```java
Frame frame = new Frame();              // no title
Frame frame = new Frame(String title);  // with title
```

### Key Methods

| Method | Description |
|--------|-------------|
| `setTitle(String t)` | Sets window title |
| `getTitle()` | Returns window title |
| `setSize(int w, int h)` | Sets window dimensions |
| `setLocation(int x, int y)` | Sets window position |
| `setResizable(boolean b)` | Allow/disallow resize |
| `setVisible(boolean b)` | Show/hide window |
| `setLayout(LayoutManager lm)` | Sets layout manager |
| `setBackground(Color c)` | Sets background color |
| `setIconImage(Image img)` | Sets window icon |
| `dispose()` | Destroys window and releases resources |
| `addWindowListener(...)` | Listen for window events |

### Closing a Frame

By default, clicking the close button does **nothing** in AWT. You must handle it manually:

```java
frame.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();    // just close this frame
        // OR
        System.exit(0);     // close entire application
    }
});
```

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class FrameDemo extends Frame {
    FrameDemo() {
        setTitle("My AWT Application");
        setSize(400, 300);
        setLocation(300, 200);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout());
        setResizable(true);

        add(new Label("Welcome to AWT!"));
        add(new Button("OK"));

        // Handle close button
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) { new FrameDemo(); }
}
```

### Use Cases
- Main application window
- Standalone AWT application entry point
- Secondary windows (settings, about)

---

## 16. Dialog

A **Dialog** is a **secondary popup window** associated with a parent Frame. Used for messages, confirmations, and data input. Can be **modal** (blocks parent) or **non-modal**.

### Syntax

```java
Dialog d = new Dialog(Frame parent, String title, boolean modal);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `setTitle(String t)` | Sets dialog title |
| `setModal(boolean b)` | Set modal/non-modal |
| `isModal()` | Check if modal |
| `setSize(int w, int h)` | Set size |
| `setVisible(boolean b)` | Show/hide dialog |
| `dispose()` | Close and destroy dialog |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class DialogDemo extends Frame {
    DialogDemo() {
        setSize(300, 200);
        setTitle("Main Window");
        setLayout(new FlowLayout());

        Button showDialog = new Button("Show Dialog");
        showDialog.addActionListener(e -> {
            Dialog dialog = new Dialog(this, "Confirmation", true);
            dialog.setLayout(new FlowLayout());
            dialog.setSize(250, 150);

            dialog.add(new Label("Are you sure you want to proceed?"));

            Button yes = new Button("Yes");
            Button no  = new Button("No");

            yes.addActionListener(ev -> {
                System.out.println("Confirmed!");
                dialog.dispose();
            });
            no.addActionListener(ev -> dialog.dispose());

            dialog.add(yes);
            dialog.add(no);
            dialog.setVisible(true);
        });

        add(showDialog);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        setVisible(true);
    }

    public static void main(String[] args) { new DialogDemo(); }
}
```

### Use Cases
- Confirmation dialogs (Delete? Save?)
- Error/warning messages
- Custom input dialogs
- Settings windows

---

## 17. FileDialog

A **FileDialog** is a **built-in OS file chooser dialog** — lets the user **browse and select files** using the native OS file picker.

### Syntax

```java
FileDialog fd = new FileDialog(Frame parent, String title, int mode);
```

### Mode Constants

| Constant | Value | Description |
|----------|-------|-------------|
| `FileDialog.LOAD` | 0 | Open file mode |
| `FileDialog.SAVE` | 1 | Save file mode |

### Key Methods

| Method | Description |
|--------|-------------|
| `getDirectory()` | Returns selected directory path |
| `getFile()` | Returns selected filename |
| `setDirectory(String dir)` | Sets initial directory |
| `setFile(String file)` | Sets default filename |
| `setFilenameFilter(FilenameFilter f)` | Filter file types |
| `getMode()` | Returns LOAD or SAVE mode |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class FileDialogDemo extends Frame {
    FileDialogDemo() {
        setLayout(new FlowLayout());
        setSize(300, 150);
        setTitle("File Dialog Demo");

        Button openBtn = new Button("Open File");
        Button saveBtn = new Button("Save File");
        Label  result  = new Label("No file selected");

        openBtn.addActionListener(e -> {
            FileDialog fd = new FileDialog(this, "Open File",
                                            FileDialog.LOAD);
            fd.setVisible(true);
            if (fd.getFile() != null)
                result.setText(fd.getDirectory() + fd.getFile());
        });

        saveBtn.addActionListener(e -> {
            FileDialog fd = new FileDialog(this, "Save File",
                                            FileDialog.SAVE);
            fd.setVisible(true);
            if (fd.getFile() != null)
                result.setText("Saved: " + fd.getFile());
        });

        add(openBtn); add(saveBtn); add(result);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        setVisible(true);
    }

    public static void main(String[] args) { new FileDialogDemo(); }
}
```

### Use Cases
- File open dialogs (text editors, image viewers)
- Save as dialogs
- Import/export functionality
- Batch file processing tools

---

## 18. MenuBar, Menu & MenuItem

AWT provides a full **menu system** — a `MenuBar` sits at the top of a `Frame` and holds `Menu` objects, each containing `MenuItem` entries.

```
┌──────────────────────────────────────────────────────────────┐
│                  Menu Structure                              │
│                                                              │
│   Frame                                                      │
│   └── MenuBar                                                │
│       ├── Menu ("File")                                      │
│       │   ├── MenuItem ("New")                               │
│       │   ├── MenuItem ("Open")                              │
│       │   ├── MenuItem ("Save")                              │
│       │   ├── MenuSeparator  (--- divider ---)               │
│       │   └── MenuItem ("Exit")                              │
│       └── Menu ("Edit")                                      │
│           ├── MenuItem ("Cut")                               │
│           ├── MenuItem ("Copy")                              │
│           └── MenuItem ("Paste")                             │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Menu Hierarchy Structure**

### Syntax

```java
MenuBar  mb   = new MenuBar();
Menu     menu = new Menu(String label);
MenuItem item = new MenuItem(String label);

menu.add(item);
mb.add(menu);
frame.setMenuBar(mb);
```

### Key Classes & Methods

| Class | Key Methods | Description |
|-------|-------------|-------------|
| `MenuBar` | `add(Menu m)`, `remove(Menu m)` | Holds menus |
| `Menu` | `add(MenuItem i)`, `addSeparator()`, `remove(int i)` | Holds items |
| `MenuItem` | `setLabel(String)`, `setEnabled(boolean)`, `addActionListener()` | Clickable item |
| `CheckboxMenuItem` | `getState()`, `setState(boolean)` | Toggleable menu item |

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class MenuDemo extends Frame {
    MenuDemo() {
        setTitle("Menu Demo");
        setSize(400, 300);

        MenuBar mb = new MenuBar();

        // File Menu
        Menu fileMenu = new Menu("File");
        MenuItem newItem   = new MenuItem("New");
        MenuItem openItem  = new MenuItem("Open");
        MenuItem saveItem  = new MenuItem("Save");
        MenuItem exitItem  = new MenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();           // --- divider ---
        fileMenu.add(exitItem);

        // Edit Menu
        Menu editMenu = new Menu("Edit");
        editMenu.add(new MenuItem("Cut"));
        editMenu.add(new MenuItem("Copy"));
        editMenu.add(new MenuItem("Paste"));

        // View Menu with CheckboxMenuItem
        Menu viewMenu = new Menu("View");
        CheckboxMenuItem toolbar = new CheckboxMenuItem("Show Toolbar", true);
        viewMenu.add(toolbar);

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(viewMenu);
        setMenuBar(mb);

        exitItem.addActionListener(e -> System.exit(0));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        setVisible(true);
    }

    public static void main(String[] args) { new MenuDemo(); }
}
```

### Use Cases
- Application menu bars (File, Edit, View, Help)
- Context menus
- Toolbar menus
- Settings access

---

## 19. PopupMenu

A **PopupMenu** is a **context menu** that appears at a specific position (usually on right-click).

### Syntax

```java
PopupMenu popup = new PopupMenu();
popup.add(new MenuItem("Option"));
component.add(popup);
popup.show(component, x, y);
```

### Example

```java
import java.awt.*;
import java.awt.event.*;

public class PopupMenuDemo extends Frame {
    PopupMenuDemo() {
        setSize(400, 300);
        setTitle("Right-click for menu");

        PopupMenu popup = new PopupMenu();
        popup.add(new MenuItem("Cut"));
        popup.add(new MenuItem("Copy"));
        popup.add(new MenuItem("Paste"));
        popup.addSeparator();
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        popup.add(exit);

        add(popup);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    popup.show(e.getComponent(), e.getX(), e.getY());
            }
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger())
                    popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
        setVisible(true);
    }

    public static void main(String[] args) { new PopupMenuDemo(); }
}
```

### Use Cases
- Right-click context menus
- Quick action menus on canvas
- Table row actions (Edit, Delete)

---

## 20. Complete AWT Components Reference Table

| Component | Class | Type | Interactive | Use |
|-----------|-------|------|-------------|-----|
| Label | `Label` | Atomic | ❌ | Display static/dynamic text |
| Button | `Button` | Atomic | ✅ | Trigger actions on click |
| TextField | `TextField` | Atomic | ✅ | Single-line text input |
| TextArea | `TextArea` | Atomic | ✅ | Multi-line text input |
| Checkbox | `Checkbox` | Atomic | ✅ | Toggle on/off selections |
| Radio Button | `Checkbox` + `CheckboxGroup` | Atomic | ✅ | Exclusive single selection |
| Choice | `Choice` | Atomic | ✅ | Drop-down single select |
| List | `List` | Atomic | ✅ | Scrollable single/multi select |
| Scrollbar | `Scrollbar` | Atomic | ✅ | Scroll/range value selector |
| Canvas | `Canvas` | Atomic | ✅ | Custom drawing surface |
| Panel | `Panel` | Container | ❌ | Group/organize components |
| Frame | `Frame` | Container | ❌ | Main top-level window |
| Dialog | `Dialog` | Container | ❌ | Popup secondary window |
| FileDialog | `FileDialog` | Container | ✅ | OS file browser |
| MenuBar | `MenuBar` | Menu | ❌ | Holds menus on frame |
| Menu | `Menu` | Menu | ❌ | Holds menu items |
| MenuItem | `MenuItem` | Menu | ✅ | Clickable menu action |
| CheckboxMenuItem | `CheckboxMenuItem` | Menu | ✅ | Toggleable menu item |
| PopupMenu | `PopupMenu` | Menu | ✅ | Right-click context menu |

---

## 21. AWT vs Swing Components

| AWT Component | Swing Equivalent | Improvement |
|---------------|-----------------|-------------|
| `Label` | `JLabel` | Supports HTML, icons |
| `Button` | `JButton` | Icons, custom border, rollover |
| `TextField` | `JTextField` | Better styling, document model |
| `TextArea` | `JTextArea` | Used with `JScrollPane` |
| `Checkbox` | `JCheckBox` | Icons, better events |
| `CheckboxGroup` | `JRadioButton` + `ButtonGroup` | Cleaner API |
| `Choice` | `JComboBox` | Editable, custom renderer |
| `List` | `JList` | Custom cell renderer, models |
| `Scrollbar` | `JScrollBar` / `JSlider` | Better visual |
| `Canvas` | `JPanel` (override `paintComponent`) | Double buffering |
| `Panel` | `JPanel` | Much more flexible |
| `Frame` | `JFrame` | Richer content pane |
| `Dialog` | `JDialog` | More features |
| `FileDialog` | `JFileChooser` | Cross-platform, filters |
| `MenuBar` | `JMenuBar` | Icons, separators |

---

## 22. Full Working AWT Example

A complete AWT form bringing all major components together:

```java
import java.awt.*;
import java.awt.event.*;

public class CompleteAWTForm extends Frame {

    CompleteAWTForm() {
        setTitle("Student Registration - AWT");
        setSize(450, 500);
        setLayout(null);

        // --- Labels & TextFields ---
        new Label("Name:").setBounds(30, 30, 80, 25);
        add(new Label("Name:"));
        ((Label) getComponent(0)).setBounds(30, 30, 80, 25);

        // Simpler approach with variables
        Label nameL  = new Label("Name:");       nameL.setBounds(30, 30, 80, 25);
        Label emailL = new Label("Email:");      emailL.setBounds(30, 70, 80, 25);
        Label ageL   = new Label("Age:");        ageL.setBounds(30, 110, 80, 25);
        Label genderL= new Label("Gender:");     genderL.setBounds(30, 150, 80, 25);
        Label skillL = new Label("Skills:");     skillL.setBounds(30, 200, 80, 25);
        Label countryL=new Label("Country:");    countryL.setBounds(30, 310, 80, 25);
        Label bioL   = new Label("Bio:");        bioL.setBounds(30, 350, 80, 25);

        TextField nameTF  = new TextField(20);  nameTF.setBounds(120, 30, 200, 25);
        TextField emailTF = new TextField(20);  emailTF.setBounds(120, 70, 200, 25);
        TextField ageTF   = new TextField(5);   ageTF.setBounds(120, 110, 60, 25);

        CheckboxGroup genderGroup = new CheckboxGroup();
        Checkbox male   = new Checkbox("Male",   true, genderGroup);
        Checkbox female = new Checkbox("Female", false, genderGroup);
        male.setBounds(120, 150, 70, 25);
        female.setBounds(200, 150, 80, 25);

        List skills = new List(4, true);
        skills.add("Java"); skills.add("Python");
        skills.add("SQL"); skills.add("HTML");
        skills.setBounds(120, 200, 200, 80);

        Choice country = new Choice();
        country.add("Nepal"); country.add("India"); country.add("USA");
        country.setBounds(120, 310, 150, 25);

        TextArea bio = new TextArea(3, 25);
        bio.setBounds(120, 350, 200, 70);

        Button submit = new Button("Register");
        Button reset  = new Button("Reset");
        submit.setBounds(120, 440, 90, 30);
        reset.setBounds(230, 440, 90, 30);
        submit.setBackground(Color.GREEN);
        reset.setBackground(Color.ORANGE);

        submit.addActionListener(e ->
            System.out.println("Registered: " + nameTF.getText())
        );
        reset.addActionListener(e -> {
            nameTF.setText(""); emailTF.setText("");
            ageTF.setText(""); bio.setText("");
        });

        // Add all components
        add(nameL); add(emailL); add(ageL); add(genderL);
        add(skillL); add(countryL); add(bioL);
        add(nameTF); add(emailTF); add(ageTF);
        add(male); add(female); add(skills);
        add(country); add(bio);
        add(submit); add(reset);

        // Menu
        MenuBar mb = new MenuBar();
        Menu file = new Menu("File");
        file.add(new MenuItem("New"));
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);
        mb.add(file);
        setMenuBar(mb);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });

        setVisible(true);
    }

    public static void main(String[] args) { new CompleteAWTForm(); }
}
```

---

## 23. Summary

| Component | Class | Key Feature |
|-----------|-------|-------------|
| **Label** | `Label` | Read-only text display |
| **Button** | `Button` | Click to trigger `ActionEvent` |
| **TextField** | `TextField` | Single-line text input, echo char for password |
| **TextArea** | `TextArea` | Multi-line input with scrollbars |
| **Checkbox** | `Checkbox` | Boolean toggle |
| **Radio Button** | `Checkbox` + `CheckboxGroup` | Exclusive selection in group |
| **Choice** | `Choice` | Compact drop-down list |
| **List** | `List` | Scrollable single/multi-select list |
| **Scrollbar** | `Scrollbar` | Value range slider |
| **Canvas** | `Canvas` | Custom graphics drawing area |
| **Panel** | `Panel` | Group and organize components |
| **Frame** | `Frame` | Main AWT top-level window |
| **Dialog** | `Dialog` | Modal/non-modal popup |
| **FileDialog** | `FileDialog` | Native OS file picker |
| **MenuBar/Menu/MenuItem** | — | Full menu system |
| **PopupMenu** | `PopupMenu` | Right-click context menu |

```
AWT Components
├── Atomic  → display or collect data  (Label, Button, TextField...)
├── Container → hold and organize      (Panel, Frame, Dialog...)
└── Menu    → navigation and actions   (MenuBar, Menu, MenuItem...)
```