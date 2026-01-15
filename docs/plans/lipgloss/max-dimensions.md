# Implementation Plan: Max Width and Max Height

## Overview
Implement `maxWidth` and `maxHeight` style properties in Lipgloss port.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/lipgloss
- **Key Files**: `style.go`, `get.go`, `set.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.lipgloss`

## Core Components to Implement

### 1. Style Properties
- [ ] Add `maxWidth` field to `Style` class
- [ ] Add `maxHeight` field to `Style` class
- [ ] Builder methods: `maxWidth(int)`, `maxHeight(int)`
- [ ] Getter methods: `getMaxWidth()`, `getMaxHeight()`
- [ ] Unset methods: `unsetMaxWidth()`, `unsetMaxHeight()`

### 2. Rendering Logic
- [ ] Truncate rendered content to `maxWidth` characters per line
- [ ] Truncate rendered content to `maxHeight` lines
- [ ] Handle interaction with `width` (fixed width) property
- [ ] Handle interaction with `height` (fixed height) property
- [ ] Proper handling of ANSI escape sequences during truncation

### 3. Text Truncation
- [ ] Width truncation preserving ANSI codes
- [ ] Height truncation (line count limit)
- [ ] Optional ellipsis support for truncated text
- [ ] Unicode-aware truncation (grapheme clusters)

### 4. Edge Cases
- [ ] MaxWidth smaller than content + padding + border
- [ ] MaxHeight smaller than content + padding + border
- [ ] MaxWidth/MaxHeight of 0 (unlimited)
- [ ] Interaction with text wrapping

## Dependencies
- Existing `Style` class (done)
- ANSI text width utilities (done)
- Text truncation utilities from `ansi` package

## Testing
- [ ] Unit tests for maxWidth truncation
- [ ] Unit tests for maxHeight truncation
- [ ] Unit tests for ANSI-safe truncation
- [ ] Unit tests for interaction with padding/borders
- [ ] Unit tests for zero values (unlimited)

## Estimated Effort
Low-Medium - 1-2 days
