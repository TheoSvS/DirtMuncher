package com.dirtmuncher.services.impl;

import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;
import org.springframework.stereotype.Component;

@Component
public class Transformer {
    public RobotActivityState reqDTOToDomain(RobotActivityReqDTO reqDTO){
        return new RobotActivityState(reqDTO.getRoomSize(),reqDTO.getCoords(), reqDTO.getPatches(),reqDTO.getInstructions(),0);
    }

    public RobotActivityReqDTO domainToReqDTO(RobotActivityState activityState){
        return new RobotActivityReqDTO(activityState.getRoom().getDimensions(),activityState.getCurrPos().getCoords(), activityState.getPatches(),activityState.getInstructions());
    }

    public RobotActivityRespDTO domainToRespDTO(RobotActivityState activityState){
        return new RobotActivityRespDTO(activityState.getCurrPos(), activityState.getCleanedCounter());
    }


}
