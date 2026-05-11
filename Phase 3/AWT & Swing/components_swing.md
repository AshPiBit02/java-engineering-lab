# 🎨 Swing Components in Java


---

## 📚 Table of Contents

- [1. What are Swing Components?](#1-what-are-swing-components)
- [2. Swing Component Hierarchy](#2-swing-component-hierarchy)
- [3. JComponent — The Base](#3-jcomponent--the-base)
- [4. JLabel](#4-jlabel)
- [5. JButton](#5-jbutton)
- [6. JTextField](#6-jtextfield)
- [7. JPasswordField](#7-jpasswordfield)
- [8. JTextArea](#8-jtextarea)
- [9. JCheckBox](#9-jcheckbox)
- [10. JRadioButton & ButtonGroup](#10-jradiobutton--buttongroup)
- [11. JComboBox](#11-jcombobox)
- [12. JList](#12-jlist)
- [13. JSlider](#13-jslider)
- [14. JProgressBar](#14-jprogressbar)
- [15. JSpinner](#15-jspinner)
- [16. JTable](#16-jtable)
- [17. JTree](#17-jtree)
- [18. JFrame](#18-jframe)
- [19. JPanel](#19-jpanel)
- [20. JScrollPane](#20-jscrollpane)
- [21. JTabbedPane](#21-jtabbedpane)
- [22. JSplitPane](#22-jsplitpane)
- [23. JDialog](#23-jdialog)
- [24. JOptionPane](#24-joptionpane)
- [25. JFileChooser](#25-jfilechooser)
- [26. JColorChooser](#26-jcolorchooser)
- [27. JMenuBar, JMenu & JMenuItem](#27-jmenubar-jmenu--jmenuitem)
- [28. JToolBar](#28-jtoolbar)
- [29. JToolTip](#29-jtooltip)
- [30. Complete Swing Components Reference Table](#30-complete-swing-components-reference-table)
- [31. Swing vs AWT Components](#31-swing-vs-awt-components)
- [32. Full Working Swing Application](#32-full-working-swing-application)
- [33. Summary](#33-summary)

---

## 1. What are Swing Components?

Swing components are **lightweight, platform-independent GUI elements** that are drawn entirely by Java using the **Java 2D API** — not by the OS. They are part of the **Java Foundation Classes (JFC)** under the `javax.swing` package.

> All Swing component class names start with **`J`** — `JButton`, `JLabel`, `JFrame`, etc.

```
┌──────────────────────────────────────────────────────────────┐
│                Swing Component Categories                    │
│                                                              │
│  ┌─────────────────┐  ┌─────────────────┐                    │
│  │  Basic/Atomic   │  │   Containers    │                    │
│  │  Components     │  │                 │                    │
│  │                 │  │  JFrame         │                    │
│  │  JLabel         │  │  JPanel         │                    │
│  │  JButton        │  │  JScrollPane    │                    │
│  │  JTextField     │  │  JTabbedPane    │                    │
│  │  JTextArea      │  │  JSplitPane     │                    │
│  │  JCheckBox      │  │  JDialog        │                    │
│  │  JRadioButton   │  └─────────────────┘                    │
│  │  JComboBox      │  ┌─────────────────┐                    │
│  │  JList          │  │  Dialogs        │                    │
│  │  JSlider        │  │                 │                    │
│  │  JProgressBar   │  │  JOptionPane    │                    │
│  │  JSpinner       │  │  JFileChooser   │                    │
│  │  JTable         │  │  JColorChooser  │                    │
│  │  JTree          │  └─────────────────┘                    │
│  └─────────────────┘  ┌─────────────────┐                    │
│                       │  Menu & Toolbar │                    │
│                       │  JMenuBar       │                    │
│                       │  JMenu          │                    │
│                       │  JMenuItem      │                    │
│                       │  JToolBar       │                    │
│                       └─────────────────┘                    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Swing Component Categories**

---

## 2. Swing Component Hierarchy

```
┌──────────────────────────────────────────────────────────────┐
│                  Swing Class Hierarchy                       │
│                                                              │
│   java.lang.Object                                           │
│   └── java.awt.Component                                     │
│       └── java.awt.Container                                 │
│           ├── java.awt.Window                                │
│           │   ├── java.awt.Frame                             │
│           │   │   └── JFrame          ← main window          │
│           │   ├── java.awt.Dialog                            │
│           │   │   └── JDialog         ← popup dialog         │
│           │   └── JWindow             ← borderless window    │
│           ├── java.awt.Panel                                 │
│           │   └── java.applet.Applet                         │
│           │       └── JApplet                                │
│           └── JComponent              ← base of all Swing    │
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
│               │   ├── JTextArea                              │
│               │   └── JEditorPane                            │
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
│               ├── JSpinner                                   │
│               ├── JToolBar                                   │
│               ├── JMenuBar                                   │
│               └── JOptionPane                                │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Full Swing Class Hierarchy**

---

## 3. JComponent — The Base

`JComponent` is the **base class** of all Swing lightweight components. It extends `java.awt.Container` and adds a rich set of features over AWT.

### Features Added by JComponent

| Feature | Description |
|---------|-------------|
| **Double buffering** | Eliminates flickering during repaint |
| **Borders** | Rich border support via `setBorder()` |
| **Tooltips** | `setToolTipText()` — built-in |
| **Pluggable L&F** | Supports Look and Feel switching |
| **Accessibility** | Screen reader support built-in |
| **Key bindings** | `getInputMap()` + `getActionMap()` |
| **Opacity control** | `setOpaque(boolean)` |
| **Custom painting** | Override `paintComponent(Graphics g)` |

### Common JComponent Methods

| Method | Description |
|--------|-------------|
| `setBorder(Border b)` | Set border around component |
| `setToolTipText(String s)` | Set hover tooltip |
| `setOpaque(boolean b)` | Fill background if true |
| `setPreferredSize(Dimension d)` | Hint preferred size to layout |
| `setMinimumSize(Dimension d)` | Set minimum size |
| `setMaximumSize(Dimension d)` | Set maximum size |
| `paintComponent(Graphics g)` | Override for custom drawing |
| `revalidate()` | Re-layout after dynamic changes |
| `repaint()` | Schedule a repaint |
| `getInsets()` | Returns border insets |
| `putClientProperty(key, val)` | Store arbitrary key-value data |

---

## 4. JLabel

A **JLabel** displays **text, an image, or both**. It is non-interactive — purely for display.

> 💡 Unlike AWT `Label`, `JLabel` supports **HTML formatting** and **icon images**.

### Syntax

```java
JLabel lbl = new JLabel();
JLabel lbl = new JLabel(String text);
JLabel lbl = new JLabel(Icon icon);
JLabel lbl = new JLabel(String text, Icon icon, int horizontalAlignment);
```

### Alignment Constants

| Constant | Value |
|----------|-------|
| `SwingConstants.LEFT` | Left align |
| `SwingConstants.CENTER` | Center align |
| `SwingConstants.RIGHT` | Right align |
| `SwingConstants.TOP` | Top align |
| `SwingConstants.BOTTOM` | Bottom align |

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` | Returns label text |
| `setText(String t)` | Sets label text |
| `setIcon(Icon icon)` | Sets image icon |
| `getIcon()` | Returns current icon |
| `setHorizontalAlignment(int a)` | Horizontal text alignment |
| `setVerticalAlignment(int a)` | Vertical text alignment |
| `setIconTextGap(int gap)` | Gap between icon and text |
| `setLabelFor(Component c)` | Associate label with a component |
| `setDisplayedMnemonic(char c)` | Keyboard shortcut char |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JLabelDemo extends JFrame {
    JLabelDemo() {
        setLayout(new FlowLayout());

        // Simple text label
        JLabel textLabel = new JLabel("Username:");

        // HTML formatted label
        JLabel htmlLabel = new JLabel(
            "<html><b>Welcome</b> to <i>Swing</i>!</html>"
        );

        // Label with icon
        JLabel iconLabel = new JLabel("User",
            new ImageIcon("user.png"), SwingConstants.LEFT);

        // Styled label
        JLabel styledLabel = new JLabel("Status: Online");
        styledLabel.setForeground(Color.GREEN);
        styledLabel.setFont(new Font("Arial", Font.BOLD, 14));
        styledLabel.setOpaque(true);
        styledLabel.setBackground(Color.BLACK);

        add(textLabel);
        add(htmlLabel);
        add(iconLabel);
        add(styledLabel);

        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JLabelDemo(); }
}
```

### Use Cases
- Form field labels
- Status messages and indicators
- Image display
- HTML-formatted rich text display
- Headings with icons

---

## 5. JButton

A **JButton** is a **clickable push button** that fires an `ActionEvent` when pressed. Far richer than AWT's `Button` — supports icons, mnemonics, and custom rendering.

### Syntax

```java
JButton btn = new JButton();
JButton btn = new JButton(String text);
JButton btn = new JButton(Icon icon);
JButton btn = new JButton(String text, Icon icon);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` / `setText(String)` | Get/set button text |
| `getIcon()` / `setIcon(Icon)` | Get/set button icon |
| `setMnemonic(int key)` | Keyboard shortcut (Alt+key) |
| `setToolTipText(String)` | Hover tooltip |
| `setEnabled(boolean)` | Enable/disable |
| `setBackground(Color)` | Background color |
| `setForeground(Color)` | Text color |
| `addActionListener(ActionListener)` | Click handler |
| `setActionCommand(String)` | Set command string for events |
| `doClick()` | Programmatically click the button |
| `setRolloverIcon(Icon)` | Icon on mouse hover |
| `setPressedIcon(Icon)` | Icon when pressed |
| `setBorderPainted(boolean)` | Show/hide border |
| `setContentAreaFilled(boolean)` | Show/hide fill background |
| `setFocusPainted(boolean)` | Show/hide focus border |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JButtonDemo extends JFrame {
    JButtonDemo() {
        setLayout(new FlowLayout());

        JButton submit = new JButton("Submit");
        submit.setBackground(new Color(70, 130, 180));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 13));
        submit.setMnemonic('S');                   // Alt+S triggers click
        submit.setToolTipText("Click to submit");

        JButton cancel = new JButton("Cancel");
        cancel.setEnabled(false);                  // disabled button

        JButton iconBtn = new JButton("Save",
            new ImageIcon("save.png"));            // icon + text

        submit.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Submitted!")
        );

        add(submit); add(cancel); add(iconBtn);

        setSize(350, 120);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JButtonDemo(); }
}
```

### Use Cases
- Form submission
- Navigation (Next, Back)
- Toolbar actions
- Dialog confirmation (OK, Cancel, Yes, No)

---

## 6. JTextField

A **JTextField** is a **single-line text input** component. Fires `ActionEvent` on Enter key press.

### Syntax

```java
JTextField tf = new JTextField();
JTextField tf = new JTextField(int columns);
JTextField tf = new JTextField(String text);
JTextField tf = new JTextField(String text, int columns);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` | Get current text |
| `setText(String t)` | Set text content |
| `setColumns(int n)` | Set visible width |
| `setEditable(boolean b)` | Allow/disallow editing |
| `setHorizontalAlignment(int a)` | Text alignment |
| `selectAll()` | Select all text |
| `select(int start, int end)` | Select text range |
| `getDocument()` | Returns underlying document model |
| `setFont(Font f)` | Set input font |
| `addActionListener(...)` | Fires on Enter key |
| `addCaretListener(...)` | Fires on cursor move |
| `addKeyListener(...)` | Low-level key events |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JTextFieldDemo extends JFrame {
    JTextFieldDemo() {
        setLayout(new GridLayout(3, 2, 10, 10));
        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(new JLabel("Username:"));
        JTextField userTF = new JTextField(15);
        add(userTF);

        add(new JLabel("Email:"));
        JTextField emailTF = new JTextField("user@example.com");
        emailTF.setForeground(Color.GRAY);
        add(emailTF);

        add(new JLabel("Age:"));
        JTextField ageTF = new JTextField(5);
        ageTF.setHorizontalAlignment(JTextField.CENTER);
        add(ageTF);

        setSize(350, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JTextFieldDemo(); }
}
```

### Use Cases
- Login forms
- Search bars
- Data entry fields
- Filter inputs

---

## 7. JPasswordField

A **JPasswordField** is a **specialized JTextField** that **masks input** characters for secure password entry.

### Syntax

```java
JPasswordField pf = new JPasswordField();
JPasswordField pf = new JPasswordField(int columns);
JPasswordField pf = new JPasswordField(String text);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getPassword()` | Returns `char[]` — secure way to get password |
| `getText()` | Returns password as String *(deprecated)* |
| `setEchoChar(char c)` | Set masking character (default `*`) |
| `getEchoChar()` | Returns echo character |

> ⚠️ Always use `getPassword()` which returns `char[]` — not `getText()`. After use, fill the array with zeros to clear sensitive data from memory.

### Example

```java
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JPasswordFieldDemo extends JFrame {
    JPasswordFieldDemo() {
        setLayout(new GridLayout(3, 2, 10, 10));
        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(15,15,15,15));

        add(new JLabel("Password:"));
        JPasswordField pf = new JPasswordField(15);
        pf.setEchoChar('●');
        add(pf);

        add(new JLabel("Confirm:"));
        JPasswordField confirm = new JPasswordField(15);
        add(confirm);

        add(new JLabel(""));
        JButton login = new JButton("Login");
        login.addActionListener(e -> {
            char[] pass    = pf.getPassword();
            char[] confArr = confirm.getPassword();
            if (Arrays.equals(pass, confArr))
                JOptionPane.showMessageDialog(this, "Passwords match!");
            else
                JOptionPane.showMessageDialog(this, "Passwords don't match!");
            Arrays.fill(pass, '0');     // clear sensitive data
            Arrays.fill(confArr, '0');
        });
        add(login);

        setSize(320, 140);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JPasswordFieldDemo(); }
}
```

---

## 8. JTextArea

A **JTextArea** is a **multi-line text input** component. Always wrap it in a `JScrollPane` for scrolling.

### Syntax

```java
JTextArea ta = new JTextArea();
JTextArea ta = new JTextArea(int rows, int cols);
JTextArea ta = new JTextArea(String text);
JTextArea ta = new JTextArea(String text, int rows, int cols);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getText()` | Returns all text |
| `setText(String t)` | Sets all text |
| `append(String t)` | Appends at end |
| `insert(String t, int pos)` | Inserts at position |
| `replaceRange(String t, int s, int e)` | Replace text range |
| `setLineWrap(boolean b)` | Enable/disable line wrap |
| `setWrapStyleWord(boolean b)` | Wrap at word boundaries |
| `setEditable(boolean b)` | Allow/disallow edit |
| `getLineCount()` | Returns number of lines |
| `getRows()` / `getColumns()` | Get dimensions |
| `setTabSize(int size)` | Set tab stop size |
| `getCaretPosition()` | Returns caret position |
| `setFont(Font f)` | Set text font |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JTextAreaDemo extends JFrame {
    JTextAreaDemo() {
        setLayout(new BorderLayout(10, 10));

        JTextArea ta = new JTextArea(10, 40);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 13));
        ta.setText("Type your message here...");

        // Always wrap in JScrollPane
        JScrollPane scroll = new JScrollPane(ta);
        scroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton clear = new JButton("Clear");
        JButton copy  = new JButton("Copy All");

        clear.addActionListener(e -> ta.setText(""));
        copy.addActionListener(e ->  ta.selectAll());

        btnPanel.add(clear);
        btnPanel.add(copy);

        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setTitle("JTextArea Demo");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JTextAreaDemo(); }
}
```

---

## 9. JCheckBox

A **JCheckBox** is a toggle component — can be **selected (true)** or **unselected (false)**. Supports icons and HTML text unlike AWT's `Checkbox`.

### Syntax

```java
JCheckBox cb = new JCheckBox();
JCheckBox cb = new JCheckBox(String text);
JCheckBox cb = new JCheckBox(String text, boolean selected);
JCheckBox cb = new JCheckBox(Icon icon);
JCheckBox cb = new JCheckBox(String text, Icon icon, boolean selected);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `isSelected()` | Returns `true` if checked |
| `setSelected(boolean b)` | Set checked state |
| `getText()` / `setText(String)` | Get/set label text |
| `addItemListener(ItemListener)` | Fires when state changes |
| `addActionListener(ActionListener)` | Fires on click |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JCheckBoxDemo extends JFrame {
    JCheckBoxDemo() {
        setLayout(new FlowLayout());

        JCheckBox java   = new JCheckBox("Java",   true);
        JCheckBox python = new JCheckBox("Python");
        JCheckBox cpp    = new JCheckBox("C++");

        java.addItemListener(e ->
            System.out.println("Java: " + java.isSelected())
        );

        JButton show = new JButton("Show Selected");
        show.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Selected: ");
            if (java.isSelected())   sb.append("Java ");
            if (python.isSelected()) sb.append("Python ");
            if (cpp.isSelected())    sb.append("C++ ");
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        add(java); add(python); add(cpp); add(show);

        setSize(350, 120);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JCheckBoxDemo(); }
}
```

---

## 10. JRadioButton & ButtonGroup

A **JRadioButton** is a circular toggle button. When grouped with **ButtonGroup**, only **one can be selected** at a time.

### Syntax

```java
JRadioButton rb  = new JRadioButton(String text);
JRadioButton rb  = new JRadioButton(String text, boolean selected);
ButtonGroup  grp = new ButtonGroup();
grp.add(rb);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `isSelected()` | Returns selection state |
| `setSelected(boolean b)` | Set state |
| `getText()` / `setText(String)` | Label text |
| `ButtonGroup.getSelection()` | Returns selected button model |
| `ButtonGroup.clearSelection()` | Deselects all |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JRadioButtonDemo extends JFrame {
    JRadioButtonDemo() {
        setLayout(new FlowLayout());

        JRadioButton male   = new JRadioButton("Male",   true);
        JRadioButton female = new JRadioButton("Female");
        JRadioButton other  = new JRadioButton("Other");

        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        group.add(other);

        JButton show = new JButton("Get Selected");
        show.addActionListener(e -> {
            String selected = male.isSelected()   ? "Male"   :
                              female.isSelected() ? "Female" : "Other";
            JOptionPane.showMessageDialog(this, "Gender: " + selected);
        });

        add(new JLabel("Gender: "));
        add(male); add(female); add(other); add(show);

        setSize(350, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JRadioButtonDemo(); }
}
```

> 🆚 **AWT vs Swing** — AWT uses `Checkbox` + `CheckboxGroup` for radio behavior. Swing has dedicated `JRadioButton` + `ButtonGroup` — cleaner and more intuitive.

---

## 11. JComboBox

A **JComboBox** is a **drop-down list** that can optionally be **editable** (user can type a value). More powerful than AWT's `Choice`.

### Syntax

```java
JComboBox<String> cb = new JComboBox<>();
JComboBox<String> cb = new JComboBox<>(String[] items);
JComboBox<String> cb = new JComboBox<>(Vector items);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `addItem(E item)` | Add item to list |
| `insertItemAt(E item, int i)` | Insert at index |
| `removeItem(Object item)` | Remove by value |
| `removeItemAt(int i)` | Remove by index |
| `removeAllItems()` | Remove all items |
| `getSelectedItem()` | Returns selected object |
| `getSelectedIndex()` | Returns selected index |
| `setSelectedIndex(int i)` | Select by index |
| `getItemAt(int i)` | Get item at index |
| `getItemCount()` | Total items |
| `setEditable(boolean b)` | Allow custom typing |
| `addActionListener(...)` | Fires on selection change |
| `addItemListener(...)` | Fires on item state change |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JComboBoxDemo extends JFrame {
    JComboBoxDemo() {
        setLayout(new FlowLayout());

        String[] countries = {"Nepal", "India", "USA", "UK", "Japan"};
        JComboBox<String> cb = new JComboBox<>(countries);
        cb.setSelectedIndex(0);
        cb.setEditable(true);   // user can type custom value

        JLabel result = new JLabel("Selected: Nepal");

        cb.addActionListener(e ->
            result.setText("Selected: " + cb.getSelectedItem())
        );

        add(new JLabel("Country:"));
        add(cb);
        add(result);

        setSize(350, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JComboBoxDemo(); }
}
```

---

## 12. JList

A **JList** displays a **scrollable list of items** supporting single or multiple selection. Uses a **ListModel** for data.

### Syntax

```java
JList<String> list = new JList<>();
JList<String> list = new JList<>(String[] items);
JList<String> list = new JList<>(ListModel<String> model);
```

### Selection Mode Constants

| Constant | Description |
|----------|-------------|
| `ListSelectionModel.SINGLE_SELECTION` | Only one item |
| `ListSelectionModel.SINGLE_INTERVAL_SELECTION` | Contiguous range |
| `ListSelectionModel.MULTIPLE_INTERVAL_SELECTION` | Any selection (default) |

### Key Methods

| Method | Description |
|--------|-------------|
| `getSelectedValue()` | Returns selected item |
| `getSelectedValuesList()` | Returns all selected items |
| `getSelectedIndex()` | Returns selected index |
| `getSelectedIndices()` | Returns all selected indices |
| `setSelectedIndex(int i)` | Select by index |
| `setSelectionMode(int mode)` | Set selection mode |
| `getModel()` | Returns underlying data model |
| `setListData(Object[])` | Set items from array |
| `addListSelectionListener(...)` | Fires on selection change |
| `setCellRenderer(ListCellRenderer)` | Custom item rendering |
| `setVisibleRowCount(int n)` | Set visible rows |
| `setFixedCellHeight(int h)` | Fixed item height |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JListDemo extends JFrame {
    JListDemo() {
        setLayout(new BorderLayout(10, 10));

        String[] skills = {"Java", "Python", "JavaScript",
                           "SQL", "HTML/CSS", "Spring Boot", "React"};

        JList<String> list = new JList<>(skills);
        list.setSelectionMode(
            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(5);

        JScrollPane scroll = new JScrollPane(list);

        JLabel result = new JLabel("Nothing selected");

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                result.setText("Selected: " +
                    list.getSelectedValuesList());
            }
        });

        add(new JLabel("Select your skills (Ctrl+click for multi):"),
            BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(result, BorderLayout.SOUTH);

        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JListDemo(); }
}
```

---

## 13. JSlider

A **JSlider** lets the user **select a numeric value** by dragging a knob along a track.

### Syntax

```java
JSlider slider = new JSlider();
JSlider slider = new JSlider(int min, int max);
JSlider slider = new JSlider(int min, int max, int value);
JSlider slider = new JSlider(int orientation, int min, int max, int value);
```

### Orientation Constants

| Constant | Description |
|----------|-------------|
| `JSlider.HORIZONTAL` | Horizontal (default) |
| `JSlider.VERTICAL` | Vertical |

### Key Methods

| Method | Description |
|--------|-------------|
| `getValue()` | Returns current value |
| `setValue(int v)` | Sets current value |
| `setMinimum(int min)` | Sets minimum value |
| `setMaximum(int max)` | Sets maximum value |
| `setMajorTickSpacing(int n)` | Large tick interval |
| `setMinorTickSpacing(int n)` | Small tick interval |
| `setPaintTicks(boolean b)` | Show tick marks |
| `setPaintLabels(boolean b)` | Show value labels |
| `setPaintTrack(boolean b)` | Show/hide track |
| `setSnapToTicks(boolean b)` | Snap to nearest tick |
| `setInverted(boolean b)` | Invert direction |
| `addChangeListener(ChangeListener)` | Fires on value change |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JSliderDemo extends JFrame {
    JSliderDemo() {
        setLayout(new BorderLayout(10, 10));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);

        JLabel valueLabel = new JLabel("Volume: 50", JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 14));

        slider.addChangeListener(e ->
            valueLabel.setText("Volume: " + slider.getValue())
        );

        add(new JLabel("Volume Control:", JLabel.CENTER),
            BorderLayout.NORTH);
        add(slider,      BorderLayout.CENTER);
        add(valueLabel,  BorderLayout.SOUTH);

        setSize(400, 160);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JSliderDemo(); }
}
```

---

## 14. JProgressBar

A **JProgressBar** displays the **progress of a task** — either determinate (with a known total) or indeterminate (unknown duration).

### Syntax

```java
JProgressBar pb = new JProgressBar();
JProgressBar pb = new JProgressBar(int min, int max);
JProgressBar pb = new JProgressBar(int orientation, int min, int max);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getValue()` / `setValue(int v)` | Get/set current value |
| `setMinimum(int min)` | Set minimum |
| `setMaximum(int max)` | Set maximum |
| `setStringPainted(boolean b)` | Show percentage text |
| `setString(String s)` | Custom text overlay |
| `setIndeterminate(boolean b)` | Toggle indeterminate mode |
| `getPercentComplete()` | Returns 0.0 to 1.0 progress |
| `addChangeListener(...)` | Fires on value change |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JProgressBarDemo extends JFrame {
    JProgressBarDemo() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(15,15,15,15));

        JProgressBar pb = new JProgressBar(0, 100);
        pb.setValue(0);
        pb.setStringPainted(true);
        pb.setPreferredSize(new Dimension(300, 25));

        JButton start = new JButton("Start Download");
        JLabel  status = new JLabel("Ready", JLabel.CENTER);

        start.addActionListener(e -> {
            start.setEnabled(false);
            status.setText("Downloading...");

            // Simulate progress with Timer
            Timer timer = new Timer(50, null);
            timer.addActionListener(ev -> {
                int val = pb.getValue();
                if (val < 100) {
                    pb.setValue(val + 1);
                } else {
                    timer.stop();
                    status.setText("Download Complete!");
                    start.setEnabled(true);
                    pb.setValue(0);
                }
            });
            timer.start();
        });

        add(new JLabel("File Download Progress:", JLabel.CENTER),
            BorderLayout.NORTH);
        add(pb,     BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        JPanel btnPanel = new JPanel();
        btnPanel.add(start);
        add(btnPanel, BorderLayout.SOUTH);

        setSize(380, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JProgressBarDemo(); }
}
```

---

## 15. JSpinner

A **JSpinner** lets the user **select a value** from an ordered sequence (numbers, dates, lists) using up/down arrows.

### Syntax

```java
JSpinner sp = new JSpinner();                           // default 0
JSpinner sp = new JSpinner(SpinnerModel model);
```

### SpinnerModel Types

| Model | Description | Example |
|-------|-------------|---------|
| `SpinnerNumberModel` | Numeric range | 1 to 100, step 1 |
| `SpinnerListModel` | List of values | months, sizes |
| `SpinnerDateModel` | Date selection | calendar dates |

### Key Methods

| Method | Description |
|--------|-------------|
| `getValue()` | Returns current value |
| `setValue(Object v)` | Sets current value |
| `getModel()` | Returns spinner model |
| `setEditor(JComponent e)` | Custom editor |
| `addChangeListener(...)` | Fires on value change |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JSpinnerDemo extends JFrame {
    JSpinnerDemo() {
        setLayout(new GridLayout(3, 2, 10, 10));
        ((JPanel)getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(15,15,15,15));

        // Number spinner
        add(new JLabel("Age (1-120):"));
        JSpinner numSpinner = new JSpinner(
            new SpinnerNumberModel(18, 1, 120, 1));
        add(numSpinner);

        // List spinner
        add(new JLabel("Month:"));
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun",
                           "Jul","Aug","Sep","Oct","Nov","Dec"};
        JSpinner listSpinner = new JSpinner(new SpinnerListModel(months));
        add(listSpinner);

        // Date spinner
        add(new JLabel("Date:"));
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        add(dateSpinner);

        setSize(350, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JSpinnerDemo(); }
}
```

---

## 16. JTable

A **JTable** displays data in a **grid of rows and columns** — like a spreadsheet. It is one of Swing's most powerful components.

### Syntax

```java
JTable table = new JTable(Object[][] data, Object[] columnNames);
JTable table = new JTable(TableModel model);
JTable table = new JTable(int rows, int cols);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `getValueAt(int row, int col)` | Get cell value |
| `setValueAt(Object v, int r, int c)` | Set cell value |
| `getRowCount()` | Returns number of rows |
| `getColumnCount()` | Returns number of columns |
| `getSelectedRow()` | Returns selected row index |
| `getSelectedRows()` | Returns all selected rows |
| `getSelectedColumn()` | Returns selected column |
| `setRowHeight(int h)` | Set row height |
| `setAutoResizeMode(int mode)` | Column auto-resize behavior |
| `setSelectionMode(int mode)` | Single/multiple selection |
| `getModel()` | Returns TableModel |
| `setModel(TableModel m)` | Sets TableModel |
| `getTableHeader()` | Returns column header |
| `setFont(Font f)` | Set cell font |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JTableDemo extends JFrame {
    JTableDemo() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Age", "Course"};
        Object[][] data = {
            {1, "Aasii",   21, "BE Computer"},
            {2, "Ram",     20, "BE IT"},
            {3, "Sita",    22, "BE Software"},
            {4, "Hari",    19, "BE Computer"},
            {5, "Gita",    21, "BE IT"},
        };

        JTable table = new JTable(data, columns);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);

        // Always wrap JTable in JScrollPane
        JScrollPane scroll = new JScrollPane(table);

        JLabel status = new JLabel("Click a row to select");
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0)
                status.setText("Selected: " + table.getValueAt(row, 1));
        });

        add(scroll, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        setTitle("Student Table");
        setSize(450, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JTableDemo(); }
}
```

> 💡 Always wrap `JTable` in a `JScrollPane` — otherwise column headers won't be visible.

---

## 17. JTree

A **JTree** displays **hierarchical data** in a collapsible/expandable tree structure — like a file browser.

### Syntax

```java
JTree tree = new JTree();
JTree tree = new JTree(TreeNode root);
JTree tree = new JTree(TreeModel model);
```

### Key Classes

| Class | Description |
|-------|-------------|
| `DefaultMutableTreeNode` | Node in the tree |
| `DefaultTreeModel` | Data model for tree |
| `TreeSelectionModel` | Controls selection behavior |

### Key Methods

| Method | Description |
|--------|-------------|
| `getSelectionPath()` | Returns selected tree path |
| `getLastSelectedPathComponent()` | Returns selected node |
| `setRootVisible(boolean b)` | Show/hide root node |
| `setShowsRootHandles(boolean b)` | Show expand icons |
| `expandRow(int row)` | Expand a row |
| `collapseRow(int row)` | Collapse a row |
| `addTreeSelectionListener(...)` | Fires on node selection |

### Example

```java
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

public class JTreeDemo extends JFrame {
    JTreeDemo() {
        setLayout(new BorderLayout());

        // Build tree structure
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Pokhara University");

        DefaultMutableTreeNode science = new DefaultMutableTreeNode("Science & Tech");
        science.add(new DefaultMutableTreeNode("BE Computer"));
        science.add(new DefaultMutableTreeNode("BE IT"));
        science.add(new DefaultMutableTreeNode("BE Software"));

        DefaultMutableTreeNode management = new DefaultMutableTreeNode("Management");
        management.add(new DefaultMutableTreeNode("BBA"));
        management.add(new DefaultMutableTreeNode("MBA"));

        root.add(science);
        root.add(management);

        JTree tree = new JTree(root);
        tree.setShowsRootHandles(true);
        tree.expandRow(0);

        JLabel status = new JLabel("Select a node");
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node != null)
                status.setText("Selected: " + node.getUserObject());
        });

        add(new JScrollPane(tree), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        setTitle("JTree Demo");
        setSize(350, 280);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JTreeDemo(); }
}
```

---

## 18. JFrame

**JFrame** is the **main top-level window** in Swing. It extends `java.awt.Frame` and adds Swing's layered pane structure.

### Syntax

```java
JFrame frame = new JFrame();
JFrame frame = new JFrame(String title);
```

### Close Operation Constants

| Constant | Description |
|----------|-------------|
| `DO_NOTHING_ON_CLOSE` | Do nothing |
| `HIDE_ON_CLOSE` | Hide window (default) |
| `DISPOSE_ON_CLOSE` | Destroy window |
| `EXIT_ON_CLOSE` | Exit JVM |

### Key Methods

| Method | Description |
|--------|-------------|
| `setTitle(String t)` | Set window title |
| `setSize(int w, int h)` | Set dimensions |
| `setLocation(int x, int y)` | Set position |
| `setLocationRelativeTo(Component c)` | Center relative to component (null = screen center) |
| `setDefaultCloseOperation(int op)` | What happens on close |
| `setResizable(boolean b)` | Allow/disallow resize |
| `setVisible(boolean b)` | Show/hide |
| `add(Component c)` | Add to content pane |
| `setLayout(LayoutManager lm)` | Set layout |
| `setJMenuBar(JMenuBar mb)` | Set menu bar |
| `getContentPane()` | Returns content pane |
| `setIconImage(Image img)` | Set window icon |
| `pack()` | Auto-size to fit components |
| `dispose()` | Close and free resources |

### Example

```java
import javax.swing.*;

public class JFrameDemo extends JFrame {
    JFrameDemo() {
        setTitle("My Swing App");
        setSize(400, 300);
        setLocationRelativeTo(null);   // center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        add(new JLabel("Hello, Swing!", JLabel.CENTER));

        setVisible(true);
    }

    public static void main(String[] args) {
        // Always create Swing UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new JFrameDemo());
    }
}
```

> 💡 Always create Swing components on the **Event Dispatch Thread (EDT)** using `SwingUtilities.invokeLater()`.

---

## 19. JPanel

A **JPanel** is the most versatile **intermediate container**. Used to group components and supports custom painting.

### Syntax

```java
JPanel panel = new JPanel();
JPanel panel = new JPanel(LayoutManager layout);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(Component c)` | Add component |
| `remove(Component c)` | Remove component |
| `setLayout(LayoutManager lm)` | Set layout |
| `setBorder(Border b)` | Set border |
| `setBackground(Color c)` | Set background |
| `setOpaque(boolean b)` | Fill background if true |
| `paintComponent(Graphics g)` | Override for custom drawing |
| `revalidate()` | Re-layout after changes |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JPanelDemo extends JFrame {
    JPanelDemo() {
        setLayout(new BorderLayout());

        // Header panel
        JPanel header = new JPanel();
        header.setBackground(new Color(52, 73, 94));
        header.add(new JLabel("<html><font color='white' size='5'>" +
                              "Student Portal</font></html>"));

        // Form panel
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        form.add(new JLabel("Name:")); form.add(new JTextField(15));
        form.add(new JLabel("Roll:")); form.add(new JTextField(10));
        form.add(new JLabel("Email:")); form.add(new JTextField(20));

        // Button panel
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(new JButton("Save"));
        buttons.add(new JButton("Cancel"));

        add(header,  BorderLayout.NORTH);
        add(form,    BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setSize(420, 260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JPanelDemo(); }
}
```

---

## 20. JScrollPane

A **JScrollPane** adds **horizontal and/or vertical scroll bars** to a component that exceeds its visible area.

### Syntax

```java
JScrollPane sp = new JScrollPane(Component view);
JScrollPane sp = new JScrollPane(Component view,
                                  int vsbPolicy, int hsbPolicy);
```

### Scrollbar Policy Constants

| Constant | Description |
|----------|-------------|
| `VERTICAL_SCROLLBAR_AS_NEEDED` | Show only when needed (default) |
| `VERTICAL_SCROLLBAR_ALWAYS` | Always show |
| `VERTICAL_SCROLLBAR_NEVER` | Never show |
| Same for `HORIZONTAL_SCROLLBAR_*` | — |

### Key Methods

| Method | Description |
|--------|-------------|
| `setViewportView(Component c)` | Set the scrolled component |
| `getViewport()` | Returns viewport |
| `setVerticalScrollBarPolicy(int p)` | Set vertical policy |
| `setHorizontalScrollBarPolicy(int p)` | Set horizontal policy |
| `getVerticalScrollBar()` | Access vertical scrollbar |
| `getHorizontalScrollBar()` | Access horizontal scrollbar |

### Example

```java
import javax.swing.*;

public class JScrollPaneDemo extends JFrame {
    JScrollPaneDemo() {
        JTextArea ta = new JTextArea(20, 40);
        ta.setText("Line 1\nLine 2\nLine 3\n...repeat...");

        JScrollPane scroll = new JScrollPane(ta,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scroll);
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JScrollPaneDemo(); }
}
```

---

## 21. JTabbedPane

A **JTabbedPane** organizes content into **multiple tabs** — click a tab to switch its panel.

### Syntax

```java
JTabbedPane tp = new JTabbedPane();
JTabbedPane tp = new JTabbedPane(int tabPlacement);
```

### Tab Placement Constants

| Constant | Description |
|----------|-------------|
| `JTabbedPane.TOP` | Tabs on top (default) |
| `JTabbedPane.BOTTOM` | Tabs on bottom |
| `JTabbedPane.LEFT` | Tabs on left |
| `JTabbedPane.RIGHT` | Tabs on right |

### Key Methods

| Method | Description |
|--------|-------------|
| `addTab(String title, Component c)` | Add a tab |
| `addTab(String title, Icon icon, Component c)` | Tab with icon |
| `addTab(String title, Icon icon, Component c, String tip)` | With tooltip |
| `removeTabAt(int index)` | Remove tab |
| `getSelectedIndex()` | Active tab index |
| `setSelectedIndex(int i)` | Switch to tab |
| `setTitleAt(int i, String t)` | Update tab title |
| `setEnabledAt(int i, boolean b)` | Enable/disable tab |
| `getTabCount()` | Total tabs |
| `addChangeListener(...)` | Fires on tab switch |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JTabbedPaneDemo extends JFrame {
    JTabbedPaneDemo() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);

        // Tab 1 — General
        JPanel general = new JPanel(new GridLayout(3, 2, 10, 10));
        general.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        general.add(new JLabel("Name:")); general.add(new JTextField(15));
        general.add(new JLabel("Age:"));  general.add(new JTextField(5));
        general.add(new JLabel("Email:")); general.add(new JTextField(20));

        // Tab 2 — Settings
        JPanel settings = new JPanel(new FlowLayout());
        settings.add(new JCheckBox("Dark Mode"));
        settings.add(new JCheckBox("Notifications"));
        settings.add(new JCheckBox("Auto-save"));

        // Tab 3 — About
        JPanel about = new JPanel(new BorderLayout());
        about.add(new JLabel("<html><center>App v1.0<br>" +
                             "Built with Swing</center></html>",
                             JLabel.CENTER), BorderLayout.CENTER);

        tabs.addTab("General",  general);
        tabs.addTab("Settings", settings);
        tabs.addTab("About",    about);
        tabs.setToolTipTextAt(2, "About this application");

        add(tabs);
        setTitle("JTabbedPane Demo");
        setSize(420, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JTabbedPaneDemo(); }
}
```

---

## 22. JSplitPane

A **JSplitPane** divides a container into **two resizable sections** separated by a draggable divider.

### Syntax

```java
JSplitPane sp = new JSplitPane(int orientation);
JSplitPane sp = new JSplitPane(int orientation,
                                Component left, Component right);
```

### Orientation Constants

| Constant | Description |
|----------|-------------|
| `JSplitPane.HORIZONTAL_SPLIT` | Side by side |
| `JSplitPane.VERTICAL_SPLIT` | Top and bottom |

### Key Methods

| Method | Description |
|--------|-------------|
| `setDividerLocation(int pixels)` | Set divider position |
| `setDividerLocation(double proportion)` | Set by proportion (0.0–1.0) |
| `setDividerSize(int size)` | Set divider thickness |
| `setOneTouchExpandable(boolean b)` | One-click collapse button |
| `setResizeWeight(double w)` | Resize weight (0.0–1.0) |
| `setLeftComponent(Component c)` | Set left/top component |
| `setRightComponent(Component c)` | Set right/bottom component |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JSplitPaneDemo extends JFrame {
    JSplitPaneDemo() {
        // File list on left, content on right
        JList<String> fileList = new JList<>(
            new String[]{"File1.txt", "File2.java", "File3.md"});
        JTextArea content = new JTextArea("Select a file to view...");

        fileList.addListSelectionListener(e ->
            content.setText("Content of: " + fileList.getSelectedValue())
        );

        JSplitPane split = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(fileList),
            new JScrollPane(content));

        split.setDividerLocation(150);
        split.setOneTouchExpandable(true);
        split.setResizeWeight(0.3);

        add(split);
        setTitle("File Explorer");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JSplitPaneDemo(); }
}
```

---

## 23. JDialog

A **JDialog** is a **secondary popup window** attached to a parent `JFrame`. Can be modal or non-modal.

### Syntax

```java
JDialog d = new JDialog(JFrame parent, String title, boolean modal);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `setTitle(String t)` | Set title |
| `setModal(boolean b)` | Set modal |
| `setSize(int w, int h)` | Set size |
| `setLocationRelativeTo(Component c)` | Position relative to component |
| `setVisible(boolean b)` | Show/hide |
| `dispose()` | Close dialog |
| `setDefaultCloseOperation(int op)` | Close behavior |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JDialogDemo extends JFrame {
    JDialogDemo() {
        setSize(300, 180);
        setLayout(new FlowLayout());
        setTitle("Main Window");

        JButton open = new JButton("Open Settings");
        open.addActionListener(e -> showSettingsDialog());

        add(open);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void showSettingsDialog() {
        JDialog dialog = new JDialog(this, "Settings", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        form.add(new JLabel("Theme:"));
        form.add(new JComboBox<>(new String[]{"Light","Dark","System"}));
        form.add(new JLabel("Language:"));
        form.add(new JComboBox<>(new String[]{"English","Nepali"}));

        JPanel btns = new JPanel();
        JButton save  = new JButton("Save");
        JButton close = new JButton("Close");
        save.addActionListener(ev ->  dialog.dispose());
        close.addActionListener(ev -> dialog.dispose());
        btns.add(save); btns.add(close);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btns, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public static void main(String[] args) { new JDialogDemo(); }
}
```

---

## 24. JOptionPane

**JOptionPane** provides **ready-made dialog boxes** — the simplest way to show messages, get input, or ask for confirmation.

### Dialog Types

| Method | Type | Returns |
|--------|------|---------|
| `showMessageDialog(...)` | Message popup | `void` |
| `showConfirmDialog(...)` | Yes/No/Cancel | `int` |
| `showInputDialog(...)` | Text input | `String` |
| `showOptionDialog(...)` | Custom buttons | `int` |

### Message Type Constants

| Constant | Icon |
|----------|------|
| `JOptionPane.INFORMATION_MESSAGE` | ℹ️ |
| `JOptionPane.WARNING_MESSAGE` | ⚠️ |
| `JOptionPane.ERROR_MESSAGE` | ❌ |
| `JOptionPane.QUESTION_MESSAGE` | ❓ |
| `JOptionPane.PLAIN_MESSAGE` | No icon |

### ConfirmDialog Return Values

| Return | Meaning |
|--------|---------|
| `JOptionPane.YES_OPTION` (0) | User clicked Yes |
| `JOptionPane.NO_OPTION` (1) | User clicked No |
| `JOptionPane.CANCEL_OPTION` (2) | User clicked Cancel |
| `JOptionPane.CLOSED_OPTION` (-1) | Dialog closed |

### Example

```java
import javax.swing.*;

public class JOptionPaneDemo {
    public static void main(String[] args) {

        // 1. Message dialog
        JOptionPane.showMessageDialog(null,
            "File saved successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);

        // 2. Confirm dialog
        int choice = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to delete this file?",
            "Confirm Delete",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION)
            System.out.println("Deleted!");
        else if (choice == JOptionPane.NO_OPTION)
            System.out.println("Cancelled.");

        // 3. Input dialog
        String name = JOptionPane.showInputDialog(null,
            "Enter your name:",
            "Input",
            JOptionPane.QUESTION_MESSAGE);
        System.out.println("Name: " + name);

        // 4. Option dialog with custom buttons
        String[] options = {"Save", "Discard", "Cancel"};
        int opt = JOptionPane.showOptionDialog(null,
            "Do you want to save changes?",
            "Unsaved Changes",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);

        System.out.println("Chose: " + options[opt]);
    }
}
```

---

## 25. JFileChooser

A **JFileChooser** shows a **cross-platform file/directory picker dialog** — more flexible than AWT's `FileDialog`.

### Syntax

```java
JFileChooser fc = new JFileChooser();
JFileChooser fc = new JFileChooser(String startPath);
```

### Return Value Constants

| Constant | Description |
|----------|-------------|
| `JFileChooser.APPROVE_OPTION` | User selected a file |
| `JFileChooser.CANCEL_OPTION` | User cancelled |
| `JFileChooser.ERROR_OPTION` | An error occurred |

### Key Methods

| Method | Description |
|--------|-------------|
| `showOpenDialog(Component parent)` | Show open file dialog |
| `showSaveDialog(Component parent)` | Show save file dialog |
| `getSelectedFile()` | Returns selected `File` |
| `getSelectedFiles()` | Returns multiple files |
| `setMultiSelectionEnabled(boolean b)` | Allow multi-select |
| `setFileSelectionMode(int mode)` | FILES_ONLY / DIRS_ONLY / BOTH |
| `setFileFilter(FileFilter f)` | Filter by extension |
| `setCurrentDirectory(File dir)` | Set starting directory |
| `setDialogTitle(String t)` | Set dialog title |

### Example

```java
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class JFileChooserDemo extends JFrame {
    JFileChooserDemo() {
        setLayout(new FlowLayout());
        setSize(400, 150);

        JLabel path = new JLabel("No file selected");
        JButton open = new JButton("Open File");
        JButton save = new JButton("Save File");

        open.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open File");
            fc.setFileFilter(new FileNameExtensionFilter(
                "Java Files (*.java)", "java"));
            fc.setMultiSelectionEnabled(false);

            int result = fc.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                path.setText(file.getAbsolutePath());
            }
        });

        save.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save File");
            int result = fc.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION)
                path.setText("Saved: " + fc.getSelectedFile().getName());
        });

        add(open); add(save); add(path);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JFileChooserDemo(); }
}
```

---

## 26. JColorChooser

A **JColorChooser** displays a **color picker dialog** for selecting colors visually.

### Syntax

```java
// Static convenience method
Color c = JColorChooser.showDialog(Component parent,
                                    String title,
                                    Color initialColor);
```

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JColorChooserDemo extends JFrame {
    JColorChooserDemo() {
        setLayout(new FlowLayout());
        setSize(350, 150);

        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setPreferredSize(new Dimension(100, 50));
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton pickColor = new JButton("Pick Color");
        JLabel hexLabel   = new JLabel("#FFFFFF");

        pickColor.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(
                this, "Choose a Color", colorPanel.getBackground());
            if (chosen != null) {
                colorPanel.setBackground(chosen);
                hexLabel.setText(String.format("#%02X%02X%02X",
                    chosen.getRed(), chosen.getGreen(), chosen.getBlue()));
            }
        });

        add(pickColor); add(colorPanel); add(hexLabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JColorChooserDemo(); }
}
```

---

## 27. JMenuBar, JMenu & JMenuItem

Swing's full **menu system** — richer than AWT menus with icon support, mnemonics, and accelerators.

```
┌──────────────────────────────────────────────────────────────┐
│                   Swing Menu Structure                       │
│                                                              │
│   JFrame                                                     │
│   └── JMenuBar                                               │
│       ├── JMenu ("File")                                     │
│       │   ├── JMenuItem ("New")         Ctrl+N               │
│       │   ├── JMenuItem ("Open")        Ctrl+O               │
│       │   ├── JSeparator                ─────────────        │
│       │   ├── JCheckBoxMenuItem ("Autosave")                 │
│       │   ├── JRadioButtonMenuItem ("Light")                 │
│       │   └── JMenuItem ("Exit")        Alt+F4               │
│       └── JMenu ("Edit")                                     │
│           ├── JMenu ("Find") ← submenu                       │
│           │   ├── JMenuItem ("Find...")                      │
│           │   └── JMenuItem ("Replace...")                   │
│           └── JMenuItem ("Preferences")                      │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Swing Menu Structure**

### Example

```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JMenuBarDemo extends JFrame {
    JMenuBarDemo() {
        setTitle("Menu Demo"); setSize(500, 350);

        JMenuBar mb = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');                // Alt+F

        JMenuItem newItem  = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Keyboard accelerators
        newItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveItem.setAccelerator(
            KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new JMenuItem("Cut"));
        editMenu.add(new JMenuItem("Copy"));
        editMenu.add(new JMenuItem("Paste"));
        editMenu.addSeparator();

        // Submenu
        JMenu findMenu = new JMenu("Find");
        findMenu.add(new JMenuItem("Find..."));
        findMenu.add(new JMenuItem("Replace..."));
        editMenu.add(findMenu);

        // View Menu with toggle items
        JMenu viewMenu = new JMenu("View");
        JCheckBoxMenuItem toolbar = new JCheckBoxMenuItem("Toolbar", true);
        JCheckBoxMenuItem status  = new JCheckBoxMenuItem("Status Bar", true);

        ButtonGroup themeGroup = new ButtonGroup();
        JRadioButtonMenuItem light = new JRadioButtonMenuItem("Light", true);
        JRadioButtonMenuItem dark  = new JRadioButtonMenuItem("Dark");
        themeGroup.add(light); themeGroup.add(dark);

        viewMenu.add(toolbar); viewMenu.add(status);
        viewMenu.addSeparator();
        viewMenu.add(light); viewMenu.add(dark);

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(viewMenu);
        setJMenuBar(mb);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JMenuBarDemo(); }
}
```

---

## 28. JToolBar

A **JToolBar** is a row of **buttons and components** — usually docked at the top of a window, providing quick access to common actions.

### Syntax

```java
JToolBar tb = new JToolBar();
JToolBar tb = new JToolBar(String name);
JToolBar tb = new JToolBar(int orientation);
```

### Key Methods

| Method | Description |
|--------|-------------|
| `add(Component c)` | Add component |
| `addSeparator()` | Add visual gap |
| `setFloatable(boolean b)` | Allow/disallow dragging |
| `setRollover(boolean b)` | Rollover effect on buttons |
| `setOrientation(int o)` | HORIZONTAL or VERTICAL |

### Example

```java
import javax.swing.*;
import java.awt.*;

public class JToolBarDemo extends JFrame {
    JToolBarDemo() {
        setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar("Main Toolbar");
        toolbar.setFloatable(true);
        toolbar.setRollover(true);

        JButton newBtn  = new JButton("New");
        JButton openBtn = new JButton("Open");
        JButton saveBtn = new JButton("Save");
        JButton cutBtn  = new JButton("Cut");
        JButton copyBtn = new JButton("Copy");

        toolbar.add(newBtn);
        toolbar.add(openBtn);
        toolbar.add(saveBtn);
        toolbar.addSeparator();
        toolbar.add(cutBtn);
        toolbar.add(copyBtn);

        JTextArea content = new JTextArea("Main content area...");

        add(toolbar,               BorderLayout.NORTH);
        add(new JScrollPane(content), BorderLayout.CENTER);

        setTitle("JToolBar Demo");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) { new JToolBarDemo(); }
}
```

---

## 29. JToolTip

**Tooltips** are small pop-up hints that appear when the user **hovers over a component**. Built into every `JComponent` — just call `setToolTipText()`.

```java
JButton btn = new JButton("Save");
btn.setToolTipText("Save the current file (Ctrl+S)");

JTextField tf = new JTextField(15);
tf.setToolTipText("Enter your username here");

// Custom tooltip delay
ToolTipManager.sharedInstance().setInitialDelay(500);   // ms before showing
ToolTipManager.sharedInstance().setDismissDelay(3000);  // ms before hiding
```

---

## 30. Complete Swing Components Reference Table

| Component | Class | Type | Interactive | Key Feature |
|-----------|-------|------|-------------|-------------|
| Label | `JLabel` | Atomic | ❌ | HTML support, icons |
| Button | `JButton` | Atomic | ✅ | Icons, mnemonics, rollover |
| TextField | `JTextField` | Atomic | ✅ | Single-line text input |
| PasswordField | `JPasswordField` | Atomic | ✅ | Masked input, `char[]` |
| TextArea | `JTextArea` | Atomic | ✅ | Multi-line, line wrap |
| CheckBox | `JCheckBox` | Atomic | ✅ | Boolean toggle with icon |
| RadioButton | `JRadioButton` | Atomic | ✅ | Exclusive with `ButtonGroup` |
| ComboBox | `JComboBox<E>` | Atomic | ✅ | Editable drop-down |
| List | `JList<E>` | Atomic | ✅ | Multi-select, custom renderer |
| Slider | `JSlider` | Atomic | ✅ | Tick marks, labels |
| ProgressBar | `JProgressBar` | Atomic | ❌ | Determinate/indeterminate |
| Spinner | `JSpinner` | Atomic | ✅ | Number/list/date models |
| Table | `JTable` | Atomic | ✅ | Grid data, sortable |
| Tree | `JTree` | Atomic | ✅ | Hierarchical collapsible |
| Frame | `JFrame` | Top-Level | ❌ | Main window |
| Dialog | `JDialog` | Top-Level | ❌ | Modal/non-modal popup |
| Window | `JWindow` | Top-Level | ❌ | Borderless window |
| Panel | `JPanel` | Container | ❌ | Group components, custom draw |
| ScrollPane | `JScrollPane` | Container | ❌ | Add scrollbars to any component |
| TabbedPane | `JTabbedPane` | Container | ✅ | Tabbed panels |
| SplitPane | `JSplitPane` | Container | ✅ | Two resizable sections |
| OptionPane | `JOptionPane` | Dialog | ✅ | Message/confirm/input dialogs |
| FileChooser | `JFileChooser` | Dialog | ✅ | Cross-platform file picker |
| ColorChooser | `JColorChooser` | Dialog | ✅ | Color picker |
| MenuBar | `JMenuBar` | Menu | ❌ | Holds menus on frame |
| Menu | `JMenu` | Menu | ✅ | Drop-down menu |
| MenuItem | `JMenuItem` | Menu | ✅ | Clickable with accelerator |
| CheckBoxMenuItem | `JCheckBoxMenuItem` | Menu | ✅ | Toggleable menu item |
| RadioButtonMenuItem | `JRadioButtonMenuItem` | Menu | ✅ | Exclusive menu item |
| ToolBar | `JToolBar` | Container | ❌ | Dockable action bar |

---

## 31. Swing vs AWT Components

| Feature | AWT | Swing |
|---------|-----|-------|
| Component type | Heavyweight | Lightweight |
| Rendering | OS native | Java 2D |
| HTML in labels | ❌ | ✅ `JLabel` |
| Icons on buttons | ❌ | ✅ `JButton` |
| Password field | `setEchoChar()` on `TextField` | `JPasswordField` |
| Tables | ❌ | ✅ `JTable` |
| Trees | ❌ | ✅ `JTree` |
| File chooser | `FileDialog` (OS native) | `JFileChooser` (Java) |
| Color chooser | ❌ | ✅ `JColorChooser` |
| Progress bar | ❌ | ✅ `JProgressBar` |
| Spinner | ❌ | ✅ `JSpinner` |
| Slider | `Scrollbar` | ✅ `JSlider` |
| Tabs | ❌ | ✅ `JTabbedPane` |
| Split view | ❌ | ✅ `JSplitPane` |
| Ready dialogs | ❌ | ✅ `JOptionPane` |
| Tooltip | ❌ | ✅ `setToolTipText()` |
| Borders | ❌ | ✅ `setBorder()` |
| MVC | ❌ | ✅ Built-in models |

---

## 32. Full Working Swing Application

```java
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SwingRegistrationForm extends JFrame {

    SwingRegistrationForm() {
        setTitle("Student Registration — Swing");
        setSize(500, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ── Header ──────────────────────────────────────────
        JLabel header = new JLabel("Student Registration",
                                    JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(41, 128, 185));
        header.setForeground(Color.WHITE);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));

        // ── Form Panel ──────────────────────────────────────
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 12));
        form.setBorder(new EmptyBorder(20, 30, 10, 30));

        JTextField nameTF  = new JTextField();
        JTextField emailTF = new JTextField();
        JPasswordField passPF = new JPasswordField();
        JTextField ageTF   = new JTextField();

        form.add(new JLabel("Full Name:"));   form.add(nameTF);
        form.add(new JLabel("Email:"));        form.add(emailTF);
        form.add(new JLabel("Password:"));     form.add(passPF);
        form.add(new JLabel("Age:"));          form.add(ageTF);

        // Gender
        JRadioButton male   = new JRadioButton("Male", true);
        JRadioButton female = new JRadioButton("Female");
        ButtonGroup genderGrp = new ButtonGroup();
        genderGrp.add(male); genderGrp.add(female);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(male); genderPanel.add(female);
        form.add(new JLabel("Gender:"));  form.add(genderPanel);

        // Country
        JComboBox<String> country = new JComboBox<>(
            new String[]{"Nepal","India","USA","UK","Other"});
        form.add(new JLabel("Country:")); form.add(country);

        // Skills
        JList<String> skills = new JList<>(
            new String[]{"Java","Python","JavaScript","SQL","HTML"});
        skills.setVisibleRowCount(3);
        skills.setSelectionMode(
            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        form.add(new JLabel("Skills (Ctrl+click):"));
        form.add(new JScrollPane(skills));

        // Bio
        JTextArea bio = new JTextArea(3, 20);
        bio.setLineWrap(true);
        bio.setWrapStyleWord(true);
        form.add(new JLabel("Bio:"));
        form.add(new JScrollPane(bio));

        // Terms
        JCheckBox terms = new JCheckBox("I agree to Terms & Conditions");
        form.add(new JLabel(""));
        form.add(terms);

        // ── Progress Bar ─────────────────────────────────────
        JProgressBar progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        progress.setString("Fill in the form to register");
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setBorder(new EmptyBorder(0, 30, 10, 30));
        progressPanel.add(progress);

        // ── Button Panel ─────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton register = new JButton("Register");
        JButton reset    = new JButton("Reset");
        JButton cancel   = new JButton("Cancel");

        register.setBackground(new Color(39, 174, 96));
        register.setForeground(Color.WHITE);
        register.setFont(new Font("Arial", Font.BOLD, 13));

        reset.setBackground(new Color(230, 126, 34));
        reset.setForeground(Color.WHITE);

        cancel.addActionListener(e -> System.exit(0));

        register.addActionListener(e -> {
            if (!terms.isSelected()) {
                JOptionPane.showMessageDialog(this,
                    "Please accept the Terms & Conditions.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (nameTF.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Name cannot be empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            progress.setValue(100);
            progress.setString("Registered Successfully!");
            JOptionPane.showMessageDialog(this,
                "Welcome, " + nameTF.getText() + "!\nRegistration complete.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });

        reset.addActionListener(e -> {
            nameTF.setText(""); emailTF.setText("");
            passPF.setText(""); ageTF.setText("");
            bio.setText(""); terms.setSelected(false);
            genderGrp.clearSelection(); male.setSelected(true);
            country.setSelectedIndex(0);
            skills.clearSelection();
            progress.setValue(0);
            progress.setString("Fill in the form to register");
        });

        btnPanel.add(register); btnPanel.add(reset); btnPanel.add(cancel);

        // ── Menu Bar ─────────────────────────────────────────
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("About"));
        mb.add(fileMenu); mb.add(helpMenu);
        setJMenuBar(mb);

        // ── Assemble ─────────────────────────────────────────
        add(header,        BorderLayout.NORTH);
        add(form,          BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(progressPanel, BorderLayout.NORTH);
        south.add(btnPanel,      BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingRegistrationForm::new);
    }
}
```

---

## 33. Summary

| Component | Class | Key Point |
|-----------|-------|-----------|
| **JLabel** | `JLabel` | Text + icon, supports HTML |
| **JButton** | `JButton` | Icons, mnemonics, accelerators |
| **JTextField** | `JTextField` | Single-line input |
| **JPasswordField** | `JPasswordField` | Masked input — use `getPassword()` |
| **JTextArea** | `JTextArea` | Multi-line — wrap in `JScrollPane` |
| **JCheckBox** | `JCheckBox` | Boolean toggle |
| **JRadioButton** | `JRadioButton` | Use with `ButtonGroup` for exclusivity |
| **JComboBox** | `JComboBox<E>` | Drop-down, optionally editable |
| **JList** | `JList<E>` | Single/multi-select scrollable list |
| **JSlider** | `JSlider` | Range input with tick marks |
| **JProgressBar** | `JProgressBar` | Task progress indicator |
| **JSpinner** | `JSpinner` | Step-through number/list/date |
| **JTable** | `JTable` | Grid data — wrap in `JScrollPane` |
| **JTree** | `JTree` | Hierarchical collapsible data |
| **JFrame** | `JFrame` | Main window — use `EXIT_ON_CLOSE` |
| **JPanel** | `JPanel` | Grouping container, custom paint |
| **JScrollPane** | `JScrollPane` | Scrollable wrapper |
| **JTabbedPane** | `JTabbedPane` | Tabbed navigation |
| **JSplitPane** | `JSplitPane` | Two resizable sections |
| **JDialog** | `JDialog` | Custom modal/non-modal popups |
| **JOptionPane** | `JOptionPane` | Quick message/confirm/input dialogs |
| **JFileChooser** | `JFileChooser` | Cross-platform file picker |
| **JColorChooser** | `JColorChooser` | Color picker dialog |
| **JMenuBar/JMenu/JMenuItem** | — | Full menu with icons + shortcuts |
| **JToolBar** | `JToolBar` | Dockable quick-action bar |

```
Swing Components
├── Atomic    → input or display data     (JLabel, JButton, JTextField...)
├── Container → hold and organize         (JPanel, JFrame, JScrollPane...)
├── Dialogs   → user interaction popups   (JOptionPane, JFileChooser...)
└── Menus     → navigation and actions    (JMenuBar, JMenu, JMenuItem...)

Always launch Swing on the EDT:
SwingUtilities.invokeLater(() -> new MyFrame());
```