package edu.java.bot.api.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import edu.java.bot.service.bot_service.handlers.LinkUpdateHandler;
import edu.java.requests.LinkUpdateRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UpdateController.class)
class UpdateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkUpdateHandler linkUpdateHandler;

    @Test
    @DisplayName("Status code OK")
    public void updateOkTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(
            1,
            "SomeLink.com",
            "Some description",
            List.of(1, 2, 3)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/update")
                    .content(objectMapper.writeValueAsString(linkUpdateRequest))
                    .contentType("application/json")
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    @DisplayName("Status code BAD_REQUEST")
//    public void updateBadRequestTest() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//
//        LinkUpdateRequestWrong linkUpdateRequestWrong = new LinkUpdateRequestWrong(
//            1,
//            "SomeLink.com",
//            "Some description"
//        );
//
//        MvcResult mvcResult = mockMvc.perform(
//                MockMvcRequestBuilders.post("/update")
//                    .content(objectMapper.writeValueAsString(linkUpdateRequestWrong))
//                    .contentType("application/json")
//            )
//            .andReturn();
//
//        ApiErrorResponse error = objectMapper.readValue(
//            mvcResult.getResponse().getContentAsString(),
//            ApiErrorResponse.class
//        );
//    }
//
//    private record LinkUpdateRequestWrong(
//        Integer id,
//        String uri,
//        String description
//    ) {
//
//    }
}
