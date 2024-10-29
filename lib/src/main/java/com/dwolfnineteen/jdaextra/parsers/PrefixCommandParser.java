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
import com.dwolfnineteen.jdaextra.events.PrefixCommandEvent;
import com.dwolfnineteen.jdaextra.models.CommonCommandProperties;
import com.dwolfnineteen.jdaextra.models.CommonPrefixCommandProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.mappings.PrefixOptionMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// TODO: Add caching for PrefixCommandParser#parse** methods
/**
 * Parser for prefix commands.
 *
 * @see com.dwolfnineteen.jdaextra.parsers parsers
 */
public class PrefixCommandParser extends CommandParser {
    private final List<PrefixOptionMapping> mappings;
    private boolean isCommand;
    private String trigger;
    private List<String> elements;
    private String name;
    private String subcommandName;
    private String subcommandInGroupName;
    private String subcommandGroupName;

    {
        mappings = new ArrayList<>();
    }

    /**
     * Construct new {@link PrefixCommandParser}.
     *
     * @param jdaExtra The {@link JDAExtra} instance.
     * @param sourceEvent The {@link MessageReceivedEvent} for this parser.
     */
    public PrefixCommandParser(@NotNull JDAExtra jdaExtra, @NotNull MessageReceivedEvent sourceEvent) {
        super(jdaExtra, sourceEvent);

        String mention = sourceEvent.getJDA().getSelfUser().getAsMention();
        String content = sourceEvent.getMessage().getContentRaw();
        String prefix = jdaExtra.getPrefix();

        if (!(content.startsWith(prefix) || (jdaExtra.isWhenMention() && content.startsWith(mention)))) {
            return;
        }

        isCommand = true;
        trigger = content.startsWith(prefix) ? prefix : mention + " ";

        elements = Arrays.asList(content.substring(trigger.length()).split(" "));

        name = content.substring(trigger.length()).split(" ")[0];
        subcommandName = elements.size() >= 2 ? elements.get(1) : null;
        subcommandGroupName = elements.size() >= 2 ? elements.get(1) : null;
        subcommandInGroupName = elements.size() >= 3 ? elements.get(2) : null;
    }

    /**
     * The {@link MessageReceivedEvent} (source event) for this parser.
     *
     * @return The {@link MessageReceivedEvent}.
     */
    @Override
    public @NotNull MessageReceivedEvent getSourceEvent() {
        return (MessageReceivedEvent) sourceEvent;
    }

    /**
     * The {@link CommonPrefixCommandProperties} for this parser.
     *
     * @return The {@link CommonPrefixCommandProperties}.
     */
    @Override
    public @NotNull CommonPrefixCommandProperties getProperties() {
        return (CommonPrefixCommandProperties) properties;
    }

    /**
     * Sets {@link CommonCommandProperties} for this parser.
     *
     * @param properties The {@link CommonCommandProperties}.
     * @return The {@link PrefixCommandParser} instance, for chaining.
     */
    @Override
    public @NotNull PrefixCommandParser setProperties(@NotNull CommonCommandProperties properties) {
        this.properties = properties;

        return this;
    }

    /**
     * Checks if the source message begins with a given prefix.
     *
     * @return {@code True} if it is a command.
     */
    public boolean isCommand() {
        return isCommand;
    }

    /**
     * Trigger (prefix/mention) from the message.
     *
     * @return Trigger for this command.
     */
    public @NotNull String getTrigger() {
        return trigger;
    }

    /**
     * Command name from the message.
     *
     * @return The command name.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Command option values as a {@link String} array.
     *
     * @return The option values.
     */
    public @NotNull List<String> getOptions() {
        return elements.size() >= 2 ? elements.subList(1, elements.size()) : Collections.emptyList();
    }

    /**
     * @return
     */
    public @NotNull List<String> getSubcommandOptions() {
        return elements.size() >= 3 ? elements.subList(2, elements.size()) : Collections.emptyList();
    }

    /**
     * @return
     */
    public @NotNull List<String> getSubcommandInGroupOptions() {
        return elements.size() >= 4 ? elements.subList(3, elements.size()) : Collections.emptyList();
    }

    /**
     * @param options
     */
    public void addAsOptionMappings(@NotNull List<String> options) {
        for (int i = 0; i < options.size(); i++) {
            CommandOptionData data = properties.getOptions().get(i);

            mappings.add(new PrefixOptionMapping(data.getType(),
                    data.getName(),
                    options.get(i),
                    (MessageReceivedEvent) sourceEvent));
        }
    }

    /**
     * @return
     */
    public @Nullable String getSubcommandName() {
        return subcommandName;
    }

    /**
     * @return
     */
    public @Nullable String getSubcommandInGroupName() {
        return subcommandInGroupName;
    }

    /**
     * @return
     */
    public @Nullable String getSubcommandGroupName() {
        return subcommandGroupName;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Object[] buildInvokeArguments() {
        Objects.requireNonNull(properties);

        List<Object> arguments = new ArrayList<>();
        List<? extends CommandOptionData> options = properties.getOptions();

        for (int i = 0; i < options.size(); i++) {
            if (mappings.size() <= i) {
                arguments.add(null);

                continue;
            }

            arguments.add(buildInvokeArgumentType(options.get(i).getType(), mappings.get(i)));
        }

        arguments.add(0, new PrefixCommandEvent((MessageReceivedEvent) sourceEvent,
                jdaExtra,
                trigger,
                name,
                properties.getDescription(),
                mappings));

        return arguments.toArray();
    }
}
