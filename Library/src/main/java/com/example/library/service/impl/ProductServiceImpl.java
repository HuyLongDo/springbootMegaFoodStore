package com.example.library.service.impl;

import com.example.library.dto.ProductDto;
import com.example.library.model.Product;
import com.example.library.repository.ProductRepository;
import com.example.library.service.ProductService;
import com.example.library.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;

    @Override
    public List<ProductDto> getListProductDto() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = transferDataProduct_ProductDto(products);
        return productDtos;
    }

    //Chuyển 1 món product -> productDto
    @Override
    public ProductDto getProductById(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.getById(id);
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        return productDto;
    }

    @Override
    public Page<ProductDto> pageProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList = this.getListProductDto();
        Page<ProductDto> productDtoPage = toPage(productDtoList, pageable);
        return productDtoPage;
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo,String keyword) {
        Pageable pageable = PageRequest.of(pageNo,5);
        List<ProductDto> productDtoList = transferDataProduct_ProductDto(productRepository.searchProductsList(keyword));
        Page<ProductDto> productDtoPage = toPage(productDtoList, pageable);
        return productDtoPage;
    }

    private Page toPage(List<ProductDto> list , Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }


    //Chuyển danh sách product -> productDto
    private List<ProductDto> transferDataProduct_ProductDto(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());
            productDto.setCategory(product.getCategory());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if (imageUpload.uploadFile(imageProduct)){
                    System.out.println("Upload image successfully!");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setCategory(productDto.getCategory());
            product.set_deleted(false);
            product.set_activated(true);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getById(productDto.getId());
            if (imageProduct == null) {
                productUpdate.setImage(productUpdate.getImage());
            } else {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                    System.out.println("Image existed!");
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                    System.out.println("Upload Image complete!");
                }
            }
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setName(productDto.getName());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());
            System.out.println(productUpdate.getName()+ " : " +productUpdate.getCategory());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(false);
        product.set_activated(true);
        productRepository.save(product);
    }

    @Override
    public void disableById(Long id) {
        Product product = productRepository.getById(id);
        product.set_deleted(true);
        product.set_activated(false );
        productRepository.save(product);
    }

    //Customer

    @Override
    public List<ProductDto> getAllProductCustomer() {
        return  transferDataProduct_ProductDto(productRepository.getAllProducts());
    }

    @Override
    public List<ProductDto> listViewProducts() {
        return transferDataProduct_ProductDto(productRepository.listViewProduct());
    }

    //Liệt kê danh sách món có cùng category với món đang có
    @Override
    public List<ProductDto> findByCategoryId(Long id) {
        return transferDataProduct_ProductDto(productRepository.findProductByCategoryId(id));
    }

    @Override
    public List<ProductDto> findAllByCategory(String category) {
        return transferDataProduct_ProductDto(productRepository.findAllByCategory(category));
    }

    @Override
    public List<ProductDto> filterHighProducts() {
        return transferDataProduct_ProductDto(productRepository.filterHighProducts());
    }

    @Override
    public List<ProductDto> filterLowerProducts() {
        return transferDataProduct_ProductDto(productRepository.filterLowerProducts());
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        return transferDataProduct_ProductDto(productRepository.searchProductsList(keyword));
    }





}
