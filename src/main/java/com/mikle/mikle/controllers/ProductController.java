package com.mikle.mikle.controllers;

import com.mikle.mikle.models.Products;
import com.mikle.mikle.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Principal principal, Model model) { //required = false - значит данный товар не обязателен
        model.addAttribute("products", productService.listProducts(title));// при открытии корня сайта (его запуск), передаем ему лист с продуктами
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Products products = productService.getProductById(id);
        model.addAttribute("product", products);
        model.addAttribute("images", products.getImages());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Products product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3); // сохраняем продукт и фотографии в базу данных
        return "redirect:/";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) { // показываем, что мы берем эту переменную из контекста, который указан в PostMapping, с помощью PathVariable
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
