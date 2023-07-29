package example.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JWT implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        req.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        // 判断请求路径是否是 /login，如果是，则直接放行
        if ("/login".equals(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        // 判断请求路径是否是 /register，如果是，则直接放行
        if ("/register".equals(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        // 判断请求路径是否是 /login，如果是，则直接放行
        if ("/static".equals(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        // 判断请求路径是否是 /video，如果是，则直接放行
        if ("/video".equals(request.getServletPath())) {
            chain.doFilter(request, response);
            return;
        }
        // 判断请求方法是否是OPTIONS，如果是，则直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // 从请求头中获取 Token
        String token = request.getHeader("Authorization");


        // 如果 Token 为空或无效，则返回未登录的错误响应
        if (token == null || !JwtUtils.validateJwt(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 构建响应 JSON
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "no login");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(responseJson);
            out.flush();
            return;
        }
        System.out.println("test-----"+JwtUtils.validateJwt(token));
        // Token 验证通过，继续处理请求
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化方法，可留空
    }

    @Override
    public void destroy() {
        // 销毁方法，可留空
    }
}
