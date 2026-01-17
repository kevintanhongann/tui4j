# Implementation Plan: Pipe Example

## Overview
Port the `pipe` example from [bubbletea/examples/pipe](https://github.com/charmbracelet/bubbletea/tree/master/examples/pipe).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/pipe/`

## Target Directory
`examples/generic/pipe/`

## Prerequisites
- Core Program stdin/stdout handling

## Features to Implement
- [x] Read from stdin pipe
- [x] Process piped input
- [x] Handle non-TTY input
- [x] Detect pipe vs interactive mode
- [x] Output to stdout

## Key Concepts
1. [x] Stdin detection (TTY vs pipe)
2. [x] Input buffering
3. [x] Non-interactive mode handling
4. [x] Output formatting

## Java Implementation Notes
```java
// Check if stdin is a TTY
boolean isTTY = System.console() != null;

// Read piped input
if (!isTTY) {
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(System.in));
    // Process input...
}
```

## Estimated Effort
Low-Medium - 1 day ✅ Completed

## Implementation
- **Location**: `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/pipe/PipeExample.java`
- **Main class**: `com.williamcallahan.tui4j.examples.pipe.PipeExample`

## Usage
```bash
echo 'Hello, World!' | java -cp tui4j-examples.jar com.williamcallahan.tui4j.examples.pipe.PipeExample
```
