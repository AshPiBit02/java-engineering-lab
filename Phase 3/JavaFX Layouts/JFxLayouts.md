# JavaFX Layouts

---

## Table of Contents

1. [Overview](#1-overview)
2. [HBox — Horizontal Box](#2-hbox--horizontal-box)
3. [VBox — Vertical Box](#3-vbox--vertical-box)
4. [BorderPane](#4-borderpane)
5. [GridPane](#5-gridpane)
6. [FlowPane](#6-flowpane)
7. [TilePane](#7-tilepane)
8. [StackPane](#8-stackpane)
9. [AnchorPane](#9-anchorpane)
10. [Common Layout Properties](#10-common-layout-properties)
11. [Nesting Layouts](#11-nesting-layouts)
12. [Layout Decision Guide](#12-layout-decision-guide)
13. [Summary Table](#13-summary-table)

---

## 1. Overview

JavaFX layout panes are **containers** that automatically position and size their child nodes.
Every layout pane extends `javafx.scene.layout.Pane`, and all panes are themselves `Node`s — meaning they can be nested freely.

> Unlike Swing's `LayoutManager` pattern (where you set a manager on a container), JavaFX **bakes layout logic into the pane class itself**. You pick the right pane for the job.

```
javafx.scene.Node
    └── javafx.scene.Parent
            └── javafx.scene.layout.Region
                    └── javafx.scene.layout.Pane
                            ├── HBox
                            ├── VBox
                            ├── BorderPane
                            ├── GridPane
                            ├── FlowPane
                            ├── TilePane
                            ├── StackPane
                            └── AnchorPane
```

**Common imports block used throughout this file:**
```java
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
```

---

## 2. HBox — Horizontal Box

Places children in a **single horizontal row**, left-to-right.

```
Fig. 1 — HBox Layout
┌─────────────────────────────────────────┐
│  [  Btn A  ]  [  Btn B  ]  [  Btn C  ]  │
└─────────────────────────────────────────┘
     ◄────────── spacing ──────────►
```

### Syntax
```java
HBox hbox = new HBox();          // empty
HBox hbox = new HBox(10);        // spacing = 10px
HBox hbox = new HBox(10, nodeA, nodeB); // spacing + initial children
```

### Key Methods / Properties

| Method / Property | Description |
|---|---|
| `setSpacing(double)` | Gap between children |
| `setAlignment(Pos)` | Alignment of children within HBox |
| `setPadding(Insets)` | Inner padding around the HBox |
| `setFillHeight(boolean)` | Whether children stretch to fill height (default: `true`) |
| `HBox.setHgrow(node, Priority)` | Static — lets a child grow horizontally |
| `getChildren().add(node)` | Add a child node |

### Code Snippet
```java
HBox toolbar = new HBox(8);
toolbar.setAlignment(Pos.CENTER_LEFT);
toolbar.setPadding(new Insets(5, 10, 5, 10));

Button save = new Button("Save");
Button open = new Button("Open");
TextField search = new TextField();
HBox.setHgrow(search, Priority.ALWAYS); // search bar fills remaining space

toolbar.getChildren().addAll(save, open, search);
```

**Best for:** Toolbars, button rows, icon bars, form label+field pairs in a row.

---

## 3. VBox — Vertical Box

Places children in a **single vertical column**, top-to-bottom.

```
Fig. 2 — VBox Layout
┌──────────────────┐
│   [  Label   ]   │
│   [ TextField]   │
│   [  Button  ]   │
└──────────────────┘
     ▲ spacing ▼
```

### Syntax
```java
VBox vbox = new VBox();
VBox vbox = new VBox(12);                   // spacing = 12px
VBox vbox = new VBox(12, label, field, btn);
```

### Key Methods / Properties

| Method / Property | Description |
|---|---|
| `setSpacing(double)` | Gap between children |
| `setAlignment(Pos)` | Alignment of children within VBox |
| `setPadding(Insets)` | Inner padding |
| `setFillWidth(boolean)` | Whether children stretch to fill width (default: `true`) |
| `VBox.setVgrow(node, Priority)` | Static — lets a child grow vertically |

### Code Snippet
```java
VBox sidebar = new VBox(6);
sidebar.setPrefWidth(160);
sidebar.setPadding(new Insets(10));

Label header = new Label("Navigation");
header.setStyle("-fx-font-weight: bold;");
Button home   = new Button("Home");
Button about  = new Button("About");
Button exit   = new Button("Exit");

// Make buttons fill full width
home.setMaxWidth(Double.MAX_VALUE);
about.setMaxWidth(Double.MAX_VALUE);
exit.setMaxWidth(Double.MAX_VALUE);

sidebar.getChildren().addAll(header, home, about, exit);
```

**Best for:** Sidebars, form fields stacked vertically, navigation menus.

---

## 4. BorderPane

Divides the scene into **five regions**: Top, Bottom, Left, Right, and Center.
Only Center grows to fill all remaining space.

```
Fig. 3 — BorderPane Layout
┌──────────────────────────────────────┐
│             TOP (toolbar)            │
├────────┬─────────────────┬───────────┤
│        │                 │           │
│  LEFT  │     CENTER      │   RIGHT   │
│ (nav)  │   (main area)   │  (panel)  │
│        │                 │           │
├────────┴─────────────────┴───────────┤
│            BOTTOM (status bar)       │
└──────────────────────────────────────┘
```

> All five regions are optional. Unset regions collapse to zero size.

### Syntax
```java
BorderPane bp = new BorderPane();
bp.setTop(node);
bp.setBottom(node);
bp.setLeft(node);
bp.setRight(node);
bp.setCenter(node);
```

### Key Methods / Properties

| Method | Description |
|---|---|
| `setTop/Bottom/Left/Right/Center(Node)` | Place a node in the named region |
| `getCenter()` | Retrieve center node |
| `BorderPane.setAlignment(node, Pos)` | Align a node within its region |
| `BorderPane.setMargin(node, Insets)` | Outer margin for a node in its region |

### Code Snippet
```java
BorderPane root = new BorderPane();

MenuBar menuBar = new MenuBar();
root.setTop(menuBar);

TextArea editor = new TextArea();
root.setCenter(editor);

VBox nav = new VBox(4, new Button("File"), new Button("Edit"));
nav.setPadding(new Insets(5));
root.setLeft(nav);

Label status = new Label("Ready");
BorderPane.setAlignment(status, Pos.CENTER_LEFT);
root.setBottom(status);

Scene scene = new Scene(root, 800, 600);
```

**Best for:** Application shells — top menu/toolbar, side navigation, central workspace.

---

## 5. GridPane

Arranges children in a **flexible grid of rows and columns**.
Each cell can span multiple rows/columns. Column and row sizes are independently configurable.

```
Fig. 4 — GridPane Layout (3×3 example)
         col 0        col 1        col 2
       ┌────────────┬────────────┬────────────┐
row 0  │  [Name:]   │ [TextField─────────────]│  ← colspan=2
       ├────────────┼────────────┼────────────┤
row 1  │  [Email:]  │ [TextField]│            │
       ├────────────┼────────────┼────────────┤
row 2  │            │  [Cancel]  │   [OK]     │
       └────────────┴────────────┴────────────┘
```

### Syntax
```java
GridPane grid = new GridPane();
// add(node, col, row)
grid.add(node, 0, 0);
// add(node, col, row, colspan, rowspan)
grid.add(node, 1, 0, 2, 1);
```

### Key Methods / Properties

| Method / Property | Description |
|---|---|
| `add(Node, col, row)` | Add node at grid position |
| `add(Node, col, row, colSpan, rowSpan)` | Add with spanning |
| `setHgap(double)` / `setVgap(double)` | Horizontal / vertical gap between cells |
| `setAlignment(Pos)` | Alignment of entire grid within the pane |
| `setPadding(Insets)` | Inner padding |
| `getColumnConstraints()` | Access column sizing rules |
| `getRowConstraints()` | Access row sizing rules |
| `GridPane.setHalignment(node, HPos)` | Horizontal align of node within its cell |
| `GridPane.setValignment(node, VPos)` | Vertical align of node within its cell |

### Code Snippet
```java
GridPane form = new GridPane();
form.setHgap(10);
form.setVgap(8);
form.setPadding(new Insets(15));

// Column constraints: label fixed, field grows
ColumnConstraints labelCol = new ColumnConstraints(80);
ColumnConstraints fieldCol = new ColumnConstraints(100, 150, Double.MAX_VALUE);
fieldCol.setHgrow(Priority.ALWAYS);
form.getColumnConstraints().addAll(labelCol, fieldCol);

form.add(new Label("Name:"),  0, 0);
form.add(new TextField(),     1, 0);
form.add(new Label("Email:"), 0, 1);
form.add(new TextField(),     1, 1);

Button submit = new Button("Submit");
GridPane.setHalignment(submit, HPos.RIGHT);
form.add(submit, 1, 2);
```

**Best for:** Data entry forms, settings dialogs, any structured label+field grid.

---

## 6. FlowPane

Children are laid out in a row; when a row **runs out of space, it wraps** to the next row (like CSS `flex-wrap`). Can also be set to wrap columns vertically.

```
Fig. 5 — FlowPane Layout (HORIZONTAL, wrapping)
┌──────────────────────────────────┐
│ [Tag1] [Tag2] [Tag3] [Tag4]      │
│ [Tag5] [Tag6]                    │  ← wrapped row
└──────────────────────────────────┘
   resize window narrower ↓
┌────────────────┐
│ [Tag1] [Tag2]  │
│ [Tag3] [Tag4]  │
│ [Tag5] [Tag6]  │
└────────────────┘
```

### Syntax
```java
FlowPane fp = new FlowPane();                          // HORIZONTAL (default)
FlowPane fp = new FlowPane(Orientation.VERTICAL);      // VERTICAL
FlowPane fp = new FlowPane(hgap, vgap);
```

### Key Methods / Properties

| Method / Property | Description |
|---|---|
| `setOrientation(Orientation)` | `HORIZONTAL` or `VERTICAL` |
| `setHgap(double)` / `setVgap(double)` | Gap between items |
| `setAlignment(Pos)` | Alignment of rows/columns within pane |
| `setRowValignment(VPos)` | Vertical alignment of items within a row |
| `setColumnHalignment(HPos)` | Horizontal alignment in vertical mode |
| `setPrefWrapLength(double)` | Preferred width (H) or height (V) before wrapping |

### Code Snippet
```java
FlowPane tagCloud = new FlowPane(6, 6);
tagCloud.setPadding(new Insets(10));
tagCloud.setPrefWrapLength(300);

String[] tags = {"Java", "JavaFX", "OOP", "GUI", "FXML", "CSS"};
for (String tag : tags) {
    Button btn = new Button(tag);
    btn.setStyle("-fx-background-radius: 12;");
    tagCloud.getChildren().add(btn);
}
```

**Best for:** Tag clouds, chip groups, image galleries, any dynamically-sized item lists.

---

## 7. TilePane

Like FlowPane but all tiles are **the same size** (sized to the largest child). Gives a uniform grid feel that reflows on resize.

```
Fig. 6 — TilePane Layout
┌───────────────────────────────────────┐
│ ┌───────┐ ┌───────┐ ┌───────┐         │
│ │ App 1 │ │ App 2 │ │ App 3 │         │
│ └───────┘ └───────┘ └───────┘         │
│ ┌───────┐ ┌───────┐                   │
│ │ App 4 │ │ App 5 │                   │
│ └───────┘ └───────┘                   │
└───────────────────────────────────────┘
  All tiles = same width & height
```

### Syntax
```java
TilePane tp = new TilePane();
TilePane tp = new TilePane(hgap, vgap);
TilePane tp = new TilePane(Orientation.VERTICAL, hgap, vgap);
```

### Key Methods / Properties

| Method / Property | Description |
|---|---|
| `setPrefColumns(int)` | Preferred number of columns (H mode) |
| `setPrefRows(int)` | Preferred number of rows (V mode) |
| `setHgap(double)` / `setVgap(double)` | Gap between tiles |
| `setTileAlignment(Pos)` | Alignment of node within its tile |
| `setAlignment(Pos)` | Alignment of all tiles in the pane |
| `setPrefTileWidth/Height(double)` | Override computed tile size |

### Code Snippet
```java
TilePane appGrid = new TilePane(10, 10);
appGrid.setPrefColumns(4);
appGrid.setTileAlignment(Pos.CENTER);
appGrid.setPadding(new Insets(10));

String[] apps = {"Browser", "Files", "Music", "Photos", "Settings", "Terminal"};
for (String app : apps) {
    VBox tile = new VBox(4, new Label("🔷"), new Label(app));
    tile.setAlignment(Pos.CENTER);
    appGrid.getChildren().add(tile);
}
```

**Best for:** App launchers, icon grids, card grids where uniform sizing is required.

> **FlowPane vs TilePane:** FlowPane tiles can differ in size; TilePane forces uniform sizing.

---

## 8. StackPane

**Stacks all children on top of each other** (Z-axis layering). The last child added is drawn on top.

```
Fig. 7 — StackPane Layer Model
         (top view)                 (side / z-axis view)
┌─────────────────────────┐         ──────────── Text (z=2, top)
│                         │         ──────────── Button (z=1)
│       [  Label  ]       │    →    ──────────── Background Rect (z=0, bottom)
│                         │
└─────────────────────────┘
  All children share same bounds
```

### Syntax
```java
StackPane sp = new StackPane();
StackPane sp = new StackPane(nodeA, nodeB); // A at bottom, B on top
```

### Key Methods / Properties

| Method / Property | Description |
|---|---|
| `setAlignment(Pos)` | Default alignment for all children |
| `StackPane.setAlignment(node, Pos)` | Per-child alignment override |
| `StackPane.setMargin(node, Insets)` | Per-child margin (to offset from center) |

### Code Snippet
```java
// Card with a badge overlay
Rectangle card = new Rectangle(120, 80);
card.setArcWidth(10); card.setArcHeight(10);
card.setStyle("-fx-fill: #2196F3;");

Label title = new Label("Dashboard");
title.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

Label badge = new Label("3");
badge.setStyle("-fx-background-color: red; -fx-text-fill: white; "
             + "-fx-background-radius: 8; -fx-padding: 2 5 2 5;");
StackPane.setAlignment(badge, Pos.TOP_RIGHT);
StackPane.setMargin(badge, new Insets(5, 5, 0, 0));

StackPane cardPane = new StackPane(card, title, badge);
```

**Best for:** Overlaying text on images, notification badges, loading spinners over content, dialog backdrops.

---

## 9. AnchorPane

Children are **anchored** to one or more edges of the pane by fixed pixel distances. When the pane resizes, nodes move/stretch to maintain their anchors.

```
Fig. 8 — AnchorPane Anchor Points
┌──────────────────────────────────────┐
│◄─10px─►[Toolbar]◄──────────────10px─►│  ← anchored L+R+Top
│                                      │
│         [Central Content]            │  ← anchored all 4 sides (stretches)
│                                      │
│◄─10px─►[Status Bar]◄───────────10px─►│  ← anchored L+R+Bottom
└──────────────────────────────────────┘
```

### Syntax
```java
AnchorPane ap = new AnchorPane();
AnchorPane.setTopAnchor(node, 10.0);
AnchorPane.setRightAnchor(node, 10.0);
AnchorPane.setBottomAnchor(node, 10.0);
AnchorPane.setLeftAnchor(node, 10.0);
ap.getChildren().add(node);
```

Setting **opposite anchors** (e.g., Left + Right) causes the node to **stretch** as the pane resizes.

### Key Static Methods / Properties

| Method | Description |
|---|---|
| `AnchorPane.setTopAnchor(node, val)` | Distance from top edge |
| `AnchorPane.setBottomAnchor(node, val)` | Distance from bottom edge |
| `AnchorPane.setLeftAnchor(node, val)` | Distance from left edge |
| `AnchorPane.setRightAnchor(node, val)` | Distance from right edge |
| Pass `null` to any setter | Clears that anchor constraint |

### Code Snippet
```java
AnchorPane root = new AnchorPane();

Button topLeft = new Button("Menu");
AnchorPane.setTopAnchor(topLeft, 10.0);
AnchorPane.setLeftAnchor(topLeft, 10.0);

Button bottomRight = new Button("Help");
AnchorPane.setBottomAnchor(bottomRight, 10.0);
AnchorPane.setRightAnchor(bottomRight, 10.0);

TextArea content = new TextArea();
// Stretch to fill all space with margins
AnchorPane.setTopAnchor(content, 45.0);
AnchorPane.setBottomAnchor(content, 45.0);
AnchorPane.setLeftAnchor(content, 10.0);
AnchorPane.setRightAnchor(content, 10.0);

root.getChildren().addAll(topLeft, bottomRight, content);
```

**Best for:** FXML-based UIs (Scene Builder default), absolute/pixel-precise layouts, resizable panels that must maintain edge distances.

> ⚠️ **C++ Note:** Unlike C++ UI frameworks where you often manage coordinates manually, AnchorPane handles coordinate math on resize automatically.

---

## 10. Common Layout Properties

### Insets

`Insets` specifies padding or margin on all four sides.

```java
// All sides equal
new Insets(10)

// top, right, bottom, left  (like CSS)
new Insets(10, 15, 10, 15)
```

### Pos — Alignment Constants

`Pos` enum values (used in `setAlignment()`):

```
Fig. 9 — Pos Constants Grid
┌───────────────┬──────────────┬────────────────┐
│  TOP_LEFT     │  TOP_CENTER  │  TOP_RIGHT     │
├───────────────┼──────────────┼────────────────┤
│  CENTER_LEFT  │   CENTER     │  CENTER_RIGHT  │
├───────────────┼──────────────┼────────────────┤
│  BOTTOM_LEFT  │ BOTTOM_CENTER│  BOTTOM_RIGHT  │
└───────────────┴──────────────┴────────────────┘
  BASELINE_LEFT, BASELINE_CENTER, BASELINE_RIGHT also available
```

### Priority — Grow/Shrink Behaviour

Used with `HBox.setHgrow()` and `VBox.setVgrow()`:

| Constant | Meaning |
|---|---|
| `Priority.ALWAYS` | Node always takes extra space when available |
| `Priority.SOMETIMES` | Node takes space if nothing with ALWAYS wants it |
| `Priority.NEVER` | Node never grows beyond preferred size |

```java
// Classic "spacer" pattern — pushes siblings apart
Region spacer = new Region();
HBox.setHgrow(spacer, Priority.ALWAYS);
hbox.getChildren().addAll(leftBtn, spacer, rightBtn);
```

```
Fig. 10 — Priority.ALWAYS Spacer Pattern
┌──────────────────────────────────────────────────┐
│ [LeftBtn] ←──── spacer (ALWAYS) ────→ [RightBtn] │
└──────────────────────────────────────────────────┘
```

### ColumnConstraints & RowConstraints (GridPane)

```java
ColumnConstraints c1 = new ColumnConstraints();
c1.setPercentWidth(30);          // 30% of GridPane width
c1.setHgrow(Priority.SOMETIMES);

ColumnConstraints c2 = new ColumnConstraints();
c2.setPercentWidth(70);
c2.setHgrow(Priority.ALWAYS);

gridPane.getColumnConstraints().addAll(c1, c2);
```

### Margin vs Padding

| Concept | API | Scope |
|---|---|---|
| **Padding** | `pane.setPadding(Insets)` | Inside the pane (between border and children) |
| **Margin** | `HBox.setMargin(node, Insets)` etc. | Outside a specific child node |

---

## 11. Nesting Layouts

Real UIs combine multiple panes. The root is typically a `BorderPane`; inner regions use specialized panes.

```
Fig. 11 — Nested Layout Structure
BorderPane (root)
├── TOP    → HBox (toolbar: buttons + spacer + search)
├── LEFT   → VBox (nav menu: labels + buttons)
├── CENTER → StackPane
│               ├── background ImageView
│               └── GridPane (form overlay)
└── BOTTOM → HBox (status bar: label + progress)
```

### Code Example — Nested Layout

```java
// --- Top toolbar ---
Button newBtn  = new Button("New");
Button openBtn = new Button("Open");
Region spacer  = new Region();
HBox.setHgrow(spacer, Priority.ALWAYS);
TextField searchField = new TextField();
searchField.setPromptText("Search...");

HBox toolbar = new HBox(6, newBtn, openBtn, spacer, searchField);
toolbar.setPadding(new Insets(5, 10, 5, 10));
toolbar.setAlignment(Pos.CENTER_LEFT);

// --- Left sidebar ---
VBox sidebar = new VBox(4,
    new Label("Home"), new Label("Files"), new Label("Settings"));
sidebar.setPadding(new Insets(8));
sidebar.setPrefWidth(130);

// --- Center content (form on a card) ---
GridPane form = new GridPane();
form.setHgap(8); form.setVgap(8);
form.add(new Label("Title:"),     0, 0);
form.add(new TextField(),         1, 0);
form.add(new Label("Body:"),      0, 1);
form.add(new TextArea(),          1, 1);

StackPane centerCard = new StackPane(form);
centerCard.setPadding(new Insets(20));

// --- Root assembly ---
BorderPane root = new BorderPane();
root.setTop(toolbar);
root.setLeft(sidebar);
root.setCenter(centerCard);
root.setBottom(new Label("  Ready"));
```

---

## 12. Layout Decision Guide

```
Fig. 12 — Layout Decision Flowchart

         Start: What is the layout need?
                        │
          ┌─────────────┼──────────────┐
          │             │              │
     Row of items   Column of       Application
     horizontally   items vert.     shell / frame
          │             │              │
        HBox          VBox         BorderPane
                                       │
                        ┌──────────────┼──────────────┐
                        │              │              │
                   Grid / form    Stack items     Anchor to
                   (rows+cols)    on top of       edges
                        │         each other          │
                     GridPane      StackPane       AnchorPane
                        
         Need items that WRAP on resize?
          ├── Variable tile sizes → FlowPane
          └── Uniform tile sizes  → TilePane
```

| Pane | Use When |
|---|---|
| **HBox** | Toolbar, button row, horizontal nav |
| **VBox** | Sidebar, form fields stacked, vertical nav |
| **BorderPane** | App shell with top/bottom/side panels + main area |
| **GridPane** | Forms, tables, structured label+field alignment |
| **FlowPane** | Tags, chips, images that should wrap & reflow |
| **TilePane** | App icon grids, card decks — uniform tile sizes |
| **StackPane** | Overlays, badges, image+text, loading masks |
| **AnchorPane** | Scene Builder / FXML, pixel-precise edge anchoring |

---

## 13. Summary Table

| Pane | Layout Style | Children Resize? | Swing Equivalent | Key Property |
|---|---|---|---|---|
| `HBox` | Horizontal row | Optionally (Hgrow) | `FlowLayout` / `BoxLayout X_AXIS` | `spacing`, `alignment` |
| `VBox` | Vertical column | Optionally (Vgrow) | `BoxLayout Y_AXIS` | `spacing`, `fillWidth` |
| `BorderPane` | 5 named regions | Center auto-fills | `BorderLayout` | `setTop/Bottom/Left/Right/Center` |
| `GridPane` | Rows + columns grid | Via constraints | `GridBagLayout` | `setHgap`, `setVgap`, `ColumnConstraints` |
| `FlowPane` | Wrapping row/col | No | `FlowLayout` | `setOrientation`, `setPrefWrapLength` |
| `TilePane` | Uniform tile grid | No | `GridLayout` | `setPrefColumns`, `setTileAlignment` |
| `StackPane` | Z-axis stacking | Yes (fills pane) | `OverlayLayout` (partial) | `setAlignment` |
| `AnchorPane` | Anchor to edges | Via opposite anchors | `null` layout (absolute) | `setTopAnchor` etc. |

---

```java
// Quick Reference — All Panes in One Block
HBox     hbox = new HBox(spacing, children...);
VBox     vbox = new VBox(spacing, children...);
BorderPane bp = new BorderPane(center, top, right, bottom, left);
GridPane grid = new GridPane();   grid.add(node, col, row, colSpan, rowSpan);
FlowPane flow = new FlowPane(hgap, vgap);
TilePane tile = new TilePane(hgap, vgap);
StackPane  sp = new StackPane(bottom, middle, top);
AnchorPane ap = new AnchorPane();
    AnchorPane.setTopAnchor(node, 10.0);
    AnchorPane.setLeftAnchor(node, 10.0);
    ap.getChildren().add(node);

// Growth helpers
HBox.setHgrow(node, Priority.ALWAYS);
VBox.setVgrow(node, Priority.ALWAYS);

// Alignment & padding (works on all panes)
pane.setAlignment(Pos.CENTER);
pane.setPadding(new Insets(top, right, bottom, left));
```