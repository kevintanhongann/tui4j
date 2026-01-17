# Implementation Plan: Timer Bubble

## Overview
Port the `timer` bubble from [bubbles/timer](https://github.com/charmbracelet/bubbles/tree/master/timer) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `timer`
- **Key Files**: `timer.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.timer`

## Current Status
**COMPLETED** - All components implemented and tests passing.

## Core Components to Implement

### 1. Timer Model
- [x] `Timer` class implementing `Model`
- [x] Countdown from specified duration
- [x] Remaining time tracking with `java.time.Duration`
- [x] Running/paused state management
- [x] Timeout detection

### 2. Configuration Options
- [x] `timeout` - Initial countdown duration
- [x] `interval` - Tick interval (default: 1 second)

### 3. Messages
- [x] `StartStopMsg` - Toggle running state
- [x] `TickMsg` - Internal tick for countdown
- [x] `TimeoutMsg` - Fired when timer reaches zero

### 4. Commands
- [x] `start()` - Start countdown
- [x] `stop()` - Pause countdown
- [x] `toggle()` - Toggle running state
- [x] `reset()` - Reset to initial duration (via `setTimeout()`)
- [x] `init()` - Initialize and start tick loop

### 5. State
- [x] `running` - Is timer counting down
- [x] `timedOut` - Has timer reached zero
- [x] `remaining` - Time remaining

### 6. View
- [x] Formatted time display (Duration format: 1m5s, 1h2m3s, etc.)
- [x] Customizable format
- [x] Styling support

## Dependencies
- Core tick mechanism from bubbletea (done)
- Lipgloss styling (done)

## Testing
- [x] Unit tests for countdown logic
- [x] Unit tests for timeout detection
- [x] Unit tests for start/stop/reset state transitions
- [x] Unit tests for time formatting

## Example
- [x] Create `examples/generic/timer/` matching the upstream example.

## Actual Effort
- Completed in existing codebase
