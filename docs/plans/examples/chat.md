# Implementation Plan: Chat Example

## Overview
Port the `chat` example from [bubbletea/examples/chat](https://github.com/charmbracelet/bubbletea/tree/master/examples/chat).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/chat/`

## Target Directory
`examples/generic/chat/`

## Prerequisites
- [ ] `textarea` bubble (TODO)
- [ ] `viewport` bubble (TODO)

## Features to Implement
- [ ] Split view: message history + input area
- [ ] Scrollable message viewport
- [ ] Multi-line text input
- [ ] Message sending and display
- [ ] Styled message bubbles

## Key Components
1. Viewport for message history (scrollable)
2. Textarea for message composition
3. Message model with sender/content
4. Layout combining both components

## Estimated Effort
Medium - 2 days (after prerequisites)
