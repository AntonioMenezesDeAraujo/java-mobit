package com.mobit.mobit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobit.mobit.docs.ContactDoc;
import com.mobit.mobit.dto.AddressDTO;
import com.mobit.mobit.dto.ContactDTO;
import com.mobit.mobit.entity.Contact;
import com.mobit.mobit.service.AddressService;
import com.mobit.mobit.service.ContactService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContactController implements ContactDoc {

	private final ContactService contactService;
	private final AddressService addressService;

	@PostMapping
	public ResponseEntity<ContactDTO> save(@RequestBody final ContactDTO contactDTO) {
		return ResponseEntity.ok().body(contactService.save(Contact.transform(contactDTO)));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Contact> delete(@PathVariable(value = "id") Long id) {
		this.contactService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ContactDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok().body(contactService.findById(id));
	}

	@GetMapping(value = "/{id}/enderecos")
	public ResponseEntity<List<AddressDTO>> listAllByContactId(@PathVariable(value = "id") Long idContact) {
		return ResponseEntity.ok().body(addressService.findAllByContactId(idContact));
	}

	@GetMapping
	public ResponseEntity<List<ContactDTO>> findAll(Contact contact,
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok().body(contactService.findAll(contact, pageable));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ContactDTO> update(@PathVariable(value = "id") Long id, @RequestBody Contact contact) {
		Optional<ContactDTO> contactOptionl = contactService.update(id, contact);
		if (contactOptionl.isPresent()) {
			return ResponseEntity.ok(contactOptionl.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
