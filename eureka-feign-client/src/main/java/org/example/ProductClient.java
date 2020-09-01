package org.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("eureka-client")
public interface ProductClient {
    @RequestMapping("/")
    List<Product> getAllProducts();

    @RequestMapping("/{id}")
    Product getOneProduct(@PathVariable Long id);

    @PostMapping("/add")
    ResponseEntity<Product> saveNewProduct (@RequestBody Product product);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOneProducts(@PathVariable Long id);

    @PostMapping("/edit/{id}")
    ResponseEntity<?> modifyProduct(@RequestBody Product product, @PathVariable Long id);
}
