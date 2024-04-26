package org.example.labthree.controllerLayer;


import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerFinderDto;
import org.example.labthree.serviceLayer.owner.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService currentOwnerService) {
        this.ownerService = currentOwnerService;
    }

    @PostMapping(value = "/owners", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody OwnerDto owner) {
        ownerService.saveOwner(owner);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/owners")
    public ResponseEntity<List<OwnerDto>> read(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "dateOfBirth", required = false) LocalDate dateOfBirth
    ) {
        if (name != null || dateOfBirth != null) {
            var param = new OwnerFinderDto(name, dateOfBirth);
            final List<OwnerDto> owner = ownerService.findOwnersByParam(param);

            return owner != null
                    ? new ResponseEntity<>(owner, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final List<OwnerDto> owners = ownerService.findAllOwners();

            return owners != null && !owners.isEmpty()
                    ? new ResponseEntity<>(owners, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/owners/{id}")
    public ResponseEntity<OwnerDto> read(@PathVariable(name = "id") UUID id) {
        final OwnerDto owner = ownerService.findOwner(id);

        return owner != null
                ? new ResponseEntity<>(owner, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/owners/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody OwnerDto owner) {
        final boolean updated = ownerService.updateOwner(owner, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/owners/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = ownerService.deleteOwner(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
