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
package com.dwolfnineteen.jdaextra.builders;

import com.dwolfnineteen.jdaextra.annotations.commands.CommandLocalizationFunction;
import com.dwolfnineteen.jdaextra.commands.BaseCommand;
import com.dwolfnineteen.jdaextra.commands.SlashLikeCommand;
import com.dwolfnineteen.jdaextra.models.commands.CommandModel;
import com.dwolfnineteen.jdaextra.models.commands.SlashLikeCommandModel;
import net.dv8tion.jda.api.interactions.commands.localization.LocalizationFunction;
import net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction;
import org.jetbrains.annotations.NotNull;

/**
 * Base builder for slash-like (slash-runnable) commands.
 */
public abstract class SlashLikeCommandBuilder extends CommandBuilder {
    /**
     * Construct new {@link SlashLikeCommandBuilder}.
     *
     * @param command The command class.
     */
    protected SlashLikeCommandBuilder(@NotNull SlashLikeCommand command) {
        super(command);
    }

    /**
     * Build full command localization.
     *
     * @param model The command model.
     * @param clazz The command class.
     * @return Configured command model.
     */
    protected @NotNull SlashLikeCommandModel buildLocalization(@NotNull CommandModel model,
                                                               @NotNull Class<? extends BaseCommand> clazz) {
        return ((SlashLikeCommandModel) super.buildSettings(model, clazz))
                .setLocalizationFunction(buildLocalizationFunction(clazz));
    }

    /**
     * Build localization function.
     *
     * @param clazz The command class.
     * @return The {@link LocalizationFunction}.
     */
    protected @NotNull LocalizationFunction buildLocalizationFunction(@NotNull Class<? extends BaseCommand> clazz) {
        CommandLocalizationFunction annotation = clazz.getAnnotation(CommandLocalizationFunction.class);

        return annotation == null
                ? ResourceBundleLocalizationFunction.empty().build()
                : ResourceBundleLocalizationFunction.fromBundles(annotation.baseName(),
                annotation.locales()).build();
    }
}
