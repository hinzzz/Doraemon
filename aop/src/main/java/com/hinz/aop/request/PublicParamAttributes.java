package com.hinz.aop.request;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求url公参
 *
 * @author yudong
 * @date 2021/6/2
 */
@Getter
@ToString
public class PublicParamAttributes {
    private Integer appType;
    private String appTypeStr;
    private String client;
    private String clientKey;
    private String clientValue;
    private String clientType;
    private String cuid;
    private String version;

    public static PublicParamAttributes initAttributes(HttpServletRequest request) {
        PublicParamAttributes attributes = new PublicParamAttributes();
        attributes.appTypeStr = request.getParameter("appType");
        if (attributes.appTypeStr != null) {
            if (StringUtils.isNumeric(attributes.appTypeStr)) {
                attributes.appType = Integer.parseInt(attributes.appTypeStr);
            }
        }
        attributes.client = request.getParameter("client");
        attributes.clientKey = request.getParameter("clientKey");
        attributes.clientValue = request.getParameter("clientValue");
        attributes.clientType = request.getParameter("clientType");
        attributes.cuid = request.getParameter("cuid");
        attributes.version = request.getParameter("version");
        return attributes;
    }

}
