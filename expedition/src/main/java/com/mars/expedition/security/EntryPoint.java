//package com.mars.expedition.security;
//
//import jakarta.servlet.ServletException;
//import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
//
//public class EntryPoint extends BasicAuthenticationEntryPoint {
//
//    @Override
//    public void commence(
//            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
//            throws IOException, ServletException {
//        response.addHeader("WWW-Authenticate", "Basic realm="" + getRealmName() + """);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = response.getWriter();
//        writer.println("HTTP Status 401 - " + authEx.getMessage());
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        setRealmName("Baeldung");
//        super.afterPropertiesSet();
//    }}
//
