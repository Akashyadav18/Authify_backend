package com.Security.Authify.custom;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class CustomGenerator {

    public String generateUniqueId(){
        int random = (int) (Math.random() * 90000) + 10000;
        return "PIS" + random;
    }

    public String generateUniqueName(String firstName, String rollno){
        String name = firstName
                .toLowerCase()
                .replace("\\s+", "") //remove spaces
                .replaceAll("[^a-z]", "");

        String currentYear = String.valueOf(LocalDate.now().getYear());
        String rollNo =  String.format("%03d", Integer.parseInt(rollno));

        return name + currentYear + rollNo;
    }
}
