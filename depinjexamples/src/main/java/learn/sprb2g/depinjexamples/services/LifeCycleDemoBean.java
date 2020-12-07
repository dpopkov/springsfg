package learn.sprb2g.depinjexamples.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifeCycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware,
        BeanFactoryAware, ApplicationContextAware {

    public LifeCycleDemoBean() {
        System.out.println("I am in the LifeCycleDemoBean constructor");
    }

    /* InitializingBean method */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("## afterPropertiesSet(): The LifeCycleBean has its properties set!");
    }

    /* DisposableBean method */
    @Override
    public void destroy() throws Exception {
        System.out.println("## destroy(): The Lifecycle bean has been terminated");
    }

    /* BeanNameAware method */
    @Override
    public void setBeanName(String name) {
        System.out.println("## setBeanName(String): My Bean Name is: " + name);
    }

    /* BeanFactoryAware method */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("## setBeanFactory(BeanFactory): Bean Factory has been set");
    }

    /* ApplicationContextAware method */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("## setApplicationContext(ApplicationContext): Application context has been set");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("## The Post Construct annotated method has been called");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("## The PreDestroy annotated method has been called");
    }

    public void beforeInit(){
        System.out.println("## - Before Init - Called by Bean Post Processor");
    }

    public void afterInit(){
        System.out.println("## - After init called by Bean Post Processor");
    }
}
