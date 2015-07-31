package com.buss.template.service;
import com.buss.template.entity.GenTemplateEntity;
import com.buss.files.entity.GenTemplateFilesEntity;

import java.util.List;
import org.jeecgframework.core.common.service.CommonService;
import java.io.Serializable;

public interface GenTemplateServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(GenTemplateEntity genTemplate,
	        List<GenTemplateFilesEntity> genTemplateFilesList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(GenTemplateEntity genTemplate,
	        List<GenTemplateFilesEntity> genTemplateFilesList);
	public void delMain (GenTemplateEntity genTemplate);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(GenTemplateEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(GenTemplateEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(GenTemplateEntity t);
}
