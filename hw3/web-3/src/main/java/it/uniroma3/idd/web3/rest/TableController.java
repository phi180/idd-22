package it.uniroma3.idd.web3.rest;

import it.uniroma3.idd.entity.TableByRowVO;
import it.uniroma3.idd.tablesrepo.util.CsvWriter;
import it.uniroma3.idd.web3.domain.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class TableController {

    @Autowired
    private TableService tableService;

    @PostMapping("/dataset")
    public void insertDataset(@RequestBody InsertDatasetRequest insertDatasetRequest) {
        tableService.insertDataset(insertDatasetRequest.getDatasetPath());
    }

    @PostMapping("/datasets")
    public void insertMultipleDatasets(@RequestBody InsertDatasetsRequest insertDatasetsRequest) {
        tableService.insertMultipleDatasets(insertDatasetsRequest.getDatasetPath());
    }

    @GetMapping("/table/{oid}")
    public TableByRowVO getTableByOid(@PathVariable String oid) {
        log.info("TableController - getTableByOid(): oid={}",oid);
        TableByRowVO table = tableService.getTable(oid);
        CsvWriter.writeTableToCsv(table);

        return table;
    }

    @PostMapping("/query")
    public List<TableByRowVO> query(@RequestBody QueryRequest queryRequest) {
        log.info("TableController - query(): queryRequest={}",queryRequest);
        List<TableByRowVO> tableByRowVOS = tableService.query(queryRequest.getColumnCells(), queryRequest.getK());
        for(TableByRowVO table : tableByRowVOS) {
            log.info("TableController - query(): Writing table={}",table);
            CsvWriter.writeTableToCsv(table);
        }

        return tableByRowVOS;
    }

    @DeleteMapping("/delete")
    public void deleteAll() {
        tableService.deleteAll();
    }

}
