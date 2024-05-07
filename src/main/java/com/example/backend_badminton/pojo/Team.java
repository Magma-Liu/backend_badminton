package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Team {

    private Integer team_id;

    private String name;

    private String introduction;

    private Integer leader_id;

    private String leader_name;

    private String leader_phone;

}
