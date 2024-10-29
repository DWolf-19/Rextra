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
import com.dwolfnineteen.jdaextra.commands.SlashCommand;
import com.dwolfnineteen.jdaextra.models.CommonSlashCommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SlashSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SlashSubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.SlashOptionData;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
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
 * Slash command model.
 *
 * @see CommandModel
 */
public class SlashCommandModel extends CommonSlashCommandProperties implements SlashLikeCommandModel {
    /**
     * The command class inherited from {@link SlashCommand}.
     */
    protected SlashCommand command;
    /**
     * Regular {@link SlashCommandData} from JDA.
     */
    protected final SlashCommandData regularData;
    /**
     * {@link List} of command options.
     */
    protected final List<SlashOptionData> options;
    /**
     * {@link Map} of subcommand name and {@link SlashSubcommandProperties}.
     */
    protected final Map<String, SlashSubcommandProperties> subcommands;
    /**
     * {@link Map} of subcommand name and {@link SlashSubcommandGroupProperties}.
     */
    protected final Map<String, SlashSubcommandGroupProperties> subcommandGroups;

    {
        options = new ArrayList<>();
        subcommands = new HashMap<>();
        subcommandGroups = new HashMap<>();
    }

    /**
     * Construct new {@link SlashCommandModel}.
     *
     * @param name The command name.
     * @param description The command description.
     * @param command The command class.
     */
    public SlashCommandModel(@NotNull SlashCommand command, @NotNull String name, @NotNull String description) {
        this.command = command;
        regularData = Commands.slash(name, description);
    }

    /**
     * The command class inherited from {@link SlashCommand}.
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull SlashCommand getCommand() {
        return command;
    }

    /**
     * Sets the command class. This method expects {@link SlashCommand}!
     *
     * @param command {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setCommand(@NotNull BaseCommand command) {
        this.command = (SlashCommand) command;

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
     * {@inheritDoc}
     *
     * @param entryPoint {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setEntryPoint(@Nullable Method entryPoint) {
        this.entryPoint = entryPoint;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return regularData.getName();
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setName(@NotNull String name) {
        regularData.setName(name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull LocalizationMap getNameLocalizations() {
        return regularData.getNameLocalizations();
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param name {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setNameLocalization(@NotNull DiscordLocale locale, @NotNull String name) {
        regularData.setNameLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param nameLocalizations {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setNameLocalizations(@NotNull Map<DiscordLocale, String> nameLocalizations) {
        regularData.setNameLocalizations(nameLocalizations);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull String getDescription() {
        return regularData.getDescription();
    }

    /**
     * Sets the command description.
     *
     * @param description The command description.
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setDescription(@NotNull String description) {
        regularData.setDescription(description);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull LocalizationMap getDescriptionLocalizations() {
        return regularData.getDescriptionLocalizations();
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param name {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setDescriptionLocalization(@NotNull DiscordLocale locale, @NotNull String name) {
        regularData.setDescriptionLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param descriptionLocalizations {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setDescriptionLocalizations(@NotNull Map<DiscordLocale, String> descriptionLocalizations) {
        regularData.setDescriptionLocalizations(descriptionLocalizations);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizationFunction {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setLocalizationFunction(@NotNull LocalizationFunction localizationFunction) {
        regularData.setLocalizationFunction(localizationFunction);

        return this;
    }

    /**
     * All command options as a {@link List}.
     *
     * @return The command options.
     */
    @Override
    public @NotNull List<SlashOptionData> getOptions() {
        return options;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addOption(@NotNull OptionType type,
                                                @NotNull String name,
                                                @NotNull String description) {
        options.add(new SlashOptionData(type, name, description));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @param required {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addOption(@NotNull OptionType type,
                                                @NotNull String name,
                                                @NotNull String description,
                                                boolean required) {
        options.add(new SlashOptionData(type, name, description, required));

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
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addOption(@NotNull OptionType type,
                                                @NotNull String name,
                                                @NotNull String description,
                                                boolean required,
                                                boolean autocomplete) {
        options.add(new SlashOptionData(type, name, description, required, autocomplete));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link SlashOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addOptions(@NotNull Collection<? extends CommandOptionData> options) {
        this.options.addAll(options.stream()
                .map(option -> (SlashOptionData) option)
                .collect(Collectors.toList()));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link SlashOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addOptions(@NotNull CommandOptionData... options) {
        this.options.addAll(Arrays.stream(options)
                .map(option -> (SlashOptionData) option)
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
        return regularData.isGuildOnly();
    }

    /**
     * {@inheritDoc}
     *
     * @param guildOnly {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setGuildOnly(boolean guildOnly) {
        regularData.setGuildOnly(guildOnly);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNsfw() {
        return regularData.isNSFW();
    }

    /**
     * {@inheritDoc}
     *
     * @param nsfw {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel setNsfw(boolean nsfw) {
        regularData.setNSFW(nsfw);

        return this;
    }

    /**
     * {@link Map} of subcommand name and {@link SlashSubcommandProperties}.
     *
     * @return {@link Map} of subcommand name and {@link SlashSubcommandProperties}.
     */
    @Override
    public @NotNull Map<String, SlashSubcommandProperties> getSubcommandMap() {
        return subcommands;
    }

    /**
     * {@link List} of {@link SlashSubcommandProperties}.
     *
     * @return {@link List} of {@link SlashSubcommandProperties}.
     */
    @Override
    public @NotNull List<SlashSubcommandProperties> getSubcommandList() {
        return new ArrayList<>(subcommands.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link SlashSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addSubcommands(@NotNull Collection<? extends SubcommandProperties> subcommands) {
        this.subcommands.putAll(subcommands.stream()
                .map(subcommand -> (SlashSubcommandProperties) subcommand)
                .collect(Collectors.toMap(SlashSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link SlashSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addSubcommands(@NotNull SubcommandProperties... subcommands) {
        this.subcommands.putAll(Arrays.stream(subcommands)
                .map(subcommand -> (SlashSubcommandProperties) subcommand)
                .collect(Collectors.toMap(SlashSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@link Map} of subcommand group name and {@link SlashSubcommandGroupProperties}.
     *
     * @return {@link Map} of subcommand group name and {@link SlashSubcommandGroupProperties}.
     */
    @Override
    public @NotNull Map<String, SlashSubcommandGroupProperties> getSubcommandGroupMap() {
        return subcommandGroups;
    }

    /**
     * {@link List} of {@link SlashSubcommandGroupProperties}.
     *
     * @return {@link List} of {@link SlashSubcommandGroupProperties}.
     */
    @Override
    public @NotNull List<SlashSubcommandGroupProperties> getSubcommandGroupList() {
        return new ArrayList<>(subcommandGroups.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link SlashSubcommandGroupProperties}!
     *
     * @param subcommandGroups {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addSubcommandGroups(@NotNull Collection<? extends SubcommandGroupProperties> subcommandGroups) {
        this.subcommandGroups.putAll(subcommandGroups.stream()
                .map(group -> (SlashSubcommandGroupProperties) group)
                .collect(Collectors.toMap(SlashSubcommandGroupProperties::getName, group -> group)));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link SlashSubcommandGroupProperties}!
     *
     * @param subcommandGroups {@inheritDoc}
     * @return The {@link SlashCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandModel addSubcommandGroups(@NotNull SubcommandGroupProperties... subcommandGroups) {
        this.subcommandGroups.putAll(Arrays.stream(subcommandGroups)
                .map(group -> (SlashSubcommandGroupProperties) group)
                .collect(Collectors.toMap(SlashSubcommandGroupProperties::getName, group -> group)));

        return this;
    }
}
