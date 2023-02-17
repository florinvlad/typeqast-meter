package typeqast.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import typeqast.constants.RequestParams;
import typeqast.constants.RestEndpoints;
import typeqast.entities.Reading;
import typeqast.entities.dto.ReadingDTO;
import typeqast.service.ReadingService;
import typeqast.util.assertions.ReadingAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReadingController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class ReadingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ReadingService mockReadingService;

    @Test
    void addReadingTest() throws Exception {

        Integer year = 2000;
        Month month = Month.APRIL;
        Long value = 1234L;

        ReadingDTO mockReadingDTO = new ReadingDTO(year, month, value);

        String readingAsString = new ObjectMapper().writeValueAsString(mockReadingDTO);

        mockReadingDTO.setId(BigInteger.valueOf(1));

        when(mockReadingService.addReading(any(), any())).thenReturn(mockReadingDTO);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.METER_ID, String.valueOf(1));

        RequestBuilder requestBuilder = (post(RestEndpoints.READINGS).contentType(MediaType.APPLICATION_JSON).content(readingAsString).params(params));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        ReadingDTO responseReading = new ObjectMapper().readValue(responseBodyString, ReadingDTO.class);

        ReadingAssertions.execute(mockReadingDTO, responseReading);

    }

    @Test
    void getReadingsTest() throws Exception {

        RequestBuilder requestBuilder = (get(RestEndpoints.READINGS).contentType(MediaType.APPLICATION_JSON));

        ReadingDTO mockResultReadingDTO = new ReadingDTO(2000, Month.APRIL, 1234L);

        List<ReadingDTO> readingList = new ArrayList<>();
        readingList.add(mockResultReadingDTO);

        mockResultReadingDTO = new ReadingDTO(2000, Month.JUNE, 1111L);

        readingList.add(mockResultReadingDTO);

        given(mockReadingService.getReadings()).willReturn(readingList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        List<Reading> responseReadingList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Reading>>() {
        });

        Assertions.assertNotNull(responseReadingList);
        Assertions.assertEquals(2, responseReadingList.size());


    }

}
