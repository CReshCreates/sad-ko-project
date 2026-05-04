package com.spring.smbs_backend.controller;

import com.spring.smbs_backend.DTO.Request.ProductAdditionRequest;
import com.spring.smbs_backend.model.Product;
import com.spring.smbs_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productsService;

    //getting products
    @GetMapping("/admin/product/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productsService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/getByCode/{barcode}")
    public ResponseEntity<List<Product>> getAllProductsByBarcode(@PathVariable String barcode){
        Product product = productsService.getProductsByBarcode(barcode);
        if (product != null) {
            return ResponseEntity.ok(Collections.singletonList(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //getting stats
    @GetMapping("/admin/product/getTotalItemsCount")
    public ResponseEntity<?> getTotalItemsCount(){
        return new ResponseEntity<>(productsService.getAllProductsCount(), HttpStatus.OK);
    }

    @GetMapping("/admin/product/getLowStockProductsCount")
    public ResponseEntity<?> getLowStockProductsCount(){
        return new ResponseEntity<>(productsService.getLowStockProductsCount(), HttpStatus.OK);
    }

    @GetMapping("/admin/product/getOnStockProductsCount")
    public ResponseEntity<?> getOnStockProductsCount(){
        return new ResponseEntity<>(productsService.getOnStockProductsCount(), HttpStatus.OK);
    }

    @GetMapping("/admin/product/getOutOfStockProductsCount")
    public ResponseEntity<?> getOutOfStockProductsCount(){
        return new ResponseEntity<>(productsService.getOutOfStockProductsCount(), HttpStatus.OK);
    }

    //adding products
    @PostMapping("/admin/product/addNewProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductAdditionRequest productAdditionRequest){
        try{
            System.out.println(productAdditionRequest);
            Product product1 = productsService.addProducts(productAdditionRequest);
            return new ResponseEntity<>(product1, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //updating product details
    @PutMapping("/admin/editProduct/{barcode}")
    public ResponseEntity<?> updateProductByBarcode(@PathVariable String barcode, @RequestBody Product product){
        Product updatedProduct = productsService.updateProductByBarcode(barcode, product);
        if(updatedProduct!=null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/admin/deleteProduct/{barcode}")
    public ResponseEntity<?> deleteProductByBarcode(@PathVariable String barcode){
        Product product = productsService.getProductsByBarcode(barcode);
        if(product != null){
            product.setDeleted(true);
            Product updatedProduct = productsService.updateProductByBarcode(barcode, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
}
