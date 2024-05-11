package org.example.labthree.controllerLayer;


import org.example.labthree.dataAccessLayer.dao.OwnerDao;
import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerFinderDto;
import org.example.labthree.serviceLayer.owner.OwnerService;
import org.example.labthree.serviceLayer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class OwnerController {
    private final OwnerService ownerService;
    private final UserService userService;
    private final OwnerDao ownerRepository;

    @Autowired
    public OwnerController(OwnerService currentOwnerService, UserService currentUserService, OwnerDao ownerDao) {
        this.ownerService = currentOwnerService;
        this.userService = currentUserService;
        this.ownerRepository = ownerDao;
    }

    @PostMapping(value = "/owners/{username}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> save(@RequestBody OwnerDto owner, @PathVariable("username") String userName) {
        //ownerService.saveOwner(owner);
        ownerService.addOrUpdateOwnerWithDtoByUsername(owner, userName);

        boolean isUserTheAdmin = userService.getUserByUserName(userName).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(userName) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/owners/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<OwnerDto>> read(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "dateOfBirth", required = false) LocalDate dateOfBirth,
            @PathVariable(name = "username") String userName
    ) {
        boolean isUserTheAdmin = userService.getUserByUserName(userName).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(userName) && !isUserTheAdmin) {
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
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /*@GetMapping(value = "/owners/{id}")
    public ResponseEntity<OwnerDto> read(@PathVariable(name = "id") UUID id) {
        final OwnerDto owner = ownerService.findOwner(id);

        return owner != null
                ? new ResponseEntity<>(owner, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

    @GetMapping(value = "/owners/details/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<OwnerDto> read(@PathVariable(name = "username") String username) {
        final OwnerDto owner = ownerService.getOwnerDtoByUsername(username);

        return owner != null
                ? new ResponseEntity<>(owner, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/owners/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable(name = "username") String userName, @RequestBody OwnerDto owner) {
        //final boolean updated = ownerService.updateOwner(owner, id);
        boolean isUserTheAdmin = userService.getUserByUserName(userName).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(userName) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ownerService.addOrUpdateOwnerWithDtoByUsername(owner, userName);
        final boolean updated = ownerRepository.existsById(owner.getId());
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/owners/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable(name = "username") String userName) {
        //final boolean deleted = ownerService.deleteOwner(id);
        boolean isUserTheAdmin = userService.getUserByUserName(userName).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(userName) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OwnerDto owner = ownerService.getOwnerDtoByUsername(userName);
        ownerService.deleteOwner(owner.getId());
        final boolean deleted = ownerRepository.existsById(owner.getId());
        return deleted
                ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED)
                : new ResponseEntity<>(HttpStatus.OK);
    }

}
