package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.Lexer;
import model.Token;

public class Tela_principal extends JFrame {

    JFileChooser fileChooser = new JFileChooser();
    
    JLabel labelInf;

    JPanel painelOutPut = new JPanel();

    JMenuBar barra = new JMenuBar();

    JMenu menuFicheiro = new JMenu("File");
    JMenuItem itemFile = new JMenuItem("Open file");

    JMenu menuSalvar = new JMenu("Save");
    JMenuItem itemSave = new JMenuItem("Save");

    JMenu menuVoltar = new JMenu("Undo");

    JMenu menuReVoltar = new JMenu("Redo");

    JMenu menuRun = new JMenu("Run");
    JMenuItem itemRun = new JMenuItem("Run Project");

    JMenu menuClean = new JMenu("Clean");
    JMenuItem itemClean = new JMenuItem("Clean Project");

    JMenu menuOpcoes = new JMenu("Settings");

    JTextArea areaTexto = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(areaTexto);
    JList<String> listaNumeros = new JList<>();
    DefaultTableModel tabelaLexemaIdentificador;
    JTable tabela;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();

    Lexer analisador = new Lexer();

    private Tela_principal() {
        this.accaoJMenuBar();
        this.menuBar();
        this.configuracaoTela();
    }

    private void configuracaoTela() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Compilador-Trabalho Semestral de LPC");
        setLocationRelativeTo(null);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.painelOutPut();
        this.textArea();
        this.tabelaLexemaIdentificador();

        setVisible(true);
    }

    private void painelOutPut() {
        painelOutPut.setLayout(new BorderLayout());
        painelOutPut.setPreferredSize(new Dimension(getWidth(), 120)); // Definindo tamanho
        painelOutPut.setBorder(BorderFactory.createMatteBorder(20, 2, 2, 2, new Color(60, 173, 250)));
        painelOutPut.setBackground(Color.WHITE);
        add(painelOutPut, BorderLayout.SOUTH); // Adicionando o painel na parte inferior

        JLabel label = new JLabel("ÁREA DE EXECUÇÃO.:");
        label.setBounds(2, 0, 200, 25);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        label.setForeground(Color.BLACK);
        painelOutPut.setLayout(null);
        
        labelInf = new JLabel();
        labelInf.setBounds(2, 20, 500, 30);
        labelInf.setFont(new Font("Arial", Font.PLAIN, 16));
        labelInf.setForeground(Color.DARK_GRAY);

        painelOutPut.add(label);
        painelOutPut.add(labelInf);
    }

    private void menuBar() {
        setJMenuBar(barra);
        barra.setBackground(new Color(60, 173, 250));
        menuSalvar.setIcon(new ImageIcon(getClass().getResource("/image/save.png")));
        menuFicheiro.setIcon(new ImageIcon(getClass().getResource("/image/file.png")));
        menuVoltar.setIcon(new ImageIcon(getClass().getResource("/image/undo.png")));
        menuReVoltar.setIcon(new ImageIcon(getClass().getResource("/image/rundo.png")));
        menuRun.setIcon(new ImageIcon(getClass().getResource("/image/run.png")));
        menuClean.setIcon(new ImageIcon(getClass().getResource("/image/clean.png")));
        menuOpcoes.setIcon(new ImageIcon(getClass().getResource("/image/settings.png")));

        itemFile.setIcon(new ImageIcon(getClass().getResource("/image/fileItem.png")));
        itemRun.setIcon(new ImageIcon(getClass().getResource("/image/runItem.png")));
        itemClean.setIcon(new ImageIcon(getClass().getResource("/image/cleanItem.png")));
        itemSave.setIcon(new ImageIcon(getClass().getResource("/image/saveItem.png")));
        barra.add(menuFicheiro);
        menuFicheiro.add(itemFile);

        barra.add(menuSalvar);
        menuSalvar.add(itemSave);
        menuSalvar.setBackground(Color.BLACK);

        barra.add(menuVoltar);
        barra.add(menuReVoltar);
        barra.add(menuRun);
        menuRun.add(itemRun);

        barra.add(menuClean);
        menuClean.add(itemClean);

        barra.add(menuOpcoes);
    }

    private void textArea() {
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (int i = 1; i <= 1000; i++) { // Defina o número de linhas como desejar
            modeloLista.addElement(String.valueOf(i));
        }
        listaNumeros.setModel(modeloLista);
        listaNumeros.setBackground(new Color(240, 240, 240));
        listaNumeros.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        listaNumeros.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        listaNumeros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNumeros.setFixedCellWidth(30); // Define a largura fixa para os números das linhas
        listaNumeros.setFixedCellHeight(areaTexto.getFontMetrics(areaTexto.getFont()).getHeight());
        listaNumeros.setFixedCellHeight(22);
        scrollPane.setRowHeaderView(listaNumeros);
        add(scrollPane, BorderLayout.CENTER);

        areaTexto.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Ajuste para alinhar com a numeração
        areaTexto.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        areaTexto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listaNumeros.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listaNumeros.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listaNumeros.repaint();
            }
        });
    }

    private void tabelaLexemaIdentificador() {
        Object[][] data = {};
        Object[] columns = {"Liha", "Lexema", "Token"};
        tabelaLexemaIdentificador = new DefaultTableModel(data, columns);
        tabela = new JTable(tabelaLexemaIdentificador);
        JScrollPane tabelaScrollPane = new JScrollPane(tabela);
        add(tabelaScrollPane, BorderLayout.EAST); // Adicionando a tabela à direita da área de texto
    }

    public static void main(String[] args) {
        new Tela_principal();
    }

    private void accaoJMenuBar() {

        itemRun.addActionListener(new ActionListener() {
            long startTime = System.nanoTime();
            @Override
            public void actionPerformed(ActionEvent e) {
                analisador.tokenize(areaTexto.getText());
                ArrayList<Token> tokens = analisador.getTokens();

                // Limpa a tabela antes de adicionar novos dados
                tabelaLexemaIdentificador.setRowCount(0);

                // Adiciona cada token à tabela
                for (Token token : tokens) {
                    Object[] data = {token.getLineNumber(), token.getLexeme(), token.getTokenType()};
                    tabelaLexemaIdentificador.addRow(data);
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1_000_000_000;
                labelInf.setText("BUILD SUCCESSFUL (total time: " + duration + " seconds)");
            }
        });

        itemClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                areaTexto.setText("");
                analisador.clearTokens();
                tabelaLexemaIdentificador.setRowCount(0);
            }
        });
        itemFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTexto();
            }
        });
        
        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = areaTexto.getText();
                if (!texto.isEmpty()) {
                    salvarTexto();
                } else {
                    JOptionPane.showMessageDialog(null, "A área de texto está vazia. Não há nada para salvar.", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    private void salvarTexto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Como");
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                writer.write(areaTexto.getText());
                JOptionPane.showMessageDialog(this, "Texto salvo com sucesso!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void carregarTexto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir");

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
                StringBuilder conteudo = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    conteudo.append(linha).append("\n");
                }
                areaTexto.setText(conteudo.toString());
                JOptionPane.showMessageDialog(this, "Arquivo carregado com sucesso!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
