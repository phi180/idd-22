package it.uniroma3.idd.web3.rest;

import it.uniroma3.idd.entity.TableByRowVO;
import it.uniroma3.idd.hw3.token.CustomTokenizer;
import it.uniroma3.idd.web3.domain.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
        return tableService.getTable(oid);
    }

    @PostMapping("/query")
    public List<TableByRowVO> query(@RequestBody QueryRequest queryRequest) {
        return tableService.query(queryRequest.getColumnCells(), queryRequest.getK());
    }


    @DeleteMapping("/delete")
    public void deleteAll() {
        tableService.deleteAll();
    }

}
