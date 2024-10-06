package com.dirtmuncher.controllers;

import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;
import com.dirtmuncher.services.impl.RobotActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The RestController that allows for the robot control requests to be handled and RobotActivityReqDTO to be processed.
 */
@RestController
@Slf4j
@CrossOrigin(origins = " * ", allowedHeaders = " * ")
@RequestMapping(value = "/api/v1")
public class RobotActionsController {
    private final RobotActivityService robotActivityService;

    public RobotActionsController(RobotActivityService robotActivityService) {
        this.robotActivityService = robotActivityService;
    }

    @PostMapping(value = "/execute")
    public ResponseEntity<RobotActivityRespDTO> getRobotActionsResult(@RequestBody RobotActivityReqDTO robotActivityReqDTO) {
        RobotActivityRespDTO robotActivityRespDTO = robotActivityService.getActivityResult(robotActivityReqDTO);
        if (robotActivityRespDTO != null) { //TODO: if validation is ok
            return ResponseEntity.ok(robotActivityRespDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
