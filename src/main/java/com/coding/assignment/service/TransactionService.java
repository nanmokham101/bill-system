package com.coding.assignment.service;

import com.coding.assignment.Exception.BadRequestException;
import com.coding.assignment.Exception.ResourceNotFoundException;
import com.coding.assignment.dto.BillDTO;
import com.coding.assignment.dto.TransactionDTO;

public interface TransactionService {
    TransactionDTO save(TransactionDTO transactionDTO) throws BadRequestException, ResourceNotFoundException;
    TransactionDTO findById(Long id) throws ResourceNotFoundException;
}
