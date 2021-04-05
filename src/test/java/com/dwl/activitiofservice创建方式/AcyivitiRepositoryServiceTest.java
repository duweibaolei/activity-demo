package com.dwl.activitiofservice创建方式;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Service 创建方式 <br>
 * 通过 ProcessEngine 创建 Service，Service 是工作流引擎提供用于进行工作流部署、执行、管理的服务接口。 方式如下：<br>
 *      <pre>RuntimeService runtimeService = processEngine.getRuntimeService();
 *      <pre>RepositoryService repositoryService = processEngine.getRepositoryService();
 *      <pre>TaskService taskService = processEngine.getTaskService();
 */
public class AcyivitiRepositoryServiceTest {

  /** 获取流程引擎 */
  private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

  /** 部署流程定义就是要将上边绘制的图形即流程定义（.bpmn）部署在工作流程引擎 activiti 中 */
  @Test
  public void testCrateRepositoryService() {
    // 使用 ProcessEngine 创建 RepositoryService
    RepositoryService repositoryService = processEngine.getRepositoryService();
    Deployment deploy =
        repositoryService
            .createDeployment()
            .name("出差申请流程")
            .addClasspathResource("bpmn/evection.bpmn")
            .addClasspathResource("bpmn/evection.png")
            .deploy();
    System.out.println("流程部署id:" + deploy.getId());
    System.out.println("流程部署name:" + deploy.getName());
    System.out.println("流程部署key" + deploy.getKey());
  }

  /** 使用zip包进行批量的部署 */
  @Test
  public void deployProcessByZip() {
    // 获取 RepositoryService
    RepositoryService repositoryService = processEngine.getRepositoryService();
    // 流程部署 读取资源包文件，构造成 inputStream
    InputStream inputStream =
        this.getClass().getClassLoader().getResourceAsStream("bpmn/evection.zip");
    // 用inputStream 构造 ZipInputStream
    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
    // 使用压缩包的流进行流程的部署
    Deployment deploy =
        repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy();
    System.out.println("流程部署id=" + deploy.getId());
    System.out.println("流程部署的名称=" + deploy.getName());
  }

  /** 查询部署的流程定义 */
  @Test
  public void queryProceccDefinition() {
    // 流程定义key
    String processDefinitionKey = "myProcess_2";
    // 获取 repositoryService
    RepositoryService repositoryService = processEngine.getRepositoryService();
    // 查询流程定义
    ProcessDefinitionQuery processDefinitionQuery =
        repositoryService.createProcessDefinitionQuery();
    // 遍历查询结果
    List<ProcessDefinition> list =
        processDefinitionQuery
            .processDefinitionKey(processDefinitionKey)
            .orderByProcessDefinitionVersion()
            .desc()
            .list();
    for (ProcessDefinition definition : list) {
      System.out.println("------------------------");
      System.out.println("流程部署id：" + definition.getDeploymentId());
      System.out.println("流程定义id：" + definition.getId());
      System.out.println("流程定义名称：" + definition.getName());
      System.out.println("流程定义key：" + definition.getKey());
      System.out.println("流程定义版本：" + definition.getVersion());
    }
  }

  /**
   * 流程定义刪除：删除已经部署成功的流程定义 <br>
   * 使用 repositoryService 删除流程定义 <br>
   * 如果该流程定义下没有正在运行的流程，则可以用普通删除。<br>
   * 如果该流程定义下存在已经运行的流程，使用普通删除报错，可用级联删除方法将流程及相关 记录全部删除。<br>
   * 项目开发中使用级联删除的情况比较多，删除操作一般只开放给超级管理员使用。<br>
   * `act_ge_bytearray` <br>
   * `act_re_deployment` <br>
   * `act_re_procdef`
   */
  @Test
  public void deleteDeployment() {
    // 流程部署id
    String deploymentId = "7501";
    // 通过流程引擎获取repositoryService
    RepositoryService repositoryService = processEngine.getRepositoryService();
    // 删除流程定义，如果该流程定义已有流程实例启动则删除时出错。
    // repositoryService.deleteDeployment(deploymentId);
    // 设置 true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为 false 非级别删除方式，如果流程在启动中则报错。
    repositoryService.deleteDeployment(deploymentId, true);
  }

  /**
   * 通过流程定义对象获取流程定义资源，获取 bpmn 和 png。
   *
   * @throws IOException
   */
  @Test
  public void getProcessResources1() throws IOException {
    // 获取api，RepositoryService
    RepositoryService repositoryService = processEngine.getRepositoryService();
    // 获取查询对象 ProcessDefinitionQuery查询流程定义信息
    ProcessDefinition processDefinition =
        repositoryService
            .createProcessDefinitionQuery()
            .processDefinitionKey("myProcess_1")
            .singleResult();
    // 通过流程定义信息，获取部署ID
    String deploymentId = processDefinition.getDeploymentId();
    /* 通过 RepositoryService ，传递部署id参数，读取资源信息（png 和 bpmn）
     *   1.获取png图片的流，从流程定义表中，获取png图片的目录和名字 */
    String pngName = processDefinition.getDiagramResourceName();
    // 通过 部署id和 文件名字来获取图片的资源
    InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, pngName);
    //   2.获取bpmn的流
    String bpmnName = processDefinition.getResourceName();
    InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, bpmnName);
    // 构造OutputStream流
    File pngFile = new File("d:/evectionflow01.png");
    File bpmnFile = new File("d:/evectionflow01.bpmn");
    FileOutputStream pngOutStream = new FileOutputStream(pngFile);
    FileOutputStream bpmnOutStream = new FileOutputStream(bpmnFile);
    // 输入流，输出流的转换
    IOUtils.copy(pngInput, pngOutStream);
    IOUtils.copy(bpmnInput, bpmnOutStream);
    // 关闭流
    pngOutStream.close();
    bpmnOutStream.close();
    pngInput.close();
    bpmnInput.close();
  }

  /**
   * 获取流程定义图片资源 <br>
   * deploymentId 为流程部署 ID <br>
   * resource_name 为 act_ge_bytearray 表中 NAME_列的值 <br>
   * 使用 repositoryService 的 getDeploymentResourceNames方法可以获取指定部署下得所有文件的名称 <br>
   * 使用 repositoryService 的 getResourceAsStream 方法传入部署 ID 和资源图片名称可以获取部署下指定名称文件的输入流。 <br>
   * 最后的将输入流中的图片资源进行输出。
   *
   * @throws IOException
   */
  @Test
  public void getProcessResources2() throws IOException {
    // 流程部署id
    String deploymentId = "15001";
    // 通过流程引擎获取repositoryService
    RepositoryService repositoryService = processEngine.getRepositoryService();
    // 读取资源名称
    List<String> resources = repositoryService.getDeploymentResourceNames(deploymentId);
    String resource_image = null;
    // 获取图片
    for (String resource_name : resources) {
      if (resource_name.indexOf(".png") >= 0) {
        resource_image = resource_name;
      }
    }
    // 图片输入流
    InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resource_image);
    File exportFile = new File("E:/holiday.png");
    FileOutputStream fileOutputStream = new FileOutputStream(exportFile);
    byte[] buffer = new byte[1024];
    int len = -1;
    // 输出图片
    while ((len = inputStream.read(buffer)) != -1) {
      fileOutputStream.write(buffer, 0, len);
    }
    inputStream.close();
    fileOutputStream.close();
  }
}
