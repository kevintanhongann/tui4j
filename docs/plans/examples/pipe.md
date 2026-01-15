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
- [ ] Read from stdin pipe
- [ ] Process piped input
- [ ] Handle non-TTY input
- [ ] Detect pipe vs interactive mode
- [ ] Output to stdout

## Key Concepts
1. Stdin detection (TTY vs pipe)
2. Input buffering
3. Non-interactive mode handling
4. Output formatting

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
Low-Medium - 1 day
