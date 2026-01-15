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
- [ ] `Stopwatch` class implementing `Model`
- [ ] Elapsed time tracking with `java.time.Duration`
- [ ] Start/stop/reset functionality
- [ ] Running state management

### 2. Configuration Options
- [ ] `interval` - Tick interval (default: 1 second)
- [ ] Custom time format

### 3. Messages
- [ ] `StartStopMsg` - Toggle running state
- [ ] `ResetMsg` - Reset elapsed time to zero
- [ ] `TickMsg` - Internal tick message for time updates

### 4. Commands
- [ ] `start()` - Start the stopwatch
- [ ] `stop()` - Stop the stopwatch
- [ ] `reset()` - Reset to zero
- [ ] `toggle()` - Toggle running state

### 5. View
- [ ] Formatted time display (MM:SS or HH:MM:SS)
- [ ] Configurable format string
- [ ] Styling support via Lipgloss

## Dependencies
- Tick/timer mechanism from core bubbletea
- Lipgloss styling (done)

## Testing
- [ ] Unit tests for time formatting
- [ ] Unit tests for start/stop/reset state transitions
- [ ] Unit tests for elapsed time calculation

## Example
Create `examples/generic/stopwatch/` matching the upstream example.

## Estimated Effort
Low - 1 day
