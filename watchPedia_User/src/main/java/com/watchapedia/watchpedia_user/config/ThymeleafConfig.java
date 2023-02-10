package com.watchapedia.watchpedia_user.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

//타임리프 th.xml 사용하기위해 타임리프 설정을 함

//@Configuration은 스프링 IOC Container에게 해당 클래스를 Bean구성 Class임을 알려주는 것
//@Bean과 @Component 둘다 Spring(IOC) Container에 Bean을 등록하도록 하는 메타데이터를 기입하는 어노테이션

//@Bean의 경우 개발자가 직접 제어가 불가능한 외부 라이브러리등을 Bean으로 만들려할때 사용
//아래와 같이 SpringResourceTemplateResolver와 같은 라이브러리를 Bean으로 등록하기 위해서는
//별도로 해당라이브러리 객체를 반환하는 메소드를 만들고 @Bean을 붙여주면 된다
//아래의 경우 Bean안에 아무런 값을 지정하지않았으므로 메소드이름을 카멜형식으로 변경한것이 Bean id로 등록
//springResourceTemplateResolver가 Bean id가 된다
//@Bean(name="abc") 이렇게 하면 abc가 Bean의 id가 된다

//@Component같은 경우 개발자가 직접 작성한 Class를 Bean으로 등록학 위한 어노테이션이다
//@Bean과 같이 아무런 추가 정보가 없으면 Class의 이름을 Camelcase(카멜표기법)으로 변경한 것이 Bean id로 사용된다
//하지만 @Bean과 다르게 @Component는 name이 아닌 value를 이용해 Bean의 이름을 지정한다.

//@Component를 사용한 Bean의 의존성 주입은 @AutoWired를 이용하여 할수있다
//@Autowired를 쓴 자리에 들어올 수 있는 객체가 다형성으로인해 여러개 올 수 있다면
//@Qualifier("Bean이름")을 이용하여 해당 자리에 주입될 Bean을 명시해주어야한다


@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultTemplateResolver;
    }

    @RequiredArgsConstructor
    @Getter
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {
        /**
         * Use Thymeleaf 3 Decoupled Logic
         */
        private final boolean decoupledLogic;
    }
}
