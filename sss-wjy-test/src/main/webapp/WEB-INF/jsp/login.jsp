<%--
  Created by IntelliJ IDEA.
  User: 继洋
  Date: 2020/4/13
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>

    <script src="/js/jquery.min.js" type="text/javascript"></script>

    <script>

    </script>
    <script>
        $(function () {
            // $('#loginBtn').bind('click', function (e) {
            //     $.ajax({
            //         url : '/login/handleLogin',
            //         data : {
            //             username : $('#username').val(),
            //             password : $('#password').val()
            //         },
            //         type : 'POST',
            //         dataType : 'json',
            //         success :function (data) {
            //             if (data === 'succ') {
            //                 alert('登陆成功');
            //                 window.href.url = '/resume/list';
            //             } else {
            //                 alert('用户名或密码错误！');
            //             }
            //         }
            //     });
            // });
        });

        function login() {
            $.ajax({
                url : '/login/handleLogin',
                data : {
                    username : $('#username').val(),
                    password : $('#password').val()
                },
                type : 'POST',
                dataType : 'json',
                success :function (data) {
                    console.log(data);
                    if (data === null) {
                        alert('用户名或密码错误！');
                    } else {
                        alert(data.username + '登陆成功');
                        window.location.href = '/resume/list';
                    }
                }
            });
        }
    </script>
</head>
<body>
${errorMsg}

<form>
    <label>用户名：</label>
    <label>
        <input type="text" name="username" value="admin" id="username" />
    </label>
    <label>密码：</label>
    <label>
        <input type="password" name="password" id="password" />
    </label>
    <input id="loginBtn" type="button" value="登录" onclick="login()" />
</form>

</body>
</html>
