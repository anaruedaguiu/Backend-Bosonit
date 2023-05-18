package example;

import java.io.IOException;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
    public static List<Person> readCSVFile() throws IOException, InvalidLineFormatException {
        List<Person> PersonList = new ArrayList<>(); //Creo arraylist vacío
        //example.Person person = new example.Person(); //Creo la persona (objeto)
        Path path = Paths.get("resources/people.csv"); //Le paso lo que quiero que lea

        try {
            BufferedReader reader = Files.newBufferedReader(path); //Creo un lector
            String line = reader.readLine(); //Le digo que lea
            //String[] parts = line.split(":"); //Hago split - how to split a string - usando delimitador ":"

            while (line != null) {
                //Se crea el bloque try-catch dentro del while para manejar la excepción sin dejar de ejecutar el código
                String[] parts = line.split(":"); //Hago split - how to split a string - usando delimitador ":"
                try {
                    if (parts[0].isBlank()) { //Si el nombre es nulo
                        throw new InvalidLineFormatException("Invalid line format " + line + ". Name field is required");
                    }

                    //Cuento el nº de delimitadores que tiene que haber
                    int delimiterCount = line.length() - line.replace(":", "").length();

                    if (delimiterCount == 0 || delimiterCount == 1) { //Si faltan delimitadores de campo
                        throw new InvalidLineFormatException("Invalid line format " + line + ". Missing field delimiters");
                    }

                    Person person = new Person("", "", 0); //Creo la persona (objeto)

                    //Name
                    String name = parts[0].trim();
                    person.setName(name); //Asigno el nombre

                    //Town
                    String town = parts[1].isBlank() ? "unknown" : parts[1]; //Cuando este campo está vacío tiene que aparecer como unknown
                    person.setTown(town); //Asigno la ciudad

                    //Age
                    int age = parts.length <= 2 || parts[2].isBlank() ? 0 : Integer.parseInt(parts[2]);
                    person.setAge(age);

                    PersonList.add(person); //Añadir a la persona al array

                } catch (InvalidLineFormatException e) {
                    System.out.println(e.getMessage());
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PersonList;
    }

    public static void main(String[] args) throws IOException, InvalidLineFormatException {

        List <Person> PersonList = readCSVFile(); // Variable PersonList - lista de objetos con las personas que lee del archivo csv
        Stream<Person> streamWithA = PersonList.stream(); // Convierto la lista a stream

        // Expresion para validar que el nombre empieza por A
        List<Person> PersonListWithA = streamWithA.filter(p -> p.getName().startsWith("A") || p.getName().startsWith("Á")).toList();
        System.out.println("*** List of people whose name starts with A ***");
        PersonListWithA.forEach(p-> System.out.println(p));

        System.out.println("-----------------------------------------------");

        // Expresion para validar si es menor de 25 años
        Stream<Person> streamUnder25 = PersonList.stream();
        List<Person> PersonListUnder25 = streamUnder25.filter(p -> p.getAge()>0 && p.getAge()<25).toList();
        System.out.println("*** List of people whose age is under 25 years old ***");
        PersonListUnder25.forEach(p -> System.out.println(p));

        System.out.println("-----------------------------------------------");

        // Expresion para validar < 25 años + Madrid
        Optional<Person> townMadrid = PersonListUnder25.stream()
                .filter(p -> p.getTown().equals("Madrid"))
                .findFirst();
        if(!townMadrid.isEmpty()){
            System.out.println("*** Person under 25 years from Madrid ***");
            System.out.println(townMadrid.get().toString());
        } else {
            System.out.println("*** There's not a person under 25 years from Madrid ***");
        }

        System.out.println("-----------------------------------------------");

        // Expresion para validar < 25 años + Barcelona
        Optional<Person> townBcn = PersonListUnder25.stream()
                .filter(p -> p.getTown().equals("Barcelona"))
                .findFirst();
        if(!townBcn.isEmpty()) {
            System.out.println("*** Person under 25 years from Barcelona ***");
            System.out.println(townBcn.get().toString());
        } else {
            System.out.println("*** There's not a person under 25 years from Barcelona ***");
        }
    }
}
