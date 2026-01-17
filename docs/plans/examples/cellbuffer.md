# Implementation Plan: Cellbuffer Example

## Overview
Port the `cellbuffer` example from [bubbletea/examples/cellbuffer](https://github.com/charmbracelet/bubbletea/tree/master/examples/cellbuffer).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/cellbuffer/`

## Target Directory
`examples/generic/src/main/java/com/williamcallahan/tui4j/examples/cellbuffer/`

## Prerequisites
- [x] Port of `harmonica` library (spring animation physics)

## Features Implemented
- [x] Cell-based rendering buffer
- [x] Smooth animation using spring physics
- [x] Direct cell manipulation for performance

## Implementation Details

### Harmonca Spring Physics (`com.williamcallahan.tui4j.compat.bubbletea.harmonica.Spring`)
- Ported from Go's harmonica library
- Implements damped harmonic oscillator (spring physics)
- Precomputed coefficients for efficient updates
- Supports over-damped, critically-damped, and under-damped spring behavior

### Cellbuffer Example (`com.williamcallahan.tui4j.examples.cellbuffer.CellExample`)
- Cell-based rendering buffer for direct manipulation
- Smooth ellipse animation using spring physics
- Mouse tracking for interactive control
- Ellipse drawing algorithm (midpoint ellipse algorithm)

## Files Created
- `src/main/java/com/williamcallahan/tui4j/compat/bubbletea/harmonica/Spring.java`
- `examples/generic/src/main/java/com/williamcallahan/tui4j/examples/cellbuffer/CellExample.java`

## Verification
- `./gradlew build` - BUILD SUCCESSFUL
- All 486 tests pass
