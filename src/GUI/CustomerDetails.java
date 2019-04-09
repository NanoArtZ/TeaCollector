/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Nuwan Dissanayake | CEO-Nano Artz Technologies
 */
public final class CustomerDetails extends javax.swing.JFrame {

    /**
     * Creates new form CustomerDetails
     */
    public CustomerDetails() {
        initComponents();
        
        showDate();
        showTime();
        FullScreenMethod();
        LoadCustomerNameOrIDToCustomerPanel();
    }

    //All Custom Code - Start
    
    //    Date&Time
    void showDate() {
        Datelbl.setText(new SimpleDateFormat("MM-dd-yyyy").format(new java.util.Date()));
    }

    void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Datelbl1.setText(new SimpleDateFormat("hh:mm:ss").format(new java.util.Date()));
            }
        }).start();
    }
//    Date&Time Method END
    

    
    //Full Screen Method Start
    public void FullScreenMethod() {

        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            setBounds(0, 0, screenSize.width, screenSize.height);
            setVisible(true);
            Toolkit tk = Toolkit.getDefaultToolkit();

            int xsize = (int) tk.getScreenSize().getWidth();
            int ysize = (int) tk.getScreenSize().getHeight();

            this.setSize(xsize, ysize);
            this.setExtendedState(this.MAXIMIZED_BOTH);

        } catch (HeadlessException e) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    //Full Screen Method END
    
    //Auto Load Customer Name or ID to Customer Details Combo box Sales Panel Method Start
    public void LoadCustomerNameOrIDToCustomerPanel() {
        try {
            CDCustomerSearchCB.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {

                    if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_UP && e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_SHIFT) {

                        String TypedTxt = CDCustomerSearchCB.getEditor().getItem().toString();

                        if (!TypedTxt.isEmpty()) {

                            try {
                                CDCustomerSearchCB.removeAllItems();
                                int st = 0;

                                Statement s = DBConnector.DBConnection.GetMyConnection().createStatement();
                                ResultSet rs = s.executeQuery("SELECT PassBookNo FROM customerdetails WHERE PassBookNo LIKE '%" + TypedTxt + "%' ");

                                Vector StoreCBData = new Vector();

                                while (rs.next()) {
                                    if (rs.getString("PassBookNo").toLowerCase().contains(TypedTxt.toLowerCase())) {
                                        StoreCBData.add(rs.getString("PassBookNo"));
                                        st++;
                                    }
                                }

//                                rs = s.executeQuery("SELECT CusName FROM customerdetails WHERE CusName LIKE '%" + TypedTxt + "%'");
//
//                                while (rs.next()) {
//                                    if (rs.getString("CusName").toLowerCase().contains(TypedTxt.toLowerCase())) {
//                                        StoreCBData.add(rs.getString("CusName"));
//                                        st++;
//                                    }
//                                }

                                rs.close();
                                s.close();

                                DefaultComboBoxModel dcm = new DefaultComboBoxModel(StoreCBData);
                                CDCustomerSearchCB.setModel(dcm);

                                CDCustomerSearchCB.getEditor().setItem(TypedTxt);
                                CDCustomerSearchCB.setSelectedItem(TypedTxt);
                                JTextField TextField = (JTextField) e.getSource();
                                TextField.setCaretPosition(TextField.getDocument().getLength());

                                if (st != 0) {
                                    CDCustomerSearchCB.showPopup();

                                    if (st > 10) {
                                        CDCustomerSearchCB.setMaximumRowCount(10);
                                    } else {
                                        CDCustomerSearchCB.setMaximumRowCount(st);
                                    }
                                } else {
                                    CDCustomerSearchCB.hidePopup();
                                }

                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        } else {
                            CDCustomerSearchCB.hidePopup();
                            CDCustomerSearchCB.removeAllItems();
                        }
                    }
                }
            });
        } catch (Exception e) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    //Auto Load Customer Name or ID to Customer Details Combo box Sales Panel Method END
    
    
    
    // Customer Details Search combo box Methood Start
    public void CustomerComboBox() {
        try {
            if (CDCustomerSearchCB.getSelectedItem() != null) {
                String TypedTextOnCB = CDCustomerSearchCB.getSelectedItem().toString();
                Statement st = DBConnector.DBConnection.GetMyConnection().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM customerdetails where PassBookNo = '" + TypedTextOnCB + "'  ");    //|| CusName = '" + TypedTextOnCB + "'

                if(rs.next()){
                    
                    CusPassBookNoorNameTF.setText(rs.getString("CusName"));
                    cusAddressTA.setText(rs.getString("CusAddress"));
                    cusNICTF.setText(rs.getString("CusNIC"));
//                    EnterDateJC.setDate(Date.valueOf(rs.getString("CusDateStart")));
                    cusPhoneNoTF.setText(rs.getString("CusPhoneNo"));
                }else{
                    //AutoGenCusID();
                    CusPassBookNoorNameTF.setText(null);
                    cusAddressTA.setText(null);
                    cusNICTF.setText(null);
                    
//                    cusNumOrNameLbl.setText("mdia fmd;a wxlh fyda ku");
//                    cusNameLbl.setText("mdßfNda.slhdf.a ku");
                }
            }
            
        } catch (Exception e) {
            Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null, e);
        }
    }
// Customer Details Search combo box Methood END
    
//All Custom Code - END
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cusNumOrNameLbl = new javax.swing.JLabel();
        cusNameLbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Datelbl = new javax.swing.JLabel();
        CusPassBookNoorNameTF = new javax.swing.JTextField();
        cusPhoneNoTF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cusNICTF = new javax.swing.JTextField();
        cusEnterDateJC = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        CDCustomerSearchCB = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        cusAddressTA = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        Datelbl1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(18, 124, 193));
        jPanel1.setMinimumSize(new java.awt.Dimension(1024, 768));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 768));

        jLabel1.setFont(new java.awt.Font("FMBindumathi", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("mdßfNda.sl úia;r");

        jLabel2.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ÿrl:k wxlh");

        cusNumOrNameLbl.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        cusNumOrNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        cusNumOrNameLbl.setText("mdia fmd;a wxlh");

        cusNameLbl.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        cusNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        cusNameLbl.setText("mdßfNda.slhdf.a ku");

        jLabel5.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(",smskh");

        jLabel6.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("yeÿkqïm;a wxlh");

        jLabel7.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("oskh");

        Datelbl.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Datelbl.setForeground(new java.awt.Color(255, 255, 255));

        CusPassBookNoorNameTF.setFont(new java.awt.Font("FMBindumathi", 0, 16)); // NOI18N
        CusPassBookNoorNameTF.setForeground(new java.awt.Color(51, 51, 51));
        CusPassBookNoorNameTF.setMinimumSize(new java.awt.Dimension(250, 30));
        CusPassBookNoorNameTF.setName(""); // NOI18N
        CusPassBookNoorNameTF.setPreferredSize(new java.awt.Dimension(250, 30));

        cusPhoneNoTF.setFont(new java.awt.Font("FMBindumathi", 0, 16)); // NOI18N
        cusPhoneNoTF.setForeground(new java.awt.Color(51, 51, 51));
        cusPhoneNoTF.setMinimumSize(new java.awt.Dimension(250, 30));
        cusPhoneNoTF.setName(""); // NOI18N
        cusPhoneNoTF.setPreferredSize(new java.awt.Dimension(250, 30));

        jLabel9.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("we;=,;a l, oskh");

        cusNICTF.setFont(new java.awt.Font("FMBindumathi", 0, 16)); // NOI18N
        cusNICTF.setForeground(new java.awt.Color(51, 51, 51));
        cusNICTF.setMinimumSize(new java.awt.Dimension(250, 30));
        cusNICTF.setName(""); // NOI18N
        cusNICTF.setPreferredSize(new java.awt.Dimension(250, 30));

        cusEnterDateJC.setEnabled(false);
        cusEnterDateJC.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cusEnterDateJC.setPreferredSize(new java.awt.Dimension(250, 30));

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 204, 102));
        jButton2.setText("we;=,;a lrkak");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 204, 102));
        jButton3.setText("bj;a lrkak");

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 204, 102));
        jButton4.setText("hdj;ald,Sk lrkak");

        CDCustomerSearchCB.setEditable(true);
        CDCustomerSearchCB.setFont(new java.awt.Font("FMBindumathi", 0, 16)); // NOI18N
        CDCustomerSearchCB.setMinimumSize(new java.awt.Dimension(250, 30));
        CDCustomerSearchCB.setPreferredSize(new java.awt.Dimension(250, 30));
        CDCustomerSearchCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CDCustomerSearchCBActionPerformed(evt);
            }
        });

        cusAddressTA.setColumns(20);
        cusAddressTA.setFont(new java.awt.Font("FMBindumathi", 0, 16)); // NOI18N
        cusAddressTA.setRows(5);
        jScrollPane1.setViewportView(cusAddressTA);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ex : Mar 6,2019");

        Datelbl1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Datelbl1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("FMBindumathi", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("fõ,dj");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Cancel_50px_1.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Minus_50px_1.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(497, 497, 497)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Datelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Datelbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel10)))
                .addGap(10, 10, 10)
                .addComponent(jLabel4))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(cusNumOrNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(CDCustomerSearchCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(cusNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(CusPassBookNoorNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(cusNICTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(cusEnterDateJC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel3))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(cusPhoneNoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Datelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(Datelbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cusNumOrNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CDCustomerSearchCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cusNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CusPassBookNoorNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cusNICTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cusEnterDateJC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cusPhoneNoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            
            String cusPassNo = CDCustomerSearchCB.getSelectedItem().toString();
            String cusName = CusPassBookNoorNameTF.getText();
            String cusAddress = cusAddressTA.getText();
            String cusNIC = cusNICTF.getText();
//            String cusDateStart = cusEnterDateJC.getDate().toString();
            String cusPhone = cusPhoneNoTF.getText();
            
            Statement s = DBConnector.DBConnection.GetMyConnection().createStatement();
            s.executeUpdate("INSERT into customerdetails (PassBookNo, CusName, CusAddress, CusNIC, CusDateStart, CusPhoneNo)"
                    + " values('"+cusPassNo+"' , '"+cusName+"' , '"+cusAddress+"' , '"+cusNIC+"' , '"+"oldcus"+"' , '"+cusPhone+"') ");
        
        JOptionPane.showMessageDialog(rootPane, "Details Saved ! ");
        } catch (Exception e) {
           Logger.getLogger(CustomerDetails.class.getName()).log(Level.SEVERE, null,e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void CDCustomerSearchCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CDCustomerSearchCBActionPerformed
       CustomerComboBox();
    }//GEN-LAST:event_CDCustomerSearchCBActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_jLabel10MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CDCustomerSearchCB;
    private javax.swing.JTextField CusPassBookNoorNameTF;
    private javax.swing.JLabel Datelbl;
    private javax.swing.JLabel Datelbl1;
    private javax.swing.JTextArea cusAddressTA;
    private com.toedter.calendar.JDateChooser cusEnterDateJC;
    private javax.swing.JTextField cusNICTF;
    private javax.swing.JLabel cusNameLbl;
    private javax.swing.JLabel cusNumOrNameLbl;
    private javax.swing.JTextField cusPhoneNoTF;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
