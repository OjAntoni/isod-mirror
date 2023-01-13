package by.tms.schoolmanagementsystem.repository;

import by.tms.schoolmanagementsystem.entity.user.Token;
import by.tms.schoolmanagementsystem.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findTokenByUser(User user);
    Token findTokenByValue(UUID value);
}
