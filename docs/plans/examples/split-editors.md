# Implementation Plan: Split Editors Example

## Overview
Port the `split-editors` example from [bubbletea/examples/split-editors](https://github.com/charmbracelet/bubbletea/tree/master/examples/split-editors).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/split-editors/`

## Target Directory
`examples/generic/src/main/java/com/williamcallahan/tui4j/examples/split/SplitEditorsExample.java`

## Prerequisites
- [x] `textarea` bubble

## Features Implemented
- [x] Side-by-side text editors
- [x] Focus switching between editors (tab/shift+tab)
- [x] Independent content in each editor
- [x] Visual focus indicator (rounded border for focused, hidden for blurred)
- [x] Responsive layout (width divided among editors)
- [x] Add/remove editors (ctrl+n/ctrl+w)
- [x] Help bar at bottom

## Key Components
1. Two textarea instances (configurable 1-6)
2. Focus state management with visual indicators
3. Horizontal layout with Lipgloss
4. Tab/shift+tab to switch focus
5. ctrl+n to add editor
6. ctrl+w to remove editor
7. esc to quit

## Implementation Notes
- Uses `Textarea.Style` builder pattern for styling
- Uses `StandardBorder.RoundedBorder` and `StandardBorder.HiddenBorder` for borders
- Editor dimensions calculated based on terminal size
- Help bar shows available keybindings with enabled/disabled state

## Status
✅ **COMPLETED** - Implemented and tested

## Estimated Effort
Low-Medium - 1 day (after textarea) - **Actual**: ~2 hours
