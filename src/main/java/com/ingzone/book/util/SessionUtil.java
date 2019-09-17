package com.ingzone.book.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Created by wjx on 17-7-10.
 */
public class SessionUtil {
    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    public static void setCookies(Cookie ...cookies){
        Arrays.stream(cookies).forEach(getResponse()::addCookie);
    }
    public static void deleteCookies(Cookie ...cookies){
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            if(name.equals("nickName")||name.equals("account")||name.equals("role")){
                cookie.setValue(null);
            }
            cookie.setMaxAge(0);  //销毁
            cookie.setPath("/");
            getResponse().addCookie(cookie);
        }
    }
    public static String user() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute("id") + "";
    }
    public static HttpSession setAttribute(String a,Object b){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        session.setAttribute(a,b);
        return session;
    }
    public static void setTime(int t){
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().setMaxInactiveInterval(t*60);
    }
    public static Object getAttribute(String a){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession().getAttribute(a);
    }
    public static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
    }
    public static boolean testExist(String a,Object b) {
        return b != null && b.equals(getSession().getAttribute(a));
    }
    public static void destroySession(){
        HttpSession session = getSession();
        if(session!=null)
            deleteCookies(getRequest().getCookies());
            session.invalidate();
    }
}
