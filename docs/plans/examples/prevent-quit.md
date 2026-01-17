# Implementation Plan: Prevent Quit Example

## Overview
Port the `prevent-quit` example from [bubbletea/examples/prevent-quit](https://github.com/charmbracelet/bubbletea/tree/master/examples/prevent-quit).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/prevent-quit/`

## Target Directory
`examples/generic/prevent-quit/`

## Prerequisites
- [x] `help` bubble (done)
- [x] `key` bubble (done)
- [x] `textarea` bubble (done - used in chat example)

## Features Implemented
- [x] Dirty state tracking (unsaved changes)
- [x] Confirmation dialog on quit attempt
- [x] Cancel quit functionality
- [x] Force quit option
- [x] Visual dirty indicator

## Key Components
1. Textarea for content editing
2. Dirty state flag
3. Confirmation overlay/dialog
4. Key binding for quit with confirmation

## Files Created
- `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/preventquit/PreventQuitExample.java`
- `examples/generic/prevent-quit/run` (run script)
- `examples/generic/prevent-quit/tui4j-examples.jar` (fat JAR with all examples)

## Estimated Effort
Low-Medium - 1 day (after textarea) - **Completed**

## Status
🟢 Done
