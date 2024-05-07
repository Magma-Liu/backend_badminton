package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ActivityApplication {
    private Integer application_id;
    private Integer event_id;
    private Integer team_id;
    private Boolean is_approved;
}
