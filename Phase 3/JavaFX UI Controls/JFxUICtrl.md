# JavaFX UI Controls

---

## Table of Contents

1. [Overview](#1-overview)
2. [JavaFX Control Hierarchy](#2-javafx-control-hierarchy)
3. [Text Controls](#3-text-controls)
   - [Label](#31-label)
   - [TextField](#32-textfield)
   - [PasswordField](#33-passwordfield)
   - [TextArea](#34-textarea)
4. [Button Controls](#4-button-controls)
   - [Button](#41-button)
   - [CheckBox](#42-checkbox)
   - [RadioButton & ToggleGroup](#43-radiobutton--togglegroup)
   - [ToggleButton](#44-togglebutton)
5. [Selection Controls](#5-selection-controls)
   - [ComboBox](#51-combobox)
   - [ChoiceBox](#52-choicebox)
   - [ListView](#53-listview)
   - [ColorPicker](#54-colorpicker)
   - [DatePicker](#55-datepicker)
6. [Range Controls](#6-range-controls)
   - [Slider](#61-slider)
   - [ProgressBar](#62-progressbar)
   - [ProgressIndicator](#63-progressindicator)
   - [Spinner](#64-spinner)
7. [Display Controls](#7-display-controls)
   - [ImageView](#71-imageview)
   - [TableView](#72-tableview)
   - [TreeView](#73-treeview)
   - [TreeTableView](#74-treetableview)
8. [Container Controls](#8-container-controls)
   - [ScrollPane](#81-scrollpane)
   - [TitledPane](#82-titledpane)
   - [Accordion](#83-accordion)
   - [TabPane & Tab](#84-tabpane--tab)
   - [SplitPane](#85-splitpane)
9. [Menu Controls](#9-menu-controls)
   - [MenuBar, Menu, MenuItem](#91-menubar-menu-menuitem)
   - [ContextMenu](#92-contextmenu)
   - [ToolBar](#93-toolbar)
10. [Dialog Controls](#10-dialog-controls)
    - [Alert](#101-alert)
    - [Dialog](#102-dialog)
    - [FileChooser](#103-filechooser)
    - [DirectoryChooser](#104-directorychooser)
11. [JavaFX Control Events Reference](#11-javafx-control-events-reference)
12. [JavaFX Properties & Binding](#12-javafx-properties--binding)
13. [CSS Styling in JavaFX](#13-css-styling-in-javafx)
14. [Complete Controls Reference Table](#14-complete-controls-reference-table)
15. [Summary](#15-summary)

---

## 1. Overview

JavaFX UI Controls are pre-built, skinnable, and observable-property-aware widgets. Unlike Swing (covered in 3.3), every JavaFX control:

- Extends `javafx.scene.control.Control`
- Has **JavaFX Properties** (e.g., `textProperty()`, `valueProperty()`) that support **bidirectional binding**
- Is styleable via **CSS** (`-fx-*` properties)
- Supports **event handlers** using lambda-friendly `setOn*` methods
- Is rendered via the **Prism** graphics engine, not the OS widget set

**C++ note:** JavaFX controls are analogous to Qt widgets (`QLabel`, `QPushButton`, etc.) — both use signal/slot (event) patterns and property systems for reactive UI.

---

## 2. JavaFX Control Hierarchy

```
Object
 └── Node                              (javafx.scene)
      └── Parent
           └── Region
                └── Control            (javafx.scene.control)
                     ├── Labeled
                     │    ├── Label
                     │    ├── Button
                     │    ├── CheckBox
                     │    ├── RadioButton
                     │    └── ToggleButton
                     ├── TextInputControl
                     │    ├── TextField
                     │    │    └── PasswordField
                     │    └── TextArea
                     ├── ComboBoxBase
                     │    ├── ComboBox<T>
                     │    ├── ColorPicker
                     │    └── DatePicker
                     ├── ListView<T>
                     ├── TableView<S>
                     ├── TreeView<T>
                     ├── TreeTableView<S>
                     ├── Slider
                     ├── ProgressBar       ← also ProgressIndicator
                     ├── Spinner<T>
                     ├── ScrollPane
                     ├── TitledPane
                     ├── TabPane
                     ├── SplitPane
                     ├── MenuBar
                     └── ToolBar
```
*Fig. 1 — JavaFX Control class hierarchy (simplified)*

---

## 3. Text Controls

### 3.1 Label

A **non-editable** text/graphic display node.

**Swing equivalent:** `JLabel`

**Syntax:**
```java
Label label = new Label("Hello, JavaFX!");
Label labelWithIcon = new Label("Icon", new ImageView(image));
```

| Method / Property | Description |
|---|---|
| `setText(String)` | Set display text |
| `getText()` | Get display text |
| `setGraphic(Node)` | Set icon/node beside text |
| `setFont(Font)` | Set font |
| `setTextFill(Color)` | Set text color |
| `setWrapText(boolean)` | Enable text wrapping |
| `setAlignment(Pos)` | Align content |
| `textProperty()` | Observable property for binding |

```java
Label lbl = new Label("Score: 0");
// Bind label text to an IntegerProperty
IntegerProperty score = new SimpleIntegerProperty(0);
lbl.textProperty().bind(score.asString("Score: %d"));
```

---

### 3.2 TextField

A **single-line** editable text input.

**Swing equivalent:** `JTextField`

**Syntax:**
```java
TextField tf = new TextField();
TextField tfWithPrompt = new TextField();
tfWithPrompt.setPromptText("Enter your name...");
```

| Method / Property | Description |
|---|---|
| `setText(String)` | Set text content |
| `getText()` | Get current text |
| `setPromptText(String)` | Placeholder/hint text |
| `setEditable(boolean)` | Enable/disable editing |
| `setPrefColumnCount(int)` | Preferred visible columns |
| `textProperty()` | Observable string property |
| `setOnAction(handler)` | Fire on Enter key |

```java
TextField tf = new TextField();
tf.setPromptText("Username");
tf.setOnAction(e -> System.out.println("Entered: " + tf.getText()));
```

---

### 3.3 PasswordField

Extends `TextField` — masks input with bullets. No extra constructor arguments.

**Swing equivalent:** `JPasswordField`

```java
PasswordField pf = new PasswordField();
pf.setPromptText("Password");

Button login = new Button("Login");
login.setOnAction(e -> {
    String pwd = pf.getText();   // getText() — NOT getPassword() like Swing
    System.out.println("Password length: " + pwd.length());
});
```

> **vs Swing:** Swing uses `getPassword()` returning `char[]` for security; JavaFX `getText()` returns `String`. Neither is memory-secure in modern JVMs, but `char[]` is the safer convention.

---

### 3.4 TextArea

A **multi-line** editable text field.

**Swing equivalent:** `JTextArea` (usually wrapped in `JScrollPane`)

**Syntax:**
```java
TextArea ta = new TextArea();
ta.setPrefRowCount(5);
ta.setPrefColumnCount(30);
ta.setWrapText(true);
```

| Method / Property | Description |
|---|---|
| `setText(String)` / `getText()` | Set/get text |
| `appendText(String)` | Append to existing content |
| `setWrapText(boolean)` | Wrap long lines |
| `setPrefRowCount(int)` | Preferred visible rows |
| `setPrefColumnCount(int)` | Preferred visible columns |
| `setScrollTop(double)` | Scroll position |
| `textProperty()` | Observable property |

```java
TextArea log = new TextArea();
log.setEditable(false);       // Read-only output area
log.setWrapText(true);
log.appendText("App started.\n");
```

> **Key difference from Swing:** JavaFX `TextArea` has **built-in scroll support** — no need to wrap in `ScrollPane` (though it can be).

---

## 4. Button Controls

```
ButtonBase (abstract)
 ├── Button          ← pushable, fires ActionEvent
 ├── CheckBox        ← tri-state (checked / unchecked / indeterminate)
 ├── RadioButton     ← use with ToggleGroup
 └── ToggleButton    ← stays pressed; use with ToggleGroup
```
*Fig. 2 — Button control hierarchy*

---

### 4.1 Button

Standard clickable button. Extends `ButtonBase → Labeled`.

**Swing equivalent:** `JButton`

```java
Button btn = new Button("Click Me");
btn.setGraphic(new ImageView(icon));     // optional icon
btn.setDefaultButton(true);             // triggers on Enter key
btn.setCancelButton(true);              // triggers on Escape key
btn.setOnAction(e -> System.out.println("Clicked!"));
```

| Method | Description |
|---|---|
| `setDefaultButton(boolean)` | Fires on Enter key |
| `setCancelButton(boolean)` | Fires on Escape key |
| `setDisable(boolean)` | Enable/disable |
| `setOnAction(EventHandler)` | Primary click handler |

---

### 4.2 CheckBox

Three states: **checked**, **unchecked**, **indeterminate**.

**Swing equivalent:** `JCheckBox`

```java
CheckBox cb = new CheckBox("Accept Terms");
cb.setAllowIndeterminate(true);         // enable 3rd state

cb.selectedProperty().addListener((obs, oldVal, newVal) -> {
    System.out.println("Checked: " + newVal);
});
```

| Method / Property | Description |
|---|---|
| `isSelected()` | `true` if checked |
| `setSelected(boolean)` | Set checked state |
| `isIndeterminate()` | `true` if indeterminate |
| `setAllowIndeterminate(boolean)` | Enable tri-state cycling |
| `selectedProperty()` | `BooleanProperty` for binding |

---

### 4.3 RadioButton & ToggleGroup

`RadioButton` works inside a `ToggleGroup` — only one can be selected at a time.

**Swing equivalent:** `JRadioButton` + `ButtonGroup`

```java
ToggleGroup group = new ToggleGroup();

RadioButton rb1 = new RadioButton("Male");
RadioButton rb2 = new RadioButton("Female");
RadioButton rb3 = new RadioButton("Other");

rb1.setToggleGroup(group);
rb2.setToggleGroup(group);
rb3.setToggleGroup(group);
rb1.setSelected(true);    // default selection

group.selectedToggleProperty().addListener((obs, old, newToggle) -> {
    RadioButton selected = (RadioButton) newToggle;
    System.out.println("Selected: " + selected.getText());
});
```

```
ToggleGroup
 ├── [●] Male       ← rb1 (selected)
 ├── [ ] Female     ← rb2
 └── [ ] Other      ← rb3
```
*Fig. 3 — RadioButton + ToggleGroup relationship*

---

### 4.4 ToggleButton

A button that **stays pressed** when clicked; click again to release.  
Often used with `ToggleGroup` for tab-like selections.

**Swing equivalent:** `JToggleButton`

```java
ToggleButton tb = new ToggleButton("Bold");
tb.selectedProperty().addListener((obs, old, isSelected) -> {
    System.out.println("Bold: " + isSelected);
});
```

---

## 5. Selection Controls

### 5.1 ComboBox

Editable or non-editable **drop-down list** that shows one selected item.

**Swing equivalent:** `JComboBox`

```java
ComboBox<String> cb = new ComboBox<>();
cb.getItems().addAll("Red", "Green", "Blue");
cb.setValue("Red");                     // default
cb.setEditable(true);                  // allows typing custom value

cb.setOnAction(e -> 
    System.out.println("Selected: " + cb.getValue())
);
```

| Method | Description |
|---|---|
| `getItems()` | Returns `ObservableList<T>` |
| `getValue()` | Currently selected item |
| `setValue(T)` | Set selected item |
| `setEditable(boolean)` | Allow custom typed input |
| `valueProperty()` | `ObjectProperty<T>` for binding |
| `setPromptText(String)` | Hint when nothing is selected |

---

### 5.2 ChoiceBox

Like `ComboBox` but **non-editable** and simpler — no cell factory support.

**Swing equivalent:** (simpler form of `JComboBox`)

```java
ChoiceBox<String> choice = new ChoiceBox<>();
choice.getItems().addAll("Small", "Medium", "Large");
choice.getSelectionModel().selectFirst();

choice.getSelectionModel().selectedItemProperty().addListener(
    (obs, old, newVal) -> System.out.println("Chosen: " + newVal)
);
```

> **ComboBox vs ChoiceBox:** Use `ComboBox` when you need cell customization, editing, or large lists with `setCellFactory`. Use `ChoiceBox` for simple, small, read-only lists.

---

### 5.3 ListView

Displays a **scrollable list** of items; supports single or multiple selection.

**Swing equivalent:** `JList`

```java
ListView<String> lv = new ListView<>();
lv.getItems().addAll("Apple", "Banana", "Cherry");
lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
lv.setPrefHeight(120);

lv.getSelectionModel().selectedItemProperty().addListener(
    (obs, old, newVal) -> System.out.println("Selected: " + newVal)
);

// Get all selected items (multi-select):
ObservableList<String> selected = lv.getSelectionModel().getSelectedItems();
```

| Method | Description |
|---|---|
| `getItems()` | `ObservableList<T>` backing the list |
| `getSelectionModel()` | `MultipleSelectionModel<T>` |
| `setCellFactory(Callback)` | Custom cell renderer |
| `setOrientation(Orientation)` | `HORIZONTAL` or `VERTICAL` |

---

### 5.4 ColorPicker

A specialized `ComboBoxBase` for **color selection** with a built-in color palette.

**Swing equivalent:** `JColorChooser` (but modal; ColorPicker is inline)

```java
ColorPicker cp = new ColorPicker(Color.BLUE);

cp.setOnAction(e -> {
    Color chosen = cp.getValue();
    System.out.printf("R=%.2f G=%.2f B=%.2f%n",
        chosen.getRed(), chosen.getGreen(), chosen.getBlue());
});
```

---

### 5.5 DatePicker

Allows the user to select a **date** from a popup calendar.

**Swing equivalent:** No direct equivalent (required external library, e.g., JDatePicker)

```java
DatePicker dp = new DatePicker(LocalDate.now());
dp.setShowWeekNumbers(true);

dp.valueProperty().addListener((obs, old, newDate) -> {
    System.out.println("Date: " + newDate);  // LocalDate
});
```

| Method | Description |
|---|---|
| `getValue()` | Returns `LocalDate` |
| `setValue(LocalDate)` | Set current date |
| `setShowWeekNumbers(boolean)` | Show ISO week numbers |
| `setDayCellFactory(Callback)` | Disable/style specific days |

```java
// Disable past dates:
dp.setDayCellFactory(picker -> new DateCell() {
    @Override public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        setDisable(date.isBefore(LocalDate.now()));
    }
});
```

---

## 6. Range Controls

### 6.1 Slider

A draggable **thumb** on a track; represents a value in a min–max range.

**Swing equivalent:** `JSlider`

```java
Slider slider = new Slider(0, 100, 50);  // min, max, initial
slider.setShowTickLabels(true);
slider.setShowTickMarks(true);
slider.setMajorTickUnit(25);
slider.setMinorTickCount(4);
slider.setSnapToTicks(true);

slider.valueProperty().addListener((obs, old, newVal) ->
    System.out.println("Value: " + newVal.intValue())
);
```

| Method | Description |
|---|---|
| `getValue()` / `setValue(double)` | Current value |
| `setMin(double)` / `setMax(double)` | Range |
| `setOrientation(Orientation)` | `HORIZONTAL` / `VERTICAL` |
| `setMajorTickUnit(double)` | Spacing for major ticks |
| `valueProperty()` | `DoubleProperty` for binding |

---

### 6.2 ProgressBar

Displays **determinate or indeterminate** horizontal progress.

**Swing equivalent:** `JProgressBar`

```java
ProgressBar pb = new ProgressBar(0.0);   // 0.0 → 1.0
pb.setPrefWidth(200);

// Indeterminate (animated):
ProgressBar indeterminate = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);

// Bind to a Task:
pb.progressProperty().bind(task.progressProperty());
```

| Value | Meaning |
|---|---|
| `0.0 – 1.0` | Fraction complete (determinate) |
| `-1.0` (`INDETERMINATE_PROGRESS`) | Continuous animation |

---

### 6.3 ProgressIndicator

A **circular** progress control — same API as `ProgressBar`.

**Swing equivalent:** Custom (no direct equivalent; often used JProgressBar styled)

```java
ProgressIndicator pi = new ProgressIndicator();
pi.setProgress(0.65);    // 65%
pi.setPrefSize(60, 60);

// Show percentage text:
pi.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
```

```
ProgressBar:       [████████░░░░]  67%
ProgressIndicator:    ( 67% )
                       circular
```
*Fig. 4 — ProgressBar vs ProgressIndicator*

---

### 6.4 Spinner

A **numeric or list** field with increment/decrement arrows.

**Swing equivalent:** `JSpinner`

```java
// Integer spinner
Spinner<Integer> spinner = new Spinner<>(1, 100, 10, 5);
// (min, max, initial, step)

spinner.setEditable(true);

spinner.valueProperty().addListener((obs, old, newVal) ->
    System.out.println("Spinner: " + newVal)
);

// String spinner (list of items):
SpinnerValueFactory<String> factory =
    new SpinnerValueFactory.ListSpinnerValueFactory<>(
        FXCollections.observableArrayList("Low", "Medium", "High")
    );
Spinner<String> strSpinner = new Spinner<>(factory);
```

---

## 7. Display Controls

### 7.1 ImageView

Displays an **image** (PNG, JPG, GIF, BMP). Not a `Control` — extends `Node` directly — but commonly used alongside controls.

**Swing equivalent:** `JLabel` with `ImageIcon`

```java
Image img = new Image("file:photo.png");          // from file
Image netImg = new Image("https://example.com/img.png", true); // async
ImageView iv = new ImageView(img);
iv.setFitWidth(200);
iv.setFitHeight(150);
iv.setPreserveRatio(true);
iv.setSmooth(true);
```

| Method | Description |
|---|---|
| `setFitWidth(double)` | Scale to width |
| `setFitHeight(double)` | Scale to height |
| `setPreserveRatio(boolean)` | Keep aspect ratio |
| `setViewport(Rectangle2D)` | Crop/sub-image |

---

### 7.2 TableView

Displays **tabular data** backed by an `ObservableList`. Requires defining `TableColumn<S,T>` objects with cell value factories.

**Swing equivalent:** `JTable`

```java
// Model class
public class Student {
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty age = new SimpleIntegerProperty();
    // getters, setters, property accessors
}

// TableView setup
TableView<Student> table = new TableView<>();

TableColumn<Student, String> nameCol = new TableColumn<>("Name");
nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

TableColumn<Student, Integer> ageCol = new TableColumn<>("Age");
ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

table.getColumns().addAll(nameCol, ageCol);
table.getItems().addAll(
    new Student("Alice", 20),
    new Student("Bob", 21)
);

// Selection:
table.getSelectionModel().selectedItemProperty().addListener(
    (obs, old, selected) -> System.out.println("Row: " + selected.getName())
);
```

```
┌──────────────┬─────┐
│ Name         │ Age │
├──────────────┼─────┤
│ Alice        │  20 │
│ Bob          │  21 │   ← selected row
└──────────────┴─────┘
```
*Fig. 5 — TableView layout*

| Key API | Description |
|---|---|
| `getColumns()` | `ObservableList<TableColumn<S,?>>`|
| `getItems()` | `ObservableList<S>` — table data |
| `getSelectionModel()` | `TableViewSelectionModel<S>` |
| `setEditable(true)` | Enable cell editing |
| `setCellFactory(Callback)` | Custom cell rendering |
| `setSortPolicy(Callback)` | Custom sort logic |

---

### 7.3 TreeView

Displays **hierarchical tree** data. Each node is a `TreeItem<T>`.

**Swing equivalent:** `JTree`

```java
TreeItem<String> root = new TreeItem<>("Root");
root.setExpanded(true);

TreeItem<String> child1 = new TreeItem<>("Child 1");
TreeItem<String> child2 = new TreeItem<>("Child 2");
child1.getChildren().add(new TreeItem<>("Leaf 1.1"));
root.getChildren().addAll(child1, child2);

TreeView<String> tree = new TreeView<>(root);
tree.setShowRoot(true);

tree.getSelectionModel().selectedItemProperty().addListener(
    (obs, old, item) -> System.out.println("Node: " + item.getValue())
);
```

```
▼ Root
  ▶ Child 1
      Leaf 1.1
    Child 2
```
*Fig. 6 — TreeView node structure*

---

### 7.4 TreeTableView

Combines `TreeView` + `TableView` — hierarchical rows with **multiple columns**.

**Swing equivalent:** No direct equivalent

```java
TreeTableView<Employee> ttv = new TreeTableView<>();

TreeTableColumn<Employee, String> nameCol = new TreeTableColumn<>("Name");
nameCol.setCellValueFactory(p ->
    p.getValue().getValue().nameProperty()
);

ttv.getColumns().add(nameCol);

TreeItem<Employee> rootItem = new TreeItem<>(new Employee("Dept Head"));
rootItem.getChildren().add(new TreeItem<>(new Employee("Alice")));
ttv.setRoot(rootItem);
```

---

## 8. Container Controls

These are controls that **contain other nodes** (not layout panes).

### 8.1 ScrollPane

Provides **scroll bars** for any content node that overflows.

**Swing equivalent:** `JScrollPane`

```java
ImageView large = new ImageView(new Image("file:large.png"));
ScrollPane sp = new ScrollPane(large);
sp.setFitToWidth(true);         // fit content to viewport width
sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
```

| Policy | Meaning |
|---|---|
| `ALWAYS` | Always show scrollbar |
| `NEVER` | Never show scrollbar |
| `AS_NEEDED` | Show only when content overflows |

---

### 8.2 TitledPane

A **collapsible panel** with a title bar. Content collapses/expands on click.

**Swing equivalent:** No direct equivalent (requires `JPanel` + custom logic)

```java
VBox content = new VBox(new Label("Line 1"), new Label("Line 2"));
TitledPane tp = new TitledPane("Settings", content);
tp.setExpanded(true);                  // default open
tp.setCollapsible(true);              // allow collapsing
```

---

### 8.3 Accordion

A **group of TitledPanes** where only **one** can be open at a time.

**Swing equivalent:** No direct equivalent

```java
TitledPane pane1 = new TitledPane("Section A", new Label("Content A"));
TitledPane pane2 = new TitledPane("Section B", new Label("Content B"));

Accordion accordion = new Accordion(pane1, pane2);
accordion.setExpandedPane(pane1);     // default expanded
```

```
▼ Section A      ← expanded
  Content A
▶ Section B      ← collapsed
```
*Fig. 7 — Accordion control*

---

### 8.4 TabPane & Tab

A **tabbed container** — each `Tab` holds its own content node.

**Swing equivalent:** `JTabbedPane`

```java
Tab tab1 = new Tab("Home", new Label("Home content"));
Tab tab2 = new Tab("Settings", new VBox(new CheckBox("Dark Mode")));
tab1.setClosable(false);             // prevent close button

TabPane tabPane = new TabPane(tab1, tab2);
tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

tabPane.getSelectionModel().selectedItemProperty().addListener(
    (obs, old, newTab) -> System.out.println("Tab: " + newTab.getText())
);
```

| Closing Policy | Meaning |
|---|---|
| `UNAVAILABLE` | No close buttons |
| `SELECTED_TAB` | Only selected tab has close button |
| `ALL_TABS` | All tabs have close buttons |

---

### 8.5 SplitPane

Divides the UI into **resizable panes** separated by a draggable divider.

**Swing equivalent:** `JSplitPane`

```java
ListView<String> left = new ListView<>();
TextArea right = new TextArea();

SplitPane sp = new SplitPane(left, right);
sp.setOrientation(Orientation.HORIZONTAL);
sp.setDividerPositions(0.3);         // 30% / 70% split
```

---

## 9. Menu Controls

### 9.1 MenuBar, Menu, MenuItem

```
MenuBar
 └── Menu ("File")
      ├── MenuItem ("New")
      ├── MenuItem ("Open")
      ├── SeparatorMenuItem
      ├── CheckMenuItem ("Auto-save")
      ├── RadioMenuItem ("UTF-8")
      └── Menu ("Recent")         ← sub-menu
           └── MenuItem ("file1.txt")
```
*Fig. 8 — Menu hierarchy*

**Swing equivalent:** `JMenuBar`, `JMenu`, `JMenuItem`

```java
MenuItem newItem  = new MenuItem("New");
MenuItem openItem = new MenuItem("Open");
MenuItem exitItem = new MenuItem("Exit");

newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
exitItem.setOnAction(e -> Platform.exit());

SeparatorMenuItem sep = new SeparatorMenuItem();
CheckMenuItem autosave = new CheckMenuItem("Auto-Save");

Menu fileMenu = new Menu("File");
fileMenu.getItems().addAll(newItem, openItem, sep, autosave, exitItem);

MenuBar menuBar = new MenuBar(fileMenu);
menuBar.setUseSystemMenuBar(true);   // macOS native menubar
```

---

### 9.2 ContextMenu

A **right-click popup menu**.

**Swing equivalent:** `JPopupMenu`

```java
ContextMenu cm = new ContextMenu();
MenuItem cut   = new MenuItem("Cut");
MenuItem copy  = new MenuItem("Copy");
MenuItem paste = new MenuItem("Paste");
cm.getItems().addAll(cut, copy, paste);

TextArea ta = new TextArea();
ta.setContextMenu(cm);               // attach to a control

// Or show manually:
ta.setOnMouseClicked(e -> {
    if (e.getButton() == MouseButton.SECONDARY)
        cm.show(ta, e.getScreenX(), e.getScreenY());
});
```

---

### 9.3 ToolBar

A **horizontal/vertical bar** for buttons and controls.

**Swing equivalent:** `JToolBar`

```java
Button save  = new Button("Save",   new ImageView(saveIcon));
Button open  = new Button("Open",   new ImageView(openIcon));
Separator sep = new Separator();
ComboBox<String> zoom = new ComboBox<>();
zoom.getItems().addAll("50%", "100%", "150%");

ToolBar tb = new ToolBar(save, open, sep, zoom);
tb.setOrientation(Orientation.HORIZONTAL);
```

---

## 10. Dialog Controls

### 10.1 Alert

A pre-built **modal dialog** for messages, confirmations, and errors.

**Swing equivalent:** `JOptionPane`

```java
// Information
Alert info = new Alert(Alert.AlertType.INFORMATION);
info.setTitle("Done");
info.setHeaderText("File Saved");
info.setContentText("Your file was saved successfully.");
info.showAndWait();

// Confirmation
Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
    "Delete this file?", ButtonType.YES, ButtonType.NO);
Optional<ButtonType> result = confirm.showAndWait();
if (result.isPresent() && result.get() == ButtonType.YES) {
    System.out.println("Deleted");
}
```

| Alert Type | Use |
|---|---|
| `INFORMATION` | Info message |
| `WARNING` | Warning |
| `ERROR` | Error message |
| `CONFIRMATION` | Yes/No prompt |
| `NONE` | Custom buttons only |

---

### 10.2 Dialog

A **fully customizable** modal dialog with custom content and buttons.

**Swing equivalent:** `JDialog`

```java
Dialog<String> dialog = new Dialog<>();
dialog.setTitle("Input");
dialog.setHeaderText("Enter your name:");

TextField input = new TextField();
dialog.getDialogPane().setContent(input);
dialog.getDialogPane().getButtonTypes().addAll(
    ButtonType.OK, ButtonType.CANCEL
);

dialog.setResultConverter(btn -> {
    if (btn == ButtonType.OK) return input.getText();
    return null;
});

Optional<String> result = dialog.showAndWait();
result.ifPresent(name -> System.out.println("Name: " + name));
```

---

### 10.3 FileChooser

Opens the **OS native file open/save dialog**.

**Swing equivalent:** `JFileChooser`

```java
FileChooser fc = new FileChooser();
fc.setTitle("Open File");
fc.setInitialDirectory(new File(System.getProperty("user.home")));
fc.getExtensionFilters().addAll(
    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
    new FileChooser.ExtensionFilter("All Files", "*.*")
);

// Open single file:
File file = fc.showOpenDialog(primaryStage);

// Save dialog:
File saveFile = fc.showSaveDialog(primaryStage);

// Open multiple:
List<File> files = fc.showOpenMultipleDialog(primaryStage);
```

---

### 10.4 DirectoryChooser

Opens the **OS native directory browser**.

**Swing equivalent:** `JFileChooser` with `DIRECTORIES_ONLY` mode

```java
DirectoryChooser dc = new DirectoryChooser();
dc.setTitle("Select Output Folder");
dc.setInitialDirectory(new File(System.getProperty("user.home")));

File dir = dc.showDialog(primaryStage);
if (dir != null) System.out.println("Selected: " + dir.getAbsolutePath());
```

---

## 11. JavaFX Control Events Reference

Every control's primary interaction method follows the `setOn*` naming convention.

| Control | Primary Event Method | Event Type |
|---|---|---|
| `Button` | `setOnAction(e -> ...)` | `ActionEvent` |
| `TextField` | `setOnAction(e -> ...)` | `ActionEvent` (Enter key) |
| `TextArea` | `textProperty().addListener(...)` | `ChangeListener` |
| `CheckBox` | `setOnAction(e -> ...)` | `ActionEvent` |
| `RadioButton` | `setOnAction(e -> ...)` | `ActionEvent` |
| `ToggleButton` | `setOnAction(e -> ...)` | `ActionEvent` |
| `ComboBox` | `setOnAction(e -> ...)` | `ActionEvent` |
| `ChoiceBox` | `getSelectionModel().selectedItemProperty()` | `ChangeListener` |
| `ListView` | `getSelectionModel().selectedItemProperty()` | `ChangeListener` |
| `Slider` | `valueProperty().addListener(...)` | `ChangeListener<Number>` |
| `Spinner` | `valueProperty().addListener(...)` | `ChangeListener<T>` |
| `DatePicker` | `setOnAction(e -> ...)` | `ActionEvent` |
| `ColorPicker` | `setOnAction(e -> ...)` | `ActionEvent` |
| `TabPane` | `getSelectionModel().selectedItemProperty()` | `ChangeListener<Tab>` |
| `TreeView` | `getSelectionModel().selectedItemProperty()` | `ChangeListener<TreeItem>` |
| `TableView` | `getSelectionModel().selectedItemProperty()` | `ChangeListener<S>` |
| `MenuItem` | `setOnAction(e -> ...)` | `ActionEvent` |
| Any Node | `setOnMouseClicked(e -> ...)` | `MouseEvent` |
| Any Node | `setOnKeyPressed(e -> ...)` | `KeyEvent` |

**Generic listener pattern:**
```java
control.someProperty().addListener((observable, oldValue, newValue) -> {
    // observable = the ObservableValue
    // oldValue   = previous value
    // newValue   = current value
});
```

---

## 12. JavaFX Properties & Binding

JavaFX's **property system** is one of its defining features over Swing. Every `Control` exposes Observable Properties that can be **listened to** or **bound** to other properties.

### Property Types

| Type | Interface | Concrete Class | Used In |
|---|---|---|---|
| `boolean` | `BooleanProperty` | `SimpleBooleanProperty` | `CheckBox.selectedProperty()` |
| `int` | `IntegerProperty` | `SimpleIntegerProperty` | `Spinner.valueProperty()` |
| `double` | `DoubleProperty` | `SimpleDoubleProperty` | `Slider.valueProperty()` |
| `String` | `StringProperty` | `SimpleStringProperty` | `TextField.textProperty()` |
| `Object` | `ObjectProperty<T>` | `SimpleObjectProperty<T>` | `ComboBox.valueProperty()` |
| `List` | `ListProperty<T>` | `SimpleListProperty<T>` | `ListView.itemsProperty()` |

### Unidirectional Binding (`bind`)

```java
// label.text always equals slider.value (formatted)
Label lbl = new Label();
Slider slider = new Slider(0, 100, 50);

lbl.textProperty().bind(slider.valueProperty().asString("%.1f"));
```

> Target (`lbl.textProperty`) follows source (`slider.valueProperty`).  
> You **cannot** set the target manually while it is bound.

### Bidirectional Binding (`bindBidirectional`)

```java
TextField tf1 = new TextField();
TextField tf2 = new TextField();

// Both stay in sync:
tf1.textProperty().bindBidirectional(tf2.textProperty());
```

### Binding Diagram

```
                     bind()  (unidirectional)
  Source Property ─────────────────────────────► Target Property
  slider.value                                   label.text
       │
       └─── change propagates automatically ───►  label updates

              bindBidirectional()
  Property A ◄────────────────────────────────► Property B
  tf1.text                                       tf2.text
       │◄──── either change syncs both ─────────►│
```
*Fig. 9 — Property binding types*

### Custom Observable Properties in Model Classes

```java
public class Product {
    private StringProperty name  = new SimpleStringProperty();
    private DoubleProperty price = new SimpleDoubleProperty();

    // Standard accessor pattern for JavaFX:
    public String  getName()               { return name.get(); }
    public void    setName(String v)       { name.set(v); }
    public StringProperty nameProperty()   { return name; }   // ← required for PropertyValueFactory

    public double  getPrice()              { return price.get(); }
    public void    setPrice(double v)      { price.set(v); }
    public DoubleProperty priceProperty()  { return price; }
}
```

> **C++ analogy:** JavaFX properties ≈ Qt's `Q_PROPERTY` + signals/slots. Both enable reactive UI without manual update calls.

---

## 13. CSS Styling in JavaFX

JavaFX uses a **CSS dialect** with `-fx-` prefixed properties. Apply via:
1. Inline style: `node.setStyle("-fx-background-color: #336699;");`
2. Stylesheet: `scene.getStylesheets().add("styles.css");`
3. ID selector: `node.setId("myButton");` → `#myButton { ... }`
4. Class selector: `node.getStyleClass().add("danger");` → `.danger { ... }`

**Example stylesheet (styles.css):**
```css
/* Button styling */
.button {
    -fx-background-color: #2c7be5;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 8 16 8 16;
    -fx-background-radius: 6;
    -fx-cursor: hand;
}
.button:hover {
    -fx-background-color: #1a65cc;
}
.button:pressed {
    -fx-background-color: #0f4fa8;
}

/* TextField */
.text-field {
    -fx-background-color: #f0f4f8;
    -fx-border-color: #ccd6e0;
    -fx-border-radius: 4;
    -fx-padding: 6;
    -fx-font-size: 13px;
}
.text-field:focused {
    -fx-border-color: #2c7be5;
}

/* Alert dialog header */
.dialog-pane > .header-panel {
    -fx-background-color: #f8f9fa;
}

/* TableView */
.table-view .column-header {
    -fx-background-color: #343a40;
    -fx-text-fill: white;
    -fx-font-weight: bold;
}
.table-row-cell:selected {
    -fx-background-color: #2c7be5;
    -fx-text-fill: white;
}

/* Progress bar */
.progress-bar > .bar {
    -fx-background-color: linear-gradient(to right, #00b09b, #96c93d);
    -fx-background-radius: 5;
}
.progress-bar > .track {
    -fx-background-color: #e9ecef;
}

/* Custom class */
.danger-btn {
    -fx-background-color: #dc3545;
    -fx-text-fill: white;
}
```

**Applying in Java:**
```java
// Inline:
button.setStyle("-fx-background-color: red; -fx-text-fill: white;");

// From file (placed in resources):
scene.getStylesheets().add(
    getClass().getResource("/styles.css").toExternalForm()
);

// Add style class:
button.getStyleClass().add("danger-btn");

// Set ID for unique targeting:
button.setId("submitBtn");   // CSS: #submitBtn { ... }
```

---

## 14. Complete Controls Reference Table

| Control | Package | Category | Swing Equivalent | Key Property | Primary Event |
|---|---|---|---|---|---|
| `Label` | `javafx.scene.control` | Text | `JLabel` | `textProperty()` | N/A (display only) |
| `TextField` | `javafx.scene.control` | Text | `JTextField` | `textProperty()` | `setOnAction` |
| `PasswordField` | `javafx.scene.control` | Text | `JPasswordField` | `textProperty()` | `setOnAction` |
| `TextArea` | `javafx.scene.control` | Text | `JTextArea` | `textProperty()` | `addListener` |
| `Button` | `javafx.scene.control` | Button | `JButton` | `textProperty()` | `setOnAction` |
| `CheckBox` | `javafx.scene.control` | Button | `JCheckBox` | `selectedProperty()` | `setOnAction` |
| `RadioButton` | `javafx.scene.control` | Button | `JRadioButton` | `selectedProperty()` | `setOnAction` |
| `ToggleButton` | `javafx.scene.control` | Button | `JToggleButton` | `selectedProperty()` | `setOnAction` |
| `ToggleGroup` | `javafx.scene.control` | Button | `ButtonGroup` | `selectedToggleProperty()` | `addListener` |
| `ComboBox<T>` | `javafx.scene.control` | Selection | `JComboBox` | `valueProperty()` | `setOnAction` |
| `ChoiceBox<T>` | `javafx.scene.control` | Selection | `JComboBox` | `valueProperty()` | `addListener` |
| `ListView<T>` | `javafx.scene.control` | Selection | `JList` | `selectionModel` | `addListener` |
| `ColorPicker` | `javafx.scene.control` | Selection | `JColorChooser` | `valueProperty()` | `setOnAction` |
| `DatePicker` | `javafx.scene.control` | Selection | *(none)* | `valueProperty()` | `setOnAction` |
| `Slider` | `javafx.scene.control` | Range | `JSlider` | `valueProperty()` | `addListener` |
| `ProgressBar` | `javafx.scene.control` | Range | `JProgressBar` | `progressProperty()` | (bind) |
| `ProgressIndicator` | `javafx.scene.control` | Range | *(none)* | `progressProperty()` | (bind) |
| `Spinner<T>` | `javafx.scene.control` | Range | `JSpinner` | `valueProperty()` | `addListener` |
| `ImageView` | `javafx.scene.image` | Display | `JLabel+ImageIcon` | `imageProperty()` | N/A |
| `TableView<S>` | `javafx.scene.control` | Display | `JTable` | `items` / selection | `addListener` |
| `TreeView<T>` | `javafx.scene.control` | Display | `JTree` | selection | `addListener` |
| `TreeTableView<S>` | `javafx.scene.control` | Display | *(none)* | selection | `addListener` |
| `ScrollPane` | `javafx.scene.control` | Container | `JScrollPane` | `contentProperty()` | N/A |
| `TitledPane` | `javafx.scene.control` | Container | *(none)* | `expandedProperty()` | `addListener` |
| `Accordion` | `javafx.scene.control` | Container | *(none)* | `expandedPane` | `addListener` |
| `TabPane` | `javafx.scene.control` | Container | `JTabbedPane` | selectionModel | `addListener` |
| `SplitPane` | `javafx.scene.control` | Container | `JSplitPane` | `dividerPositions` | N/A |
| `MenuBar` | `javafx.scene.control` | Menu | `JMenuBar` | `menus` | N/A |
| `Menu` | `javafx.scene.control` | Menu | `JMenu` | `items` | N/A |
| `MenuItem` | `javafx.scene.control` | Menu | `JMenuItem` | `textProperty()` | `setOnAction` |
| `ContextMenu` | `javafx.scene.control` | Menu | `JPopupMenu` | `items` | N/A |
| `ToolBar` | `javafx.scene.control` | Menu | `JToolBar` | `items` | N/A |
| `Alert` | `javafx.scene.control` | Dialog | `JOptionPane` | `alertType` | `showAndWait()` |
| `Dialog<R>` | `javafx.scene.control` | Dialog | `JDialog` | `resultConverter` | `showAndWait()` |
| `FileChooser` | `javafx.stage` | Dialog | `JFileChooser` | `extensionFilters` | `showOpenDialog()` |
| `DirectoryChooser` | `javafx.stage` | Dialog | `JFileChooser` | `initialDirectory` | `showDialog()` |

---

## 15. Summary

```
┌─────────────────────────────────────────────────────────────────────┐
│                  JavaFX UI Controls — Key Takeaways                 │
├────────────────────────┬────────────────────────────────────────────┤
│ All controls extend    │ Control → Region → Parent → Node           │
│ Property system        │ Every control exposes Observable Properties │
│ Binding                │ bind() / bindBidirectional() / addListener │
│ Events                 │ setOnAction, setOnMouseClicked, setOnKey*  │
│ Styling                │ CSS with -fx- prefix, stylesheets, classes │
│ Dialogs                │ Alert, Dialog<R>, FileChooser (modal)      │
│ vs Swing               │ Observable properties, CSS, no ScrollPane  │
│                        │ needed for TextArea; DatePicker built-in   │
│ vs C++ Qt              │ Similar to Q_PROPERTY + signal/slot system │
└────────────────────────┴────────────────────────────────────────────┘
```
*Fig. 10 — Summary overview*

**Key patterns to remember:**

- All controls are styled with **CSS** (`-fx-*`) — no custom `paint()` overrides needed
- Use **`PropertyValueFactory`** for `TableView` / `TreeTableView` column binding (requires proper `nameProperty()` accessor)
- **`ToggleGroup`** is mandatory for mutual exclusion with `RadioButton` and `ToggleButton`
- **`showAndWait()`** is the blocking call for all dialogs — returns `Optional<R>`
- **`ObservableList`** (`FXCollections.observableArrayList(...)`) is required as backing list for `ListView`, `TableView`, `ComboBox` — changes auto-reflect in the UI without manual refresh
- `FileChooser` and `DirectoryChooser` are **`javafx.stage`** classes, not `javafx.scene.control`

---
