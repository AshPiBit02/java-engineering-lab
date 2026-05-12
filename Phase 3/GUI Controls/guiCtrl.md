# 🎛️ GUI Controls in Java Swing


---

## 📚 Table of Contents

- [1. What are GUI Controls?](#1-what-are-gui-controls)
- [2. Text Controls](#2-text-controls)
- [3. Button Controls](#3-button-controls)
- [4. Selection Controls](#4-selection-controls)
- [5. List Controls](#5-list-controls)
- [6. Range Controls](#6-range-controls)
- [7. Display Controls](#7-display-controls)
- [8. Control Events Quick Reference](#8-control-events-quick-reference)
- [9. Summary](#9-summary)

---

## 1. What are GUI Controls?

**GUI Controls** (also called widgets) are the **interactive components** that users directly interact with — type into, click, select, or drag. They are the building blocks of any GUI application.

```
┌──────────────────────────────────────────────────────────────┐
│                   GUI Control Categories                     │
│                                                              │
│   Text Controls     →  JTextField, JTextArea, JPasswordField │
│   Button Controls   →  JButton, JCheckBox, JRadioButton      │
│   Selection Controls→  JComboBox, JList, ButtonGroup         │
│   Range Controls    →  JSlider, JProgressBar, JSpinner       │
│   Display Controls  →  JLabel, JTable, JTree                 │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — GUI Control Categories**

Every control inherits from `JComponent` and shares these **common properties**:

| Property | Method |
|----------|--------|
| Enable/disable | `setEnabled(boolean)` |
| Show/hide | `setVisible(boolean)` |
| Background | `setBackground(Color)` |
| Foreground | `setForeground(Color)` |
| Font | `setFont(Font)` |
| Tooltip | `setToolTipText(String)` |
| Border | `setBorder(Border)` |
| Size hint | `setPreferredSize(Dimension)` |

---

## 2. Text Controls

### JTextField — Single-line Input

```java
JTextField tf = new JTextField(20);          // 20 columns wide
tf.setText("default text");
tf.setEditable(false);                        // read-only
tf.setHorizontalAlignment(JTextField.CENTER); // center text

// Fires on Enter key
tf.addActionListener(e ->
    System.out.println("Entered: " + tf.getText()));

// Fires on every keystroke
tf.getDocument().addDocumentListener(new DocumentListener() {
    public void insertUpdate(DocumentEvent e)  { validate(); }
    public void removeUpdate(DocumentEvent e)  { validate(); }
    public void changedUpdate(DocumentEvent e) { validate(); }
    void validate() { /* real-time validation */ }
});
```

---

### JPasswordField — Masked Input

```java
JPasswordField pf = new JPasswordField(20);
pf.setEchoChar('●');

char[] password = pf.getPassword();  // use char[] not getText()
Arrays.fill(password, '0');          // zero out after use
```

---

### JTextArea — Multi-line Input

```java
JTextArea ta = new JTextArea(6, 30);
ta.setLineWrap(true);
ta.setWrapStyleWord(true);
ta.append("Added text\n");

// Always wrap in JScrollPane
JScrollPane scroll = new JScrollPane(ta,
    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
```

```
┌──────────────────────────────────────────────────────────────┐
│               Text Control Comparison                        │
│                                                              │
│   JTextField      →  single line, Enter fires ActionEvent    │
│   JPasswordField  →  single line, characters masked          │
│   JTextArea       →  multi-line, wrap support, no Enter event│
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Text Control Comparison**

---

## 3. Button Controls

### JButton — Push Button

```java
JButton btn = new JButton("Submit");
btn.setMnemonic(KeyEvent.VK_S);           // Alt+S triggers click
btn.setActionCommand("submit");           // identify button in shared listener

btn.addActionListener(e -> {
    if (e.getActionCommand().equals("submit")) {
        // handle submit
    }
});
```

**Customizing appearance:**
```java
btn.setBackground(new Color(70, 130, 180));
btn.setForeground(Color.WHITE);
btn.setFocusPainted(false);               // remove focus border
btn.setBorderPainted(false);              // flat look
btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
```

---

### JCheckBox — Toggle Control

```java
JCheckBox cb = new JCheckBox("Remember me", false);

cb.addItemListener(e -> {
    if (e.getStateChange() == ItemEvent.SELECTED)
        System.out.println("Checked");
    else
        System.out.println("Unchecked");
});

boolean checked = cb.isSelected();  // get state
cb.setSelected(true);               // set state
```

---

### JRadioButton — Exclusive Selection

> Must be grouped with `ButtonGroup` — only one selectable at a time.

```java
JRadioButton r1 = new JRadioButton("Male",   true);
JRadioButton r2 = new JRadioButton("Female");
JRadioButton r3 = new JRadioButton("Other");

ButtonGroup group = new ButtonGroup();
group.add(r1); group.add(r2); group.add(r3);

// Get selected
String selected = r1.isSelected() ? "Male" :
                  r2.isSelected() ? "Female" : "Other";
```

```
┌──────────────────────────────────────────────────────────────┐
│           JCheckBox  vs  JRadioButton                        │
│                                                              │
│   JCheckBox             JRadioButton + ButtonGroup           │
│   ──────────────────    ──────────────────────────────       │
│   ☑ Java                ◉ Male                              │
│   ☑ Python              ○ Female   <- only one              │
│   ☐ C++                 ○ Other                             │
│                                                              │
│   Multiple selectable   Only one selectable at a time        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — JCheckBox vs JRadioButton**

---

## 4. Selection Controls

### JComboBox — Drop-down List

```java
String[] items = {"Nepal", "India", "USA", "UK"};
JComboBox<String> cb = new JComboBox<>(items);

cb.setEditable(true);                  // allow custom typed value
cb.setSelectedIndex(0);

cb.addActionListener(e ->
    System.out.println("Selected: " + cb.getSelectedItem()));

// Dynamic modification
cb.addItem("Japan");
cb.removeItem("USA");
cb.removeAllItems();
```

---

### JList — Scrollable List

```java
String[] data = {"Java", "Python", "C++", "Go", "Rust"};
JList<String> list = new JList<>(data);

list.setSelectionMode(
    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
list.setVisibleRowCount(4);

list.addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting())
        System.out.println(list.getSelectedValuesList());
});

// Use DefaultListModel for dynamic data
DefaultListModel<String> model = new DefaultListModel<>();
model.addElement("Item 1");
model.removeElement("Item 1");
JList<String> dynamicList = new JList<>(model);
```

```
┌──────────────────────────────────────────────────────────────┐
│             JComboBox  vs  JList                             │
│                                                              │
│   JComboBox                  JList                           │
│   ─────────────────          ─────────────────────────       │
│   Compact dropdown           Always expanded                 │
│   Single select only         Single or multi-select          │
│   Can be editable            Not editable                    │
│   Space-efficient            Shows multiple items at once    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — JComboBox vs JList**

---

## 5. Range Controls

### JSlider — Value from a Range

```java
JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
slider.setMajorTickSpacing(20);
slider.setMinorTickSpacing(5);
slider.setPaintTicks(true);
slider.setPaintLabels(true);
slider.setSnapToTicks(true);

slider.addChangeListener(e -> {
    if (!slider.getValueIsAdjusting())
        System.out.println("Value: " + slider.getValue());
});
```

---

### JSpinner — Step-through Values

```java
// Number spinner
JSpinner numSp = new JSpinner(
    new SpinnerNumberModel(1, 1, 100, 1)); // value, min, max, step

// List spinner
JSpinner listSp = new JSpinner(
    new SpinnerListModel(new String[]{"Low","Medium","High"}));

numSp.addChangeListener(e ->
    System.out.println("Value: " + numSp.getValue()));
```

---

### JProgressBar — Progress Indicator

```java
JProgressBar pb = new JProgressBar(0, 100);
pb.setStringPainted(true);

// Determinate (known progress)
pb.setValue(75);

// Indeterminate (unknown duration — animated)
pb.setIndeterminate(true);
```

```
┌──────────────────────────────────────────────────────────────┐
│               Range Control Comparison                       │
│                                                              │
│   JSlider      ->  user drags knob to pick value             │
│   JSpinner     ->  user clicks up/down arrows                │
│   JProgressBar ->  display only, shows task progress         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Range Control Comparison**

---

## 6. Display Controls

### JLabel — Text and Image Display

```java
JLabel lbl = new JLabel("Welcome!", SwingConstants.CENTER);
lbl.setFont(new Font("Arial", Font.BOLD, 16));
lbl.setForeground(Color.DARK_GRAY);

// HTML support
JLabel html = new JLabel(
    "<html><b>Bold</b> and <i>italic</i> text</html>");

// With icon
JLabel icon = new JLabel("User", new ImageIcon("user.png"),
                           SwingConstants.LEFT);
```

---

### JTable — Grid Data Display

```java
String[]   cols = {"Name", "Age", "Grade"};
Object[][] data = {{"Aasii", 21, "A"}, {"Ram", 20, "B"}};

JTable table = new JTable(data, cols);
table.setRowHeight(25);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Always wrap in JScrollPane (shows column headers)
JScrollPane sp = new JScrollPane(table);

// Get selected row data
table.getSelectionModel().addListSelectionListener(e -> {
    int row = table.getSelectedRow();
    if (row >= 0)
        System.out.println(table.getValueAt(row, 0));
});
```

---

### JTree — Hierarchical Data

```java
DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
DefaultMutableTreeNode child = new DefaultMutableTreeNode("Child 1");
child.add(new DefaultMutableTreeNode("Leaf"));
root.add(child);

JTree tree = new JTree(root);
tree.setRootVisible(true);
tree.expandRow(0);

tree.addTreeSelectionListener(e -> {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    if (node != null)
        System.out.println("Selected: " + node.getUserObject());
});
```

---

## 7. Control Events Quick Reference

| Control | Primary Event | Listener | Key Method |
|---------|--------------|----------|------------|
| `JButton` | Click | `ActionListener` | `actionPerformed()` |
| `JTextField` | Enter key | `ActionListener` | `actionPerformed()` |
| `JTextField` | Any change | `DocumentListener` | `insertUpdate()` etc. |
| `JPasswordField` | Enter key | `ActionListener` | `getPassword()` |
| `JTextArea` | Any change | `DocumentListener` | `insertUpdate()` etc. |
| `JCheckBox` | Toggle | `ItemListener` | `itemStateChanged()` |
| `JRadioButton` | Toggle | `ItemListener` | `itemStateChanged()` |
| `JComboBox` | Selection | `ActionListener` | `getSelectedItem()` |
| `JList` | Selection | `ListSelectionListener` | `valueChanged()` |
| `JSlider` | Drag | `ChangeListener` | `stateChanged()` |
| `JSpinner` | Change | `ChangeListener` | `stateChanged()` |
| `JTable` | Row select | `ListSelectionListener` | `valueChanged()` |
| `JTree` | Node select | `TreeSelectionListener` | `valueChanged()` |

---

## 8. Summary

```
┌──────────────────────────────────────────────────────────────┐
│                   GUI Controls at a Glance                   │
│                                                              │
│   Text       JTextField, JPasswordField, JTextArea           │
│   Buttons    JButton, JCheckBox, JRadioButton                │
│   Selection  JComboBox, JList + DefaultListModel             │
│   Range      JSlider, JSpinner, JProgressBar                 │
│   Display    JLabel, JTable, JTree                           │
│                                                              │
│   All share: setEnabled(), setVisible(), setFont(),          │
│              setBackground(), setForeground(),               │
│              setToolTipText(), setBorder()                   │
└──────────────────────────────────────────────────────────────┘
```

| Control | Use When |
|---------|---------|
| `JTextField` | Single-line text input |
| `JPasswordField` | Sensitive/masked input |
| `JTextArea` | Multi-line text input |
| `JButton` | Trigger a one-time action |
| `JCheckBox` | Multiple independent toggles |
| `JRadioButton` | One choice from a group |
| `JComboBox` | Space-efficient dropdown |
| `JList` | Multi-select from visible list |
| `JSlider` | Continuous range value |
| `JSpinner` | Stepped value selection |
| `JProgressBar` | Show task completion |
| `JLabel` | Static text or image display |
| `JTable` | Tabular/grid data |
| `JTree` | Hierarchical/nested data |