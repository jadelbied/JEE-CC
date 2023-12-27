package com.mcommandes.mcommandes.controller;

import com.mcommandes.mcommandes.configurations.ApplicationPropertiesConfiguration;
import com.mcommandes.mcommandes.dao.CommandeDao;
import com.mcommandes.mcommandes.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController implements HealthIndicator {
    @Autowired
    CommandeDao commandeDao;
    @Autowired
    ApplicationPropertiesConfiguration appProperties;
    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Commandes")
    public List<Commande> listeDesCommandes() {
        System.out.println(" ********* ProductController listeDesCommandes() ");
        List<Commande> commandes = commandeDao.findAll();
        if (commandes.isEmpty()){
            System.out.println("Aucune commande n'est disponible.");
            return null;}
        else{
            int startIndex = Math.max(commandes.size() - appProperties.getLastCommandes(), 0);
            return commandes.subList(startIndex, commandes.size());
        }
    }
    @GetMapping(value = "/Commandes/{id}")
    public Optional<Commande> recupererUneCommande(@PathVariable int id) {
        System.out.println(" ********* ProductController recupererUneCommande(@PathVariable int id) ");
        Optional<Commande> commande = commandeDao.findById(id);
        if (!commande.isPresent()) {
            System.out.println("La commande correspondante à l'id " + id + " n'existe pas");
        }
        return commande;
    }
    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Commande> commandes = commandeDao.findAll();
        if (commandes.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }

}
