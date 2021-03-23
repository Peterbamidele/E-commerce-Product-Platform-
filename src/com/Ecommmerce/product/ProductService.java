package com.Ecommmerce.product;

import com.Ecommmerce.exceptions.ProductException;
import com.Ecommmerce.product.Product;

import java.util.Map;

/**
 * Provides services for dealing with products in pentazon
 * */

public interface ProductService {
    /**
     *  search for product with specified productId
     * @param productId
     * @return
     */

    public Product findProductById(String productId) throws ProductException;
    /**
     *  search for product with specified productId
     * @param product
     * @return
     */

    public boolean addProduct(Product product) throws ProductException;

    public boolean removeProduct(Product product) throws ProductException;

    public Map<String, Product> getAllProducts();
}