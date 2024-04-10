package edu.java.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import edu.java.requests.AddLinkRequest;
import edu.java.requests.RemoveLinkRequest;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import edu.java.service.ScrapperService;
import edu.java.service.exception.ExceptionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DefaultController.class)
class DefaultControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScrapperService scrapperService;

    @MockBean
    private ExceptionService exceptionService;

    @Test
    @Rollback
    public void allTrackedLinksTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        ListLinksResponse listLinksResponse = new ListLinksResponse(
            new ArrayList<>(List.of(
                new LinkResponse(12, "somelink.com"))
            ),
            1
        );

        Mockito.when(scrapperService.listAll(12))
            .thenReturn(listLinksResponse);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/links")
                    .header("Tg-Chat-Id", 12)
                    .contentType("application/json")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Assertions.assertEquals(
            objectMapper.writeValueAsString(listLinksResponse),
            mvcResult.getResponse().getContentAsString()
        );
    }

    @Test
    @Rollback
    void addNewTrackLink() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        AddLinkRequest addLinkRequest = new AddLinkRequest("somelink.com");
        LinkResponse linkResponse = new LinkResponse(12, "somelink.com");

        Mockito.when(scrapperService.add(12, addLinkRequest.link()))
            .thenReturn(linkResponse);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/links")
                    .header("Tg-Chat-Id", 12)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(addLinkRequest))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Assertions.assertEquals(
            objectMapper.writeValueAsString(linkResponse),
            mvcResult.getResponse().getContentAsString()
        );
    }

    @Test
    @Rollback
    void untrackLink() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest("somelink.com");
        LinkResponse linkResponse = new LinkResponse(12, "somelink.com");

        Mockito.when(scrapperService.remove(12, removeLinkRequest.link()))
            .thenReturn(linkResponse);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.delete("/links")
                    .header("Tg-Chat-Id", 12)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(removeLinkRequest))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        Assertions.assertEquals(
            objectMapper.writeValueAsString(linkResponse),
            mvcResult.getResponse().getContentAsString()
        );
    }

    @Test
    @Rollback
    void registerChat() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tg-chat/12")
                    .contentType("application/json")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    @Test
    @Rollback
    void deleteChat() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tg-chat/12")
                    .contentType("application/json")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }
}
