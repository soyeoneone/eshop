<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/css/Style1.css"
	rel="stylesheet" type="text/css" />
<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	function addProduct(){
		window.location.href = "${pageContext.request.contextPath}/admin/product/add.jsp";
	}
</script>
<script type="text/javascript">
	//跳转到指定页码
	$(document).ready(function () {
		$("#b1").click(function () {
			var currentPage = $("#currentPage").val()
			window.location.href = "${pageContext.request.contextPath}/admin/product?method=readAll&currentPage="+currentPage;
		})
	})
	//搜索
	$(document).ready(function () {
		$("#searchType").change(function () {
			var type = $(this).children('option:selected').val()
			if("pcategory"==type)
				$("#categorys").show()
			else
				$("#categorys").hide()
		})
		//预处理,异步查询产品类型
		$.post("/admin/category",{method:"readCategorys"},function (data) {
			var optionitem = ""
			$(data).each(function () {
				optionitem += "<option value="+this.cid+">"+this.cname+"</option>"
			})
			$("#categorys").html(optionitem)
		})
	})
	//删除产品
	$(document).ready(function () {
		$(".delbutton").click(function () {
			var conf = confirm("是否确认删除?");
			if(conf) {
				var $delitem = $(this).parent().parent()
				var $pid = $(this).attr("name")
				$.post("/admin/product", {method: "delete",pid:$pid}, function (data) {
					if ("success"==data) {
						$delitem.remove()
					}
				});
			}
		})
	})
	//动态为form表单添加数据并提交
	function formAppendSubmit(pageNum){
		var myform=$('#Form1'); //得到form对象
		var tmpInput=$("<input type='hidden' name='currentPage'/>");
		tmpInput.attr("value",pageNum);
		myform.append(tmpInput);
		myform.submit();
	}
	$(document).ready(function () {
		var optvalue = "${requestScope.pageBean.searchType}";
		$("#searchType").find("option[value = '"+optvalue+"']").attr("selected","selected");
	})
</script>
</HEAD>
<body>
	<br>
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/admin/product"
		method="post">
		<input type="hidden" name="method" value="readAll">
		<table cellSpacing="1" cellPadding="0" width="100%" align="center"
			bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
					</TD>
				</tr>
				<tr>
					<td class="ta_01" align="right">
						<div style="float: left">商品搜索:
							<select id="searchType" name="searchType">
								<option value="pname">按商品名称</option>
								<option value="pdesc">按商品描述</option>
								<option value="pcategory">按商品类型</option>
							</select>
							<select id="categorys" name="cid" style="display: none;">

							</select>
							<input type="text" name="keyword" value="${requestScope.pageBean.keyword}">
							<input type="button" value="搜索" onclick="formAppendSubmit(1)">
						</div>
						<button type="button" id="add" name="add" value="添加"
							class="button_add" onclick="addProduct()">
							&#28155;&#21152;</button>

					</td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe">
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

								<td align="center" width="18%">序号</td>
								<td align="center" width="17%">商品图片</td>
								<td align="center" width="17%">商品名称</td>
								<td align="center" width="17%">商品价格</td>
								<td align="center" width="17%">是否热门</td>
								<td width="7%" align="center">编辑</td>
								<td width="7%" align="center">删除</td>
							</tr>
							<c:forEach items="${requestScope.pageBean.products}" var="p">
							<tr onmouseover="this.style.backgroundColor = 'white'"
								onmouseout="this.style.backgroundColor = '#F5FAFE';">
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="18%">${p.pid}</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%"><img width="40" height="45" src="${ pageContext.request.contextPath }/${p.pimage}"></td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${p.pname}</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">${p.shop_price}</td>
								<td style="CURSOR: hand; HEIGHT: 22px" align="center"
									width="17%">
									<c:if test="${p.is_hot eq 0}">否</c:if>
									<c:if test="${p.is_hot eq 1}">是</c:if>
								</td>
								<td align="center" style="HEIGHT: 22px">
                                    <a href="${ pageContext.request.contextPath }/admin/product?method=toEdit&pid=${p.pid}">
                                        <img
										src="${pageContext.request.contextPath}/images/i_edit.gif"
										border="0" style="CURSOR: hand">
								</a></td>

								<td align="center" style="HEIGHT: 22px">
                                    <a class="delbutton" name="${p.pid}" href="javascript:void(0)">
                                        <img
										src="${pageContext.request.contextPath}/images/i_del.gif"
										width="16" height="16" border="0" style="CURSOR: hand">
                                </a></td>
							</tr>
							</c:forEach>
							<tr align="center">
								<td colspan="7">
									第${requestScope.pageBean.currentPage}页/共${requestScope.pageBean.totalPage}页
									<a href="javascript:void(0)" onclick="formAppendSubmit(1)">首页</a>
									<a href="javascript:void(0)" onclick="formAppendSubmit(${requestScope.pageBean.prePage})">上一页</a>
									<a href="javascript:void(0)" onclick="formAppendSubmit(${requestScope.pageBean.nextPage})">下一页</a>
									<a href="javascript:void(0)" onclick="formAppendSubmit(${requestScope.pageBean.totalPage})">末页</a>
									跳转到第<input id="currentPage" type="number" min="1" max="${requestScope.pageBean.totalPage}" style="width: 50px">页<input id="b1" type="button" value="前往">
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</TBODY>
		</table>
	</form>
</body>
</HTML>

