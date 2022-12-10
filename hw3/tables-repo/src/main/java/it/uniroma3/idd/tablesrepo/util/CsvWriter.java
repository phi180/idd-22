package it.uniroma3.idd.tablesrepo.util;

import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.RowVO;
import it.uniroma3.idd.entity.TableByRowVO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CsvWriter {

    private static final String OUTPUT_FOLDER = "./target/csv/";
    private static final String CSV_EXT = ".csv";

    private static final String COMMA = ",";
    private static final String QUOTE = "\"";

    public static void writeTableToCsv(TableByRowVO table) {
        File file = new File(OUTPUT_FOLDER + table.getOid() + CSV_EXT);
        for(RowVO rowVO : table.getRows().values()) {
            appendRow(file,rowVO);
        }
    }

    /** private methods */

    private static void appendRow(File file, RowVO rowVO) {
        StringBuilder rowCsvBuilder = new StringBuilder();
        for(CellVO cell : rowVO.getCells().values()) {
            rowCsvBuilder.append(QUOTE).append(cell.getContent()).append(QUOTE).append(COMMA);
        }
        String row = rowCsvBuilder.substring(0,rowCsvBuilder.length()-1);
        try {
            FileUtils.writeStringToFile(file, row +"\n", "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
