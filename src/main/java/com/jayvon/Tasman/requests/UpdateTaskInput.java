package com.jayvon.Tasman.requests;

import com.jayvon.Tasman.entities.TaskStatusEnum;

import java.util.Date;

public record UpdateTaskInput(TaskStatusEnum status, Date dueDate) {
}
