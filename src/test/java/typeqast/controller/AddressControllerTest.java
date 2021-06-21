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
import org.springframework.http.HttpStatus;
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
import typeqast.entities.Address;
import typeqast.entities.response.AddressResponse;
import typeqast.service.AddressService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService mockAddressService;

    @Test
    public void getAddresssTest() throws Exception {
        RequestBuilder requestBuilder = (get(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON));

        Address address = new Address("country1", "city1", "street1", 1);

        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        address = new Address("country2", "city2", "street2", 2);
        addressList.add(address);

        given(mockAddressService.getAddresses(any())).willReturn(addressList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        List<Address> responseAddressList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Address>>() {
        });

        Assert.assertNotNull(responseAddressList);
        Assert.assertEquals(2, responseAddressList.size());

    }

    @Test
    public void addAddresssTest() throws Exception {

        String country = "country1";
        String city = "city1";
        String street = "street1";
        Integer number = 1;

        Address mockAddress = new Address(country, city, street, number);

        String addressAsString = new ObjectMapper().writeValueAsString(mockAddress);

        mockAddress.setId(BigInteger.valueOf(1));
        when(mockAddressService.addAddress(any(), any())).thenReturn(new AddressResponse(mockAddress, HttpStatus.CREATED));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        RequestBuilder requestBuilder = (post(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON).params(params).content(addressAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        Address responseAddress = new ObjectMapper().readValue(responseBodyString, Address.class);

        Assert.assertNotNull(responseAddress.getId());
        Assert.assertEquals(country, responseAddress.getCountry());
        Assert.assertEquals(city, responseAddress.getCity());
        Assert.assertEquals(street, responseAddress.getStreet());
        Assert.assertEquals(number, responseAddress.getNumber());
        Assert.assertEquals(mockAddress.getId(), responseAddress.getId());

    }

    @Test
    public void updateAddressTest() throws Exception {

        String country = "country1";
        String city = "city1";
        String street = "street1";
        Integer number = 1;

        Address mockAddress = new Address(country, city, street, number);

        String addressAsString = new ObjectMapper().writeValueAsString(mockAddress);

        mockAddress.setId(BigInteger.valueOf(1));
        when(mockAddressService.addAddress(any(), any())).thenReturn(new AddressResponse(mockAddress, HttpStatus.CREATED));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(RequestParams.CLIENT_ID, String.valueOf(1));
        RequestBuilder requestBuilder = (post(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON).params(params).content(addressAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        Address responseAddress = new ObjectMapper().readValue(responseBodyString, Address.class);

        Assert.assertNotNull(responseAddress.getId());
        Assert.assertEquals(country, responseAddress.getCountry());
        Assert.assertEquals(city, responseAddress.getCity());
        Assert.assertEquals(street, responseAddress.getStreet());
        Assert.assertEquals(number, responseAddress.getNumber());
        Assert.assertEquals(mockAddress.getId(), responseAddress.getId());

        country = "country1_updated";
        city = "city1_updated";
        street = "street1_updated";
        number = 11;

        mockAddress.setCountry(country);
        mockAddress.setCity(city);
        mockAddress.setStreet(street);
        mockAddress.setNumber(number);

        addressAsString = new ObjectMapper().writeValueAsString(mockAddress);

        requestBuilder = (put(RestEndpoints.ADDRESSES).contentType(MediaType.APPLICATION_JSON).content(addressAsString));

        when(mockAddressService.updateAddress(any(), any())).thenReturn(new AddressResponse(mockAddress, HttpStatus.OK));

        result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        responseAddress = new ObjectMapper().readValue(responseBodyString, Address.class);

        Assert.assertNotNull(responseAddress.getId());
        Assert.assertEquals(country, responseAddress.getCountry());
        Assert.assertEquals(city, responseAddress.getCity());
        Assert.assertEquals(street, responseAddress.getStreet());
        Assert.assertEquals(number, responseAddress.getNumber());
        Assert.assertEquals(mockAddress.getId(), responseAddress.getId());

    }

}