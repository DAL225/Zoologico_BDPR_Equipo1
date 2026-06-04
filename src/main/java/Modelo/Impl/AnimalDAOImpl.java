/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Sorts.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author amiss
 */
public class AnimalDAOImpl extends BaseDAOMongo implements AnimalDAO{
    
    private BaseDAOMongo DAO;
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public AnimalDAOImpl() throws Exception {
        this.DAO = new BaseDAOMongo();
    }
    
    /**
     * Obtiene un animal por su id.
     * @param id id de animal buscado
     * @return el animal en cuestion, null si no se encontro
     * @throws Exception 
     */
    @Override
    public Animal obtenerAnimal(int id) throws Exception {
        Document doc = this.DAO.obtenerDocumento(id, this.DAO.colAnimales);
        Animal animal = new Animal();
        animal.setId(id);
        animal.setNombreCientifico(doc.getString("nombre_cientifico"));
        animal.setEspecie(doc.getString("especie"));
        if (doc.getInteger("id_habitat") != null){
            animal.setIdHabitat(doc.getInteger("id_habitat"));
        }
        animal.setNombreComun(doc.getString("nombre_común"));
        if (doc.getInteger("edad") != null){
            animal.setEdad(doc.getInteger("edad"));
        }
        animal.setSexo(doc.getString("sexo"));
        animal.setEstadoSalud(doc.getString("estado_salud"));

        List<String> recomendaciones = doc.getList("recomendaciones_cuidado", String.class);
        animal.setRecomendacionesCuidado(recomendaciones != null ? recomendaciones : new ArrayList<>());

        List<String> tratamientos = doc.getList("tratamientos", String.class);
        animal.setTratamientos(tratamientos != null ? tratamientos : new ArrayList<>());

        return animal;
    }
    
    /**
     * Obtiene una lista de animales, que contengan el id de cada elemento de la lista.
     * Un cuidador/veterinario tienen como atributo una lista de ids,
     * este metodo busca que cuando ellos accedan se cargue en automatico su lista de animales,
     * de acuerdo a la lista de isd como parametro.
     * @param ids Lista de ids buscados
     * @return Lista de animales, ya sea con contenido o vacia, si no se encontro ninguno
     * @throws Exception 
     */
    @Override
    public List<Animal> obtenerAnimales(List<Integer> ids) throws Exception {
        List<Animal> listaAnimales = new ArrayList<>();

        // Validación inicial: Si no hay IDs que buscar, regresamos la lista vacía de inmediato
        if (ids == null || ids.isEmpty()) {
            return listaAnimales;
        }

        FindIterable<Document> documentosAux = DAO.colAnimales.find(com.mongodb.client.model.Filters.in("_id", ids));

        // Mapeamos de forma segura cada documento encontrado
        for (Document doc : documentosAux) {
            if (doc != null) {
                Animal animalAux = new Animal();

                // Asignamos el ID del documento
                animalAux.setId(doc.getInteger("_id"));

                // Cadenas de texto protegidas contra nulos
                animalAux.setNombreCientifico(doc.getString("nombre_cientifico") != null ? doc.getString("nombre_cientifico") : "");
                animalAux.setEspecie(doc.getString("especie") != null ? doc.getString("especie") : "");
                animalAux.setNombreComun(doc.getString("nombre_común") != null ? doc.getString("nombre_común") : "");
                animalAux.setSexo(doc.getString("sexo") != null ? doc.getString("sexo") : "");
                animalAux.setEstadoSalud(doc.getString("estado_salud") != null ? doc.getString("estado_salud") : "");

                // Colecciones
                List<String> recomendaciones = doc.getList("recomendaciones_cuidado", String.class);
                animalAux.setRecomendacionesCuidado(recomendaciones != null ? recomendaciones : new ArrayList<>());

                List<String> tratamientos = doc.getList("tratamientos", String.class);
                animalAux.setTratamientos(tratamientos != null ? tratamientos : new ArrayList<>());

                // Agregamos el animal ya estructurado a la lista final
                listaAnimales.add(animalAux);
            }
        }

        return listaAnimales;
    }

    /**
     * Obtiene todos los animales almacenados sin excepcion.
     *
     * @return Lista de animales, ya sea con contenido o vacia, si no se
     * encontro ninguno
     * @throws Exception
     */
    @Override
    public List<Animal> obtenerTodosAnimales() throws Exception {

        List<Animal> listaAnimales = new ArrayList<>();
        FindIterable<Document> Aux = DAO.obtenerDocumentos(colAnimales);
        for (Document doc : Aux) {
            Animal animalAux = this.obtenerAnimal(doc.getInteger("_id"));
            listaAnimales.add(animalAux);
        }
        //logica find/mongo

        return listaAnimales;
    }

    /**
     * Obtiene el id maximo dentro de la bd. La idea es usar
     * 'sort(descending("_id")).first()' que se cree obtiene el maximo id, luego
     * sumarle 1, y regresar eso como valor.
     *
     * @return el id disponible
     * @throws Exception
     */
    @Override
    public Integer obtenerIdDisponible() throws Exception {
        Document doc = DAO.colAnimales.find()
                .sort(descending("_id"))
                .first();

        if (doc == null) {
            return 1;
        }

        Integer ultimoId = doc.getInteger("_id");

        return ultimoId + 1;
    }

    /**
     * Modifica los datos de un animal pasando un parametro con los datos a
     * modificar y el id del cual se modificara.
     *
     * @param animalAux animal con los nuevos datos pero el id requerido
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean modificarDatos(Animal animalAux) throws Exception {
        //update cada dato
        return true;
    }

    /**
     * Eliminar un animal por su id.
     *
     * @param idAnimal id del animal requerido
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean eliminarAnimal(int idAnimal) throws Exception {
        //delete
        //tambien debe buscar en todos los cuidadores y veterinarios quin tenga ese id y borrarlo.
        return true;
    }
    
    /**
     * Se modifica la lista de tratamientos de un animal.
     * el comando set reemplaza lo actual por lo nuevo
     * @param idAnimal id del animal a modificar
     * @param recomendaciones nuevas recomendaciones
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarRecomendaciones(int idAnimal, List<String> recomendaciones) throws Exception{
        
        return true;
    }
    
    /**
     * Se modifica la lista de recomendaciones de un animal.
     * el comando set reemplaza lo actual por lo nuevo
     * @param idAnimal id del animal a modificar
     * @param tratamientos nuevas recomendaciones
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarTratamientos(int idAnimal, List<String> tratamientos) throws Exception{
        
        return true;
    }

    /**
     * Cambia el valor idHabitat del animal a 0.Se usa cuando se elimina un animal, para mantener consistencia.
     * La idea es que se recorran todos los animales y donde el valor de idHabitat sea igual al parametro
     * cambiarlo a 0
     * @param idHabitat id del habitat que se eliminara del
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean desasignarHabitat(int idHabitat) throws Exception {
        return false;
    }

    @Override
    public boolean agregarAnimal(Animal animalAux) throws Exception {
        Document doc = DAO.nuevoConInt("_id", animalAux.getId());
        if (animalAux.getNombreCientifico() != null){
            doc = DAO.appendString(doc, "nombre_cientifico", animalAux.getNombreCientifico());
        }
        if (animalAux.getEspecie() != null){
            doc = DAO.appendString(doc, "especie", animalAux.getEspecie());
        }
        if ((Integer) animalAux.getIdHabitat() != null){
            doc = DAO.appendInt(doc, "id_habitat", animalAux.getIdHabitat());
        }
        if (animalAux.getNombreComun() != null){
            doc = DAO.appendString(doc, "nombre_común", animalAux.getNombreComun());
        }
        if ((Integer) animalAux.getEdad() != null){
            doc = DAO.appendInt(doc, "edad", animalAux.getEdad());
        }
        if (animalAux.getSexo() != null){
            doc = DAO.appendString(doc, "sexo", animalAux.getSexo());
        }
        if (animalAux.getEstadoSalud() != null){
            doc = DAO.appendString(doc, "estado_salud", animalAux.getEstadoSalud());
        }
        if (!animalAux.getRecomendacionesCuidado().isEmpty()) {
            // lista al constructor de ArrayList en lugar de hacer cast directo
            ArrayList<String> recomendacionesList = new ArrayList<>(animalAux.getRecomendacionesCuidado());
            doc = DAO.appendArrayString(doc, "recomendaciones_cuidado", recomendacionesList);
        }

        if (!animalAux.getTratamientos().isEmpty()) {
            // tratamientos
            ArrayList<String> tratamientosList = new ArrayList<>(animalAux.getTratamientos());
            doc = DAO.appendArrayString(doc, "tratamientos", tratamientosList);
        }
        if (DAO.insertarUno(doc, colAnimales)) {
            return true;
        } else {
            return false;
        }
    }
}
