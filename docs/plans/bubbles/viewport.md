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
- [ ] `Viewport` class implementing `Model`
- [ ] Content storage (multi-line string)
- [ ] Scroll position (Y offset)
- [ ] Visible window dimensions

### 2. Configuration Options
- [ ] `width` - Viewport width in characters
- [ ] `height` - Viewport height in lines
- [ ] `content` - Full content string
- [ ] `yOffset` - Current scroll position
- [ ] `highPerformanceRendering` - Optimization flag
- [ ] `mouseWheelEnabled` - Enable mouse scroll
- [ ] `mouseWheelDelta` - Lines per scroll

### 3. Scroll Behavior
- [ ] Line-by-line scrolling
- [ ] Page scrolling (viewport height)
- [ ] Half-page scrolling
- [ ] Jump to top/bottom
- [ ] Percentage-based positioning

### 4. Key Bindings
- [ ] Up/Down - Scroll one line
- [ ] Page Up/Down - Scroll one page
- [ ] Home/End - Jump to top/bottom
- [ ] Mouse wheel - Scroll (if enabled)

### 5. Content Management
- [ ] `setContent(String)` - Set viewport content
- [ ] `lineCount()` - Total lines in content
- [ ] `visibleLineCount()` - Lines visible in viewport
- [ ] `atTop()` / `atBottom()` - Position checks
- [ ] `scrollPercent()` - Current scroll position as percentage

### 6. View Rendering
- [ ] Extract visible lines from content
- [ ] Handle content shorter than viewport
- [ ] Proper line truncation/wrapping

### 7. Messages
- [ ] `ViewDown` / `ViewUp` messages
- [ ] `HalfViewDown` / `HalfViewUp` messages
- [ ] `GotoTop` / `GotoBottom` messages

## Dependencies
- `key` bubble (done)
- Lipgloss styling (done)
- ANSI text width utilities (done)

## Testing
- [ ] Unit tests for scroll position clamping
- [ ] Unit tests for content extraction
- [ ] Unit tests for boundary conditions
- [ ] Unit tests for mouse wheel handling

## Examples
- `examples/generic/pager/`
- `examples/generic/chat/` (uses viewport)

## Estimated Effort
Medium - 2-3 days
