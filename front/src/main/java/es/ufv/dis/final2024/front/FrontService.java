package es.ufv.dis.final2024.front;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

public class FrontService {

    private static final String BACKEND_URL = "http://localhost:8085/datos";
    private static final String BACKEND_URL1 = "http://localhost:8085/datos/verTodo";
    private static final String FRONTEND_URL = "http://localhost:8085/datos/verTodoCode";

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger LOGGER = Logger.getLogger(FrontService.class.getName());

    // Singleton pattern
    private static FrontService instance;

    private FrontService() {}

    public static FrontService getInstance() {
        if (instance == null) {
            instance = new FrontService();
        }
        return instance;
    }

    //Ver los datos
    public List<Datos> findAll() {
        try {
            Datos[] childrenArray = restTemplate.getForObject(BACKEND_URL1, Datos[].class);
            return childrenArray != null ? Arrays.asList(childrenArray) : List.of();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los datos de 'Child' del backend", e);
            return List.of();
        }
    }

    //Ver MsCode
    public List<DatosMsCode> findAllmsCode() {
        try {
            DatosMsCode[] mscodeArray = restTemplate.getForObject(FRONTEND_URL, DatosMsCode[].class);
            return mscodeArray != null ? Arrays.asList(mscodeArray) : List.of();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los datos de 'MsCode' del backend", e);
            return List.of();
        }
    }
    // Método para buscar
    public Datos findById(String id) {
        try {
            return restTemplate.getForObject(BACKEND_URL + "/" + id, Datos.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar 'Child' con id: " + id, e);
            return null;
        }
    }

    // Otros métodos para agregar, editar, eliminar, etc.
    public void delete(String id) {
        try {
            restTemplate.delete(BACKEND_URL + "/" + id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar 'Child' con id: " + id, e);
        }
    }
    // Añadir
    public void save(Datos datos) {
        try {
            restTemplate.postForEntity(BACKEND_URL, datos, Datos.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al añadir o actualizar el objeto 'Child'", e);
        }
    }


    //Para ver MsCode pero da error
    public Map<String, List<DatosMsCode>> getMsCodeGroupedData() {
        List<DatosMsCode> msCodeData = this.findAllmsCode(); // Suponiendo que esta función lee los datos de MsCode del JSON
        return groupMsCodeDataByMscode(msCodeData);
    }


    //Para
    private Map<String, List<DatosMsCode>> groupMsCodeDataByMscode(List<DatosMsCode> msCodeData) {
        Map<String, List<DatosMsCode>> groupedData = new HashMap<>();
        for (DatosMsCode data : msCodeData) {
            String mscode = data.getMscode(); // Utiliza el campo mscode para la agrupación
            groupedData.computeIfAbsent(mscode, k -> new ArrayList<>()).add(data);
        }
        return groupedData;
    }
}

