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

import com.dwolfnineteen.jdaextra.models.CommonSlashCommandProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.SlashOptionData;
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

/**
 * Slash subcommand properties.
 *
 * @see SubcommandProperties
 */
public class SlashSubcommandProperties extends CommonSlashCommandProperties implements SlashLikeSubcommandProperties {
    /**
     * Regular {@link SubcommandData} from JDA.
     */
    protected final SubcommandData regularData;
    /**
     * {@link List} of command options.
     */
    protected final List<SlashOptionData> options;

    {
        options = new ArrayList<>();
    }

    /**
     * Construct new {@link SlashSubcommandProperties}.
     *
     * @param entryPoint The command entry point.
     * @param name The command name.
     * @param description The command description.
     */
    public SlashSubcommandProperties(@NotNull Method entryPoint, @NotNull String name, @NotNull String description) {
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setEntryPoint(@NotNull Method entryPoint) {
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setName(@NotNull String name) {
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setNameLocalization(@NotNull DiscordLocale locale, @NotNull String name) {
        regularData.setNameLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizations {@inheritDoc}
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setNameLocalizations(@NotNull Map<DiscordLocale, String> localizations) {
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setDescription(@NotNull String description) {
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setDescriptionLocalization(@NotNull DiscordLocale locale,
                                                                         @NotNull String name) {
        regularData.setDescriptionLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizations {@inheritDoc}
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties setDescriptionLocalizations(@NotNull Map<DiscordLocale, String> localizations) {
        regularData.setDescriptionLocalizations(localizations);

        return this;
    }

    /**
     * The subcommand options ({@link List} of {@link SlashOptionData}).
     *
     * @return {@inheritDoc}
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties addOption(@NotNull OptionType type,
                                                        @NotNull String name,
                                                        @NotNull String description) {
        this.options.add(new SlashOptionData(type, name, description));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param type {@inheritDoc}
     * @param name {@inheritDoc}
     * @param description {@inheritDoc}
     * @param required {@inheritDoc}
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties addOption(@NotNull OptionType type,
                                                        @NotNull String name,
                                                        @NotNull String description,
                                                        boolean required) {
        this.options.add(new SlashOptionData(type, name, description, required));

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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties addOption(@NotNull OptionType type,
                                                        @NotNull String name,
                                                        @NotNull String description,
                                                        boolean required,
                                                        boolean autocomplete) {
        this.options.add(new SlashOptionData(type, name, description, required, autocomplete));

        return this;
    }

    /**
     * {@inheritDoc}
     * This method expects {@link Collection} of {@link SlashOptionData}!
     *
     * @param options {@inheritDoc}
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties addOptions(@NotNull Collection<? extends CommandOptionData> options) {
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
     * @return The {@link SlashSubcommandProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandProperties addOptions(@NotNull CommandOptionData... options) {
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
    public @NotNull SubcommandData toGeneralSubcommandData() {
        return regularData.addOptions(options.stream()
                .map(SlashOptionData::toGeneralOptionData)
                .collect(Collectors.toList()));
    }
}
