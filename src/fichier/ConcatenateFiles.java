package fichier;
import java.io.*;


import java.awt.Dimension;
import java.io.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ConcatenateFiles extends JFrame {

	 JTextArea textArea = new JTextArea("");
	    JButton btnSelectFile1 = new JButton("Sélectionner le premier fichier");
	    JButton btnSelectFile2 = new JButton("Sélectionner le deuxième fichier");
	    JButton btnConcatenate = new JButton("Concaténer fichiers");
	    JPanel panel, panelTop, panelBottom;
	    File file1, file2;

	    public ConcatenateFiles() {
	        super("Concaténation de Fichiers");
	        panelTop = new JPanel();
	        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.LINE_AXIS));
	        panelTop.setMaximumSize(new Dimension(600, 50));
	        panelTop.setPreferredSize(new Dimension(600, 50));
	        panelTop.add(btnSelectFile1);
	        panelTop.add(btnSelectFile2);
	        panelTop.add(btnConcatenate);

	        panelBottom = new JPanel();
	        panelBottom.setLayout(new BoxLayout(panelBottom, BoxLayout.LINE_AXIS));
	        textArea.setMaximumSize(new Dimension(600, 500));
	        textArea.setPreferredSize(new Dimension(600, 500));
	        panelBottom.add(textArea);

	        this.setVisible(true);
	        setSize(600, 600);
	        panel = new JPanel();
	        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	        panel.add(panelTop);
	        panel.add(panelBottom);
	        getContentPane().add(panel);

	        // Add action listeners for buttons
	        btnSelectFile1.addActionListener(e -> selectFile1());
	        btnSelectFile2.addActionListener(e -> selectFile2());
	        btnConcatenate.addActionListener(e -> concatenateFiles());
	    }

	    private void selectFile1() {
	        JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            file1 = fileChooser.getSelectedFile();
	        }
	    }

	    private void selectFile2() {
	        JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            file2 = fileChooser.getSelectedFile();
	        }
	    }

	    private void concatenateFiles() {
	        if (file1 == null || file2 == null) {
	            return;
	        }

	        JFileChooser fileChooser = new JFileChooser();
	        int returnValue = fileChooser.showSaveDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	            File outputFile = fileChooser.getSelectedFile();
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
	                writeFromFileToWriter(file1, writer);
	                writeFromFileToWriter(file2, writer);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    private void writeFromFileToWriter(File file, BufferedWriter writer) throws IOException {
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                writer.write(line);
	                writer.newLine();
	            }
	        }
	    }
    public static void main(String[] args) {
        new ConcatenateFiles();
    }
}
