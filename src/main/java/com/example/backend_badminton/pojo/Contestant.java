package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Contestant {

    private Integer contestant_id;

    private String name;

    private Boolean is_leader;

    private String phone;
    private String password;

}
