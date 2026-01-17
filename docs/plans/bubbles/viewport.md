# Implementation Plan: Viewport Bubble

## Overview
Port the `viewport` bubble from [bubbles/viewport](https://github.com/charmbracelet/bubbles/tree/master/viewport) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `viewport`
- **Key Files**: `viewport.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.viewport`

## Core Components to Implement

### 1. Viewport Model
- [x] `Viewport` class implementing `Model`
- [x] Content storage (multi-line string)
- [x] Scroll position (Y offset)
- [x] Visible window dimensions

### 2. Configuration Options
- [x] `width` - Viewport width in characters
- [x] `height` - Viewport height in lines
- [x] `content` - Full content string
- [x] `yOffset` - Current scroll position
- [x] `highPerformanceRendering` - Optimization flag
- [x] `mouseWheelEnabled` - Enable mouse scroll
- [x] `mouseWheelDelta` - Lines per scroll

### 3. Scroll Behavior
- [x] Line-by-line scrolling
- [x] Page scrolling (viewport height)
- [x] Half-page scrolling
- [x] Jump to top/bottom
- [x] Percentage-based positioning

### 4. Key Bindings
- [x] Up/Down - Scroll one line
- [x] Page Up/Down - Scroll one page
- [x] Home/End - Jump to top/bottom
- [x] Mouse wheel - Scroll (if enabled)

### 5. Content Management
- [x] `setContent(String)` - Set viewport content
- [x] `lineCount()` - Total lines in content
- [x] `visibleLineCount()` - Lines visible in viewport
- [x] `atTop()` / `atBottom()` - Position checks
- [x] `scrollPercent()` - Current scroll position as percentage

### 6. View Rendering
- [x] Extract visible lines from content
- [x] Handle content shorter than viewport
- [x] Proper line truncation/wrapping

### 7. Messages
- [x] `ViewDown` / `ViewUp` messages
- [x] `HalfViewDown` / `HalfViewUp` messages
- [x] `GotoTop` / `GotoBottom` messages

## Dependencies
- `key` bubble (done)
- Lipgloss styling (done)
- ANSI text width utilities (done)

## Testing
- [x] Unit tests for scroll position clamping
- [x] Unit tests for content extraction
- [x] Unit tests for boundary conditions
- [x] Unit tests for mouse wheel handling

## Examples
- `examples/generic/pager/`
- `examples/generic/chat/` (uses viewport)

## Estimated Effort
Medium - 2-3 days

## Status
✅ IMPLEMENTATION COMPLETE
