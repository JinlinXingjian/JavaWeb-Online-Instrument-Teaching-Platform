# Java Web，在线乐器学习平台

前后端分离
- [x] 前端：vue3,vite,pinia
- [x] 后端：原生servlet，mybaties，数据库MySQL



## 实现的功能

l 用户账户：包括注册、登录、个人信息管理，以及密码找回功能。

l 课程浏览：能够按照乐器种类、难度级别、教师等条件筛选和搜索课程。

l 课程学习：查看所选课程的教学视频，可以随时暂停、快进和后退，还能查看视频下方的文字说明和相关资料。

l 课程评价：可以对课程内容和教师进行评价，还能查看其他学生的评价。



l 用户管理：查看和管理学生的用户信息，可以封禁违规用户。

l 课程管理：更改所有课程的信息。



## 学生模块

### 登录及功能演示

![image-20230721192927861](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721192927861.png)

![image-20230721192950192](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721192950192.png)



![image-20230721193008472](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193008472.png)

![image-20230721193022753](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193022753.png)

![image-20230721193040614](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193040614.png)

![image-20230721193053257](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193053257.png)

![image-20230721193101394](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193101394.png)

![image-20230721193115847](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193115847.png)

![image-20230721193143871](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193143871.png)

![image-20230721193157150](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193157150.png)

![image-20230721193205188](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193205188.png)

### 注册

![image-20230721193301071](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193301071.png)

相关代码如下

```java
@WebServlet(value = "/login")
public class UserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 password 字段值
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        try {
            // 调用 UserMapper 的方法进行验证
            boolean isValidLogin = userDao.validateLogin(username, password);
            // 构建响应 JSON
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

            if (isValidLogin) {
                // 登录成功
                User userObject = userDao.getUserByUsername(username);
                JSONObject userJson = new JSONObject();
                userJson.put("username", userObject.getUsername());
                userJson.put("Email", userObject.getEmail());
                userJson.put("avatar", userObject.getAvatar());
                userJson.put("status", userObject.getStatus());
                userJson.put("role", userObject.getRole());
                if (userObject.getUsername() == null || userObject.getUsername() == "") {
                    return;
                }
                // 生成 JWT Token
                String token = JwtUtils.generateJwt(userObject.getUsername(), 3600000L);
                System.out.println("login-g-token:" + userObject.getUsername() + "---" + token);
                //设置响应json的内容
                responseBuilder.add("message", "login success");
                responseBuilder.add("token", token);
                responseBuilder.add("user", userJson.toJSONString());
                JsonObject responseJson = responseBuilder.build();

                // 设置响应内容为 JSON
                resp.setContentType("application/json;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(responseJson);
                out.flush();
            } else {
                // 登录失败
                //设置响应json的内容
                responseBuilder.add("message", "login fail");
                JsonObject responseJson = responseBuilder.build();

                // 设置响应内容为 JSON
                resp.setContentType("application/json;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(responseJson);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
```

### 找回密码

![image-20230721193316724](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193316724.png)

相关代码如下

```java
@WebServlet("/FindPassword")
public class UserFindPassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        // 获取注册表单参数
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 Verification_code 和 newPassword 字段值
        String Email = jsonObject.getString("Email");


        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        User user=userDao.getUserByEmail(Email);
        //校验邮箱的有效性
        if(user!=null){
            String recipientEmail = Email;
            String subject = "您的验证码为";
            // 生成临时验证码，并存储到内存中,然后发送
            String code= VerificationCodeGenerator.generateNumericCode(6);
            MemoryCodeStorage.saveCode(Email,code);
            EmailSender.sendEmail(recipientEmail,subject,code);

            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "sentCode success");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(responseJson);
            out.flush();
        }
        else {
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "username error");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(responseJson);
            out.flush();
        }
    }
}
```



## 管理员模块

### 用户管理模块

![image-20230721193405803](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193405803.png)

### 课程管理模块

![image-20230721193450018](https://typora-1305229676.cos.ap-chengdu.myqcloud.com//image-20230721193450018.png)



---





**更多代码请查看源代码**
