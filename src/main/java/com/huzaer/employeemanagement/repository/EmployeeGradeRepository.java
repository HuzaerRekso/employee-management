package com.huzaer.employeemanagement.repository;

import com.huzaer.employeemanagement.data.EmployeeGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Repository
public interface EmployeeGradeRepository extends JpaRepository<EmployeeGrade, Integer> {

    EmployeeGrade findByGradeName(String gradeName);

    Page<EmployeeGrade> findAllByGradeId(Integer gradeId, Pageable pageable);

    Page<EmployeeGrade> findAllByGradeName(String gradeName, Pageable pageable);

    Page<EmployeeGrade> findAllByGradeValue(Integer gradeValue, Pageable pageable);

}
