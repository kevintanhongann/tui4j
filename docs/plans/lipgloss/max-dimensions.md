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
- [x] Add `maxWidth` field to `Style` class
- [x] Add `maxHeight` field to `Style` class
- [x] Builder methods: `maxWidth(int)`, `maxHeight(int)`
- [x] Getter methods: `getMaxWidth()`, `getMaxHeight()`
- [x] Unset methods: `unsetMaxWidth()`, `unsetMaxHeight()`

### 2. Rendering Logic
- [x] Truncate rendered content to `maxWidth` characters per line
- [x] Truncate rendered content to `maxHeight` lines
- [x] Handle interaction with `width` (fixed width) property
- [x] Handle interaction with `height` (fixed height) property
- [x] Proper handling of ANSI escape sequences during truncation

### 3. Text Truncation
- [x] Width truncation preserving ANSI codes (uses existing `Truncate.truncate()`)
- [x] Height truncation (line count limit)
- [x] Optional ellipsis support for truncated text (configurable via `ellipsis()` method)
- [x] Unicode-aware truncation (grapheme clusters via existing utilities)

### 4. Edge Cases
- [x] MaxWidth smaller than content + padding + border (truncation applied after borders/margins)
- [x] MaxHeight smaller than content + padding + border (truncation applied after borders/margins)
- [x] MaxWidth/MaxHeight of 0 (unlimited)
- [x] Interaction with text wrapping (maxWidth applied after wrapping)

## Dependencies
- Existing `Style` class (done)
- ANSI text width utilities (done)
- Text truncation utilities from `ansi` package (done)

## Testing
- [x] Unit tests for maxWidth truncation
- [x] Unit tests for maxHeight truncation
- [x] Unit tests for ANSI-safe truncation
- [x] Unit tests for interaction with padding/borders
- [x] Unit tests for zero values (unlimited)
- [x] Unit tests for ellipsis support

## Estimated Effort
Low-Medium - 1-2 days
