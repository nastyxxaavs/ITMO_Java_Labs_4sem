package org.example.labthree.controllerLayer;


import org.example.labthree.dataAccessLayer.entities.cat.CatBase;
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

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
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
        if (!userService.isCurrentUserEquals(username)){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
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
        OwnerDto owner = ownerService.findOwnerByName(username);
        if (!userService.isCurrentUserEquals(username) && !isUserTheAdmin) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (name != null || dateOfBirth != null || species != null || color != null) {
            var param = new CatFinderDto(name, dateOfBirth, species, color);
            if (isUserTheAdmin) {
                final List<CatDto> cats = catService.findCatsByParam(param);
                return cats != null
                        ? new ResponseEntity<>(cats, HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            final List<UUID> catBases = owner.getCats();
            List<CatDto> cats = new ArrayList<CatDto>();
            for (UUID each : catBases) {
                CatDto catDto = catService.findCat(each);
                final List<CatDto> cat = catService.findCatsByParam(param);
                for(CatDto cat1 : cat){
                    if (cat1.getId().equals(catDto.getId())){
                        cats.add(cat1);
                        return new ResponseEntity<>(cats, HttpStatus.OK);
                    }
                    //return new ResponseEntity<>(cats, HttpStatus.NOT_FOUND);
                }
            }
            //final List<CatDto> cat = catService.findCatsByParam(param);//////////////////////дописать!!!!!!
            return new ResponseEntity<>(cats, HttpStatus.NOT_FOUND);
        }
        else {
            if (isUserTheAdmin) {
                final List<CatDto> cats = catService.findAll();
                return cats != null && !cats.isEmpty()
                        ? new ResponseEntity<>(cats, HttpStatus.OK)
                        : new ResponseEntity<>(cats, HttpStatus.NO_CONTENT);
            }
            final List<UUID> catBases = owner.getCats();
            List<CatDto> cats = new ArrayList<CatDto>();
            for (UUID each : catBases) {
                CatDto catDto = catService.findCat(each);
                cats.add(catDto);
            }

            return cats != null && !cats.isEmpty()
                    ? new ResponseEntity<>(cats, HttpStatus.OK)
                    : new ResponseEntity<>(cats, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/cats/{username}/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<CatDto> read(@PathVariable(name = "username") String username, @PathVariable(name = "id") UUID id) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!catService.IsItCurrentCatOwner(username, id) && !isUserTheAdmin) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        final CatDto cat = catService.findCat(id);
        return cat != null
                ? new ResponseEntity<>(cat, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/cats/{username}/{id}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable(name = "username") String username, @PathVariable(name = "id") UUID id, @RequestBody CatDto cat) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!catService.IsItCurrentCatOwner(username, id) && !isUserTheAdmin) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        final boolean updated = catService.update(cat, id);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/cats/{username}/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable(name = "username") String username, @PathVariable(name = "id") UUID id) {
        boolean isUserTheAdmin = userService.getUserByUsername(username).getRoles().stream().allMatch(roleBase -> roleBase.getName().equals("ROLE_ADMIN"));
        if (!catService.IsItCurrentCatOwner(username, id) && !isUserTheAdmin) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        final boolean deleted = catService.delete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
