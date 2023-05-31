package com.coding.assignment.dto;

import com.coding.assignment.validation.Phone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor

public class TransactionDTO {
    private Long transaction_id;
    @NotNull
    private Long bill_id;
    @NotBlank
    private String api_caller;
    @NotNull
    private Integer amount;
    @NotBlank(message = "please enter reference_no")
    private String reference_no;
    @NotBlank(message = "please enter phone_number")
    @Phone
    private String phone_number;
    private String transaction_date;
    private String status_message;
}
