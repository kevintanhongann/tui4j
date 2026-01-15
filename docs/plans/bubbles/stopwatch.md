# Implementation Plan: Stopwatch Bubble

## Overview
Port the `stopwatch` bubble from [bubbles/stopwatch](https://github.com/charmbracelet/bubbles/tree/master/stopwatch) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `stopwatch`
- **Key Files**: `stopwatch.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.stopwatch`

## Core Components to Implement

### 1. Stopwatch Model
- [x] `Stopwatch` class implementing `Model`
- [x] Elapsed time tracking with `java.time.Duration`
- [x] Start/stop/reset functionality
- [x] Running state management

### 2. Configuration Options
- [x] `interval` - Tick interval (default: 1 second)
- [x] Custom time format

### 3. Messages
- [x] `StartStopMsg` - Toggle running state
- [x] `ResetMsg` - Reset elapsed time to zero
- [x] `TickMsg` - Internal tick message for time updates

### 4. Commands
- [x] `start()` - Start the stopwatch
- [x] `stop()` - Stop the stopwatch
- [x] `reset()` - Reset to zero
- [x] `toggle()` - Toggle running state

### 5. View
- [x] Formatted time display (MM:SS or HH:MM:SS)
- [x] Configurable format string
- [x] Styling support via Lipgloss

## Dependencies
- Tick/timer mechanism from core bubbletea
- Lipgloss styling (done)

## Testing
- [x] Unit tests for time formatting
- [x] Unit tests for start/stop/reset state transitions
- [x] Unit tests for elapsed time calculation

## Example
- [x] Create `examples/generic/stopwatch/` matching the upstream example.

## Estimated Effort
Low - 1 day

## Status
**Completed** - All components implemented and tested (424 tests passing).
