import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Vincibuttons extends JButton {
	Vincibuttons(String text) {
		super();
		this.setBackground(Color.white);
		this.setText(text);
	}
}

class ouverture implements ActionListener {
	JTextArea text;
	JTextField espasnonfichye;
	ouverture(JTextArea editortext, JTextField nonfichye) {
		text = editortext;
		espasnonfichye = nonfichye;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(espasnonfichye.getText().length() == 0) {
			JFileChooser ouvrir = new JFileChooser();
			int result = ouvrir.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				try {
					String contenu = "";
					String chemin = ouvrir.getSelectedFile().getAbsolutePath();
					espasnonfichye.setText(chemin);
					File fichier = new File(chemin);
					Scanner scan = new Scanner(fichier);
					while(scan.hasNextLine()) {
						contenu += scan.nextLine() + "\n";
					}
					text.setText(contenu);
				}catch(Exception err) {
					err.printStackTrace();
				}
			}
		}else {
			try {
				String contenu = "";
				String chemin = espasnonfichye.getText();
				espasnonfichye.setText(chemin);
				File fichier = new File(chemin);
				Scanner scan = new Scanner(fichier);
				while(scan.hasNextLine()) {
					contenu += scan.nextLine() + "\n";
				}
				text.setText(contenu);
				scan.close();
			}catch(Exception err) {
				err.printStackTrace();
			}
		}
	}
}

class sauvegarde implements ActionListener {
	JTextArea text;
	JTextField espasnonfichye;
	sauvegarde(JTextArea editortext, JTextField nonfichye) {
		text = editortext;
		espasnonfichye = nonfichye;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(espasnonfichye.getText().length() == 0) {
			JFileChooser enregistrer = new JFileChooser();
			int result = enregistrer.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				try {
					String chemin = enregistrer.getSelectedFile().getAbsolutePath();
					espasnonfichye.setText(chemin);
					FileWriter monfichier = new FileWriter(chemin);
				    String contenutext = text.getText();
					monfichier.write(contenutext);
					monfichier.flush();
					monfichier.close();
				}catch(Exception err) {
					err.printStackTrace();
				}
			}
		}else {
			try {
				String chemin = espasnonfichye.getText();
				FileWriter monfichier = new FileWriter(chemin);
			    String contenutext = text.getText();
				monfichier.write(contenutext);
				monfichier.flush();
				monfichier.close();
			}catch(Exception err) {
				err.printStackTrace();
			}
		}
		
	}
}

class Options implements ActionListener {
	JTextArea text;
	Options(JTextArea t){
		text = t;
	}
	static void copyto(List<String> tab, int l1, int l2) {
		tab.set(l2,tab.get(l1));
	}
	static void swap(List<String> tab, int l1, int l2) {
		String temp = tab.get(l1);
		tab.set(l1,tab.get(l2));
		tab.set(l2, temp);
	}
	static void remove(List<String> tab, int l1) {
		tab.set(l1,"");
	}
	static void replace(List<String> tab, int l1, String replacement) {
		replacement = replacement.replaceAll("_", " ");
		tab.set(l1,replacement);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<String> content = Arrays.asList(text.getText().split("\n"));
		for(String ligne: content) {
			if(ligne.contains("copyto")) {
				List<String> ligneContent = Arrays.asList(ligne.split(" "));
				int i1 = Integer.valueOf(ligneContent.get(0)) - 1;
				int i2 = Integer.valueOf(ligneContent.get(2)) - 1;
				copyto(content,i1,i2);
				content.set(content.indexOf(ligne),"");
			}
			if(ligne.contains("remove")) {
				List<String> ligneContent = Arrays.asList(ligne.split(" "));
				int i1 = Integer.valueOf(ligneContent.get(0)) - 1;
				remove(content,i1);
				content.set(content.indexOf(ligne),"");
			}
			if(ligne.contains("swap")) {
				List<String> ligneContent = Arrays.asList(ligne.split(" "));
				int i1 = Integer.valueOf(ligneContent.get(0)) - 1;
				int i2 = Integer.valueOf(ligneContent.get(2)) - 1;
				swap(content,i1,i2);
				content.set(content.indexOf(ligne),"");
			}
			if(ligne.contains("replace") && ligne.contains("by")) {
				List<String> ligneContent = Arrays.asList(ligne.split(" "));
				int i1 = Integer.valueOf(ligneContent.get(1)) - 1;
				String rep = ligneContent.get(3);
				replace(content,i1,rep);
				content.set(content.indexOf(ligne),"");
			}
		}
		text.setText(String.join("\n", content));
	}
}

public class MyTextEditor {

	public static void customSetFont(JComponent...args) {
		for(JComponent w: args) {
			w.setFont(new Font("Hack",Font.PLAIN,14));
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fenetre = new JFrame();
		
		JPanel pan = new JPanel();
		
		JLabel labelnon = new JLabel("Filename");
		JTextField nonfichye = new JTextField();
		nonfichye.setPreferredSize(new Dimension(220,40));
		Vincibuttons ouvrir = new Vincibuttons("Open");
		Vincibuttons modif = new Vincibuttons("Run cmd");
		Vincibuttons sauvegarder = new Vincibuttons("Save");
		JTextArea zone_texte = new JTextArea(24,103);
		//zone_texte.setPreferredSize(new Dimension(820,405));
		JScrollPane sp = new JScrollPane(zone_texte,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		customSetFont(nonfichye,ouvrir,modif,sauvegarder,zone_texte);
		ouvrir.addActionListener(new ouverture(zone_texte,nonfichye));
		sauvegarder.addActionListener(new sauvegarde(zone_texte,nonfichye));
		modif.addActionListener(new Options(zone_texte));
		pan.add(labelnon);
		pan.add(nonfichye);
		pan.add(ouvrir);
		pan.add(modif);
		pan.add(sauvegarder);
		pan.add(sp);
		
		
		fenetre.setTitle("Editor");
		fenetre.setSize(new Dimension(850,500));
		fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fenetre.setVisible(true);
		fenetre.setResizable(false);
		fenetre.setContentPane(pan);
	}

}
