# Implementation Plan: Table Resize Example

## Overview
Port the `table-resize` example from [bubbletea/examples/table-resize](https://github.com/charmbracelet/bubbletea/tree/master/examples/table-resize).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/table-resize/`

## Target Directory
`examples/generic/table-resize/`

## Prerequisites
- [x] `table` bubble (TODO)
- [x] Lipgloss table component (TODO)

## Features to Implement
- [x] Responsive table sizing
- [x] Window resize handling
- [x] Dynamic column width adjustment
- [x] Proper reflow on resize

## Key Components
1. Table bubble with dynamic sizing
2. WindowSizeMsg handling
3. Column width recalculation
4. Smooth resize experience

## Estimated Effort
Low-Medium - 0.5-1 day (after table bubble)

## Implementation Status
- [x] Create source file: `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/tableresize/TableResizeExample.java`
- [x] Create run script: `examples/generic/table-resize/run`
- [x] Build and verify compilation
- [x] Run tests to verify no regressions
