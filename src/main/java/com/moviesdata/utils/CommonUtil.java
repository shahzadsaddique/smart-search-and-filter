package com.moviesdata.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class CommonUtil {

    public static  List<CSVMovieData> parseCSVFile(MultipartFile csvFile) throws IOException {

     Reader reader = new BufferedReader(
                new InputStreamReader(csvFile.getInputStream()));
        CsvToBean<CSVMovieData> csvToBean = new CsvToBeanBuilder(reader)
                .withType(CSVMovieData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .build();
        List<CSVMovieData> csvMovieDataList = csvToBean.parse();

        return csvMovieDataList;
    }


}

