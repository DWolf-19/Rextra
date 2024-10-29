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

import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;

/**
 * Interface defining additional APIs for normal slash-like (slash-runnable) commands.
 */
public interface SlashLikeCommandModel extends CommandModel {
    /**
     * Sets the {@link LocalizationFunction} for this command.
     * This allows to localize the entire command.
     * <br>
     * Only accepts
     * {@link net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction ResourceBundleLocalizationFunction}.
     *
     * @param localizationFunction The {@link LocalizationFunction}.
     * @return The {@link SlashLikeCommandModel} instance, for chaining.
     */
    SlashLikeCommandModel setLocalizationFunction(LocalizationFunction localizationFunction);
}
