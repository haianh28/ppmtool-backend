package com.example.demo.payload;

public class ProjectIdExceptionRespone {
    private String projectIdentifier;

    public ProjectIdExceptionRespone(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
