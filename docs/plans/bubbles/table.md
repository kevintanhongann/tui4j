# Implementation Plan: Table Bubble

## Overview
Port the `table` bubble from [bubbles/table](https://github.com/charmbracelet/bubbles/tree/master/table) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `table`
- **Key Files**: `table.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.table`

## Prerequisites
- [x] Lipgloss table component must be implemented first (see `lipgloss/table.md`)

## Core Components to Implement

### 1. Table Model
- [x] `Table` class implementing `Model`
- [x] Row data management
- [x] Column definitions with headers
- [x] Selection/cursor support
- [x] Scrolling for large datasets

### 2. Column Definition
- [x] `Column` record/class
- [x] Title
- [x] Width (fixed or flexible)

### 3. Row
- [x] `Row` record/class
- [x] Cell values as `List<String>`

### 4. Configuration Options
- [x] `columns` - List of column definitions
- [x] `rows` - List of row data
- [x] `height` - Visible rows (for scrolling)
- [x] `width` - Total table width
- [x] `focused` - Whether table has focus

### 5. Styling
- [x] Header style
- [x] Cell style
- [x] Selected row style
- [x] Border style (uses Lipgloss borders)

### 6. Key Bindings
- [x] Up/Down - Navigate rows
- [x] Page Up/Down - Scroll pages
- [x] Home/End - Jump to first/last row
- [x] Enter - Select row

### 7. Messages
- [x] Navigation messages
- [x] Selection messages

## Dependencies
- [x] Lipgloss table component
- [x] `key` bubble (done)
- [x] `paginator` bubble (done)

## Testing
- [x] Unit tests for column layout
- [x] Unit tests for row rendering
- [x] Unit tests for scrolling behavior
- [x] Unit tests for selection

## Example
- [x] Create `examples/generic/table/` matching upstream.
- [x] Create `examples/generic/table-resize/` matching upstream.

## Estimated Effort
~~High - 3-5 days (depends on Lipgloss table)~~ **Completed**
