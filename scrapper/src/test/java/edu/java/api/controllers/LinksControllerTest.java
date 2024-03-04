package edu.java.api.controllers;

import edu.java.database.InMemoryDataBase;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(LinksController.class)
class LinksControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InMemoryDataBase inMemoryDataBase;

    @Test
    public void allTrackedLinksTest() throws Exception {
        Mockito.when(inMemoryDataBase.allDataByKey(12)).thenReturn(Set.of("SomeLink.com"));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/links")
                    .header("id", 12)
                    .contentType("application/json")
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
