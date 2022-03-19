package Demo.BubleSort.controller;

import Demo.BubleSort.model.Record;
import Demo.BubleSort.repo.IRecordRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Record getOne(@PathVariable("id") Record record) {
        return record;
    }

    @PostMapping
    public Record create (@RequestBody Record record) {
        String[] array = record.getNums().split(" ");
        Integer[] nums = new Integer[array.length];

        //Convert to array
        for (int i = 0; i < array.length; i++) {
            nums[i] = Integer.parseInt(array[i]);
        }

        int n = array.length;

        //Bubble Sort
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (nums[j - 1] > nums[j]) {
                    //Swap
                    int cache = nums[j - 1];
                    nums[j - 1] = nums[j];
                    nums[j] = cache;
                }
            }
        }

        //convert to string
        String result = "";

        for (int i = 0; i < nums.length; i++) {
            result += nums[i] + " ";
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
}
