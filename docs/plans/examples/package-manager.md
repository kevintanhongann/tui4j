# Implementation Plan: Package Manager Example

## Overview
Port the `package-manager` example from [bubbletea/examples/package-manager](https://github.com/charmbracelet/bubbletea/tree/master/examples/package-manager).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/package-manager/`

## Target Directory
`examples/generic/package-manager/`

## Prerequisites
- [x] `progress` bubble (completed)

## Features to Implement
- [x] Multiple concurrent progress bars
- [x] Simulated package downloads
- [x] Individual package states (waiting, downloading, complete)
- [x] Overall progress tracking
- [x] Styled package list

## Key Components
1. [x] Multiple progress bubble instances
2. [x] Async download simulation
3. [x] State machine per package
4. [x] Combined view layout

## Estimated Effort
Medium - 1-2 days (after progress bubble)

## Status
✅ **COMPLETED** - Implementation exists at `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/progress/packagemanager/PackageManagerExample.java`

### To Run
```bash
./examples/generic/package-manager/run
```

### Features
- Overall progress bar with weighted completion
- Per-task expandable progress bars
- Spinner animation during active operations
- Keyboard controls (j/k or up/down to navigate, space to expand/collapse)
- Checkmarks for completed operations
