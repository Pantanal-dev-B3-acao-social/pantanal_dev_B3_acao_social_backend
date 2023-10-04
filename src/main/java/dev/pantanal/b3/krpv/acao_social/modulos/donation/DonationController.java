package dev.pantanal.b3.krpv.acao_social.modulos.donation;

import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationService;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.response.DonationResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.response.DonationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationController.ROUTE_DONATION;


@RestController
@RequestMapping(ROUTE_DONATION)

public class DonationController {

    @Autowired
    private DonationService service;
    public static final String ROUTE_DONATION = "/v1/donation";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets Donations", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<DonationEntity> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid DonationParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<DonationEntity> response = service.findAll(paging, request);
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one Donation", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<DonationResponseDto> findOne(@PathVariable UUID id) {
        DonationEntity entity = service.findById(id);
        DonationResponseDto response = new DonationResponseDto(
                entity.getDonation_date(),
                entity.getValue_money(),
                entity.getMotivation()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates an Donation", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Donation succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Donation not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating donation"),
    })
    public ResponseEntity<DonationResponseDto> create(@RequestBody @Valid DonationCreateDto request) {
        DonationEntity entity = service.create(request);
        DonationResponseDto response = new DonationResponseDto(
                entity.getDonation_date(),
                entity.getValue_money(),
                entity.getMotivation()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Donation", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Donation no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating donation"),
    })
    public ResponseEntity<DonationResponseDto> update(@PathVariable UUID id, @Valid @RequestBody DonationUpdateDto request) {
        DonationEntity entity = service.update(id, request);
        DonationResponseDto response = new DonationResponseDto(
                entity.getDonation_date(),
                entity.getValue_money(),
                entity.getMotivation()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an Donation", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Donation not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding donation"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
