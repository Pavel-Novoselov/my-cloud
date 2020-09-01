package org.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductClient productClient;


    @GetMapping
    public String showAll(Model model) {
        List<Product> list = productClient.getAllProducts();
        model.addAttribute("products", list);
        return "all_products";
    }

    @GetMapping("/{id}")
    public String getOneProduct(@PathVariable Long id, Model model){
        List<Product> list = new ArrayList<>();
        Product product = productClient.getOneProduct(id);
        list.add(product);
        model.addAttribute("products", list);
        return "all_products";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_product_form";
    }

    @PostMapping("/add")
    public String saveNewProduct(@ModelAttribute Product product) {
        productClient.saveNewProduct(product);
        return "redirect:/products/";
    }

    @DeleteMapping("/{id}")
    public String deleteOneProducts(@PathVariable Long id) {
        productClient.deleteOneProducts(id);
        return "redirect:/products/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productClient.getOneProduct(id));
        return "edit_product_form";
    }

    @PostMapping("/edit")
    public String modifyProduct(@ModelAttribute Product product) {
        productClient.modifyProduct(product, product.getId());
        return "redirect:/products/";
    }
}