package org.example.spring.models.main;
import jakarta.persistence.*;
import lombok.*;
import org.example.spring.models.auth.User;

@Entity
@Data
@Table(name = "avatars_url")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    private String urlPath;
}
