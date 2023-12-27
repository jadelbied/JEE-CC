package com.mproduits.mproduits.controller;

import com.mproduits.mproduits.configurations.ApplicationPropertiesConfiguration;
import com.mproduits.mproduits.dao.ProductDao;
import com.mproduits.mproduits.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements HealthIndicator {
    @Autowired
    ProductDao productDao;
    @Autowired
    ApplicationPropertiesConfiguration appProperties;
    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits() {
        System.out.println(" ********* ProductController listeDesProduits() ");
        List<Product> products = productDao.findAll();
        if (products.isEmpty())
            System.out.println("Aucun produit n'est disponible à la vente");
                    List<Product> listeLimitee = products.subList(0,appProperties.getLimitDeProduits());
        return listeLimitee;
    }
    @GetMapping(value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) {
        System.out.println(" ********* ProductController recupererUnProduit(@PathVariable int id) ");
        Optional<Product> product = productDao.findById(id);
        if (!product.isPresent()) {
            System.out.println("Le produit correspondant à l'id " + id + " n'existe pas");
        }
        return product;
    }
    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Product> products = productDao.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

}
