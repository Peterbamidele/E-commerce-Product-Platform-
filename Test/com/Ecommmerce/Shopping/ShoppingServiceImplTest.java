package com.Ecommmerce.Shopping;

import com.Ecommmerce.customer.Address;
import com.Ecommmerce.customer.Buyer;
import com.Ecommmerce.exceptions.CheckOutException;
import com.Ecommmerce.payment.PaymentCard;
import com.Ecommmerce.product.ProductDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class ShoppingServiceImplTest {
    ShoppingService shoppingService=new ShoppingServiceImpl();
    private Buyer dozie;
    @BeforeEach
    void setUp() {
        ProductDB productDB= new ProductDB();
        dozie= new Buyer();
        PaymentCard fbnVisa= new PaymentCard("123456","Don Dozie", LocalDate.of(2025,1,29));
        dozie.getCards().add(fbnVisa);
        Cart dozieCart= new Cart();
        dozieCart.addToCart(productDB.getMockProducts().get("AD001"),5);
        dozie.setCart(dozieCart);

        dozie.getCart().setPaymentCard(dozie.getCards().get(0));
        Address home= new Address();
        home.setHouseNumber(1);
        home.setStreet("Aso Rock Avenue");
        home.setCity("Aso Rock");
        dozie.getAddresses().add(home);
        dozie.setCart(dozieCart);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void checkOutTestWithNullBuyer(){
        assertThrows(CheckOutException.class, ()-> shoppingService.checkout(null));

    }
    
    @Test
    void checkOutWithNullCart(){
        dozie.setCart(null);
        assertThrows(CheckOutException.class, ()-> shoppingService.checkout(dozie));

    }

    @Test
    void checkOutWithEmptyCart(){
        dozie.setCart(new Cart());
        assertThrows(CheckOutException.class, ()-> shoppingService.checkout(dozie));

    }

    @Test
    void checkOutTestWithNullPaymentCard(){
        dozie.setCards(null);
        assertThrows(CheckOutException.class, ()-> shoppingService.checkout(dozie));

    }


    @Test
    void checkOut(){
        try {

            Map<String,Item> cartItem=dozie.getCart().getCartItems();
            Order dozieOrder=shoppingService.checkout(dozie);
            assertEquals(cartItem,dozieOrder.getOrderItems());
            assertTrue(dozieOrder.isPaid());
            assertNotNull(dozieOrder);
            assertTrue(dozieOrder.isPaid());
            assertNull(dozie.getCart());
        } catch (CheckOutException e) {
            e.printStackTrace();
        }
    }
    @Test
    void checkOutWithExpiredPaymentCard(){
        try {  Address deliveryAddress=dozie.getCart().getDeliveryAddress();
            Map<String, Item> cartItems=dozie.getCart().getCartItems();
            dozie.getCart().getPaymentCard().setExpiry(LocalDate.now());
            Order dozieOrder= shoppingService.checkout(dozie);
            assertNotNull(dozieOrder);
            assertFalse(dozieOrder.isPaid());
            assertEquals(deliveryAddress,dozieOrder.getDeliveryAddress());
            assertNotNull(dozie.getCart());

        } catch (CheckOutException e) {
            e.printStackTrace();
        }

    }

}