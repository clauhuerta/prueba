package es.ufv.dis.final2024.back;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DatosRepository {

    // Rutas para acceder a los archivos json
    private static final String fichero = "src/main/resources/cp-national-datafile.json"; // Ruta del archivo JSON
    private static final String fichero1 = "src/main/resources/MsCode_json.json"; // Ruta del archivo JSON

    private final Gson gson = new Gson();

    // Funcion vertodo del ChildData
    public List<Datos> verTodo() {
        try (FileReader reader = new FileReader(fichero)) {
            Type dataType = new TypeToken<ArrayList<Datos>>(){}.getType();
            return gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Funcion Vertodo del MsCode
    public List<DatosMsCode> verTodoCode() {
        try (FileReader reader = new FileReader(fichero1)) {
            Type dataType = new TypeToken<Map<String, List<DatosMsCode>>>(){}.getType();
            Map<String, List<DatosMsCode>> allData = gson.fromJson(reader, dataType);

            List<DatosMsCode> result = new ArrayList<>();
            for (List<DatosMsCode> dataList : allData.values()) {
                result.addAll(dataList);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //Funcion buscar por id del ChildData
    public Optional<Datos> getDataById(String id) {
        return verTodo().stream().filter(data -> data.get_id().equals(id)).findFirst();
    }

    // Funcion Añadir de los datos si el ID no coincide añado id con las caracteristicas a las lista
    public void Anadir(Datos newData) {
        List<Datos> dataList = verTodo();
        dataList.add(newData);
        saveDataList(dataList);
    }

    // Funcion Actualizar de los datos si el ID coincide actualizo
    public void Actualizar(String id, Datos updatedData) {
        List<Datos> dataList = verTodo();
        dataList.stream().filter(data -> data.get_id().equals(id)).findFirst().ifPresent(data -> {
            dataList.remove(data);
            dataList.add(updatedData);
            saveDataList(dataList);
        });
    }

    // Funcion Eliminar del ChildData
    public void Eliminar(String id) {
        List<Datos> dataList = verTodo();
        dataList.removeIf(data -> data.get_id().equals(id));
        saveDataList(dataList);
    }

    // Funcion guardar del ChildData
    private void saveDataList(List<Datos> dataList) {
        try (FileWriter writer = new FileWriter(fichero)) {
            gson.toJson(dataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
