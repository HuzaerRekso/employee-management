package com.huzaer.employeemanagement.service;

import com.huzaer.core.payload.BaseResponse;
import com.huzaer.employeemanagement.data.EmployeeGrade;
import com.huzaer.employeemanagement.dto.EmployeeGradeData;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
public interface EmployeeGradeService {

    EmployeeGradeData saveEmployeeGrade(EmployeeGradeData employeeGradeData);

    EmployeeGradeData updateEmployeeGrade(Integer gradeId, EmployeeGradeData employeeGradeData);

    BaseResponse<List<EmployeeGradeData>> getAll(Integer gradeId, String gradeName, Integer gradeValue, Pageable paging);

    void deleteEmployeeGrade(Integer gradeId);

    EmployeeGrade findById(Integer id);
}
