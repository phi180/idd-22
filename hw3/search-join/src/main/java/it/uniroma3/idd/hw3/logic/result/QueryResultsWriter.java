package it.uniroma3.idd.hw3.logic.result;

import it.uniroma3.idd.entity.ResultVO;
import it.uniroma3.idd.entity.TableVO;
import it.uniroma3.idd.hw3.api.ParseApi;
import it.uniroma3.idd.hw3.api.ParseApiImpl;
import it.uniroma3.idd.hw3.filesystem.DatasetBuffer;
import it.uniroma3.idd.hw3.utils.PropertiesReader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class QueryResultsWriter {
    private static final String RESULTS_DIR = PropertiesReader.getResultsDir();
    private static final Logger logger = Logger.getLogger(QueryResultsWriter.class.toString());

    private Long beginTimestamp;
    private String resultsFullPath;

    public QueryResultsWriter(Long beginTimestamp) {
        this.beginTimestamp = beginTimestamp;
        this.resultsFullPath = RESULTS_DIR + "/" + "res_" + beginTimestamp.toString() + ".txt";
    }

    public void initQueryResultsFile(List<String[]> queryTokens) {
        File file = new File(this.resultsFullPath);
        StringBuilder sb = new StringBuilder();

        logger.info("queryTokens=" + List.of(queryTokens));

        for(String[] queryTokenGroup:queryTokens) {
            sb.append(" | - | ");
            for(String token : queryTokenGroup)
                sb.append(token).append(" ");
        }

        try {
            FileUtils.writeStringToFile(file, "Timestamp: " + beginTimestamp + "\n", "UTF-8", true);
            FileUtils.writeStringToFile(file, "Query tokens: " + sb.toString() + "\n", "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendResult(ResultVO result) {
        File file = new File(this.resultsFullPath);
        try {
            FileUtils.writeStringToFile(file, result.getTableId() + ":" + result.getColumnNum()+"\n", "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendFullResults(List<ResultVO> results, String datasetPath) {
        logger.info("QueryResultsWriter - appendFullResults(): datasetPath="+datasetPath);

        ParseApi parseApi = new ParseApiImpl();

        try {
            DatasetBuffer datasetBuffer = new DatasetBuffer(datasetPath);
            while(!datasetBuffer.isEnded()) {
                String line = datasetBuffer.readNextLine();
                TableVO tableVO = parseApi.parse(line);
                if (tableVO == null)
                    break;

                List<ResultVO> currentTableResults = results.stream().filter(result -> Objects.equals(result.getTableId(), tableVO.getOid())).collect(Collectors.toList());

                if(currentTableResults.stream().map(ResultVO::getTableId).collect(Collectors.toList()).contains(tableVO.getOid())) {
                    List<Long> columnsNums = currentTableResults.stream().map(ResultVO::getColumnNum).collect(Collectors.toList());
                    new HtmlTableResultWriter().appendResultTable(tableVO, columnsNums, beginTimestamp);
                }

            }

            logger.info("QueryResultsWriter - appendFullResults(): END");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeFile() {
        File file = new File(this.resultsFullPath);
        try {
            FileUtils.writeStringToFile(file, "Elapsed time: " + (new Date().getTime() - beginTimestamp) + "ms", "UTF-8", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
