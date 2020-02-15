package com.tensquare.encrypt.filter;

import com.google.common.base.Charsets;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.apache.http.protocol.RequestContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author haixin
 * @time 2020/2/15
 */
@Component
public class RSARequestFilter extends ZuulFilter {

    @Autowired
    private RsaService rsaService;

    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 1;
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        try {
            //1.获得request
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletRequest request = requestContext.getRequest();
            //2.获得请求参数(流的方式)
            ServletInputStream is = request.getInputStream();
            String params = StreamUtils.copyToString(is, Charsets.UTF_8);
            System.out.println("加密的请求参数:" + params);
            //3.进行解密
            if (!StringUtils.isEmpty(params)) {

                params = rsaService.rsaDecryptDataPEM(params, RsaKeys.getServerPrvKeyPkcs8());
                System.out.println("解密的请求参数:" + params);

                //4.转发到微服务
                if(!StringUtils.isEmpty(params)){
                    byte[] bytes = params.getBytes();
                    requestContext.setRequest(new HttpServletRequestWrapper(request){
                        @Override
                        public ServletInputStream getInputStream() throws IOException {
                            return new ServletInputStreamWrapper(bytes);
                        }

                        @Override
                        public int getContentLength() {
                            return bytes.length;
                        }

                        @Override
                        public long getContentLengthLong() {
                            return bytes.length;
                        }
                    });

                }
                //5.设置request请求头中的Content-Type为application/json，否则api接口模块需要进行url转码操作
                requestContext.addZuulRequestHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON) + ";charset=UTF-8");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
