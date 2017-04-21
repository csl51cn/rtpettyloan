package com.global.param.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.global.framework.exception.BaseException;
import com.global.framework.system.domain.DataDict;
import com.global.framework.system.web.common.CacheService;
import com.global.param.domain.Country;
import com.global.param.domain.Currency;
import com.global.web.BaseController;

/**
 * 
 * @author chen.feng
 * @date 2014-6-14 下午10:56:08
 */
@Controller
@RequestMapping("/param/paramCommonController.do")
public class ParamCommonController extends BaseController {

	/**
	 * 获取国家信息
	 * 
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getCountrys")
	public List<Country> getCountrys() throws BaseException {
		return CacheService.getCountryCacheList();
	}

	/**
	 * 根据safeCode获取国家信息
	 * 
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getCountryBySafeCode")
	public Country getCountryBySafeCode(String safeCode) throws BaseException {
		return CacheService.getCountryCacheById(safeCode);
	}

	/**
	 * 获取币种列表
	 * 
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getCurrencys")
	public List<Currency> getCurrencys() throws BaseException {
		return CacheService.getCurrencyCacheList();
	}

	/**
	 * 根据SIGN获取币种代码信息
	 * 
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getCurrencyBySign")
	public Currency getCurrencyBySign(String sign) throws BaseException {
		return CacheService.getCurrencyCacheById(sign);
	}

	/**
	 * 根据字典类别代码和字典代码获取字典信息
	 * 
	 * @param catalogCode
	 * @param dictCode
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getDatadictByValue")
	public DataDict getDatadictByValue(String catalogCode, String dictCode)
			throws BaseException {
		DataDict dict = CacheService.getDictCacheById(catalogCode, dictCode);
		return dict;
	}

	/**
	 * 根据字典类别CODE获取数据字典
	 * 
	 * @param catalogCode
	 *            数据字典类别code
	 * @return
	 * @throws BaseException
	 */
	@ResponseBody
	@RequestMapping(params = "method=getDatadict")
	public List<DataDict> getDatadict(String code) throws BaseException {
		return CacheService.getDataDictListByCatalogCode(code);
	}
}
