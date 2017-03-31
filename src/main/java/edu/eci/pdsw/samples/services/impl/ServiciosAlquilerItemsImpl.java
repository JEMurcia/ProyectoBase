package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.pdsw.sampleprj.dao.ClienteDAO;
import edu.eci.pdsw.sampleprj.dao.ItemDAO;
import edu.eci.pdsw.sampleprj.dao.PersistenceException;

import edu.eci.pdsw.samples.entities.Cliente;
import edu.eci.pdsw.samples.entities.Item;
import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.entities.TipoItem;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author hcadavid
 */
@Singleton
public class ServiciosAlquilerItemsImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO daoItem;
    
    @Inject 
    private ClienteDAO daoCliente;
        
    @Override
    public int valorMultaRetrasoxDia() {
        return 5000;
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try {
            return daoCliente.load((int)docu);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el cliente con documento "+docu,ex);
        }
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {

        try {
//            System.out.println(idcliente);
            return daoCliente.consultarItems((int) idcliente);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("no se ha podido consultar los items del cliente con "
                    + "documento "+idcliente, e);
        }

    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try {
            return daoCliente.loadClientes();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("no se puede mostrar los clientes", ex);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return daoItem.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        
//        return ChronoUnit.DAYS.between(consultarItem(iditem).get, temporal2Exclusive)
//            
//            LocalDate fechaMinimaEntrega=ir.getFechafinrenta().toLocalDate();
//            LocalDate fechaEntrega=fechaDevolucion.toLocalDate();
//            long diasRetraso = ChronoUnit.DAYS.between(fechaMinimaEntrega, fechaEntrega);
//            return diasRetraso*valorMultaRetrasoxDia();
throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return daoItem.load(id).getTipo();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("el tipo de item no se encuentra registrado", ex);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try {
            return daoItem.tiposItems();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("no se ha logrado consultar los tipos de items", ex);
        }
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DAY_OF_YEAR, numdias);
        try {
            daoCliente.registrarItemRentado( (int)(long) docu, item.getId(), date, calendar.getTime());
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosAlquilerItemsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrarCliente(Cliente p) throws ExcepcionServiciosAlquiler {
        try {
            daoCliente.save(p);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("no se ha registrado el cliente", ex);
        }
    }

    @Override
    public void registrarDevolucion(int iditem) throws ExcepcionServiciosAlquiler {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        return consultarItem(iditem).getTarifaxDia()*numdias;
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
            daoItem.save(i);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("no se ha registrado el nuevo item", ex);
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
