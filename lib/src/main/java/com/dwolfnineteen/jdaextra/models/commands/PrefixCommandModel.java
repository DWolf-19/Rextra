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
package com.dwolfnineteen.jdaextra.models.commands;

import com.dwolfnineteen.jdaextra.commands.BaseCommand;
import com.dwolfnineteen.jdaextra.commands.PrefixCommand;
import com.dwolfnineteen.jdaextra.models.CommonPrefixCommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.PrefixSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.PrefixSubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.PrefixOptionData;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Prefix command model.
 *
 * @see CommandModel CommandModel
 */
public class PrefixCommandModel extends CommonPrefixCommandProperties implements CommandModel {
    /**
     * The command class inherited from {@link PrefixCommand}.
     */
    protected PrefixCommand command;
    /**
     * Whether this command can be executed only on guilds.
     */
    protected boolean guildOnly;
    /**
     * Whether this command can be executed only in NSFW channels.
     */
    protected boolean nsfw;
    /**
     * {@link List} of command options.
     */
    protected final List<PrefixOptionData> options;
    /**
     * {@link Map} of subcommand name and {@link PrefixSubcommandProperties}.
     */
    protected final Map<String, PrefixSubcommandProperties> subcommands;
    /**
     * {@link Map} of subcommand name and {@link PrefixSubcommandGroupProperties}.
     */
    protected final Map<String, PrefixSubcommandGroupProperties> subcommandGroups;

    {
        options = new ArrayList<>();
        subcommands = new HashMap<>();
        subcommandGroups = new HashMap<>();
    }

    /**
     * Construct new {@link PrefixCommandModel}.
     *
     * @param command The command class.
     * @param name The command name.
     */
    public PrefixCommandModel(@NotNull PrefixCommand command, @NotNull String name) {
        this.command = command;
        this.name = name;
    }

    /**
     * Construct new {@link PrefixCommandModel}.
     *
     * @param command The command class.
     * @param name The command name.
     * @param description The command description.
     */
    public PrefixCommandModel(@NotNull PrefixCommand command, @NotNull String name, @Nullable String description) {
        this.command = command;
        this.name = name;
        this.description = description;
    }

    /**
     * The command class inherited from {@link PrefixCommand}.
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull PrefixCommand getCommand() {
        return command;
    }

    /**
     * {@inheritDoc}
     *
     * @param command {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel setCommand(@NotNull BaseCommand command) {
        this.command = (PrefixCommand) command;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public @Nullable Method getEntryPoint() {
        return entryPoint;
    }

    /**
     * @param entryPoint The command entry point.
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel setEntryPoint(Method entryPoint) {
        this.entryPoint = entryPoint;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel setName(@NotNull String name) {
        this.name = name;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param description {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel setDescription(@Nullable String description) {
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
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addOption(@NotNull OptionType type, @NotNull String name, @Nullable String description) {
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
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addOption(@NotNull OptionType type,
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
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addOption(@NotNull OptionType type,
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
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addOptions(@NotNull Collection<? extends CommandOptionData> options) {
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
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addOptions(@NotNull CommandOptionData... options) {
        this.options.addAll(Arrays.stream(options)
                .map(option -> (PrefixOptionData) option)
                .collect(Collectors.toList()));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isGuildOnly() {
        return guildOnly;
    }

    /**
     * {@inheritDoc}
     *
     * @param guildOnly {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel setGuildOnly(boolean guildOnly) {
        this.guildOnly = guildOnly;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNsfw() {
        return nsfw;
    }

    /**
     * {@inheritDoc}
     *
     * @param nsfw {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel setNsfw(boolean nsfw) {
        this.nsfw = nsfw;

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
    public @NotNull List<PrefixSubcommandProperties> getSubcommandList() {
        return new ArrayList<>(subcommands.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link PrefixSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addSubcommands(@NotNull Collection<? extends SubcommandProperties> subcommands) {
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
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addSubcommands(@NotNull SubcommandProperties... subcommands) {
        this.subcommands.putAll(Arrays.stream(subcommands)
                .map(subcommand -> (PrefixSubcommandProperties) subcommand)
                .collect(Collectors.toMap(PrefixSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@link Map} of subcommand group name and {@link PrefixSubcommandGroupProperties}.
     *
     * @return {@link Map} of subcommand group name and {@link PrefixSubcommandGroupProperties}.
     */
    @Override
    public Map<String, PrefixSubcommandGroupProperties> getSubcommandGroupMap() {
        return subcommandGroups;
    }

    /**
     * {@link List} of {@link PrefixSubcommandGroupProperties}.
     *
     * @return {@link List} of {@link PrefixSubcommandGroupProperties}.
     */
    @Override
    public List<PrefixSubcommandGroupProperties> getSubcommandGroupList() {
        return new ArrayList<>(subcommandGroups.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link PrefixSubcommandGroupProperties}!
     *
     * @param subcommandGroups {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addSubcommandGroups(@NotNull Collection<? extends SubcommandGroupProperties> subcommandGroups) {
        this.subcommandGroups.putAll(subcommandGroups.stream()
                .map(group -> (PrefixSubcommandGroupProperties) group)
                .collect(Collectors.toMap(PrefixSubcommandGroupProperties::getName, group -> group)));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link PrefixSubcommandGroupProperties}!
     *
     * @param subcommandGroups {@inheritDoc}
     * @return The {@link PrefixCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandModel addSubcommandGroups(@NotNull SubcommandGroupProperties... subcommandGroups) {
        this.subcommandGroups.putAll(Arrays.stream(subcommandGroups)
                .map(group -> (PrefixSubcommandGroupProperties) group)
                .collect(Collectors.toMap(PrefixSubcommandGroupProperties::getName, group -> group)));

        return this;
    }
}
