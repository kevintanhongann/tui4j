# Implementation Plan: Views Example

## Overview
Port the `views` example from [bubbletea/examples/views](https://github.com/charmbracelet/bubbletea/tree/master/examples/views).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/views/`

## Target Directory
`examples/generic/src/main/java/com/williamcallahan/tui4j/examples/views/`

## Prerequisites
- Core Model/Program (done)
- Lipgloss styling (done)

## Features Implemented
- [x] Multiple view states (screens) - CHOOSING and CHOSEN states
- [x] View transitions - navigate between menu and detail view on Enter
- [x] State machine for navigation - enum-based state transitions
- [x] Different layouts per view - separate rendering methods for each view

## Key Concepts
1. View state enum (ViewState: CHOOSING, CHOSEN)
2. Switch statement in view() delegates to appropriate view renderer
3. Navigation messages (TickMessage, FrameMessage for animations)
4. Shared model state across views (choice, progress, ticks)

## Implementation Pattern
```java
enum ViewState { CHOOSING, CHOSEN }

public String view() {
    return switch (viewState) {
        case CHOOSING -> choicesView();
        case CHOSEN -> chosenView();
    };
}
```

## Implementation Details
- **File**: `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/views/ViewsExample.java`
- **Classes**:
  - `ViewsExample` - Main model implementing the multi-view pattern
  - `ViewState` enum - CHOOSING, CHOSED
  - `Choice` enum - PLANT_CARROTS, GO_TO_MARKET, READ_SOMETHING, SEE_FRIENDS
  - `TickMessage` - Timer tick message
  - `FrameMessage` - Animation frame message
- **Styling**: Lipgloss with gradient progress bar (b14ff to 00ffa3)
- **Navigation**: j/k or arrow keys to select, Enter to choose, q/esc to quit

## Estimated Effort
Completed in ~2 hours

## Build Status
- Compilation: Success
- Tests: Pass
