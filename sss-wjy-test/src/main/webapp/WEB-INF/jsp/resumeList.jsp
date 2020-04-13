<%--
  Created by IntelliJ IDEA.
  User: 继洋
  Date: 2020/4/13
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>resume列表</title>
    <script src="/js/jquery.min.js" type="text/javascript"></script>

    <script>

        function create() {
            window.location.href= '/resume/create';
        }

        function remove(params) {
            $.ajax({
                url : '/resume/remove',
                data : params,
                type : 'POST',
                dataType : 'json',
                success :function (data) {
                    alert(data.username + '删除成功');
                    window.location.href = '/resume/list';
                }
            });
        }
        function modify(params) {
            window.location.href = '/resume/create?id='+params.id;
        }
    </script>
</head>
<body>
    <input id="create" type="button" value="新增" onclick="create()" />
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>姓名</th>
                <th>住址</th>
                <th>手机</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${resumeList}" var="data">
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.address}</td>
                <td>${data.phone}</td>
                <td>
                    <a id="modify" href="javascript:void(null);" onclick="modify({'id' : '${data.id}'});">编辑</a>
                    <a id="remove" href="javascript:void(null);" onclick="remove({'id' : '${data.id}'});" >删除</a>
                </td>
            </tr>
        </c:forEach>

        </tbody>
    </table>


</body>
</html>
