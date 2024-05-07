package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Component
@Data
public class Event {

    private Integer event_id;

    private String name;

    private String introduction;

    private Integer venue_id;

    private Integer required_area_count;

    private Integer chief_referee_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date activity_time;

    private String scoring_system;

    private String match_types;

}
