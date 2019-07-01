package cn.fintecher.pangolin.business.web.rest;

import cn.fintecher.pangolin.business.repository.TaskBoxRepository;
import cn.fintecher.pangolin.entity.TaskBox;
import cn.fintecher.pangolin.web.HeaderUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/taskBoxResource")
public class TaskBoxResource {
    private final Logger log = LoggerFactory.getLogger(TaskBoxResource.class);

    @Autowired
    private TaskBoxRepository taskBoxRepository;


    @PostMapping
    @ApiOperation(value = "保存任务盒子", notes = "保存任务盒子")
    public ResponseEntity<TaskBox> save(@RequestBody TaskBox taskBox) throws URISyntaxException {
        log.debug("REST request to save TaskBox : {}", taskBox);
        taskBox = taskBoxRepository.save(taskBox);
        return ResponseEntity.created(new URI("/api/taskBoxResource/" + taskBox.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(TaskBox.class.getSimpleName(), taskBox.getId()))
                .body(taskBox);
    }
}
