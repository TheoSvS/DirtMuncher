package com.dirtmuncher;

import com.dirtmuncher.configuration.LoggingConfiguration;
import com.dirtmuncher.controllers.RobotActionsController;
import com.dirtmuncher.requests.RobotActivityReqDTO;
import com.dirtmuncher.responses.RobotActivityRespDTO;
import com.dirtmuncher.services.impl.RobotActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class RobotActionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private RobotActivityService mockRobotActivityService = Mockito.mock(RobotActivityService.class);

    @Mock
    private LoggingConfiguration mockLoggingConfiguration;

    @InjectMocks
    private RobotActionsController robotActionsController;

    @Test
    public void shouldReturnOk_shouldAssertExpectedResult() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(new RobotActivityReqDTO(
                new int[]{5, 5},
                new int[]{1, 2},
                List.of(
                        new int[]{1, 0},
                        new int[]{2, 2},
                        new int[]{2, 3}
                ),
                "NNESEESWNWW"
        ));
        System.out.println(jsonRequest);

        MockHttpServletResponse result = mockMvc.perform(post("/api/v1/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();


/*              .andExpect(jsonPath("$.coords[0]").value(0))
                .andExpect(jsonPath("$.coords[1]").value(0))
                .andExpect(jsonPath("$.patches").value(1));*/


        String jsonResponse = result.getContentAsString();
        // Convert the response JSON into a RobotActivityRespDTO object
        ObjectMapper objectMapper = new ObjectMapper();
        RobotActivityRespDTO response = objectMapper.readValue(jsonResponse, RobotActivityRespDTO.class);

        // Assert the deserialized response object
        assertEquals(3, response.getCoords()[0]);
        assertEquals(3, response.getCoords()[1]);
        assertEquals(1, response.getPatches());

    }

    @Test
    public void negativeRoomSize_shouldReturnBadRequest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(new RobotActivityReqDTO(
                new int[]{-5, 5},
                new int[]{1, 2},
                List.of(
                        new int[]{1, 0},
                        new int[]{2, 2},
                        new int[]{2, 3}
                ),
                "NNESEESWNWW"
        ));
        System.out.println(jsonRequest);

        MockHttpServletResponse result = mockMvc.perform(post("/api/v1/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        System.out.println(result.getContentAsString());
    }

    @Test
    public void negativeStartingCoords_shouldReturnBadRequest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(new RobotActivityReqDTO(
                new int[]{5, 5},
                new int[]{-1, 2},
                List.of(
                        new int[]{1, 0},
                        new int[]{2, 2},
                        new int[]{2, 3}
                ),
                "NNESEESWNWW"
        ));
        System.out.println(jsonRequest);

        MockHttpServletResponse result = mockMvc.perform(post("/api/v1/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        System.out.println(result.getContentAsString());
    }

    @Test
    public void negativePatchCoords_shouldReturnBadRequest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(new RobotActivityReqDTO(
                new int[]{5, 5},
                new int[]{1, 2},
                List.of(
                        new int[]{1, 0},
                        new int[]{2, 2},
                        new int[]{-2, 3}
                ),
                "NNESEESWNWW"
        ));
        System.out.println(jsonRequest);

        MockHttpServletResponse result = mockMvc.perform(post("/api/v1/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        System.out.println(result.getContentAsString());
    }

    @Test
    public void nullField_shouldReturnBadRequest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(new RobotActivityReqDTO(
                null,
                new int[]{1, 2},
                List.of(
                        new int[]{1, 0},
                        new int[]{2, 2},
                        new int[]{2, 3}
                ),
                "NNESEESWNWW"
        ));
        System.out.println(jsonRequest);

        MockHttpServletResponse result = mockMvc.perform(post("/api/v1/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        System.out.println(result.getContentAsString());
    }

}