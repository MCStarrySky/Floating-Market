//��������������������������������������������������������
//	JavaScript�ű��ļ�
//	������ö�Ӧ�ĺ������ؼ۸�  ����ʹ��/fg setformula �������� ��������Ʒ�ļ��㺯����������ƷĬ��ʹ��Price����
//		Price������Ĭ�ϵļ���۸�ĺ���
//  	stock������Ĭ�ϵļ���������ĺ���
//      variable.getNumberOfItems()   ��ȡ��Ʒ������
//��������������������������������������������������������

//----------------------------
//Ԥ����ֵ����
//----------------------------
var jibengjiage = 10; //�����۸� ������Ʒ��û�б�����ʱ�ļ۸�
var goumaibianliang = 0.01;//ÿ�α�����۸����ӻ���ٵļ۸�
var zuidijiage = 0.01;//��Ʒ�����۵�������۸�ͺ�����������۸�
var kong = -99; //���������ֵ���ڳ���
var man = 99; //��������������չ�
//-----------------------------
//����������Ϊ��Ԥ�躯��������ֻ���޸�Ԥ����ֵ���򼴿ɣ�����������Ҫ�޸�
//-----------------------------
//Ĭ�ϵļ���۸�ķ���
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
//Ĭ�ϵļ���������ķ���
function stock(variable){
	var stock = -variable.getNumberOfItems();//���͹��������Ƿ������ԼӸ�-�žͿ����ˡ�
	if(stock<=kong){
		return "������"+kong+"���ٳ���";
	}
	if(stock>=man){
		return "������"+man+"�����չ�";
	}
	return stock;//����Ҫ��ʾ��ֵ���߲����
}