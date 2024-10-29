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
package com.dwolfnineteen.jdaextra.models.subcommands;

import com.dwolfnineteen.jdaextra.models.CommonPrefixCommandProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.PrefixOptionData;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Prefix subcommand properties.
 *
 * @see SubcommandProperties
 */
public class PrefixSubcommandProperties extends CommonPrefixCommandProperties implements SubcommandProperties {
    /**
     * {@link List} of command options.
     */
    protected final List<PrefixOptionData> options;

    {
        options = new ArrayList<>();
    }

    /**
     * Construct new {@link PrefixSubcommandProperties}.
     *
     * @param entryPoint The command entry point.
     * @param name The command name.
     */
    public PrefixSubcommandProperties(@NotNull Method entryPoint, @NotNull String name) {
        this.entryPoint = entryPoint;
        this.name = name;
    }

    /**
     * Construct new {@link PrefixSubcommandProperties}.
     *
     * @param entryPoint The command entry point.
     * @param name The command name.
     * @param description The command description.
     */
    public PrefixSubcommandProperties(@NotNull Method entryPoint, @NotNull String name, @Nullable String description) {
        this.entryPoint = entryPoint;
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public @NotNull Method getEntryPoint() {
        return entryPoint;
    }

    /**
     * {@inheritDoc}
     *
     * @param entryPoint {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties setEntryPoint(@NotNull Method entryPoint) {
        this.entryPoint = entryPoint;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties setName(@NotNull String name) {
        this.name = name;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param description {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties setDescription(@Nullable String description) {
        this.description = description;

        return this;
    }

    /**
     * The command options ({@link List} of {@link PrefixOptionData}).
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull List<PrefixOptionData> getOptions() {
        return options;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties addOption(@NotNull OptionType type,
                                                         @NotNull String name,
                                                         @Nullable String description) {
        options.add(new PrefixOptionData(type, name, description));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @param required {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties addOption(@NotNull OptionType type,
                                                         @NotNull String name,
                                                         @Nullable String description,
                                                         boolean required) {
        options.add(new PrefixOptionData(type, name, description, required));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @param required {@inheritDoc}
     * @param autocomplete {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties addOption(@NotNull OptionType type,
                                                         @NotNull String name,
                                                         @Nullable String description,
                                                         boolean required,
                                                         boolean autocomplete) {
        options.add(new PrefixOptionData(type, name, description, required, autocomplete));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link PrefixOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties addOptions(@NotNull Collection<? extends CommandOptionData> options) {
        this.options.addAll(options.stream()
                .map(option -> (PrefixOptionData) option)
                .collect(Collectors.toList()));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link PrefixOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link PrefixSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull PrefixSubcommandProperties addOptions(@NotNull CommandOptionData... options) {
        this.options.addAll(Arrays.stream(options)
                .map(option -> (PrefixOptionData) option)
                .collect(Collectors.toList()));

        return this;
    }
}
