package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public Calendar parseCalendarFromJsonFile(File file){
        if(file == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        try{
            return mapper.readValue(file, Calendar.class);
        }catch (IOException exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }
}