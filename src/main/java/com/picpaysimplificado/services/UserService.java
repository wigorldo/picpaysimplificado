package com.picpaysimplificado.services;

import com.picpaysimplificado.dtos.UserDto;
import com.picpaysimplificado.repositories.UserRepository;
import com.picpaysimplificado.user.User;
import com.picpaysimplificado.user.UserType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Data
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.LOJISTA) {
            throw new Exception("Usuário Lojista não está autorizado a fazer transação de pagamento ");
        }
        if (sender.getBalance().compareTo(amount) <= 0) {
            throw new Exception("Saldo Insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
        return user;
    }

    public void saveUser(UserDto userDto) throws Exception {
        User user = new User();
        userRepository.findUserByDocument(userDto.document());
        if (userRepository == null) {
            if (!userDto.document().isBlank() || userDto.document() != null) {
                user.setFirstName(userDto.firstName());
                user.setLastName(userDto.lastName());
                user.setEmail(userDto.email());
                user.setUserType(userDto.userType());
                user.setBalance(userDto.balance());
                user.setPassword(userDto.password());
            }
        } else {
            throw new Exception("Documento já existe na base de dados!");
        }
        userRepository.save(user);
    }
}
