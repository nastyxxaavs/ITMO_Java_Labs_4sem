package org.example.labthree.controllerLayer;


import org.example.labthree.dataAccessLayer.entities.cat.CatDto;
import org.example.labthree.dataAccessLayer.entities.owner.OwnerDto;
import org.example.labthree.serviceLayer.cat.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/cats")
    public ResponseEntity<?> save(@RequestBody CatDto cat) {
        catService.saveCat(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value="/cats")
    public ResponseEntity<List<CatDto>> read() {
        final List<CatDto> cats = catService.findAll();

        return cats != null &&  !cats.isEmpty()
                ? new ResponseEntity<>(cats, HttpStatus.OK)
                : new ResponseEntity<>(cats, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/cats/{id}")
    public ResponseEntity<CatDto> read(@PathVariable(name = "id") UUID id) {
        final CatDto cat = catService.findCat(id);

        return cat != null
                ? new ResponseEntity<>(cat, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/cats/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody CatDto cat) {
        final boolean updated = catService.update(cat, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/cats/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final boolean deleted = catService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
