package Demo.BubleSort.repo;

import Demo.BubleSort.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecordRepo extends JpaRepository <Record, Long> {
}
