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
import typeqast.constants.RestEndpoints;
import typeqast.entities.Client;
import typeqast.service.AddressService;
import typeqast.service.ClientService;
import typeqast.service.MeterService;
import typeqast.util.assertions.ClientAssertions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService mockClientService;

    @MockBean
    private AddressService mockAddressService;

    @MockBean
    private MeterService mockMeterService;

    @Test
    public void getClientsTest() throws Exception {
        RequestBuilder requestBuilder = (get(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON));

        Client client = new Client("name1");

        List<Client> clientList = new ArrayList<>();

        clientList.add(client);

        client = new Client("name2");

        clientList.add(client);


        given(mockClientService.getClients()).willReturn(clientList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        List<Client> responseClientList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Client>>() {
        });

        Assert.assertNotNull(responseClientList);
        Assert.assertEquals(2, responseClientList.size());

    }

    @Test
    public void addClientTest() throws Exception {

        Client mockClient = new Client("name1");

        String clientAsString = new ObjectMapper().writeValueAsString(mockClient);

        mockClient.setId(BigInteger.valueOf(1));
        when(mockClientService.addClient(any())).thenReturn(mockClient);

        RequestBuilder requestBuilder = (post(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON).content(clientAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Client responseClient = new ObjectMapper().readValue(responseBodyString, Client.class);

        ClientAssertions.execute(mockClient, responseClient);


    }

    @Test
    public void updateClientTest() throws Exception {

        String clientName = "name1";

        Client mockClient = new Client(clientName);
        mockClient.setId(BigInteger.valueOf(1));

        String clientAsString = new ObjectMapper().writeValueAsString(mockClient);

        when(mockClientService.addClient(any())).thenReturn(mockClient);

        RequestBuilder requestBuilder = (post(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON).content(clientAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        Client responseClient = new ObjectMapper().readValue(responseBodyString, Client.class);

        ClientAssertions.execute(mockClient, responseClient);

        clientName = "name1_updated";
        mockClient.setName(clientName);

        clientAsString = new ObjectMapper().writeValueAsString(mockClient);

        requestBuilder = (put(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON).content(clientAsString));

        when(mockClientService.updateClient(any())).thenReturn(mockClient);

        result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        responseBodyString = result.getResponse().getContentAsString();

        Assert.assertNotNull(responseBodyString);

        responseClient = new ObjectMapper().readValue(responseBodyString, Client.class);

        ClientAssertions.execute(mockClient, responseClient);


    }

}
