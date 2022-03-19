package Demo.BubleSort.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id","nums"})
@EqualsAndHashCode(of = {"id"})
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nums;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNums(){
        return nums;
    }

    public void setNums(String nums){
        this.nums = nums;
    }
}
