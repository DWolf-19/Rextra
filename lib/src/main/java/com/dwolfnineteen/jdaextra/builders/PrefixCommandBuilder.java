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

import com.dwolfnineteen.jdaextra.annotations.ExtraPrefixCommand;
import com.dwolfnineteen.jdaextra.annotations.options.PrefixOption;
import com.dwolfnineteen.jdaextra.annotations.options.Required;
import com.dwolfnineteen.jdaextra.annotations.subcommands.RextraPrefixSubcommand;
import com.dwolfnineteen.jdaextra.annotations.subcommands.groups.RextraPrefixSubcommandGroup;
import com.dwolfnineteen.jdaextra.commands.BaseCommand;
import com.dwolfnineteen.jdaextra.commands.PrefixCommand;
import com.dwolfnineteen.jdaextra.commands.subcommandgroups.PrefixSubcommandGroup;
import com.dwolfnineteen.jdaextra.exceptions.CommandAnnotationNotFoundException;
import com.dwolfnineteen.jdaextra.exceptions.buildtime.CommandPropertyNotFoundException;
import com.dwolfnineteen.jdaextra.models.commands.PrefixCommandModel;
import com.dwolfnineteen.jdaextra.models.subcommands.PrefixSubcommandProperties;
import com.dwolfnineteen.jdaextra.models.subcommands.groups.PrefixSubcommandGroupProperties;
import com.dwolfnineteen.jdaextra.options.data.CommandOptionData;
import com.dwolfnineteen.jdaextra.options.data.PrefixOptionData;
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
 * Prefix command builder.
 */
public class PrefixCommandBuilder extends CommandBuilder {
    /**
     * Construct new {@link PrefixCommandBuilder}.
     *
     * @param command The prefix command class.
     */
    public PrefixCommandBuilder(@NotNull PrefixCommand command) {
        super(command);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull PrefixCommandModel buildModel() {
        Class<? extends BaseCommand> clazz = command.getClass();
        ExtraPrefixCommand annotation = clazz.getAnnotation(ExtraPrefixCommand.class);

        if (annotation == null) {
            throw new CommandAnnotationNotFoundException();
        }

        Method mainEntryPoint = buildEntryPoint();
        String name;
        String description = annotation.description().isEmpty() ? null : annotation.description();
        List<CommandOptionData> options = mainEntryPoint == null ? Collections.emptyList() : buildOptions(mainEntryPoint);

        if (mainEntryPoint == null) {
            if (annotation.name().isEmpty()) {
                throw new CommandPropertyNotFoundException("could not found command name in annotation or pick up a method name");
            }

            name = annotation.name();
        } else {
            name = annotation.name().isEmpty()
                    ? mainEntryPoint.getName()
                    : annotation.name();
        }

        PrefixCommandModel model = new PrefixCommandModel((PrefixCommand) command, name, description);

        model.setEntryPoint(mainEntryPoint)
                .addOptions(options)
                .addSubcommands(buildSubcommands())
                .addSubcommandGroups(buildSubcommandGroups());

        return (PrefixCommandModel) buildSettings(model, clazz);
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
            if (parameter.isAnnotationPresent(PrefixOption.class)) {
                PrefixOption prefixOption = parameter.getAnnotation(PrefixOption.class);

                PrefixOptionData data = new PrefixOptionData(buildOptionType(parameter.getType(), prefixOption.type()),
                        prefixOption.name(),
                        prefixOption.description().isEmpty() ? null : prefixOption.description(),
                        parameter.isAnnotationPresent(Required.class));

                options.add(data);
            }
        }

        return options;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link PrefixSubcommandProperties}.
     */
    @Override
    protected @NotNull List<PrefixSubcommandProperties> buildSubcommands() {
        List<PrefixSubcommandProperties> subcommands = new ArrayList<>();

        for (Method method : command.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(RextraPrefixSubcommand.class)) {
                continue;
            }

            RextraPrefixSubcommand annotation = method.getAnnotation(RextraPrefixSubcommand.class);

            String name = annotation.name().isEmpty() ? method.getName() : annotation.name();
            PrefixSubcommandProperties subcommand = new PrefixSubcommandProperties(method, name, annotation.description());

            subcommands.add(subcommand.addOptions(buildOptions(method)));
        }

        System.out.println(subcommands);
        return subcommands;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@link List} of {@link PrefixSubcommandProperties}.
     */
    @Override
    protected @NotNull List<PrefixSubcommandGroupProperties> buildSubcommandGroups() {
        List<PrefixSubcommandGroupProperties> groups = new ArrayList<>();

        List<Class<?>> classes = Arrays.stream(command.getClass().getDeclaredClasses())
                .filter(clazz -> clazz.isAnnotationPresent(RextraPrefixSubcommandGroup.class))
                .collect(Collectors.toList());

        for (Class<?> clazz : classes) {
            RextraPrefixSubcommandGroup groupAnnotation = clazz.getAnnotation(RextraPrefixSubcommandGroup.class);

            PrefixSubcommandGroupProperties group = new PrefixSubcommandGroupProperties(groupAnnotation.name(),
                    groupAnnotation.description());

            List<Method> entryPoints = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RextraPrefixSubcommand.class))
                    .collect(Collectors.toList());

            List<PrefixSubcommandProperties> subcommands = new ArrayList<>();

            for (Method entryPoint : entryPoints) {
                RextraPrefixSubcommand subcommandAnnotation = entryPoint.getAnnotation(RextraPrefixSubcommand.class);
                String name = subcommandAnnotation.name().isEmpty() ? entryPoint.getName() : subcommandAnnotation.name();

                PrefixSubcommandProperties subcommand = new PrefixSubcommandProperties(entryPoint, name, subcommandAnnotation.description());

                subcommands.add(subcommand.addOptions(buildOptions(entryPoint)));
            }

            PrefixSubcommandGroup groupClassObject;

            try {
                groupClassObject = (PrefixSubcommandGroup) clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException exception) {
                // TODO(?): Custom exception
                throw new RuntimeException(exception);
            }

            groups.add(group.setGroupClass(groupClassObject).addSubcommands(subcommands));
        }

        return groups;
    }
}
