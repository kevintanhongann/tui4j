# Suspend Example

This example demonstrates how to handle Ctrl+Z to suspend the TUI application.

## Usage

```bash
# From project root
./gradlew examplesJar
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.suspend.SuspendExample
```

## Controls

- `ctrl+z` - Suspend the application (put it in the background)
- `ctrl+c` - Interrupt and exit
- `q` or `esc` - Quit

## Resume

After suspending with `ctrl+z`, you can resume the application by running `fg` in your shell.

## Platform Notes

- **Unix/Linux/macOS**: Full support for SIGTSTP/SIGCONT signals
- **Windows**: Limited or no support for suspend functionality

## Reference

Original Bubble Tea example: https://github.com/charmbracelet/bubbletea/tree/master/examples/suspend
