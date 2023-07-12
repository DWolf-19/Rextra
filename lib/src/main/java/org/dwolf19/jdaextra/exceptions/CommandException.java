package org.dwolf19.jdaextra.exceptions;

import org.jetbrains.annotations.NotNull;

public class CommandException extends RuntimeException {
    public CommandException(@NotNull String message) {
        super(message);
    }
}