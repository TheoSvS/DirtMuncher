package com.dirtmuncher.services.impl;


import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;
import com.dirtmuncher.services.def.IExecuteCommands;
import com.dirtmuncher.services.def.IRobotActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RobotActivityService implements IRobotActivityService {
    IExecuteCommands iExecuteCommands;
    Transformer transformer;

    public RobotActivityService(IExecuteCommands iExecuteCommands, Transformer transformer) {
        this.iExecuteCommands = iExecuteCommands;
        this.transformer = transformer;
    }

    public RobotActivityRespDTO getActivityResult(RobotActivityReqDTO reqDTO) {
        RobotActivityState robotState = transformer.reqDTOToDomain(reqDTO);
        iExecuteCommands.executePlan(robotState);
        return transformer.domainToRespDTO(robotState);
    }
}
