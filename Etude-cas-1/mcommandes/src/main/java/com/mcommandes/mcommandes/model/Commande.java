package com.mcommandes.mcommandes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    @Id
    @GeneratedValue
    private int id;
    private String description;
    private int quantite;
    private Date date;
    private Double montant;

    @Override
    public String toString(){
        return  "Commande{" + "id=" + id  + '\'' + ", description='" + description + '\'' + ",quantité='" + quantite + '\'' + ", date=" + date +'\'' + ", montant=" + montant+ '}';
    }
}
