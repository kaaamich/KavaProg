# Застосунок Spring

<big>Artem Shcherbatiuk, 122-20-4</big>

## Опис додатку

**Основа** 

    За основу для репозиторію даних було взято список кавоварок з магазину епіцентр

**Інтерфейс** 
    package com.example.demo.repository;

    import com.example.demo.entity.Product;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    @Repository
    public interface ICoffeeRepository extends JpaRepository<Product, String> {
    }

**Демо**

    package com.example.demo;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.ComponentScan;

    @SpringBootApplication
    @ComponentScan(basePackages = { "com.example.demo.controllers", "com.example.demo.configs" })
    public class DemoApplication {

        public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);
        }

    }


**Post запит**

![Post method](Screen\POST_POSTMAN.png)

**Get запит**

![Get method](Screen\GET.png)

**Результат запита**

![Get method](Screen\RESULT.png)



**Реалізація самозаповнення таблиці**

    function fetchData() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    var data = JSON.parse(this.responseText);
                    populateTable(data);
                }
            };
            xhttp.open("GET", "http://localhost:8080/products/", true);
            xhttp.send();
        }

    function populateTable(data) {
        var table = document.getElementById("productTable");
        for (var i = 0; i < data.length; i++) {
            var row = table.insertRow();
            var idCell = row.insertCell(0);
            var titleCell = row.insertCell(1);
            var linkCell = row.insertCell(2);
            var descriptionCell = row.insertCell(3);
            var priceCell = row.insertCell(4);
            idCell.innerHTML = data[i].id;
            titleCell.innerHTML = data[i].title;
            linkCell.innerHTML = '<a href="' + data[i].link + '">Посилання</a>';
            descriptionCell.innerHTML = data[i].description;
            priceCell.innerHTML = data[i].price;
        }
    }

**Таблиця із кнопкою для експорту**

![Table](Screen\TABLE.png)


**Результат натискання кнопки**

![Table](Screen\EXPORT.png)

