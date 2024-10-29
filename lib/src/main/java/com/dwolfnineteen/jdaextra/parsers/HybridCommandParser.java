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
import com.dwolfnineteen.jdaextra.events.HybridCommandEvent;
import com.dwolfnineteen.jdaextra.events.PrefixCommandEvent;
import com.dwolfnineteen.jdaextra.events.SlashCommandEvent;
import com.dwolfnineteen.jdaextra.models.CommonCommandProperties;
import com.dwolfnineteen.jdaextra.models.CommonHybridCommandProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.mappings.HybridOptionMapping;
import com.dwolfnineteen.jdaextra.options.mappings.PrefixOptionMapping;
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
 * Parser for hybrid commands.
 *
 * @see com.dwolfnineteen.jdaextra.parsers parsers
 */
public class HybridCommandParser extends CommandParser {
    private final CommandParser parser;

    /**
     * Construct new {@link HybridCommandParser}.
     *
     * @param jdaExtra The {@link JDAExtra} instance.
     * @param parser The {@link CommandParser} to be used.
     */
    public HybridCommandParser(@NotNull JDAExtra jdaExtra,
                               @NotNull GenericEvent sourceEvent,
                               @NotNull CommandParser parser) {
        super(jdaExtra, sourceEvent);

        this.parser = parser;
    }

    /**
     * @return
     */
    public @NotNull CommandParser getSourceParser() {
        return parser;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull GenericEvent getSourceEvent() {
        return sourceEvent;
    }

    /**
     * The {@link CommonHybridCommandProperties} for this parser.
     *
     * @return The {@link CommonHybridCommandProperties} for this parser.
     */
    @Override
    public @Nullable CommonHybridCommandProperties getProperties() {
        return (CommonHybridCommandProperties) properties;
    }

    /**
     * Sets {@link CommonCommandProperties} for this parser.
     *
     * @param properties The {@link CommonCommandProperties}.
     * @return The {@link HybridCommandParser} instance, for chaining.
     */
    @Override
    public @NotNull HybridCommandParser setProperties(@NotNull CommonCommandProperties properties) {
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
        Objects.requireNonNull(parser);

        List<Object> arguments = new ArrayList<>();
        List<? extends CommandOptionData> options = properties.getOptions();

        if (parser instanceof PrefixCommandParser) {
            PrefixCommandParser prefixParser = (PrefixCommandParser) parser;

            List<PrefixOptionMapping> mappings = new ArrayList<>();

            String trigger = prefixParser.getTrigger();
            List<String> optionValues = prefixParser.getOptions();

            if (!optionValues.isEmpty()) {
                for (int i = 0; i < optionValues.size(); i++) {
                    mappings.add(new PrefixOptionMapping(options.get(i).getType(),
                            options.get(i).getName(),
                            optionValues.get(i),
                            prefixParser.getSourceEvent()));
                }
            }

            for (int i = 0; i < options.size(); i++) {
                if (mappings.size() <= i) {
                    arguments.add(null);

                    continue;
                }

                arguments.add(buildInvokeArgumentType(options.get(i).getType(),
                        new HybridOptionMapping(mappings.get(i))));
            }

            arguments.add(0, new HybridCommandEvent(new PrefixCommandEvent(prefixParser.getSourceEvent(),
                    jdaExtra,
                    trigger,
                    prefixParser.getName(),
                    properties.getDescription(),
                    mappings),
                    // TODO: Reorganize mappings translation for better look
                    mappings.stream().map(HybridOptionMapping::new).collect(Collectors.toList())));
        } else if (parser instanceof SlashCommandParser) {
            SlashCommandParser slashParser = (SlashCommandParser) parser;
            SlashCommandInteractionEvent event = slashParser.getSourceEvent();

            List<OptionMapping> regularMappings = event.getOptions();

            List<SlashOptionMapping> mappings = regularMappings.stream()
                    .map(SlashOptionMapping::new)
                    .collect(Collectors.toList());

            SlashCommandEvent slashEvent = new SlashCommandEvent(event,
                    jdaExtra,
                    mappings,
                    properties.getDescription());

            for (int i = 0; i < options.size(); i++) {
                if (mappings.size() <= i) {
                    arguments.add(null);

                    continue;
                }

                arguments.add(buildInvokeArgumentType(options.get(i).getType(),
                        new HybridOptionMapping(mappings.get(i))));
            }

            arguments.add(0, new HybridCommandEvent(slashEvent,
                    // TODO: Reorganize mappings translation for better look
                    mappings.stream().map(HybridOptionMapping::new).collect(Collectors.toList())));
        }

        return arguments.toArray();
    }
}
