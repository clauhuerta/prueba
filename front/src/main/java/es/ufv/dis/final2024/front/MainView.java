package es.ufv.dis.final2024.front;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.applayout.AppLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("")
@PageTitle("Main View")
public class MainView extends AppLayout {

    //Caja principal vertical para pestaña datos layout pero no se crea la pestaña como tal
    private final VerticalLayout datosLayout;
    private final VerticalLayout msCodeLayout; //Caja principal vertical para pestaña mscode layout

    private final VerticalLayout pestana3; //Caja principal vertical para pestaña pestaña3

    public MainView() {
        setPrimarySection(Section.DRAWER); //Que el primero que se vea sea datos
        //Crear el "menu" para las pestañas
        //Donde se van a ver arriba de la pantalla para seleccionarlas y cambiar de pestaña a pestaña
        Tabs tabs = createMenuTabs();  //Creo la fucnion para el menu de las pestañas y la almaceno en tabs
        addToNavbar(true, tabs);

        //en datos layout que es una caja principal vertical
        // guardamos la funcion crearDatosLyout para crear la distribucion de esta pantalla
        datosLayout = createDatosLayout();
        msCodeLayout = createMsCodeLayout();
        pestana3 = createPestanaLayout();

        // Inicialmente muestra la pestaña Child
        setContent(datosLayout);

        //Si selecciono una pestaña que se mueva a esa pestaña
        tabs.addSelectedChangeListener(event -> {
            Tab selectedTab = tabs.getSelectedTab();
            if ("Child Data".equals(selectedTab.getLabel())) {
                setContent(datosLayout);
            } else if ("MsCode Data".equals(selectedTab.getLabel())) {
                setContent(msCodeLayout);
            } else if ("Pestaña".equals(selectedTab.getLabel())){
                setContent(pestana3);
            }
        });
    }

    //Función para el menú de las pastañas
    private Tabs createMenuTabs() {
        Tabs tabs = new Tabs();
        Tab childTab = new Tab("Child Data"); //CReo que es para nombres que aparezcan en las pastañas
        Tab msCodeTab = new Tab("MsCode Data");
        Tab pestañaTab = new Tab("Pestaña data");

        tabs.add(childTab, msCodeTab, pestañaTab);
        return tabs;
    }

    //Organizamos la pantalla de pestaña
    private VerticalLayout createPestanaLayout() {
        //Creo las cajas horizontales dentro de la pantalla
        // Dentro de cada "caja" voy a meter los elementos como: botones, campo de texto, etc..
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout formLayout = new HorizontalLayout(); // Usar HorizontalLayout
        HorizontalLayout formLayout1 = new HorizontalLayout();
        HorizontalLayout botonesAnadir = new HorizontalLayout();
        HorizontalLayout botonesEliminar = new HorizontalLayout();

        return layout;

    }


    //Creamos la pantalla de datos
    private VerticalLayout createDatosLayout() {

        //Creo las cajas horizontales dentro de la pantalla
        // Dentro de cada "caja" voy a meter los elementos como: botones, campo de texto, etc..
        VerticalLayout layout = new VerticalLayout(); //Layout es la caja vertical principal
        HorizontalLayout formLayout = new HorizontalLayout(); // Usar HorizontalLayout
        HorizontalLayout formLayout1 = new HorizontalLayout();
        HorizontalLayout botonesAnadir = new HorizontalLayout();
        HorizontalLayout botonesEliminar = new HorizontalLayout();


        // Se crean los campos de texto para poder añadir y actualizar para cada atributo
        TextField mscode = new TextField("Mscode");
        TextField estimate = new TextField("Estimate");
        TextField estCode = new TextField("EstCode");
        TextField se = new TextField("se");
        TextField year = new TextField("Year");
        TextField lowerCIB = new TextField("LowerCIB");
        TextField upperCIB = new TextField("UpperCIB");
        TextField _id = new TextField("_id");

        // Creo el campo de texto ID para buscar y eliminar
        TextField idField = new TextField("_id");

        // Creo los botones
        //En text escribo el texto qu eva a aparecer dentro del botón
        Button addButton = new Button("Add Child");
        Button viewAllButton = new Button("Ver Todo");
        Button deleteButton = new Button("Eliminar");
        Button searchButton = new Button("Buscar");

        // Añadir los componentes al formulario
        //Se añaden los componentes (campos de texto, botones, etc) creados dentro de cada caja que he creado
        //De esta forma organizo los elementos de la pantalla
        formLayout.add(_id, mscode, estimate, se, year, lowerCIB, upperCIB, estCode); //añado los campos de texto
        // para añadir y actualizar

        formLayout1.add(idField); //Añado el campo de texto de id para buscar y eliminar
        botonesAnadir.add(addButton, viewAllButton); // Añado en esta caja los botones de añadir y ver all
        botonesEliminar.add(deleteButton, searchButton); // Añado en esta caja los botones de eliminar y buscar

        // Añado las cajas al layout principal en el orden que quiera que se muestren
        layout.add(formLayout, botonesAnadir, formLayout1, botonesEliminar);
        Grid<Datos> grid = new Grid<>(Datos.class);
        layout.add(grid);


        //FUNCIONES DE CADA BOTON DE LA PESTAÑA DATOS*******************************

        //VER ALL
        //Añadir manejador de eventos para el botón de ver all
        viewAllButton.addClickListener(event -> {
            List<Datos> datos = FrontService.getInstance().findAll();
            grid.setItems(datos);
        });

        //ELIMINAR
        // Añadir manejador de eventos para el botón de eliminar
        deleteButton.addClickListener(event -> {
            String id = idField.getValue();
            FrontService.getInstance().delete(id);
            // Actualizar la vista o grid si es necesario
        });

        // BUSCAR
        // Añadir manejador de eventos para el botón de buscar
        searchButton.addClickListener(event -> {
            String id = idField.getValue(); // Asume que tienes un TextField llamado idField para el ID
            Datos datoEncontrado = FrontService.getInstance().findById(id);

            if (datoEncontrado != null) { //Si el dato encontrado no es nulo
                grid.setItems(datoEncontrado); // Establece un solo item en el Grid
            } else {
                grid.setItems(); // Limpia el grid si no se encuentra el Child
                // Podrías mostrar un mensaje de que no se encontró el Child
            }
        });

        // ADD
        // Añadir usuario
        addButton.addClickListener(event -> {
            try {
                Datos newData = new Datos();

                // Establecer los atributos de newChild con los valores de los campos
                newData.setMscode(mscode.getValue());
                newData.setEstimate(Float.parseFloat(estimate.getValue()));
                newData.setSe(Float.parseFloat(se.getValue()));
                newData.setYear(year.getValue());
                newData.setLowerCIB(Float.parseFloat(lowerCIB.getValue()));
                newData.setUpperCIB(Float.parseFloat(upperCIB.getValue()));
                newData.set_id(_id.getValue());
                newData.setEstCode(estCode.getValue());

                // Llamar a ChildService para guardar el nuevo objeto
                FrontService.getInstance().save(newData);

                List<Datos> children = FrontService.getInstance().findAll();
                grid.setItems(children);

            } catch (NumberFormatException e) {
            } catch (Exception e) {

            }
        });


        return layout;

    }


    //PESTAÑA DE MSCODE
    private VerticalLayout createMsCodeLayout() {
        VerticalLayout layout = new VerticalLayout(); //Caja vertical pantalla entera principal

        // Creo los botones
        //En text escribo el texto qu eva a aparecer dentro del botón
        Button viewAllButton = new Button("Ver Todo");

        //FUNCIONES DE CADA BOTON DE LA PESTAÑA MSCODE*******************************
        viewAllButton.addClickListener(event -> {
            Accordion accordion = new Accordion(); // Reinicializar el acordeón
            accordion.setWidthFull(); // Establecer el ancho del acordeón al máximo
            //Accede a todos los datos del mscode:
            List<DatosMsCode> mscodeData = FrontService.getInstance().findAllmsCode();
            //Mapear por agrupaciones el mscode
            Map<String, List<DatosMsCode>> groupedData = groupMsCodeDataByMscode(mscodeData);

            //Mostrar por pantalla los datos de MsCode por categorías
            for (Map.Entry<String, List<DatosMsCode>> entry : groupedData.entrySet()) {
                String mscodeCategory = entry.getKey();
                List<DatosMsCode> dataList = entry.getValue();

                Grid<DatosMsCode> grid = new Grid<>(DatosMsCode.class);
                grid.setItems(dataList);

                AccordionPanel panel = accordion.add(mscodeCategory, grid);
                // Configuración adicional del panel si es necesario
            }

            layout.removeAll(); // Limpia el layout antes de agregar el nuevo acordeón
            layout.add(viewAllButton, accordion); // Agrega el botón y el acordeón al layout
        });

        // Inicialmente, el layout solo tiene el botón
        layout.add(viewAllButton);
        return layout;
    }

    // Método auxiliar para agrupar los datos de MsCode por categoría mscode
    private Map<String, List<DatosMsCode>> groupMsCodeDataByMscode(List<DatosMsCode> mscodeData) {
        Map<String, List<DatosMsCode>> groupedData = new HashMap<>();
        for (DatosMsCode data : mscodeData) {
            String mscodeCategory = data.getMscode(); // Utiliza el campo mscode para la agrupación
            groupedData.computeIfAbsent(mscodeCategory, k -> new ArrayList<>()).add(data);
        }
        return groupedData;
    }
}
