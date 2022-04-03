package com.huzaer.employeemanagement.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
public class EmployeeData {

    public static final int NAME_LENGTH = 50;

    private String employeeId;

    private String employeeName;

    private BigInteger employeeSalary;

    private Integer gradeId;

    private String gradeName;

    private BigInteger totalBonus;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public BigInteger getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(BigInteger employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public BigInteger getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(BigInteger totalBonus) {
        this.totalBonus = totalBonus;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
