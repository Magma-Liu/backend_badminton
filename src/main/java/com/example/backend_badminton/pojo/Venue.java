package com.example.backend_badminton.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Venue {

    private Integer venue_id;

    private String address;

    private String name;

    private Integer area_count;
}
