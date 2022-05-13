package com.toanlv.springmvc.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "tblProduct") // ten cua bang tao trong database sql, trong memory thi khong can
public class Product {
    @Id
    @SequenceGenerator( // Quy tac de tao thu tu
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1  // moi lan tang se tang len 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )// truong ID tu sinh ra
    private Long id;
    // validata=constrain // cac rang buoc cua cac cot
    @Column(nullable = false, unique = true, length = 300) // unique: ten khong duoc trung nhau
    private String productName;
    private int year;
    private String url;
    private Double price;

    public Product() {
    }
   // calculated field: transient: trong SQL thuong co truong nay, trung nay duoc tinh toan tu cac truongw khac
    // trainsient la truong khong duoc luu trong co so du lieu
    @Transient
    private int age; // age duoc tinh tu year

    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR)-this.year;
    }


    public Product(String productName, int year, String url, Double price) {
        this.productName = productName;
        this.year = year;
        this.url = url;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + year +
                ", url='" + url + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year && age == product.age && Objects.equals(id, product.id) && productName.equals(product.productName) && Objects.equals(url, product.url) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, year, url, price, age);
    }
}
