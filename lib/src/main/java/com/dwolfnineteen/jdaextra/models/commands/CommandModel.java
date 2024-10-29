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

import com.dwolfnineteen.jdaextra.commands.BaseCommand;
import com.dwolfnineteen.jdaextra.models.subcommands.SubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SubcommandGroupProperties;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// TODO: Rename to CommandProperties
// TODO: Create RegularSlashCommandData interface
// TODO(?): Add addSubcommand(), addSubcommandGroup()
/**
 * Interface defining additional APIs for normal commands.
 */
public interface CommandModel {
    /**
     * The command class inherited from one of the {@link com.dwolfnineteen.jdaextra.commands commands}.
     *
     * @return The command class.
     */
    BaseCommand getCommand();

    /**
     * Sets the command class inherited from one of the {@link com.dwolfnineteen.jdaextra.commands commands}.
     *
     * @param command The command class.
     */
    CommandModel setCommand(BaseCommand command);

    /**
     * Whether this command can be executed only on guilds.
     *
     * @return Whether this command is guild only.
     */
    boolean isGuildOnly();

    /**
     * Sets whether this command can be executed only on guilds.
     *
     * @param guildOnly Whether this command is guild only.
     */
    CommandModel setGuildOnly(boolean guildOnly);

    /**
     * Whether this command can be executed only in NSFW channels.
     *
     * @return Whether this command is NSFW.
     */
    boolean isNsfw();

    /**
     * Sets whether this command can be executed only in NSFW channels.
     *
     * @param nsfw Whether this command is NSFW only.
     */
    CommandModel setNsfw(boolean nsfw);

    /**
     * {@link Map} of subcommand name and {@link SubcommandProperties}.
     *
     * @return {@link Map} of subcommand name and {@link SubcommandProperties}.
     */
    Map<String, ? extends SubcommandProperties> getSubcommandMap();

    /**
     * {@link List} of {@link SubcommandProperties}.
     *
     * @return {@link List} of {@link SubcommandProperties}.
     */
    List<? extends SubcommandProperties> getSubcommandList();

    /**
     * Add {@link Collection} of {@link SubcommandProperties} to subcommands of this command.
     *
     * @param subcommands {@link Collection} of {@link SubcommandProperties}.
     * @return The {@link CommandModel} instance, for chaining.
     */
    CommandModel addSubcommands(Collection<? extends SubcommandProperties> subcommands);

    /**
     * Add varargs of {@link SubcommandProperties} to subcommands of this command.
     *
     * @param subcommands Varargs of {@link SubcommandProperties}.
     * @return The {@link CommandModel} instance, for chaining.
     */
    CommandModel addSubcommands(SubcommandProperties... subcommands);

    /**
     * {@link Map} of subcommand group name and {@link SubcommandGroupProperties}.
     *
     * @return {@link Map} of subcommand group name and {@link SubcommandGroupProperties}.
     */
    Map<String, ? extends SubcommandGroupProperties> getSubcommandGroupMap();

    /**
     * {@link List} of {@link SubcommandGroupProperties}.
     *
     * @return {@link List} of {@link SubcommandGroupProperties}.
     */
    List<? extends SubcommandGroupProperties> getSubcommandGroupList();

    /**
     * Add {@link Collection} of {@link SubcommandGroupProperties} to subcommand groups of this command.
     *
     * @param subcommandGroups {@link Collection} of {@link SubcommandGroupProperties}.
     * @return The {@link CommandModel} instance, for chaining.
     */
    CommandModel addSubcommandGroups(Collection<? extends SubcommandGroupProperties> subcommandGroups);

    /**
     * Add varargs of {@link SubcommandGroupProperties} to subcommand groups of this command.
     *
     * @param subcommandGroups Varargs of {@link SubcommandGroupProperties}.
     * @return The {@link CommandModel} instance, for chaining.
     */
    CommandModel addSubcommandGroups(SubcommandGroupProperties... subcommandGroups);
}
