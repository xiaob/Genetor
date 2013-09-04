Java代码生成器，可根据数据库表+模版文件自动生成需要的各种文件：类、配置文件、JSP等，省去大量重复的工作。

![QQ图片20130826120939](http://git.oschina.net/uploads/images/2013/0826/120944_68bc9c22_16855.jpeg)
***
可在 **\template\** 文件夹下添加 生成的模板

**模板文件必须以 .vm 作为后缀。**

模版文件开头必须 **#*生成的文件路径（含文件名）*#**

如： #*com/controller/{objectName}.java*#

其中可用参数为

	objectName ： 对象名（可自定义）

	tableName  ： 表名

	dbName     ： 数据库名

	
模板引擎采用 Velocity，所以语法规则请参考Velocity的语法规则。
其中可用参数如下：

	objectName ： 对象名（可自定义）

	tableName  ： 表名

	dbName     ： 数据库名

	pk		   ： 主键的名字

	fieldList  ： 所选的column的集合List

	fieldList->Field{

		columnType	: 数据库数据类型

		name		: 名字

		dataType	: 转为Java后的类型,可在datatype.ini进行配置。

		name_fl		：开头首个字母为小写的名字 如：Hello -> hello ; Hello-joy -> helloJoy

		name_fu		：开头首个字母为大写的名字如：hello -> Hello ; hello-joy -> HelloJoy

		remark		: 注释 (可自定义)

		datasize	: 数据库长度(INT)

		nullable	: 可否为空(Boolean)
		
		regex       : 验证的key，下文将会说出对应关系。

	}
	
验证的key和name对应关系

		"en"> "纯英文"
        "number"> "纯数字"
        "account"> "字母数字下划线"
        "email"> "电子邮箱"
        "url"> "网址"
        "id"> "身份证"
        "phone"> "电话号码"
        "zip"> "中国邮政编码"
        "custom"> "自定义"