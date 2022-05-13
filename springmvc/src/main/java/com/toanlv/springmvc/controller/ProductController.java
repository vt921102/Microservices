package com.toanlv.springmvc.controller;

import com.toanlv.springmvc.model.Product;
import com.toanlv.springmvc.model.ResponseObject;
import com.toanlv.springmvc.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/Product")
public class ProductController {
    @Autowired // giups khoi tao chi 1 lan giong nhu singleton
    ProductRepository productRepository;

    @GetMapping(path = "/hello")
    public String hello() {
        return "Xin chao";
    }

    @GetMapping("")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
        // khi ma co loi exception tra ve thi phai dong bo doi tuong tra ve
        // phai co data tra ve de biet chi tiet loi
        // tao doi tuong tra ve: data, message, status
    ResponseEntity<ResponseObject> getproductbyID(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Find product successfull", "ok", foundProduct)
                )
                :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Can not find product by id = " + id, "faild", "")
                );

    }
  @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product){

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Insert data successfull","success",productRepository.save(product)));
    }
    @GetMapping("/n/{productName}")
    ResponseEntity<ResponseObject> getByProductName(@PathVariable String productName){
        List<Product> foundProduct=productRepository.findProductByProductName(productName.trim());
        return foundProduct.size()>0?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Find product successfull", "ok", foundProduct)
                )
                :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Can not find product by name = " + productName, "faild", "")
                );

    }
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            product.map(product1 -> {
                product1.setProductName(newProduct.getProductName());
                product1.setPrice(newProduct.getPrice());
                product1.setUrl(newProduct.getUrl());
                product1.setYear(newProduct.getYear());
            return productRepository.save(product1);
            });
        } else {
            newProduct.setId(id);
            productRepository.save(newProduct);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject("Update product successfully","ok",newProduct));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id){
        boolean exist = productRepository.existsById(id);
     if(exist) {
         productRepository.deleteById(id);
         return ResponseEntity.status(HttpStatus.OK).body(
                 new ResponseObject("Delete product successfully","ok",""));
     }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Can not find product to delete","ok",""));
    }
}
