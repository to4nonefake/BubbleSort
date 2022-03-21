package Demo.BubleSort.controller;

import Demo.BubleSort.exceptions.NotFoundException;
import Demo.BubleSort.model.Record;
import Demo.BubleSort.repo.IRecordRepo;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("sort")
public class SortingController {

    private final IRecordRepo recordRepo;

    @Autowired
    public SortingController(IRecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }

    @GetMapping
    public List<Record> list(){
        return recordRepo.findAll();
    }


    @GetMapping("{id}")
    public RequestOneResult getOne(@PathVariable("id") Record record) {
        if (record == null) {
            throw new NotFoundException();
        }

        Long[] items = recordToArray(record);

        RequestOneResult result = new RequestOneResult();
        result.id = record.getId();
        result.items = items;

        return result;
    }

    @PostMapping
    public Record create (@RequestBody Record record) {
        Long[] items = recordToArray(record);

        int n = items.length;

        //Bubble Sort
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (items[j - 1] > items[j]) {
                    //Swap
                    Long cache = items[j - 1];
                    items[j - 1] = items[j];
                    items[j] = cache;
                }
            }
        }

        //convert to string
        String result = "";

        for (int i = 0; i < items.length; i++) {
            result += items[i] + " ";
        }

        record.setNums(result);

        return recordRepo.save(record);
    }

    @PutMapping("{id}")
    public Record update (
            @PathVariable("id") Record recordFromDb,
            @RequestBody Record record
    ) {
        BeanUtils.copyProperties(record, recordFromDb, "id");
        return recordRepo.save(recordFromDb);
    }

    @DeleteMapping("{id}")
    public void delete (@PathVariable("id") Record record){
        recordRepo.delete(record);
    }

    private Long[] recordToArray(Record record){
        String[] array = record.getNums().split(" ");
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.parseLong(array[i]);
        }
        return result;
    }

    private class RequestOneResult {
        public Long id;
        public Long[] items;
    }
}
