package com.xcmis.framework.modules.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xcmis.framework.common.global.Globals;
import com.xcmis.framework.common.vo.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.xcmis.framework.modules.entity.ActReModel;
import com.xcmis.framework.modules.service.ActReModelService;


@Controller
@RequestMapping(value = "/sys")
public class ActReModelController {
	@Autowired
    private RepositoryService repositoryService;
	
	@Autowired
	private ActReModelService actReModelService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;
	
	
	
	@RequestMapping(value = "/getActReModel", method = RequestMethod.GET)
	public String getActReModel() {
		return "activiti/actremodel";
	}
	
	@RequestMapping(value = "/addActReModel", method = RequestMethod.GET)
	public String addActReModel() {
		return "activiti/actremodel_op";
	}
	
	
	@RequestMapping("graphHistoryProcessInstance")
    public void processTracking(String processInstanceId1, HttpServletResponse response, String key) throws Exception {
		ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(key + "." + processInstanceId1).singleResult();
		
		String processInstanceId = processInstance1.getId();
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String procDefId = processInstance.getProcessDefinitionId();

        // 当前活动节点、活动线
        List<String> activeActivityIds = new ArrayList<String>(), highLightedFlows;
        //所有的历史活动节点
        List<String> highLightedFinishes = new ArrayList<String>();

        // 如果流程已经结束，则得到结束节点
        if (!isFinished(processInstanceId)) {
            // 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        }

        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().asc().list();

        for(HistoricActivityInstance historicActivityInstance : historicActivityInstances){
            highLightedFinishes.add(historicActivityInstance.getActivityId());
        }
        // 计算活动线
        highLightedFlows = getHighLightedFlows(
                (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(procDefId),
                historicActivityInstances);

        if (null != activeActivityIds) {
            InputStream imageStream = null;
            try {
                response.setContentType("image/png");
                // 获得流程引擎配置
                //ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
                // 根据流程定义ID获得BpmnModel
                BpmnModel bpmnModel = repositoryService
                        .getBpmnModel(procDefId);
                // 输出资源内容到相应对象
                imageStream = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, "png", activeActivityIds, highLightedFlows, "宋体", "宋体", "宋体",processEngineConfiguration.getClassLoader(), 1.0);
                int len;
                byte[] b = new byte[1024];

                while ((len = imageStream.read(b, 0, 1024)) != -1) {
                    response.getOutputStream().write(b, 0, len);
                }
            } finally {
                if(imageStream != null){
                    imageStream.close();
                }
            }
        }
    }

    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count() > 0;
    }


    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        List<String> highActivitiImpl = new ArrayList<String>();

        for(HistoricActivityInstance historicActivityInstance : historicActivityInstances){
            highActivitiImpl.add(historicActivityInstance.getActivityId());
        }

        for(HistoricActivityInstance historicActivityInstance : historicActivityInstances){
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstance.getActivityId());
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            // 对所有的线进行遍历
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                if (highActivitiImpl.contains(pvmActivityImpl.getId())) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }

        return highFlows;
    }

	
	
	@RequestMapping("actReModelDeploy")
	@ResponseBody
	public String actReModelDeploy(String modelId) {   
		try {  
			Model modelData = repositoryService.getModel(modelId);  
	        ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
	        byte[] bpmnBytes = null;  
	        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);  
	        bpmnBytes = new BpmnXMLConverter().convertToXML(model);  
	  
	        String processName = modelData.getName() + ".bpmn20.xml";  
	        repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "utf-8")).deploy();  
	        return "success";
	    } catch (Exception e) {  
	        e.printStackTrace();
	        return "error";
	    }  
  
	}  

	
	@RequestMapping("addActReModelDo")
	@ResponseBody
    public Result<String> addActReModelDo(String name, String key, String description, HttpServletRequest request, HttpServletResponse response) {
        try {       
        	
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();
 
            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(key);
 
            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            return new Result<>(modelData.getId(), Globals.OP_SUCCESS, Globals.SUCCESS_CODE);


        } catch (Exception e) {
        	e.printStackTrace();
            return new Result<>("", Globals.OP_FAILURE, Globals.FAILED_CODE);
        }
    }
    
	
	@RequestMapping(value = "/getActReModelListByCondition", method = RequestMethod.POST)
	@ResponseBody
	public Result<Map<String,Object>> getActReModelListByCondition(String page, String rows, String sort, String order) {
		ActReModel actReModel = new ActReModel();
		Map<String, Object> mapIn = new HashMap<String, Object>();

		long total = actReModelService.getActReModelListTotal(actReModel);
		mapIn.put("startrow", (Integer.parseInt(page) - 1) * Integer.parseInt(rows));
		mapIn.put("endrow", (Integer.parseInt(page) - 1) * Integer.parseInt(rows) + Integer.parseInt(rows));
		mapIn.put("sort", sort);
		mapIn.put("order", order);

		List<ActReModel> list = actReModelService.getActReModelListByCondition(mapIn);

		Map<String,Object> mapOut = new HashMap<String,Object>();
		mapOut.put("total", total);//实际的行数
		mapOut.put("rows", list);//要以JSON格式返回

		return new Result<Map<String,Object>>(mapOut, Globals.OP_SUCCESS, Globals.SUCCESS_CODE);
	}
	
	
	
	
}
