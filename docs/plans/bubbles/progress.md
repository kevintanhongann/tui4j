# Implementation Plan: Progress Bubble

## Overview
Port the `progress` bubble from [bubbles/progress](https://github.com/charmbracelet/bubbles/tree/master/progress) to Java.

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbles
- **Package**: `progress`
- **Key Files**: `progress.go`

## Target Package
`com.williamcallahan.tui4j.compat.bubbletea.bubbles.progress`

## Core Components to Implement

### 1. Progress Model
- [ ] `Progress` class implementing `Model`
- [ ] Percentage tracking (0.0 to 1.0)
- [ ] Width configuration
- [ ] Animated vs static modes

### 2. Configuration Options
- [ ] `width` - Total bar width in characters
- [ ] `full` - Character for filled portion (default: `█`)
- [ ] `empty` - Character for empty portion (default: `░`)
- [ ] `showPercentage` - Display percentage text
- [ ] `percentageFormat` - Format string for percentage

### 3. Styling
- [ ] `fullColor` - Gradient or solid color for filled portion
- [ ] `emptyColor` - Color for empty portion
- [ ] Gradient support (start/end colors)
- [ ] Spring animation configuration (for animated mode)

### 4. Messages
- [ ] `FrameMsg` - Animation tick for smooth transitions
- [ ] `SetPercentMsg` - Set progress percentage

### 5. Commands
- [ ] `setPercent(double)` - Command to update progress
- [ ] `incrPercent(double)` - Command to increment progress

### 6. Animation Support
- [ ] Spring-based animation for smooth transitions
- [ ] Configurable spring parameters (damping, frequency)
- [ ] Frame-based rendering

## Dependencies
- Lipgloss styling (done)
- Potentially `harmonica` for spring animation (may need simplified implementation)

## Testing
- [ ] Unit tests for percentage calculation
- [ ] Unit tests for bar rendering at various widths
- [ ] Unit tests for gradient colors
- [ ] Visual tests for animation

## Examples
- `examples/generic/progress-static/`
- `examples/generic/progress-animated/`
- `examples/generic/progress-download/`
- `examples/generic/package-manager/`

## Estimated Effort
Medium - 2-3 days (more if spring animation is complex)
