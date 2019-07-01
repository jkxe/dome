package cn.fintecher.pangolin.service.reminder.model;

import cn.fintecher.pangolin.entity.TaskBox;
import cn.fintecher.pangolin.web.WebSocketMessage;

public class TaskBoxMessage extends WebSocketMessage<TaskBox> {

    public TaskBoxMessage() {
        super("Task");
    }
}
