# Implementation Plan: Exec Example

## Overview
Port the `exec` example from [bubbletea/examples/exec](https://github.com/charmbracelet/bubbletea/tree/master/examples/exec).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/exec/`

## Target Directory
`examples/generic/exec/`

## Prerequisites
- Core Program exec functionality

## Features to Implement
- [ ] Execute external command (e.g., editor)
- [ ] Suspend TUI during external process
- [ ] Restore TUI after process completes
- [ ] Pass data to/from external process

## Key Components
1. `ExecCommand` - Command to run external process
2. Program suspension/restoration
3. Process output capture (optional)
4. Error handling for failed processes

## Java Implementation Notes
- Use `ProcessBuilder` for process execution
- Handle terminal mode switching (raw → cooked → raw)
- Proper cleanup on process termination

## Estimated Effort
Medium - 2 days (terminal mode handling)
