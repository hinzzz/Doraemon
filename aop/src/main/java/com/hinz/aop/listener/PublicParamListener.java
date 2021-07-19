package com.hinz.aop.listener;




import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * 负责初始化以及销毁请求url公参对象
 *
 * @author yudong
 * @date 2021/6/2
 */
public class PublicParamListener implements ServletRequestListener {

    /*@Override
    public void requestInitialized(ServletRequestEvent sre) {
        PublicParamAttributes attributes = PublicParamAttributes.initAttributes((HttpServletRequest) sre.getServletRequest());
        PublicParamHolder.setRequestAttributes(attributes);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        PublicParamHolder.resetRequestAttributes();
    }*/

}
