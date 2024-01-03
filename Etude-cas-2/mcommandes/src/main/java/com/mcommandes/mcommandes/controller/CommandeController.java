package com.mcommandes.mcommandes.controller;

import com.mcommandes.mcommandes.configurations.ApplicationPropertiesConfiguration;
import com.mcommandes.mcommandes.dao.CommandeDao;
import com.mcommandes.mcommandes.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(" ********* CommandeController listeDesCommandes() ");
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
        System.out.println(" ********* CommandeController recupererUneCommande(@PathVariable int id) ");
        Optional<Commande> commande = commandeDao.findById(id);
        if (!commande.isPresent()) {
            System.out.println("La commande correspondante à l'id " + id + " n'existe pas");
        }
        return commande;
    }
    @PostMapping(value = "/Commandes")
    public ResponseEntity<Commande> createCommande(@RequestBody Commande newCommande) {
        System.out.println(" ********* CommandeController createCommande() ");
        Commande createdCommande = commandeDao.save(newCommande);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/Commandes/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable int id) {
        System.out.println(" ********* CommandeController deleteCommande() ");
        Optional<Commande> existingCommande = commandeDao.findById(id);
        if (existingCommande.isPresent()) {
            commandeDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
