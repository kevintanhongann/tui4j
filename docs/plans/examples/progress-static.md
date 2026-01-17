# Implementation Plan: Progress Static Example

## Overview
Port the `progress-static` example from [bubbletea/examples/progress-static](https://github.com/charmbracelet/bubbletea/tree/master/examples/progress-static).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/progress-static/`

## Target Directory
`examples/generic/progress-static/`

## Prerequisites
- [x] `progress` bubble (exists at `src/main/java/com/williamcallahan/tui4j/compat/bubbletea/bubbles/progress/Progress.java`)

## Features Implemented
- [x] Non-animated progress bar (uses `ViewAs()` for pure rendering)
- [x] Instant progress updates (no spring animation)
- [] Key-triggered progress changes (quits on any key)
- [x] Simple styling (withDefaultGradient)

## Key Components
1. Progress bubble in static mode using `ViewAs(percent)` method
2. Direct percentage setting maintained in model
3. Tick-based automatic progress increment (25% per second)
4. Window size responsiveness

## Implementation Notes
- Created new example at `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/progress/staticview/ProgressStaticExample.java`
- Uses `ViewAs()` method for pure/progress-bar-agnostic rendering (matches upstream)
- Auto-increments by 25% every second
- Quits on any key press
- Existing `progress/staticbar/ProgressStaticExample.java` kept as separate example showing multiple styled bars

## Estimated Effort
Completed - Low
