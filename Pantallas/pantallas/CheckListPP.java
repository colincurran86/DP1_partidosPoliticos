package pantallas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import pantallas.CheckListPartPol.CheckListRenderer;
import pantallas.CheckListPartPol.CheckableItem;

public class CheckListPP extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CheckListPP dialog = new CheckListPP(Configuracion.listaStrPP);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	/*public CheckListPP() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
	}*/
	public CheckListPP(  String[] strs ) {
	    //super("CheckList Example");
	    setModal(true);
	    //String[] strs = { "swing", "home", "basic", "metal", "JList" };    
	    final JList list = new JList(createData(strs));

	    list.setCellRenderer(new CheckListRenderer());
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setBorder(new EmptyBorder(0, 4, 0, 0));
	    list.addMouseListener(new MouseAdapter() {
	      public void mouseClicked(MouseEvent e) {
	        int index = list.locationToIndex(e.getPoint());
	        CheckableItem item = (CheckableItem) list.getModel() .getElementAt(index);
	    
	        item.setSelected(!item.isSelected());
	        Rectangle rect = list.getCellBounds(index, index);
	        list.repaint(rect);
	      }
	    });
	    JScrollPane sp = new JScrollPane(list);
	    JScrollPane textPanel = new JScrollPane();
	    JButton printButton = new JButton("aceptar");
	    printButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        ListModel model = list.getModel();
	        int n = model.getSize();
	        for (int i = 0; i < n; i++) {
	          CheckableItem item = (CheckableItem) model.getElementAt(i);
	          if (item.isSelected()) {
	        	    Carga.ppEscogidos.add(  Configuracion.listaPP.get(i)     ) ;
	        	    
	        	    //System.exit(0);
	        	  //  System.out.println(i);
	        	   // System.out.println(  Configuracion.listaPP.get(i).getNombre()     );
	        	  //aquiii es 
	        	  
	        	  
	          //  textArea.append(item.toString());
	           // textArea.append(System.getProperty("line.separator"));
	          }
	        }
	      }
	    });
	    JPanel panel = new JPanel(new GridLayout(2, 1));
	    panel.add(printButton);

	    getContentPane().add(sp, BorderLayout.CENTER);
	    getContentPane().add(panel, BorderLayout.EAST);
	    getContentPane().add(textPanel, BorderLayout.SOUTH);
	  }

	  private CheckableItem[] createData(String[] strs) {
	    int n = strs.length;
	    CheckableItem[] items = new CheckableItem[n];
	    for (int i = 0; i < n; i++) {
	      items[i] = new CheckableItem(strs[i]);
	    }
	    return items;
	  }

	  class CheckableItem {
	    private String str;

	    private boolean isSelected;

	    public CheckableItem(String str) {
	      this.str = str;
	      isSelected = false;
	    }

	    public void setSelected(boolean b) {
	      isSelected = b;
	    }

	    public boolean isSelected() {
	      return isSelected;
	    }

	    public String toString() {
	      return str;
	    }
	  }

	  class CheckListRenderer extends JCheckBox implements ListCellRenderer {

	    public CheckListRenderer() {
	      setBackground(UIManager.getColor("List.textBackground"));
	      setForeground(UIManager.getColor("List.textForeground"));
	    }

	    public Component getListCellRendererComponent(JList list, Object value,
	        int index, boolean isSelected, boolean hasFocus) {
	      setEnabled(list.isEnabled());
	      setSelected(((CheckableItem) value).isSelected());
	      setFont(list.getFont());
	      setText(value.toString());
	      return this;
	    }
	  }


}
