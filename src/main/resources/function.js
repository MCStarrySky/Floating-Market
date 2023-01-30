//————————————————————————————
//	JavaScript脚本文件
//	插件调用对应的函数返回价格  可以使用/fg setformula 函数名字 来设置物品的计算函数，所有物品默认使用Price函数
//		Price———默认的计算价格的函数
//  	stock———默认的计算库存变量的函数
//      variable.getNumberOfItems()   获取物品的数量
//————————————————————————————

//----------------------------
//预设数值区域
//----------------------------
var jibengjiage = 10; //基本价格 所有物品在没有被购买时的价格
var goumaibianliang = 0.01;//每次被购买价格增加或减少的价格
var zuidijiage = 0.01;//商品被跌价到比这个价格低后会继续持这个价格
var kong = -99; //库存低于这个值不在出售
var man = 99; //库存高于这个不再收购
//-----------------------------
//函数区域，因为是预设函数，所以只用修改预设数值区域即可，函数区域不需要修改
//-----------------------------
//默认的计算价格的方法
function Price(variable){
	var shuliang = variable.getNumberOfItems();
	var stock = -variable.getNumberOfItems();
	var jiage = shuliang*goumaibianliang+jibengjiage;
	if(jiage<zuidijiage){
		jiage = zuidijiage;
	}
	if(stock<kong){
		return -1;
	}
	if(stock>man){
		return -1;
	}
	return jiage;
}
//默认的计算库存变量的方法
function stock(variable){
	var stock = -variable.getNumberOfItems();//库存和购买数量是反的所以加个-号就可以了。
	if(stock<=kong){
		return "库存低于"+kong+"不再出售";
	}
	if(stock>=man){
		return "库存高于"+man+"不再收购";
	}
	return stock;//把需要显示的值告诉插件。
}