package com.huzaer.employeemanagement.validator;

import com.huzaer.core.payload.BaseRequest;
import com.huzaer.core.validator.BaseValidator;
import com.huzaer.core.validator.ValidatorType;
import com.huzaer.employeemanagement.component.Message;
import com.huzaer.employeemanagement.dto.EmployeeGradeData;
import org.springframework.stereotype.Component;

/**
 * This class function is to validate request
 *
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Component
public class EmployeeGradeValidator extends BaseValidator {

    public void validate(BaseRequest<EmployeeGradeData> request, Integer gradeId, ValidatorType type) {
        if (type.equals(ValidatorType.UPDATE) || type.equals(ValidatorType.DELETE)) {
            checkGradeId(gradeId);
            if (type.equals(ValidatorType.DELETE)) {
                return;
            }
        }
        notNull(request.getData(), getMandatoryMsg("data"));
        EmployeeGradeData data = request.getData();

        checkName(data.getGradeName());
        checkValue(data.getGradeValue());
    }

    private void checkGradeId(Integer gradeId) {
        notNull(gradeId, getMandatoryMsg("gradeId"));
    }

    private void checkName(String gradeName) {
        notBlank(gradeName, getMandatoryMsg("gradeName"));
        isAlphabetSpace(gradeName, Message.get("field.format.alphabet", "gradeName"));
        isMax(gradeName, EmployeeGradeData.NAME_LENGTH, Message.get("field.format.max", "gradeName",
                String.valueOf(EmployeeGradeData.NAME_LENGTH)));
    }

    private void checkValue(Integer gradeValue) {
        notNull(gradeValue, getMandatoryMsg("gradeValue"));
    }

    private String getMandatoryMsg(String field) {
        return Message.get("field.required", field);
    }
}
