# TUI4J Implementation Plans

This directory contains implementation plans for all remaining Bubble Tea components to be ported.

## Priority Order (Recommended)

Based on dependencies and impact, here's the suggested implementation order:

### Phase 1: Core Components (High Impact)
| Component | Type | Effort | Unlocks |
|-----------|------|--------|---------|
| [viewport](bubbles/viewport.md) | Bubble | 2-3 days | pager, chat, textarea |
| [progress](bubbles/progress.md) | Bubble | 2-3 days | package-manager, progress-* examples |
| [timer](bubbles/timer.md) | Bubble | 1-2 days | timer, composable-views examples |
| [stopwatch](bubbles/stopwatch.md) | Bubble | 1 day | stopwatch example |

### Phase 2: Text & Tables
| Component | Type | Effort | Unlocks |
|-----------|------|--------|---------|
| [textarea](bubbles/textarea.md) | Bubble | 4-6 days | chat, split-editors, prevent-quit |
| [max-dimensions](lipgloss/max-dimensions.md) | Lipgloss | 1-2 days | Better layout control |
| [table (lipgloss)](lipgloss/table.md) | Lipgloss | 3-4 days | table bubble |
| [table](bubbles/table.md) | Bubble | 3-5 days | table, table-resize examples |

### Phase 3: File & Navigation
| Component | Type | Effort | Unlocks |
|-----------|------|--------|---------|
| [filepicker](bubbles/filepicker.md) | Bubble | 2-3 days | file-picker example |

### Phase 4: Examples (Ready Now)
These examples can be implemented with current bubbles:
| Example | Effort | Dependencies |
|---------|--------|--------------|
| [help](examples/help.md) | 0.5 days | ✅ Ready |
| [autocomplete](examples/autocomplete.md) | 1 day | ✅ Ready |
| [spinners](examples/spinners.md) | 0.5 days | ✅ Ready |
| [simple](examples/simple.md) | 0.5 days | ✅ Ready |
| [credit-card-form](examples/credit-card-form.md) | 1-2 days | ✅ Ready |
| [debounce](examples/debounce.md) | 1 day | ✅ Ready |
| [tabs](examples/tabs.md) | 1 day | ✅ Ready |
| [views](examples/views.md) | 1 day | ✅ Ready |
| [send-msg](examples/send-msg.md) | 0.5 days | ✅ Ready |
| [realtime](examples/realtime.md) | 1 day | ✅ Ready |
| [pipe](examples/pipe.md) | 1 day | ✅ Ready |

### Phase 5: Examples (After Prerequisites)
| Example | Effort | Waiting On |
|---------|--------|------------|
| [pager](examples/pager.md) | 0.5 days | viewport |
| [chat](examples/chat.md) | 2 days | viewport, textarea |
| [package-manager](examples/package-manager.md) | 1-2 days | progress |
| [progress-static](examples/progress-static.md) | 0.5 days | progress |
| [progress-animated](examples/progress-animated.md) | 0.5 days | progress |
| [progress-download](examples/progress-download.md) | 0.5 days | progress |
| [timer](examples/timer.md) | 0.5 days | timer |
| [stopwatch](examples/stopwatch.md) | 0.5 days | stopwatch |
| [composable-views](examples/composable-views.md) | 1 day | timer |
| [textarea](examples/textarea.md) | 0.5 days | textarea |
| [split-editors](examples/split-editors.md) | 1 day | textarea |
| [prevent-quit](examples/prevent-quit.md) | 1 day | textarea |
| [table](examples/table.md) | 0.5 days | table |
| [table-resize](examples/table-resize.md) | 0.5-1 day | table |
| [file-picker](examples/file-picker.md) | 0.5 days | filepicker |

### Phase 6: Platform-Specific
| Example | Effort | Notes |
|---------|--------|-------|
| [exec](examples/exec.md) | 2 days | Terminal mode handling |
| [suspend](examples/suspend.md) | 1-2 days | Unix signals |
| [tui-daemon-combo](examples/tui-daemon-combo.md) | 1-2 days | CLI modes |

### Phase 7: Complex/Blocked
| Example | Effort | Blocker |
|---------|--------|---------|
| [cellbuffer](examples/cellbuffer.md) | 2-3 days | Needs harmonica port |
| [glamour](examples/glamour.md) | 5+ days | Needs glamour port |

---

## Directory Structure

```
docs/plans/
├── README.md              # This file
├── bubbles/               # Bubble component plans
│   ├── filepicker.md
│   ├── progress.md
│   ├── stopwatch.md
│   ├── table.md
│   ├── textarea.md
│   ├── timer.md
│   └── viewport.md
├── lipgloss/              # Lipgloss feature plans
│   ├── max-dimensions.md
│   └── table.md
└── examples/              # Example application plans
    ├── autocomplete.md
    ├── cellbuffer.md
    ├── chat.md
    ├── composable-views.md
    ├── credit-card-form.md
    ├── debounce.md
    ├── exec.md
    ├── file-picker.md
    ├── glamour.md
    ├── help.md
    ├── package-manager.md
    ├── pager.md
    ├── pipe.md
    ├── prevent-quit.md
    ├── progress-animated.md
    ├── progress-download.md
    ├── progress-static.md
    ├── realtime.md
    ├── send-msg.md
    ├── simple.md
    ├── spinners.md
    ├── split-editors.md
    ├── stopwatch.md
    ├── suspend.md
    ├── table.md
    ├── table-resize.md
    ├── tabs.md
    ├── textarea.md
    ├── timer.md
    ├── tui-daemon-combo.md
    └── views.md
```

## Effort Summary

| Category | Items | Total Effort |
|----------|-------|--------------|
| Bubbles | 7 | ~15-22 days |
| Lipgloss | 2 | ~4-6 days |
| Examples | 21 | ~15-20 days |
| **Total** | **30** | **~34-48 days** |
