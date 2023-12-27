package com.mcommerce.zuulserver.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;

@Component
public class MyZuulLogFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() { return 1; }
    @Override
    public boolean shouldFilter() { return true; }
    @Override
    public Object run() throws ZuulException {
        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();
        log.info("**** MyZuulLogFilter : Requête interceptée ! L'URL est : {} " , req.getRequestURL());
        return null;
    }
    }

