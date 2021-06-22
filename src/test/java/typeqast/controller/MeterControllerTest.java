package typeqast.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@WebMvcTest(MeterController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class MeterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MeterService mockMeterService;

    @Test
    public void addMeterTest() throws Exception {

        Meter mockMeter = new Meter("meter1");

        String meterAsString = new ObjectMapper().writeValueAsString(mockMeter);

        mockMeter.setId(BigInteger.valueOf(1));

        when(mockMeterService.addMeter(any(), any())).thenReturn(mockMeter);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        RequestBuilder requestBuilder = (post(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON).params(params).content(meterAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        Meter responseMeter = new ObjectMapper().readValue(responseBodyString, Meter.class);

        MeterAssertions.execute(mockMeter, responseMeter);

    }

    @Test
    public void updateMeterTest() throws Exception {

        Meter mockMeter = new Meter("meter1");

        String meterAsString = new ObjectMapper().writeValueAsString(mockMeter);

        mockMeter.setId(BigInteger.valueOf(1));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        when(mockMeterService.addMeter(any(), any())).thenReturn(mockMeter);

        RequestBuilder requestBuilder = (post(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON).params(params).content(meterAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        Meter responseMeter = new ObjectMapper().readValue(responseBodyString, Meter.class);

        MeterAssertions.execute(mockMeter, responseMeter);

        mockMeter.setName("meter1_updated");

        meterAsString = new ObjectMapper().writeValueAsString(mockMeter);

        requestBuilder = (put(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON).params(params).content(meterAsString));

        when(mockMeterService.updateMeter(any(), any())).thenReturn(mockMeter);

        result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        responseMeter = new ObjectMapper().readValue(responseBodyString, Meter.class);

        MeterAssertions.execute(mockMeter, responseMeter);

    }

    @Test
    public void getMetersTest() throws Exception {

        RequestBuilder requestBuilder = (get(RestEndpoints.METERS).contentType(MediaType.APPLICATION_JSON));

        Meter mockResultMeter = new Meter("meter1");

        List<Meter> meterList = new ArrayList<>();
        meterList.add(mockResultMeter);

        mockResultMeter = new Meter("meter2");

        meterList.add(mockResultMeter);

        given(mockMeterService.getMeters(null)).willReturn(meterList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        List<Meter> responseMeterList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Meter>>() {
        });

        Assert.assertNotNull(responseMeterList);
        Assert.assertEquals(2, responseMeterList.size());


    }


    @Test
    public void aggregateReadingTest() throws Exception {

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

        Assert.assertNotNull(result.getResponse());

        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        AggregateReading responseAggregateReading = new ObjectMapper().readValue(responseBodyString, AggregateReading.class);

        Assert.assertNotNull(responseAggregateReading);
        Assert.assertEquals(aggregateReadingResponse.getTotal(), responseAggregateReading.getTotal());
        Assert.assertEquals(mockResultReading.getYear(), responseAggregateReading.getYear());

    }


}
