package com.example.jooheej.domain;

import com.example.jooheej.domain.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 다른 곳에서 new OrderItem() 으로 생성하는 것을 제약하기 위해 protected 생성자를 만든다.
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice); // 할인, 쿠폰 과 같은 경우가 있기때문에 orderPrice를 받아서 set
        orderItem.setCount(count);

        item.removeStock(count); //재고 수량 줄여준다.
        return orderItem;
    }

    //==비즈니스 로직==
    public void cancel() {
        getItem().addStock(count); //재고 수량 원복
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
