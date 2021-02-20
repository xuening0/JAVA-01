package spring02;


import interfaceTest.ISchool;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class SchoolAnnotation implements ISchool {
    
   /* @Autowired //primary
    Klass class1;
    
    @Resource(name = "student123")
    Student student123;*/
    
    @Override
    public void ding(){
        System.out.println("SchoolAnnotation ding()...");
    }
    
}
