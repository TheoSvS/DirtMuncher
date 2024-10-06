package com.dirtmuncher.services.impl;


import com.dirtmuncher.model.DirtPatchMap;
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

    public RobotActivityService(IExecuteCommands iExecuteCommands) {
        this.iExecuteCommands = iExecuteCommands;
    }

    public RobotActivityRespDTO getActivityResult(RobotActivityReqDTO robotActivityReqDTO) {
        DirtPatchMap dirtPatchMap = new DirtPatchMap(robotActivityReqDTO.getPatches().toArray(new int[0][]));
        RobotActivityState finalState = iExecuteCommands.executePlan(robotActivityReqDTO);
        return new RobotActivityRespDTO(finalState.getCurrPos(), dirtPatchMap.getCleanedPatches());
    }
}
