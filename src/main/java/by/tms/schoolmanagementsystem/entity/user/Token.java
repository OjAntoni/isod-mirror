package by.tms.schoolmanagementsystem.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private UUID value;
    @OneToOne
    private User user;

    public Token(User user) {
        value = UUID.randomUUID();
        this.user = user;
    }
}
