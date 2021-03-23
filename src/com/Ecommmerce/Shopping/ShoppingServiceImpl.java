package com.Ecommmerce.Shopping;

import com.Ecommmerce.customer.Buyer;
import com.Ecommmerce.exceptions.CheckOutException;
import com.Ecommmerce.payment.PayStack;
import com.Ecommmerce.payment.PaymentService;

import java.time.LocalDate;

public class ShoppingServiceImpl implements ShoppingService {

    private PaymentService paymentService;

    public  ShoppingServiceImpl(){
        paymentService = new PayStack();
    }


    @Override

    public Order checkout(Buyer buyer) throws CheckOutException {
        isValidCheckout(buyer);
        Order order=new Order();
        Cart buyerCart = buyer.getCart();
        boolean result = paymentService.pay(buyerCart.getPaymentCard(), buyerCart.calculateCartTotal());
        if(result){

            order.setOrderItems(buyer.getCart().getCartItems());
            order.setPaid(result);
            order.setOrderDate(LocalDate.now());
            order.setOrderTotal(buyerCart.calculateCartTotal());
            order.setDeliveryAddress(buyerCart.getDeliveryAddress());
            buyer.setCart(null);
        }

        return order;

    }
    private boolean isValidCheckout(Buyer buyer) throws CheckOutException{
        if(buyer == null){
            throw new CheckOutException("No buyer found at checkout");
        }
        if(buyer.getCart() == null){
//            todo log error
            throw new CheckOutException("No Cart found at checkout");
        }

        if(buyer.getCards() == null||buyer.getCards().isEmpty()){
//            todo log error
            throw new CheckOutException("No payment cards found at checkout found at checkout");
        }

        if(buyer.getCart().getCartItems().isEmpty()){
//            todo log error
            throw new CheckOutException("No payment cards found at checkout found at checkout");
        }

        return true;
    }

}