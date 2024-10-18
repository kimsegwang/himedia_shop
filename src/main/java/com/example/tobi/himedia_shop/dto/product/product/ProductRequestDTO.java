package com.example.tobi.himedia_shop.dto.product.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
public class ProductRequestDTO {
    private String productUserId;
    private String productName;
    private String productCategory;
    private String productContent;
    private MultipartFile productImage;
    private int productTemperature;
    private String productPrecipitation;
    private int productStock;
    private int productPrice;

}
