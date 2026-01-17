# Implementation Plan: Debounce Example

## Overview
Port the `debounce` example from [bubbletea/examples/debounce](https://github.com/charmbracelet/bubbletea/tree/master/examples/debounce).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/debounce/`

## Target Directory
`examples/generic/debounce/`

## Prerequisites
- Core command/message system (done)

## Features to Implement
- [x] Debounced command execution
- [x] Timer-based delay before action
- [x] Cancel pending debounced commands
- [x] Visual feedback during debounce

## Key Concepts Demonstrated
1. Command timing patterns
2. Debounce implementation using messages
3. State management for pending actions

## Implementation Approach
```java
// Debounce pattern using tick messages
Command debounce(Duration delay, Supplier<Message> action) {
    return tickAfter(delay, () -> new DebouncedActionMsg(action));
}
```

## Estimated Effort
Low - 1 day
