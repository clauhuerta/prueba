package es.ufv.dis.final2024.back;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DatosService {
    private final DatosRepository dataRepository;

    // Para acceder a las funciones del DataRepository
    public DatosService(DatosRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    // Datos de la clase datos
    public List<Datos> VerTodoChildData() {
        return dataRepository.verTodo();
    }

    // Datos del MsCode
    public List<DatosMsCode> VerTodoMsCodeData() {
        return dataRepository.verTodoCode();
    }

    // Buscar por id
    public Optional<Datos> getChildDataById(String id) {
        return dataRepository.getDataById(id);
    }

    // Añadir o actualizar usuario
    public void AnadiroActuChildData(String id, Datos childData) {
        if (dataRepository.getDataById(id).isPresent()) {
            dataRepository.Actualizar(id, childData);
        } else {
            dataRepository.Anadir(childData);
        }
    }

    // Eliminar un usuario
    // prueba feature
    public void EliminarChildData(String id) {
        dataRepository.Eliminar(id);
    }
}

