package com.mobit.mobit.entity;
//Logradouro, Bairro, UF, Cidade, CEP e tipo de endereço {Trabalho ou Residência})

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobit.mobit.dto.AddressDTO;
import com.mobit.mobit.enums.AddressType;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Address {
      
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ApiModelProperty(hidden=true)
	private String street;
	
	@ApiModelProperty(hidden=true)
	private String district;
	
	@ApiModelProperty(hidden=true)
	private String uf;
	
	@ApiModelProperty(hidden=true)
	private String city;
	
	@ApiModelProperty(hidden=true)
	private String zipcode;
	
	@ApiModelProperty(hidden=true)
	@Enumerated(EnumType.STRING)
	private AddressType type;
	
	@ApiModelProperty(hidden=true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private Contact contact;
	
	public static Address transform(AddressDTO address) {
		return Address.builder()
				.id(address.getId())
				.street(address.getStreet())
				.district(address.getDistrict())
				.uf(address.getUf())
				.city(address.getCity())
				.zipcode(address.getZipcode())
				.type(address.getType())
				.build();
	}
}
