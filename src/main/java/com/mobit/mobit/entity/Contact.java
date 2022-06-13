package com.mobit.mobit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobit.mobit.dto.AddressDTO;
import com.mobit.mobit.dto.ContactDTO;

import io.swagger.annotations.ApiModelProperty;
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
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "O campo nome deve ser informado")
	@Size(max = 100, message = "O campo nome deve conter até 100 caracteres")
	@NotBlank(message = "O campo nome deve ser informado")
	private String name;

	@NotNull(message = "O campo lastName deve ser informado")
	@Size(max = 100, message = "O campo lastName deve conter até 100 caracteres")
	@NotBlank(message = "O campo lastName deve ser informado")
	private String lastName;

	@NotNull(message = "O campo cpf deve ser informado")
	@Size(max = 11, message = "O campo cpf deve conter até 11 caracteres")
	@NotBlank(message = "O campo cpf deve ser informado")
	private String cpf;

	@ApiModelProperty(hidden=true)
	@NotNull(message = "O campo email deve ser informado")
	@Size(max = 100, message = "O campo email deve conter até 100 caracteres")
	@NotBlank(message = "O campo email deve ser informado")
	private String email;

	
	@Builder.Default
	@ApiModelProperty(hidden=true)
	@OneToMany(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Address> addresses = new ArrayList<>();

	@Transient
	public Specification<Contact> toFilter() {
		return (root, query, builder) -> {
			List<Predicate> filters = new ArrayList<>();
			if (org.springframework.util.StringUtils.hasText(name)) {
				Path<String> fieldName = root.<String>get("name");
				Predicate filterName = builder.like(fieldName, "%" + name + "%");
				filters.add(filterName);
			}

			if (org.springframework.util.StringUtils.hasText(cpf)) {
				Path<String> fieldCpf = root.<String>get("cpf");
				Predicate filterCpf = builder.equal(fieldCpf, cpf);
				filters.add(filterCpf);
			}

			if (org.springframework.util.StringUtils.hasText(lastName)) {
				Path<String> fieldLastName = root.<String>get("lastName");
				Predicate filterLastName = builder.equal(fieldLastName, cpf);
				filters.add(filterLastName);
			}
			return builder.and(filters.toArray(new Predicate[0]));
		};
	}
	
	public static Contact transform(ContactDTO contactDTO) {
		Contact contact =  Contact.builder()
				.cpf(contactDTO.getCpf())
				.email(contactDTO.getEmail())
				.lastName(contactDTO.getLastName())
				.id(contactDTO.getId())
				.name(contactDTO.getName())
				.build();
		
		contact.setAddresses(contactDTO.getAddresses().stream()
				.map(a -> Address.transform(a)).collect(Collectors.toList()));
		
		contact.getAddresses().forEach(a -> a.setContact(contact));
		
		return contact;
	}
}
