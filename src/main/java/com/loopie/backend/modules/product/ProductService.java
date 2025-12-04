package com.loopie.backend.modules.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto."));

        product.setNombre(productDetails.getNombre());
        product.setPrecio(productDetails.getPrecio());
        product.setDescripcion(productDetails.getDescripcion());
        product.setImagen(productDetails.getImagen());
        product.setCategoria(productDetails.getCategoria());
        product.setStock(productDetails.getStock());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
