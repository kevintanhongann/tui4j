# Implementation Plan: Views Example

## Overview
Port the `views` example from [bubbletea/examples/views](https://github.com/charmbracelet/bubbletea/tree/master/examples/views).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/views/`

## Target Directory
`examples/generic/views/`

## Prerequisites
- Core Model/Program (done)
- Lipgloss styling (done)

## Features to Implement
- [ ] Multiple view states (screens)
- [ ] View transitions
- [ ] State machine for navigation
- [ ] Different layouts per view

## Key Concepts
1. View state enum
2. Switch statement in view()
3. Navigation messages
4. Shared model state across views

## Implementation Pattern
```java
enum ViewState { MENU, FORM, RESULT }

public String view() {
    return switch (currentView) {
        case MENU -> renderMenu();
        case FORM -> renderForm();
        case RESULT -> renderResult();
    };
}
```

## Estimated Effort
Low - 1 day
