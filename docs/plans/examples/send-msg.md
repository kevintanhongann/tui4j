# Implementation Plan: Send Msg Example

## Overview
Port the `send-msg` example from [bubbletea/examples/send-msg](https://github.com/charmbracelet/bubbletea/tree/master/examples/send-msg).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/send-msg/`

## Target Directory
`examples/generic/send-msg/`

## Prerequisites
- Core message/command system (done)

## Features to Implement
- [ ] Programmatic message sending
- [ ] External trigger for messages
- [ ] Message queue handling
- [ ] Response to sent messages

## Key Concepts
1. `Program.send(Message)` API
2. External message injection
3. Async message handling

## Estimated Effort
Low - 0.5 days
