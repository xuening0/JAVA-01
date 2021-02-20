package service.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.test")
public class TestServiceProperties {

    private String name = "test";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}