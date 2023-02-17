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
import typeqast.entities.AggregateReading;
import typeqast.entities.Meter;
import typeqast.entities.Reading;
import typeqast.entities.dto.MeterDTO;
import typeqast.service.MeterService;
import typeqast.util.assertions.MeterAssertions;

import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeterController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class MeterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MeterService mockMeterService;

    @Test
    void addMeterTest() throws Exception {

        MeterDTO mockMeterDTO = new MeterDTO("meter1");

        String meterAsString = new ObjectMapper().writeValueAsString(mockMeterDTO);

        mockMeterDTO.setId(BigInteger.valueOf(1));

        when(mockMeterService.addMeter(any(), any())).thenReturn(mockMeterDTO);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        RequestBuilder requestBuilder = (post(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON).params(params).content(meterAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        MeterDTO responseMeterDTO = new ObjectMapper().readValue(responseBodyString, MeterDTO.class);

        MeterAssertions.execute(mockMeterDTO, responseMeterDTO);

    }

    @Test
    void updateMeterTest() throws Exception {

        MeterDTO mockMeterDTO = new MeterDTO("meter1");

        String meterAsString = new ObjectMapper().writeValueAsString(mockMeterDTO);

        mockMeterDTO.setId(BigInteger.valueOf(1));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        when(mockMeterService.addMeter(any(), any())).thenReturn(mockMeterDTO);

        RequestBuilder requestBuilder = (post(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON).params(params).content(meterAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        MeterDTO responseMeterDTO = new ObjectMapper().readValue(responseBodyString, MeterDTO.class);

        MeterAssertions.execute(mockMeterDTO, responseMeterDTO);

        mockMeterDTO.setName("meter1_updated");

        meterAsString = new ObjectMapper().writeValueAsString(mockMeterDTO);

        params.add(RequestParams.METER_ID, String.valueOf(2));

        requestBuilder = (put(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON).params(params).content(meterAsString).params(params));

        when(mockMeterService.updateMeter(any(), any())).thenReturn(mockMeterDTO);

        result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        responseMeterDTO = new ObjectMapper().readValue(responseBodyString, MeterDTO.class);

        MeterAssertions.execute(mockMeterDTO, responseMeterDTO);

    }

    @Test
    void getMetersTest() throws Exception {

        RequestBuilder requestBuilder = (get(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON));

        MeterDTO mockResultMeterDTO = new MeterDTO("meter1");

        List<MeterDTO> meterList = new ArrayList<>();
        meterList.add(mockResultMeterDTO);

        mockResultMeterDTO = new MeterDTO("meter2");

        meterList.add(mockResultMeterDTO);

        given(mockMeterService.getMeters(null)).willReturn(meterList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        List<Meter> responseMeterList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Meter>>() {
        });

        Assertions.assertNotNull(responseMeterList);
        Assertions.assertEquals(2, responseMeterList.size());


    }


    @Test
    void aggregateReadingTest() throws Exception {

        Meter meter = new Meter("test_meter");
        meter.setId(BigInteger.valueOf(1));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.YEAR, String.valueOf(2020));
        params.add(RequestParams.METER_ID, String.valueOf(1));

        RequestBuilder requestBuilder = (get(RestEndpoints.METERS + RestEndpoints.AGGREGATE).contentType(MediaType.APPLICATION_JSON).params(params));

        Reading mockResultReading = new Reading(2020, Month.APRIL, 1234L);
        mockResultReading.setId(BigInteger.valueOf(1));

        meter.addReading(mockResultReading);

        mockResultReading = new Reading(2020, Month.JUNE, 1111L);
        mockResultReading.setId(BigInteger.valueOf(2));

        meter.addReading(mockResultReading);

        AggregateReading aggregateReadingResponse = new AggregateReading(2020, 1234L);

        given(mockMeterService.getAggregateReadings(any(), any())).willReturn(aggregateReadingResponse);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        Assertions.assertNotNull(result.getResponse());

        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        AggregateReading responseAggregateReading = new ObjectMapper().readValue(responseBodyString, AggregateReading.class);

        Assertions.assertNotNull(responseAggregateReading);
        Assertions.assertEquals(aggregateReadingResponse.getTotal(), responseAggregateReading.getTotal());
        Assertions.assertEquals(mockResultReading.getYear(), responseAggregateReading.getYear());

    }


}
