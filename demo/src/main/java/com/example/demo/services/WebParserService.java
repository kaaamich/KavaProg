package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;

@Service
public class WebParserService {
    private String pageUrl;
    private List<Product> products;

    public WebParserService(String pageUrl) {
        this.pageUrl = pageUrl;
        products = new ArrayList<>();
    }

    public List<Product> parsePage() {

        try {
            Document doc = Jsoup.connect(pageUrl).get();

            Elements items = doc.select(".card__info");

            for (Element item : items) {
                buildProduct(buildCode(item), item.select("h2 a").text(), buildDescription(item),
                        item.select("h2 a").attr("href"), buildPrice(item));
            }

            return products;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void buildProduct(String id, String title, String description, String link, float price) {
        Product prod = new Product();

        prod.setTitle(title);
        prod.setId(id);
        prod.setAuthor(link);
        prod.setPrice(price);
        prod.setDescription(description);

        products.add(prod);
    }

    private String buildDescription(Element item) {
        Elements points = item.select(".card__item-characteristics");
        StringBuilder descriptionBuilder = new StringBuilder();

        for (Element point : points) {
            descriptionBuilder.append(point.select("dt").text())
                    .append(": ")
                    .append(point.select("dd").text())
                    .append("; ");
        }

        return descriptionBuilder.toString();
    }

    private String buildCode(Element item) {
        String code = null;

        try {
            code = Jsoup.connect(item.select("h2 a").attr("href"))
                    .get()
                    .select("._StMF")
                    .text().substring(4).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return code;
    }

    private float buildPrice(Element item) {
        String price = item.select("data").first().attr("value");

        return Float.parseFloat(price);
    }
}
