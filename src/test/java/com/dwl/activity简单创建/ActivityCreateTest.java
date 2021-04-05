package com.dwl.activity简单创建;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/** 使用 activiti 提供的方式创建 mysql 的表： <br>
 *  Service 创建方式 <br>
 *  通过 ProcessEngine 创建 Service，Service 是工作流引擎提供用于进行工作流部署、执行、管理的服务接口。 方式如下：<br>
 *       <pre>RuntimeService runtimeService = processEngine.getRuntimeService(); <br>
 *       <pre>RepositoryService repositoryService = processEngine.getRepositoryService(); <br>
 *       <pre>TaskService taskService = processEngine.getTaskService(); <br>
 * */
public class ActivityCreateTest {

  /** 使用activiti提供的默认方式来创建mysql的表 */
  @Test
  public void testCreateDbTable() {
    /*
     * 默认方式：
     *   需要使用 avtiviti 提供的工具类 ProcessEngines ,使用方法 getDefaultProcessEngine。
     *   getDefaultProcessEngine 会默认从 resources 下读取名字为 actviti.cfg.xml 的文件
     *   创建 processEngine 时，就会创建 mysql 的表。
     */
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    System.out.println("processEngine:" + processEngine);
  }

  /** 使用自定义方式：配置文件的名字可以自定义，bean 的名字也可以自定义。 */
  @Test
  public void testCreateDbTable2() {
    // 获取配置文件以及指定 bean 名称
    ProcessEngineConfiguration processEngineConfiguration =
        ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(
            "activiti.cfg.xml", "processEngineConfiguration");
    // 获取流程引擎对象
    ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
    System.out.println("processEngine:" + processEngine);
  }
}
