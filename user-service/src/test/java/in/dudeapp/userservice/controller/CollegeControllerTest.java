package in.dudeapp.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.dudeapp.userservice.dto.request.CollegeRequest;
import in.dudeapp.userservice.dto.response.CollegeDTO;
import in.dudeapp.userservice.dto.response.CollegeResponse;
import in.dudeapp.userservice.dto.response.PageDTO;
import in.dudeapp.userservice.service.impl.CollegeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author nandhan, Created on 17/07/22
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CollegeControllerTest {

    @MockBean
    private CollegeServiceImpl collegeService;

    @InjectMocks
    private CollegeController controller;

    @Autowired
    private MockMvc mockMvc;
    private CollegeRequest collegeRequest1;
    private CollegeRequest collegeRequest2;
    private CollegeResponse collegeResponse;
    private CollegeDTO collegeDTO;
    private CollegeDTO collegeDTO2;
    private PageDTO pageDTO;
    private ObjectMapper writer;


    @BeforeEach
    void setUp() {
        collegeDTO = generateDTO("12","Caltech","pasadena","LA","LA","CA",4423);
        collegeDTO2 = generateDTO("13","cornell","ithaca","ithaca","ny","ny",4324);
        pageDTO = PageDTO.builder().size(2).pageNumber(0).totalResults(2).build();
        collegeResponse = CollegeResponse.builder().data(List.of(collegeDTO,collegeDTO2)).page(pageDTO).build();
        collegeRequest1 = generateRequest("19","Harvard University","Cambridge","Boston","Boston","Massachusetts",6565);
        collegeRequest2 = generateRequest("20","dkafdlsj","ithaca","ithaca","ny","ny",4324);
        writer = new ObjectMapper();
    }

    @Test
    void getCollegesByName() throws Exception {
        String requestString = "/college/getCollegesByName?name=c";
        //System.out.println("contents of the response - "+collegeResponse );
        String responseString = writer.writeValueAsString(collegeResponse);
        Mockito.when(collegeService.getCollegeByName(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt())).
                thenReturn(collegeResponse);

        mockMvc.perform(get(requestString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseString));

    }

    @Test
    void saveCollege() throws Exception {
        String contentString = writer.writeValueAsString(collegeRequest1);
        when(collegeService.addCollege(any(CollegeRequest.class))).thenReturn(collegeDTO);
        //willDoNothing().given(collegeService).addCollege(collegeRequest1);
        mockMvc.perform(post("/college/").contentType(MediaType.APPLICATION_JSON)
                .content(contentString))
                .andExpect(status().isCreated());
                //.andExpect(content().string("College is Added!"));
    }

    @Test
    void updateCollege() throws Exception {
        String contentString = writer.writeValueAsString(collegeRequest1);
        String requestString = "/college/5";
        when(collegeService.updateCollege(any(CollegeRequest.class), anyString())).thenReturn(collegeDTO);
        //willDoNothing().given(collegeService).updateCollege(any(CollegeRequest.class),anyString());
        mockMvc.perform(put(requestString).contentType(MediaType.APPLICATION_JSON)
                        .content(contentString))
                .andExpect(status().isOk());
                //.andExpect(content().string("College is Updated!"));

    }

    @Test
    void getCollegeById() throws Exception {
        String responseString = writer.writeValueAsString(collegeDTO);
        when(collegeService.getCollegeById(anyString())).thenReturn(collegeDTO);
        mockMvc.perform(get("/college/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseString));
    }

    @Test
    void deleteCollege() throws Exception {
        willDoNothing().given(collegeService).deleteCollegeById(anyString());
        mockMvc.perform(delete("/college/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("College deleted successfully!"));
    }

    private CollegeDTO generateDTO(String id, String name,
                                   String street, String city,
                                   String district, String state,
                                   int pincode) {
        return CollegeDTO.builder().id(id).name(name).state(state)
                .street(street).city(city).district(district)
                .pincode(pincode).build();
    }

    private CollegeRequest generateRequest(String id, String name,
                                   String street, String city,
                                   String district, String state,
                                   int pincode) {
        return CollegeRequest.builder().name(name).state(state)
                .street(street).city(city).district(district)
                .pincode(pincode).build();

    }
}