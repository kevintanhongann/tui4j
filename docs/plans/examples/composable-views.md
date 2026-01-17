# Implementation Plan: Composable Views Example

## Overview
Port the `composable-views` example from [bubbletea/examples/composable-views](https://github.com/charmbracelet/bubbletea/tree/master/examples/composable-views).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/composable-views/`

## Target Directory
`examples/generic/composable-views/`

## Prerequisites
- [x] `timer` bubble (completed)
- [x] `spinner` bubble (completed)

## Features to Implement
- [x] Multiple independent sub-models
- [x] Composed parent model
- [x] Message routing to sub-models
- [x] Combined view rendering

## Key Concepts Demonstrated
1. Model composition pattern
2. Child model initialization
3. Update delegation
4. View composition with Lipgloss joins

## Implementation Details
- **File**: `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/composableviews/ComposableViewsExample.java`
- Uses existing Timer and Spinner bubbles
- Demonstrates focus switching between child models
- Shows horizontal view joining with `HorizontalJoinDecorator`

## Estimated Effort
Low - 1 day (after timer bubble)

## Status
Completed - Implementation ported from Go Bubble Tea example
