package com.huzaer.employeemanagement.service;

import com.huzaer.core.exception.ApplicationException;
import com.huzaer.core.payload.BaseResponse;
import com.huzaer.core.payload.Paging;
import com.huzaer.core.payload.Status;
import com.huzaer.employeemanagement.component.Message;
import com.huzaer.employeemanagement.data.EmployeeGrade;
import com.huzaer.employeemanagement.dto.EmployeeGradeData;
import com.huzaer.employeemanagement.repository.EmployeeGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Service
public class EmployeeGradeServiceImpl implements EmployeeGradeService {

    @Autowired
    EmployeeGradeRepository employeeGradeRepository;

    @Override
    public EmployeeGradeData saveEmployeeGrade(EmployeeGradeData employeeGradeData) {
        EmployeeGrade employeeGrade = populateEmployeeGradeEntity(employeeGradeData);
        EmployeeGrade employeeGradeDB = employeeGradeRepository.findByGradeName(employeeGrade.getGradeName());
        if (employeeGradeDB != null) {
            throw new ApplicationException(Status.DATA_ALREADY_EXIST(Message.get("data.already.exist",
                    "EmployeeGradeData", employeeGrade.getGradeName())));
        }
        employeeGrade.setCreatedTime(LocalDateTime.now());
        return populateEmployeeGradeData(employeeGradeRepository.save(employeeGrade));
    }

    @Override
    public EmployeeGradeData updateEmployeeGrade(Integer gradeId, EmployeeGradeData employeeGradeData) {
        EmployeeGrade employeeGrade = populateEmployeeGradeEntity(employeeGradeData);

        EmployeeGrade currentEmployeeGrade = this.findById(gradeId);

        EmployeeGrade employeeGradeDB = employeeGradeRepository.findByGradeName(employeeGrade.getGradeName());
        if (employeeGradeDB != null && !employeeGradeDB.getGradeId().equals(gradeId)) {
            throw new ApplicationException(Status.DATA_ALREADY_EXIST(Message.get("data.already.exist",
                    "EmployeeGradeData", employeeGrade.getGradeName())));
        }

        currentEmployeeGrade.setGradeValue(employeeGrade.getGradeValue());
        currentEmployeeGrade.setGradeName(employeeGrade.getGradeName());
        currentEmployeeGrade.setUpdatedTime(LocalDateTime.now());
        return populateEmployeeGradeData(employeeGradeRepository.save(currentEmployeeGrade));
    }

    @Override
    public BaseResponse<List<EmployeeGradeData>> getAll(Integer gradeId, String gradeName, Integer gradeValue, Pageable paging) {
        BaseResponse<List<EmployeeGradeData>> response = new BaseResponse<>();
        Page<EmployeeGrade> employeeGrades;
        if (gradeId != null) {
            employeeGrades = employeeGradeRepository.findAllByGradeId(gradeId, paging);
        } else if (StringUtils.hasLength(gradeName)) {
            employeeGrades = employeeGradeRepository.findAllByGradeName(gradeName, paging);
        } else if (gradeValue != null) {
            employeeGrades = employeeGradeRepository.findAllByGradeValue(gradeValue, paging);
        } else {
            employeeGrades = employeeGradeRepository.findAll(paging);
        }
        if (employeeGrades != null && employeeGrades.getTotalElements() > 0) {
            List<EmployeeGradeData> result = new ArrayList<>();
            for (EmployeeGrade loop : employeeGrades.getContent()) {
                result.add(populateEmployeeGradeData(loop));
            }
            Paging pagingResult = new Paging();
            pagingResult.setPage(employeeGrades.getNumber());
            pagingResult.setTotalPage(employeeGrades.getTotalPages());
            pagingResult.setTotalRecord(employeeGrades.getTotalElements());

            response.setPaging(pagingResult);
            response.setResult(result);
            response.setStatus(Status.SUCCESS());
        } else {
            response.setStatus(Status.DATA_NOT_FOUND());
        }
        return response;
    }

    @Override
    public void deleteEmployeeGrade(Integer gradeId) {
        EmployeeGrade currentEmployeeGrade = this.findById(gradeId);
        employeeGradeRepository.delete(currentEmployeeGrade);
    }

    @Override
    public EmployeeGrade findById(Integer id) {
        EmployeeGrade employeeGrade = employeeGradeRepository.findById(id).orElse(null);
        if (employeeGrade == null) {
            throw new ApplicationException(Status.DATA_NOT_FOUND(Message.get("data.not.found", "EmployeeGrade",
                    String.valueOf(id))));
        }
        return employeeGrade;
    }

    private EmployeeGrade populateEmployeeGradeEntity(EmployeeGradeData employeeGradeData) {
        EmployeeGrade employeeGrade = new EmployeeGrade();
        employeeGrade.setGradeId(employeeGradeData.getGradeId());
        employeeGrade.setGradeName(employeeGradeData.getGradeName());
        employeeGrade.setGradeValue(employeeGradeData.getGradeValue());
        return employeeGrade;
    }

    private EmployeeGradeData populateEmployeeGradeData(EmployeeGrade employeeGrade) {
        EmployeeGradeData employeeGradeData = new EmployeeGradeData();
        employeeGradeData.setGradeId(employeeGrade.getGradeId());
        employeeGradeData.setGradeName(employeeGrade.getGradeName());
        employeeGradeData.setGradeValue(employeeGrade.getGradeValue());
        employeeGradeData.setCreatedTime(employeeGrade.getCreatedTime());
        employeeGradeData.setUpdatedTime(employeeGrade.getUpdatedTime());
        return employeeGradeData;
    }
}
