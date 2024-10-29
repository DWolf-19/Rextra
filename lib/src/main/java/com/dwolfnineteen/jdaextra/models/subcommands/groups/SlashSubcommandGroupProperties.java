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
import com.dwolfnineteen.jdaextra.models.subcommands.SlashSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Slash subcommand group.
 *
 * @see SlashLikeSubcommandGroupProperties
 */
public class SlashSubcommandGroupProperties extends SlashLikeSubcommandGroupProperties {
    /**
     * {@link Map} of subcommand name and {@link SlashSubcommandProperties}.
     */
    protected final Map<String, SlashSubcommandProperties> subcommands;

    {
        subcommands = new HashMap<>();
    }

    /**
     * Construct new {@link SlashLikeSubcommandGroupProperties}.
     *
     * @param name The subcommand group name.
     * @param description The subcommand group description.
     */
    public SlashSubcommandGroupProperties(@NotNull String name, @NotNull String description) {
        super(name, description);
    }

    /**
     * {@inheritDoc}
     *
     * @param groupClass {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setGroupClass(@NotNull BaseSubcommandGroup groupClass) {
        this.groupClass = groupClass;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param name {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setName(@NotNull String name) {
        generalData.setName(name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param name {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setNameLocalization(@NotNull DiscordLocale locale,
                                                                       @NotNull String name) {
        generalData.setNameLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizations {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setNameLocalizations(@NotNull Map<DiscordLocale, String> localizations) {
        generalData.setNameLocalizations(localizations);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param description {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setDescription(@NotNull String description) {
        generalData.setDescription(description);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param locale {@inheritDoc}
     * @param name {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setDescriptionLocalization(@NotNull DiscordLocale locale,
                                                                              @NotNull String name) {
        generalData.setDescriptionLocalization(locale, name);

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @param localizations {@inheritDoc}
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties setDescriptionLocalizations(@NotNull Map<DiscordLocale, String> localizations) {
        generalData.setDescriptionLocalizations(localizations);

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
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties addSubcommands(@NotNull Collection<? extends SubcommandProperties> subcommands) {
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
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    @Override
    public @NotNull SlashSubcommandGroupProperties addSubcommands(@NotNull SubcommandProperties... subcommands) {
        this.subcommands.putAll(Arrays.stream(subcommands)
                .map(subcommand -> (SlashSubcommandProperties) subcommand)
                .collect(Collectors.toMap(SlashSubcommandProperties::getName, subcommand -> subcommand)));

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull SubcommandGroupData toGeneralSubcommandGroupData() {
        return generalData.addSubcommands(subcommands.values()
                .stream()
                .map(SlashSubcommandProperties::toGeneralSubcommandData)
                .collect(Collectors.toList()));
    }
}
