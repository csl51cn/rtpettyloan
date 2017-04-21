package com.global.framework.system.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.dbutils.support.PageBean;
import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.domain.DataDictClass;
import com.global.framework.system.service.DataDictService;
import com.global.web.BaseController;

/**
 * 
 * @author cqchenf@qq.com
 * @date 2012-8-28 下午4:54:13
 * @version v1.0
 */
@Controller
@RequestMapping("/sys/dataDictController.do")
public class DataDictController extends BaseController {
	@Autowired
	private DataDictService dataDictService;

	/**
	 * 转到字典分类列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "method=listDictClass")
	public String listDictClass(Model model) {
		return "sys/dataDict/dataDictClassList";
	}

	/**
	 * 分页查询字典分类列表数据
	 * 
	 * @param dictClass
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryDictClass")
	public Map<String, Object> queryDictClass(DataDictClass dictClass,
			PageBean page) throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.dataDictService.queryDictClassForPage(
				dictClass, page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}

	/**
	 * 转到编辑字典分类页面
	 * 
	 * @param model
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=editDictClass")
	public String editDictClass(DataDictClass dictClass, Model model)
			throws BaseException {
		if (dictClass != null && StringUtils.isNotBlank(dictClass.getClassId())) {
			dictClass = this.dataDictService.getDictClass(dictClass);
			model.addAttribute("dictClass", dictClass);
		}
		return "sys/dataDict/dataDictClassEdit";
	}

	/**
	 * 保存新增字典分类数据
	 * 
	 * @param dictClass
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=saveDictClass")
	public DataDictClass saveDictClass(DataDictClass dictClass)
			throws BaseException {
		return this.dataDictService.saveOrUpdateDictClass(dictClass);
	}

	/**
	 * 删除字典分类
	 * 
	 * @param dictClass
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=deleteDictClass")
	public void deleteDictClass(DataDictClass dictClass) throws BaseException {
		this.dataDictService.deleteDictClass(dictClass);
	}

	// =============================================================================数据字典代码
	/**
	 * 查询所有数据字典类别
	 * 
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getDataDictClassList")
	public List<DataDictClass> getDataDictClassList() throws BaseException {
		return this.dataDictService.getDictClassList();
	}

	/**
	 * 转到字典代码列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "method=listDataDict")
	public String listDataDict() {
		return "sys/dataDict/dataDictList";
	}

	/**
	 * 分页查询字典代码列表数据
	 * 
	 * @param dataDict
	 * @param page
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=queryDataDict")
	public Map<String, Object> queryDataDict(DataDict dataDict, PageBean page)
			throws BaseException {
		Map<String, Object> map = new HashMap<String, Object>(2);
		PageBean pageBean = this.dataDictService.queryDataDictForPage(dataDict,
				page);
		map.put(LIST_TOTAL, pageBean.getTotalRows());
		map.put(LIST_ROWS, pageBean.getDataList());
		return map;
	}

	/**
	 * 转到编辑字典代码页面
	 * 
	 * @param model
	 * @return
	 * @throws BaseException
	 */
	@RequestMapping(params = "method=editDataDict")
	public String editDataDict(DataDict dataDict, Model model)
			throws BaseException {
		if (dataDict != null && StringUtils.isNotBlank(dataDict.getDictId())) {
			dataDict = this.dataDictService.getDataDict(dataDict);
			model.addAttribute("dataDict", dataDict);
		}
		return "sys/dataDict/dataDictEdit";
	}

	/**
	 * 保存新增字典代码数据
	 * 
	 * @param dictClass
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=saveDataDict")
	public DataDict saveDataDict(DataDict dataDict) throws BaseException {
		return this.dataDictService.saveOrUpdateDataDict(dataDict);
	}

	/**
	 * 删除字典代码
	 * 
	 * @param dataDict
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=deleteDataDict")
	public void deleteDataDict(DataDict dataDict) throws BaseException {
		this.dataDictService.deleteDataDict(dataDict);
	}
	
	@ResponseBody
	@RequestMapping(params = "method=loadDictCatelog")
	public List<String> loadDictCatelog() throws BaseException {
		return this.dataDictService.getDictCatelog();
	}
	
}
