package ar.org.aveit.util;

import ar.org.aveit.rest.model.InformacionGanadorResource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DiegoT on 18/11/2015.
 */
public class CSVFileWriter {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DOUBLE_QUOTES = "\"";

    //CSV file header
    private static final String FILE_HEADER = "Premio,Numero,GanadorApellido,GanadoreNombre,GanadorDNI,GanadorDomicilio,VendedorNroSocio,VendedorApellido,VendedorNombre,VendedorGrupo,NumeroCabeceram,Numero2,VendedorTelefono";
    private static final Object [] FILE_HEADER_ACCSV = {"Premio","Numero","GanadorApellido","GanadoreNombre","GanadorDNI","GanadorDomicilio","VendedorNroSocio","VendedorApellido","VendedorNombre","VendedorGrupo","NumeroCabeceram","Numero2","VendedorTelefono"};

    public static String writeCSVFile(String fileName, List<InformacionGanadorResource> list) {

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            final String[] headers = FILE_HEADER.split(",");

            for (int i = 0; i < headers.length; i++) {
                headers[i] = DOUBLE_QUOTES.concat(headers[i]).concat(DOUBLE_QUOTES);
            }

            String listHeaders = String.join(",", headers);

            //Write the CSV file header
//            fileWriter.append(FILE_HEADER.toString());
            fileWriter.append(listHeaders);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (InformacionGanadorResource ganador : list) {
                fileWriter.append(DOUBLE_QUOTES + String.valueOf(ganador.getExtracto().getPremio()) + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + String.valueOf(ganador.getExtracto().getNumero()) + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);

                fileWriter.append(DOUBLE_QUOTES + ganador.getGanador().getApellido() + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getGanador().getNombre() + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getGanador().getDni() + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getGanador().getDomicilio() + DOUBLE_QUOTES);

                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getVendedor().getNroSocio() + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getVendedor().getApellido() + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getVendedor().getNombre() + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getVendedor().getGrupo() + DOUBLE_QUOTES);

                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(Optional.ofNullable(ganador.getVendedor().getNumeros().get(0).toString()).orElse(""));
                fileWriter.append(DOUBLE_QUOTES + (!Collections.EMPTY_LIST.equals(ganador.getVendedor().getNumeros()) ? ganador.getVendedor().getNumeros().get(0).toString() : "" ) + DOUBLE_QUOTES);
                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(Optional.ofNullable(ganador.getVendedor().getNumeros().get(1).toString()).orElse(""));
                fileWriter.append(DOUBLE_QUOTES + (!Collections.EMPTY_LIST.equals(ganador.getVendedor().getNumeros()) ? ganador.getVendedor().getNumeros().get(1).toString() : "" ) + DOUBLE_QUOTES);

                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(DOUBLE_QUOTES + ganador.getVendedor().getTelefono() + DOUBLE_QUOTES);

                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
        return fileName;
    }

    public static String writeCsvFileACCSV(String fileName, List<InformacionGanadorResource> list) {


        FileWriter fileWriter = null;

        CSVPrinter csvFilePrinter = null;

        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {

            //initialize FileWriter object
            fileWriter = new FileWriter(fileName);

            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Create CSV file header
            csvFilePrinter.printRecord(FILE_HEADER_ACCSV);

            //Write a new student object list to the CSV file
            for (InformacionGanadorResource ganador : list) {
                List ganadorDataRecord = new ArrayList();

                ganadorDataRecord.add(ganador.getExtracto().getPremio());
                ganadorDataRecord.add(ganador.getExtracto().getNumero());

                ganadorDataRecord.add(ganador.getGanador().getApellido());
                ganadorDataRecord.add(ganador.getGanador().getNombre());
                ganadorDataRecord.add(ganador.getGanador().getDni());
                ganadorDataRecord.add(ganador.getGanador().getDomicilio());

                ganadorDataRecord.add(ganador.getVendedor().getNroSocio());
                ganadorDataRecord.add(ganador.getVendedor().getApellido());
                ganadorDataRecord.add(ganador.getVendedor().getNombre());
                ganadorDataRecord.add(ganador.getVendedor().getGrupo());

                ganadorDataRecord.add((!Collections.EMPTY_LIST.equals(ganador.getVendedor().getNumeros()) ? ganador.getVendedor().getNumeros().get(0).toString() : ""));
                ganadorDataRecord.add((!Collections.EMPTY_LIST.equals(ganador.getVendedor().getNumeros()) ? ganador.getVendedor().getNumeros().get(1).toString() : ""));

                ganadorDataRecord.add(ganador.getVendedor().getTelefono());

                csvFilePrinter.printRecord(ganadorDataRecord);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
            }
        }
        return fileName;
    }
}
