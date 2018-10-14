<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function () {
				$("#password").blur(function () {
					var $password = $(this).val()
					$.post("/admin/adminuser",{method:"checkPassword",password:$(this).val()},function (data) {
						$("#ajaxmsg").html(data)
					})
				})
				$("#userAction_save_do_submit").click(function () {
					if(!$("#ajaxmsg").html() == "密码正确!") {
						alert("请输入正确的原密码!")
						return false
					}
					if(!$("#newpassword").val()) {
						alert("新密码不能为空!")
						return false
					}
					if(!($("#repassword").val() == $("#newpassword").val())) {
						alert("两次输入的新密码不一致!")
						return false
					}
					return true
				})
			})
		</script>
	</HEAD>
	
	<body>
		<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/admin/adminuser" target="_parent" method="post">
			<input type="hidden" name="method" value="updatePassword">
			&nbsp;
			<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
						height="26">
						<strong><STRONG>修改密码</STRONG>
						</strong>
					</td>
				</tr>

				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						管理员：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						${sessionScope.adminuser.name}
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						原密码：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input id="password" type="password" name="password" value="" class="bg"/>
						<span id="ajaxmsg"></span>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						新密码：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="password" id="newpassword" name="newpassword" value="" class="bg"/>
					</td>
				</tr>
				<tr>
					<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
						确认密码：
					</td>
					<td class="ta_01" bgColor="#ffffff" colspan="3">
						<input type="password" id="repassword" name="repassword" value="" class="bg"/>
					</td>
				</tr>
			
				<tr>
					<td class="ta_01" style="WIDTH: 100%" align="center"
						bgColor="#f5fafe" colSpan="4">
						<button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
							&#30830;&#23450;
						</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
						<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
						<span id="Label1"></span>
					</td>
				</tr>
			</table>
		</form>
	</body>
</HTML>