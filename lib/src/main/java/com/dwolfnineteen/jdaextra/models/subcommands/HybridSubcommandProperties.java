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

import com.dwolfnineteen.jdaextra.models.CommonHybridCommandProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.HybridOptionData;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: Add missing annotations
/**
 * Hybrid subcommand properties.
 * 
 * @see SubcommandProperties
 */
public class HybridSubcommandProperties extends CommonHybridCommandProperties implements SlashLikeSubcommandProperties {
    /**
     * Regular {@link SubcommandData} from JDA.
     */
    protected final SubcommandData regularData;
    /**
     * {@link List} of command options.
     */
    protected final List<HybridOptionData> options;

    {
        options = new ArrayList<>();
    }

    /**
     * Construct new {@link HybridSubcommandProperties}.
     *
     * @param entryPoint The command entry point.
     * @param name The command name.
     * @param description The command description.
     */
    public HybridSubcommandProperties(@NotNull Method entryPoint, @NotNull String name, @NotNull String description) {
        this.entryPoint = entryPoint;
        regularData = new SubcommandData(name, description);
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
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setEntryPoint(@NotNull Method entryPoint) {
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
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setName(@NotNull String name) {
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
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setNameLocalization(@NotNull DiscordLocale locale,
                                                                   @NotNull String name) {
        regularData.setNameLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizations {@inheritDoc}
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setNameLocalizations(@NotNull Map<DiscordLocale, String> localizations) {
        regularData.setNameLocalizations(localizations);

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
     * {@inheritDoc}
     *
     * @param description {@inheritDoc}
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setDescription(@NotNull String description) {
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
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setDescriptionLocalization(@NotNull DiscordLocale locale,
                                                                          @NotNull String name) {
        regularData.setDescriptionLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizations {@inheritDoc}
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties setDescriptionLocalizations(@NotNull Map<DiscordLocale, String> localizations) {
        regularData.setDescriptionLocalizations(localizations);

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
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties addOption(@NotNull OptionType type,
                                                         @NotNull String name,
                                                         @NotNull String description) {
        regularData.addOption(type, name, description);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @param required {@inheritDoc}
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties addOption(@NotNull OptionType type,
                                                         @NotNull String name,
                                                         @NotNull String description,
                                                         boolean required) {
        regularData.addOption(type, name, description, required);

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
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties addOption(@NotNull OptionType type,
                                                         @NotNull String name,
                                                         @NotNull String description,
                                                         boolean required,
                                                         boolean autocomplete) {
        regularData.addOption(type, name, description, required, autocomplete);

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link HybridOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties addOptions(@NotNull Collection<? extends CommandOptionData> options) {
        this.options.addAll(options.stream()
                .map(option -> (HybridOptionData) option)
                .collect(Collectors.toList()));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link HybridOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link HybridSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull HybridSubcommandProperties addOptions(@NotNull CommandOptionData... options) {
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
    public @NotNull SubcommandData toGeneralSubcommandData() {
        return regularData.addOptions(options.stream()
                .map(HybridOptionData::toGeneralOptionData)
                .collect(Collectors.toList()));
    }
}
