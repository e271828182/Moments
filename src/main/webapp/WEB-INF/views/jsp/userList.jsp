<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="utf-8"%>
<table border="1">
		<caption>人员信息列表</caption>
		<tr>
			<td>id</td>
			<td>name</td>
			<td>sex</td>
			<td>age</td>
			<td>birthday</td>
			<td>删除</td>
		</tr>
	<c:forEach items="${usersLise }" var="list">
		<tr>
			<td>${list.id}</td>
			<td>${list.name}</td>
			<td>${list.sex}</td>
			<td>${list.age}</td>
			<td>${list.birthday}</td>
			<td><a href="${pageContext.request.contextPath }/delete?id=${list.id}">删除</a>
			<a href="${pageContext.request.contextPath }/toEdit?id=${list.id}">修改</a></td>
		</tr>
	</c:forEach>
	</table>
