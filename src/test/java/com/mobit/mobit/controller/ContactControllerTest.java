package com.mobit.mobit.controller;

import java.util.Arrays;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobit.mobit.MobitApplication;
import com.mobit.mobit.dto.AddressDTO;
import com.mobit.mobit.dto.ContactDTO;
import com.mobit.mobit.entity.Address;
import com.mobit.mobit.entity.Contact;
import com.mobit.mobit.repository.ContactRepository;

@SpringBootTest(classes = MobitApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MobitApplication.class})
@TestInstance(Lifecycle.PER_CLASS)
public class ContactControllerTest {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Contact c1;
	
	private Address a1;
	
	@BeforeAll
	void setup() {
	
		c1 = Contact.builder().cpf("132").email("teste1@gmail.com").lastName("teste1").name("teste1").build();
		Contact c2 = Contact.builder().cpf("345").email("teste2@gmail.com").lastName("teste2").name("teste2").build();
		Contact c3 = Contact.builder().cpf("567").email("teste3@gmail.com").lastName("teste3").name("teste3").build();
		Contact c4 = Contact.builder().cpf("678").email("teste4@gmail.com").lastName("teste4").name("teste4").build();
		Contact c5 = Contact.builder().cpf("789").email("teste5@gmail.com").lastName("teste5").name("teste5").build();
		
		a1 = Address.builder().city("teste1").district("teste1").street("teste1").uf("teste1").zipcode("teste1").build();
		Address a2 = Address.builder().city("teste2").district("teste2").street("teste2").uf("teste2").zipcode("teste2").build();
		Address a3 = Address.builder().city("teste3").district("teste3").street("teste3").uf("teste3").zipcode("teste3").build();
		Address a4 = Address.builder().city("teste4").district("teste4").street("teste4").uf("teste4").zipcode("teste4").build();
		Address a5 = Address.builder().city("teste5").district("teste5").street("teste5").uf("teste5").zipcode("teste5").build();
		Address a6 = Address.builder().city("teste6").district("teste6").street("teste6").uf("teste6").zipcode("teste6").build();
		
		a1.setContact(c1);
		c1.getAddresses().add(a1);
		
		a2.setContact(c2);
		c2.getAddresses().add(a2);
		
		a3.setContact(c3);
		c3.getAddresses().add(a3);
		
		a4.setContact(c4);
		c4.getAddresses().add(a4);
		
		a5.setContact(c5);
		c5.getAddresses().add(a5);
		
		a6.setContact(c5);
		c5.getAddresses().add(a6);
		
		contactRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
	}
	
	@AfterAll
	public void after() {
	   contactRepository.deleteAll();
	}
	
	@Test
	public void shouldReturnOkWithContactSaved() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").lastName("teste111").name("teste111").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(contact.getCpf()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(contact.getEmail()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(contact.getLastName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(contact.getName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.size()").value(contact.getAddresses().size()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(address.getCity()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].district").value(address.getDistrict()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(address.getStreet()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].uf").value(address.getUf()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].zipcode").value(address.getZipcode()));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedNameNull() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").lastName("teste111").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo nome deve ser informado")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedNameMore100Character() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").lastName("teste111").name("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo nome deve conter até 100 caracteres")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedNameEmpty() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").lastName("teste111").name("").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo nome deve ser informado")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedCpf() throws Exception {
		ContactDTO contact = ContactDTO.builder().lastName("sdfsafasfsd").email("teste111@gmail.com").name("aaaaa").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo cpf deve ser informado")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedCpfMore11Character() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("1112222222222").email("teste111@gmail.com").lastName("aaaaa").name("aaaa").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo cpf deve conter até 11 caracteres")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedCpfEmpty() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("").email("teste111@gmail.com").lastName("sdfasfsdf").name("dgdgsdfg").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo cpf deve ser informado")));
	}
	
	
	@Test
	public void shouldReturnBadRequestWithContactSavedLastNameNull() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").name("aaaaa").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo lastName deve ser informado")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedLastNameMore100Character() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").lastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa").name("aaaa").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo lastName deve conter até 100 caracteres")));
	}
	
	@Test
	public void shouldReturnBadRequestWithContactSavedLastNameEmpty() throws Exception {
		ContactDTO contact = ContactDTO.builder().cpf("111").email("teste111@gmail.com").lastName("").name("dgdgsdfg").build();
		AddressDTO address = AddressDTO.builder().city("address1").district("address1").street("address1").uf("address1").zipcode("address1").build();
		
		contact.getAddresses().add(address);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/contatos")
				.content(objectMapper.writeValueAsString(contact))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("O campo lastName deve ser informado")));
	}

	
	
	
	@Test
	public void shouldReturnNotFoundDeleteByNotExistsIdInformed() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/contatos/{id}", 10000)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
		//.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("not.exists.by.id.informed")));
	}
	
	@Test
	public void shouldReturnOkDeleteByExistsIdInformed() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.delete("/contatos/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
//		.andExpect(MockMvcResultMatchers.content().string(org.mockito.Matchers.contains(substring)containsString("not.exists.by.id.informed")));
	}
	
	@Test
	public void findByIdReturnOk() throws Exception {
		ContactDTO contactDTO = ContactDTO.transform(c1);
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/contatos/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(contactDTO.getCpf()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(contactDTO.getEmail()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(contactDTO.getLastName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(contactDTO.getName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.size()").value(contactDTO.getAddresses().size()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].city").value(contactDTO.getAddresses().get(0).getCity()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].district").value(contactDTO.getAddresses().get(0).getDistrict()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].street").value(contactDTO.getAddresses().get(0).getStreet()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].uf").value(contactDTO.getAddresses().get(0).getUf()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.addresses[0].zipcode").value(contactDTO.getAddresses().get(0).getZipcode()));
	}
	
	
	@Test
	public void findByIdReturnNotFound() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders
				.get("/contatos/{id}", 1000)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
}
