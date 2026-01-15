# Implementation Plan: Prevent Quit Example

## Overview
Port the `prevent-quit` example from [bubbletea/examples/prevent-quit](https://github.com/charmbracelet/bubbletea/tree/master/examples/prevent-quit).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/prevent-quit/`

## Target Directory
`examples/generic/prevent-quit/`

## Prerequisites
- [x] `help` bubble (done)
- [x] `key` bubble (done)
- [ ] `textarea` bubble (TODO)

## Features to Implement
- [ ] Dirty state tracking (unsaved changes)
- [ ] Confirmation dialog on quit attempt
- [ ] Cancel quit functionality
- [ ] Force quit option
- [ ] Visual dirty indicator

## Key Components
1. Textarea for content editing
2. Dirty state flag
3. Confirmation overlay/dialog
4. Key binding for quit with confirmation

## Estimated Effort
Low-Medium - 1 day (after textarea)
