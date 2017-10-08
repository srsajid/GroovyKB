package com.tutorialspoint;

import org.codehaus.groovy.grails.io.support.ClassPathResource;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockData {

    final CellProcessor[] processors = new CellProcessor[]{
            new UniqueHashCode(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
            new NotNull(),
    };

    public List<Map> getDataList() throws Exception {
        ICsvMapReader mapReader = null;
        List<Map> maps = new ArrayList<>();
        try {
            ClassPathResource resource = new ClassPathResource("mock-data.csv");
            mapReader = new CsvMapReader(new FileReader(resource.getFile()), CsvPreference.STANDARD_PREFERENCE);
            final String[] header = mapReader.getHeader(true);

            Map<String, Object> customerMap;
            while( (customerMap = mapReader.read(header, processors)) != null ) {
                maps.add(customerMap);
            }

        }
        finally {
            if( mapReader != null ) {
                mapReader.close();
            }
        }
        return maps;
    }

    public static void main(String[] args) throws Exception {
        List list = new MockData().getDataList();
        System.out.print("break");
    }
}
