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
import com.dwolfnineteen.jdaextra.models.subcommands.PrefixSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Prefix subcommand group.
 *
 * @see SubcommandGroupProperties
 */
public class PrefixSubcommandGroupProperties extends SubcommandGroupProperties {
    /**
     * The command name.
     */
    protected String name;
    /**
     * The command description.
     */
    protected String description;
    /**
     * {@link List} of subcommands.
     */
    protected final Map<String, PrefixSubcommandProperties> subcommands;

    {
        subcommands = new HashMap<>();
    }

    /**
     * Construct new {@link PrefixSubcommandGroupProperties}.
     *
     * @param name The subcommand group name.
     */
    public PrefixSubcommandGroupProperties(@NotNull String name) {
        this.name = name;
    }

    /**
     * Construct new {@link PrefixSubcommandGroupProperties}.
     *
     * @param name The subcommand group name.
     * @param description The subcommand group description.
     */
    public PrefixSubcommandGroupProperties(@NotNull String name, @Nullable String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * @param groupClass {@inheritDoc}
     * @return The {@link PrefixSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandGroupProperties setGroupClass(@NotNull BaseSubcommandGroup groupClass) {
        this.groupClass = groupClass;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return The {@link PrefixSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandGroupProperties setName(@NotNull String name) {
        this.name = name;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @Nullable String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     * 
     * @param description {@inheritDoc}
     * @return The {@link PrefixSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandGroupProperties setDescription(@Nullable String description) {
        this.description = description;

        return this;
    }

    /**
     * {@link Map} of subcommand name and {@link PrefixSubcommandProperties}.
     *
     * @return {@link Map} of subcommand name and {@link PrefixSubcommandProperties}.
     */
    @Override
    public @NotNull Map<String, PrefixSubcommandProperties> getSubcommandMap() {
        return subcommands;
    }

    /**
     * {@link List} of {@link PrefixSubcommandProperties}.
     *
     * @return {@link List} of {@link PrefixSubcommandProperties}.
     */
    @Override
    public List<PrefixSubcommandProperties> getSubcommandList() {
        return new ArrayList<>(subcommands.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link PrefixSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link PrefixSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandGroupProperties addSubcommands(@NotNull Collection<? extends SubcommandProperties> subcommands) {
        this.subcommands.putAll(subcommands.stream()
                .map(subcommand -> (PrefixSubcommandProperties) subcommand)
                .collect(Collectors.toMap(PrefixSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link PrefixSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link PrefixSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandGroupProperties addSubcommands(@NotNull SubcommandProperties... subcommands) {
        this.subcommands.putAll(Arrays.stream(subcommands)
                .map(subcommand -> (PrefixSubcommandProperties) subcommand)
                .collect(Collectors.toMap(PrefixSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }
}
