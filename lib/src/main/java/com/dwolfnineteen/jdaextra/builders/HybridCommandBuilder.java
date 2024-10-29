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

import com.dwolfnineteen.jdaextra.annotations.RextraHybridCommand;
import com.dwolfnineteen.jdaextra.annotations.options.HybridOption;
import com.dwolfnineteen.jdaextra.annotations.options.Required;
import com.dwolfnineteen.jdaextra.annotations.subcommands.RextraHybridSubcommand;
import com.dwolfnineteen.jdaextra.annotations.subcommands.groups.RextraHybridSubcommandGroup;
import com.dwolfnineteen.jdaextra.commands.BaseCommand;
import com.dwolfnineteen.jdaextra.commands.HybridCommand;
import com.dwolfnineteen.jdaextra.commands.subcommandgroups.HybridSubcommandGroup;
import com.dwolfnineteen.jdaextra.exceptions.CommandAnnotationNotFoundException;
import com.dwolfnineteen.jdaextra.exceptions.buildtime.CommandPropertyNotFoundException;
import com.dwolfnineteen.jdaextra.models.commands.CommandModel;
import com.dwolfnineteen.jdaextra.models.commands.HybridCommandModel;
import com.dwolfnineteen.jdaextra.models.subcommands.HybridSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.HybridSubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.HybridOptionData;
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
 * Hybrid command builder.
 */
public class HybridCommandBuilder extends SlashLikeCommandBuilder {
    /**
     * Construct new {@link HybridCommandBuilder}.
     *
     * @param command The hybrid command class.
     */
    public HybridCommandBuilder(@NotNull HybridCommand command) {
        super(command);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull HybridCommandModel buildModel() {
        Class<? extends BaseCommand> clazz = command.getClass();
        RextraHybridCommand annotation = clazz.getAnnotation(RextraHybridCommand.class);

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

        HybridCommandModel model = new HybridCommandModel((HybridCommand) command, name, annotation.description());

        model.setEntryPoint(entryPoint)
                .addOptions(options)
                .addSubcommands(buildSubcommands())
                .addSubcommandGroups(buildSubcommandGroups());

        return (HybridCommandModel) buildSettings(buildLocalization(model, clazz), clazz);
    }

    /**
     * {@inheritDoc}
     *
     * @param model {@inheritDoc}
     * @param clazz {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    protected @NotNull HybridCommandModel buildLocalization(@NotNull CommandModel model,
                                                            @NotNull Class<? extends BaseCommand> clazz) {
        return ((HybridCommandModel) super.buildLocalization(model, clazz))
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
            if (parameter.isAnnotationPresent(HybridOption.class)) {
                HybridOption hybridOption = parameter.getAnnotation(HybridOption.class);

                HybridOptionData data = new HybridOptionData(buildOptionType(parameter.getType(), hybridOption.type()),
                        hybridOption.name(),
                        hybridOption.description(),
                        parameter.isAnnotationPresent(Required.class));

                data.addChoices(buildOptionChoices(parameter.getAnnotations()));

                options.add(data);
            }
        }

        return options;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link HybridSubcommandProperties}.
     */
    @Override
    protected List<HybridSubcommandProperties> buildSubcommands() {
        List<HybridSubcommandProperties> subcommands = new ArrayList<>();

        for (Method method : command.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RextraHybridSubcommand.class)) {
                continue;
            }

            RextraHybridSubcommand annotation = method.getAnnotation(RextraHybridSubcommand.class);

            String name = annotation.name().isEmpty() ? method.getName() : annotation.name();
            HybridSubcommandProperties subcommand = new HybridSubcommandProperties(method, name, annotation.description());

            subcommands.add(subcommand.setNameLocalizations(buildNameLocalizations(method))
                    .setDescriptionLocalizations(buildDescriptionLocalizations(method))
                    .addOptions(buildOptions(method)));
        }

        return subcommands;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link HybridSubcommandGroupProperties}.
     */
    @Override
    protected List<HybridSubcommandGroupProperties> buildSubcommandGroups() {
        List<HybridSubcommandGroupProperties> groups = new ArrayList<>();

        List<Class<?>> classes = Arrays.stream(command.getClass().getDeclaredClasses())
                .filter(clazz -> clazz.isAnnotationPresent(RextraHybridSubcommandGroup.class))
                .collect(Collectors.toList());

        for (Class<?> clazz : classes) {
            RextraHybridSubcommandGroup groupAnnotation = clazz.getAnnotation(RextraHybridSubcommandGroup.class);

            HybridSubcommandGroupProperties group = new HybridSubcommandGroupProperties(groupAnnotation.name(),
                    groupAnnotation.description());

            List<Method> entryPoints = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RextraHybridSubcommand.class))
                    .collect(Collectors.toList());

            List<HybridSubcommandProperties> subcommands = new ArrayList<>();

            for (Method entryPoint : entryPoints) {
                RextraHybridSubcommand subcommandAnnotation = entryPoint.getAnnotation(RextraHybridSubcommand.class);
                String name = subcommandAnnotation.name().isEmpty() ? entryPoint.getName() : subcommandAnnotation.name();

                HybridSubcommandProperties subcommand = new HybridSubcommandProperties(entryPoint, name, subcommandAnnotation.description());

                subcommand.setNameLocalizations(buildNameLocalizations(entryPoint))
                        .setDescriptionLocalizations(buildDescriptionLocalizations(entryPoint))
                        .addOptions(buildOptions(entryPoint));

                subcommands.add(subcommand);
            }

            HybridSubcommandGroup groupClassObject;

            try {
                groupClassObject = (HybridSubcommandGroup) clazz.getConstructor().newInstance();
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
