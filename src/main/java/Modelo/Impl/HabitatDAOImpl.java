/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.HabitatDAO;
import Modelo.Habitat;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Sorts.descending;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author amiss
 */
public class HabitatDAOImpl extends BaseDAOMongo implements HabitatDAO {
    
    private BaseDAOMongo DAO; 
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public HabitatDAOImpl() throws Exception {
        this.DAO = new BaseDAOMongo();
    }

    /**
     * Obtiene un habitat en particular segun su id, usado en modificar y eliminar.
     * @param id id buscado
     * @return
     * @throws Exception general
     */
    @Override
    public Habitat obtenerHabitat(int id) throws Exception {
        Document doc = this.DAO.obtenerDocumento(id, this.DAO.colHabitats);
        Habitat habitat = new Habitat();
        habitat.setId(id);
        habitat.setNombre(doc.getString("nombre"));
        habitat.setTipo(doc.getString("tipo"));
        habitat.setClima(doc.getString("clima"));
        habitat.setNivelLimpieza(doc.getString("nivel_limpieza"));
        if (doc.getInteger("capacidad_animales") != null){
            habitat.setCapacidadAnimales(doc.getInteger("capacidad_animales"));
        }
        
        //Logica obtencion habitat/mongo
        
        return habitat;
    }
    
    
    /**
     * Obtiene la lista de habitats, segun la lista de ids habitat que tiene
     * un intendente.
     * @param ids lista de ids que tiene un intendente
     * @returnlista de habitats
     * @throws Exception 
     */
    @Override
    public List<Habitat> obtenerHabitats(List<Integer> ids) throws Exception {
        List<Habitat> habitats = new ArrayList<>();
        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return habitats;
    }
    
    /**
     * Obtiene todos los habitats del sistema.
     * Utilizado por el admin para ver la lista de habitats general
     * @return lista de habitats
     * @throws Exception 
     */
    @Override
    public List<Habitat> obtenerTodosHabitats() throws Exception {
        List<Habitat> habitats = new ArrayList<>();
        FindIterable<Document> Aux = DAO.obtenerDocumentos(colHabitats);
        for (Document doc : Aux){
            Habitat habitatAux = this.obtenerHabitat(doc.getInteger("_id"));
            habitats.add(habitatAux);
        }

        return habitats;
    }

    /**
     * Obtiene el proximo id que se le asignara al nuevo habitat, cuando se cree
     * @return
     * @throws Exception 
     */
    @Override
    public Integer obtenerIdDisponible() throws Exception {
        Document doc = DAO.colHabitats.find().sort(descending("_id")).first();
        if (doc != null){
            if (doc.getInteger("_id") != null){
                int idInt = doc.getInteger("_id");
                return idInt+1;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
    
    /**
     * Modifica los datos de un habitat que se pase como parametro.
     * La idea es que el parametro traiga el id a modificar
     * y aqui se modifiquen todos los datos segun ese id.
     * @param habitatAux referencia al habitat a modificar
     * @return true si se logro, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarDatos(Habitat habitatAux) throws Exception{
        try{
            if (habitatAux.getNombre() != null){
                System.out.println("A");
                DAO.actualizarString(habitatAux.getId(), "nombre", habitatAux.getNombre(), colHabitats);
            }
            if (habitatAux.getTipo() != null){
                System.out.println("B");
                DAO.actualizarString(habitatAux.getId(), "tipo", habitatAux.getTipo(), colHabitats);
            }
            if (habitatAux.getClima()!= null){
                System.out.println("C");
                DAO.actualizarString(habitatAux.getId(), "clima", habitatAux.getClima(), colHabitats);
            }
            if (habitatAux.getNivelLimpieza()!= null){
                System.out.println("D");
                DAO.actualizarString(habitatAux.getId(), "nivel_limpieza", habitatAux.getNivelLimpieza(), colHabitats);
            }
            if ((Integer) habitatAux.getCapacidadAnimales()!= null){
                System.out.println("E");
                DAO.actualizarInt(habitatAux.getId(), "capacidad_animales", habitatAux.getCapacidadAnimales(), colAnimales);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    /**
     * Elimina un habitat segun su id.
     * @param idHabitat id del habitat a eliminar
     * @return
     * @throws Exception 
     */
    @Override
    public boolean eliminarHabitat(int idHabitat) throws Exception {
        if (DAO.eliminarDocumento(idHabitat, colHabitats)){
            return true;
        } else {
            return false;
        }

        //logicaOracle
        //tambien debe buscar en todos los animales quien tenga ese idHabitat y dejarlo en 0
    }

    /**
     * Agrega un habitat a la BD.
     * @param habitatAux habitat con los datos a agregar.
     * @return true exito, false caso contrario.
     * @throws Exception 
     */
    @Override
    public boolean agregarHabitat(Habitat habitatAux) throws Exception {
        try{//logica verificar si atributo es distinto de isblank, y .append, para las listas si es distinto de isEmpty
            Document doc = DAO.nuevoConInt("_id", habitatAux.getId());
            if (habitatAux.getNombre() != null){
                doc = DAO.appendString(doc, "nombre", habitatAux.getNombre());
            }
            if (habitatAux.getTipo() != null){
                doc = DAO.appendString(doc, "tipo", habitatAux.getTipo());
            }
            if (habitatAux.getClima() != null){
                doc = DAO.appendString(doc, "clima", habitatAux.getClima());
            }
            if (habitatAux.getNivelLimpieza() != null){
                doc = DAO.appendString(doc, "nivel_limpieza", habitatAux.getNivelLimpieza());
            }
            if ((Integer) habitatAux.getCapacidadAnimales() != null){
                doc = DAO.appendInt(doc, "capacidad_animales", habitatAux.getCapacidadAnimales());
            }
            if (DAO.insertarUno(doc, colHabitats)){
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
