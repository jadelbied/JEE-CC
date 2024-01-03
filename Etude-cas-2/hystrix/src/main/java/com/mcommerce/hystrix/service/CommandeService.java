package com.mcommerce.hystrix.service;

import java.util.Optional;

import com.mcommerce.hystrix.model.Commande;
import com.mcommerce.hystrix.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import lombok.Data;

//@Service : tout comme l’annotation @Repository, c’est une spécialisation de @Component. 
//Son rôle est donc le même, mais son nom a une valeur sémantique pour ceux qui lisent le code.

@Data
@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public Optional<Commande> getCommande(final Integer id) {
        return commandeRepository.findById(id);
    }

    public Iterable<Commande> getCommandes() {
        return commandeRepository.findAll();
    }

    public void deleteCommande(final Integer id) {
        commandeRepository.deleteById(id);
    }

    public Commande saveCommande(Commande commande) {
        Commande savedCommande = commandeRepository.save(commande);
        return savedCommande;
    }

}