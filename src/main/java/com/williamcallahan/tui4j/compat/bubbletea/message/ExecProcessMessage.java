package com.williamcallahan.tui4j.compat.bubbletea.message;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.BiConsumer;

/**
 * Executes a process and yields a message when complete.
 * Bubble Tea: bubbletea/exec.go
 */
public class ExecProcessMessage implements Message {

    private final Process process;
    private final BiConsumer<Integer, byte[]> outputHandler;
    private final BiConsumer<Integer, byte[]> errorHandler;

    public ExecProcessMessage(Process process, BiConsumer<Integer, byte[]> outputHandler, BiConsumer<Integer, byte[]> errorHandler) {
        this.process = process;
        this.outputHandler = outputHandler;
        this.errorHandler = errorHandler;
    }

    public Process process() {
        return process;
    }

    public BiConsumer<Integer, byte[]> outputHandler() {
        return outputHandler;
    }

    public BiConsumer<Integer, byte[]> errorHandler() {
        return errorHandler;
    }

    public static byte[] readStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString().getBytes();
        }
    }
}

