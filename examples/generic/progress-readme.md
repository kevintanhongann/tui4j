# Progress Bubble Examples

This directory contains examples demonstrating the Progress bubble from tui4j.

## Running Examples

### Prerequisites

- Java 21 or later
- Gradle

### Build and Run

```bash
# Build the examples fat JAR and copy to all example directories
./gradlew --no-configuration-cache copyAllExampleJars

# Run individual examples
./progress-static/run
./progress-animated/run
./progress-download/run
./package-manager/run
```

Or run directly:

```bash
./gradlew --no-configuration-cache build
java -jar build/libs/tui4j-examples.jar com.williamcallahan.tui4j.examples.progress.staticbar.ProgressStaticExample
java -jar build/libs/tui4j-examples.jar com.williamcallahan.tui4j.examples.progress.animated.ProgressAnimatedExample
java -jar build/libs/tui4j-examples.jar com.williamcallahan.tui4j.examples.progress.download.ProgressDownloadExample
java -jar build/libs/tui4j-examples.jar com.williamcallahan.tui4j.examples.progress.packagemanager.PackageManagerExample
```

## Examples

### progress-static
Demonstrates static progress bars with various configurations:
- Default block characters
- Block characters (█/░)
- ASCII style (#/.)
- Different widths (20, 40, 60 chars)
- Various percentage formats

Controls: `j/k` or `up/down` to navigate, `q` to quit

### progress-animated
Demonstrates animated progress with spring physics:
- Smooth transitions using spring animation
- Auto mode with random targets
- Manual target control via keyboard

Controls:
- `SPACE` - Toggle auto mode
- `1-5` - Set target to 10%, 25%, 50%, 75%, 100%
- `+/-` - Adjust target by 10%
- `r` - Reset to 0%
- `q` - Quit

### progress-download
Simulates a file download manager with multiple concurrent downloads:
- Multiple file downloads with progress tracking
- Download speed display
- Pause/resume support
- Selection navigation

Controls:
- `j/k` or `up/down` - Select file
- `space` - Pause/resume selected download
- `r` - Reset all downloads
- `q` - Quit

### package-manager
Simulates a package installation process:
- Multi-step installation with progress
- Overall progress bar
- Expandable individual steps
- Spinner animation during active operations

Controls:
- `j/k` or `up/down` - Select operation
- `space` - Expand/collapse operation details
- `q` - Quit
