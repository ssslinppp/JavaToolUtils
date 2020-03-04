package com.ssslinppp.readerdemo;

import com.google.common.base.Stopwatch;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * https://svn.apache.org/repos/asf/poi/trunk/src/examples/src/org/apache/poi/hssf/usermodel/examples/EventExample.java
 */
public class EventExample implements HSSFListener {
    private SSTRecord sstrec;

    /**
     * Read an excel file and spit out what we find.
     *
     * @param args Expect one argument that is the file to read.
     * @throws IOException When there is an error processing the file.
     */
    public static void main(String[] args) throws IOException {
        // create a new file input stream with the input file specified
        // at the command line
        Stopwatch stopwatch = Stopwatch.createStarted();
        String filePath = "D:\\tempTempTemp\\MyExcel1000-1.xls";
        try (FileInputStream fin = new FileInputStream(filePath)) {
            // create a new org.apache.poi.poifs.filesystem.Filesystem
            try (POIFSFileSystem poifs = new POIFSFileSystem(fin)) {
                // get the Workbook (excel part) stream in a InputStream
                try (InputStream din = poifs.createDocumentInputStream("Workbook")) {
                    // construct out HSSFRequest object
                    HSSFRequest req = new HSSFRequest();
                    // lazy listen for ALL records with the listener shown above
                    req.addListenerForAllRecords(new EventExample());
                    // create our event factory
                    HSSFEventFactory factory = new HSSFEventFactory();
                    // process our events based on the document input stream
                    factory.processEvents(req, din);
                }
            }
        }
        System.out.println("done.");
        System.out.println("Done ,耗时:" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + " 毫秒");
    }

    /**
     * This method listens for incoming records and handles them as required.
     *
     * @param record The record that was found while reading.
     */
    @Override
    public void processRecord(Record record) {
        switch (record.getSid()) {
            // the BOFRecord can represent either the beginning of a sheet or the workbook
            case BOFRecord.sid:
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
                    System.out.println("Encountered workbook");
                    // assigned to the class level member
                } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
                    System.out.println("Encountered sheet reference");
                }
                break;
            case BoundSheetRecord.sid:
                BoundSheetRecord bsr = (BoundSheetRecord) record;
                System.out.println("New sheet named: " + bsr.getSheetname());
                break;
            case RowRecord.sid:
                RowRecord rowrec = (RowRecord) record;
                System.out.println("Row found, first column at "
                        + rowrec.getFirstCol() + " last column at " + rowrec.getLastCol());
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                System.out.println("Cell found with value " + numrec.getValue()
                        + " at row " + numrec.getRow() + " and column " + numrec.getColumn());
                break;
            // SSTRecords store a array of unique strings used in Excel.
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                for (int k = 0; k < sstrec.getNumUniqueStrings(); k++) {
                    System.out.println("String table value " + k + " = " + sstrec.getString(k));
                }
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                System.out.println("String cell found with value "
                        + sstrec.getString(lrec.getSSTIndex()));
                break;
        }
    }
}
