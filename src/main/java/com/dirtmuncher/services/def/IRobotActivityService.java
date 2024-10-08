package com.dirtmuncher.services.def;

import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;

/**
 * Interface for robot activity service
 */
public interface IRobotActivityService {
    RobotActivityRespDTO getActivityResult(RobotActivityReqDTO robotActivityReqDTO);
}
