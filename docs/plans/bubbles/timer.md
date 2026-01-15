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
Directory exists at `src/main/java/.../bubbles/timer/` - verify current implementation state.

## Core Components to Implement

### 1. Timer Model
- [ ] `Timer` class implementing `Model`
- [ ] Countdown from specified duration
- [ ] Remaining time tracking with `java.time.Duration`
- [ ] Running/paused state management
- [ ] Timeout detection

### 2. Configuration Options
- [ ] `timeout` - Initial countdown duration
- [ ] `interval` - Tick interval (default: 1 second)

### 3. Messages
- [ ] `StartStopMsg` - Toggle running state
- [ ] `ResetMsg` - Reset to initial timeout
- [ ] `TickMsg` - Internal tick for countdown
- [ ] `TimeoutMsg` - Fired when timer reaches zero

### 4. Commands
- [ ] `start()` - Start countdown
- [ ] `stop()` - Pause countdown
- [ ] `toggle()` - Toggle running state
- [ ] `reset()` - Reset to initial duration
- [ ] `init()` - Initialize and start tick loop

### 5. State
- [ ] `running` - Is timer counting down
- [ ] `timedOut` - Has timer reached zero
- [ ] `remaining` - Time remaining

### 6. View
- [ ] Formatted time display (MM:SS)
- [ ] Customizable format
- [ ] Styling support

## Dependencies
- Core tick mechanism from bubbletea
- Lipgloss styling (done)

## Testing
- [ ] Unit tests for countdown logic
- [ ] Unit tests for timeout detection
- [ ] Unit tests for start/stop/reset state transitions
- [ ] Unit tests for time formatting

## Example
Create `examples/generic/timer/` matching the upstream example.

## Estimated Effort
Low - 1-2 days (similar to stopwatch)
