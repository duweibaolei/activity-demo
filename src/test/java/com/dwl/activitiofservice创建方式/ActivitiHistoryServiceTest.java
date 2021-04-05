package com.dwl.activitiofservice创建方式;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.junit.Test;

import java.util.List;

/**
 * 流程历史信息的查看 <br>
 * 即使流程定义已经删除了，流程执行的历史信息通过前面的分析，依然保存在 activiti 的 act_hi_*相 关的表中。 <br>
 * 所以我们还是可以查询流程执行的历史信息，可以通过HistoryService 来查看相关的历史记录。
 */
public class ActivitiHistoryServiceTest {

  /** 获取流程引擎 */
  private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

  @Test
  public void findHistoryInfo() {
    // 获取HistoryService
    HistoryService historyService = processEngine.getHistoryService();
    // 获取 actinst表的查询对象
    HistoricActivityInstanceQuery instanceQuery =
        historyService.createHistoricActivityInstanceQuery();
    // 查询 actinst 表，条件：根据 InstanceId 查询
    // instanceQuery.processInstanceId("2501");
    // 查询 actinst 表，条件：根据 DefinitionId 查询
    instanceQuery.processDefinitionId("myProcess_1:1:4");
    // 增加排序操作,orderByHistoricActivityInstanceStartTime 根据开始时间排序 asc 升序
    instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
    // 查询所有内容
    List<HistoricActivityInstance> activityInstanceList = instanceQuery.list();
    // 输出
    for (HistoricActivityInstance hi : activityInstanceList) {
      System.out.println(hi.getActivityId());
      System.out.println(hi.getActivityName());
      System.out.println(hi.getProcessDefinitionId());
      System.out.println(hi.getProcessInstanceId());
      System.out.println("<==========================>");
    }
  }
}
