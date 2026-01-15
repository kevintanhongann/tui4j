# Implementation Plan: Lipgloss Table Component

## Overview
Port the `table` component from [lipgloss/table](https://github.com/charmbracelet/lipgloss/tree/master/table) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/lipgloss
- **Package**: `table`
- **Key Files**: `table.go`, `rows.go`, `util.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.lipgloss.table`

## Core Components to Implement

### 1. Table Builder
- [ ] `Table` class with fluent builder API
- [ ] `headers(String...)` - Set column headers
- [ ] `rows(List<List<String>>)` - Set row data
- [ ] `row(String...)` - Add single row

### 2. Column Configuration
- [ ] Fixed width columns
- [ ] Flexible/auto width columns
- [ ] Minimum/maximum column widths
- [ ] Column alignment (left, center, right)

### 3. Borders
- [ ] `border(Border)` - Set border style
- [ ] `borderTop()`, `borderBottom()`, `borderLeft()`, `borderRight()`
- [ ] `borderRow()` - Horizontal dividers between rows
- [ ] `borderColumn()` - Vertical dividers between columns
- [ ] `borderHeader()` - Divider after header row

### 4. Styling
- [ ] `styleFunc(BiFunction<row, col, Style>)` - Per-cell styling
- [ ] `headerStyle(Style)` - Style for header row
- [ ] `rowStyle(Function<row, Style>)` - Per-row styling
- [ ] Alternating row colors support

### 5. Layout
- [ ] Column width calculation
- [ ] Cell padding
- [ ] Text wrapping within cells
- [ ] Multi-line cell support

### 6. Rendering
- [ ] `render()` - Produce final string output
- [ ] Proper border character joining
- [ ] ANSI color support in cells

## Dependencies
- Lipgloss `Style` (done)
- Lipgloss `Border` (done)
- Lipgloss alignment (done)
- Text width utilities (done)

## Testing
- [ ] Unit tests for basic table rendering
- [ ] Unit tests for column width calculation
- [ ] Unit tests for border rendering
- [ ] Unit tests for cell styling
- [ ] Unit tests for multi-line cells
- [ ] Visual comparison tests with upstream

## Example Usage
```java
String table = Table.create()
    .headers("Name", "Age", "City")
    .row("Alice", "30", "NYC")
    .row("Bob", "25", "LA")
    .border(Border.ROUNDED)
    .headerStyle(Style.create().bold(true))
    .render();
```

## Estimated Effort
High - 3-4 days (complex layout logic)
