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

import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Slash-like (slash-runnable) subcommand group properties.
 *
 * @see SubcommandGroupProperties
 */
public abstract class SlashLikeSubcommandGroupProperties extends SubcommandGroupProperties implements GeneralSubcommandGroupData {
    /**
     * Regular {@link SubcommandGroupData} from JDA.
     */
    protected final SubcommandGroupData generalData;

    /**
     * Construct new {@link SlashLikeSubcommandGroupProperties}.
     *
     * @param name The subcommand group name.
     * @param description The subcommand group description.
     */
    protected SlashLikeSubcommandGroupProperties(@NotNull String name, @NotNull String description) {
        generalData = new SubcommandGroupData(name, description);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return generalData.getName();
    }

    /**
     * Localizations of the subcommand group name.
     *
     * @return Mapping from {@link DiscordLocale} to translated {@link String}.
     */
    public @NotNull LocalizationMap getNameLocalization() {
        return generalData.getNameLocalizations();
    }

    /**
     * Sets localization of the command name.
     *
     * @param locale The {@link DiscordLocale}.
     * @param name The translated {@link String}.
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    public abstract SlashLikeSubcommandGroupProperties setNameLocalization(DiscordLocale locale, String name);

    /**
     * Sets multiple localizations of the subcommand group name.
     *
     * @param localizations {@link Map} of {@link DiscordLocale} and translated {@link String}s.
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    public abstract SlashLikeSubcommandGroupProperties setNameLocalizations(Map<DiscordLocale, String> localizations);

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull String getDescription() {
        return generalData.getDescription();
    }

    /**
     * Localizations of the subcommand group description.
     *
     * @return Mapping from {@link DiscordLocale} to translated {@link String}.
     */
    public @NotNull LocalizationMap getDescriptionLocalizations() {
        return generalData.getDescriptionLocalizations();
    }

    /**
     * Sets localization of the subcommand group description.
     *
     * @param locale The {@link DiscordLocale}.
     * @param name The translated {@link String}.
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    public abstract SlashLikeSubcommandGroupProperties setDescriptionLocalization(DiscordLocale locale, String name);

    /**
     * Sets multiple localizations of the subcommand group description.
     *
     * @param localizations {@link Map} of {@link DiscordLocale} and translated {@link String}s.
     * @return The {@link SlashLikeSubcommandGroupProperties} instance, for chaining.
     */
    public abstract SlashLikeSubcommandGroupProperties setDescriptionLocalizations(Map<DiscordLocale, String> localizations);
}
