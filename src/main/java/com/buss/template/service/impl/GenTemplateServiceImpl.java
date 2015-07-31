package com.buss.template.service.impl;
import com.buss.template.service.GenTemplateServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.buss.template.entity.GenTemplateEntity;
import com.buss.files.entity.GenTemplateFilesEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("genTemplateService")
@Transactional
public class GenTemplateServiceImpl extends CommonServiceImpl implements GenTemplateServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((GenTemplateEntity)entity);
 	}
	
	public void addMain(GenTemplateEntity genTemplate,
	        List<GenTemplateFilesEntity> genTemplateFilesList){
			//保存主信息
			this.save(genTemplate);
		
			/**保存-ss*/
			for(GenTemplateFilesEntity genTemplateFiles:genTemplateFilesList){
				//外键设置
				genTemplateFiles.setGroupId(genTemplate.getId());
				this.save(genTemplateFiles);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(genTemplate);
	}

	
	public void updateMain(GenTemplateEntity genTemplate,
	        List<GenTemplateFilesEntity> genTemplateFilesList) {
		//保存主表信息
		this.saveOrUpdate(genTemplate);
		//===================================================================================
		//获取参数
		Object id0 = genTemplate.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-ss
	    String hql0 = "from GenTemplateFilesEntity where 1 = 1 AND gROUP_ID = ? ";
	    List<GenTemplateFilesEntity> genTemplateFilesOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-ss
		for(GenTemplateFilesEntity oldE:genTemplateFilesOldList){
			boolean isUpdate = false;
				for(GenTemplateFilesEntity sendE:genTemplateFilesList){
					//需要更新的明细数据-ss
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-ss
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-ss
			for(GenTemplateFilesEntity genTemplateFiles:genTemplateFilesList){
				if(oConvertUtils.isEmpty(genTemplateFiles.getId())){
					//外键设置
					genTemplateFiles.setGroupId(genTemplate.getId());
					this.save(genTemplateFiles);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(genTemplate);
	}

	
	public void delMain(GenTemplateEntity genTemplate) {
		//删除主表信息
		this.delete(genTemplate);
		//===================================================================================
		//获取参数
		Object id0 = genTemplate.getId();
		//===================================================================================
		//删除-ss
	    String hql0 = "from GenTemplateFilesEntity where 1 = 1 AND gROUP_ID = ? ";
	    List<GenTemplateFilesEntity> genTemplateFilesOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(genTemplateFilesOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(GenTemplateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(GenTemplateEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(GenTemplateEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,GenTemplateEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{template_name}",String.valueOf(t.getTemplateName()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}