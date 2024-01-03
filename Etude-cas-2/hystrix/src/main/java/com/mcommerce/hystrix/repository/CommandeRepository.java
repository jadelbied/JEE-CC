package com.mcommerce.hystrix.repository;

import com.mcommerce.hystrix.model.Commande;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



//@Repository est une annotation Spring pour indiquer que la classe est un bean, 
//et que son rôle est de communiquer avec une source de données (en l'occurrence la base de données).
//@Repository est une spécialisation de @Component. 
//Tout comme @Component, elle permet de déclarer auprès de Spring qu’une classe est un bean à exploiter. 

@Repository
public interface CommandeRepository extends CrudRepository<Commande, Integer> {

}