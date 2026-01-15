# Implementation Plan: Realtime Example

## Overview
Port the `realtime` example from [bubbletea/examples/realtime](https://github.com/charmbracelet/bubbletea/tree/master/examples/realtime).

## Upstream Reference
- **Repository**: https://github.com/charmbracelet/bubbletea
- **Path**: `examples/realtime/`

## Target Directory
`examples/generic/realtime/`

## Prerequisites
- Core message/command system (done)

## Features to Implement
- [ ] External event source (simulated)
- [ ] Real-time updates from background thread
- [ ] Message injection into Program
- [ ] Live data display

## Key Concepts
1. Background data producer
2. Thread-safe message sending
3. Real-time UI updates
4. Proper cleanup on exit

## Java Implementation Notes
```java
// Send message from background thread
ExecutorService executor = Executors.newSingleThreadExecutor();
executor.submit(() -> {
    while (running) {
        program.send(new DataUpdateMsg(getData()));
        Thread.sleep(100);
    }
});
```

## Estimated Effort
Low-Medium - 1 day
