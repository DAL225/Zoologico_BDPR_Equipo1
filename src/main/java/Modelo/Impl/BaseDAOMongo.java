package Modelo.Impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Sorts.*;
import static java.lang.Integer.parseInt;
import org.bson.types.ObjectId;

/**
 *
 * @author amiss
 */
public class BaseDAOMongo {
    
    private String conexion = "mongodb://localhost:27017";
    private String base = "Zoologico";
    private String coleccionAnimales = "Animales";
    private String coleccionHabitats = "Habitats";
    public MongoDatabase db;
    public MongoCollection<Document> colAnimales;
    public MongoCollection<Document> colHabitats;
    
    /**
     * Constructor que conecta con la base de datos de Mongo, o la crea de no existir
     * y asigna los atributos de db, colAnimales y colHabitats a los objetos de acceso
     * a la base de datos, y las colecciones respectivas
     * @throws Exception
     */
    public BaseDAOMongo() throws Exception {
        MongoClient cliente = MongoClients.create(conexion);
        this.db = cliente.getDatabase(base);
        this.colAnimales = db.getCollection(coleccionAnimales);
        this.colHabitats = db.getCollection(coleccionHabitats);
    }
    
    /**
     * Método para insertar un documento dado a una coleccion dada, regresando true
     * de poder insertarse, y false de no lograrlo
     * @param doc El documento a insertar
     * @param coleccion La colección donde insertarlo
     * @return True de lograrse insertar, False de no hacerlo
     */
    public boolean insertarUno(Document doc, MongoCollection<Document> coleccion){
        if (coleccion.insertOne(doc) != null){
            for (Document d : coleccion.find()){
                System.out.println(d.toJson());
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Método para crear un nuevo documento con un valor inicial String
     * @param atributo El nombre del atributo inicial
     * @param valor El valor String que tendrá el atributo
     * @return El objeto Document recién creado
     */
    public Document nuevoConString(String atributo, String valor){
        Document doc = new Document(atributo, valor);
        return doc;
    }
    
    /**
     * Método para crear un nuevo documento con un valor inicial int
     * @param atributo El nombre del atributo inicial
     * @param valor El valor int que tendrá el atributo
     * @return El objeto Document recién creado
     */
    public Document nuevoConInt(String atributo, int valor){
        Document doc = new Document(atributo, valor);
        return doc;
    }
    
    /**
     * Método para crear un nuevo documento con un valor inicial ArrayList de Strings
     * @param atributo El nombre del atributo inicial
     * @param valor El valor ArrayList de Strings que tendrá el atributo
     * @return El objeto Document recién creado
     */
    public Document nuevoConArrayString(String atributo, ArrayList<String> valor){
        Document doc = new Document(atributo, valor);
        return doc;
    }
    
    /**
     * Método para crear un nuevo documento con un valor inicial ArrayList de Integers
     * @param atributo El nombre del atributo inicial
     * @param valor El valor ArrayList de Integers que tendrá el atributo
     * @return El objeto Document recién creado
     */
    public Document nuevoConArrayInt(String atributo, ArrayList<Integer> valor) {
        Document doc = new Document(atributo, valor);
        return doc;
    }
    
    /**
     * Método para añadir a un documento un atributo con valor String
     * @param doc El documento al cual se le añadirá el atributo
     * @param atributo El nombre del atributo a añadir
     * @param valor El valor String que tendrá el atributo
     * @return El objeto Document con el append realizado
     */
    public Document appendString(Document doc, String atributo, String valor){
        doc.append(atributo, valor);
        return doc;
    }
    
    /**
     * Método para añadir a un documento un atributo con valor int
     * @param doc El documento al cual se le añadirá el atributo
     * @param atributo El nombre del atributo a añadir
     * @param valor El valor int que tendrá el atributo
     * @return El objeto Document con el append realizado
     */
    public Document appendInt(Document doc, String atributo, int valor){
        doc.append(atributo, valor);
        return doc;
    }
    
    /**
     * Método para añadir a un documento un atributo con valor ArrayList de Strings
     * @param doc El documento al cual se le añadirá el atributo
     * @param atributo El nombre del atributo a añadir
     * @param valor El valor ArrayList de Strings que tendrá el atributo
     * @return El objeto Document con el append realizado
     */
    public Document appendArrayString(Document doc, String atributo, ArrayList<String> valor){
        doc.append(atributo, valor);
        return doc;
    }
    
    /**
     * Método para añadir a un documento un atributo con valor ArrayList de Integers
     * @param doc El documento al cual se le añadirá el atributo
     * @param atributo El nombre del atributo a añadir
     * @param valor El valor ArrayList de Integers que tendrá el atributo
     * @return El objeto Document con el append realizado
     */
    public Document appendArrayInt(Document doc, String atributo, ArrayList<Integer> valor){
        doc.append(atributo, valor);
        return doc;
    }
    
    public FindIterable<Document> obtenerDocumentos(MongoCollection<Document> coleccion){
        FindIterable<Document> docs = coleccion.find();
        return docs;
    }
    
    public Document obtenerDocumento(int id, MongoCollection<Document> coleccion){
        Bson comparacion = Filters.eq("_id", id);
        Document doc = coleccion.find(comparacion).first();
        return doc;
    }
    
    public boolean eliminarDocumento(int id, MongoCollection<Document> coleccion){
        Bson comparacion = Filters.eq("_id", id);
        if (coleccion.deleteOne(comparacion) != null){
            return true;
        } else {
            return false;
        }
    }
}