package in.dudeapp.common.dto;

import lombok.Data;

/**
 * Created by mohan on 27/07/22
 */
@Data
public class DatabaseProperties {
    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbname;
    private String dbInstanceIdentifier;
}
