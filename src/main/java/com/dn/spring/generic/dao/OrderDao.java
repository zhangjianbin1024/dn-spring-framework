package com.dn.spring.generic.dao;

import com.dn.spring.generic.domain.OrderModel;
import org.springframework.stereotype.Component;

@Component
public class OrderDao implements IDao<OrderModel> {//@1
}