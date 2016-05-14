package ar.org.aveit.dao.impl;

import ar.org.aveit.dao.SorteosPremiosServiceDao;
import ar.org.aveit.model.Vendedor;
import org.springframework.beans.factory.annotation.Required;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DiegoT on 14/11/2015.
 */
public class SorteosPremiosServiceDaoImpl implements SorteosPremiosServiceDao {
    private DBConnection dbConnection;

    private final static int ESTADO_CUPON_PAGO = 3;

    @Override
    public String getDBStatus() {
//        ResultSet rs = dbConnection.getQuery("select [SisAveit].[dbo].[T_Socios].Apellido, [SisAveit].[dbo].[T_Socios].Nombre from [SisAveit].[dbo].[T_Socios] where [SisAveit].[dbo].[T_Socios].NroSocio = '8729'");
        String result = "";
        try{

            dbConnection.openConnection();
            DatabaseMetaData meta = dbConnection.getMetaData();
            result = "Connected with " +
                    meta.getDriverName() + " " + meta.getDriverVersion()
                    + "{ " + meta.getDriverMajorVersion() + "," +
                    meta.getDriverMinorVersion() + " }" + " to " +
                    meta.getDatabaseProductName() + " " +
                    meta.getDatabaseProductVersion() + "\n";

//            while (rs.next())
//            {
//                result = rs.getString("Apellido") + ", " + rs.getString("Nombre");
//            }
//            rs.close();

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            result = "No database connection!!";
        }
        finally
        {
            dbConnection.closeConnection();
        }
        return result;
    }

    @Override
    public List<Vendedor> getGanadoresNCifras(int anio, int cuponPago, int cifras, int numero) {
        List<Vendedor> ganadores = new ArrayList<Vendedor>();

        String cupon = String.valueOf(cuponPago);
        String year = String.valueOf(anio);
        String query = "";
        String numeroString = String.valueOf(numero);
        String numeroCifras = numeroString.substring((numeroString.length()-cifras));
        String numeroExcluir = numeroString.substring((numeroString.length()-cifras-1));
        Vendedor vendedor;

        query += "SELECT s.NroSocio, s.Nombre, s.Apellido,s.NroGrupo, m2.NroCabecera, m2.Nro2 ";
//        query += "(SELECT TOP 1 T_TelefonoSocio.Telefono FROM [SisAveit].[dbo].T_TelefonoSocio,[SisAveit].[dbo].T_Domicilio ";
//        query += "WHERE T_TelefonoSocio.nrorefdom = T_Domicilio.NroRefDom ";
//        query += "and T_Domicilio.nropersona = s.NroSocio) AS Telefono ";
        query += "FROM [SisAveit].[dbo].[T_" + year + "] t2014 ";
        query += "inner join [SisAveit].[dbo].[t_rifa] r on t2014.NroCabecera = r.NroCabecera ";
        query += "inner join [SisAveit].[dbo].T_monton m2 on r.NroCabecera=m2.NroCabecera ";
        query += "inner join [SisAveit].[dbo].T_Socios s on r.NroPersona = s.NroSocio ";
        query += "WHERE YEAR(r.año)='" + year + "' ";
        query += "and t2014.Cupon" + cupon + " = " + ESTADO_CUPON_PAGO + " ";
        query += "and (m2.NroCabecera like '%" + numeroCifras + "' or m2.Nro2 like '%" + numeroCifras + "') ";
        query += "and  not exists (";
        query += "select 1 from [SisAveit].[dbo].[T_" + year + "] t2014 inner join [SisAveit].[dbo].[t_rifa] r on t2014.NroCabecera = r.NroCabecera ";
        query += "inner join [SisAveit].[dbo].T_monton m on r.NroCabecera=m.NroCabecera ";
        query += "inner join [SisAveit].[dbo].T_Socios s on r.NroPersona = s.NroSocio ";
        query += "where YEAR(r.año)='" + year + "' ";
        query += "and t2014.Cupon" + cupon + " = " + ESTADO_CUPON_PAGO + " ";
        query += "and (m.NroCabecera like '%" + numeroExcluir + "' or m.Nro2 like '%" + numeroExcluir + "') ";
        query += "and m.NroCabecera = m2.NroCabecera ";
        query += "and m.Nro2 = m2.Nro2)";

        dbConnection.openConnection();
        ResultSet rs = dbConnection.getQuery(query);

        try{

            while (rs.next())
            {
                vendedor = new Vendedor();
                List<Integer> numerosBoleta = new ArrayList<>();

                vendedor.setNroSocio(rs.getInt("NroSocio"));
                vendedor.setNombre(rs.getString("Nombre").trim());
                vendedor.setApellido(rs.getString("Apellido").trim());
                vendedor.setGrupo(rs.getInt("NroGrupo"));
//                vendedor.setTelefono(rs.getString("Telefono") == null ? rs.getString("Telefono").trim() : "");


                numerosBoleta.add(rs.getInt("NroCabecera"));
                numerosBoleta.add(rs.getInt("Nro2"));

                vendedor.setNumeros(numerosBoleta);

                ganadores.add(vendedor);
            }
            rs.close();

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            dbConnection.closeConnection();
        }
        return ganadores;
    }

    @Override
    public List<Vendedor> getGanadoresSorteo(int anio, int cuponPago, List<String> numeros) {
        List<Vendedor> ganadores = new ArrayList<Vendedor>();

        //String numerosCVS = "43148,40445,27295,45132,42004,27088,21035,24350,28510,26831,16540,39480,10140,8945,15207,7235,1737,49231,4509,47484";

        String numerosCVS = "";

        for (int i = 0; i < numeros.size() ; i++) {
            numerosCVS += numeros.get(i) +  (i == numeros.size()-1 ? "" : ",");
        }

        String cupon = String.valueOf(cuponPago);
        String year = String.valueOf(anio);
        String query = "";
        Vendedor vendedor;

        query += "SELECT s.NroSocio,s.Nombre,s.Apellido,s.NroGrupo, m.NroCabecera,m.Nro2,";
        query += "(SELECT TOP 1 T_TelefonoSocio.Telefono FROM [SisAveit].[dbo].T_TelefonoSocio,[SisAveit].[dbo].T_Domicilio ";
        query += "WHERE T_TelefonoSocio.nrorefdom = T_Domicilio.NroRefDom ";
        query += "and T_Domicilio.nropersona=s.nroSocio) AS Telefono ";
        query += "FROM [SisAveit].[dbo].[T_" + year + "] t2014 ";
        query += "inner join [SisAveit].[dbo].[t_rifa] r on t2014.NroCabecera = r.NroCabecera ";
        query += "inner join [SisAveit].[dbo].T_monton m on r.NroCabecera=m.NroCabecera ";
        query += "inner join [SisAveit].[dbo].T_Socios s on r.NroPersona = s.NroSocio ";
        query += "WHERE YEAR(r.año)='" + year + "' ";
        query += "and t2014.Cupon" + cupon + " = " + ESTADO_CUPON_PAGO + " ";
        query += "and (m.NroCabecera IN (" + numerosCVS + ") OR m.Nro2 IN (" + numerosCVS + ")) ";
        query += "ORDER BY r.NroCabecera ";

        dbConnection.openConnection();
        ResultSet rs = dbConnection.getQuery(query);

        try{

            while (rs.next())
            {
                vendedor = new Vendedor();
                List<Integer> numerosBoleta = new ArrayList<>();

                vendedor.setNroSocio(rs.getInt("NroSocio"));
                vendedor.setNombre(rs.getString("Nombre").trim());
                vendedor.setApellido(rs.getString("Apellido").trim());
                vendedor.setGrupo(rs.getInt("NroGrupo"));
                vendedor.setTelefono(rs.getString("Telefono").trim());

                numerosBoleta.add(rs.getInt("NroCabecera"));
                numerosBoleta.add(rs.getInt("Nro2"));

                vendedor.setNumeros(numerosBoleta);

                ganadores.add(vendedor);
            }
            rs.close();

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            dbConnection.closeConnection();
        }
        return ganadores;
    }

    @Required
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
}
