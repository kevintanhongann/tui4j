# Implementation Plan: Chat Example

## Overview
Port the `chat` example from [bubbletea/examples/chat](https://github.com/charmbracelet/bubbletea/tree/master/examples/chat).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/chat/`

## Target Directory
`examples/generic/chat/`

## Prerequisites
- [x] `textarea` bubble (TODO)
- [x] `viewport` bubble (TODO)

## Features to Implement
- [x] Split view: message history + input area
- [x] Scrollable message viewport
- [x] Multi-line text input
- [x] Message sending and display
- [x] Styled message bubbles

## Key Components
1. [x] Viewport for message history (scrollable)
2. [x] Textarea for message composition
3. [x] Message model with sender/content
4. [x] Layout combining both components

## Estimated Effort
Medium - 2 days (after prerequisites)
