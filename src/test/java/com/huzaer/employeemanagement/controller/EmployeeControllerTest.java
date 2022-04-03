package com.huzaer.employeemanagement.controller;

import com.huzaer.core.payload.BaseRequest;
import com.huzaer.core.payload.BaseResponse;
import com.huzaer.core.payload.Status;
import com.huzaer.employeemanagement.EmployeeManagementApplication;
import com.huzaer.employeemanagement.dto.EmployeeData;
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

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeControllerTest {

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
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeName("Test Name");
        employeeData.setEmployeeSalary(BigInteger.valueOf(10000000));
        employeeData.setGradeId(1);

        BaseRequest<EmployeeData> request = new BaseRequest<>();
        request.setData(employeeData);

        BaseResponse<EmployeeData> response = new BaseResponse<>();
        ResponseEntity<? extends BaseResponse> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/employee", request, response.getClass());
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    @Order(2)
    void update() {
        BaseResponse<List<EmployeeData>> response = new BaseResponse<>();
        response = restTemplate.getForObject(getRootUrl() + "/api/employee?employeeName=Test Name", response.getClass());

        Map<String, Object> result = (Map<String, Object>) response.getResult().get(0);
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeId((String) result.get("employeeId"));
        employeeData.setEmployeeName("Test Name Updated");
        employeeData.setEmployeeSalary(BigInteger.valueOf((Integer) result.get("employeeSalary")));
        employeeData.setGradeId((Integer) result.get("gradeId"));

        BaseRequest<EmployeeData> request = new BaseRequest<>();
        request.setData(employeeData);
        restTemplate.put(getRootUrl() + "/api/employee/" + employeeData.getEmployeeId(), request, response.getClass());

        BaseResponse<List<EmployeeData>> updatedResponse = new BaseResponse<>();
        updatedResponse = restTemplate.getForObject(getRootUrl() + "/api/employee?employeeName=Test Name Updated", updatedResponse.getClass());
        assertNotNull(updatedResponse);
        assertNotNull(updatedResponse.getResult());
    }

    @Test
    @Order(3)
    void getAll() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/employee",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    @Order(4)
    void delete() {
        BaseResponse<List<EmployeeData>> response = new BaseResponse<>();
        response = restTemplate.getForObject(getRootUrl() + "/api/employee?employeeName=Test Name Updated", response.getClass());
        assertNotNull(response);
        assertNotNull(response.getResult());

        Map<String, Object> result = (Map<String, Object>) response.getResult().get(0);
        restTemplate.delete(getRootUrl() + "/api/employee/" + result.get("employeeId"));

        BaseResponse<List<EmployeeData>> deletedResponse = new BaseResponse<>();
        deletedResponse = restTemplate.getForObject(getRootUrl() + "/api/employee?employeeName=Test Name Updated", deletedResponse.getClass());
        assertNotNull(deletedResponse);
        assertNotNull(deletedResponse.getStatus());
        assertEquals(Status.ERROR_NOTFOUND_CODE, deletedResponse.getStatus().getResponsecode());
    }
}