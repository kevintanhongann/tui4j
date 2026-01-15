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
- [ ] Lipgloss table component must be implemented first (see `lipgloss/table.md`)

## Core Components to Implement

### 1. Table Model
- [ ] `Table` class implementing `Model`
- [ ] Row data management
- [ ] Column definitions with headers
- [ ] Selection/cursor support
- [ ] Scrolling for large datasets

### 2. Column Definition
- [ ] `Column` record/class
- [ ] Title
- [ ] Width (fixed or flexible)

### 3. Row
- [ ] `Row` record/class
- [ ] Cell values as `List<String>`

### 4. Configuration Options
- [ ] `columns` - List of column definitions
- [ ] `rows` - List of row data
- [ ] `height` - Visible rows (for scrolling)
- [ ] `width` - Total table width
- [ ] `focused` - Whether table has focus

### 5. Styling
- [ ] Header style
- [ ] Cell style
- [ ] Selected row style
- [ ] Border style (uses Lipgloss borders)

### 6. Key Bindings
- [ ] Up/Down - Navigate rows
- [ ] Page Up/Down - Scroll pages
- [ ] Home/End - Jump to first/last row
- [ ] Enter - Select row

### 7. Messages
- [ ] Navigation messages
- [ ] Selection messages

## Dependencies
- Lipgloss table component (TODO)
- `key` bubble (done)
- `paginator` bubble (done)

## Testing
- [ ] Unit tests for column layout
- [ ] Unit tests for row rendering
- [ ] Unit tests for scrolling behavior
- [ ] Unit tests for selection

## Example
Create `examples/generic/table/` and `examples/generic/table-resize/` matching upstream.

## Estimated Effort
High - 3-5 days (depends on Lipgloss table)
