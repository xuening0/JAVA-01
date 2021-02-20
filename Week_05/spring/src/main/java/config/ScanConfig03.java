package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = "spring03")
//@Configuration
@EnableAspectJAutoProxy
public class ScanConfig03 {
}