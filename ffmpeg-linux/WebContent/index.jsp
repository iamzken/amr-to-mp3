<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>amr音频播放 - 例子</title>
</head>
<body>
	<audio src="${pageContext.request.contextPath }/media/1.amr" autoplay="autoplay" controls="controls" >
		请使用支持HTML5的浏览器！
	</audio>
	<audio src="${pageContext.request.contextPath }/media/2.amr" autoplay="autoplay" controls="controls" >
		请使用支持HTML5的浏览器！
	</audio>
</body>
</html>