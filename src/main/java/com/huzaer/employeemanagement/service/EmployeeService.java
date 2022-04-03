package com.huzaer.employeemanagement.service;

import com.huzaer.core.payload.BaseResponse;
import com.huzaer.employeemanagement.dto.EmployeeData;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
public interface EmployeeService {

    EmployeeData saveEmployee(EmployeeData employeeData);

    EmployeeData updateEmployee(String employeeId, EmployeeData employeeData);

    BaseResponse<List<EmployeeData>> getAll(String employeeId, String employeeName, BigInteger employeeSalary,
                                            Integer gradeId, String gradeName, Pageable paging);

    void deleteEmployee(String employeeId);

}
