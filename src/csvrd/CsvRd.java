/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvrd;

import java.awt.BorderLayout;
import java.io.FileReader;
import java.util.List;
 import java.io.*;
 import java.net.URL;
 import java.util.Scanner;
 import javax.swing.*;
 import javax.swing.table.DefaultTableCellRenderer;
 import javax.swing.table.DefaultTableModel;
/**
 *
 * @author KuznetsovIV
 */
public class CsvRd extends JFrame {

    

   
    JTable table;
    DefaultTableModel model;
    JButton closeButton, webButton;
  /**
   * Takes data from a CSV file and places it into a table for display.
   * @param source - a reference to the file where the CSV data is located.
   */
  public CsvRd(String title, String source) {
    super(title);
    table = new JTable();
    JScrollPane scroll = new JScrollPane(table);
    String[] colNames = { "LastName", "FirstName", "Email Address", "Dept."};
    model = new DefaultTableModel(colNames, 0);
    InputStream is;
    try {
        if(source.indexOf("http")==0) {
            URL facultyURL = new URL(source);
            is = facultyURL.openStream();
        }
        else { //local file?
            File f = new File(source);
            is = new FileInputStream(f);
        }
        insertData(is);
        //table.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer());
    }
    catch(IOException ioe) {
        JOptionPane.showMessageDialog(this, ioe, "Error reading data", JOptionPane.ERROR_MESSAGE);
    }

    JPanel buttonPanel = new JPanel();
    closeButton = new JButton("Close");
    webButton = new JButton("Proctinator.com");
    buttonPanel.add(closeButton);
    buttonPanel.add(new JLabel("   You can download this file from our site: "));
    buttonPanel.add(webButton);

    JPanel notesPanel = new JPanel();
    JLabel note1 = new JLabel(" Make sure that your list is formatted exactly as shown below, including the *markers between categories ");
    JLabel note2 = new JLabel(" Be sure to place each faculty member into the correct category: *Teacher, *Subs, *TeacherAids, *TeacherAssistants ");
    JLabel note3 = new JLabel(" Note that the your faculty list must be a plain text file:  Export to either CSV or tab delimited format.");
    BoxLayout layout = new BoxLayout(notesPanel, BoxLayout.Y_AXIS);
    notesPanel.setLayout(layout);
    notesPanel.add(note1);
    notesPanel.add(note2);
    notesPanel.add(note3);       
    getContentPane().add(notesPanel, BorderLayout.NORTH);
    getContentPane().add(scroll, BorderLayout.CENTER);
    getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    pack();
}

/**
 * Places the data from the specified stream into this table for display.  The data from the file must be in CSV format
 * @param is - an input stream which could be from a file or a network connection or URL.
 */
void insertData(InputStream is) {
    Scanner scan = new Scanner(is);
    String[] array;
    while (scan.hasNextLine()) {
        String line = scan.nextLine();
        if(line.indexOf(",")>-1)
            array = line.split(",");
        else
            array = line.split("\t");
        Object[] data = new Object[array.length];
        for (int i = 0; i < array.length; i++)
            data[i] = array[i];

        model.addRow(data);
    }
    table.setModel(model);
} 

public static void main(String args[]) {
    CsvRd frame = new CsvRd("Faculty List Example","C:/users.csv");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    
    
}
