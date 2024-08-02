package org.example.service;

import org.example.dto.ProductDTO;
import org.example.entity.Product;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.ManufacturerRepository;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<ProductDTO> getAllProducts() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = convertToEntity(dto);
        repository.save(product);
        return convertToDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setManufacturer(manufacturerRepository.findById(dto.getManufacturerId()).orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found")));
        repository.save(product);
        return convertToDTO(product);
    }

    public void deleteProduct(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        repository.delete(product);
    }


    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setManufacturerId(product.getManufacturer().getId());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCategory(dto.getCategory());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setManufacturer(manufacturerRepository.findById(dto.getManufacturerId()).orElseThrow(() -> new ResourceNotFoundException("Manufacturer not found")));
        return product;
    }
}
