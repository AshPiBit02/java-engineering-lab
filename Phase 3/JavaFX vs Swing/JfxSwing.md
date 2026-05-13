# ⚔️ JavaFX vs Swing

---

## 📚 Table of Contents

- [1. Overview](#1-overview)
- [2. Architecture Comparison](#2-architecture-comparison)
- [3. Feature-by-Feature Comparison](#3-feature-by-feature-comparison)
- [4. UI Components Comparison](#4-ui-components-comparison)
- [5. Styling — CSS vs Look and Feel](#5-styling--css-vs-look-and-feel)
- [6. Layout Management](#6-layout-management)
- [7. Event Handling](#7-event-handling)
- [8. Scene Graph vs Component Hierarchy](#8-scene-graph-vs-component-hierarchy)
- [9. Multimedia and Graphics](#9-multimedia-and-graphics)
- [10. FXML — JavaFX Declarative UI](#10-fxml--javafx-declarative-ui)
- [11. Threading Model](#11-threading-model)
- [12. When to Use Which](#12-when-to-use-which)
- [13. Summary](#13-summary)

---

## 1. Overview

Both **Swing** and **JavaFX** are Java GUI frameworks — but they come from different eras and philosophies.

```
┌──────────────────────────────────────────────────────────────┐
│                  Swing  vs  JavaFX — Timeline                │
│                                                              │
│   1996  AWT introduced (Java 1.0)                            │
│   1998  Swing introduced (Java 1.2) — built on AWT           │
│   2008  JavaFX 1.0 released (JavaFX Script)                  │
│   2011  JavaFX 2.0 — pure Java API, replaced Swing           │
│   2014  JavaFX bundled into JDK 8                            │
│   2018  JavaFX removed from JDK 11 → OpenJFX                 │
│   Now   JavaFX = modern standard, Swing = legacy/maintained  │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 1 — Swing vs JavaFX Timeline**

- **Swing** — mature, stable, widely used in legacy enterprise apps
- **JavaFX** — modern replacement, hardware-accelerated, CSS styling, FXML

---

## 2. Architecture Comparison

```
┌──────────────────────────────────────────────────────────────┐
│                Swing Architecture                            │
│                                                              │
│   Java Code (Swing Components)                               │
│        │                                                     │
│        ▼                                                     │
│   Java 2D API  (software rendering)                          │
│        │                                                     │
│        ▼                                                     │
│   OS Graphics Context                                        │
│                                                              │
│   ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─            │
│                JavaFX Architecture                           │
│                                                              │
│   Java Code / FXML                                           │
│        │                                                     │
│        ▼                                                     │
│   Scene Graph  (structured node tree)                        │
│        │                                                     │
│        ▼                                                     │
│   Prism  (hardware-accelerated rendering)                    │
│        │                                                     │
│        ▼                                                     │
│   DirectX (Win) / OpenGL (Mac/Linux) / Software fallback     │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 2 — Swing vs JavaFX Architecture**

---

## 3. Feature-by-Feature Comparison

| Feature | Swing | JavaFX |
|---------|-------|--------|
| Introduced | Java 1.2 (1998) | JavaFX 2.0 (2011) |
| Package | `javax.swing` | `javafx.*` |
| Rendering | Java 2D (software) | Prism (GPU-accelerated) |
| Styling | Pluggable Look & Feel | CSS stylesheets |
| Layout | Layout Manager classes | Layout Panes |
| UI definition | Code only | Code **or** FXML |
| Animation | Manual / Timers | Built-in Animation API |
| Media | ❌ Limited | ✅ Audio, Video, WebView |
| 3D support | ❌ No | ✅ Built-in 3D |
| Scene Graph | ❌ No | ✅ Yes |
| Data binding | ❌ Manual | ✅ Property binding |
| Charts | ❌ None built-in | ✅ Built-in chart types |
| WebView | ❌ None | ✅ Embedded browser |
| Touch/gesture | ❌ Limited | ✅ Native support |
| JDK bundled | ✅ Always | ❌ Separate (OpenJFX 11+) |
| Actively developed | ❌ Maintenance only | ✅ Actively developed |
| Legacy app support | ✅ Excellent | ⚠️ Limited |
| Learning resources | ✅ Abundant | ✅ Growing |

---

## 4. UI Components Comparison

| Purpose | Swing | JavaFX |
|---------|-------|--------|
| Label | `JLabel` | `Label` |
| Button | `JButton` | `Button` |
| Text field | `JTextField` | `TextField` |
| Password | `JPasswordField` | `PasswordField` |
| Text area | `JTextArea` | `TextArea` |
| Checkbox | `JCheckBox` | `CheckBox` |
| Radio button | `JRadioButton` | `RadioButton` |
| Combo box | `JComboBox` | `ComboBox` |
| List | `JList` | `ListView` |
| Table | `JTable` | `TableView` |
| Tree | `JTree` | `TreeView` |
| Slider | `JSlider` | `Slider` |
| Progress bar | `JProgressBar` | `ProgressBar` |
| Menu bar | `JMenuBar` | `MenuBar` |
| Tab pane | `JTabbedPane` | `TabPane` |
| Split pane | `JSplitPane` | `SplitPane` |
| Scroll pane | `JScrollPane` | `ScrollPane` |
| Main window | `JFrame` | `Stage` |
| Container | `JPanel` | `Pane` |
| Dialog | `JDialog` / `JOptionPane` | `Dialog` / `Alert` |
| Web browser | ❌ | `WebView` |
| Charts | ❌ | `LineChart`, `BarChart`, `PieChart` |
| Media player | ❌ | `MediaPlayer` |

---

## 5. Styling — CSS vs Look and Feel

### Swing — Pluggable Look and Feel

```java
// Set OS native look
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

// Set Nimbus (cross-platform modern look)
UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

// Set Metal (default Java look)
UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
```

> Styling in Swing is **all-or-nothing** — you apply a L&F globally. Per-component styling is limited and verbose.

---

### JavaFX — CSS Styling

```css
/* styles.css */
.button {
    -fx-background-color: #2980b9;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-padding: 8px 20px;
    -fx-border-radius: 5px;
}

.button:hover {
    -fx-background-color: #3498db;
}

.text-field {
    -fx-border-color: #bdc3c7;
    -fx-border-radius: 4px;
}
```

```java
// Apply to scene
scene.getStylesheets().add("styles.css");

// Apply to single node
button.getStyleClass().add("primary-btn");
button.setStyle("-fx-background-color: green;"); // inline style
```

```
┌──────────────────────────────────────────────────────────────┐
│              Styling Comparison                              │
│                                                              │
│   Swing                        JavaFX                        │
│   ─────────────────────        ──────────────────────────    │
│   Pluggable Look & Feel        CSS stylesheets               │
│   Global, all-or-nothing       Per-node or global            │
│   Verbose Java code styling    Standard CSS syntax           │
│   No :hover, :focus pseudo     Full CSS pseudo-classes       │
│   Limited per-component        Fine-grained per-node         │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 3 — Swing L&F vs JavaFX CSS Styling**

---

## 6. Layout Management

### Swing Layouts

```java
panel.setLayout(new BorderLayout());
panel.setLayout(new GridLayout(3, 2));
panel.setLayout(new FlowLayout());
panel.setLayout(new GridBagLayout());
panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
```

### JavaFX Layout Panes

```java
BorderPane bp  = new BorderPane();   // North/South/East/West/Center
HBox       hb  = new HBox(10);       // horizontal row (like FlowLayout)
VBox       vb  = new VBox(10);       // vertical column (like BoxLayout Y)
GridPane   gp  = new GridPane();     // grid (like GridBagLayout)
FlowPane   fp  = new FlowPane();     // wrapping flow
StackPane  sp  = new StackPane();    // stack on top of each other
AnchorPane ap  = new AnchorPane();   // anchor to edges (like null layout)
TilePane   tp  = new TilePane();     // equal-size tiles (like GridLayout)
```

```
┌──────────────────────────────────────────────────────────────┐
│            Swing  vs  JavaFX Layouts                         │
│                                                              │
│   Swing                  JavaFX                              │
│   ───────────────────    ───────────────────────             │
│   BorderLayout       ->  BorderPane                          │
│   FlowLayout         ->  FlowPane / HBox                     │
│   BoxLayout (Y)      ->  VBox                                │
│   BoxLayout (X)      ->  HBox                                │
│   GridLayout         ->  TilePane                            │
│   GridBagLayout      ->  GridPane                            │
│   Null layout        ->  AnchorPane                          │
│   (no equivalent)    ->  StackPane                           │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 4 — Swing Layouts vs JavaFX Layout Panes**

---

## 7. Event Handling

### Swing — Listener Interfaces

```java
button.addActionListener(e ->
    System.out.println("Clicked!"));

slider.addChangeListener(e ->
    System.out.println("Value: " + slider.getValue()));
```

### JavaFX — Event Handlers & Properties

```java
button.setOnAction(e ->
    System.out.println("Clicked!"));

slider.valueProperty().addListener((obs, oldVal, newVal) ->
    System.out.println("Value: " + newVal));
```

```
┌──────────────────────────────────────────────────────────────┐
│             Event Handling Comparison                        │
│                                                              │
│   Swing                          JavaFX                      │
│   ─────────────────────          ──────────────────────      │
│   addActionListener(...)         setOnAction(...)            │
│   addMouseListener(...)          setOnMouseClicked(...)      │
│   addKeyListener(...)            setOnKeyPressed(...)        │
│   Listener interfaces            EventHandler<T> functional  │
│   Separate registration method  setOn* directly on node      │
│   No property binding            Observable property binding │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 5 — Event Handling Comparison**

### JavaFX Property Binding *(no Swing equivalent)*

```java
Label  countLabel = new Label();
Slider slider     = new Slider(0, 100, 50);

// Bind label text to slider value — auto-updates!
countLabel.textProperty().bind(
    slider.valueProperty().asString("Volume: %.0f"));
```

---

## 8. Scene Graph vs Component Hierarchy

```
┌──────────────────────────────────────────────────────────────┐
│           Swing Component Tree  vs  JavaFX Scene Graph       │
│                                                              │
│   SWING                          JAVAFX                      │
│   ────────────────────           ──────────────────────      │
│   JFrame                         Stage (OS window)           │
│   └── ContentPane                └── Scene                   │
│       └── JPanel                     └── Root Node (Pane)    │
│           ├── JButton                    ├── Button          │
│           ├── JLabel                     ├── Label           │
│           └── JTextField                └── TextField        │
│                                                              │
│   Swing: Component tree (Container holds Component)          │
│   JavaFX: Scene Graph (Parent node holds Child nodes)        │
│                                                              │
│   JavaFX Scene Graph enables:                                │
│   - CSS styling per node                                     │
│   - Transformations (rotate, scale, translate)               │
│   - Effects (blur, shadow, glow)                             │
│   - Clipping and opacity per node                            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 6 — Swing Component Tree vs JavaFX Scene Graph**

---

## 9. Multimedia and Graphics

| Capability | Swing | JavaFX |
|------------|-------|--------|
| 2D drawing | ✅ via `Graphics` / Canvas | ✅ via `Canvas` / shapes |
| 3D graphics | ❌ | ✅ Built-in 3D shapes |
| Animation | ❌ Manual `Timer` | ✅ `Timeline`, `TranslateTransition`, etc. |
| Audio playback | ❌ Limited (`AudioClip`) | ✅ `MediaPlayer` (MP3, WAV) |
| Video playback | ❌ | ✅ `MediaView` |
| Embedded browser | ❌ | ✅ `WebView` (WebKit) |
| Image effects | ❌ | ✅ Drop shadow, blur, glow |
| CSS transitions | ❌ | ✅ |

### JavaFX Animation (brief)

```java
// Fade out a button over 2 seconds
FadeTransition fade = new FadeTransition(Duration.seconds(2), button);
fade.setFromValue(1.0);
fade.setToValue(0.0);
fade.play();

// Move a node
TranslateTransition move = new TranslateTransition(Duration.millis(500), node);
move.setToX(200);
move.setToY(100);
move.play();
```

---

## 10. FXML — JavaFX Declarative UI

**FXML** is an XML-based format for defining JavaFX UIs — separating UI structure from logic (like HTML + JavaScript).

```xml
<!-- login.fxml -->
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.control.*?>

<VBox spacing="10" xmlns:fx="http://javafx.com/fxml"
      fx:controller="com.app.LoginController">

    <Label text="Username:" />
    <TextField fx:id="userField" />

    <Label text="Password:" />
    <PasswordField fx:id="passField" />

    <Button text="Login" onAction="#handleLogin" />
</VBox>
```

```java
// LoginController.java
public class LoginController {
    @FXML private TextField    userField;
    @FXML private PasswordField passField;

    @FXML
    void handleLogin(ActionEvent e) {
        System.out.println("User: " + userField.getText());
    }
}
```

> Swing has **no equivalent** — all UI must be built in code.

```
┌──────────────────────────────────────────────────────────────┐
│              FXML — Separation of Concerns                   │
│                                                              │
│   FXML file        <- UI structure (designer's work)         │
│   Controller class <- Logic (developer's work)               │
│   CSS file         <- Styling (designer's work)              │
│                                                              │
│   Similar to:  HTML + JavaScript + CSS on the web            │
└──────────────────────────────────────────────────────────────┘
```

> **Fig. 7 — FXML Separation of UI and Logic**

---

## 11. Threading Model

### Swing — Event Dispatch Thread (EDT)

```java
// All UI work must be on EDT
SwingUtilities.invokeLater(() -> {
    new MySwingApp().setVisible(true);
});

// Long task — use SwingWorker
SwingWorker<Void, Void> worker = new SwingWorker<>() {
    protected Void doInBackground() { /* heavy work */ return null; }
    protected void done() { label.setText("Done!"); }
};
worker.execute();
```

### JavaFX — JavaFX Application Thread

```java
// All UI work must be on FX Application Thread
Platform.runLater(() -> label.setText("Updated!"));

// Long task — use Task
Task<Void> task = new Task<>() {
    protected Void call() { /* heavy work */ return null; }
};
task.setOnSucceeded(e -> label.setText("Done!"));
new Thread(task).start();
```

| Threading | Swing | JavaFX |
|-----------|-------|--------|
| UI thread name | Event Dispatch Thread (EDT) | JavaFX Application Thread |
| Run on UI thread | `SwingUtilities.invokeLater()` | `Platform.runLater()` |
| Background task | `SwingWorker` | `Task<T>` |
| Check if on UI thread | `SwingUtilities.isEventDispatchThread()` | `Platform.isFxApplicationThread()` |

---

## 12. When to Use Which

```
┌──────────────────────────────────────────────────────────────┐
│              When to Use Swing                               │
│                                                              │
│   ✅ Maintaining or extending existing Swing applications   │
│   ✅ Enterprise legacy systems                              │
│   ✅ Simple desktop tools with basic UI needs               │
│   ✅ Projects requiring JDK-only dependencies               │
│   ✅ Team already experienced with Swing                    │
└──────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│              When to Use JavaFX                             │
│                                                             │
│   ✅ New desktop application projects                       │
│   ✅ Rich UI with CSS styling and animations                │
│   ✅ Applications needing charts, media, or WebView         │
│   ✅ Data-driven UIs with property binding                  │
│   ✅ Separate UI from logic (FXML + Controller)             │
│   ✅ Modern look-and-feel across platforms                  │
└─────────────────────────────────────────────────────────────┘
```

> **Fig. 8 — When to Use Swing vs JavaFX**

---

## 13. Summary

```
┌──────────────────────────────────────────────────────────────┐
│                  Quick Comparison                            │
│                                                              │
│                  SWING              JAVAFX                   │
│   Era            1998               2011                     │
│   Status         Maintenance        Active development       │
│   Rendering      Java 2D            GPU (Prism)              │
│   Styling        Look & Feel        CSS                      │
│   UI definition  Code only          Code or FXML             │
│   Animation      Manual Timer       Built-in API             │
│   Media          None               Audio + Video            │
│   Binding        Manual             Observable Properties    │
│   3D             No                 Yes                      │
│   JDK bundled    Yes                No (OpenJFX)             │
└──────────────────────────────────────────────────────────────┘
```

| Use | Swing | JavaFX |
|-----|-------|--------|
| Legacy app maintenance | ✅ | ⚠️ |
| New desktop app | ⚠️ | ✅ |
| CSS-styled UI | ❌ | ✅ |
| Charts & media | ❌ | ✅ |
| FXML / design separation | ❌ | ✅ |
| Simpler learning curve | ✅ | ⚠️ |
| JDK without extras | ✅ | ❌ |

> **Bottom line:** Use **Swing** to work with existing legacy code. Use **JavaFX** for all new Java desktop application development.