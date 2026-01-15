# Implementation Plan: Filepicker Bubble

## Overview
Port the `filepicker` bubble from [bubbles/filepicker](https://github.com/charmbracelet/bubbles/tree/master/filepicker) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `filepicker`
- **Key Files**: `filepicker.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.filepicker`

## Core Components to Implement

### 1. FilePicker Model
- [x] `FilePicker` class implementing `Model`
- [x] File/directory listing with `java.nio.file` APIs
- [x] Navigation (up/down, enter directory, go back)
- [x] File filtering by extension or predicate
- [x] Hidden file toggle
- [x] Selection state management

### 2. Configuration Options
- [x] `allowedTypes` - List of allowed file extensions
- [x] `showHidden` - Toggle hidden files visibility
- [x] `dirAllowed` - Allow directory selection
- [x] `fileAllowed` - Allow file selection
- [x] `currentDirectory` - Starting directory
- [x] `height` - Visible rows

### 3. Styling
- [x] `Style` inner class for customization
- [x] Directory style
- [x] File style
- [x] Selected item style
- [x] Cursor style
- [x] Permission denied style

### 4. Messages
- [x] `DidSelectFileMsg` - File was selected
- [x] `DidSelectDirectoryMsg` - Directory was selected (if allowed)

### 5. Key Bindings
- [x] Up/Down - Navigate list
- [x] Enter - Select file or enter directory
- [x] Backspace/Left - Go to parent directory
- [x] `.` - Toggle hidden files
- [x] Escape - Cancel selection

## Dependencies
- [x] `key` bubble (done)
- [x] `list` bubble (done) - may reuse for display
- [x] `paginator` bubble (done)

## Testing
- [x] Unit tests for file listing
- [x] Unit tests for filtering
- [x] Unit tests for navigation
- [ ] Integration test with mock file system

## Example
Create `examples/generic/file-picker/` matching the upstream example.

## Estimated Effort
Completed - 2-3 days
