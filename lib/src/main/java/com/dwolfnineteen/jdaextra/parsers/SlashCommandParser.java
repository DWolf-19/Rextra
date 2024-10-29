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
package com.dwolfnineteen.jdaextra.parsers;

import com.dwolfnineteen.jdaextra.JDAExtra;
import com.dwolfnineteen.jdaextra.events.SlashCommandEvent;
import com.dwolfnineteen.jdaextra.models.CommonCommandProperties;
import com.dwolfnineteen.jdaextra.models.CommonSlashCommandProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.mappings.SlashOptionMapping;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Parser for slash commands.
 *
 * @see com.dwolfnineteen.jdaextra.parsers parsers
 */
public class SlashCommandParser extends CommandParser {
    /**
     * Construct new {@link SlashCommandParser}.
     *
     * @param jdaExtra The {@link JDAExtra} instance.
     * @param sourceEvent The {@link GenericEvent} for this parser.
     */
    public SlashCommandParser(@NotNull JDAExtra jdaExtra, @NotNull SlashCommandInteractionEvent sourceEvent) {
        super(jdaExtra, sourceEvent);
    }

    /**
     * The {@link SlashCommandInteractionEvent} (source event) for this parser.
     *
     * @return The {@link SlashCommandInteractionEvent}.
     */
    @Override
    public @NotNull SlashCommandInteractionEvent getSourceEvent() {
        return (SlashCommandInteractionEvent) sourceEvent;
    }

    /**
     * The {@link CommonSlashCommandProperties} for this parser.
     *
     * @return The {@link CommonSlashCommandProperties}. {@code null} if the {@link CommonSlashCommandProperties} not set.
     */
    @Override
    public @Nullable CommonSlashCommandProperties getProperties() {
        return (CommonSlashCommandProperties) properties;
    }

    // Properties should be set through setter, not constructor!
    // As you can see, in prefix command parser (JdaExtra.onMessageReceived),
    // properties are set much later than the parser init
    /**
     * Sets {@link CommonCommandProperties} for this parser.
     *
     * @param properties The {@link CommonCommandProperties}.
     * @return The {@link SlashCommandParser} instance, for chaining.
     */
    @Override
    public @NotNull SlashCommandParser setProperties(@NotNull CommonCommandProperties properties) {
        this.properties = properties;

        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull Object[] buildInvokeArguments() {
        Objects.requireNonNull(properties);

        List<Object> arguments = new ArrayList<>();
        List<? extends CommandOptionData> options = properties.getOptions();
        List<OptionMapping> regularMappings = ((SlashCommandInteractionEvent) sourceEvent).getOptions();

        for (int i = 0; i < options.size(); i++) {
            if (regularMappings.size() <= i) {
                arguments.add(null);

                continue;
            }

            Object type = buildInvokeArgumentType(options.get(i).getType(),
                    new SlashOptionMapping(regularMappings.get(i)));
            // TODO: Doesn't look very reliable (attachment if type is null)
            arguments.add(type == null ? regularMappings.get(i).getAsAttachment() : type);
        }

        List<SlashOptionMapping> mappings = regularMappings.stream()
                .map(SlashOptionMapping::new)
                .collect(Collectors.toList());

        arguments.add(0, new SlashCommandEvent((SlashCommandInteractionEvent) sourceEvent,
                jdaExtra,
                mappings,
                properties.getDescription()));

        return arguments.toArray();
    }
}
