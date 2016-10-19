import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ChargingSheduleGUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
    private JButton btnAdd;
    private DefaultTableModel tableModel;
    private JTextField txtField1;
    private JTextField txtField2;

    private ChargingSheduleGUI() {
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        JPanel eastPanel = new JPanel();
        btnAdd = new JButton("Add");
        eastPanel.add(btnAdd);
        JPanel northPanel = new JPanel();
        txtField1 = new JTextField();
        txtField2 = new JTextField();
       
        JLabel lblField1 = new JLabel("Column1");
        
        northPanel.add(lblField1);
        northPanel.add(txtField1);
        northPanel.add(txtField2);
        
       // txtField1.setPreferredSize(lblField1.getPreferredSize());

        add(northPanel, BorderLayout.NORTH);
        add(eastPanel, BorderLayout.EAST);
        add(pane,BorderLayout.CENTER);
        tableModel = new DefaultTableModel(new Object[]{"column1"},0);
        table.setModel(tableModel);
        btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = tableModel.getRowCount()+1;
                tableModel.addRow(new Object[]{"Column 1"});
                //tableModel.addRow(new Object[]{txtField1.getText(),txtField1.getText()});
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                ChargingSheduleGUI frm = new ChargingSheduleGUI();
                frm.setLocationByPlatform(true);
                frm.pack();
                frm.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frm.setVisible(true);

            }

        });
    }
} 