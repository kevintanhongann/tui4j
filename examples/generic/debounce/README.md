# Debounce Example

This example demonstrates how to debounce commands in TUI4J, ported from [bubbletea/examples/debounce](https://github.com/charmbracelet/bubbletea/tree/master/examples/debounce).

## Features

- Debounced command execution
- Timer-based delay before action
- Cancel pending debounced commands (by pressing another key)

## Running the Example

### Prerequisites

- Java 21 or later
- Gradle

### Build and Run

```bash
# From project root
./gradlew examplesJar
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.debounce.DebounceExample
```

## How It Works

1. Each key press increments a `tag` value on the model
2. A tick command is scheduled to fire after 1 second, carrying the current tag value
3. If another key is pressed before the tick fires, the tag increments again and a new tick is scheduled
4. When the tick fires, if the tag matches the current model tag, the action is executed (quit); otherwise it's ignored

## Controls

- Press any key to increment the counter
- Wait 1 second without pressing anything to confirm and quit
