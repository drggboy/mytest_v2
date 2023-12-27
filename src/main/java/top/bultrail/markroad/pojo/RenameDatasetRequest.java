package top.bultrail.markroad.pojo;
import lombok.Data;

@Data
public class RenameDatasetRequest {
    private String oldName;
    private String newName;
}
