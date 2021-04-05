package com.dwl.activitiofservice创建方式;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/** 流程启动后，各各任务的负责人就可以查询自己当前需要处理的任务，查询出来的任务都是该用户的待办任务。 */
public class ActivitiTaskServiceTest {

  /** 查询个人待执行的任务 */
  @Test
  public void findPersonalTaskList() {
    // 获取流程引擎
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    // 获取taskService
    TaskService taskService = processEngine.getTaskService();
    // 根据流程key 和 任务的负责人 查询任务
    List<Task> taskList =
        taskService
            .createTaskQuery()
            .processDefinitionKey("myProcess_2") // 流程Key
            .taskAssignee("zhangsan") // 要查询的负责人
            .list();
    // 输出
    for (Task task : taskList) {
      System.out.println("流程实例id=" + task.getProcessInstanceId());
      System.out.println("任务Id=" + task.getId());
      System.out.println("任务负责人=" + task.getAssignee());
      System.out.println("任务名称=" + task.getName());
    }
  }

  /**
   * 任务处理：<br>
   * 任务负责人查询待办任务，选择任务进行处理，完成任务。
   */
  @Test
  public void completTask() {
    // 获取引擎
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    // 获取操作任务的服务 TaskService
    TaskService taskService = processEngine.getTaskService();
    // 完成任务,参数：任务id,完成zhangsan的任务
    taskService.complete("2505");
    // 获取 zhangsan - myProcess_1 对应的任务
    Task task =
        taskService
            .createTaskQuery()
            .processDefinitionKey("myProcess_1")
            .taskAssignee("zhangsan")
            .singleResult();
    System.out.println("流程实例id=" + task.getProcessInstanceId());
    System.out.println("任务Id=" + task.getId());
    System.out.println("任务负责人=" + task.getAssignee());
    System.out.println("任务名称=" + task.getName());
    //        完成jerry的任务 、完成jack的任务、完成rose的任务
    taskService.complete(task.getId());
  }
}
