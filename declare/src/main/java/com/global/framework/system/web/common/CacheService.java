package com.global.framework.system.web.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.domain.DataDictClass;
import com.global.framework.system.domain.Menu;
import com.global.framework.system.domain.Operate;
import com.global.framework.system.domain.Org;
import com.global.framework.system.domain.Role;
import com.global.framework.system.domain.RoleRight;
import com.global.framework.system.service.DataDictService;
import com.global.framework.system.service.MenuService;
import com.global.framework.system.service.OperateService;
import com.global.framework.system.service.OrgService;
import com.global.framework.system.service.RoleService;
import com.global.framework.util.SpringContextUtil;
import com.global.param.domain.CommMode;
import com.global.param.domain.Country;
import com.global.param.domain.Currency;
import com.global.param.service.CommModeService;
import com.global.param.service.CountryService;
import com.global.param.service.CurrencyService;
import com.global.workflow.domain.TradeCode;
import com.global.workflow.service.TradeCodeService;

/**
 * 缓存服务类
 * @author cqchenf@qq.com
 * @date 2012-1-3 上午12:10:04
 * @version v1.0
 */
public final class CacheService {
	private static final Log log = LogFactory.getLog(CacheService.class);
	private static CacheService instance = null;
	
	/**
	 * 获取缓存服务类实例
	 * @return
	 */
	public static synchronized CacheService getInstance() throws BaseException {
		if (null == instance) {
			instance = new CacheService();
		}
		return instance;
	}
	
	/**
	 * 刷新所有缓存
	 */
	public void refresh() throws BaseException {
		instance = new CacheService();
	}
	
	private static List<Org> orgCacheList = null;
	private static Map<String, Org> orgCacheMap = new ConcurrentHashMap<String, Org>();
	
	private static List<TradeCode> tradeCodeCacheList = null;
	private static Map<String, TradeCode> tradeCodeCacheMap = new ConcurrentHashMap<String, TradeCode>();
	
	private static List<Menu> menuGroupCacheList = null;
	private static Map<String, Menu> menuGroupCacheMap = new ConcurrentHashMap<String, Menu>();
	
	private static List<Menu> menuCacheList = null;
	private static Map<String, Menu> menuCacheMap = new ConcurrentHashMap<String, Menu>();
	
	private static List<Menu> menuAllCacheList = null;
	private static Map<String, Menu> menuAllCacheMap = new ConcurrentHashMap<String, Menu>();
	
	private static List<Role> roleCacheList = null;
	private static Map<String, Role> roleCacheMap = new ConcurrentHashMap<String, Role>();
	
	private static List<Operate> operateCacheList = null;
	private static Map<String, Operate> operateCacheMap = new ConcurrentHashMap<String, Operate>();
	
	private static List<Operate> noCheckRightCacheList = null;
	private static Map<String, Operate> noCheckOperateCacheMap = new ConcurrentHashMap<String, Operate>();
	
	private static List<DataDictClass> dictClassCacheList = null;
	private static Map<String, DataDictClass> dictClassCacheMap = new ConcurrentHashMap<String, DataDictClass>();
	
	private static List<DataDict> dictCacheList = null;
	private static Map<String, DataDict> dictCacheMap = new ConcurrentHashMap<String, DataDict>();
	
	//国家信息
	private static List<Country> countryCacheList = null;
	private static Map<String, Country> countryCacheMap = new ConcurrentHashMap<String, Country>();
	
	//币种信息
	private static List<Currency> currencyCacheList = null;
	private static Map<String, Currency> currencyCacheMap = new ConcurrentHashMap<String, Currency>();
	
	//渠道通讯方式
	private static List<CommMode> commModeCacheList = null;
	private static Map<String, CommMode> commModeCacheMap = new ConcurrentHashMap<String, CommMode>();
	
	private OrgService orgService = (OrgService) SpringContextUtil.getBean("orgService");
	private TradeCodeService tradeCodeService = (TradeCodeService) SpringContextUtil.getBean("tradeCodeService");
	private MenuService menuService = (MenuService) SpringContextUtil.getBean("menuService");
	private RoleService roleService = (RoleService) SpringContextUtil.getBean("roleService");
	private OperateService operateService = (OperateService) SpringContextUtil.getBean("operateService");
	private DataDictService dataDictService = (DataDictService) SpringContextUtil.getBean("dataDictService");
	private CountryService countryService = (CountryService) SpringContextUtil.getBean("countryService");
	private CurrencyService currencyService = (CurrencyService) SpringContextUtil.getBean("currencyService");
	private CommModeService commModeService = (CommModeService) SpringContextUtil.getBean("commModeService");

	private CacheService() throws BaseException {
		// 加载机构
		loadOrgAll();
		loadTradeCodeAll();

		// 加载角色信息
		loadRoleAll();
		
		loadRightAll();

		// 加载菜单信息
		loadMenuAll();
		
		//加载数据字典
		loadDataDictAll();
		
		//加载国家信息
		loadCountry();
		
		loadCommMode();
		
		loadCurrency();
	}
	
	/**
	 * 加载所有机构信息
	 */
	public void loadOrgAll(){
		try {
			orgCacheList = this.orgService.getOrgList();
			//设置机构信息的缓存
			if(orgCacheList != null){
				Iterator<Org> it = orgCacheList.iterator();
				while(it.hasNext()){
					Org dto = it.next();
					orgCacheMap.put(dto.getOrgId(), dto);
					orgCacheMap.put(dto.getOrgCode(), dto);
				}
			}
			log.info("加载机构信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载机构信息发生异常： ", e);
		}
	}
	
	public void loadTradeCodeAll(){
		try {
			tradeCodeCacheList = this.tradeCodeService.getTradeCodeList();
			if(tradeCodeCacheList != null){
				Iterator<TradeCode> it = tradeCodeCacheList.iterator();
				while(it.hasNext()){
					TradeCode dto = it.next();
					tradeCodeCacheMap.put(dto.getTradeNo(), dto);
				}
			}
			log.info("加载交易编码信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载交易编码信息发生异常： ", e);
		}
	}
	
	/**
	 * 加载所有角色信息
	 */
	public void loadRoleAll() {
		try {
			roleCacheList = this.roleService.getRoleList();
			//设置角色信息的缓存
			if(roleCacheList != null){
				Iterator<Role> it = roleCacheList.iterator();
				while (it.hasNext()) {
					Role dto = it.next();
					roleCacheMap.put(dto.getRoleId(), dto);
				}
			}
			log.info("加载角色信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载角色信息发生异常： ", e);
		}
	}
	
	/**
	 * 加载所有权限信息
	 */
	public void loadRightAll() {
		try {
			operateCacheList = this.operateService.getOperateList();
			if(operateCacheList != null){
				Iterator<Operate> it = operateCacheList.iterator();
				while (it.hasNext()) {
					Operate dto = it.next();
					operateCacheMap.put(dto.getOperateId(), dto);
				}
			}
			
			noCheckRightCacheList = this.operateService.getNoCheckOperateList();
			if(noCheckRightCacheList != null){
				Iterator<Operate> it = noCheckRightCacheList.iterator();
				while (it.hasNext()) {
					Operate dto = it.next();
					noCheckOperateCacheMap.put(dto.getOperateId(), dto);
				}
			}
			log.info("加载权限信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载权限信息发生异常： ", e);
		}
	}
	
	/**
	 * 加载所有菜单信息
	 */
	public void loadMenuAll() {
		try {
			menuGroupCacheList = this.menuService.getMenuGroupList();
			// 设置菜单组信息的缓存
			if (menuGroupCacheList != null) {

				Iterator<Menu> it = menuGroupCacheList.iterator();
				while (it.hasNext()) {
					Menu dto = it.next();
					menuGroupCacheMap.put(dto.getMenuId(), dto);
				}
			}
			
			menuCacheList = this.menuService.getMenuList();
			//设置菜单信息的缓存
			if(menuCacheList != null){
				Iterator<Menu> it = menuCacheList.iterator();
				while (it.hasNext()) {
					Menu dto = it.next();
					menuCacheMap.put(dto.getMenuId(), dto);
				}
			}
			
			menuAllCacheList = this.menuService.getMenuAll();
			//设置所有菜单信息的缓存
			if(menuAllCacheList != null){
				Iterator<Menu> it = menuAllCacheList.iterator();
				while (it.hasNext()) {
					Menu dto = it.next();
					menuAllCacheMap.put(dto.getMenuId(), dto);
				}
			}
			
			log.info("加载菜单信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载菜单信息发生异常： ", e);
		}
	}
	
	/**
	 * 加载数据字典
	 */
	public void loadDataDictAll(){
		try {
			//字典分类
			dictClassCacheList = this.dataDictService.getDictClassList();
			if(dictClassCacheList != null){
				Iterator<DataDictClass> it = dictClassCacheList.iterator();
				while (it.hasNext()) {
					DataDictClass dto = it.next();
					dictClassCacheMap.put(dto.getClassId(), dto);
					dictClassCacheMap.put(dto.getClassCode(), dto);
				}
			}
			
			//字典代码
			dictCacheList = this.dataDictService.getDataDictList();
			if(dictCacheList != null){
				Iterator<DataDict> it = dictCacheList.iterator();
				while (it.hasNext()) {
					DataDict dto = it.next();
					dictCacheMap.put(dto.getDictId(), dto);
				}
			}
			log.info("加载数据字典成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载数据字典发生异常： ", e);
		}
	}
	
	/**
	 * 加载所有国家信息
	 */
	public void loadCountry() {
		try {
			countryCacheList = this.countryService.getCountryList();
			if(countryCacheList != null){
				Iterator<Country> it = countryCacheList.iterator();
				while (it.hasNext()) {
					Country dto = it.next();
					//countryCacheMap.put(dto.getCountryNo(), dto);
					countryCacheMap.put(dto.getSafeCode(), dto);
				}
			}
			log.info("加载所有国家信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载所有国家信息失败： ", e);
		}
	}
	
	public void loadCurrency() {
		try {
			currencyCacheList = this.currencyService.getCurrencyList();
			if(currencyCacheList != null){
				Iterator<Currency> it = currencyCacheList.iterator();
				while (it.hasNext()) {
					Currency dto = it.next();
					currencyCacheMap.put(dto.getCurSign(), dto);
				}
			}
			log.info("加载所有币种信息成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载所有币种信息失败： ", e);
		}
	}
	

	/**
	 * 获取缓存中的机构信息
	 * @return 所有机构信息列表，没有机构信息时返回null
	 */
	public static List<Org> getOrgCacheList() {
		return orgCacheList;
	}
	
	/**
	 * 获取当前机构的下属机构
	 * @param orgId 机构主键
	 * @return
	 */
	public static List<Org> getSubOrgCacheList(String orgId) {
		List<Org> orgList = new ArrayList<Org>();
		for (Org org : orgCacheList) {
			if (orgId.equals(org.getParentOrgId())) {
				orgList.add(org);
			}
		}
		return orgList;
	}
	
	/**
	 * 根据机构ID获取机构信息实体
	 * @param orgId
	 * @return
	 */
	public static Org getOrgById(String orgId){
		return orgCacheMap.get(orgId);
	}
	
	public static Org getOrgByCode(String orgCode){
		return orgCacheMap.get(orgCode);
	}
	
	public static List<TradeCode> getTradeCodeCacheList() {
		return tradeCodeCacheList;
	}
	
	public static TradeCode getTradeCodeCacheById(String tradeNo) {
		for (TradeCode t : tradeCodeCacheList) {
			if(t.getTradeNo().equals(tradeNo)){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * 获取缓存中的菜单组信息列表
	 * @return
	 */
	public static List<Menu> getMenuGroupCacheList() {
		return menuGroupCacheList;
	}
	
	/**
	 * 获取缓存中的菜单栏目信息列表
	 * @return
	 */
	public static List<Menu> getMenuCacheList() {
		return menuCacheList;
	}
	
	/**
	 * 根据菜单ID获取缓存中的菜单栏目信息列表
	 * @return
	 */
	public static List<Menu> getMenuCacheList(String menuId) {
		List<Menu> menuList = new ArrayList<Menu>();
		for (Menu menu : menuCacheList) {
			if (menuId.equals(menu.getMenuId())) {
				menuList.add(menu);
			}
		}
		return menuList;
	}
	
	public static List<Role> getRoleCacheList(){
		return roleCacheList;
	}

	public static List<Menu> getMenuAllCacheList() {
		return menuAllCacheList;
	}

	public static Menu getMenuById(String menuId) {
		for (Menu menu : menuAllCacheList) {
			if (menu.getMenuId().equals(menuId)) {
				return menu;
			}
		}
		return null;
	}

	public static List<DataDictClass> getDictClassCacheList() {
		return dictClassCacheList;
	}
	
	public static DataDictClass getDictClassChcheById(String classCode) {
		for (DataDictClass catalog : dictClassCacheList) {
			if (catalog.getClassCode().equals(classCode)) {
				return catalog;
			}
		}
		return null;
	}

	public static List<DataDict> getDictCacheList() {
		return dictCacheList;
	}
	
	public static List<DataDict> getDataDictListByCatalogCode(String catalogCode) {
		DataDictClass dc = getDictClassChcheById(catalogCode);
		List<DataDict> dictList = new ArrayList<DataDict>();
		for (DataDict d : dictCacheList) {
			if (d.getClassId().equals(dc.getClassId())) {
				dictList.add(d);
			}
		}
		return dictList;
	}
	
	public static DataDict getDictCacheById(String dictId) {
		for (DataDict d : dictCacheList) {
			if(d.getDictId().equals(dictId)){
				return d;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param catalogCode 字典类别CODE
	 * @param dictCode 字典代码
	 * @return
	 */
	public static DataDict getDictCacheById(String catalogCode, String dictCode) {
		DataDictClass catalog = getDictClassChcheById(catalogCode);
		if(catalog != null) {
			for (DataDict d : dictCacheList) {
				if (d.getClassId().equals(catalog.getClassId()) && d.getDictCode().equals(dictCode)) {
					return d;
				}
			}
		}
		return null;
	}

	/**
	 * 根据分类标识获取字典代码
	 * @param classCode
	 * @return
	 */
	public static List<DataDict> getDictByClassCode(String classCode) {
		List<DataDict> dictList = new ArrayList<DataDict>();
		for (DataDictClass dictClass : dictClassCacheList) {
			if (classCode.toUpperCase().equals(dictClass.getClassCode().toUpperCase())) {
				for (DataDict dict : dictCacheList) {
					if (dict.getClassId().equals(dictClass.getClassId())) {
						dictList.add(dict);
					}
				}
				break;
			}
		}
		return dictList;
	}

	public static List<Operate> getRightCacheList() {
		return operateCacheList;
	}

	public static List<Operate> getNoCheckRightCacheList() {
		return noCheckRightCacheList;
	}

	/**
	 * 根据权限ID获取对应的菜单权限和按钮权限
	 * @param rights
	 * @return
	 */
	public static List<Map<String, String>> getRights(List<RoleRight> rights) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for (RoleRight roleRight : rights) {
			if("1".equals(roleRight.getRightType())){
				for (Menu menu : menuAllCacheList) {
					if(menu.getMenuId().equals(roleRight.getRightId())){
						Map<String, String> map = new HashMap<String, String>();
						map.put("rightId", menu.getMenuId());
						map.put("reqUrl", menu.getAccessUrl());
						list.add(map);
					}
				}
			}else{
				for (Operate right : operateCacheList) {
					if(right.getOperateId().equals(roleRight.getRightId())){
						Map<String, String> map = new HashMap<String, String>();
						map.put("rightId", right.getOperateId());
						map.put("reqUrl", right.getReqUrl());
						list.add(map);
					}
				}
			}
		}
		return list;
	}

	public static List<Country> getCountryCacheList() {
		return countryCacheList;
	}
	
	public static Country getCountryCacheById(String countryNo) {
		for (Country c : countryCacheList) {
			if(c.getSafeCode().equals(countryNo)){
				return c;
			}
		}
		return null;
	}


	public static List<Currency> getCurrencyCacheList() {
		return currencyCacheList;
	}
	
	public static Currency getCurrencyCacheById(String sign) {
		for (Currency t : currencyCacheList) {
			if(t.getCurSign().equals(sign)){
				return t;
			}
		}
		return null;
	}

	//加载渠道通讯方式
	public void loadCommMode() {
		try {
			commModeCacheList = this.commModeService.getCommModeList();
			//设置机构信息的缓存
			if(commModeCacheList != null){
				Iterator<CommMode> it = commModeCacheList.iterator();
				while(it.hasNext()){
					CommMode dto = it.next();
					commModeCacheMap.put(dto.getChannelId(), dto);
					commModeCacheMap.put(dto.getReqSysCode(), dto);
				}
			}
			log.info("加载渠道通讯方式成功！");
		} catch (Exception e) {
			throw new RuntimeException("加载渠道通讯方式发生异常： ", e);
		}
	}

	public static List<CommMode> getCommModeCacheList() {
		return commModeCacheList;
	}
	
	public static CommMode getCommModeCacheById(String channelId) {
		for (CommMode t : commModeCacheList) {
			if(t.getChannelId().equals(channelId)){
				return t;
			}
		}
		return null;
	}
	
	public static CommMode getCommModeCacheByReqSysCode(String code) {
		for (CommMode t : commModeCacheList) {
			if(t.getReqSysCode().equals(code)){
				return t;
			}
		}
		return null;
	}
}
