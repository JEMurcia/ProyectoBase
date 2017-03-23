/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquilerFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 * 
 * Calculo Multa:
 * 
 * Frontera:
 * CF1: Multas a devoluciones hechas en la fecha exacta (multa 0).
 * CF2: Multas a devoluciones hechas 1 dia despues del prestamo (multa 0)
 * 
 * Clases de equivalencia:
 * CE1: Multas hechas a devolciones realizadas en fechas posteriores
 * a la limite. (multa multa_diaria*dias_retraso)
 * CE2: Multas hechas a devoluciones realizadas en fechas que estan dentro del limite
 * 
 * 
 * Registro de item a Cliente:
 * 
 * Frontera:
 * 
 * Clases de equivalencia:
 * CE3: El item esta registrado al Cliente
 * 
 */
public class AlquilerTest {

    public AlquilerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void CF1Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();        
        Item i1=new Item(sa.consultarTipoItem(1), 44, "Los 4 Fantasticos", "Los 4 FantÃ¡sticos  es una pelÃ­cula de superhÃ©roes  basada en la serie de cÃ³mic homÃ³nima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",3842,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(44);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 3842, item, 5);
        
        assertEquals("No se calcula correctamente la multa (0) "
                + "cuando la devolucion se realiza el dia limite."
                ,0,sa.consultarMultaAlquiler(44, java.sql.Date.valueOf("2005-12-25")));
                
    }
    

    @Test
    public void CE1Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();        
        
        Item i1=new Item(sa.consultarTipoItem(1), 55, "Los 4 Fantasticos", "Los 4 FantÃ¡sticos  es una pelÃ­cula de superhÃ©roes  basada en la serie de cÃ³mic homÃ³nima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",9843,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(55);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9843, item, 5);
        //prueba: 3 dias de retraso
        assertEquals("No se calcula correctamente la multa "
                + "cuando la devolucion se realiza varios dias despues del limite."
                ,sa.valorMultaRetrasoxDia()*3,sa.consultarMultaAlquiler(55, java.sql.Date.valueOf("2005-12-28")));
                
    }
    
    @Test
    public void CE2Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();        
        
        Item i1=new Item(sa.consultarTipoItem(1), 56, "Los 4 Fantasticos", "Los 4 FantÃ¡sticos  es una pelÃ­cula de superhÃ©roes  basada en la serie de cÃ³mic homÃ³nima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",9844,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(56);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9844, item, 3);
        //prueba: Sin dias de retraso, entrega a 2 dias despues del prestamo
        assertEquals("No se calcula correctamente la multa (0)"
                + "cuando la devolucion se realiza dentro de la fecha limite."
                ,0,sa.consultarMultaAlquiler(56, java.sql.Date.valueOf("2005-12-22")));
                
    }
    
    @Test
    public void CF2Test() throws ExcepcionServiciosAlquiler{
        ServiciosAlquiler sa=ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();        
        
        Item i1=new Item(sa.consultarTipoItem(1), 56, "Los 4 Fantasticos", "Los 4 FantÃ¡sticos  es una pelÃ­cula de superhÃ©roes  basada en la serie de cÃ³mic homÃ³nima de Marvel.", java.sql.Date.valueOf("2005-06-08"), 2000, "DVD", "Ciencia Ficcion");        
        sa.registrarCliente(new Cliente("Juan Perez",9845,"24234","calle 123","aa@gmail.com"));
        sa.registrarItem(i1);
                
        Item item=sa.consultarItem(56);
        
        sa.registrarAlquilerCliente(java.sql.Date.valueOf("2005-12-20"), 9845, item, 3);
        //prueba: Sin dias de retraso, entrega a 2 dias despues del prestamo
        assertEquals("No se calcula correctamente la multa (0)"
                + "cuando la devolucion se realiza un dia despues del prestamo."
                ,0,sa.consultarMultaAlquiler(56, java.sql.Date.valueOf("2005-12-21")));
                
    }
    
    
    
    
}
