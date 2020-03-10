package com.amz.microservices.account.server.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "amz_address")
public class Address implements Serializable {

  private static final long serialVersionUID = 5150969414491185086L;

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "country", nullable = false, length = 128)
  private String country;

  @Column(name = "state", nullable = false, length = 128)
  private String state;

  @Column(name = "city", nullable = false, length = 128)
  private String city;

  @Column(name = "address_line_1", nullable = false, length = 128)
  private String addressLine1;

  @Column(name = "address_line_2", length = 128)
  private String addressLine2;

  @Column(name = "zip_code", nullable = false, length = 16)
  private String zipCode;

  @JoinColumn(name = "account_id")
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Account account;

  @PrePersist
  public void prePersist() {
    if (id == null) {
      id = UUID.randomUUID();
    }
  }
}
