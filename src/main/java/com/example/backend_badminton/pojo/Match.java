package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Match {
    private Integer match_id;

    private Integer event_id;

    private Integer team1_id;

    private Integer team2_id;

    private String scoring_system;

    private Integer referee_id;

    private String score1;

    private String score2;

    private String score3;

    private String score4;

    private String score5;

    private String score6;

    private String score7;

    private Integer winner_id;
}
