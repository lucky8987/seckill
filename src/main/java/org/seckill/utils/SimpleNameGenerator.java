package org.seckill.utils;

import java.beans.Introspector;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.util.ClassUtils;

/**
 * 自定义spring bean 的命名策略<br/>
 * 通过@ComponentScan(nameGenerator = SimpleNameGenerator.class)启用
 */
public class SimpleNameGenerator implements BeanNameGenerator {

    /**
     * 默认 simpleName 驼峰
     * @param definition
     * @param registry
     * @return
     */
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
