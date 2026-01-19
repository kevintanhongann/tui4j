# Spinners Example

The `spinners` example demonstrates all available spinner styles in a synchronized grid layout.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/spinners/SpinnersExample.java">
  <img width="750" src="../../assets/spinners-tape.gif" alt="Animation showing TUI4J spinners example" />
</a>

## Features

- All 12 spinner styles from SpinnerType enum
- Grid layout display
- Spinner style names with each spinner
- Synchronized animation using shared tick
- Key handling (q, ctrl+c to quit)

## Spinner Types

1. LINE - Classic line spinner (|/-\)
2. DOT - Braille dot spinner
3. MINI_DOT - Smaller braille dot spinner
4. JUMP - Jumping characters spinner
5. PULSE - Block pulse spinner
6. POINTS - Pointillist spinner
7. GLOBE - Earth globe spinner
8. MOON - Moon phase spinner
9. MONKEY - Monkey emoji spinner
10. METER - Meter bar spinner
11. HAMBURGER - Hamburger emoji spinner
12. ELLIPSIS - Text ellipsis spinner

## Running

```bash
# From project root
./gradlew examplesJar
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.spinners.SpinnersExample
```
