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
- [x] Execute external command (e.g., editor)
- [x] Suspend TUI during external process
- [x] Restore TUI after process completes
- [x] Pass data to/from external process

## Key Components
1. `Command.execProcess()` - Command factory to run external process
2. `Program.executeProcess()` - Program suspension/restoration using JLine `pause()`/`resume()`
3. `ExecProcessMessage` - Message class for process execution data
4. `ExecCompletedMessage` - Message sent when process completes
5. `ExecExample` - Example demonstrating external command execution

## Java Implementation Notes
- Use `ProcessBuilder` for process execution
- Handle terminal mode switching (raw → cooked → raw)
- Proper cleanup on process termination

## Estimated Effort
Medium - 2 days (terminal mode handling)

## Status
Completed - Implementation merged
