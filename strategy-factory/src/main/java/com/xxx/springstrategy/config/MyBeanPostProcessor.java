package com.xxx.springstrategy.config;

import com.xxx.springstrategy.Factory;
import com.xxx.springstrategy.Handler;
import com.xxx.springstrategy.StrategyAnno;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @Desciption: bean初始化 通过注解 把对应业务逻辑的策略 根据value 注册到工厂里去
 * @author ZhangXueCheng4441
 * @Date:2020/12/13/013 10:27
 */
@Configuration
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 初始化的时候 搜索脑袋上带StrategyAnno注解的 把注解里面的值作为key 内容作为value 注册进去
     *
     * @param bean the new bean instance
     * @param beanName the name of the bean
     * @return Object
     * @throws BeansException BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        StrategyAnno annotation = bean.getClass().getAnnotation(StrategyAnno.class);
        if (annotation != null && bean instanceof Handler){
            Factory.register(annotation.value(), (Handler) bean);
        }
        return bean;
    }

}
