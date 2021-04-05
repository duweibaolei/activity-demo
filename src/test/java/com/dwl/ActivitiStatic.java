package com.dwl;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

public class ActivitiStatic {

  public static void getDefaultProcessEngine() {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
  }
}
