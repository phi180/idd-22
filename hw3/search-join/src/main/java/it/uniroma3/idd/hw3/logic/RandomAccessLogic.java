package it.uniroma3.idd.hw3.logic;

import it.uniroma3.idd.hw3.api.ParseApi;
import it.uniroma3.idd.entity.ColumnarTableVO;
import it.uniroma3.idd.hw3.api.ParseApiImpl;
import it.uniroma3.idd.hw3.filesystem.DatasetBuffer;
import it.uniroma3.idd.hw3.utils.Utils;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

public class RandomAccessLogic {

    private static final Logger logger = Logger.getLogger(QueryLogic.class.toString());

    public ColumnarTableVO getRandomTable(String datasetPath) throws IOException {
        logger.info("RandomAccessLogic - getRandomTable(): datasetPath="+datasetPath);

        DatasetBuffer datasetBuffer = new DatasetBuffer(datasetPath);
        Random rand = new Random();
        int lineNumber = rand.nextInt(Utils.countLines(datasetPath).intValue());
        logger.info("Fetched random number: #line: " + lineNumber);
        String lineContent = datasetBuffer.readLine(lineNumber);
        ParseApi parseApi = new ParseApiImpl();

        return parseApi.parseByColumn(lineContent);
    }

}
