package com.mcommerce.hystrix.controler;

import java.util.Date;
import java.util.Optional;

import com.mcommerce.hystrix.model.Commande;
import com.mcommerce.hystrix.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableCircuitBreaker 
@Configuration 
@EnableHystrixDashboard
@RestController
public class CommandeController {
    @Autowired
    private CommandeService commandeService;

    @GetMapping("/myMessage")
    
    @HystrixCommand(fallbackMethod = "myHistrixbuildFallbackMessage",
    		commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")},
    		threadPoolKey = "messageThreadPool") 
    
//    @HystrixCommand(fallbackMethod = "myHistrixbuildFallbackMessage",
//	commandProperties ={@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")}) 
//    
    public String getMessage() throws InterruptedException { 
    	System.out.println("Message from CommandeController.getMessage(): Begin To sleep for 3 scondes ");
    	Thread.sleep(3000);
    	return "Message from CommandeController.getMessage(): End from sleep for 3 scondes ";
    	} 
 
    private String myHistrixbuildFallbackMessage() { 
    	return "Message from myHistrixbuildFallbackMessage() : Hystrix Fallback message ( after timeout : 1 second )"; 
    	} 

    /**
    * Read - Get all employees
    * @return - An Iterable object of Employee full filled
    */
    @GetMapping("/Commandes")
    public Iterable<Commande> getCommandes() {
        return commandeService.getCommandes();
    }
    
    /**
	 * Delete - Delete an employee
	 * @param id - The id of the employee to delete
	 */
	@DeleteMapping("/Commandes/{id}")
	public void deleteCommande(@PathVariable("id") final Integer id) {
		commandeService.deleteCommande(id);
	}
	
	/**
	 * Create - Add a new employee
	 * @param commande An object employee
	 * @return The employee object saved
	 */
	@PostMapping("/Commandes")
	public Commande createCommande(@RequestBody Commande commande) {
		return commandeService.saveCommande(commande);
	}
	
	/**
	 * Read - Get one employee 
	 * @param id The id of the employee
	 * @return An Employee object full filled
	 */
	@GetMapping("/Commandes/{id}")
	public Commande getCommande(@PathVariable("id") final Integer id) {
		Optional<Commande> commande = commandeService.getCommande(id);
		if(commande.isPresent()) {
			return commande.get();
		} else {
			return null;
		}
	}
	/**
	 * Update - Update an existing employee
	 * @param id - The id of the employee to update
	 * @param commande - The employee object updated
	 * @return
	 */
	@PutMapping("/Commandes/{id}")
	public Commande updateEmployee(@PathVariable("id") final Integer id, @RequestBody Commande commande) {
		Optional<Commande> e = commandeService.getCommande(id);
		if(e.isPresent()) {
			Commande currentCommande = e.get();
			
			String description = commande.getDescription();
			if(description != null) {
				currentCommande.setDescription(description);
			}
			Integer quantite = commande.getQuantite();
			if(quantite != null) {
				currentCommande.setQuantite(quantite);;
			}
			Date date = commande.getDate();
			if(date != null) {
				currentCommande.setDate(date);
			}
			Double montant = commande.getMontant();
			if(montant != null) {
				currentCommande.setMontant(montant);;
			}
			Integer produit_id = commande.getProduit_id();
			if(produit_id != null) {
				currentCommande.setProduit_id(produit_id);;
			}
			commandeService.saveCommande(currentCommande);
			return currentCommande;
		} else {
			return null;
		}
	}

}