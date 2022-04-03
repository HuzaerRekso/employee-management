package com.huzaer.employeemanagement.repository;

import com.huzaer.employeemanagement.data.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Page<Employee> findAllByEmployeeId(String employeeId, Pageable pageable);

    Page<Employee> findAllByEmployeeName(String employeeName, Pageable pageable);

    Page<Employee> findAllByEmployeeSalary(BigInteger employeeSalary, Pageable pageable);

    Page<Employee> findAllByEmployeeGradeGradeId(Integer gradeId, Pageable pageable);

    Page<Employee> findAllByEmployeeGradeGradeName(String gradeName, Pageable pageable);
}
