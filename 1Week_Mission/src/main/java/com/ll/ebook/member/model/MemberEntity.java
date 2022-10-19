package com.ll.ebook.member.model;

import com.ll.ebook.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name = "users")
public class MemberEntity extends BaseEntity {

    @Column(unique = true)
    private String username;

    @Setter
    private String password;

    @Setter
    private String nickname;

    @Setter
    @Column(unique = true)
    private String email;

}
