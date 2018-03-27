目录介绍
	1. doc:
		存放各类文档
	2. monitor-admin
		web app,管理后台
	3. monitor-android
		手机端,android
		需要请求手机页面则通过http请求shenbian-web
		需要直接请求数据,则通过thrift去调用shenbian-rpc
	4. monitor-ios
		手机端,ios
		需要请求手机页面则通过http请求shenbian-web
		需要直接请求数据,则通过thrift去调用shenbian-rpc
	5. monitor-rpc
		rpc server,用于内部service远程调用
		对外需要再包装一层
	6. monitor-services
		jar包,代码各种services
	7. monitor-web
		web app,前台网站
		移动端的网站路径以/m开头,如/m/user.PC端网站路径则直接/user
	8.script
		各种脚本,也包含数据库脚本文件
