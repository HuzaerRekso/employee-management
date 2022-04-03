package com.huzaer.employeemanagement.controller;

import com.huzaer.core.exception.ApplicationException;
import com.huzaer.core.payload.BaseRequest;
import com.huzaer.core.payload.BaseResponse;
import com.huzaer.core.payload.Status;
import com.huzaer.core.validator.ValidatorType;
import com.huzaer.employeemanagement.component.Message;
import com.huzaer.employeemanagement.dto.EmployeeData;
import com.huzaer.employeemanagement.service.EmployeeService;
import com.huzaer.employeemanagement.validator.EmployeeValidator;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeValidator validator;

    @Autowired
    public EmployeeController(EmployeeService service, EmployeeValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping
    public BaseResponse<EmployeeData> create(@RequestBody BaseRequest<EmployeeData> request) {
        BaseResponse<EmployeeData> response = new BaseResponse<>();
        try {
            validator.validate(request, null, ValidatorType.CREATE);
            EmployeeData result = service.saveEmployee(request.getData());
            response.setResult(result);
            response.setStatus(Status.SUCCESS(Message.get("data.create.success", result.getEmployeeName())));
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR(MDC.get("X-B3-TraceId") + " - " + MDC.get("X-B3-SpanId")));
        }
        return response;
    }

    @PutMapping("/{employeeId}")
    public BaseResponse<EmployeeData> update(@PathVariable(value = "employeeId") String employeeId,
                                                  @RequestBody BaseRequest<EmployeeData> request) {
        BaseResponse<EmployeeData> response = new BaseResponse<>();
        try {
            validator.validate(request, employeeId, ValidatorType.UPDATE);
            EmployeeData result = service.updateEmployee(employeeId, request.getData());
            response.setResult(result);
            response.setStatus(Status.SUCCESS(Message.get("data.update.success", result.getEmployeeName())));
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }

    @GetMapping
    public BaseResponse<List<EmployeeData>> getAll(@RequestParam(required = false) String employeeId,
                                                   @RequestParam(required = false) String employeeName,
                                                   @RequestParam(required = false) BigInteger employeeSalary,
                                                   @RequestParam(required = false) Integer gradeId,
                                                   @RequestParam(required = false) String gradeName,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "3") int size) {
        BaseResponse<List<EmployeeData>> response = new BaseResponse<>();
        try {
            Pageable paging = PageRequest.of(page, size);
            response = service.getAll(employeeId, employeeName, employeeSalary, gradeId, gradeName, paging);
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }

    @DeleteMapping("/{employeeId}")
    public BaseResponse<EmployeeData> delete(@PathVariable(value = "employeeId") String employeeId) {
        BaseResponse<EmployeeData> response = new BaseResponse<>();
        try {
            validator.validate(null, employeeId, ValidatorType.DELETE);
            service.deleteEmployee(employeeId);
            response.setStatus(Status.SUCCESS(Message.get("data.delete.success", "Employee",
                    String.valueOf(employeeId))));
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }
}
