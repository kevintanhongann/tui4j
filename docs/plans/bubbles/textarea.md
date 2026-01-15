# Implementation Plan: Textarea Bubble

## Overview
Port the `textarea` bubble from [bubbles/textarea](https://github.com/charmbracelet/bubbles/tree/master/textarea) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `textarea`
- **Key Files**: `textarea.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.textarea`

## Core Components to Implement

### 1. Textarea Model
- [ ] `Textarea` class implementing `Model`
- [ ] Multi-line text content storage
- [ ] Cursor position tracking (row, column)
- [ ] Line wrapping support
- [ ] Viewport/scrolling for long content

### 2. Configuration Options
- [ ] `width` - Character width
- [ ] `height` - Visible lines
- [ ] `maxHeight` - Maximum height (0 = unlimited)
- [ ] `maxWidth` - Maximum width
- [ ] `placeholder` - Placeholder text
- [ ] `showLineNumbers` - Display line numbers
- [ ] `characterLimit` - Max characters (0 = unlimited)
- [ ] `endOfBufferCharacter` - Character shown at end

### 3. Cursor Management
- [ ] Reuse existing `cursor` bubble
- [ ] Cursor style (blink mode, shape)
- [ ] Cursor position within multi-line content

### 4. Styling
- [ ] `Style` inner class
- [ ] Base style
- [ ] Focused style
- [ ] Placeholder style
- [ ] Line number style
- [ ] End of buffer style
- [ ] Cursor line style

### 5. Key Bindings
- [ ] Arrow keys - Navigate cursor
- [ ] Enter - New line
- [ ] Backspace/Delete - Remove characters
- [ ] Ctrl+A - Select all
- [ ] Ctrl+C/V - Copy/Paste (if supported)
- [ ] Home/End - Line start/end
- [ ] Page Up/Down - Scroll viewport

### 6. Text Operations
- [ ] Insert text at cursor
- [ ] Delete text (backspace, delete, word delete)
- [ ] Line operations (split, join)
- [ ] Word boundary detection

### 7. Messages
- [ ] Focus/blur messages
- [ ] Value change messages

## Dependencies
- `cursor` bubble (done)
- `key` bubble (done)
- `runeutil` bubble (done)
- `viewport` bubble (TODO - may need to implement first)

## Testing
- [ ] Unit tests for text insertion
- [ ] Unit tests for cursor navigation
- [ ] Unit tests for line wrapping
- [ ] Unit tests for scrolling
- [ ] Unit tests for delete operations

## Examples
- `examples/generic/textarea/`
- `examples/generic/chat/` (uses textarea)
- `examples/generic/split-editors/` (uses textarea)

## Estimated Effort
High - 4-6 days (complex multi-line editing logic)
