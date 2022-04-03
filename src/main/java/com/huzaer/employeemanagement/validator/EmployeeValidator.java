package com.huzaer.employeemanagement.validator;

import com.huzaer.core.payload.BaseRequest;
import com.huzaer.core.validator.BaseValidator;
import com.huzaer.core.validator.ValidatorType;
import com.huzaer.employeemanagement.component.Message;
import com.huzaer.employeemanagement.dto.EmployeeData;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Component
public class EmployeeValidator extends BaseValidator {

    public void validate(BaseRequest<EmployeeData> request, String employeeId, ValidatorType type) {
        if (type.equals(ValidatorType.UPDATE) || type.equals(ValidatorType.DELETE)) {
            checkEmployeeId(employeeId);
            if (type.equals(ValidatorType.DELETE)) {
                return;
            }
        }
        notNull(request.getData(), getMandatoryMsg("data"));
        EmployeeData data = request.getData();
        checkName(data.getEmployeeName());
        checkSalary(data.getEmployeeSalary());
        checkGradeId(data.getGradeId());
    }

    private void checkEmployeeId(String employeeId) {
        notBlank(employeeId, getMandatoryMsg("employeeId"));
    }

    private void checkName(String employeeName) {
        notBlank(employeeName, getMandatoryMsg("employeeName"));
        isAlphabetSpace(employeeName, Message.get("field.format.alphabet", "employeeName"));
        isMax(employeeName, EmployeeData.NAME_LENGTH, Message.get("field.format.max", "employeeName",
                String.valueOf(EmployeeData.NAME_LENGTH)));
    }

    private void checkSalary(BigInteger employeeSalary) {
        notNull(employeeSalary, getMandatoryMsg("employeeSalary"));
    }

    private void checkGradeId(Integer gradeId) {
        notNull(gradeId, getMandatoryMsg("gradeId"));
    }

    private String getMandatoryMsg(String field) {
        return Message.get("field.required", field);
    }
}
