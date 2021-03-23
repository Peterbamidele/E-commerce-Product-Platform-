package com.Ecommmerce.Shopping;

import com.Ecommmerce.customer.Buyer;
import com.Ecommmerce.exceptions.CheckOutException;

public interface ShoppingService {
    public Order checkout(Buyer buyer) throws CheckOutException;
}
