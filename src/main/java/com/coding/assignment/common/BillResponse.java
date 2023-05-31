package com.coding.assignment.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillResponse {
    private String status_message;
    private String date_time;
    private List<Billers> billers;
}
