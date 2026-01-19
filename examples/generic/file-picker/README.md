# File Picker

The `file-picker` example demonstrates how to use the FilePicker bubble to browse directories and select files.

<a href="./src/main/java/com/williamcallahan/tui4j/examples/filepicker/FilePickerExample.java">
  Code
</a>

## Features

- Browse directories with file/folder navigation
- File type filtering (configured for `.mod`, `.sum`, `.go`, `.txt`, `.md`)
- File permissions and size display
- Keyboard navigation (vim-style keys)
- Help overlay with all available shortcuts

## Controls

| Key | Action |
|-----|--------|
| `↑` / `k` | Move cursor up |
| `↓` / `j` | Move cursor down |
| `g` / `home` | Go to top |
| `G` / `end` | Go to bottom |
| `pgup` | Page up |
| `pgdn` | Page down |
| `→` / `l` / `enter` | Open directory / select file |
| `←` / `h` / `backspace` | Go back |
| `ctrl+s` | Select file |
| `.` | Toggle hidden files |
| `q` / `esc` / `ctrl+c` | Quit |

## Running

```bash
# From project root
./gradlew examplesJar
java -cp examples/generic/tui4j-examples.jar com.williamcallahan.tui4j.examples.filepicker.FilePickerExample
```
