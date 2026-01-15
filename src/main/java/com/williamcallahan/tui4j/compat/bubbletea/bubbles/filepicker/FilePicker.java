package com.williamcallahan.tui4j.compat.bubbletea.bubbles.filepicker;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.bubbles.key.Binding;
import com.williamcallahan.tui4j.compat.bubbletea.lipgloss.Style;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FilePicker implements Model {

    private static final int MARGIN_BOTTOM = 5;
    private static final int FILE_SIZE_WIDTH = 7;
    private static final int PADDING_LEFT = 2;

    private final int id;
    private String path;
    private String currentDirectory;
    private List<String> allowedTypes;
    private KeyMap keyMap;
    private List<DirEntry> files;
    private boolean showPermissions;
    private boolean showSize;
    private boolean showHidden;
    private boolean dirAllowed;
    private boolean fileAllowed;
    private boolean autoHeight;
    private int height;
    private int cursor;
    private int selected;
    private int min;
    private int max;
    private Stack selectedStack;
    private Stack minStack;
    private Stack maxStack;
    private Styles styles;
    private String cursorChar;
    private AtomicInteger nextId;
    private boolean widthSet;
    private int terminalWidth;

    public FilePicker() {
        this.nextId = new AtomicInteger(1);
        this.id = generateId();
        this.currentDirectory = ".";
        this.cursorChar = ">";
        this.allowedTypes = new ArrayList<>();
        this.selected = 0;
        this.showPermissions = true;
        this.showSize = true;
        this.showHidden = false;
        this.dirAllowed = false;
        this.fileAllowed = true;
        this.autoHeight = true;
        this.height = 0;
        this.max = 0;
        this.min = 0;
        this.selectedStack = new Stack();
        this.minStack = new Stack();
        this.maxStack = new Stack();
        this.keyMap = new KeyMap();
        this.styles = Styles.defaultStyles();
        this.files = new ArrayList<>();
        this.widthSet = false;
        this.terminalWidth = 0;
    }

    private int generateId() {
        return nextId.getAndIncrement();
    }

    public Command init() {
        return readDir(this.currentDirectory, this.showHidden);
    }

    private Command readDir(String directory, boolean showHidden) {
        return () -> {
            try {
                Path dirPath = Paths.get(directory);
                List<DirEntry> entries = new ArrayList<>();
                try (var stream = Files.list(dirPath)) {
                    stream.sorted(Comparator.comparing((Path p) -> {
                        try {
                            boolean isDir = Files.isDirectory(p);
                            return !isDir;
                        } catch (Exception e) {
                            return true;
                        }
                    }).thenComparing(Path::getFileName)).forEach(p -> {
                        try {
                            boolean isDir = Files.isDirectory(p);
                            boolean isSymlink = Files.isSymbolicLink(p);
                            boolean isHidden = p.getFileName().toString().startsWith(".");
                            String name = p.getFileName().toString();

                            if (!showHidden && isHidden && !name.equals(".") && !name.equals("..")) {
                                return;
                            }

                            long size = Files.size(p);
                            String permissions = Files.getPosixFilePermissions(p).toString();
                            entries.add(new DirEntry(name, isDir, isSymlink, size, permissions));
                        } catch (Exception e) {
                        }
                    });
                }
                return new ReadDirMessage(this.id, entries);
            } catch (Exception e) {
                return new ErrorMessage(e);
            }
        };
    }

    public void setHeight(int height) {
        this.height = height;
        if (this.max > this.height - 1) {
            this.max = this.min + this.height - 1;
        }
    }

    public void setTerminalSize(int width, int height) {
        this.terminalWidth = width;
        this.widthSet = true;
        if (this.autoHeight) {
            this.height = height - MARGIN_BOTTOM;
        }
        this.max = this.height - 1;
    }

    @Override
    public UpdateResult<FilePicker> update(Message msg) {
        if (msg instanceof KeyPressMessage keyMsg) {
            if (Binding.matches(keyMsg, keyMap.goToTop())) {
                this.selected = 0;
                this.min = 0;
                this.max = this.height - 1;
                return UpdateResult.from(this);
            } else if (Binding.matches(keyMsg, keyMap.goToLast())) {
                this.selected = Math.max(0, this.files.size() - 1);
                this.min = Math.max(0, this.files.size() - this.height);
                this.max = Math.max(0, this.files.size() - 1);
                return UpdateResult.from(this);
            } else if (Binding.matches(keyMsg, keyMap.down())) {
                this.selected++;
                if (this.selected >= this.files.size()) {
                    this.selected = this.files.size() - 1;
                }
                if (this.selected > this.max) {
                    this.min++;
                    this.max++;
                }
                return UpdateResult.from(this);
            } else if (Binding.matches(keyMsg, keyMap.up())) {
                this.selected--;
                if (this.selected < 0) {
                    this.selected = 0;
                }
                if (this.selected < this.min) {
                    this.min--;
                    this.max--;
                }
                return UpdateResult.from(this);
            } else if (Binding.matches(keyMsg, keyMap.pageDown())) {
                this.selected += this.height;
                if (this.selected >= this.files.size()) {
                    this.selected = this.files.size() - 1;
                }
                this.min += this.height;
                this.max += this.height;

                if (this.max >= this.files.size()) {
                    this.max = this.files.size() - 1;
                    this.min = Math.max(0, this.max - this.height);
                }
                return UpdateResult.from(this);
            } else if (Binding.matches(keyMsg, keyMap.pageUp())) {
                this.selected -= this.height;
                if (this.selected < 0) {
                    this.selected = 0;
                }
                this.min -= this.height;
                this.max -= this.height;

                if (this.min < 0) {
                    this.min = 0;
                    this.max = this.min + this.height;
                }
                return UpdateResult.from(this);
            } else if (Binding.matches(keyMsg, keyMap.back())) {
                this.currentDirectory = Path.of(this.currentDirectory).getParent() != null
                        ? Path.of(this.currentDirectory).getParent().toString()
                        : ".";
                if (this.selectedStack.length() > 0) {
                    this.selected = this.selectedStack.pop();
                    this.min = this.minStack.pop();
                    this.max = this.maxStack.pop();
                } else {
                    this.selected = 0;
                    this.min = 0;
                    this.max = this.height - 1;
                }
                return UpdateResult.from(this, readDir(this.currentDirectory, this.showHidden));
            } else if (Binding.matches(keyMsg, keyMap.open())) {
                if (this.files.isEmpty()) {
                    return UpdateResult.from(this);
                }

                DirEntry f = this.files.get(this.selected);
                boolean isDir = f.isDir();
                boolean isSymlink = f.isSymlink();

                if (isSymlink) {
                    try {
                        Path symlinkPath = Files.readSymbolicLink(Paths.get(this.currentDirectory, f.name()));
                        isDir = Files.isDirectory(symlinkPath);
                    } catch (Exception e) {
                    }
                }

                if ((!isDir && this.fileAllowed) || (isDir && this.dirAllowed)) {
                    if (Binding.matches(keyMsg, keyMap.select())) {
                        this.path = Paths.get(this.currentDirectory, f.name()).toString();
                    }
                }

                if (!isDir) {
                    return UpdateResult.from(this);
                }

                this.currentDirectory = Paths.get(this.currentDirectory, f.name()).toString();
                this.selectedStack.push(this.selected);
                this.minStack.push(this.min);
                this.maxStack.push(this.max);
                this.selected = 0;
                this.min = 0;
                this.max = this.height - 1;
                return UpdateResult.from(this, readDir(this.currentDirectory, this.showHidden));
            }
        } else if (msg instanceof ReadDirMessage readDirMsg) {
            if (readDirMsg.id() != this.id) {
                return UpdateResult.from(this);
            }
            this.files = readDirMsg.entries();
            this.max = Math.max(this.max, this.height - 1);
            return UpdateResult.from(this);
        } else if (msg instanceof ErrorMessage errorMsg) {
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        StringBuilder sb = new StringBuilder();

        if (this.files.isEmpty()) {
            sb.append(this.styles.emptyDirectory()
                    .height(this.height)
                    .render("Bummer. No Files Found."));
            return sb.toString();
        }

        for (int i = 0; i < this.files.size(); i++) {
            if (i < this.min || i > this.max) {
                continue;
            }

            DirEntry f = this.files.get(i);
            boolean disabled = !canSelect(f.name()) && !f.isDir();

            String line;
            if (this.selected == i) {
                StringBuilder selectedBuilder = new StringBuilder();
                if (this.showPermissions) {
                    selectedBuilder.append(" ").append(f.permissions());
                }
                if (this.showSize) {
                    selectedBuilder.append(formatSize(f.size()));
                }
                selectedBuilder.append(" ").append(f.name());

                if (f.isSymlink()) {
                    try {
                        Path symlinkPath = Files.readSymbolicLink(Paths.get(this.currentDirectory, f.name()));
                        selectedBuilder.append(" → ").append(symlinkPath);
                    } catch (Exception e) {
                    }
                }

                if (disabled) {
                    sb.append(this.styles.disabledCursor().render(this.cursorChar));
                    sb.append(this.styles.disabledSelected().render(selectedBuilder.toString()));
                } else {
                    sb.append(this.styles.cursor().render(this.cursorChar));
                    sb.append(this.styles.selected().render(selectedBuilder.toString()));
                }
                sb.append("\n");
                continue;
            }

            Style style = this.styles.file();
            if (f.isDir()) {
                style = this.styles.directory();
            } else if (f.isSymlink()) {
                style = this.styles.symlink();
            } else if (disabled) {
                style = this.styles.disabledFile();
            }

            sb.append(this.styles.cursor().render(" "));

            String fileName = style.render(f.name());
            if (f.isSymlink()) {
                try {
                    Path symlinkPath = Files.readSymbolicLink(Paths.get(this.currentDirectory, f.name()));
                    fileName += " → " + symlinkPath;
                } catch (Exception e) {
                }
            }

            if (this.showPermissions) {
                sb.append(" ").append(this.styles.permission().render(f.permissions()));
            }
            if (this.showSize) {
                sb.append(this.styles.fileSize().render(formatSize(f.size())));
            }
            sb.append(" ").append(fileName);
            sb.append("\n");
        }

        int currentHeight = sb.toString().split("\n", -1).length;
        for (int i = currentHeight; i <= this.height; i++) {
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.1f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.1f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }

    private boolean canSelect(String file) {
        if (this.allowedTypes.isEmpty()) {
            return true;
        }

        for (String ext : this.allowedTypes) {
            if (file.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public String selectedPath() {
        return this.path;
    }

    public boolean didSelectFile(Message msg) {
        if (msg instanceof KeyPressMessage keyMsg) {
            if (!Binding.matches(keyMsg, keyMap.select())) {
                return false;
            }

            if (this.files.isEmpty()) {
                return false;
            }

            DirEntry f = this.files.get(this.selected);
            boolean isDir = f.isDir();
            boolean isSymlink = f.isSymlink();

            if (isSymlink) {
                try {
                    Path symlinkPath = Files.readSymbolicLink(Paths.get(this.currentDirectory, f.name()));
                    isDir = Files.isDirectory(symlinkPath);
                } catch (Exception e) {
                    return false;
                }
            }

            if ((!isDir && this.fileAllowed) || (isDir && this.dirAllowed)) {
                this.path = Paths.get(this.currentDirectory, f.name()).toString();
                return true;
            }
        }
        return false;
    }

    public boolean didSelectDirectory(Message msg) {
        if (msg instanceof KeyPressMessage keyMsg) {
            if (!Binding.matches(keyMsg, keyMap.open())) {
                return false;
            }

            if (this.files.isEmpty()) {
                return false;
            }

            DirEntry f = this.files.get(this.selected);
            if (!f.isDir()) {
                return false;
            }

            return true;
        }
        return false;
    }

    public String currentDirectory() {
        return this.currentDirectory;
    }

    public void setCurrentDirectory(String directory) {
        this.currentDirectory = directory;
    }

    public List<String> allowedTypes() {
        return new ArrayList<>(this.allowedTypes);
    }

    public void setAllowedTypes(String... types) {
        this.allowedTypes = new ArrayList<>(Arrays.asList(types));
    }

    public boolean showHidden() {
        return this.showHidden;
    }

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public boolean dirAllowed() {
        return this.dirAllowed;
    }

    public void setDirAllowed(boolean dirAllowed) {
        this.dirAllowed = dirAllowed;
    }

    public boolean fileAllowed() {
        return this.fileAllowed;
    }

    public void setFileAllowed(boolean fileAllowed) {
        this.fileAllowed = fileAllowed;
    }

    public boolean showPermissions() {
        return this.showPermissions;
    }

    public void setShowPermissions(boolean showPermissions) {
        this.showPermissions = showPermissions;
    }

    public boolean showSize() {
        return this.showSize;
    }

    public void setShowSize(boolean showSize) {
        this.showSize = showSize;
    }

    public int height() {
        return this.height;
    }

    public Styles styles() {
        return this.styles;
    }

    public void setStyles(Styles styles) {
        this.styles = styles;
    }

    public KeyMap keyMap() {
        return this.keyMap;
    }

    public void setKeyMap(KeyMap keyMap) {
        this.keyMap = keyMap;
    }

    public String cursorChar() {
        return this.cursorChar;
    }

    public void setCursorChar(String cursorChar) {
        this.cursorChar = cursorChar;
    }

    public record DirEntry(String name, boolean isDir, boolean isSymlink, long size, String permissions) {
    }

    private record ReadDirMessage(int id, List<DirEntry> entries) implements Message {
    }

    private record ErrorMessage(Exception err) implements Message {
    }

    private static class Stack {
        private final List<Integer> items = new ArrayList<>();

        public void push(int item) {
            this.items.add(item);
        }

        public int pop() {
            if (this.items.isEmpty()) {
                return 0;
            }
            int result = this.items.get(this.items.size() - 1);
            this.items.remove(this.items.size() - 1);
            return result;
        }

        public int length() {
            return this.items.size();
        }
    }
}
