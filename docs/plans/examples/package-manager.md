# Implementation Plan: Package Manager Example

## Overview
Port the `package-manager` example from [bubbletea/examples/package-manager](https://github.com/charmbracelet/bubbletea/tree/master/examples/package-manager).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/package-manager/`

## Target Directory
`examples/generic/package-manager/`

## Prerequisites
- [ ] `progress` bubble (TODO)

## Features to Implement
- [ ] Multiple concurrent progress bars
- [ ] Simulated package downloads
- [ ] Individual package states (waiting, downloading, complete)
- [ ] Overall progress tracking
- [ ] Styled package list

## Key Components
1. Multiple progress bubble instances
2. Async download simulation
3. State machine per package
4. Combined view layout

## Estimated Effort
Medium - 1-2 days (after progress bubble)
