package com.endava.rpg.persistence.services.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVReader {

    private String fileNameFromResources;
    private String dataSplitter = ",";
    private String resourcePath = "C:\\Users\\arailean\\IdeaProjects\\rpg\\rpg-persistence\\src\\main\\resources\\csv\\";

    /**
     * @param fileName name of file from resources
     */
    public CSVReader(String fileName) {
        this.fileNameFromResources = fileName;
    }

    /**
     * @param fileName     name of file from resources
     * @param dataSplitter - if you need different data splitter (not ",")
     */
    public CSVReader(String fileName, String dataSplitter) {
        this.fileNameFromResources = fileName;
        this.dataSplitter = dataSplitter;
    }

    /**
     * private method for extracting data from *.csv
     *
     * @return List of rows from *.csv as array
     */
    private List<String[]> getData() {
        String line;
        int headerSkipper = 0;
        List<String[]> result = new ArrayList<>();

        try {
            BufferedReader br =
                    new BufferedReader(new FileReader(resourcePath +
                            fileNameFromResources));

            while ((line = br.readLine()) != null) {
                if (headerSkipper == 0) {
                    headerSkipper++;
                    continue;
                }
                // "_" this symbol is separator of text in *.csv files
                result.add(line.replaceAll("\\s", "").replaceAll("_", " ").split(dataSplitter));
            }
        } catch (IOException iO) {
            iO.printStackTrace();
        }
        return result;
    }

    /**
     * @return List of rows from *.csv as array
     */
    public List<String[]> getAsListOfArrays() {
        return getData();
    }

    /**
     * @return Map where key is first column from *.csv and List of rows from *.csv as array
     */
    public Map<String, List<String>> getMapFirstColumnAsKeys() {
        Map<String, List<String>> result = new HashMap<>();

        getData()
                .forEach(extraction -> {
                    result.put(extraction[0],
                            Arrays.asList(extraction).subList(1, extraction.length));
                });

        return result;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

}
