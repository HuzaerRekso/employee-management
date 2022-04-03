package com.huzaer.employeemanagement.controller;

import com.huzaer.core.payload.BaseRequest;
import com.huzaer.core.payload.BaseResponse;
import com.huzaer.core.payload.Status;
import com.huzaer.employeemanagement.EmployeeManagementApplication;
import com.huzaer.employeemanagement.dto.EmployeeData;
import com.huzaer.employeemanagement.dto.EmployeeGradeData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeGradeControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    @Order(1)
    public void contextLoads() {
    }

    @LocalServerPort
    private int port;

    @Test
    @Order(2)
    void create() {
        EmployeeGradeData employeeGradeData = new EmployeeGradeData();
        employeeGradeData.setGradeName("Grade Name");
        employeeGradeData.setGradeValue(13);

        BaseRequest<EmployeeGradeData> request = new BaseRequest<>();
        request.setData(employeeGradeData);

        BaseResponse<EmployeeData> response = new BaseResponse<>();
        ResponseEntity<? extends BaseResponse> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/employeegrade", request, response.getClass());
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    @Order(2)
    void update() {
        BaseResponse<List<EmployeeGradeData>> response = new BaseResponse<>();
        response = restTemplate.getForObject(getRootUrl() + "/api/employeegrade?gradeName=Grade Name", response.getClass());

        Map<String, Object> result = (Map<String, Object>) response.getResult().get(0);
        EmployeeGradeData employeeGradeData = new EmployeeGradeData();
        employeeGradeData.setGradeId((Integer) result.get("gradeId"));
        employeeGradeData.setGradeName("Grade Name Updated");
        employeeGradeData.setGradeValue((Integer) result.get("gradeValue"));

        BaseRequest<EmployeeGradeData> request = new BaseRequest<>();
        request.setData(employeeGradeData);
        restTemplate.put(getRootUrl() + "/api/employeegrade/" + employeeGradeData.getGradeId(), request, response.getClass());

        BaseResponse<List<EmployeeGradeData>> updatedResponse = new BaseResponse<>();
        updatedResponse = restTemplate.getForObject(getRootUrl() + "/api/employeegrade?gradeName=Grade Name Updated", updatedResponse.getClass());
        assertNotNull(updatedResponse);
        assertNotNull(updatedResponse.getResult());
    }

    @Test
    @Order(3)
    void getAll() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/employeegrade",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        BaseResponse<List<EmployeeGradeData>> response = new BaseResponse<>();
        response = restTemplate.getForObject(getRootUrl() + "/api/employeegrade?gradeName=Grade Name Updated", response.getClass());
        assertNotNull(response);
        assertNotNull(response.getResult());

        Map<String, Object> result = (Map<String, Object>) response.getResult().get(0);

        restTemplate.delete(getRootUrl() + "/api/employeegrade/" + result.get("gradeId"));

        BaseResponse<List<EmployeeGradeData>> deletedResponse = new BaseResponse<>();
        deletedResponse = restTemplate.getForObject(getRootUrl() + "/api/employeegrade?gradeName=Grade Name Updated", deletedResponse.getClass());
        assertNotNull(deletedResponse);
        assertNotNull(deletedResponse.getStatus());
        assertEquals(Status.ERROR_NOTFOUND_CODE, deletedResponse.getStatus().getResponseCode());
    }
}