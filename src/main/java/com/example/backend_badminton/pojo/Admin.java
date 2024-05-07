package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Admin {
    private Integer admin_id;
    private String name;
    private String phone;
    private String password;
}
