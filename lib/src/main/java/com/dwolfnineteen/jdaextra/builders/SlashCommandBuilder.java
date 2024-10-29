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

import com.dwolfnineteen.jdaextra.annotations.ExtraSlashCommand;
import com.dwolfnineteen.jdaextra.annotations.options.AutoComplete;
import com.dwolfnineteen.jdaextra.annotations.options.Required;
import com.dwolfnineteen.jdaextra.annotations.options.SlashOption;
import com.dwolfnineteen.jdaextra.annotations.subcommands.RextraSlashSubcommand;
import com.dwolfnineteen.jdaextra.annotations.subcommands.groups.RextraSlashSubcommandGroup;
import com.dwolfnineteen.jdaextra.commands.BaseCommand;
import com.dwolfnineteen.jdaextra.commands.SlashCommand;
import com.dwolfnineteen.jdaextra.commands.subcommandgroups.SlashSubcommandGroup;
import com.dwolfnineteen.jdaextra.exceptions.CommandAnnotationNotFoundException;
import com.dwolfnineteen.jdaextra.exceptions.buildtime.CommandPropertyNotFoundException;
import com.dwolfnineteen.jdaextra.models.commands.CommandModel;
import com.dwolfnineteen.jdaextra.models.commands.SlashCommandModel;
import com.dwolfnineteen.jdaextra.models.subcommands.SlashSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.SlashSubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.SlashOptionData;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Slash command builder.
 */
public class SlashCommandBuilder extends SlashLikeCommandBuilder {
    /**
     * Construct new {@link SlashCommandBuilder}.
     *
     * @param command The slash command class.
     */
    public SlashCommandBuilder(@NotNull SlashCommand command) {
        super(command);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull SlashCommandModel buildModel() {
        Class<? extends BaseCommand> clazz = command.getClass();
        ExtraSlashCommand annotation = clazz.getAnnotation(ExtraSlashCommand.class);

        if (annotation == null) {
            throw new CommandAnnotationNotFoundException();
        }

        Method entryPoint = buildEntryPoint();
        String name;
        List<CommandOptionData> options = entryPoint == null ? Collections.emptyList() : buildOptions(entryPoint);

        if (entryPoint == null) {
            if (annotation.name().isEmpty()) {
                throw new CommandPropertyNotFoundException("could not found command name in annotation or pick up a method name");
            }

            name = annotation.name();
        } else {
            name = annotation.name().isEmpty() ? entryPoint.getName() : annotation.name();
        }

        SlashCommandModel model = new SlashCommandModel((SlashCommand) command, name, annotation.description());

        model.setCommand(command)
                .setEntryPoint(entryPoint)
                .addOptions(options)
                .addSubcommands(buildSubcommands())
                .addSubcommandGroups(buildSubcommandGroups());

        return (SlashCommandModel) buildSettings(buildLocalization(model, clazz), clazz);
    }

    /**
     * {@inheritDoc}
     *
     * @param model {@inheritDoc}
     * @param clazz {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected @NotNull SlashCommandModel buildLocalization(@NotNull CommandModel model,
                                                           @NotNull Class<? extends BaseCommand> clazz) {
        return ((SlashCommandModel) super.buildLocalization(model, clazz))
                .setNameLocalizations(buildNameLocalizations(clazz))
                .setDescriptionLocalizations(buildDescriptionLocalizations(clazz));
    }

    /**
     * {@inheritDoc}
     *
     * @param entryPoint {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected @NotNull List<CommandOptionData> buildOptions(@NotNull Method entryPoint) {
        List<CommandOptionData> options = new ArrayList<>();

        for (Parameter parameter : entryPoint.getParameters()) {
            if (parameter.isAnnotationPresent(SlashOption.class)) {
                SlashOption slashOption = parameter.getAnnotation(SlashOption.class);

                SlashOptionData data = new SlashOptionData(buildOptionType(parameter.getType(), slashOption.type()),
                        slashOption.name(),
                        slashOption.description(),
                        parameter.isAnnotationPresent(Required.class),
                        parameter.isAnnotationPresent(AutoComplete.class));

                data.addChoices(buildOptionChoices(parameter.getAnnotations()));

                options.add(data);
            }
        }

        return options;
    }

    /**
     * {@inheritDoc}
     *
     * @param parameterType {@inheritDoc}
     * @param typeFromAnnotation {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected @NotNull OptionType buildOptionType(@NotNull Class<?> parameterType,
                                                  @NotNull OptionType typeFromAnnotation) {
        return typeFromAnnotation == OptionType.UNKNOWN && parameterType.equals(Message.Attachment.class)
                ? OptionType.ATTACHMENT
                : super.buildOptionType(parameterType, typeFromAnnotation);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link SlashSubcommandProperties}.
     */
    @Override
    protected @NotNull List<SlashSubcommandProperties> buildSubcommands() {
        List<SlashSubcommandProperties> subcommands = new ArrayList<>();

        for (Method method : command.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RextraSlashSubcommand.class)) {
                continue;
            }

            RextraSlashSubcommand annotation = method.getAnnotation(RextraSlashSubcommand.class);

            String name = annotation.name().isEmpty() ? method.getName() : annotation.name();
            SlashSubcommandProperties subcommand = new SlashSubcommandProperties(method, name, annotation.description());

            subcommands.add(subcommand.setNameLocalizations(buildNameLocalizations(method))
                    .setDescriptionLocalizations(buildDescriptionLocalizations(method))
                    .addOptions(buildOptions(method)));
        }

        return subcommands;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link SlashSubcommandGroupProperties}.
     */
    @Override
    protected @NotNull List<SlashSubcommandGroupProperties> buildSubcommandGroups() {
        List<SlashSubcommandGroupProperties> groups = new ArrayList<>();

        List<Class<?>> classes = Arrays.stream(command.getClass().getDeclaredClasses())
                .filter(clazz -> clazz.isAnnotationPresent(RextraSlashSubcommandGroup.class))
                .collect(Collectors.toList());

        for (Class<?> clazz : classes) {
            RextraSlashSubcommandGroup groupAnnotation = clazz.getAnnotation(RextraSlashSubcommandGroup.class);

            SlashSubcommandGroupProperties group = new SlashSubcommandGroupProperties(groupAnnotation.name(),
                    groupAnnotation.description());

            List<Method> entryPoints = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RextraSlashSubcommand.class))
                    .collect(Collectors.toList());

            List<SlashSubcommandProperties> subcommands = new ArrayList<>();

            for (Method entryPoint : entryPoints) {
                RextraSlashSubcommand subcommandAnnotation = entryPoint.getAnnotation(RextraSlashSubcommand.class);
                String name = subcommandAnnotation.name().isEmpty() ? entryPoint.getName() : subcommandAnnotation.name();

                SlashSubcommandProperties subcommand = new SlashSubcommandProperties(entryPoint, name, subcommandAnnotation.description());

                subcommand.setNameLocalizations(buildNameLocalizations(entryPoint))
                        .setDescriptionLocalizations(buildDescriptionLocalizations(entryPoint))
                        .addOptions(buildOptions(entryPoint));

                subcommands.add(subcommand);
            }

            SlashSubcommandGroup groupClassObject;

            try {
                groupClassObject = (SlashSubcommandGroup) clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException exception) {
                // TODO(?): Custom exception
                throw new RuntimeException(exception);
            }

            groups.add(group.setGroupClass(groupClassObject)
                    .setNameLocalizations(buildNameLocalizations(clazz))
                    .setDescriptionLocalizations(buildDescriptionLocalizations(clazz))
                    .addSubcommands(subcommands));
        }

        return groups;
    }
}
