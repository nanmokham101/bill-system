package com.coding.assignment.service.serviceImpl;

import com.coding.assignment.Exception.BadRequestException;
import com.coding.assignment.Exception.ResourceNotFoundException;
import com.coding.assignment.common.Constant;
import com.coding.assignment.dto.TransactionDTO;
import com.coding.assignment.models.Transaction;
import com.coding.assignment.repository.TransactionRepository;
import com.coding.assignment.service.TransactionService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    private static Logger logger = Logger.getLogger(TransactionServiceImpl.class);
    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) throws BadRequestException {
        Transaction transaction = new Transaction();
        if (transactionDTO.getAmount() > 100000) {
            throw new BadRequestException(Constant.INVALID_AMOUNT);
        } else {

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String formattedDate = formatter.format(date);
            BeanUtils.copyProperties(transactionDTO, transaction);
            transaction.setTransaction_date(formattedDate);
            transactionRepository.save(transaction);
            transactionDTO.setTransaction_id(transaction.getTransaction_id());
            transactionDTO.setTransaction_date(transaction.getTransaction_date());
            transactionDTO.setStatus_message(Constant.BILL_FOUND);
            logger.info("transactionDTO in save:........." + transactionDTO);

            return transactionDTO;
        }
    }

    @Override
    public TransactionDTO findById(Long id) throws ResourceNotFoundException {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        TransactionDTO transactionDTO = new TransactionDTO();
        if (optionalTransaction.isPresent()) {
            BeanUtils.copyProperties(optionalTransaction.get(), transactionDTO);
            transactionDTO.setStatus_message(Constant.BILL_FOUND);
        } else {
            throw new ResourceNotFoundException(Constant.BILL_NOT_FOUND);
        }
        logger.info("transactionDTO..findById......."+transactionDTO);
        return transactionDTO;
    }
}
