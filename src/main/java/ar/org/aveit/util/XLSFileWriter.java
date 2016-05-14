package ar.org.aveit.util;

import ar.org.aveit.rest.model.InformacionGanadorResource;
import ar.org.aveit.rest.model.PremiosGanadoresResource;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by DiegoT on 21/11/2015.
 */
public class XLSFileWriter {
    private static final String FILE_HEADER = "Premio,Numero,GanadorApellido,GanadoreNombre,GanadorDNI,GanadorDomicilio,VendedorNroSocio,VendedorApellido,VendedorNombre,VendedorGrupo,NumeroCabecera,Numero2,VendedorTelefono";

    public static String writeXLSFile(String fileName, List<InformacionGanadorResource> list) {

        Workbook libro = new HSSFWorkbook();

        Sheet hoja = libro.createSheet("Hoja1");

        for (int i = 0; i < list.size(); i++) {

            if (i == 0) {
                Row fila = hoja.createRow(i);
                List<String> headers = Arrays.asList(FILE_HEADER.split(","));
                IntStream.range(0, headers.size()-1).forEach(c -> fila.createCell(c).setCellValue(headers.get(c)));
            }
            Row fila = hoja.createRow(i+1);

            fila.createCell(0).setCellValue(list.get(i).getExtracto().getPremio());
            fila.createCell(1).setCellValue(list.get(i).getExtracto().getNumero());

            fila.createCell(2).setCellValue(list.get(i).getGanador().getApellido());
            fila.createCell(3).setCellValue(list.get(i).getGanador().getNombre());
            fila.createCell(4).setCellValue(list.get(i).getGanador().getDni());
            fila.createCell(5).setCellValue(list.get(i).getGanador().getDomicilio());

            fila.createCell(6).setCellValue(list.get(i).getVendedor().getNroSocio());
            fila.createCell(7).setCellValue(list.get(i).getVendedor().getApellido());
            fila.createCell(8).setCellValue(list.get(i).getVendedor().getNombre());
            fila.createCell(9).setCellValue(list.get(i).getVendedor().getGrupo());

            fila.createCell(10).setCellValue((!Collections.EMPTY_LIST.equals(list.get(i).getVendedor().getNumeros()) ? list.get(i).getVendedor().getNumeros().get(0).toString() : ""));
            fila.createCell(11).setCellValue((!Collections.EMPTY_LIST.equals(list.get(i).getVendedor().getNumeros()) ? list.get(i).getVendedor().getNumeros().get(1).toString() : ""));

            fila.createCell(12).setCellValue(list.get(i).getVendedor().getTelefono());
        }


        FileOutputStream archivo = null;
        try {
            archivo = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            libro.write(archivo);
            libro.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("XLS file was created successfully !!!");
        return fileName;
    }

    public static String writeXLSFile(String fileName, PremiosGanadoresResource premios) {

        Workbook libro = new HSSFWorkbook();

        createSheet("Ganadores", libro, premios.getPremios());
        createSheet("4 Cifras", libro, premios.getPremios4Cifras());
        createSheet("3 Cifras", libro, premios.getPremios3Cifras());
        createSheet("2 Cifras", libro, premios.getPremios2Cifras());

        FileOutputStream archivo = null;
        try {
            archivo = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            libro.write(archivo);
            libro.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("XLS file was created successfully !!!");
        return fileName;
    }

    private static void createSheet(String name, Workbook book, List<InformacionGanadorResource> list) {
        Sheet hoja = book.createSheet(name);

        for (int i = 0; i < list.size(); i++) {

            if (i == 0) {
                Row fila = hoja.createRow(i);
                List<String> headers = Arrays.asList(FILE_HEADER.split(","));
                IntStream.range(0, headers.size()).forEach(c -> fila.createCell(c).setCellValue(headers.get(c)));
            }
            Row fila = hoja.createRow(i+1);

            fila.createCell(0).setCellValue(list.get(i).getExtracto().getPremio());
            fila.createCell(1).setCellValue(list.get(i).getExtracto().getNumero());

            fila.createCell(2).setCellValue(list.get(i).getGanador().getApellido());
            fila.createCell(3).setCellValue(list.get(i).getGanador().getNombre());
            fila.createCell(4).setCellValue(list.get(i).getGanador().getDni());
            fila.createCell(5).setCellValue(list.get(i).getGanador().getDomicilio());

            fila.createCell(6).setCellValue(list.get(i).getVendedor().getNroSocio());
            fila.createCell(7).setCellValue(list.get(i).getVendedor().getApellido());
            fila.createCell(8).setCellValue(list.get(i).getVendedor().getNombre());
            fila.createCell(9).setCellValue(list.get(i).getVendedor().getGrupo());

            fila.createCell(10).setCellValue((!Collections.EMPTY_LIST.equals(list.get(i).getVendedor().getNumeros()) ? list.get(i).getVendedor().getNumeros().get(0).toString() : ""));
            fila.createCell(11).setCellValue((!Collections.EMPTY_LIST.equals(list.get(i).getVendedor().getNumeros()) ? list.get(i).getVendedor().getNumeros().get(1).toString() : ""));

            fila.createCell(12).setCellValue(list.get(i).getVendedor().getTelefono());
        }
    }
}
