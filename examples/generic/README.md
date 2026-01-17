# Examples

### Alt Screen Toggle

The `altscreen-toggle` example shows how to transition between the alternative
screen buffer and the normal screen buffer using TUI4J.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/altscreentoggle/AltScreenToggleExample.java">
  <img width="750" src="../../assets/altscreen-toggle-tape.gif" alt="Animation showing TUI4J alt screen toggle transitioning between normal and alternative screen buffers" />
</a>

### Cursor
The `cursor` example shows how to spawn an artificial cursor in TUI4J application, to be used mostly as a component in more complex scenarios (eg. Input, Text area)

<a href="./src/main/java/com/williamcallahan/tui4j/examples/cursor/CursorExample.java">
<img width="750" src="../../assets/cursor-tape.gif" alt="Animation showing TUI4J cursor blinking and moving in a terminal" />
</a>

### Composable Views

The `composable-views` example demonstrates composing multiple independent models (timer and spinner) into a parent model with focus switching between child models.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/composableviews/ComposableViewsExample.java">
  Code
</a>

### Debounce

The `debounce` example shows how to debounce commands in TUI4J, delaying action execution until input pauses.

<a href="./debounce/src/main/java/com/williamcallahan/tui4j/examples/debounce/DebounceExample.java">
  Code
</a>

### Full Screen

The `fullscreen` example shows how to make a TUI4J application fullscreen.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/fullscreen/FullscreenExample.java">
  <img width="750" src="../../assets/fullscreen-tape.gif" alt="Animation showing TUI4J fullscreen application filling the entire terminal" />
</a>

### Mouse

The `mouse` example shows how to receive mouse events in a TUI4J
application.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/mouse/MouseExample.java">
  Code
</a>

### Demo

The `demo` example shows a choice menu with the ability to select an option.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/demo/Demo.java">
  <img width="750" src="../../assets/demo-tape.gif" alt="Animation showing TUI4J demo with interactive choice menu selection" />
</a>

### Http

The `http` example shows how to make an `http` call within your TUI4J application.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/http/HttpExample.java">
  <img width="750" src="../../assets/http-tape.gif" alt="Animation showing TUI4J application making HTTP requests and displaying responses" />
</a>

### Paginator

The `paginator` example shows how to build a simple paginated list.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/paginator/PaginatorExample.java">
  <img width="750" src="../../assets/paginator-tape.gif" alt="Animation showing TUI4J paginated list with page navigation" />
</a>

### Text Input

The `textinput` example demonstrates a simple TUI4J application using a textinput bubble.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/textinput/TextInputExample.java">
  <img width="750" src="../../assets/textinput-tape.gif" alt="Animation showing TUI4J text input field with typing and cursor" />
</a>

### Multiple Text Inputs

The `textinputs` example shows multiple `textinputs` and being able to switch focus between them as well as changing the cursor mode.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/textinputs/TextInputsExample.java">
  <img width="750" src="../../assets/textinputs-tape.gif" alt="Animation showing TUI4J multiple text inputs with focus switching" />
</a>

### Default List

The `list-default` example shows how to use the list bubble. Have a look [here](../../ListComponent.md) for some more details on how to use `List`.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/listdefault/ListDefaultExample.java">
  <img width="750" src="../../assets/list-default-tape.gif" alt="Animation showing TUI4J default list component with item selection" />
</a>

### Fancy List

The `list-fancy` example shows how to use the list bubble with extra customizations.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/listfancy/ListFancyExample.java">
  <img width="750" src="../../assets/list-fancy-tape.gif" alt="Animation showing TUI4J customized fancy list with styled items" />
</a>

### Simple List

The `list-simple` example shows how to use the list and customize it to have a simpler, more compact, appearance.
This example shows how to implement your own custom **ItemDelegate**.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/listsimple/ListSimpleExample.java">
  <img width="750" src="../../assets/list-simple-tape.gif" alt="Animation showing TUI4J simple compact list with custom item delegate" />
</a>

### Tetris
A fully functional Tetris clone written with TUI4J :)

<a href="./src/main/java/com/williamcallahan/tui4j/examples/tetris/TetrisGame.java">
  <img width="750" src="../../assets/tetris.gif" alt="Animation showing TUI4J Tetris game with falling blocks and line clearing" />
</a>

### Game of Life
 A cellular automaton implementation.

 <a href="./src/main/java/com/williamcallahan/tui4j/examples/conway/ConwayGame.java">
  <img width="750" src="../../assets/conway-tape.gif" alt="Animation showing TUI4J Conway's Game of Life cellular automaton simulation" />
</a>

### Fireworks
Fireworks simulation :) Ported from https://github.com/Wayoung7/firework-rs/

 <a href="./src/main/java/com/williamcallahan/tui4j/examples/fireworks/Fireworks.java">
   <img width="750" src="../../assets/fireworks-tape.gif" alt="Animation showing TUI4J fireworks simulation with colorful particle effects" />
 </a>

### Progress

The `progress` examples demonstrate the Progress bubble with various configurations:

- **progress-static** - Static progress bars with different characters, widths, and formats
- **progress-animated** - Animated progress with spring physics and keyboard controls
- **progress-download** - File download manager simulation
- **package-manager** - Package installation workflow simulation

See [progress-readme.md](./progress-readme.md) for detailed usage instructions.

#### Progress Static

<a href="./src/main/java/com/williamcallahan/tui4j/examples/progress/staticbar/ProgressStaticExample.java">
  <img width="750" src="../../assets/progress-static-tape.gif" alt="Animation showing various static progress bar configurations" />
</a>

#### Progress Animated

<a href="./src/main/java/com/williamcallahan/tui4j/examples/progress/animated/ProgressAnimatedExample.java">
  <img width="750" src="../../assets/progress-animated-tape.gif" alt="Animation showing animated progress bar with spring physics" />
</a>

#### Progress Download

<a href="./src/main/java/com/williamcallahan/tui4j/examples/progress/download/ProgressDownloadExample.java">
  <img width="750" src="../../assets/progress-download-tape.gif" alt="Animation showing file download manager with multiple progress bars" />
</a>

#### Package Manager

<a href="./src/main/java/com/williamcallahan/tui4j/examples/progress/packagemanager/PackageManagerExample.java">
  <img width="750" src="../../assets/package-manager-tape.gif" alt="Animation showing package installation simulation with progress tracking" />
</a>
