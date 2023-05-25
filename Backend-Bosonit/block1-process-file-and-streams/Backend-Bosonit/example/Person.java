package example;

public class Person {
    private String name;
    private String town;
    private int age;

    // Constructor
    public Person(String name, String town, int age){
        this.name = name;
        this.town = town;
        this.age = age;
    }

    // Métodos getter y setter name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Métodos getter y setter town
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    // Métodos getter y setter age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString(){
        return "Person{" +
                "name='" + name + '\'' +
                ", town='" + town + '\'' +
                ", age=" + age +
                '}';

    }
}
