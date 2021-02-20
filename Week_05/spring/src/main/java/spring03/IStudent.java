package spring03;

import aop.Mart;
import aop.Student;
import org.springframework.stereotype.Service;

@Service
@Mart
public class IStudent implements Student {

    @Override
    @Mart
    public void read() {
        System.out.println("student read...");
    }
}
