# Implementation Plan: File Picker Example

## Overview
Port the `file-picker` example from [bubbletea/examples/file-picker](https://github.com/charmbracelet/bubbletea/tree/master/examples/file-picker).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/file-picker/`

## Target Directory
`examples/generic/file-picker/`

## Prerequisites
- [x] `filepicker` bubble (exists)

## Features to Implement
- [x] Directory browsing
- [x] File selection
- [x] Navigation (enter directories, go back)
- [x] File type filtering
- [x] Selected file display

## Key Components
1. [x] Filepicker bubble integration
2. [x] Selected file state management
3. [x] Styled file list display
4. [x] Keyboard shortcuts help

## Estimated Effort
Low - 0.5 days (after filepicker bubble)

## Files Created
- `examples/generic/file-picker/src/main/java/com/williamcallahan/tui4j/examples/filepicker/FilePickerExample.java`
- `examples/generic/file-picker/README.md`
- `examples/generic/file-picker/run`

## Build Configuration
- Updated `build.gradle.kts` to include file-picker in copyAllExampleJars task
