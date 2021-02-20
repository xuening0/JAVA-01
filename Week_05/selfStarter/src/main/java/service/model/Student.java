package service.model;


import java.io.Serializable;

public class Student implements Serializable {
    
    private int id;
    private String name;
    
    public void init(){
        System.out.println("hello...........");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
