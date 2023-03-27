package com.test.Insomnia;

import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.test.Insomnia.model.Product;

@Component
public class MyTest implements HandlerInterceptor{
	
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在拦截器中使用常量
		Set<Entry<Integer, String>> entrySet = Product.CATEGORY_OPTIONS.entrySet();
        request.setAttribute("entrySet", entrySet);
        return true;
        
        
    }

}
