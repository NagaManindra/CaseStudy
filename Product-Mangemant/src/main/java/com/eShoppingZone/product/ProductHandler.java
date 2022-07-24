package com.eShoppingZone.product;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.eShoppingZone.product.model.Product;

@SuppressWarnings("deprecation")
public class ProductHandler extends SpringBootRequestHandler<Product, Product> {

}
