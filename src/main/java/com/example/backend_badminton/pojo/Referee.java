package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Referee {

    private Integer referee_id;

    private String name;

    private String phone;

    private String password;

}
