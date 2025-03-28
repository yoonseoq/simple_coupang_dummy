package com.green.simple_bank_dummy.account.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Acc {
    private int accountId;
    private String accountType;
    private double balance;
    private int customerId;
}
