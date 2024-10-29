/*
 * Copyright (c) 2023 DWolf Nineteen & The JDA-Extra Contributors
 * Copyright (c) 2024 DWolf Nineteen & The Rextra Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.dwolfnineteen.jdaextra.parsers;

import com.dwolfnineteen.jdaextra.JDAExtra;
import com.dwolfnineteen.jdaextra.models.CommonCommandProperties;
import com.dwolfnineteen.jdaextra.options.mappings.CommandOptionMapping;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 */
public abstract class CommandParser {
    /**
     * The {@link JDAExtra} instance.
     */
    protected JDAExtra jdaExtra;
    /**
     * The {@link GenericEvent} (source event) for this parser.
     */
    protected GenericEvent sourceEvent;
    /**
     *
     */
    protected CommonCommandProperties properties;

    /**
     *
     */
    public CommandParser(@NotNull JDAExtra jdaExtra, @NotNull GenericEvent sourceEvent) {
        this.jdaExtra = jdaExtra;
        this.sourceEvent = sourceEvent;
    }

    /**
     * The {@link JDAExtra} instance.
     *
     * @return The {@link JDAExtra} instance.
     */
    public @NotNull JDAExtra getJDAExtra() {
        return jdaExtra;
    }

    /**
     * The {@link GenericEvent} (source event) for this parser.
     *
     * @return The {@link GenericEvent} for this parser.
     */
    public abstract GenericEvent getSourceEvent();

    /**
     * {@link CommonCommandProperties} for this parser.
     *
     * @return The {@link CommonCommandProperties} for this parser.
     */
    public abstract CommonCommandProperties getProperties();

    /**
     * Sets {@link CommonCommandProperties} for this parser.
     *
     * @param properties {@link CommonCommandProperties} for this parser.
     * @return Current {@link CommandParser} instance, for chaining.
     */
    public abstract CommandParser setProperties(CommonCommandProperties properties);

    /**
     * Build arguments for executing command entry point.
     *
     * @return Arguments for {@link java.lang.reflect.Method#invoke Method.invoke()}.
     * @see com.dwolfnineteen.jdaextra.annotations.ExtraMainCommand ExtraMainCommand
     */
    public abstract Object[] buildInvokeArguments();

    /**
     * Build argument type for {@link java.lang.reflect.Method#invoke Method.invoke()}.
     *
     * @param type The {@link OptionType} from
     * {@link com.dwolfnineteen.jdaextra.options.data.CommandOptionData CommandOptionData}.
     * @param mapping The {@link CommandOptionMapping}.
     * @return Argument type. {@code null}, if the {@link OptionType} does not match supported type.
     */
    protected @Nullable Object buildInvokeArgumentType(@NotNull OptionType type, @NotNull CommandOptionMapping mapping) {
        switch (type) {
            case BOOLEAN:
                return mapping.getAsBoolean();
            case CHANNEL:
                return mapping.getAsChannel();
            case INTEGER:
                return mapping.getAsLong();
            case MENTIONABLE:
                return mapping.getAsMentionable();
            case NUMBER:
                return mapping.getAsDouble();
            case STRING:
                return mapping.getAsString();
            case USER:
                return mapping.getAsUser();
            default:
                return null;
        }
    }
}
