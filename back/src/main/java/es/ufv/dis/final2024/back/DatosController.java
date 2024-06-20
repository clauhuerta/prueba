package es.ufv.dis.final2024.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/datos")

public class DatosController {

    @Autowired
    private DatosService childDataService;

    // Constructor que acepta ChildDataService
    public DatosController(DatosService childDataService) {
        this.childDataService = childDataService;
    }

    // GetMapping para ver el json de cp-national localhost:8080/child-data/verTodo
    @GetMapping("/verTodo")
    public ResponseEntity<List<Datos>> VerTodoChildData() {
        return ResponseEntity.ok(childDataService.VerTodoChildData());
    }

    // GetMapping para ver el json de MsCode localhost:8080/child-data/verTodoCode
    @GetMapping("/verTodoCode")
    public ResponseEntity<List<DatosMsCode>> VerTodoMsCodeData() {
        return ResponseEntity.ok(childDataService.VerTodoMsCodeData());
    }

    // GetMapping para ver un usuario por via "_id". localhost:8080/child-data/"_id"
    @GetMapping("/{id}")
    public ResponseEntity<Datos> getChildDataById(@PathVariable String id) {
        return childDataService.getChildDataById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PostMapping para que se añada un usuario si la id no existe, si existe la id y hay cambios en otros atributros se añadirá.
    @PostMapping
    public ResponseEntity<Datos> AnadiroActuChildData(@RequestBody Datos childData) {
        childDataService.AnadiroActuChildData(childData.get_id(), childData);
        return ResponseEntity.ok(childData);
    }

    // DeleteMapping para eliminar un usuario via "_id" localhost:8080/child-data/"_id"
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> EliminarChildData(@PathVariable String id) {
        childDataService.EliminarChildData(id);
        return ResponseEntity.ok().build();
    }
}
