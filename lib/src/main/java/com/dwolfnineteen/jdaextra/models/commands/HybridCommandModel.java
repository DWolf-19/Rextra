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
import com.dwolfnineteen.jdaextra.commands.HybridCommand;
import com.dwolfnineteen.jdaextra.models.CommonHybridCommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.HybridSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.HybridSubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.HybridOptionData;
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
 * Hybrid command model.
 *
 * @see CommandModel
 */
public class HybridCommandModel extends CommonHybridCommandProperties implements SlashLikeCommandModel {
    /**
     * The command class inherited from {@link HybridCommand}.
     */
    protected HybridCommand command;
    /**
     * Regular {@link SlashCommandData} from JDA.
     */
    protected final SlashCommandData regularData;
    /**
     * {@link List} of command options.
     */
    protected final List<HybridOptionData> options;
    /**
     * {@link Map} of subcommand name and {@link HybridSubcommandProperties}.
     */
    protected final Map<String, HybridSubcommandProperties> subcommands;
    /**
     * {@link Map} of subcommand name and {@link HybridSubcommandGroupProperties}.
     */
    protected final Map<String, HybridSubcommandGroupProperties> subcommandGroups;

    {
        options = new ArrayList<>();
        subcommands = new HashMap<>();
        subcommandGroups = new HashMap<>();
    }

    /**
     * Construct new {@link HybridCommandModel}.
     *
     * @param name The command name.
     * @param description The command description.
     * @param command The command class.
     */
    public HybridCommandModel(@NotNull HybridCommand command, @NotNull String name, @NotNull String description) {
        this.command = command;
        regularData = Commands.slash(name, description);
    }

    /**
     * The command class inherited from {@link HybridCommand}.
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull HybridCommand getCommand() {
        return command;
    }

    /**
     * {@inheritDoc}
     *
     * @param command {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setCommand(@NotNull BaseCommand command) {
        this.command = (HybridCommand) command;

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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setEntryPoint(@Nullable Method entryPoint) {
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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setName(@NotNull String name) {
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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setNameLocalization(@NotNull DiscordLocale locale, @NotNull String name) {
        regularData.setNameLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param nameLocalizations {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setNameLocalizations(@NotNull Map<DiscordLocale, String> nameLocalizations) {
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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setDescription(@NotNull String description) {
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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setDescriptionLocalization(@NotNull DiscordLocale locale, @NotNull String name) {
        regularData.setDescriptionLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param descriptionLocalizations {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setDescriptionLocalizations(@NotNull Map<DiscordLocale, String> descriptionLocalizations) {
        regularData.setDescriptionLocalizations(descriptionLocalizations);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizationFunction {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setLocalizationFunction(@NotNull LocalizationFunction localizationFunction) {
        regularData.setLocalizationFunction(localizationFunction);

        return this;
    }

    /**
     * The command options ({@link List} of {@link HybridOptionData}).
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull List<HybridOptionData> getOptions() {
        return options;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addOption(@NotNull OptionType type,
                                                 @NotNull String name,
                                                 @NotNull String description) {
        options.add(new HybridOptionData(type, name, description));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @param required {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addOption(@NotNull OptionType type,
                                                 @NotNull String name,
                                                 @NotNull String description,
                                                 boolean required) {
        options.add(new HybridOptionData(type, name, description, required));

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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addOption(@NotNull OptionType type,
                                                 @NotNull String name,
                                                 @NotNull String description,
                                                 boolean required,
                                                 boolean autocomplete) {
        options.add(new HybridOptionData(type, name, description, required, autocomplete));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link HybridOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addOptions(@NotNull Collection<? extends CommandOptionData> options) {
        this.options.addAll(options.stream()
                .map(option -> (HybridOptionData) option)
                .collect(Collectors.toList()));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link HybridOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addOptions(@NotNull CommandOptionData... options) {
        this.options.addAll(Arrays.stream(options)
                .map(option -> (HybridOptionData) option)
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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setGuildOnly(boolean guildOnly) {
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
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel setNsfw(boolean nsfw) {
        regularData.setNSFW(nsfw);

        return this;
    }

    /**
     * {@link Map} of subcommand name and {@link HybridSubcommandProperties}.
     *
     * @return {@link Map} of subcommand name and {@link HybridSubcommandProperties}.
     */
    @Override
    public @NotNull Map<String, HybridSubcommandProperties> getSubcommandMap() {
        return subcommands;
    }

    /**
     * {@link List} of {@link HybridSubcommandProperties}.
     *
     * @return {@link List} of {@link HybridSubcommandProperties}.
     */
    @Override
    public @NotNull List<HybridSubcommandProperties> getSubcommandList() {
        return new ArrayList<>(subcommands.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link HybridSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addSubcommands(@NotNull Collection<? extends SubcommandProperties> subcommands) {
        this.subcommands.putAll(subcommands.stream()
                .map(subcommand -> (HybridSubcommandProperties) subcommand)
                .collect(Collectors.toMap(HybridSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link HybridSubcommandProperties}!
     *
     * @param subcommands {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addSubcommands(@NotNull SubcommandProperties... subcommands) {
        this.subcommands.putAll(Arrays.stream(subcommands)
                .map(subcommand -> (HybridSubcommandProperties) subcommand)
                .collect(Collectors.toMap(HybridSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@link Map} of subcommand group name and {@link HybridSubcommandGroupProperties}.
     *
     * @return {@link Map} of subcommand group name and {@link HybridSubcommandGroupProperties}.
     */
    @Override
    public @NotNull Map<String, HybridSubcommandGroupProperties> getSubcommandGroupMap() {
        return subcommandGroups;
    }

    /**
     * {@link List} of {@link HybridSubcommandGroupProperties}.
     *
     * @return {@link List} of {@link HybridSubcommandGroupProperties}.
     */
    @Override
    public @NotNull List<HybridSubcommandGroupProperties> getSubcommandGroupList() {
        return new ArrayList<>(subcommandGroups.values());
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link HybridSubcommandGroupProperties}!
     *
     * @param subcommandGroups {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addSubcommandGroups(@NotNull Collection<? extends SubcommandGroupProperties> subcommandGroups) {
        this.subcommandGroups.putAll(subcommandGroups.stream()
                .map(group -> (HybridSubcommandGroupProperties) group)
                .collect(Collectors.toMap(HybridSubcommandGroupProperties::getName, group -> group)));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects varargs of {@link HybridSubcommandGroupProperties}!
     *
     * @param subcommandGroups {@inheritDoc}
     * @return The {@link HybridCommandModel} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandModel addSubcommandGroups(@NotNull SubcommandGroupProperties... subcommandGroups) {
        this.subcommandGroups.putAll(Arrays.stream(subcommandGroups)
                .map(group -> (HybridSubcommandGroupProperties) group)
                .collect(Collectors.toMap(HybridSubcommandGroupProperties::getName, group -> group)));

        return this;
    }
}
