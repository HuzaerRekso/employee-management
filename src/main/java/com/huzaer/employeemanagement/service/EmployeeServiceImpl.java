package com.huzaer.employeemanagement.service;

import com.huzaer.core.exception.ApplicationException;
import com.huzaer.core.payload.BaseResponse;
import com.huzaer.core.payload.Paging;
import com.huzaer.core.payload.Status;
import com.huzaer.employeemanagement.component.Message;
import com.huzaer.employeemanagement.data.Employee;
import com.huzaer.employeemanagement.data.EmployeeGrade;
import com.huzaer.employeemanagement.dto.EmployeeData;
import com.huzaer.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeGradeService employeeGradeService;

    @Override
    public EmployeeData saveEmployee(EmployeeData employeeData) {
        Employee employee = populateEmployeeEntity(employeeData);

        EmployeeGrade employeeGrade = employeeGradeService.findById(employeeData.getGradeId());
        employee.setEmployeeGrade(employeeGrade);
        employee.setEmployeeId(generateId());
        return populateEmployeeData(employeeRepository.save(employee), employeeGrade);
    }

    @Override
    public EmployeeData updateEmployee(String employeeId, EmployeeData employeeData) {
        Employee employee = populateEmployeeEntity(employeeData);
        Employee currentEmployee = findById(employeeId);

        EmployeeGrade employeeGrade = employeeGradeService.findById(employeeData.getGradeId());

        currentEmployee.setEmployeeGrade(employeeGrade);
        currentEmployee.setEmployeeName(employee.getEmployeeName());
        currentEmployee.setEmployeeSalary(employee.getEmployeeSalary());
        return populateEmployeeData(employeeRepository.save(currentEmployee), employeeGrade);
    }

    @Override
    public BaseResponse<List<EmployeeData>> getAll(String employeeId, String employeeName, BigInteger employeeSalary,
                                                   Integer gradeId, String gradeName, Pageable paging) {
        BaseResponse<List<EmployeeData>> response = new BaseResponse<>();
        Page<Employee> employee;
        if (StringUtils.hasLength(employeeId)) {
            employee = employeeRepository.findAllByEmployeeId(employeeId, paging);
        } else if (StringUtils.hasLength(employeeName)) {
            employee = employeeRepository.findAllByEmployeeName(employeeName, paging);
        } else if (employeeSalary != null) {
            employee = employeeRepository.findAllByEmployeeSalary(employeeSalary, paging);
        } else if (gradeId != null) {
            employee = employeeRepository.findAllByEmployeeGradeGradeId(gradeId, paging);
        } else if (StringUtils.hasLength(gradeName)) {
            employee = employeeRepository.findAllByEmployeeGradeGradeName(gradeName, paging);
        } else {
            employee = employeeRepository.findAll(paging);
        }
        if (employee != null && employee.getTotalElements() > 0) {
            List<EmployeeData> result = new ArrayList<>();
            for (Employee loop : employee.getContent()) {
                result.add(populateEmployeeData(loop, loop.getEmployeeGrade()));
            }
            Paging pagingResult = new Paging();
            pagingResult.setPage(employee.getNumber());
            pagingResult.setTotalpage(employee.getTotalPages());
            pagingResult.setTotalrecord(employee.getTotalElements());

            response.setPaging(pagingResult);
            response.setResult(result);
            response.setStatus(Status.SUCCESS());
        } else {
            response.setStatus(Status.DATA_NOT_FOUND());
        }
        return response;
    }

    @Override
    public void deleteEmployee(String employeeId) {
        Employee currentEmployee = findById(employeeId);
        employeeRepository.delete(currentEmployee);
    }

    private Employee populateEmployeeEntity(EmployeeData employeeData) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeData.getEmployeeId());
        employee.setEmployeeName(employeeData.getEmployeeName());
        employee.setEmployeeSalary(employeeData.getEmployeeSalary());
        return employee;
    }

    private EmployeeData populateEmployeeData(Employee employee, EmployeeGrade employeeGrade) {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeId(employee.getEmployeeId());
        employeeData.setEmployeeName(employee.getEmployeeName());
        employeeData.setEmployeeSalary(employee.getEmployeeSalary());
        employeeData.setGradeId(employeeGrade.getGradeId());
        employeeData.setGradeName(employeeGrade.getGradeName());
        employeeData.setCreatedTime(employee.getCreatedTime());
        employeeData.setUpdatedTime(employee.getUpdatedTime());

        BigInteger totalBonus = countTotalBonus(employee.getEmployeeSalary(), employeeGrade.getGradeValue());
        employeeData.setTotalBonus(totalBonus);

        return employeeData;
    }

    private String generateId() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String randString = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        return formatter.format(localDate)+randString;
    }

    private BigInteger countTotalBonus(BigInteger salary, Integer gradeValue) {
        return salary.multiply(BigInteger.valueOf(gradeValue)).divide(BigInteger.valueOf(100));
    }

    private Employee findById(String employeeId) {
        Employee currentEmployee = employeeRepository.findById(employeeId).orElse(null);
        if (currentEmployee == null) {
            throw new ApplicationException(Status.DATA_NOT_FOUND(Message.get("data.not.found",
                    "Employee", employeeId)));
        }
        return currentEmployee;
    }
}
