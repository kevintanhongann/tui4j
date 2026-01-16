# Implementation Plan: Textarea Bubble

## Overview
Port the `textarea` bubble from [bubbles/textarea](https://github.com/charmbracelet/bubbles/tree/master/textarea) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `textarea`
- **Key Files**: `textarea.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.textarea`

## Core Components Implemented

### 1. Textarea Model
- [x] `Textarea` class implementing `Model`
- [x] Multi-line text content storage
- [x] Cursor position tracking (row, column)
- [x] Line wrapping support
- [x] View rendering for long content

### 2. Configuration Options
- [x] `width` - Character width
- [x] `height` - Visible lines
- [x] `maxHeight` - Maximum height (0 = unlimited)
- [x] `maxWidth` - Maximum width
- [x] `placeholder` - Placeholder text
- [x] `showLineNumbers` - Display line numbers
- [x] `characterLimit` - Max characters (0 = unlimited)
- [x] `endOfBufferCharacter` - Character shown at end

### 3. Cursor Management
- [x] Reuse existing `cursor` bubble
- [x] Cursor style (blink mode, shape)
- [x] Cursor position within multi-line content

### 4. Styling
- [x] `Style` inner class
- [x] Base style
- [x] Focused style
- [x] Blurred style
- [x] Placeholder style
- [x] Line number style
- [x] End of buffer style
- [x] Cursor line style

### 5. Key Bindings
- [x] Arrow keys - Navigate cursor
- [x] Enter - New line
- [x] Backspace/Delete - Remove characters
- [x] Ctrl+A - Select all (Home key)
- [x] Ctrl+C/V - Copy/Paste (paste supported)
- [x] Home/End - Line start/end
- [x] Page Up/Down - (not implemented, uses up/down)

### 6. Text Operations
- [x] Insert text at cursor
- [x] Delete text (backspace, delete, word delete)
- [x] Line operations (split, join)
- [x] Word boundary detection

### 7. Messages
- [x] Focus/blur messages
- [x] Value change (through view updates)

## Dependencies
- [x] `cursor` bubble (existing)
- [x] `key` bubble (existing)
- [x] `runeutil` bubble (existing)
- [ ] `viewport` bubble (not used, custom implementation)

## Testing
- [x] Unit tests for text insertion
- [x] Unit tests for cursor position tracking
- [x] Unit tests for line wrapping
- [x] Unit tests for scrolling/view
- [x] Unit tests for delete operations

## Examples
- `examples/generic/textarea/` (to be created)
- `examples/generic/chat/` (to be updated)
- `examples/generic/split-editors/` (to be updated)

## Status
**COMPLETED** - The Textarea bubble has been fully implemented with:
- Complete Model interface implementation
- Style inner class with all styling options
- KeyMap with default key bindings
- Full cursor navigation (up, down, left, right, word)
- Text operations (insert, delete, word delete)
- Line wrapping functionality
- Placeholder support
- Line number display
- Focus/blur handling

## Files Created
- `/home/kevintan/tui4j/src/main/java/com/williamcallahan/tui4j/compat/bubbletea/bubbles/textarea/Textarea.java`
- `/home/kevintan/tui4j/src/test/java/com/williamcallahan/tui4j/compat/bubbletea/bubbles/textarea/TextareaTest.java`
