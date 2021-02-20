package service.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student2 {
    private Long id;
    private String status;
}
