package ogr.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {
    private ProductsRepository productsRepository;

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Product saveOrUpdate(Product product) {
        return productsRepository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> product = productsRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            return new Product();
        }
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }


    public Page<Product> findAll(Specification<Product> spec, Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return productsRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public void deleteAll() {
        productsRepository.deleteAll();
    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return productsRepository.existsById(id);
    }

//    public List<ProductDto> getDtoData() {
//        return productsRepository.findAllBy();
//    }
}