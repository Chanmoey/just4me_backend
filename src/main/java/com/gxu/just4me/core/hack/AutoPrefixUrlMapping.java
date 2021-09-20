package com.gxu.just4me.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author Chanmoey
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {

    @Value("${just4me.api-package}")
    private String apiPackagePath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {

        // mappingInfo记录了我们在@RequestMapping注解上配置的路由信息。
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if (mappingInfo != null) {
            String prefix = this.getPrefix(handlerType);

            // 将前缀"/v1/"和之前的路由信息合并，生成新的路由
            return RequestMappingInfo.paths(prefix).build().combine(mappingInfo);
        }
        return null;
    }

    private String getPrefix(Class<?> handlerType) {
        String packageName = handlerType.getPackage().getName();

        String dotPath = packageName.replaceAll(this.apiPackagePath, "");
        return dotPath.replace(".", "/") + "/";
    }
}
