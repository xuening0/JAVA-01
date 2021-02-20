package service.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.model.Student;

@Configuration
@EnableConfigurationProperties(TestServiceProperties.class)
@ConditionalOnClass(Student.class)
@ConditionalOnProperty(prefix = "com.test", value = "enabled", matchIfMissing = true)
public class TestServiceAutoConfiguration {

    @Autowired
    private TestServiceProperties testServiceProperties;

    @Bean
    @ConditionalOnMissingBean(Student.class)
    public Student testServiceConfiguration() {
        Student student = new Student();
        student.setName(testServiceProperties.getName());
        return student;
    }
}