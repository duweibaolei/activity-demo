package com.dwl.activitiofservice创建方式;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程定义部署在 activiti 后就可以通过工作流管理业务流程了，也就是说上边部署的请假申请流程可以使用了。<br>
 * 针对该流程，启动一个流程表示发起一个新的请假申请单，这就相当于 java 类与 java 对象的关 系，类定义好后需要 new 创建一个对象使用，<br>
 * 当然可以 new多个对象。对于请假申请流程，张三发 起一个请假申请单需要启动一个流程实例，请假申请单发起一个请假单也需要启动一个流程实例。
 */
public class ActivitiRuntimeServiceTest {

  /**
   * 启动流程实例 <br>
   * `act_hi_actinst` 流程实例执行历史信息 <br>
   * `act_hi_identitylink` 流程参与用户的历史信息 <br>
   * `act_hi_procinst` 流程实例的历史信息 <br>
   * `act_hi_taskinst` 流程任务的历史信息 <br>
   * `act_ru_execution` 流程执行信息 <br>
   * `act_ru_identitylink` 流程的正在参与用户信息 <br>
   * `act_ru_task` 流程当前任务信息
   */
  @Test
  public void testStartProcessInstance() {
    // 创建 ProcessEngine
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    // 获取 RunTimeService
    RuntimeService runtimeService = processEngine.getRuntimeService();
    // 根据流程定义 key 启动流程
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_1");
    // 输出内容
    System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
    System.out.println("流程实例id：" + processInstance.getId());
    System.out.println("当前活动id：" + processInstance.getActivityId());
  }
}
