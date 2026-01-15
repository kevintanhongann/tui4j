# Implementation Plan: Suspend Example

## Overview
Port the `suspend` example from [bubbletea/examples/suspend](https://github.com/charmbracelet/bubbletea/tree/master/examples/suspend).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/suspend/`

## Target Directory
`examples/generic/suspend/`

## Prerequisites
- Core Program suspend functionality

## Features to Implement
- [ ] Ctrl+Z suspend handling
- [ ] Background process (SIGTSTP equivalent)
- [ ] Resume from suspension
- [ ] Terminal state restoration

## Platform Notes
- Unix: SIGTSTP/SIGCONT signals
- Windows: May not be fully supported
- JVM signal handling limitations

## Java Implementation
```java
// Register signal handler
Signal.handle(new Signal("TSTP"), signal -> {
    program.suspend();
});

Signal.handle(new Signal("CONT"), signal -> {
    program.resume();
});
```

## Estimated Effort
Medium - 1-2 days (platform-specific)
