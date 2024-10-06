package com.dirtmuncher.services.def;

import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;

public interface IRobotActivityService {
    RobotActivityRespDTO getActivityResult(RobotActivityReqDTO robotActivityReqDTO);
}
