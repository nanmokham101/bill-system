package com.coding.assignment.service.serviceImpl;


import com.coding.assignment.Exception.ResourceNotFoundException;
import com.coding.assignment.common.Constant;
import com.coding.assignment.dto.BillDTO;
import com.coding.assignment.models.Bill;
import com.coding.assignment.repository.BillRepository;
import com.coding.assignment.service.BillService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    private static Logger logger = Logger.getLogger(BillServiceImpl.class);
    @Override
    public BillDTO save(BillDTO billDTO) {
        Bill bill = new Bill();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = formatter.format(date);
        BeanUtils.copyProperties(billDTO, bill);
        bill.setDate_time(formattedDate);
        billRepository.save(bill);
        billDTO.setId(bill.getId());
        billDTO.setDate_time(bill.getDate_time());
        billDTO.setStatus_message(Constant.SUCCESS);
        logger.info("billDTO in save:........." + billDTO);
        return billDTO;
    }

    @Override
    public BillDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Bill> optionalBill = billRepository.findById(id);
        BillDTO billDTO = new BillDTO();
        if (optionalBill.isPresent()) {
            BeanUtils.copyProperties(optionalBill.get(), billDTO);
        } else {
            throw new ResourceNotFoundException(Constant.BILL_NOT_FOUND);
        }
        logger.info("billDTO in findById:........." + billDTO);
        return billDTO;
    }
}
