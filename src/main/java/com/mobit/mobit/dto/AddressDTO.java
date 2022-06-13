package com.mobit.mobit.dto;

import com.mobit.mobit.entity.Address;
import com.mobit.mobit.enums.AddressType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

	private Long id;
	private String street;
	private String district;
	private String uf;
	private String city;
	private String zipcode;
	private AddressType type;
	
	public static AddressDTO transform(Address address) {
		return AddressDTO.builder()
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
