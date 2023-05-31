package com.coding.assignment.controllers;

import com.coding.assignment.Exception.BadRequestException;
import com.coding.assignment.Exception.ResourceNotFoundException;
import com.coding.assignment.dto.TransactionDTO;
import com.coding.assignment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/pay")
    public ResponseEntity<TransactionDTO> save(@RequestBody @Validated TransactionDTO transactionDTO) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<TransactionDTO>(transactionService.save(transactionDTO), HttpStatus.OK);
    }
    @GetMapping("/transaction")
    public ResponseEntity<TransactionDTO> getBillById(@RequestParam Long id) throws ResourceNotFoundException {
        return new ResponseEntity<TransactionDTO>(transactionService.findById(id), HttpStatus.OK);

    }
}
