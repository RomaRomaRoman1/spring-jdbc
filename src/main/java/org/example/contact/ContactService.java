package org.example.contact;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    public List<Contact> readContactsFromCsv(String resourcePath) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(resourcePath);

        try (Reader reader = new InputStreamReader(resource.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                String name = record.get(0).split(" ")[0];
                String surname = record.get(0).split(" ")[1];
                String phone = record.get(1);
                String email = record.get(2);
                contacts.add(new Contact(name, surname, email, phone));
            }
        }
        return contacts;
    }
}
