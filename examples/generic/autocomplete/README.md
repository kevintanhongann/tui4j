# Autocomplete Example

This example demonstrates the TextInput bubble's autocomplete functionality, ported from [bubbletea/examples/autocomplete](https://github.com/charmbracelet/bubbletea/tree/master/examples/autocomplete).

## Features

- Text input with autocomplete suggestions
- Suggestion filtering based on input
- Keyboard navigation through suggestions
- Tab completion

## Running the Example

### Prerequisites

- Java 21 or later
- Gradle

### Build and Run

```bash
# Build the examples fat JAR and copy to all example directories
./gradlew --no-configuration-cache copyAllExampleJars

# Run the autocomplete example
./autocomplete/run
```

Or run directly:

```bash
./gradlew --no-configuration-cache build
java -jar build/libs/tui4j-examples.jar com.williamcallahan.tui4j.examples.autocomplete.AutocompleteExample
```

## Controls

- `tab` - Complete the current suggestion
- `ctrl+n` - Next suggestion
- `ctrl+p` - Previous suggestion
- `esc` - Quit
