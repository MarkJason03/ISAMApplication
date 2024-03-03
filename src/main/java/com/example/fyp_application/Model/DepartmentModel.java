package com.example.fyp_application.Model;

public class DepartmentModel {

    private int deptID;
    private String deptName;

    public DepartmentModel(int deptID, String deptName) {
        this.deptID = deptID;
        this.deptName = deptName;
    }

    public int getDeptID() {
        return deptID;
    }

    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return deptName;
    }

}
