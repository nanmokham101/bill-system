package com.coding.assignment.controllers;

import com.coding.assignment.Exception.BadRequestException;
import com.coding.assignment.Exception.ResourceNotFoundException;
import com.coding.assignment.common.BillResponse;
import com.coding.assignment.common.Billers;
import com.coding.assignment.common.Constant;
import com.coding.assignment.dto.BillDTO;
import com.coding.assignment.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class BillController {
    @Autowired
    private BillService billService;
    @PostMapping("/add")
    public ResponseEntity<BillDTO> save(@RequestBody @Validated BillDTO billDTO) throws BadRequestException {
        return new ResponseEntity<BillDTO>(billService.save(billDTO), HttpStatus.OK);
    }
    @GetMapping("/list")
    public BillResponse getBillById(@RequestParam Long id) throws ResourceNotFoundException {
        BillResponse response = new BillResponse();
        BillDTO billDTO = billService.findById(id);
        if (billDTO != null) {
            response.setStatus_message(Constant.BILL_FOUND);
            response.setDate_time(billDTO.getDate_time());
            List<Billers> billers = new ArrayList<>();
            Billers biller = new Billers();
            biller.setBill_id(billDTO.getId());
            biller.setName(billDTO.getName());
            biller.setDescription(billDTO.getDescription());
            billers.add(biller);
            response.setBillers(billers);
        }else{
            response.setStatus_message(Constant.BILL_NOT_FOUND);
        }

        return response;

    }
}
