package com.mobit.mobit.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.mobit.mobit.entity.Address;
import com.mobit.mobit.entity.Contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactDTO {

	private Long id;

	private String name;
	
	private String lastName;
	
	private String cpf;
	
	private String email;

	@Builder.Default
	private List<AddressDTO> addresses = new ArrayList<>();
	
	public static ContactDTO transform(Contact contact) {
		ContactDTO contactDTO =  ContactDTO.builder()
				.cpf(contact.getCpf())
				.email(contact.getEmail())
				.lastName(contact.getLastName())
				.id(contact.getId())
				.name(contact.getName())
				.build();
		
		contactDTO.setAddresses(contact.getAddresses().stream()
				.map(a -> AddressDTO.transform(a)).collect(Collectors.toList()));
		
		return contactDTO;
	}
}
