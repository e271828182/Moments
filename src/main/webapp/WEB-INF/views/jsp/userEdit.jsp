<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" pageEncoding="utf-8"%>
<form action="${pageContext.request.contextPath }/edit" method="post">
		<table border="1">
			<caption>人员信息列表</caption>
			<tr>
				<td>id</td>
				<td>name</td>
				<td>sex</td>
				<td>age</td>
				<td>birthday</td>
			</tr>
			<tr>
				<td><input name="id" value="${user.id}"></td>
				<td><input name="name" value="${user.name}"></td>
				<td><input name="sex" value="${user.sex}"></td>
				<td><input name="age" value="${user.age}"></td>
 				<td><input name="birthday" value="${user.birthday}"></td>
			</tr>
		</table>
		<input type="submit" value="提交">
	</form>
