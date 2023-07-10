package com.tukorea.cogTest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tukorea.cogTest.controller.AdminController;
import com.tukorea.cogTest.controller.FieldController;
import com.tukorea.cogTest.controller.SubjectController;
import com.tukorea.cogTest.controller.SuperAdminController;
import com.tukorea.cogTest.domain.AdminRepository;
import com.tukorea.cogTest.domain.FieldRepository;
import com.tukorea.cogTest.domain.SubjectRepository;
import com.tukorea.cogTest.domain.TestResultRepository;
import com.tukorea.cogTest.logging.logtrace.LogTrace.LogTrace;
import com.tukorea.cogTest.logging.proxy.MessageInterceptor;
import com.tukorea.cogTest.repository.*;
import com.tukorea.cogTest.service.*;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@ComponentScan
public class WebConfig {

    @Bean
    AdminRepository adminRepository(){
        AdminRepository repositoryImpl = new AdminRepositoryImpl(jpaAdminRepository);
        ProxyFactory proxyFactory = new ProxyFactory(repositoryImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        AdminRepository proxy = (AdminRepository) proxyFactory.getProxy();
        return proxy;
    }
    @Bean
    FieldRepository fieldRepository(){
        FieldRepository repositoryImpl = new FieldRepositoryImpl(
                jpaSimpleFieldRepository
        );
        ProxyFactory proxyFactory = new ProxyFactory(repositoryImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        return (FieldRepository) proxyFactory.getProxy();
    }

    @Bean
    SubjectRepository subjectRepository(){
        SubjectRepository repositoryImpl = new SubjectRepositoryImpl(jpaSubjectRepository);
        ProxyFactory proxyFactory = new ProxyFactory(repositoryImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        return (SubjectRepository) proxyFactory.getProxy();
    }

    @Bean
    TestResultRepository testResultRepository(){
        TestResultRepository repositoryImpl = new TestResultRepositoryImpl(
                jpaTestResultRepository,
                jpaSubjectRepository);
        ProxyFactory proxyFactory = new ProxyFactory(repositoryImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        return (TestResultRepository) proxyFactory.getProxy();
    }
    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
    @Bean
    LogTrace logTrace(){
        return new LogTrace();
    }

    @Bean
    AdminService adminService(){
        AdminService serviceImpl = new AdminServiceImpl(
                adminRepository(),
                encoder,
                fieldRepository(),
                subjectRepository()
                );
        ProxyFactory proxyFactory = new ProxyFactory(serviceImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        AdminService proxy = (AdminService) proxyFactory.getProxy();
        return proxy;
    }
    @Bean
    FieldService fieldService(){
        FieldService serviceImpl = new FieldService(
                fieldRepository(),
                adminService(),
                subjectService()
        );
        ProxyFactory proxyFactory = new ProxyFactory(serviceImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        return (FieldService) proxyFactory.getProxy();
    }
    @Bean
    SubjectService subjectService(){
        SubjectServiceImpl serviceImpl = new SubjectServiceImpl(
                subjectRepository(),
                testResultRepository(),
                fieldRepository()
        );
        ProxyFactory proxyFactory = new ProxyFactory(serviceImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        return (SubjectService) proxyFactory.getProxy();
    }
    @Bean
    TestResultService testResultService(){
        TestResultService serviceImpl = new TestResultService(
                testResultRepository(),
                subjectRepository()
        );
        ProxyFactory proxyFactory = new ProxyFactory(serviceImpl);
        proxyFactory.addAdvice(new MessageInterceptor(logTrace()));
        return (TestResultService) proxyFactory.getProxy();
    }

    @Bean
    LogoutRepository logoutRepository(){
        return new LogoutRepositoryImpl();
    }
    @Autowired
    JpaSimpleAdminRepository jpaAdminRepository;
    @Autowired
    JpaSimpleSubjectRepository jpaSubjectRepository;
    @Autowired
    JpaSimpleTestResultRepository jpaTestResultRepository;
    @Autowired
    JpaSimpleFieldRepository jpaSimpleFieldRepository;
    @Autowired
    @Lazy
    PasswordEncoder encoder;


}
