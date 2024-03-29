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
import typeqast.entities.Address;
import typeqast.entities.dto.AddressDTO;
import typeqast.service.AddressService;
import typeqast.util.assertions.AddressAssertions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService mockAddressService;

    @Test
    void addAddressesTest() throws Exception {

        AddressDTO mockAddress = new AddressDTO("country1", "city1", "street1", 1);

        String addressAsString = new ObjectMapper().writeValueAsString(mockAddress);

        mockAddress.setId(BigInteger.valueOf(1));
        when(mockAddressService.addAddress(any(), any())).thenReturn(mockAddress);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        RequestBuilder requestBuilder = (post(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON).params(params).content(addressAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        AddressDTO responseAddress = new ObjectMapper().readValue(responseBodyString, AddressDTO.class);

        AddressAssertions.execute(mockAddress, responseAddress);

    }

    @Test
    void updateAddressTest() throws Exception {

        AddressDTO mockAddress = new AddressDTO("country1", "city1", "street1", 1);

        String addressAsString = new ObjectMapper().writeValueAsString(mockAddress);

        mockAddress.setId(BigInteger.valueOf(1));
        when(mockAddressService.addAddress(any(), any())).thenReturn(mockAddress);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        RequestBuilder requestBuilder = (post(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON).params(params).content(addressAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        AddressDTO responseAddress = new ObjectMapper().readValue(responseBodyString, AddressDTO.class);

        AddressAssertions.execute(mockAddress, responseAddress);

        mockAddress = new AddressDTO("country1_updated", "city1_updated", "street1_updated", 2);
        mockAddress.setId(BigInteger.valueOf(1));

        addressAsString = new ObjectMapper().writeValueAsString(mockAddress);

        params.add(RequestParams.ADDRESS_ID, String.valueOf(1));

        requestBuilder = (put(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON).content(addressAsString).params(params));

        when(mockAddressService.updateAddress(any(), any())).thenReturn(mockAddress);

        result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        responseAddress = new ObjectMapper().readValue(responseBodyString, AddressDTO.class);

        AddressAssertions.execute(mockAddress, responseAddress);

    }

    @Test
    void getAddressesTest() throws Exception {
        RequestBuilder requestBuilder = (get(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON));

        AddressDTO address = new AddressDTO("country1", "city1", "street1", 1);

        List<AddressDTO> addressList = new ArrayList<>();
        addressList.add(address);

        address = new AddressDTO("country2", "city2", "street2", 2);
        addressList.add(address);

        given(mockAddressService.getAddresses(any())).willReturn(addressList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        List<Address> responseAddressList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Address>>() {
        });

        Assertions.assertNotNull(responseAddressList);
        Assertions.assertEquals(2, responseAddressList.size());

    }

}
