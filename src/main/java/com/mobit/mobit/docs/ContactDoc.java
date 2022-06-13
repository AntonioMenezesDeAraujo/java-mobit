package com.mobit.mobit.docs;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mobit.mobit.dto.AddressDTO;
import com.mobit.mobit.dto.ContactDTO;
import com.mobit.mobit.entity.Contact;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ContactDoc {

	@Operation(summary = "Salva o contato e retorna os dados dele")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class)))), 
		
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
			
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
		}
	)
	ResponseEntity<ContactDTO> save(@RequestBody final ContactDTO contact);
	
	
	@Operation(summary = "Retorna o contato com base no Id passado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful operation - No Content", 
					content = @Content(array = @ArraySchema(schema = @Schema()))), 
		
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
			
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
		}
	)
	ResponseEntity<Contact> delete(@PathVariable(value="id") Long id);
	
	
	@Operation(summary = "Deleta contato com base no Id passado como parâmetro")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful operation - No Content", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class)))), 
		
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
			
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
		}
	)
	ResponseEntity<ContactDTO> findById(@PathVariable(value="id") Long id);
	
	
	
	@Operation(summary = "Retorna os endereços com base no id do contato passado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful operation - No Content", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class)))), 
		
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
			
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
		}
	)
	ResponseEntity<List<AddressDTO>> listAllByContactId(@PathVariable(value="id") Long idContact);
	
	
	@Operation(summary = "Retorna os usuários com base nos filtros passados e pagina conforme parâmetros passados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful operation - No Content", 
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class)))), 
		
			@ApiResponse(responseCode = "400", description = "Bad Request", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
			
			@ApiResponse(responseCode = "404", description = "Not Found", 
			content = @Content(array = @ArraySchema(schema = @Schema()))),
		}
	)
	ResponseEntity<List<ContactDTO>> findAll(Contact contact, @PageableDefault(page=0, size=10, sort="name", direction=Direction.ASC) Pageable pageable);
}
