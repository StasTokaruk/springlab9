package com.labrob9.springlab9.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String full_name;
    private String num_card;
    private String MM_YY;
    private Integer cvv;
    private Integer money = 1000000;

    public Payment(String full_name, String num_card, String MM_YY, Integer cvv) {
        this.full_name = full_name;
        this.num_card = num_card;
        this.MM_YY = MM_YY;
        this.cvv = cvv;
    }

    public Payment() {}

    public Boolean validatePayment(Integer cost) {
        if(cost < money){
            money -= cost;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getNum_card() {
        return num_card;
    }

    public void setNum_card(String num_card) {
        this.num_card = num_card;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
