# Implementation Plan: Stopwatch Example

## Overview
Port the `stopwatch` example from [bubbletea/examples/stopwatch](https://github.com/charmbracelet/bubbletea/tree/master/examples/stopwatch).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/stopwatch/`

## Target Directory
`examples/generic/stopwatch/`

## Prerequisites
- [x] `stopwatch` bubble (done)
- [x] `help` bubble (done)
- [x] `key` bubble (done)

## Features to Implement
- [x] Running time display
- [x] Start/stop/reset controls
- [x] Keyboard shortcuts
- [x] Help bar with key bindings

## Key Components
1. Stopwatch bubble integration
2. Key bindings for controls
3. Help bubble for shortcuts
4. Styled time display

## Estimated Effort
Low - 0.5 days (after stopwatch bubble)

## Implementation Status
COMPLETED

## Implementation Details
- **Stopwatch bubble**: `src/main/java/com/williamcallahan/tui4j/compat/bubbletea/bubbles/stopwatch/Stopwatch.java`
- **Example**: `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/stopwatch/StopwatchExample.java`
- **Tests**: 24 tests passing (StopwatchTest.java, StopwatchFormatTest.java)
