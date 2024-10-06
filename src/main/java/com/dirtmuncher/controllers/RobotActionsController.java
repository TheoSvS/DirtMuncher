package com.dirtmuncher.controllers;

import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;
import com.dirtmuncher.services.def.IRobotActivityService;
import jakarta.validation.Valid;
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
    private final IRobotActivityService iRobotActivityService;

    public RobotActionsController(IRobotActivityService iRobotActivityService) {
        this.iRobotActivityService = iRobotActivityService;
    }

    @PostMapping(value = "/execute")
    public ResponseEntity<RobotActivityRespDTO> getRobotActionsResult(@Valid @RequestBody RobotActivityReqDTO robotActivityReqDTO) {
        RobotActivityRespDTO robotActivityRespDTO = iRobotActivityService.getActivityResult(robotActivityReqDTO);
        if (robotActivityRespDTO != null) { //TODO: if validation is ok
            return ResponseEntity.ok(robotActivityRespDTO);
        }
        return ResponseEntity.badRequest().build();
    }
}
