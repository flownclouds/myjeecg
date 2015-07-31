package com.buss.template.controller;

import com.buss.template.entity.GenTemplateEntity;
import com.buss.template.service.GenTemplateServiceI;
import com.buss.template.page.GenTemplatePage;
import com.buss.files.entity.GenTemplateFilesEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.context.annotation.Scope;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 模板组
 * @date 2015-07-10 16:31:09
 */
@Scope("prototype")
@Controller
@RequestMapping("/genTemplateController")
public class GenTemplateController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(GenTemplateController.class);

    @Autowired
    private GenTemplateServiceI genTemplateService;
    @Autowired
    private SystemService systemService;


    /**
     * 模板组列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "genTemplate")
    public ModelAndView genTemplate(HttpServletRequest request) {
        return new ModelAndView("com/buss/template/genTemplateList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(GenTemplateEntity genTemplate, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(GenTemplateEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, genTemplate);
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.genTemplateService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除模板组
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(GenTemplateEntity genTemplate, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        genTemplate = systemService.getEntity(GenTemplateEntity.class, genTemplate.getId());


        String message = "模板组删除成功";
        try {
            if(!isOperator(genTemplate.getCreateBy())){
                throw new BusinessException("失败，你没有该条记录的操作权限！");
            }

            genTemplateService.delMain(genTemplate);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "模板组删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    private boolean isOperator(String curUser){
        TSUser currLoginUser = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId()).getUser();
        String user = currLoginUser.getUserName();
        if(curUser.equals(user) || user.equals("admin")){
            return true;
        }
return false;
    }

    /**
     * 批量删除模板组
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "模板组删除成功";
        try {
            for (String id : ids.split(",")) {
                GenTemplateEntity genTemplate = systemService.getEntity(GenTemplateEntity.class,
                        id
                );
                if(!isOperator(genTemplate.getCreateBy())){
                    throw new BusinessException("失败，你没有该条记录的操作权限！");
                }

                genTemplateService.delMain(genTemplate);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "模板组删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加模板组
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(GenTemplateEntity genTemplate, GenTemplatePage genTemplatePage, HttpServletRequest request) {
        List<GenTemplateFilesEntity> genTemplateFilesList = genTemplatePage.getGenTemplateFilesList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try {
            long count = genTemplateService.getCountForJdbc("SELECT count(0) FROM gen_template where TEMPLATE_NAME ='" + genTemplate.getTemplateName() + "'");
            if (count > 0) {
                throw new BusinessException("模板名重复:" + genTemplate.getTemplateName());
            }

            CheckRepeatFileName(genTemplateFilesList);

            genTemplateService.addMain(genTemplate, genTemplateFilesList);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "模板组添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    private void CheckRepeatFileName(List<GenTemplateFilesEntity> genTemplateFilesList) {
        List<String> fArr = new ArrayList<String>();
        for (GenTemplateFilesEntity item : genTemplateFilesList) {
            String fName = item.getFileName().toUpperCase();
            if (fArr.contains(fName)) {
                throw new BusinessException("文件名重复:" + fName);
            } else {
                fArr.add(fName);
            }
        }
    }

    /**
     * 更新模板组
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(GenTemplateEntity genTemplate, GenTemplatePage genTemplatePage, HttpServletRequest request) {
        List<GenTemplateFilesEntity> genTemplateFilesList = genTemplatePage.getGenTemplateFilesList();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
            CheckRepeatFileName(genTemplateFilesList);

            if(!isOperator(genTemplate.getCreateBy())){
                throw new BusinessException("失败，你没有该条记录的操作权限！");
            }

            genTemplateService.updateMain(genTemplate, genTemplateFilesList);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新模板组失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doFileUpdate")
    @ResponseBody
    public AjaxJson doFileUpdate(GenTemplateFilesEntity genTemplateFilePage, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "更新模板文件成功";
        try {


            GenTemplateFilesEntity fileEntity = genTemplateService.get(GenTemplateFilesEntity.class, genTemplateFilePage.getId());
            fileEntity.setFileContent(genTemplateFilePage.getFileContent());
            genTemplateService.saveOrUpdate(fileEntity);

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新模板文件失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 模板组新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(GenTemplateEntity genTemplate, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(genTemplate.getId())) {
            genTemplate = genTemplateService.getEntity(GenTemplateEntity.class, genTemplate.getId());
            req.setAttribute("genTemplatePage", genTemplate);
        }
        return new ModelAndView("com/buss/template/genTemplate-add");
    }

    /**
     * 模板组编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(GenTemplateEntity genTemplate, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(genTemplate.getId())) {
            genTemplate = genTemplateService.getEntity(GenTemplateEntity.class, genTemplate.getId());
            req.setAttribute("genTemplatePage", genTemplate);

            Boolean isPermit=true;
            if(!isOperator(genTemplate.getCreateBy())){
                isPermit=false;
            }
            req.setAttribute("isPermit",isPermit);


        }
        return new ModelAndView("com/buss/template/genTemplate-update");
    }

    @RequestMapping(params = "goFileUpdate")
    public ModelAndView goFileUpdate(GenTemplateFilesEntity genFileTemplate, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(genFileTemplate.getId())) {
            genFileTemplate = genTemplateService.getEntity(GenTemplateFilesEntity.class, genFileTemplate.getId());
            req.setAttribute("genTemplateFilePage", genFileTemplate);
        }
        return new ModelAndView("com/buss/files/genTemplate-file-update");
    }


    /**
     * 加载明细列表[ss]
     *
     * @return
     */
    @RequestMapping(params = "genTemplateFilesList")
    public ModelAndView genTemplateFilesList(GenTemplateEntity genTemplate, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = genTemplate.getId();
        //===================================================================================
        //查询-ss
        String hql0 = "select new com.buss.files.entity.GenTemplateFilesEntity(id,createName,createBy,createDate,updateName,updateBy,updateDate,fileName,groupId) " +
                "from GenTemplateFilesEntity where 1 = 1 AND groupId = ? ";
        try {
//            List<GenTemplateFilesEntity> genTemplateFilesEntityList=systemService.findObjForJdbc
//                    ("SELECT id,create_name,create_by,create_date,update_name,update_by,update_date,file_name,group_id FROM gen_template_files where group_id='" + id0 + "'", 1, 1000, GenTemplateFilesEntity.class);
            List<GenTemplateFilesEntity> genTemplateFilesEntityList = systemService.findHql(hql0, id0);
            req.setAttribute("genTemplateFilesList", genTemplateFilesEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/buss/files/genTemplateFilesList");
    }

    @RequestMapping(params = "goGenCode")
    public ModelAndView goGenCode(HttpServletRequest req) {

        return new ModelAndView("com/buss/template/genCode");
    }
    @RequestMapping(params = "goGenCodeByModel")
    public ModelAndView goGenCodeByModel(HttpServletRequest req) {

        return new ModelAndView("com/buss/template/genCodeByModel");
    }

    @RequestMapping(params = "goGenConfig")
    public ModelAndView goGenConfig(HttpServletRequest req) {
        List<Map<String, Object>> infos = getTemplateFiles();

        req.setAttribute("infos", infos);

        return new ModelAndView("com/buss/template/genConfig");
    }
    @RequestMapping(params = "goGenConfigByModel")
    public ModelAndView goGenConfigByModel(HttpServletRequest req) {
        List<Map<String, Object>> infos = getTemplateFiles();


        req.setAttribute("infos", infos);

        return new ModelAndView("com/buss/template/genConfigByModel");
    }

    private List<Map<String, Object>> getTemplateFiles() {
        List<Map<String, Object>> list = genTemplateService.findForJdbc("SELECT f.file_name,t.template_name,t.id tid,f.id fid FROM gen_template_files f,gen_template t \n" +
                "where f.group_id=t.id");
        List<Map<String, Object>> infos = new ArrayList<Map<String, Object>>();
        Map<String, List<Object>> maps = new HashMap<String, List<Object>>();
        for (Map<String, Object> item : list) {
            String tid = item.get("tid").toString();
            if (maps.containsKey(tid)) {
                maps.get(tid).add(item);
            } else {
                List _list = new ArrayList<Object>();
                _list.add(item);
                maps.put(tid, _list);
            }
        }
        for (Map.Entry<String, List<Object>> item : maps.entrySet()) {
            Map<String, Object> _map=new HashMap<String, Object>();
            Map<String, Object> mobj=(Map<String, Object>)item.getValue().get(0);
            _map.put("tid",mobj.get("tid"));
            _map.put("tmplate_name",mobj.get("template_name"));

            List<GenTemplateFilesEntity> listFiles=new ArrayList<GenTemplateFilesEntity>();
            for(Object _item : (List<Object>)item.getValue()){
                Map<String, Object> _mm= (Map<String, Object>)_item;
                GenTemplateFilesEntity fentity=new GenTemplateFilesEntity();
                fentity.setFileName(_mm.get("file_name").toString());
                fentity.setId(_mm.get("fid").toString());
                listFiles.add(fentity);
            }
            Gson gs=new Gson();
            String strFiles= gs.toJson(listFiles);
            _map.put("files",strFiles);
            infos.add(_map);
        }
        return infos;
    }

}
