package com.example.demo.controllers;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.ProductGamingPlatform;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    private static String filePath="src/main/resources/static/images/";

    /**
     * Обработва заявките към началната страница (front page) и предоставя данни за нея.
     * Този метод извлича текущия аутентикиран потребител чрез тяхната електронна поща,
     * изчислява общото количество на продуктите в тяхната поръчка и извлича всички продукти
     * предоставени от системата. След това добавя тези данни към модела, за да се визуализират
     * във визуалния интерфейс.
     *
     * @param model Моделът, който предава данните между контролера и визуалния интерфейс.
     * @param authentication Обектът за аутентикация, който предоставя информация за текущо аутентикирания потребител.
     * @return Името на HTML шаблона на началната страница, който трябва да бъде изобразен.
     */
    @GetMapping("/frontPage")
    public String frontPage(Model model, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        int totalQuantity = orderService.totalQuantity(user);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("role", user.getUserRole().toString());
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);

        model.addAttribute("gamingPlatform", "All");
        return "frontPage";
    }
    /**
     * Обработва заявки за показване само на компютърни игри на първа страница.
     * Филтрира списъка с всички продукти, за да включва само тези, които са компютърни игри.
     *
     * @param model Обектът на модела за предаване на атрибути към изгледа.
     * @param authentication Обектът за удостоверяване, който съдържа информация за текущо удостоверения потребител.
     * @return Името на изгледа за изобразяване, в този случай 'frontPage', с филтрирани продукти.
     */
    @GetMapping("/pc-games")
    public String pcGames(Model model, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        int totalQuantity = orderService.totalQuantity(user);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("role", user.getUserRole().toString());
        List<ProductDto> products = productService.getAllProducts().stream().filter(p->p.getGamingPlatform()== ProductGamingPlatform.PC).collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("gamingPlatform", "PC");
        return "frontPage";
    }
    /**
     * Обработва заявки за показване само на PlayStation игри на първа страница.
     * Филтрира списъка с всички продукти, за да включва само тези, които са игри за PlayStation.
     *
     * @param model Обектът на модела за предаване на атрибути към изгледа.
     * @param authentication Обектът за удостоверяване, който съдържа информация за текущо удостоверения потребител.
     * @return Името на изгледа за изобразяване, в този случай 'frontPage', с филтрирани продукти.
     */
    @GetMapping("/ps-games")
    public String psGames(Model model, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        int totalQuantity = orderService.totalQuantity(user);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("role", user.getUserRole().toString());
        List<ProductDto> products = productService.getAllProducts().stream().filter(p->p.getGamingPlatform()== ProductGamingPlatform.PS).collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("gamingPlatform", "PS");
        return "frontPage";
    }
    /**
     * Обработва заявки за показване само на Xbox игри на първа страница.
     * Филтрира списъка с всички продукти, за да включва само тези, които са Xbox игри.
     *
     * @param model Обектът на модела за предаване на атрибути към изгледа.
     * @param authentication Обектът за удостоверяване, който съдържа информация за текущо удостоверения потребител.
     * @return Името на изгледа за изобразяване, в този случай 'frontPage', с филтрирани продукти.
     */
    @GetMapping("/xbox-games")
    public String xboxGames(Model model, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        int totalQuantity = orderService.totalQuantity(user);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("role", user.getUserRole().toString());
        List<ProductDto> products = productService.getAllProducts().stream().filter(p->p.getGamingPlatform()== ProductGamingPlatform.xBox).collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("gamingPlatform", "xBox");
        return "frontPage";
    }
    /**
     * Обработва заявки за показване само на Nintendo игри на първа страница.
     * Филтрира списъка с всички продукти, за да включва само тези, които са игри на Nintendo.
     *
     * @param model Обектът на модела за предаване на атрибути към изгледа.
     * @param authentication Обектът за удостоверяване, който съдържа информация за текущо удостоверения потребител.
     * @return Името на изгледа за изобразяване, в този случай 'frontPage', с филтрирани продукти.
     */
    @GetMapping("/nintendo-games")
    public String nintendoGames(Model model, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        int totalQuantity = orderService.totalQuantity(user);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("role", user.getUserRole().toString());
        List<ProductDto> products = productService.getAllProducts().stream().filter(p->p.getGamingPlatform()== ProductGamingPlatform.Nintendo).collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("gamingPlatform", "Nintendo");
        return "frontPage";
    }
    /**
     * Показва формата за добавяне на продукти.
     * Създава празен обект ProductDto и го добавя към модела, като подготвя
     * формата за добавяне на нов продукт.
     *
     * @param model Моделът, който предава данни между контролера и изгледа.
     * @return Името на шаблона за добавяне на продукти.
     */
    @GetMapping("/addProducts")
    public String showAddProductsForm(Model model){
        ProductDto product = new ProductDto();
        model.addAttribute("product", product);
        return "addProducts";
    }
    /**
     * Обработва заявката за запазване на нов продукт.
     * Ако продуктът вече съществува, пренасочва към формата с грешка.
     * В противен случай, запазва продукта и пренасочва към формата с успех.
     *
     * @param productDto Обектът ProductDto, който съдържа информацията за продукта.
     * @param model Моделът, използван за предаване на данни между контролера и изгледа.
     * @param file Мултимедиен файл, който съдържа изображението на продукта.
     * @return Пренасочва към URL за добавяне на продукти със съответното съобщение за грешка или успех.
     * @throws IOException Ако има проблем при записване на файла.
     */
    @PostMapping("/addProducts/save")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto, Model model,
                             @RequestPart("image") MultipartFile file) throws IOException {
        Product existingProduct = productService.findByName(productDto.getName());

        String fileName = file.getOriginalFilename();
        byte[] fileBytes = file.getBytes();
        Path path = Paths.get(filePath, fileName);
        Files.write( path, fileBytes);

        productDto.setImages(fileName);
        if(existingProduct !=null) {
            return "redirect:/addProducts?error";
        }

        productService.addProduct(productDto);
        return "redirect:/addProducts?success";
    }
    /**
     * Обработва заявката за преглед на продукт по име.
     * Зарежда информация за продукта и го добавя към модела за изгледа, заедно
     * с информация за общото количество на поръчките на текущия потребител.
     *
     * @param productName Името на продукта, който се търси.
     * @param model Моделът, използван за предаване на данни.
     * @param authentication Обект за аутентикация, използван за идентифициране на потребителя.
     * @return Името на шаблона за изглед на продукта.
     */
    @PostMapping("/addProducts/view")
    public String view(@RequestParam("productName") String productName, Model model,Authentication authentication)  {
        Product product = productService.findByName(productName);
        model.addAttribute("game", product);

        int totalQuantity= orderService.totalQuantity(userService.findByEmail(authentication.getName()));
        model.addAttribute("totalQuantity", totalQuantity);
        
        List<ProductDto> products = productService.getAllProducts().stream().limit(3).collect(Collectors.toList());
        model.addAttribute("products", products);

        return "viewProduct";
    }

    /**
     * Отговаря на GET заявка за страницата на продуктите.
     * Извлича всички продукти чрез сервиса за продукти и добавя списък с тези продукти към модела,
     * за да могат да бъдат визуализирани във визуалния интерфейс.
     *
     * @param model Моделът, който предава данни между контролера и изгледа.
     * @return Името на шаблона за изглед, където продуктите могат да бъдат добавени или обновени.
     */
    @GetMapping("/products")
    public String products(Model model){
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "refillProduct";
    }

    /**
     * Обработва POST заявка за добавяне на количество към съществуващ продукт.
     * Намира продукта по подаден идентификационен номер и добавя указаното количество към наличността му.
     *
     * @param Id Идентификационният номер на продукта, който трябва да бъде обновен.
     * @param quantity Количеството, което трябва да бъде добавено към продукта.
     * @param model Моделът за предаване на данни към изгледа.
     * @return Пренасочва обратно към страницата с продукти след успешно изпълнение на операцията.
     */
    @PostMapping("/product/refill")
    public String refillProduct(@Valid @ModelAttribute("id") Long Id,@Valid @ModelAttribute("quantity") int quantity, Model model){
        Product product = productService.findById(Id);
        product.setQuantity(product.getQuantity()+quantity);
        productService.refillProduct(product);
        return "redirect:/products";

    }
    /**
     * Обработва POST заявка за изтриване на продукт по идентификационен номер.
     * Търси и изтрива продукта от базата данни и файловата система, ако продуктът съществува.
     *
     * @param Id Идентификационният номер на продукта, който трябва да бъде изтрит.
     * @param model Моделът за предаване на данни към изгледа.
     * @return Пренасочва обратно към страницата с продукти след успешно изпълнение на операцията.
     */
    @PostMapping("/product/delete")
    public String usersDelete(@Valid @ModelAttribute("id") Long Id, Model model){
        productService.deleteProduct(Id,filePath);
        return"redirect:/products";

    }

}
