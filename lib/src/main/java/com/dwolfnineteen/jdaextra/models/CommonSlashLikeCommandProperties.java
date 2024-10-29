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
package com.dwolfnineteen.jdaextra.models;

import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationMap;

import java.util.Map;

/**
 * Base class for all slash-like (slash-runnable) commands.
 */
public abstract class CommonSlashLikeCommandProperties extends CommonCommandProperties {
    /**
     * Localizations of the command name.
     *
     * @return Mapping from {@link DiscordLocale} to translated {@link String}.
     */
    public abstract LocalizationMap getNameLocalizations();

    /**
     * Localizations of the command description.
     *
     * @return Mapping from {@link DiscordLocale} to translated {@link String}.
     */
    public abstract LocalizationMap getDescriptionLocalizations();

    /**
     * Sets localization of the command name.
     *
     * @param locale The {@link DiscordLocale}.
     * @param name The translated {@link String}.
     * @return The {@link CommonSlashLikeCommandProperties} instance, for chaining.
     */
    public abstract CommonSlashLikeCommandProperties setNameLocalization(DiscordLocale locale, String name);

    /**
     * Sets multiple localizations of the command name.
     *
     * @param localizations {@link Map} of {@link DiscordLocale} and translated {@link String}s.
     * @return The {@link CommonSlashLikeCommandProperties} instance, for chaining.
     */
    public abstract CommonSlashLikeCommandProperties setNameLocalizations(Map<DiscordLocale, String> localizations);

    /**
     * Sets localization of the command description.
     *
     * @param locale The {@link DiscordLocale}.
     * @param name The translated {@link String}.
     * @return The {@link CommonSlashLikeCommandProperties} instance, for chaining.
     */
    public abstract CommonSlashLikeCommandProperties setDescriptionLocalization(DiscordLocale locale, String name);

    /**
     * Sets multiple localizations of the command description.
     *
     * @param localizations {@link Map} of {@link DiscordLocale} and translated {@link String}s.
     * @return The {@link CommonSlashLikeCommandProperties} instance, for chaining.
     */
    public abstract CommonSlashLikeCommandProperties setDescriptionLocalizations(Map<DiscordLocale, String> localizations);
}
