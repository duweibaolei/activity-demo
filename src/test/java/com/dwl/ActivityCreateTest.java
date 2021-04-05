package com.dwl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.junit.Test;

public class ActivityCreateTest {
  /** 使用activiti提供的默认方式来创建mysql的表 */
  @Test
  public void testCreateDbTable() {
    /*
      需要使用 avtiviti 提供的工具类 ProcessEngines ,使用方法 getDefaultProcessEngine.
      getDefaultProcessEngine会默认从 resources 下读取名字为actviti.cfg.xml的文件
      创建 processEngine 时，就会创建 mysql 的表。

     默认方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment();
    */

    // 使用自定义方式：配置文件的名字可以自定义，bean的名字也可以自定义。
    ProcessEngineConfiguration processEngineConfiguration =
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(
            "activiti.cfg.xml", "processEngineConfiguration");
    // 获取流程引擎对象
    ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
    RuntimeService runtimeService = processEngine.getRuntimeService();
    System.out.println("processEngine:" + processEngine);
    System.out.println("runtimeService:" + runtimeService);
  }
}
