package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class MatchInfo {
    private List<Integer> teams;

    private List<Integer> referees;

    private Integer event_id;

    private String scoring_system;

    private Integer count;

}
