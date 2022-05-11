package com.mikle.mikle.services;

import com.mikle.mikle.models.Image;
import com.mikle.mikle.models.Products;
import com.mikle.mikle.models.User;
import com.mikle.mikle.repositories.ProductRepository;
import com.mikle.mikle.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Products> listProducts(String title) {
        if (title != null) {
            return productRepository.findByTitle(title); // если название не равно нулю, то продолжаем поиск и возвращаем все характеристики
        }
        return productRepository.findAll();
    }

    public void saveProduct(Principal principal, Products product, MultipartFile file1,
                            MultipartFile file2, MultipartFile file3) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        if (file1.getSize() != 0) {
            Image image = toImageEntity(file1); // преобразовываем из файла в фотографию
            image.setPreviewImage(true); // первая фотография является главной
            product.addImageToProduct(image); //добавляем саму фотографию к товару
        }
        if (file2.getSize() != 0) {
            Image image = toImageEntity(file2); // преобразовываем из файла в фотографию
            product.addImageToProduct(image); //добавляем саму фотографию к товару
        }
        if (file3.getSize() != 0) {
            Image image = toImageEntity(file3); // преобразовываем из файла в фотографию
            product.addImageToProduct(image); //добавляем саму фотографию к товару
        }

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Products productFromDb = productRepository.save(product); // создаем товар, сохранеяем его в репозиторий
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId()); // получаем айди первой фотографии продукта
        productRepository.save(product); // обновляем товар и сохраняем его с обновленной главной фотографией
    }

    public User getUserByPrincipal(Principal principal) { // функция поиска юзера по email
        if (principal == null) {
            return new User();
        }
        return userRepository.findByEmail(principal.getName());
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Products getProductById(Long id) {
        return productRepository.findById(id).orElse(null); // если товар с таким id не найден, то вернется null, если найден, то товар вернется
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}
