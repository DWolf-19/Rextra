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
package com.dwolfnineteen.jdaextra.models.subcommands.groups;

import com.dwolfnineteen.jdaextra.commands.subcommandgroups.BaseSubcommandGroup;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Subcommand group properties.
 */
public abstract class SubcommandGroupProperties {
    /**
     * The group class.
     */
    protected BaseSubcommandGroup groupClass;

    /**
     * The group class.
     *
     * @return The group class.
     */
    public @NotNull BaseSubcommandGroup getGroupClass() {
        return groupClass;
    }

    /**
     * Sets the command class.
     *
     * @param groupClass The group class.
     * @return The {@link SubcommandGroupProperties} instance, for chaining.
     */
    public abstract SubcommandGroupProperties setGroupClass(BaseSubcommandGroup groupClass);

    /**
     * The subcommand group name.
     *
     * @return The name.
     */
    public abstract String getName();

    /**
     * Sets the subcommand group name.
     *
     * @param name The name.
     * @return The {@link SubcommandGroupProperties} instance, for chaining.
     */
    public abstract SubcommandGroupProperties setName(String name);

    /**
     * The subcommand group description.
     *
     * @return The description.
     */
    public abstract String getDescription();

    /**
     * Sets the subcommand group description.
     *
     * @param description The description.
     * @return The {@link SubcommandGroupProperties} instance, for chaining.
     */
    public abstract SubcommandGroupProperties setDescription(String description);

    /**
     * {@link Map} of subcommand name and {@link SubcommandProperties}.
     *
     * @return {@link Map} of subcommand name and {@link SubcommandProperties}.
     */
    public abstract Map<String, ? extends SubcommandProperties> getSubcommandMap();

    /**
     * {@link List} of {@link SubcommandProperties}.
     *
     * @return {@link List} of {@link SubcommandProperties}.
     */
    public abstract List<? extends SubcommandProperties> getSubcommandList();

    /**
     * Add {@link Collection} of {@link SubcommandProperties} to subcommands of this command.
     *
     * @param subcommands {@link Collection} of {@link SubcommandProperties}.
     * @return The {@link SubcommandGroupProperties} instance, for chaining.
     */
    public abstract SubcommandGroupProperties addSubcommands(Collection<? extends SubcommandProperties> subcommands);

    /**
     * Add varargs of {@link SubcommandProperties} to subcommands of this command.
     *
     * @param subcommands Varargs of {@link SubcommandProperties}.
     * @return The {@link SubcommandGroupProperties} instance, for chaining.
     */
    public abstract SubcommandGroupProperties addSubcommands(SubcommandProperties... subcommands);
}
