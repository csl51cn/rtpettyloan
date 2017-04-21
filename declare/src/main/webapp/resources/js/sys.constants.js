/** 是否系统预置 */
var isFix = [{"code":"N","name":"否"}, {"code":"Y","name":"是"}];

/** 申报操作类型 */
var declareActionType = [{"code":"A","name":"新建"},{"code":"C","name":"修改"},{"code":"D","name":"删除"}];

/** 流程状态 */
var processStatus = [{"code":"","name":"全部"},{"code":"1","name":"新建"},{"code":"2","name":"待复核"},{"code":"4","name":"已复核"},{"code":"5","name":"经办更正"}];

/** 是否已申报类型 */
var sfysbType = [{"code":"N","name":"未申报"}, {"code":"Y","name":"已申报"}];

/**
 * 根据操作类型CODE获取名称
 * @param code
 */
function getActionType(code) {
	for(var json in declareActionType) {
		if (declareActionType[json].code == code) {
			return declareActionType[json].name;
		}
	}
}

/**
 * 根据CODE获取名称
 * @param code
 */
function getProcessStatus(code) {
	for(var json in processStatus) {
		if (processStatus[json].code == code) {
			return processStatus[json].name;
		}
	}
}

/**
 * 根据操作类型CODE获取名称
 * @param code
 */
function getSfysbType(code) {
	for(var json in sfysbType) {
		if (sfysbType[json].code == code) {
			return sfysbType[json].name;
		}
	}
}

