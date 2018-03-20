package com.endava.rpg.persistence.services.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CSVReader {
    private Logger LOGGER = LoggerFactory.getLogger(CSVReader.class);

    private String fileName;

    private String dataSplitter = ";";

    private String path = "csv/";

    public CSVReader(String fileName) {
        this.fileName = fileName;
    }

    public List<String[]> getData() {
        String line;
        boolean first = true;
        List<String[]> result = new ArrayList<>();

        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(
                            CSVReader.class.getClassLoader().getResourceAsStream(path + fileName)));

            while ((line = br.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }

                String[] rawData = line.trim().split(dataSplitter);

                String[] finalArray = Arrays.stream(rawData).map(String::trim).toArray(unused -> rawData);

                result.add(finalArray);
            }
        } catch (IOException io) {
            LOGGER.error("Error -> {}", io);
        }
        return result;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDataSplitter(String dataSplitter) {
        this.dataSplitter = dataSplitter;
    }
}
