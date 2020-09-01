package ogr.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/")
public class ProductController {
    private ProductsService productsService;

    @Autowired
    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping(produces = "application/json")
    public List<Product> getAllProducts() {
        final List<Product> list = productsService.findAll();
        if (list.isEmpty()) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }
//
//    @GetMapping("/{id}")
//    public Product getOne(@PathVariable Long id){
//        return productsService.findById(id);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOneProduct(
            @PathVariable  Long id
    ) {
        if (!productsService.existsById(id)) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productsService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOneProducts(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        productsService.deleteById(id);
        return new ResponseEntity<>("Product id:" + id + " deleted", HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveNewProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            product.setId(null);
        }
        if (product.getPrice().doubleValue() < 0.0) {
            return new ResponseEntity<>(
                    "Product's price cannot be negative",
                    HttpStatus.BAD_REQUEST
            );
        }
        Product productNew = productsService.saveOrUpdate(product);
        if (productNew == null) {
            return new ResponseEntity<>(
                    "Product was not saved",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(productNew, HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> modifyProduct(@RequestBody Product product, @PathVariable Long id) {
        if (product.getId() == null || !productsService.existsById(product.getId())) {
 //           throw new ProductNotFoundException("Product not found, id: " + product.getId());
            return new ResponseEntity<>(
                    "Product not found, id: " + product.getId(),
                    HttpStatus.NOT_FOUND
            );
        }
        if (product.getPrice().doubleValue() < 0.0) {
            return new ResponseEntity<>(
                    "Product's price cannot be negative",
                    HttpStatus.BAD_REQUEST
            );
        }
        Product productFromDB = productsService.findById(id);
        productFromDB.setPrice(product.getPrice());
        productFromDB.setTitle(product.getTitle());
        return new ResponseEntity<>(productsService.saveOrUpdate(productFromDB), HttpStatus.OK);
    }

}