package br.com.carlos.cashMachine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.Data;

/**
 * This class represents a customer entity
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
@Data
@Entity
@Table(name="CUSTOMERS")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String document;

	@Email
	private String email;
	
	@NotNull
	private Double balance;
}
