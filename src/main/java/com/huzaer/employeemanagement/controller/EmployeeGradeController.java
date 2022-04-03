package com.huzaer.employeemanagement.controller;

import com.huzaer.core.exception.ApplicationException;
import com.huzaer.core.payload.BaseRequest;
import com.huzaer.core.payload.BaseResponse;
import com.huzaer.core.payload.Status;
import com.huzaer.core.validator.ValidatorType;
import com.huzaer.employeemanagement.component.Message;
import com.huzaer.employeemanagement.dto.EmployeeGradeData;
import com.huzaer.employeemanagement.service.EmployeeGradeService;
import com.huzaer.employeemanagement.validator.EmployeeGradeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/employeegrade")
public class EmployeeGradeController {

    private final EmployeeGradeService service;
    private final EmployeeGradeValidator validator;

    @Autowired
    public EmployeeGradeController(EmployeeGradeService service, EmployeeGradeValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping
    public BaseResponse<EmployeeGradeData> create(@RequestBody BaseRequest<EmployeeGradeData> request) {
        BaseResponse<EmployeeGradeData> response = new BaseResponse<>();
        try {
            validator.validate(request, null, ValidatorType.CREATE);
            EmployeeGradeData result = service.saveEmployeeGrade(request.getData());
            response.setResult(result);
            response.setStatus(Status.SUCCESS(Message.get("data.create.success", result.getGradeName())));
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }

    @PutMapping("/{gradeId}")
    public BaseResponse<EmployeeGradeData> update(@PathVariable(value = "gradeId") Integer gradeId,
                                                  @RequestBody BaseRequest<EmployeeGradeData> request) {
        BaseResponse<EmployeeGradeData> response = new BaseResponse<>();
        try {
            validator.validate(request, gradeId, ValidatorType.UPDATE);
            EmployeeGradeData result = service.updateEmployeeGrade(gradeId, request.getData());
            response.setResult(result);
            response.setStatus(Status.SUCCESS(Message.get("data.update.success", result.getGradeName())));
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }

    @GetMapping
    public BaseResponse<List<EmployeeGradeData>> getAll(@RequestParam(required = false) Integer gradeId,
                                                        @RequestParam(required = false) String gradeName,
                                                        @RequestParam(required = false) Integer gradeValue,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        BaseResponse<List<EmployeeGradeData>> response = new BaseResponse<>();
        try {
            Pageable paging = PageRequest.of(page, size);
            response = service.getAll(gradeId, gradeName, gradeValue, paging);
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }

    @DeleteMapping("/{gradeId}")
    public BaseResponse<EmployeeGradeData> delete(@PathVariable(value = "gradeId") Integer gradeId) {
        BaseResponse<EmployeeGradeData> response = new BaseResponse<>();
        try {
            validator.validate(null, gradeId, ValidatorType.DELETE);
            service.deleteEmployeeGrade(gradeId);
            response.setStatus(Status.SUCCESS(Message.get("data.delete.success", "EmployeeGrade",
                    String.valueOf(gradeId))));
        } catch (ApplicationException e) {
            response.setStatus(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(Status.ERROR());
        }
        return response;
    }
}
