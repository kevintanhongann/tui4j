# Implementation Plan: Autocomplete Example

## Overview
Port the `autocomplete` example from [bubbletea/examples/autocomplete](https://github.com/charmbracelet/bubbletea/tree/master/examples/autocomplete).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/autocomplete/`

## Target Directory
`examples/generic/autocomplete/`

## Prerequisites
- [ ] `help` bubble (done)
- [ ] `key` bubble (done)
- [ ] `textinput` bubble (done)

## Features to Implement
- [ ] Text input with autocomplete suggestions
- [ ] Suggestion filtering based on input
- [ ] Keyboard navigation through suggestions
- [ ] Selection and completion
- [ ] Help text display

## Key Components
1. Model with textinput and suggestion list
2. Suggestion matching algorithm
3. Dropdown-style suggestion display
4. Tab/Enter completion

## Estimated Effort
Low - 1 day (dependencies ready)
