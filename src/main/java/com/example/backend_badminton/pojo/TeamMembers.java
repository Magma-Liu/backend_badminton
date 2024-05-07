package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TeamMembers {
    private Integer team_id;
    private Integer contestant_id;
}
