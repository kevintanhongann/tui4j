# Tabs Example

The `tabs` example demonstrates a tabbed interface with multiple tabs and content panels using TUI4J's Lipgloss styling.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/tabs/TabsExample.java">
  <img width="750" src="../../assets/tabs-tape.gif" alt="Animation showing TUI4J tabs example with tab switching" />
</a>

## Features

- Tab bar with multiple tabs
- Active tab indicator with styled borders
- Tab switching with arrow keys (left/right), tab/shift+tab, or vim keys (h/l)
- Tab content panels that change based on active tab
- Styled tab appearance using Lipgloss

## Controls

| Key | Action |
|-----|--------|
| `→` `l` `n` `Tab` | Next tab |
| `←` `h` `p` `Shift+Tab` | Previous tab |
| `q` `Ctrl+C` | Quit |

## Running

```bash
# From project root
./gradlew examplesJar
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.tabs.TabsExample
```
