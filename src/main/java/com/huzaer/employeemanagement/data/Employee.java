package com.huzaer.employeemanagement.data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author  Mohammad Huzaer Rekso Jiwo
 * @version 0.1
 * @since   2022-04-02
 */
@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_salary")
    private BigInteger employeeSalary;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "grade_id")
    private EmployeeGrade employeeGrade;

    @Column(updatable = false, nullable = false, name = "created_time")
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(nullable = false, name = "updated_time")
    @LastModifiedDate
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

    public EmployeeGrade getEmployeeGrade() {
        return employeeGrade;
    }

    public void setEmployeeGrade(EmployeeGrade employeeGrade) {
        this.employeeGrade = employeeGrade;
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
