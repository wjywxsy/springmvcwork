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
        var id = '${resume.id}';
        function create() {
            $.ajax({
                url : '/resume/doCreate',
                data : {
                    id : id,
                    name : $('#name').val(),
                    address : $('#address').val(),
                    phone : $('#phone').val()
                },
                type : 'POST',
                dataType : 'json',
                success :function (data) {
                    if (id) {
                        alert('修改成功');
                    } else {
                        alert('创建成功')
                    }
                    window.location.href='/resume/list';
                }
            });
        }

    </script>
</head>
<body>

    <table>
        <tbody>
        <tr>
            <th>姓名：</th>
            <td>
                <input id="name" value="${resume.name}" type="text" />
            </td>
        </tr>
        <tr>
            <th>住址：</th>
            <td>
                <input id="address" value="${resume.address}" type="text" />
            </td>
        </tr>
        <tr>
            <th>手机：</th>
            <td>
                <input id="phone" value="${resume.phone}" type="text" />
            </td>
        </tr>
        <tr>
            <th></th>
            <td>
                <input id="submit" value="提交" type="button" onclick="create()" />
            </td>
        </tr>
        </tbody>
    </table>


</body>
</html>
