package com.coding.assignment.service;

import com.coding.assignment.Exception.BadRequestException;
import com.coding.assignment.Exception.ResourceNotFoundException;
import com.coding.assignment.dto.BillDTO;

public interface BillService {
    BillDTO save(BillDTO billDTO) throws BadRequestException;
    BillDTO findById(Long id) throws ResourceNotFoundException;
}
