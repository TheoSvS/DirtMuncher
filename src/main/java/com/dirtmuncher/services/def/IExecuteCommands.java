package com.dirtmuncher.services.def;

import com.dirtmuncher.model.RobotActivityState;
import com.dirtmuncher.requests.RobotActivityReqDTO;

import java.util.List;

public interface IExecuteCommands {

    public RobotActivityState executePlan(RobotActivityReqDTO robotActivityReqDTO);
}
