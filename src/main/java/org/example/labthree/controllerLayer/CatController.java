package org.example.labthree.controllerLayer;


import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.cat.CatFinderDto;

import org.example.labthree.dataAccessLayer.models.CatColors;
import org.example.labthree.serviceLayer.cat.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
//@RequestMapping(value = "/cats")
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService currentCatService) {
        this.catService = currentCatService;
    }
    @PostMapping(value = "/cats", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody CatDto cat) {
        catService.saveCat(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value="/cats")
    public ResponseEntity<List<CatDto>> read(

        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "dateOfBirth", required = false) LocalDate dateOfBirth,
        @RequestParam(name = "species", required = false) String species,
        @RequestParam(name = "color", required = false) CatColors color
    ) {
            if (name != null || dateOfBirth != null || species != null || color != null) {
                var param = new CatFinderDto(name, dateOfBirth, species, color);
                final List<CatDto> cat = catService.findCatsByParam(param);

                return cat != null
                        ? new ResponseEntity<>(cat, HttpStatus.OK)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
        final List<CatDto> cats = catService.findAll();

        return cats != null &&  !cats.isEmpty()
                ? new ResponseEntity<>(cats, HttpStatus.OK)
                : new ResponseEntity<>(cats, HttpStatus.NO_CONTENT);
    }
    }

    @GetMapping(value = "/cats/{id}")
    public ResponseEntity<CatDto> read(@PathVariable(name = "id") UUID id) {
        final CatDto cat = catService.findCat(id);

        return cat != null
                ? new ResponseEntity<>(cat, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/cats/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody CatDto cat) {
        final boolean updated = catService.update(cat, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/cats/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = catService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
