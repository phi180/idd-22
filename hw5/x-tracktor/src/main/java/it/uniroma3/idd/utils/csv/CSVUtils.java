package it.uniroma3.idd.utils.csv;

import it.uniroma3.idd.utils.Constants;
import it.uniroma3.idd.vo.ExtractedLabeledDataVO;
import it.uniroma3.idd.vo.RowVO;
import it.uniroma3.idd.vo.TableVO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CSVUtils {

    private static final String FOLDER_PATH = "./target/output/";
    private static final String SEPARATOR = ",";
    private static final String NEW_LINE = "\n";
    private static final String CSV_EXTENSION = ".csv";

    public static void appendToDataset(ExtractedLabeledDataVO extractedLabeledDataVO) {
        String datasetName = System.getProperty(Constants.DATASET_PROPERTY);
        if(datasetName==null || datasetName.isBlank()) {
            appendToDataset(extractedLabeledDataVO, Constants.DEFAULT_DATASET);
        } else {
            appendToDataset(extractedLabeledDataVO, datasetName);
        }
    }

    public static void appendToDataset(TableVO tableVO) {
        String datasetName = System.getProperty(Constants.DATASET_PROPERTY);
        if(datasetName==null || datasetName.isBlank()) {
            datasetName = Constants.DEFAULT_DATASET;
        }

        String filePath = FOLDER_PATH + datasetName + CSV_EXTENSION;
        File outputFile = new File(filePath);

        try {
            if(!tableVO.getHeaderRow().getCells().isEmpty())
                FileUtils.writeStringToFile(outputFile, generateLine(tableVO.getHeaderRow().getCells()) + NEW_LINE,"UTF-8", true);

            for(RowVO rowVO : tableVO.getRows()) {
                FileUtils.writeStringToFile(outputFile, generateLine(rowVO.getCells()) + NEW_LINE, "UTF-8", true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /** private methods */
    private static void appendToDataset(ExtractedLabeledDataVO extractedLabeledDataVO, String datasetName) {
        String filePath = FOLDER_PATH + datasetName + CSV_EXTENSION;
        File outputFile = new File(filePath);

        Map<String,String> label2data = new HashMap<>();
        try {
            for(Map.Entry<String,Map<String,String>> label2xpathData : extractedLabeledDataVO.getLabel2xpathData().entrySet()) {
                String label = label2xpathData.getKey();
                String data = "";
                for(Map.Entry<String,String> xpath2data:label2xpathData.getValue().entrySet()) {
                    data = xpath2data.getValue();
                }
                label2data.put(label,data);
            }
            if(!outputFile.exists()) {
                FileUtils.writeStringToFile(outputFile, generateLine(label2data.keySet()) + NEW_LINE,"UTF-8", true);
            }
            FileUtils.writeStringToFile(outputFile, generateLine(label2data.values()) + NEW_LINE,"UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateLine(Collection<String> cells) {
        StringBuilder sb = new StringBuilder();
        for(String cell:cells) {
            sb.append("\"").append(cell).append("\"").append(SEPARATOR);
        }
        String result = sb.toString();

        return result.substring(0,result.length()-1);
    }

}
