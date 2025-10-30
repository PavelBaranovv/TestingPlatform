package com.javarush.baranov.testingplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "authentication_attempts")
@Data
@NoArgsConstructor
public class AuthenticationAttempts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private Integer attempts = 0;

    @Column(name = "last_attempt", nullable = false)
    private LocalDateTime lastAttempt;

    public AuthenticationAttempts(String login, int attempts, LocalDateTime lastAttempt) {
        this.login = login;
        this.attempts = attempts;
        this.lastAttempt = lastAttempt;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AuthenticationAttempts attempts = (AuthenticationAttempts) o;
        return getId() != null && Objects.equals(getId(), attempts.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}