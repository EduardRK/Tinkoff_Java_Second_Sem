package edu.java.api.controllers;

//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureMockMvc
//class LinksControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private InMemoryDataBase inMemoryDataBase;
//
//    @Test
//    public void allTrackedLinksTest() throws Exception {
////        Mockito.when(inMemoryDataBase.allDataByKey(12)).thenReturn(Set.of("SomeLink.com"));
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/links")
//                    .header("id", 12)
//                    .contentType("application/json")
//            )
//            .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//}
