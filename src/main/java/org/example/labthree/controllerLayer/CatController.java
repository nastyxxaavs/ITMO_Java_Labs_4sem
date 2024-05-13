package org.example.labthree.controllerLayer;


import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.cat.CatFinderDto;

import org.example.labthree.dataAccessLayer.entities.owner.OwnerBase;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.dataAccessLayer.models.CatColors;
import org.example.labthree.serviceLayer.cat.CatService;
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
//@RequestMapping(value = "/cats")
public class CatController {
    private final CatService catService;
    private final UserService userService;
    private final OwnerService ownerService;

    @Autowired
    public CatController(CatService currentCatService, UserService currentUserService, OwnerService currentOwnerService) {
        this.catService = currentCatService;
        this.userService = currentUserService;
        this.ownerService = currentOwnerService;
    }
    @PostMapping(value = "/cats/{username}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> save(@PathVariable(name = "username") String username, @RequestBody CatDto cat) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(username) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        catService.saveCat(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value="/cats/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<List<CatDto>> read(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "dateOfBirth", required = false) LocalDate dateOfBirth,
        @RequestParam(name = "species", required = false) String species,
        @RequestParam(name = "color", required = false) CatColors color,
        @PathVariable(name = "username") String username
    ) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(username) && !isUserTheAdmin) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (name != null || dateOfBirth != null || species != null || color != null) {
            var param = new CatFinderDto(name, dateOfBirth, species, color);
            final List<CatDto> cat = catService.findCatsByParam(param);

            return cat != null
                    ? new ResponseEntity<>(cat, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final List<CatDto> cats = catService.findAll();

            return cats != null && !cats.isEmpty()
                    ? new ResponseEntity<>(cats, HttpStatus.OK)
                    : new ResponseEntity<>(cats, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/cats/{username}/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CatDto> read(@PathVariable(name = "username") String username, @PathVariable(name = "id") UUID id) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(username) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OwnerDto owner = ownerService.getOwnerDtoByUsername(username);
        if (owner.getId().equals(catService.findOwnerById(id).getId())){
            final CatDto cat = catService.findCat(id);
            return cat != null
                    ? new ResponseEntity<>(cat, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }



        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping(value = "/cats/{username}/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable(name = "username") String username, @PathVariable(name = "id") UUID id, @RequestBody CatDto cat) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(username) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final boolean updated = catService.update(cat, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/cats/{username}/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable(name = "username") String username, @PathVariable(name = "id") UUID id) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!userService.isCurrentUserEquals(username) && !isUserTheAdmin){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        final boolean deleted = catService.delete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
