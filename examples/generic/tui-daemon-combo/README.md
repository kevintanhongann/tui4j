# TUI Daemon Combo Example

Port of [bubbletea/examples/tui-daemon-combo](https://github.com/charmbracelet/bubbletea/tree/master/examples/tui-daemon-combo).

Demonstrates running an application in both TUI and daemon modes with shared business logic.

## Features

- **TUI Mode**: Interactive terminal user interface with spinner animation
- **Daemon Mode**: Headless background operation with logging output
- **Shared Logic**: Same model and process simulation for both modes

## Usage

Run in TUI mode (default):
```bash
# From project root
./gradlew examplesJar
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.tuidaemoncombo.TuiDaemonComboExample
```

Run in daemon mode (headless):
```bash
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.tuidaemoncombo.TuiDaemonComboExample --daemon
```

## Controls

- **Any key**: Quit (TUI mode only)

## Architecture

The example shows how to structure an application that can run in two modes:

1. **TUI Mode**: Uses Bubble Tea's Program to render an interactive interface
2. **Daemon Mode**: Runs the same business logic without the TUI, outputting to stdout

The `main` method checks for the `--daemon` flag and routes to the appropriate mode.
