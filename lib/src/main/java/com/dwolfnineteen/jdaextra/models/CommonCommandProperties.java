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
package com.dwolfnineteen.jdaextra.models;

import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

// TODO: Methods for removing options
/**
 * The commands' superclass. Properties shared across all types of commands.
 */
public abstract class CommonCommandProperties {
    /**
     * The command main entry point.
     */
    protected Method entryPoint;

    /**
     * The command entry point (instance of {@link Method}).
     *
     * @return The entry point.
     */
    public abstract Method getEntryPoint();

    /**
     * Sets the command entry point.
     *
     * @param entryPoint The command entry point.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties setEntryPoint(Method entryPoint);

    /**
     * The command name.
     *
     * @return The name.
     */
    public abstract String getName();

    /**
     * Sets the command name.
     *
     * @param name The name.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties setName(String name);

    /**
     * The command description.
     *
     * @return The description.
     */
    public abstract String getDescription();

    /**
     * Sets the command description.
     *
     * @param description The description.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties setDescription(String description);

    /**
     * The command options ({@link List} of {@link CommandOptionData}).
     *
     * @return The option list.
     */
    public abstract List<? extends CommandOptionData> getOptions();

    /**
     * Add new option to the command options.
     *
     * @param type The option type.
     * @param name The option name.
     * @param description The option description.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties addOption(OptionType type, String name, String description);

    /**
     * Add new option to the command options.
     *
     * @param type The option type.
     * @param name The option name.
     * @param description The option description.
     * @param required Whether this option is required.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties addOption(OptionType type,
                                                      String name,
                                                      String description,
                                                      boolean required);

    /**
     * Add new option to the command options.
     *
     * @param type The option type.
     * @param name The option name.
     * @param description The option description.
     * @param required Whether this option is required.
     * @param autocomplete Whether this option support autocomplete.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties addOption(OptionType type,
                                                      String name,
                                                      String description,
                                                      boolean required,
                                                      boolean autocomplete);

    /**
     * Add {@link Collection} of {@link CommandOptionData} to this command.
     *
     * @param options {@link Collection} of {@link CommandOptionData}.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties addOptions(Collection<? extends CommandOptionData> options);

    /**
     * Add varargs of {@link CommandOptionData} to this command.
     *
     * @param options Varargs of {@link CommandOptionData}.
     * @return The {@link CommonCommandProperties} instance, for chaining.
     */
    public abstract CommonCommandProperties addOptions(CommandOptionData... options);
}
