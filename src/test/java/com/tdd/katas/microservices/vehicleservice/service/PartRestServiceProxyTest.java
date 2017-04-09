package com.tdd.katas.microservices.vehicleservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.katas.microservices.vehicleservice.model.PartData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(PartRestServiceProxy.class)
public class PartRestServiceProxyTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private PartRestServiceProxy partRestServiceProxy;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", partRestServiceProxy);
    }

    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String vinCode = "1";

        String mockPartData =
                "[" +
                    "{" +
                        "\"partId\" : \"1\" ,"  +
                        "\"description\" : \"Wheel\"" +
                    "}," +
                    "{" +
                        "\"partId\" : \"2\" ,"  +
                        "\"description\" : \"door\"" +
                    "}" +
                "]";

        PartData[] expectedPartDataList = objectMapper.readValue(mockPartData, new TypeReference<PartData[]>(){});

        this.server.expect(requestTo("/parts/" + vinCode))
                .andRespond(withSuccess(mockPartData, MediaType.APPLICATION_JSON));
        PartData[] actualPartDataList = partRestServiceProxy.getPartData(vinCode);

        assertArrayEquals("Part data lists must match", expectedPartDataList, actualPartDataList);

    }

    @Test
    public void It_returns_null_if_the_vin_does_not_exist() throws Exception {

        final String vinCode = "NON_EXISTING_ID";

        this.server.expect(requestTo("/parts/" + vinCode))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        PartData[]  actualPartData = partRestServiceProxy.getPartData(vinCode);

        assertNull("Part must not exist", actualPartData);

    }

    @Test(expected = HttpServerErrorException.class)
    public void It_throws_an_exception_if_the_service_returns_a_service_error() throws Exception {

        this.server.expect(requestTo("/parts/ANYTHING"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        partRestServiceProxy.getPartData("ANYTHING");
    }
}
