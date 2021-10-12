package io.security.core.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccessIp {

    @Id @GeneratedValue
    @Column(name = "access_ip_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String ipAddress;
}
