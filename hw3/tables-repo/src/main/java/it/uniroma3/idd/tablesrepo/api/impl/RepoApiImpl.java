package it.uniroma3.idd.tablesrepo.api.impl;

import it.uniroma3.idd.entity.SimpleTableVO;
import it.uniroma3.idd.entity.TableByRowVO;
import it.uniroma3.idd.hw3.api.ParseApi;
import it.uniroma3.idd.hw3.api.RepoApi;
import it.uniroma3.idd.hw3.filesystem.DatasetBuffer;
import it.uniroma3.idd.tablesrepo.model.TableMentor;
import it.uniroma3.idd.tablesrepo.repository.TableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RepoApiImpl implements RepoApi {

    private static final int TABLES_INTERVAL = 10000;

    private static final int BUFFER_SIZE = 1000;

    @Autowired
    private ParseApi parseApi;
    @Autowired
    private TableRepository repository;

    @Override
    public void insertDataset(String datasetPath) {
        log.info("RepoApiImpl - insertDataset(): datasetPath={}",datasetPath);

        DatasetBuffer datasetBuffer = null;
        int insertedTables = 0;

        try {
            datasetBuffer = new DatasetBuffer(datasetPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<TableMentor> bufferTables = new ArrayList<>();
        boolean datasetEnded = false;
        do {
            String line;
            line = datasetBuffer.readNextLine();

            if(!datasetBuffer.isEnded()) {
                SimpleTableVO simpleTableVO = parseApi.parseSimple(line);

                TableMentor table = new TableMentor();
                table.setOid(simpleTableVO.getOid());
                table.setCells(simpleTableVO.getJsonContent());
                bufferTables.add(table);

                if (bufferTables.size() % BUFFER_SIZE == 0) {
                    repository.saveAll(bufferTables);
                    bufferTables.clear();
                }

                if (insertedTables % TABLES_INTERVAL == 0) {
                    log.info("Inserted n. {} tables", insertedTables);
                }
                insertedTables++;
            } else {
                datasetEnded = true;
            }
        } while (!datasetBuffer.isEnded());
    }

    @Override
    public TableByRowVO getTableByOid(String oid) {
        log.info("RepoApiImpl - getTableByOid(): oid={}",oid);

        TableByRowVO table = new TableByRowVO();
        table.setOid(oid);

        List<TableMentor> tables = repository.findByOid(oid);
        if(tables.size() != 0)
            table.setRows(parseApi.parseSimpleCells(tables.get(0).getCells()).getRows());

        return table;
    }


}
