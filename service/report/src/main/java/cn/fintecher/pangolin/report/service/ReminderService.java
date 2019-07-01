package cn.fintecher.pangolin.report.service;

import cn.fintecher.pangolin.entity.TaskBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("reminderService")
public class ReminderService {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 发送任务盒子消息
     *
     * @param taskBox
     */
    public void sendTaskBoxMessage(TaskBox taskBox) {
        restTemplate.postForLocation("http://reminder-service/api/reminderMessages/sendTaskBoxMessage", taskBox);
    }
}
