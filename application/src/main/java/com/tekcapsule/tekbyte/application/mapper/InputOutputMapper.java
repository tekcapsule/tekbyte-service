package com.tekcapsule.tekbyte.application.mapper;

import com.tekcapsule.core.domain.Command;
import com.tekcapsule.core.domain.ExecBy;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.tekbyte.application.function.input.CreateInput;
import com.tekcapsule.tekbyte.application.function.input.DisableInput;
import com.tekcapsule.tekbyte.application.function.input.UpdateInput;
import com.tekcapsule.tekbyte.domain.command.CreateCommand;
import com.tekcapsule.tekbyte.domain.command.DisableCommand;
import com.tekcapsule.tekbyte.domain.command.UpdateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

@Slf4j
public final class InputOutputMapper {
    private InputOutputMapper() {

    }

    public static final BiFunction<Command, Origin, Command> addOrigin = (command, origin) -> {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        command.setChannel(origin.getChannel());
        command.setExecBy(ExecBy.builder().tenantId(origin.getTenantId()).userId(origin.getUserId()).build());
        command.setExecOn(utc.toString());
        return command;
    };

    public static final BiFunction<CreateInput, Origin, CreateCommand> buildCreateCommandFromCreateInput = (createInput, origin) -> {
        CreateCommand createCommand =  CreateCommand.builder().build();
        BeanUtils.copyProperties(createInput, createCommand);
        addOrigin.apply(createCommand, origin);
        return createCommand;
    };

    public static final BiFunction<UpdateInput, Origin, UpdateCommand> buildUpdateCommandFromUpdateInput = (updateInput, origin) -> {
        UpdateCommand updateCommand = UpdateCommand.builder().build();
        BeanUtils.copyProperties(updateInput, updateCommand);
        addOrigin.apply(updateCommand, origin);
        return updateCommand;
    };

    public static final BiFunction<DisableInput, Origin, DisableCommand> buildDisableCommandFromDisableInput = (disableInput, origin) -> {
        DisableCommand disableCommand =  DisableCommand.builder().build();
        BeanUtils.copyProperties(disableInput, disableCommand);
        addOrigin.apply(disableCommand, origin);
        return disableCommand;
    };

}
