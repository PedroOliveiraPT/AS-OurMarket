/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;


import ActiveEntity.AEControl;
import ActiveEntity.AECustomer;
import ActiveEntity.AEManager;
import Communication.CClient;
import Communication.CServer;

import ActiveEntity.AECashier;
import ActiveEntity.AECustomer;
import ActiveEntity.AEManager;
import GUI.GUI_Manager;
import SACorridor.ICorridor_Customer;
import SACorridor.SACorridor;
import SACorridorHall.ICorridorHall_Customer;
import SACorridorHall.ICorridorHall_Manager;

import SACorridorHall.SACorridorHall;
import SAEntranceHall.IEntranceHall_Customer;
import SAEntranceHall.IEntranceHall_Manager;
import SAEntranceHall.SAEntranceHall;

import SAIdle.IIdle_Cashier;

import SAIdle.IIdle_Customer;
import SAIdle.IIdle_Manager;
import SAIdle.SAIdle;

import SAOutsideHall.IOutsideHall_Customer;
import SAOutsideHall.IOutsideHall_Manager;
import SAOutsideHall.SAOutsideHall;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentHall.IPaymentHall_Customer;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.IPaymentPoint_Cashier;
import SAPaymentPoint.IPaymentPoint_Customer;
import SAPaymentPoint.SAPaymentPoint;

import SACorridorHall.ICorridorHall_Customer;

/*
 * @author omp
 */
public class OIS extends javax.swing.JFrame {
    CServer cServer;
    
    javax.swing.JTextPane[] entranceHallJTextPanes;
    javax.swing.JTextPane[] corridorHallJTextPanes;
    javax.swing.JTextPane[] corridorJTextPanes;
    javax.swing.JTextPane[] payHallJTextPanes;
    javax.swing.JTextPane[] payJTextPanes;
    /**
     * Creates new form OIS
     */
    public OIS() {
        initComponents();
        
        this.entranceHallJTextPanes = new javax.swing.JTextPane[]{EH_pos01, EH_pos02, EH_pos03, EH_pos04, EH_pos05, EH_pos06};
        this.corridorHallJTextPanes = new javax.swing.JTextPane[]{CH_01_pos01, CH_01_pos02,CH_01_pos03,CH_02_pos01,CH_02_pos02,CH_02_pos03,CH_03_pos01,CH_03_pos02, CH_03_pos03};
        this.corridorJTextPanes = new javax.swing.JTextPane[]{C_01_pos01,C_01_pos02,C_02_pos01,C_02_pos02,C_03_pos01,C_03_pos02};
        this.payHallJTextPanes = new javax.swing.JTextPane[]{PH_pos01, PH_pos02};
        this.payJTextPanes = new javax.swing.JTextPane[]{P_pos01};


//        initCommunications();
        initOIS();
    }
    private void initOIS() {
<<<<<<< HEAD
        final int MAX_CUSTOMERS = 9;
=======
        final int MAX_CUSTOMERS = 90;
>>>>>>> ba5aa3e6cf4718ec934e739d75e0b1191000c635
        final int N_CORRIDOR_HALL = 3;
        final int N_CORRIDOR = 3;
        final int SIZE_ENTRANCE_HALL = 6;
        final int SIZE_CORRIDOR_HALL = 3;
        final int SIZE_CORRIDOR = 2;
        final int SIZE_PAYMENT_HALL = 2;
        final int SIZE_PAYMENT_POINT = 1;
        // ....
        
        System.out.println("Preparing OIS");
        final SAIdle idle = new SAIdle();
        final SAOutsideHall outsideHall =  new SAOutsideHall( MAX_CUSTOMERS );
        final SAEntranceHall entranceHall =  new SAEntranceHall( SIZE_ENTRANCE_HALL );
        final SACorridorHall[] corridorHalls = new SACorridorHall[N_CORRIDOR_HALL];
        final SACorridor[] corridors = new SACorridor[N_CORRIDOR_HALL];
        final SAPaymentHall paymentHall = new SAPaymentHall(SIZE_PAYMENT_HALL, corridors);
        final SAPaymentPoint paymentPoint = new SAPaymentPoint(SIZE_PAYMENT_POINT);
        
        GUI_Manager guim = new GUI_Manager(OH_all, entranceHallJTextPanes, corridorHallJTextPanes, corridorJTextPanes, 
                payHallJTextPanes, payJTextPanes);
        
        for (int i = 0; i < N_CORRIDOR_HALL; i++) {
            corridorHalls[i] = new SACorridorHall(SIZE_CORRIDOR_HALL);
            corridors[i] = new SACorridor(SIZE_CORRIDOR, corridorHalls[i]);
        }
        // outras SA ...
        
        final AECustomer[] aeCustomer = new AECustomer[ MAX_CUSTOMERS ];
        final AEManager aeManager = new AEManager((IIdle_Manager) idle,
                                                    (IOutsideHall_Manager) outsideHall,
                                                    (IEntranceHall_Manager) entranceHall,
                                                    (ICorridorHall_Manager[]) corridorHalls);
        
        final AECashier aeCashier = new AECashier((IIdle_Cashier) idle,
                                                   (IPaymentHall_Cashier) paymentHall,
                                                    (IPaymentPoint_Cashier) paymentPoint);
        
        for ( int i = 0; i < MAX_CUSTOMERS; i++ ) {
            aeCustomer[ i ] = new AECustomer( i,
                                              (IIdle_Customer) idle,
                                              (IOutsideHall_Customer) outsideHall,
                                              (IEntranceHall_Customer) entranceHall,
                                              (ICorridorHall_Customer[]) corridorHalls,
                                              (ICorridor_Customer[]) corridors,
                                              (IPaymentHall_Customer) paymentHall,
                                              (IPaymentPoint_Customer) paymentPoint,
                                              guim
            );
            aeCustomer[ i ].start();
        }
        aeCashier.start();
        aeManager.start();
        
        
        
        // ...
        
        try {
            aeManager.join();
            for ( int i = 0; i < MAX_CUSTOMERS; i++ )
                aeCustomer[ i ].join();
            aeCashier.join();
        } catch ( Exception ex ) {}    
        
        System.out.println("Finished simulation");
        System.out.println("Manager: "+ enti_manager.getX() + "; " + enti_manager.getY());
        enti_manager.setLocation(456, 18);
//        System.out.println("Manager: "+ enti_manager.getX() + "; " + enti_manager.getY());
        enti_manager.repaint();
    }
    
    private void initCommunications(){
        cServer = new CServer(401);
        cServer.connect();
        
//        CClient cClient = new CClient(400);
//        cClient.start();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel3 = new java.awt.Panel();
        OH_all = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EH_pos01 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        EH_pos02 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        EH_pos03 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        EH_pos04 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        EH_pos05 = new javax.swing.JTextPane();
        jScrollPane31 = new javax.swing.JScrollPane();
        EH_pos06 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        CH_01_pos01 = new javax.swing.JTextPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        CH_01_pos02 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        CH_01_pos03 = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        CH_02_pos01 = new javax.swing.JTextPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        CH_02_pos02 = new javax.swing.JTextPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        CH_02_pos03 = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        CH_03_pos01 = new javax.swing.JTextPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        CH_03_pos02 = new javax.swing.JTextPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        CH_03_pos03 = new javax.swing.JTextPane();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextPane17 = new javax.swing.JTextPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        C_03_pos01 = new javax.swing.JTextPane();
        jScrollPane19 = new javax.swing.JScrollPane();
        C_03_pos02 = new javax.swing.JTextPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        C_01_pos01 = new javax.swing.JTextPane();
        jScrollPane21 = new javax.swing.JScrollPane();
        C_01_pos02 = new javax.swing.JTextPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        C_02_pos01 = new javax.swing.JTextPane();
        jScrollPane23 = new javax.swing.JScrollPane();
        C_02_pos02 = new javax.swing.JTextPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        PH_pos01 = new javax.swing.JTextPane();
        jScrollPane25 = new javax.swing.JScrollPane();
        PH_pos02 = new javax.swing.JTextPane();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextPane26 = new javax.swing.JTextPane();
        jScrollPane27 = new javax.swing.JScrollPane();
        jTextPane27 = new javax.swing.JTextPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane30 = new javax.swing.JScrollPane();
        P_pos01 = new javax.swing.JTextPane();
        jScrollPane32 = new javax.swing.JScrollPane();
        jTextPane32 = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        enti_manager = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel3.setBackground(new java.awt.Color(153, 153, 153));

        OH_all.setEditable(false);
        OH_all.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        OH_all.setText("0 Costumers Waiting");
        OH_all.setToolTipText("");
        OH_all.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OH_allActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(OH_all, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(OH_all, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        jTextPane1.setEditable(false);
        jTextPane1.setText("Outside Hall");
        jScrollPane2.setViewportView(jTextPane1);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        EH_pos01.setEditable(false);
        EH_pos01.setBorder(null);
        EH_pos01.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        EH_pos01.setText("empty");
        jScrollPane1.setViewportView(EH_pos01);

        EH_pos02.setEditable(false);
        EH_pos02.setBorder(null);
        EH_pos02.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        EH_pos02.setText("empty");
        jScrollPane4.setViewportView(EH_pos02);

        EH_pos03.setEditable(false);
        EH_pos03.setBorder(null);
        EH_pos03.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        EH_pos03.setText("empty");
        jScrollPane5.setViewportView(EH_pos03);

        EH_pos04.setEditable(false);
        EH_pos04.setBorder(null);
        EH_pos04.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        EH_pos04.setText("empty");
        jScrollPane6.setViewportView(EH_pos04);

        EH_pos05.setEditable(false);
        EH_pos05.setBorder(null);
        EH_pos05.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        EH_pos05.setText("empty");
        EH_pos05.setToolTipText("");
        jScrollPane7.setViewportView(EH_pos05);

        EH_pos06.setEditable(false);
        EH_pos06.setBorder(null);
        EH_pos06.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        EH_pos06.setText("empty");
        jScrollPane31.setViewportView(EH_pos06);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane31, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTextPane2.setEditable(false);
        jTextPane2.setText("Entrance Hall");
        jScrollPane3.setViewportView(jTextPane2);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel1.setText("→");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel2.setText("--");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel3.setText("→");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel4.setText("→");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel5.setText("→");

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        CH_01_pos01.setEditable(false);
        CH_01_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_01_pos01.setText("empty");
        jScrollPane8.setViewportView(CH_01_pos01);

        CH_01_pos02.setEditable(false);
        CH_01_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_01_pos02.setText("empty");
        jScrollPane9.setViewportView(CH_01_pos02);

        CH_01_pos03.setEditable(false);
        CH_01_pos03.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_01_pos03.setText("empty");
        jScrollPane10.setViewportView(CH_01_pos03);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane9)
            .addComponent(jScrollPane10)
        );

        jPanel3.setBackground(new java.awt.Color(153, 153, 153));

        CH_02_pos01.setEditable(false);
        CH_02_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_02_pos01.setText("empty");
        jScrollPane11.setViewportView(CH_02_pos01);

        CH_02_pos02.setEditable(false);
        CH_02_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_02_pos02.setText("empty");
        jScrollPane12.setViewportView(CH_02_pos02);

        CH_02_pos03.setEditable(false);
        CH_02_pos03.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_02_pos03.setText("empty");
        jScrollPane13.setViewportView(CH_02_pos03);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane12)
            .addComponent(jScrollPane13)
        );

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        CH_03_pos01.setEditable(false);
        CH_03_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_03_pos01.setText("empty");
        jScrollPane14.setViewportView(CH_03_pos01);

        CH_03_pos02.setEditable(false);
        CH_03_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_03_pos02.setText("empty");
        jScrollPane15.setViewportView(CH_03_pos02);

        CH_03_pos03.setEditable(false);
        CH_03_pos03.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        CH_03_pos03.setText("empty");
        jScrollPane16.setViewportView(CH_03_pos03);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane15)
            .addComponent(jScrollPane16)
        );

        jTextPane17.setEditable(false);
        jTextPane17.setText("Corridor Hall");
        jScrollPane17.setViewportView(jTextPane17);

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        C_03_pos01.setEditable(false);
        C_03_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        C_03_pos01.setText("empty");
        jScrollPane18.setViewportView(C_03_pos01);

        C_03_pos02.setEditable(false);
        C_03_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        C_03_pos02.setText("empty");
        jScrollPane19.setViewportView(C_03_pos02);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane19)
        );

        jPanel6.setBackground(new java.awt.Color(153, 153, 153));

        C_01_pos01.setEditable(false);
        C_01_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        C_01_pos01.setText("empty");
        jScrollPane20.setViewportView(C_01_pos01);

        C_01_pos02.setEditable(false);
        C_01_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        C_01_pos02.setText("empty");
        jScrollPane21.setViewportView(C_01_pos02);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane21)
        );

        jPanel7.setBackground(new java.awt.Color(153, 153, 153));

        C_02_pos01.setEditable(false);
        C_02_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        C_02_pos01.setText("empty");
        jScrollPane22.setViewportView(C_02_pos01);

        C_02_pos02.setEditable(false);
        C_02_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        C_02_pos02.setText("empty");
        jScrollPane23.setViewportView(C_02_pos02);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane23)
        );

        jPanel8.setBackground(new java.awt.Color(153, 153, 153));

        PH_pos01.setEditable(false);
        PH_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        PH_pos01.setText("empty");
        jScrollPane24.setViewportView(PH_pos01);

        PH_pos02.setEditable(false);
        PH_pos02.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        PH_pos02.setText("empty");
        jScrollPane25.setViewportView(PH_pos02);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane25, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
            .addComponent(jScrollPane25)
        );

        jTextPane26.setEditable(false);
        jTextPane26.setText("Corridor");
        jScrollPane26.setViewportView(jTextPane26);

        jTextPane27.setEditable(false);
        jTextPane27.setText("Payment Hall");
        jScrollPane27.setViewportView(jTextPane27);

        jPanel10.setBackground(new java.awt.Color(153, 153, 153));

        P_pos01.setEditable(false);
        P_pos01.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        P_pos01.setText("empty");
        jScrollPane30.setViewportView(P_pos01);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane30, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
        );

        jTextPane32.setEditable(false);
        jTextPane32.setText("Payment");
        jScrollPane32.setViewportView(jTextPane32);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel6.setText("→");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel7.setText("→");

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel8.setText("→");

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel9.setText("→");

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel10.setText("↓");

        jLabel11.setIcon(new javax.swing.ImageIcon("C:\\Users\\Luís\\Downloads\\cashier (1).png")); // NOI18N
        jLabel11.setText("Cashier");

        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\Luís\\Downloads\\customer.png")); // NOI18N
        jLabel12.setText("Costumer");

        enti_manager.setIcon(new javax.swing.ImageIcon("C:\\Users\\Luís\\Downloads\\manager.png")); // NOI18N
        enti_manager.setText("Manager");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel1)))))
                            .addComponent(enti_manager))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel9))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11))
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel12)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel1)
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addGap(35, 35, 35)
                                .addComponent(jLabel4))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(jLabel7)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(jLabel6)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel9))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel8)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(enti_manager)
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(45, 45, 45)
                                    .addComponent(jLabel3))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OH_allActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OH_allActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OH_allActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException {
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
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OIS().setVisible(true);
            }
        });

     

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane CH_01_pos01;
    private javax.swing.JTextPane CH_01_pos02;
    private javax.swing.JTextPane CH_01_pos03;
    private javax.swing.JTextPane CH_02_pos01;
    private javax.swing.JTextPane CH_02_pos02;
    private javax.swing.JTextPane CH_02_pos03;
    private javax.swing.JTextPane CH_03_pos01;
    private javax.swing.JTextPane CH_03_pos02;
    private javax.swing.JTextPane CH_03_pos03;
    private javax.swing.JTextPane C_01_pos01;
    private javax.swing.JTextPane C_01_pos02;
    private javax.swing.JTextPane C_02_pos01;
    private javax.swing.JTextPane C_02_pos02;
    private javax.swing.JTextPane C_03_pos01;
    private javax.swing.JTextPane C_03_pos02;
    private javax.swing.JTextPane EH_pos01;
    private javax.swing.JTextPane EH_pos02;
    private javax.swing.JTextPane EH_pos03;
    private javax.swing.JTextPane EH_pos04;
    private javax.swing.JTextPane EH_pos05;
    private javax.swing.JTextPane EH_pos06;
    private javax.swing.JTextField OH_all;
    private javax.swing.JTextPane PH_pos01;
    private javax.swing.JTextPane PH_pos02;
    private javax.swing.JTextPane P_pos01;
    private javax.swing.JLabel enti_manager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane30;
    private javax.swing.JScrollPane jScrollPane31;
    private javax.swing.JScrollPane jScrollPane32;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane17;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane26;
    private javax.swing.JTextPane jTextPane27;
    private javax.swing.JTextPane jTextPane32;
    private java.awt.Panel panel3;
    // End of variables declaration//GEN-END:variables
}
