package com.example.jooheej.domain;

import com.example.jooheej.domain.Address;
import com.example.jooheej.domain.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ORDINAL 은 숫자로 입력되는데 상태가 하나 더 생기면 꼬일 수 있으므로 String 으로 저장 !!
    private DeliveryStatus status;
}
