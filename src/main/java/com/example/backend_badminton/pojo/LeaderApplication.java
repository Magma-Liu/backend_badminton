package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Component
@Data
public class LeaderApplication {
    private Integer application_id;
    private Integer contestant_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date application_time;

    private Boolean is_approved;

    private Integer admin_id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date approval_time;
}
