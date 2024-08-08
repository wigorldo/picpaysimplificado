package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionDto;
import com.picpaysimplificado.repositories.TransactionRepository;
import com.picpaysimplificado.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@Data
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;
    private RestTemplate restTemplate;

    public void createdTransaction(TransactionDto transactionDto) throws Exception {
        User sender = userService.findUserById(transactionDto.senderId());
        User receiver = userService.findUserById(transactionDto.receiverId());
        userService.validateTransaction(sender, transactionDto.value());
        boolean isAuthorized = authorizeTransaction(sender, transactionDto.value());
        if (!authorizeTransaction(sender, transactionDto.value())) {
            throw new Exception("Transação não autorizada");
        }
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(transactionDto.value());
        transaction.setTimestamp(LocalDateTime.now());
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse =
                restTemplate.getForEntity("https://run.mocky.io/y3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
        if (authorizationResponse.getStatusCode() == HttpStatus.OK
                && authorizationResponse.getBody().get("message") == "Autorizado") {
            return true;
        } else return false;
    }
}
