# Implementation Plan: Pager Example

## Overview
Port the `pager` example from [bubbletea/examples/pager](https://github.com/charmbracelet/bubbletea/tree/master/examples/pager).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/pager/`

## Target Directory
`examples/generic/pager/`

## Prerequisites
- [x] `viewport` bubble (DONE - already implemented with tests)

## Features to Implement
- [x] Scrollable content display
- [x] Line/page navigation
- [x] Scroll position indicator
- [x] Search functionality (omitted - not in upstream)
- [x] Mouse wheel support

## Key Components
1. Viewport bubble integration
2. Content loading (from file or default content)
3. Status bar with position indicator
4. Keyboard navigation (pgup, pgdown, arrows)
5. Mouse wheel support

## Implementation Notes
- Ported from `bubbletea/examples/pager/main.go`
- Added default content for standalone execution
- Supports loading from `pager.md` file in current or examples/generic/pager directory
- Uses alt screen and mouse cell motion options
- Includes header with title and footer with scroll percentage

## Status
✅ **COMPLETED** - Pager example implemented and tested

## Files Created
- `examples/generic/pager/src/main/java/com/williamcallahan/tui4j/examples/pager/PagerExample.java`
- `examples/generic/pager/pager.md` (sample content file)

## Estimated Effort
Low - 0.5 days (after viewport bubble) - **Actual: ~1 hour**
