package com.mobit.mobit.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobit.mobit.dto.ContactDTO;
import com.mobit.mobit.entity.Contact;
import com.mobit.mobit.exception.NotFoundException;
import com.mobit.mobit.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

	private final ContactRepository contactRepository;
	
	public ContactDTO save(Contact contact) {
		contact.getAddresses().forEach(address -> address.setContact(contact));
		Contact contactDB = this.contactRepository.save(contact);
		return ContactDTO.transform(contactDB);
	}
	
	public void delete(Long id) {
		if(contactRepository.existsById(id)) {
			contactRepository.deleteById(id);
		}
		throw new NotFoundException("not.exists.by.id.informed");
	}
	
	public ContactDTO findById(Long id) {
		Contact contact =  this.contactRepository.findById(id).orElseThrow(() -> new NotFoundException("Contact not found"));
		return ContactDTO.transform(contact);
	}
	
	public List<ContactDTO> findAll(Contact contact, Pageable pageable) {
		return this.contactRepository.findAll(contact.toFilter(), pageable).toList().stream().map(c -> ContactDTO.transform(c)).collect(Collectors.toList());
	}
	
	public Optional<ContactDTO> update(Long id, Contact contact) {
		Optional<Contact> optionalContactDB = contactRepository.findById(id);
		
		if(optionalContactDB.isPresent()) {
			Contact contactDB = optionalContactDB.get();
			
			contactDB.setCpf(contact.getCpf());
			contactDB.setEmail(contact.getEmail());
			contactDB.setLastName(contact.getLastName());
			contactDB.setName(contact.getName());
			
			this.contactRepository.save(contactDB);
		}
		
		return Optional.empty();
	}

	public boolean existsById(Long id) {
		return this.contactRepository.existsById(id);
	}
}
