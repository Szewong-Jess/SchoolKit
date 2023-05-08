package com.example.group6_schoolkit.taskCrud;

public class TaskModel {
    private String title;
    private String description;
    private String dueDate;
    private String importance;
    private String category;
    private String course;
    private String owner;
    private String commentBox;
    private static int lastId = 0;
    private int id;

    private int status;

    private Boolean isExpanded;
    private String email;

    public TaskModel(String title, String description, String dueDate, String importance, String category, String course, String owner, String commentBox, int status, String email) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.importance = importance;
        this.category = category;
        this.course = course;
        this.owner = owner;
        this.commentBox = commentBox;
        this.status=status;
        this.id=++lastId;
        isExpanded=false;
        this.email=email;
    }

    public TaskModel(){

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getImportance() {
        return importance;
    }

    public String getCategory() {
        return category;
    }

    public String getCourse() {
        return course;
    }

    public String getOwner() {
        return owner;
    }

    public String getCommentBox() {
        return commentBox;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setImportance(String importance) {

            this.importance = importance;

    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setCommentBox(String commentBox) {
        this.commentBox = commentBox;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
