package com.mobit.mobit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mobit.mobit.dto.AddressDTO;
import com.mobit.mobit.entity.Address;
import com.mobit.mobit.exception.NotFoundException;
import com.mobit.mobit.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;
	private final ContactService contactService;

	public List<AddressDTO> findAllByContactId(Long idContact) {
		if(contactService.existsById(idContact)) {
			List<Address> addresses = addressRepository.findAllByContactId(idContact);
			return addresses.stream().map(a -> AddressDTO.transform(a)).collect(Collectors.toList());
		}
		throw new NotFoundException("not.exists.contact.by.id");
	}
}
