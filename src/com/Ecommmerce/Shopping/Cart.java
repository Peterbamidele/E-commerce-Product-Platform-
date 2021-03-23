package com.Ecommmerce.Shopping;


import com.Ecommmerce.customer.Address;
import com.Ecommmerce.exceptions.ProductException;
import com.Ecommmerce.payment.PaymentCard;
import com.Ecommmerce.product.Product;
import com.Ecommmerce.product.ProductService;
import com.Ecommmerce.product.ProductServiceImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class Cart {
    private Logger logger=Logger.getLogger(Cart.class.getName());
    private Map<String, Item> cartItems;
    private ProductService productService;
    private BigDecimal total=BigDecimal.ZERO;
    private PaymentCard paymentCard;
    private Address deliveryAddress;


    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }


    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public Cart(){
        cartItems = new HashMap<>();
        productService= new ProductServiceImpl();
    }
    public void addToCart(Product product){
        if(isVerifiedProduct(product)){
            Item item= cartItems.get(product.getProductId());
            if(item==null){
                item= new Item(product);
            }
            item.addItems(BigInteger.ONE.intValue());
            cartItems.put(product.getProductId(),item);
        }
    }
    public void addToCart(Product product,int quantity){
        if(isVerifiedProduct(product)){
            Item item= cartItems.get(product.getProductId());
            if(item==null){
                item= new Item(product);
            }
            item.addItems(quantity);
            cartItems.put(product.getProductId(),item);
        }
    }
    private boolean isVerifiedProduct(Product product){

        boolean verified=false;
        try {
            Product verifiedProduct=productService.findProductById(product.getProductId());
            verified=true;
        } catch (ProductException e) {

            //@todo add log message
            //logger.log(System.Logger.Level.INFO,);
        }
        return verified;
    }
    public boolean removeFromCart(Product product){
        boolean removed=false;
        if(product!=null){
            this.cartItems.remove(product.getProductId());
            removed=true;
        }
        return removed;
    }

    public Map<String, Item> getCartItems() {
        return cartItems;
    }
    public BigDecimal calculateCartTotal(){
        if(!cartItems.isEmpty()){
            this.total=BigDecimal.ZERO;
            Iterator<Item> cartItemIterator= cartItems.values().iterator();
            while (cartItemIterator.hasNext()){
                this.total=this.total.add(cartItemIterator.next().getTotal());
            }
        }
        return this.getTotal();
    }

    private BigDecimal getTotal() {
        return total;
    }
}
