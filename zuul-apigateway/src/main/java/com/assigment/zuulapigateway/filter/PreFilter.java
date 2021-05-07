package com.assigment.zuulapigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class PreFilter extends ZuulFilter {
 Logger log = LoggerFactory.getLogger(PreFilter.class);
 @Override
 public Object run() {
  RequestContext ctx = RequestContext.getCurrentContext();  
  StringBuffer strLog = new StringBuffer();
  strLog.append("\n------ API GATEWAY REQUEST ------\n");
  strLog.append(String.format("Server: %s Metodo: %s Path: %s \n", ctx.getRequest().getServerName(), ctx.getRequest().getMethod(),
   ctx.getRequest().getRequestURI()));
  Enumeration < String > enume = ctx.getRequest().getHeaderNames();
  String header;
  while (enume.hasMoreElements()) {
   header = enume.nextElement();
   strLog.append(String.format("Headers: %s = %s \n", header, ctx.getRequest().getHeader(header)));
  };
  log.info(strLog.toString());
  return null;
 }

 @Override
 public boolean shouldFilter() {
  return true;
 }

 @Override
 public int filterOrder() {
  return FilterConstants.SEND_RESPONSE_FILTER_ORDER;
 }

 @Override
 public String filterType() {
  return "pre";
 }

}