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
import typeqast.constants.RestEndpoints;
import typeqast.entities.Client;
import typeqast.entities.dto.ClientDTO;
import typeqast.service.ClientService;
import typeqast.util.assertions.ClientAssertions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService mockClientService;

    @Test
    void getClientsTest() throws Exception {
        RequestBuilder requestBuilder = (get(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON));

        ClientDTO client = new ClientDTO("name1");

        List<ClientDTO> clientList = new ArrayList<>();

        clientList.add(client);

        client = new ClientDTO("name2");

        clientList.add(client);


        given(mockClientService.getClients()).willReturn(clientList);

        MvcResult result = mvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn();

        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        List<Client> responseClientList = new ObjectMapper().readValue(responseBodyString, new TypeReference<List<Client>>() {
        });

        Assertions.assertNotNull(responseClientList);
        Assertions.assertEquals(2, responseClientList.size());

    }

    @Test
    void addClientTest() throws Exception {

        ClientDTO mockClientDTO = new ClientDTO("name1");

        String clientAsString = new ObjectMapper().writeValueAsString(mockClientDTO);

        mockClientDTO.setId(BigInteger.valueOf(1));
        when(mockClientService.addClient(any())).thenReturn(mockClientDTO);

        RequestBuilder requestBuilder = (post(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON).content(clientAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        ClientDTO responseClient = new ObjectMapper().readValue(responseBodyString, ClientDTO.class);

        ClientAssertions.execute(mockClientDTO, responseClient);


    }

    @Test
    void updateClientTest() throws Exception {

        String clientName = "name1";

        ClientDTO mockClientDTO = new ClientDTO(clientName);
        mockClientDTO.setId(BigInteger.valueOf(1));

        String clientAsString = new ObjectMapper().writeValueAsString(mockClientDTO);

        when(mockClientService.addClient(any())).thenReturn(mockClientDTO);

        RequestBuilder requestBuilder = (post(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON).content(clientAsString));

        MvcResult result = mvc.perform(requestBuilder).andExpect(status().isCreated()).andReturn();
        String responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        ClientDTO responseClient = new ObjectMapper().readValue(responseBodyString, ClientDTO.class);

        ClientAssertions.execute(mockClientDTO, responseClient);

        clientName = "name1_updated";
        mockClientDTO.setName(clientName);

        clientAsString = new ObjectMapper().writeValueAsString(mockClientDTO);

        requestBuilder = (put(RestEndpoints.CLIENTS).contentType(MediaType.APPLICATION_JSON).content(clientAsString));

        when(mockClientService.updateClient(any())).thenReturn(mockClientDTO);

        result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        responseBodyString = result.getResponse().getContentAsString();

        Assertions.assertNotNull(responseBodyString);

        responseClient = new ObjectMapper().readValue(responseBodyString, ClientDTO.class);

        ClientAssertions.execute(mockClientDTO, responseClient);


    }

}
