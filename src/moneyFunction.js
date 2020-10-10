//————————————————————————————
//	JavaScript脚本文件
//	用于计算税收
//      variable.getTransactionAmount() 可以获取玩家交易的钱数
//————————————————————————————

//----------------------------
//预设数值区域
//----------------------------
var shuilv = 0.01; //税率，用小数0.01=1%

//-----------------------------
//函数区域，因为是预设函数，所以只用修改预设数值区域即可，函数区域不需要修改
//-----------------------------
//默认的税收计算公式
function tax(variable){
	var qian = variable.getTransactionAmount();//获取玩家交易的钱数
	return qian*shuilv;//如果上面if{}里的return都没有执行代表交易金额大于100，超过50元的部分扣税1%，超过100的扣税5%
}