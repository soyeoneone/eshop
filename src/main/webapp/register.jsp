<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/messages_zh.min.js"></script>
<script type="text/javascript" src="js/verify.js"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function () {
		//用户名校验
		$("#username").blur(function () {
			if($("#username").val() == "") {
				$("#checkmsg").html("")
				return false;
			}
			var xmlHttpRequest = new XMLHttpRequest();
			xmlHttpRequest.onreadystatechange = function () {
				if(xmlHttpRequest.readyState === 4)
					if(xmlHttpRequest.status === 200) {
						//TODO 错误信息冲突
						$("#checkmsg").html(xmlHttpRequest.responseText)
					}
			}
			xmlHttpRequest.open("post","user")
			xmlHttpRequest.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded")
			xmlHttpRequest.send("method=checkUsername&username="+$(this).val())
		})
	})
</script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
	.error{
		color: red;
	}
</style>
</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form id="reg_form" method="post" action="/user" class="form-horizontal" style="margin-top: 5px;">
					<input type="hidden" name="method" value="register">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input name="username" type="text" class="form-control" id="username"
								placeholder="请输入用户名">
							<span id="checkmsg" class="error radio-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input name="password" type="password" class="form-control" id="inputPassword3"
								placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input name="repassword" type="password" class="form-control" id="confirmpwd"
								placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Tel</label>
						<div class="col-sm-6">
							<input name="telephone" type="tel" class="form-control" id="tel"
								   placeholder="请输入手机号码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input name="email" type="email" class="form-control" id="inputEmail3"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input name="name" type="text" class="form-control" id="usercaption"
								placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio1" value="男">
								男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio2" value="女">
								女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input id="date" name="birthday" type="date" class="form-control">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control">

						</div>
						<div class="col-sm-2">
							<img src="image/captcha.jhtml" />
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




