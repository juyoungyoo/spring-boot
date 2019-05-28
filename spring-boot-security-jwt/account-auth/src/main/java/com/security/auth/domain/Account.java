package com.security.auth.domain;

import com.security.auth.model.AccountUpdateRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;
    private boolean emailVerified;
    private boolean state;
    private String providerId;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    private Set<AuthProvider> provider;

    @Builder
    public Account(String name,
                   String password,
                   String email,
                   RoleType roleType,
                   boolean emailVerified,
                   Set<AuthProvider> provider,
                   boolean state) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roleType = roleType;
        this.emailVerified = emailVerified;
        this.provider = provider;
        this.state = state;
    }

    public void updateMyAccount(AccountUpdateRequest dto) {
        name = dto.getName();
    }

    public void deleteAccount() {
        state = false;
    }
}