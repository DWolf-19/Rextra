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
package com.dwolfnineteen.jdaextra;

import com.dwolfnineteen.jdaextra.builders.CommandBuilder;
import com.dwolfnineteen.jdaextra.builders.HybridCommandBuilder;
import com.dwolfnineteen.jdaextra.builders.PrefixCommandBuilder;
import com.dwolfnineteen.jdaextra.builders.SlashCommandBuilder;
import com.dwolfnineteen.jdaextra.commands.HybridCommand;
import com.dwolfnineteen.jdaextra.commands.PrefixCommand;
import com.dwolfnineteen.jdaextra.commands.SlashCommand;
import com.dwolfnineteen.jdaextra.exceptions.CommandNotFoundException;
import com.dwolfnineteen.jdaextra.models.CommonCommandProperties;
import com.dwolfnineteen.jdaextra.models.CommonSlashLikeCommandProperties;
import com.dwolfnineteen.jdaextra.models.commands.CommandModel;
import com.dwolfnineteen.jdaextra.models.commands.HybridCommandModel;
import com.dwolfnineteen.jdaextra.models.commands.PrefixCommandModel;
import com.dwolfnineteen.jdaextra.models.commands.SlashCommandModel;
import com.dwolfnineteen.jdaextra.models.subcommands.GeneralSubcommandData;
import com.dwolfnineteen.jdaextra.models.subcommands.PrefixSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.GeneralSubcommandGroupData;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.GeneralOptionData;
import com.dwolfnineteen.jdaextra.parsers.CommandParser;
import com.dwolfnineteen.jdaextra.parsers.HybridCommandParser;
import com.dwolfnineteen.jdaextra.parsers.PrefixCommandParser;
import com.dwolfnineteen.jdaextra.parsers.SlashCommandParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: Rename to JdaExtra
// TODO: Implement toString(), equals() and hashCode() in classes
// TODO: Rename General* classes to Regular*
// TODO: Reorganize TODOs
// TODO: Refactor CHANGELOG.md
// TODO: Spring-like bot class
/**
 * The main class, event interceptor of JDA-Extra.
 * <br>
 * This class is inherited from {@link ListenerAdapter},
 * it uses events for building application commands data for {@link JDA JDA}
 * and command parsing when calling event handlers.
 *
 * @see JDAExtraBuilder JDAExtraBuilder
 */
public class JDAExtra extends ListenerAdapter {
    private final String prefix;
    private final boolean whenMention;
    private final Map<String, HybridCommandModel> hybridCommandModels;
    private final Map<String, PrefixCommandModel> prefixCommandModels;
    private final Map<String, SlashCommandModel> slashCommandModels;

    /**
     * Build new {@link JDAExtra} instance (usually called from {@link JDAExtraBuilder}).
     *
     * @param prefix The prefix.
     * @param whenMention Whether bot should react to its mention as a prefix.
     * @param hybridCommands List of hybrid command classes.
     * @param prefixCommands List of prefix command classes.
     * @param slashCommands List of slash command classes.
     */
    public JDAExtra(@NotNull String prefix,
                    boolean whenMention,
                    @NotNull List<HybridCommand> hybridCommands,
                    @NotNull List<PrefixCommand> prefixCommands,
                    @NotNull List<SlashCommand> slashCommands) {
        this.prefix = prefix;
        this.whenMention = whenMention;

        this.slashCommandModels = buildCommandProperties(slashCommands, SlashCommandBuilder::new);
        this.prefixCommandModels = buildCommandProperties(prefixCommands, PrefixCommandBuilder::new);
        this.hybridCommandModels = buildCommandProperties(hybridCommands, HybridCommandBuilder::new);
    }

    private <T, P extends CommonCommandProperties> @NotNull Map<String, P> buildCommandProperties(@NotNull List<T> commands,
                                                                                                  @NotNull Function<T, ? extends CommandBuilder> builderFunction) {
        Map<String, P> propertiesMap = new HashMap<>();

        for (T command : commands) {
            @SuppressWarnings("unchecked") // TODO: Fix unchecked cast
            P properties = (P) builderFunction.apply(command).buildModel();
            propertiesMap.put(properties.getName(), properties);
        }

        return propertiesMap;
    }

    /**
     * The prefix for prefix/hybrid commands.
     *
     * @return The prefix.
     */
    public @NotNull String getPrefix() {
        return prefix;
    }

    /**
     * Whether bot should react to its mention as a prefix.
     *
     * @return {@code True} if bot will react to its mention as a prefix.
     */
    public boolean isWhenMention() {
        return whenMention;
    }

    /**
     * Map of command name and {@link HybridCommandModel}.
     *
     * @return Map of command name and {@link HybridCommandModel}.
     * @see com.dwolfnineteen.jdaextra.builders builders
     */
    public @NotNull Map<String, HybridCommandModel> getHybridCommandModels() {
        return hybridCommandModels;
    }

    /**
     * Map of command name and {@link PrefixCommandModel}.
     *
     * @return Map of command name and {@link PrefixCommandModel}.
     * @see com.dwolfnineteen.jdaextra.builders builders
     */
    public @NotNull Map<String, PrefixCommandModel> getPrefixCommandModels() {
        return prefixCommandModels;
    }

    /**
     * Map of command name and {@link SlashCommandModel}.
     *
     * @return Map of command name and {@link SlashCommandModel}.
     * @see com.dwolfnineteen.jdaextra.builders builders
     */
    public @NotNull Map<String, SlashCommandModel> getSlashCommandModels() {
        return slashCommandModels;
    }

    // TODO: Data must be bound before login, move to constructor
    /**
     * {@link ReadyEvent} handler for adding application command data to {@link JDA}.
     *
     * @param event The {@link ReadyEvent}.
     */
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA()
                .updateCommands()
                .addCommands(buildCommandData(slashCommandModels.values()))
                .addCommands(buildCommandData(hybridCommandModels.values()))
                .queue();
    }

    private <T extends CommonCommandProperties> @NotNull List<CommandData> buildCommandData(@NotNull Collection<T> propertiesList) {
        List<CommandData> data = new ArrayList<>();

        // TODO: Add SlashCommandData#setDefaultPermissions
        for (T properties : propertiesList) {
            Map<DiscordLocale, String> nameLocalizations = ((CommonSlashLikeCommandProperties) properties).getNameLocalizations().toMap();
            Map<DiscordLocale, String> descriptionLocalizations = ((CommonSlashLikeCommandProperties) properties).getDescriptionLocalizations().toMap();

            List<OptionData> options = properties.getOptions()
                    .stream()
                    .map(option -> ((GeneralOptionData) option).toGeneralOptionData())
                    .collect(Collectors.toList());

            List<SubcommandData> subcommands = ((CommandModel) properties).getSubcommandList()
                    .stream()
                    .map(subcommand -> ((GeneralSubcommandData) subcommand).toGeneralSubcommandData())
                    .collect(Collectors.toList());

            List<SubcommandGroupData> groups = ((CommandModel) properties).getSubcommandGroupList()
                    .stream()
                    .map(group -> ((GeneralSubcommandGroupData) group).toGeneralSubcommandGroupData())
                    .collect(Collectors.toList());

            data.add(Commands.slash(properties.getName(), properties.getDescription())
                    .setNameLocalizations(nameLocalizations)
                    .setDescriptionLocalizations(descriptionLocalizations)
                    .addOptions(options)
                    .setGuildOnly(((CommandModel) properties).isGuildOnly())
                    .setNSFW(((CommandModel) properties).isNsfw())
                    .addSubcommands(subcommands)
                    .addSubcommandGroups(groups));
        }

        return data;
    }

    /**
     * {@link SlashCommandInteractionEvent} handler for parsing slash commands and executing their logic.
     *
     * @param event The {@link SlashCommandInteractionEvent}.
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        SlashCommandModel properties = slashCommandModels.get(event.getName());

        SlashCommandParser parser = new SlashCommandParser(this, event);

        if (properties == null) {
            onHybridCommand(event.getName(), parser);

            return;
        }

        handleSlashCommand(event, parser.setProperties(properties), properties);
    }

    /**
     * {@link MessageReceivedEvent} handler for parsing prefix/hybrid commands and executing their logic.
     *
     * @param event The {@link MessageReceivedEvent}.
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        PrefixCommandParser parser = new PrefixCommandParser(this, event);

        if (!parser.isCommand()) {
            return;
        }

        String commandName = parser.getName();

        PrefixCommandModel properties = prefixCommandModels.get(commandName);

        if (properties == null) {
            onHybridCommand(commandName, parser);

            return;
        }

        handlePrefixCommand(event, parser.setProperties(properties), properties);
    }

    private void onHybridCommand(@NotNull String commandName, @NotNull CommandParser parser) {
        HybridCommandModel properties = hybridCommandModels.get(commandName);

        if (properties == null) {
            throw new CommandNotFoundException(commandName);
        }

        HybridCommandParser hybridParser = new HybridCommandParser(this, parser.getSourceEvent(), parser);
        hybridParser.setProperties(properties);

        handleHybridCommand(parser.getSourceEvent(), hybridParser);
    }

    private void handleSlashCommand(@NotNull SlashCommandInteractionEvent event,
                                    @NotNull SlashCommandParser parser,
                                    @NotNull CommandModel properties) {
        if (handleCommand(parser)) {
            return;
        }

        if (event.getSubcommandGroup() != null && event.getSubcommandName() != null) {
            SubcommandGroupProperties subcommandGroup = properties.getSubcommandGroupMap()
                    .get(event.getSubcommandGroup());

            CommandParser subcommandParser = buildSubcommandInGroupParser(event,
                    subcommandGroup,
                    event.getSubcommandName(),
                    SlashCommandParser::new);

            runCommand(subcommandParser.getProperties().getEntryPoint(),
                    subcommandGroup.getGroupClass(),
                    subcommandParser.buildInvokeArguments());
        } else if (event.getSubcommandName() != null) {
            Map<String, ? extends SubcommandProperties> subcommandMap = properties.getSubcommandMap();

            CommandParser subcommandParser = buildSubcommandParser(event,
                    subcommandMap,
                    event.getSubcommandName(),
                    SlashCommandParser::new);

            runCommand(subcommandParser.getProperties().getEntryPoint(),
                    properties.getCommand(),
                    subcommandParser.buildInvokeArguments());
        }
    }

    private void handlePrefixCommand(@NotNull MessageReceivedEvent event,
                                     @NotNull PrefixCommandParser parser,
                                     @NotNull CommandModel properties) {
        if (handleCommand(parser)) {
            return;
        }

        String subcommandName = parser.getSubcommandName();
        PrefixSubcommandProperties subcommand = (PrefixSubcommandProperties) properties.getSubcommandMap().get(subcommandName);

        if (parser.getSubcommandGroupName() != null && parser.getSubcommandInGroupName() != null) {
            SubcommandGroupProperties subcommandGroup = properties.getSubcommandGroupMap()
                    .get(parser.getSubcommandGroupName());

            // TODO: Throw exception when !command invalidgroup <args>
            if (subcommand != null && !subcommand.getOptions().isEmpty()) {
                Map<String, ? extends SubcommandProperties> subcommandMap = properties.getSubcommandMap();

                CommandParser subcommandParser = buildSubcommandParser(event,
                        subcommandMap,
                        parser.getSubcommandInGroupName(),
                        PrefixCommandParser::new);

                List<String> options = ((PrefixCommandParser) subcommandParser).getSubcommandOptions();
                ((PrefixCommandParser) subcommandParser).addAsOptionMappings(options);

                runCommand(subcommandParser.getProperties().getEntryPoint(),
                        properties.getCommand(),
                        subcommandParser.buildInvokeArguments());

                return;
            }

            CommandParser subcommandParser = buildSubcommandInGroupParser(event,
                    subcommandGroup,
                    parser.getSubcommandInGroupName(),
                    PrefixCommandParser::new);

            List<String> options = ((PrefixCommandParser) subcommandParser).getSubcommandInGroupOptions();
            ((PrefixCommandParser) subcommandParser).addAsOptionMappings(options);

            runCommand(subcommandParser.getProperties().getEntryPoint(),
                    subcommandGroup.getGroupClass(),
                    subcommandParser.buildInvokeArguments());

        } else if (subcommandName != null) {
            Map<String, ? extends SubcommandProperties> subcommandMap = properties.getSubcommandMap();

            CommandParser subcommandParser = buildSubcommandParser(event,
                    subcommandMap,
                    subcommandName,
                    PrefixCommandParser::new);

            List<String> options = ((PrefixCommandParser) subcommandParser).getSubcommandOptions();
            ((PrefixCommandParser) subcommandParser).addAsOptionMappings(options);

            runCommand(subcommandParser.getProperties().getEntryPoint(),
                    properties.getCommand(),
                    subcommandParser.buildInvokeArguments());
        }
    }

    // TODO: Cleanup code
    private void handleHybridCommand(@NotNull GenericEvent event, @NotNull HybridCommandParser parser) {
        if (handleCommand(parser)) {
            return;
        }

        CommandParser sourceParser = parser.getSourceParser();
        HybridCommandModel properties = (HybridCommandModel) Objects.requireNonNull(parser.getProperties());

        if (sourceParser instanceof SlashCommandParser) {
            SlashCommandInteractionEvent slashEvent = (SlashCommandInteractionEvent) event;

            if (slashEvent.getSubcommandName() != null && slashEvent.getSubcommandGroup() != null) {
                SubcommandGroupProperties subcommandGroup = properties.getSubcommandGroupMap()
                        .get(slashEvent.getSubcommandGroup());

                if (subcommandGroup == null) {
                    throw new CommandNotFoundException(slashEvent.getSubcommandGroup());
                }

                Map<String, ? extends SubcommandProperties> subcommandMap = subcommandGroup.getSubcommandMap();

                CommonCommandProperties subcommand = (CommonCommandProperties) subcommandMap.get(slashEvent.getSubcommandName());

                if (subcommand == null) {
                    throw new CommandNotFoundException(slashEvent.getSubcommandName());
                }

                HybridCommandParser subcommandParser = new HybridCommandParser(this, slashEvent, new SlashCommandParser(this, slashEvent)).setProperties(properties);

                runCommand(subcommandParser.getProperties().getEntryPoint(),
                        subcommandGroup.getGroupClass(),
                        subcommandParser.buildInvokeArguments());
            } else if (slashEvent.getSubcommandName() != null) {
                Map<String, ? extends SubcommandProperties> subcommandMap = properties.getSubcommandMap();

                CommonCommandProperties subcommand = (CommonCommandProperties) subcommandMap.get(slashEvent.getSubcommandName());

                if (subcommand == null) {
                    throw new CommandNotFoundException(slashEvent.getSubcommandName());
                }

                HybridCommandParser subcommandParser = new HybridCommandParser(this, event, new SlashCommandParser(this, slashEvent)).setProperties(subcommand);

                runCommand(subcommandParser.getProperties().getEntryPoint(),
                        properties.getCommand(),
                        subcommandParser.buildInvokeArguments());
            }
        } else if (sourceParser instanceof PrefixCommandParser) {
            MessageReceivedEvent prefixEvent = (MessageReceivedEvent) event;
            PrefixCommandParser prefixParser = (PrefixCommandParser) sourceParser;
            String subcommandName = prefixParser.getSubcommandName();

            if (prefixParser.getSubcommandGroupName() != null && prefixParser.getSubcommandInGroupName() != null) {
                SubcommandGroupProperties subcommandGroup = properties.getSubcommandGroupMap()
                        .get(prefixParser.getSubcommandGroupName());

                if (subcommandGroup == null) {
                    throw new CommandNotFoundException(prefixParser.getSubcommandGroupName());
                }

                Map<String, ? extends SubcommandProperties> subcommandMap = subcommandGroup.getSubcommandMap();

                CommonCommandProperties subcommand = (CommonCommandProperties) subcommandMap.get(prefixParser.getSubcommandInGroupName());

                if (subcommand == null) {
                    throw new CommandNotFoundException(prefixParser.getSubcommandInGroupName());
                }

                HybridCommandParser subcommandParser = new HybridCommandParser(this, event, new PrefixCommandParser(this, prefixEvent)).setProperties(subcommand);

                List<String> options = ((PrefixCommandParser) subcommandParser.getSourceParser()).getSubcommandOptions();
                ((PrefixCommandParser) subcommandParser.getSourceParser()).addAsOptionMappings(options);

                runCommand(subcommandParser.getProperties().getEntryPoint(),
                        subcommandGroup.getGroupClass(),
                        subcommandParser.buildInvokeArguments());

            } else if (subcommandName != null) {
                Map<String, ? extends SubcommandProperties> subcommandMap = properties.getSubcommandMap();


                CommonCommandProperties subcommand = (CommonCommandProperties) subcommandMap.get(subcommandName);

                if (subcommand == null) {
                    throw new CommandNotFoundException(subcommandName);
                }

                HybridCommandParser subcommandParser = new HybridCommandParser(this, event, new PrefixCommandParser(this, (MessageReceivedEvent) event)).setProperties(subcommand);

                List<String> options = ((PrefixCommandParser) subcommandParser.getSourceParser()).getSubcommandOptions();
                ((PrefixCommandParser) subcommandParser.getSourceParser()).addAsOptionMappings(options);

                runCommand(subcommandParser.getProperties().getEntryPoint(),
                        properties.getCommand(),
                        subcommandParser.buildInvokeArguments());
            }
        } else {
            throw new RuntimeException();
        }
    }

    private boolean handleCommand(@NotNull CommandParser parser) {
        CommandModel properties = (CommandModel) parser.getProperties();
        Method entryPoint = ((CommonCommandProperties) properties).getEntryPoint();

        if (entryPoint != null) {
            runCommand(entryPoint, properties.getCommand(), parser.buildInvokeArguments());

            return true;
        } else {
            return false;
        }
    }

    private <E extends GenericEvent,
            C extends CommandParser> @NotNull CommandParser buildSubcommandInGroupParser(@NotNull E event,
                                                                                         @Nullable SubcommandGroupProperties subcommandGroup,
                                                                                         @NotNull String subcommandName,
                                                                                         @NotNull BiFunction<JDAExtra, E, C> builderFunction) {
        if (subcommandGroup == null) {
            throw new CommandNotFoundException(subcommandName);
        }

        return buildSubcommandParser(event, subcommandGroup.getSubcommandMap(), subcommandName, builderFunction);
    }

    private <E extends GenericEvent,
            C extends CommandParser> @NotNull CommandParser buildSubcommandParser(@NotNull E event,
                                                                                  @NotNull Map<String, ? extends SubcommandProperties> subcommandMap,
                                                                                  @NotNull String subcommandName,
                                                                                  @NotNull BiFunction<JDAExtra, E, C> builderFunction) {
        CommonCommandProperties subcommand = (CommonCommandProperties) subcommandMap.get(subcommandName);

        if (subcommand == null) {
            throw new CommandNotFoundException(subcommandName);
        }

        return builderFunction.apply(this, event).setProperties(subcommand);
    }

    private void runCommand(@NotNull Method entryPoint, @NotNull Object object, @NotNull Object[] arguments) {
        try {
            entryPoint.invoke(object, arguments);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            // TODO: Replace with custom EntryPointInvocationException
            throw new RuntimeException(exception);
        }
    }
}
