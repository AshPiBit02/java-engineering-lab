# 📐 Layout Management in Java


---

## 📚 Table of Contents

- [1. What is Layout Management?](#1-what-is-layout-management)
- [2. FlowLayout](#2-flowlayout)
- [3. BorderLayout](#3-borderlayout)
- [4. GridLayout](#4-gridlayout)
- [5. GridBagLayout](#5-gridbaglayout)
- [6. BoxLayout](#6-boxlayout)
- [7. CardLayout](#7-cardlayout)
- [8. Null Layout (Absolute Positioning)](#8-null-layout-absolute-positioning)
- [9. Choosing the Right Layout](#9-choosing-the-right-layout)
- [10. Nesting Layouts](#10-nesting-layouts)
- [11. Summary](#11-summary)

---

## 1. What is Layout Management?

A **Layout Manager** is an object that **controls the size and position** of components inside a container. Instead of manually specifying pixel coordinates, layout managers automatically arrange components based on rules.

```
┌──────────────────────────────────────────────────────────────┐
│                 Why Layout Managers?                         │
│                                                              │
│   Without Layout Manager     With Layout Manager             │
│   ──────────────────────     ──────────────────────────      │
│   Manual x,y coordinates     Auto-arranged by rules          │
│   Breaks on resize           Adapts on resize                │
│   Different on each OS       Consistent cross-platform       │
│   Hard to maintain           Easy to change                  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Why Use Layout Managers**

Java provides several built-in layout managers — each with a different arrangement strategy:

```
┌──────────────────────────────────────────────────────────────┐
│                  Java Layout Managers                        │
│                                                              │
│   ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│   │ FlowLayout  │  │BorderLayout │  │ GridLayout  │          │
│   │ left→right  │  │ 5 regions   │  │  equal grid │          │
│   └─────────────┘  └─────────────┘  └─────────────┘          │
│   ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│   │GridBagLayout│  │  BoxLayout  │  │  CardLayout │          │
│   │ most flexible│  │ row/column  │  │  card stack │         │
│   └─────────────┘  └─────────────┘  └─────────────┘          │
│                    ┌─────────────┐                           │
│                    │ Null Layout │                           │
│                    │  (absolute) │                           │
│                    └─────────────┘                           │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Available Layout Managers**

Set layout on any container using:
```java
panel.setLayout(new FlowLayout());    // or any other layout
```

---

## 2. FlowLayout

**FlowLayout** arranges components in a **left-to-right row**, wrapping to the next line when the row is full. It is the **default layout for JPanel**.

```
┌──────────────────────────────────────────────────────────────┐
│                    FlowLayout                                │
│                                                              │
│   [ Button1 ] [ Button2 ] [ Button3 ] [ Button4 ]            │
│   [ Button5 ] [ Button6 ]                                    │
│                  (wraps to next line when full)              │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — FlowLayout Arrangement**

### Syntax

```java
new FlowLayout()                              // centered, 5px gaps
new FlowLayout(int align)                     // alignment
new FlowLayout(int align, int hgap, int vgap) // alignment + gaps
```

### Alignment Constants

| Constant | Description |
|----------|-------------|
| `FlowLayout.LEFT` | Align rows to left |
| `FlowLayout.CENTER` | Center rows (default) |
| `FlowLayout.RIGHT` | Align rows to right |
| `FlowLayout.LEADING` | Leading edge (LTR = left) |
| `FlowLayout.TRAILING` | Trailing edge (LTR = right) |

### Example

```java
JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

panel.add(new JButton("New"));
panel.add(new JButton("Open"));
panel.add(new JButton("Save"));
panel.add(new JButton("Print"));
```

**Best for:** Toolbars, button rows, simple left-to-right arrangements.

---

## 3. BorderLayout

**BorderLayout** divides a container into **5 named regions** — NORTH, SOUTH, EAST, WEST, and CENTER. Each region holds one component. CENTER expands to fill remaining space.

```
┌──────────────────────────────────────────────────────────────┐
│                    BorderLayout                              │
│                                                              │
│   ┌──────────────────────────────────────────────────────┐   │
│   │                    NORTH                             │   │
│   ├───────────┬──────────────────────────┬───────────────┤   │
│   │   WEST    │         CENTER           │     EAST      │   │
│   │           │   (fills remaining       │               │   │
│   │           │        space)            │               │   │
│   ├───────────┴──────────────────────────┴───────────────┤   │
│   │                    SOUTH                             │   │
│   └──────────────────────────────────────────────────────┘   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — BorderLayout Five Regions**

### Syntax

```java
new BorderLayout()                     // no gaps
new BorderLayout(int hgap, int vgap)   // with gaps
```

### Region Constants

| Constant | Position |
|----------|----------|
| `BorderLayout.NORTH` | Top |
| `BorderLayout.SOUTH` | Bottom |
| `BorderLayout.EAST` | Right |
| `BorderLayout.WEST` | Left |
| `BorderLayout.CENTER` | Middle — fills remaining space |

### Example

```java
JFrame frame = new JFrame();
frame.setLayout(new BorderLayout(5, 5));

frame.add(new JLabel("Header", JLabel.CENTER), BorderLayout.NORTH);
frame.add(new JPanel(),                         BorderLayout.CENTER);
frame.add(new JLabel("Status bar"),             BorderLayout.SOUTH);
frame.add(new JTree(),                          BorderLayout.WEST);
frame.add(new JList<>(),                        BorderLayout.EAST);
```

> 💡 **Default layout for JFrame's ContentPane.** You don't need to add all 5 regions — unused regions shrink to zero.

**Best for:** Main application windows, editor layouts (toolbar top, status bar bottom, tree left, content center).

---

## 4. GridLayout

**GridLayout** arranges components in a **uniform grid of rows and columns**. Every cell is the **same size**.

```
┌──────────────────────────────────────────────────────────────┐
│                    GridLayout (3 x 3)                        │
│                                                              │
│   ┌───────────┬───────────┬───────────┐                      │
│   │  Cell 1   │  Cell 2   │  Cell 3   │                      │
│   ├───────────┼───────────┼───────────┤                      │
│   │  Cell 4   │  Cell 5   │  Cell 6   │                      │
│   ├───────────┼───────────┼───────────┤                      │
│   │  Cell 7   │  Cell 8   │  Cell 9   │                      │
│   └───────────┴───────────┴───────────┘                      │
│            (all cells equal size)                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — GridLayout Equal Grid**

### Syntax

```java
new GridLayout(int rows, int cols)
new GridLayout(int rows, int cols, int hgap, int vgap)
```

> 💡 Set `rows = 0` to mean "as many rows as needed", or `cols = 0` for "as many columns as needed".

### Example

```java
// Calculator keypad — 4 rows, 3 cols
JPanel keypad = new JPanel(new GridLayout(4, 3, 5, 5));

String[] keys = {"7","8","9","4","5","6","1","2","3","0",".",""};
for (String key : keys) {
    keypad.add(new JButton(key));
}
```

**Best for:** Calculators, form grids, equally-sized button grids.

---

## 5. GridBagLayout

**GridBagLayout** is the **most flexible and powerful** layout manager. Components are placed in a grid but can **span multiple rows/columns** and have individual alignment and padding rules.

```
┌──────────────────────────────────────────────────────────────┐
│                  GridBagLayout                               │
│                                                              │
│   ┌─────────────────────────────────────────────────────┐    │
│   │           Label (spans 2 columns)                   │    │
│   ├──────────────────────────┬──────────────────────────┤    │
│   │  Name Label              │  TextField               │    │
│   ├──────────────────────────┼──────────────────────────┤    │
│   │  Email Label             │  TextField               │    │
│   ├──────────────────────────┴──────────────────────────┤    │
│   │              Button (spans 2 columns)               │    │
│   └─────────────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — GridBagLayout with Column Spanning**

### GridBagConstraints — Key Fields

| Field | Description |
|-------|-------------|
| `gridx`, `gridy` | Cell column and row position (0-based) |
| `gridwidth`, `gridheight` | How many columns/rows the component spans |
| `fill` | How component fills extra space |
| `weightx`, `weighty` | How extra space is distributed (0.0–1.0) |
| `anchor` | Where to place component in its cell |
| `insets` | Padding around the component |
| `ipadx`, `ipady` | Internal padding |

### `fill` Constants

| Constant | Description |
|----------|-------------|
| `NONE` | No fill (default) |
| `HORIZONTAL` | Stretch horizontally |
| `VERTICAL` | Stretch vertically |
| `BOTH` | Stretch in both directions |

### Example

```java
JPanel panel = new JPanel(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);

// Label — row 0, col 0
gbc.gridx = 0; gbc.gridy = 0;
gbc.anchor = GridBagConstraints.EAST;
panel.add(new JLabel("Name:"), gbc);

// TextField — row 0, col 1, fills horizontally
gbc.gridx = 1; gbc.gridy = 0;
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1.0;
panel.add(new JTextField(15), gbc);

// Button — row 1, spans 2 cols
gbc.gridx = 0; gbc.gridy = 1;
gbc.gridwidth = 2;
gbc.fill = GridBagConstraints.NONE;
gbc.anchor = GridBagConstraints.CENTER;
panel.add(new JButton("Submit"), gbc);
```

**Best for:** Complex, precise form layouts where components need different sizes or span multiple cells.

---

## 6. BoxLayout

**BoxLayout** arranges components in a **single row (X_AXIS)** or **single column (Y_AXIS)**. Respects each component's preferred, minimum, and maximum size.

```
┌──────────────────────────────────────────────────────────────┐
│    BoxLayout.X_AXIS (horizontal)                             │
│    [  Btn1  ] [  Btn2  ] [  Btn3  ]                          │
│                                                              │
│    BoxLayout.Y_AXIS (vertical)                               │
│    ┌──────────────┐                                          │
│    │   Button 1   │                                          │
│    ├──────────────┤                                          │
│    │   Button 2   │                                          │
│    ├──────────────┤                                          │
│    │   Button 3   │                                          │
│    └──────────────┘                                          │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — BoxLayout X_AXIS and Y_AXIS**

### Syntax

```java
new BoxLayout(container, BoxLayout.X_AXIS)  // horizontal
new BoxLayout(container, BoxLayout.Y_AXIS)  // vertical
```

> 💡 Use `Box.createRigidArea(new Dimension(w, h))` for fixed spacing, and `Box.createHorizontalGlue()` / `Box.createVerticalGlue()` for flexible spacing.

### Example

```java
JPanel sidebar = new JPanel();
sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

sidebar.add(new JButton("Dashboard"));
sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // 10px gap
sidebar.add(new JButton("Settings"));
sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
sidebar.add(new JButton("Logout"));
sidebar.add(Box.createVerticalGlue());  // push buttons to top
```

**Best for:** Sidebars, vertical menus, stacked button panels.

---

## 7. CardLayout

**CardLayout** stacks components like a **deck of cards** — only one is visible at a time. Used to switch between panels (like a wizard or tabbed view without tabs).

```
┌──────────────────────────────────────────────────────────────┐
│                    CardLayout                                │
│                                                              │
│   ┌──────────────────────────────────┐                       │
│   │          Card 1 (visible)        │  <-- shown now        │
│   └──────────────────────────────────┘                       │
│   ┌──────────────────────────────────┐                       │
│   │          Card 2 (hidden)         │  <-- behind           │
│   └──────────────────────────────────┘                       │
│   ┌──────────────────────────────────┐                       │
│   │          Card 3 (hidden)         │  <-- behind           │
│   └──────────────────────────────────┘                       │
│                                                              │
│   switch:  first() / next() / previous() / show(name)        │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 8 — CardLayout Deck of Panels**

### Syntax

```java
CardLayout cl = new CardLayout();
JPanel cards = new JPanel(cl);
```

### Navigation Methods

| Method | Description |
|--------|-------------|
| `cl.first(cards)` | Show first card |
| `cl.last(cards)` | Show last card |
| `cl.next(cards)` | Show next card |
| `cl.previous(cards)` | Show previous card |
| `cl.show(cards, "name")` | Show card by name |

### Example

```java
CardLayout cl = new CardLayout();
JPanel cards = new JPanel(cl);

JPanel step1 = new JPanel(); step1.add(new JLabel("Step 1: Personal Info"));
JPanel step2 = new JPanel(); step2.add(new JLabel("Step 2: Contact Info"));
JPanel step3 = new JPanel(); step3.add(new JLabel("Step 3: Confirmation"));

cards.add(step1, "step1");
cards.add(step2, "step2");
cards.add(step3, "step3");

JButton next = new JButton("Next");
next.addActionListener(e -> cl.next(cards));

JButton back = new JButton("Back");
back.addActionListener(e -> cl.previous(cards));
```

**Best for:** Wizards / setup screens, multi-step forms, login/register toggle panels.

---

## 8. Null Layout (Absolute Positioning)

Setting layout to `null` disables layout management — you manually set each component's **exact x, y, width, height** using `setBounds()`.

```java
panel.setLayout(null);

JButton btn = new JButton("Click");
btn.setBounds(50, 100, 120, 35);   // x, y, width, height
panel.add(btn);
```

```
┌──────────────────────────────────────────────────────────────┐
│  (0,0)                                                       │
│        x=50                                                  │
│   y=100  ┌───────────────┐                                   │
│          │     Click     │  w=120, h=35                      │
│          └───────────────┘                                   │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 9 — Null Layout with setBounds()**

> ⚠️ **Avoid null layout in production** — components don't resize or reposition when the window resizes, and look different on different screen resolutions/DPI settings. Fine for quick prototyping.

---

## 9. Choosing the Right Layout

```
┌──────────────────────────────────────────────────────────────┐
│              Layout Manager Decision Guide                   │
│                                                              │
│   Simple row of buttons / components?                        │
│       --> FlowLayout                                         │
│                                                              │
│   Header, footer, sidebar, main content?                     │
│       --> BorderLayout                                       │
│                                                              │
│   Equal-sized cells (calculator, keypad)?                    │
│       --> GridLayout                                         │
│                                                              │
│   Complex form, spanning rows/cols, precise control?         │
│       --> GridBagLayout                                      │
│                                                              │
│   Vertical stack / horizontal row (sidebar, toolbar)?        │
│       --> BoxLayout                                          │
│                                                              │
│   Switch between multiple panels (wizard, steps)?            │
│       --> CardLayout                                         │
│                                                              │
│   Quick prototype / fixed positions?                         │
│       --> Null Layout (avoid in production)                  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 10 — Layout Manager Decision Guide**

| Layout | Resizes | Complexity | Best Use |
|--------|---------|------------|---------|
| `FlowLayout` | ✅ | Low | Simple button rows |
| `BorderLayout` | ✅ | Low | Main app window |
| `GridLayout` | ✅ | Low | Equal-cell grids |
| `GridBagLayout` | ✅ | High | Complex forms |
| `BoxLayout` | ✅ | Medium | Sidebars, stacks |
| `CardLayout` | ✅ | Medium | Wizard / step views |
| Null Layout | ❌ | Low | Prototypes only |

---

## 10. Nesting Layouts

No single layout manager can handle every complex UI. The solution is **nesting containers** — each with its own layout manager.

```
┌──────────────────────────────────────────────────────────────┐
│  JFrame  (BorderLayout)                                      │
│  ┌──────────────────────────────────────────────────────┐    │
│  │  NORTH: JPanel (FlowLayout) — toolbar buttons        │    │
│  ├──────────────────────────────────────────────────────┤    │
│  │  CENTER: JPanel (GridBagLayout) — form fields        │    │
│  ├──────────────────────────────────────────────────────┤    │
│  │  SOUTH: JPanel (FlowLayout RIGHT) — OK / Cancel      │    │
│  └──────────────────────────────────────────────────────┘    │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 11 — Nesting Multiple Layout Managers**

```java
JFrame frame = new JFrame();
frame.setLayout(new BorderLayout());

// Toolbar at top — FlowLayout
JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
toolbar.add(new JButton("New"));
toolbar.add(new JButton("Open"));
toolbar.add(new JButton("Save"));

// Form in center — GridLayout
JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
form.add(new JLabel("Name:"));   form.add(new JTextField(15));
form.add(new JLabel("Email:"));  form.add(new JTextField(15));
form.add(new JLabel("Phone:"));  form.add(new JTextField(15));

// Buttons at bottom — FlowLayout right
JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
buttons.add(new JButton("Submit"));
buttons.add(new JButton("Cancel"));

frame.add(toolbar,  BorderLayout.NORTH);
frame.add(form,     BorderLayout.CENTER);
frame.add(buttons,  BorderLayout.SOUTH);
```

---

## 11. Summary

| Layout | Arrangement | Default For |
|--------|-------------|-------------|
| `FlowLayout` | Left-to-right, wraps | `JPanel` |
| `BorderLayout` | 5 regions (N/S/E/W/C) | `JFrame` ContentPane |
| `GridLayout` | Equal rows × columns | — |
| `GridBagLayout` | Flexible grid, spanning | — |
| `BoxLayout` | Single row or column | — |
| `CardLayout` | Stacked panels, one visible | — |
| Null Layout | Absolute `setBounds()` | Prototypes only |

```
FlowLayout    ->  simplest, left-to-right
BorderLayout  ->  main window structure (N/S/E/W/CENTER)
GridLayout    ->  uniform grid, all cells equal size
GridBagLayout ->  most powerful, most complex
BoxLayout     ->  row OR column, respects preferred sizes
CardLayout    ->  panel switching, wizard/multi-step UIs
Null Layout   ->  absolute coords, avoid in production

Best practice: NEST layouts — one layout rarely fits all needs
```