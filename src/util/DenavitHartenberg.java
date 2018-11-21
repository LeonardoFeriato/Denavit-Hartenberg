/**
 * Fonte: Livro Príncipios da Mecatrônica - João Maurício Rosário (2005) pg202-204
 */
package util;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DenavitHartenberg {
    
    //ArrysList de modelos de tabela.
    private final ArrayList<DefaultTableModel> arrTableModel;
    //Houve erro na execução
    private boolean error = false;

    /**
     * Construtor.
     */
    public DenavitHartenberg() {
        this.arrTableModel = new ArrayList<>();
    }
    
    /**
     * Calcula Sistema de Denavit-Hartenberg em Matrizes de Transformação Homogênea. 
     * @param table Tabela onde estão os valores do Sistema de Denavit-Hartenberg.
     */
    public void run(JTable table){        
        /**
         * Objetivo é fazer o calculo da Matriz de Transformação Homogênea
         * 
         * Cada linha da tabela de Denavit-Hartenberg é uma matriz.
         */
        int row = 0; //Linha atual da tabela
        
        while(row < table.getRowCount()){
            /**
             * Enquanto não acabar as linhas da tabela faça...
             */
            
            /**
             * Coletando Dados
             */
            try {
                double a = Double.parseDouble(String.valueOf(table.getValueAt(row, 0)));
                double alfa = Double.parseDouble(String.valueOf(table.getValueAt(row, 1)));
                double d = Double.parseDouble(String.valueOf(table.getValueAt(row, 2)));
                double teta = Double.parseDouble(String.valueOf(table.getValueAt(row, 3)));

                /**
                 * Inserindo o novo modelo de tabela na ArrayList
                 */
                DefaultTableModel m = toTableModel(a, alfa, d, teta);
                setTableModel(m);
                
            } catch(Exception e) {                
                //Indicando que houve erro
                setError(true);
                
                //Forçar a sair do while
                row = table.getRowCount()+1; 
            }
            
            //Incrementando a variável linha
            row++;            
        }
        
        //Se não houver erro
        if (!isError()) {
            /**
             * Inicia-se a fase de multiplicação de matrizes
             */
            multiplyArray();
        }
        
    }

    /**
     * Retorna a ArrayList contendo os modelos de tabelas.
     * @return ArrayList das DefaultTableModels geradas.
     */
    public ArrayList<DefaultTableModel> getTableModel() {        
        return arrTableModel;
    }
    
    /**
     * Retorna o tamanho da ArrayList.
     * @return O valor inteiro do tamnaho da ArrayList.
     */
    public int size(){
        return arrTableModel.size();
    }

    /**
     * Houve algum erro de execução.
     * @return O valor TRUE se houve algum erro de execução.
     */
    public boolean isError() {
        return error;
    }

    /**
     * Altera o valor do erro.
     * @param error 
     */
    private void setError(boolean error) {
        this.error = error;
    }
    
    /**
     * Retorna o modelo de tabela do respectivo valor de index.
     * @param index Inteiro do respectivo modelo para retornar.
     * @return Retorna o modelo rescpetivo do index.
     */
    public DefaultTableModel getTableModelOf(int index){
        return arrTableModel.get(index);
    }

    /**
     * Adiciona o novo modelo de tabela na ArrayList.
     * @param newModel DefaultTableModel que deve ser adicionado a ArrayList.
     */
    private void setTableModel(DefaultTableModel newModel) {
        arrTableModel.add(newModel);
    }
    
    /**
     * Remove todos os elementos da ArrayList.
     */
    public void clear(){
        arrTableModel.clear();
    }
    
    /**
     * Cria uma nova matriz zerada.
     * @return Uma matriz double já zerada. 
     */
    private double[][] zeroMatrix(){
        /**
        * Matriz
        * 
        * cosθ  -cosα*senθ    senα*senθ    a*cosθ  || Linha Matriz 0
        * senθ  cosα*cosθ     -senα*cosθ   a*senθ  || Linha Matriz 1
        *  0       senα          cosα        d     || Linha Matriz 2
        *  0        0              0         1     || Linha Matriz 3
        * 
        * Criando a Matriz Zerada
        */
            
        double matrix[][] = new double[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
        
        return matrix;
    }
    
    /**
     * Criando a matriz identidade
     * @return 
     * @deprecated 
     */
    private double[][] idMatrix(){          
        double matrix[][] = new double[4][4];
        //Fonte: http://www.guj.com.br/t/matriz-identidade/58780/7
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = i == j ? 1 : 0;
            }
        }
        
        return matrix;
    }
    
    /**
     * Efetivando os cálculos da respectiva linha do Sistema de Denavit-Hartenberg.
     * @param a 
     * @param alfa
     * @param d
     * @param teta
     * @return Um novo DefaultTableModel contendo os respectivos valores calculados.
     */
    private DefaultTableModel toTableModel(double a, double alfa,
            double d, double teta){
        
        /**
         * Novo Modelo de Tabela
         */
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("");
        m.addColumn("");
        m.addColumn("");
        m.addColumn("");

        /**
         * Linha 0
         */
        m.addRow(new Object[]{
            Math.round(Math.cos(Math.toRadians(teta))),
            Math.round((-(Math.cos(Math.toRadians(alfa)))) * Math.sin(Math.toRadians((teta)))),
            Math.round(Math.sin(Math.toRadians(alfa)) * Math.sin(Math.toRadians(teta))),
            a * Math.round(Math.cos(Math.toRadians(teta)))
        });

        /**
         * Linha 1
         */
        m.addRow(new Object[]{
            Math.round(Math.sin(Math.toRadians(teta))),
            Math.round(Math.cos(Math.toRadians(alfa)) * Math.cos(Math.toRadians(teta))),
            Math.round((-(Math.sin(Math.toRadians(alfa)))) * Math.cos(Math.toRadians(teta))),
            a * Math.round(Math.sin(Math.toRadians(teta)))
        });

        /**
         * Linha 2
         */
        m.addRow(new Object[]{
            0,
            Math.round(Math.sin(Math.toRadians(alfa))),
            Math.round(Math.cos(Math.toRadians(alfa))),
            d
        });

        /**
         * Linha 3
         */
        m.addRow(new Object[]{
            0,
            0,
            0,
            1
        });

        return m;
    }
    
    /**
     * Criando um modelo de tabela a partir de uma matriz
     * @param matrix
     * @return Um novo DefaultTableModel contendo os respectivos valores da matriz.
     */
    private DefaultTableModel toTableModel(double matrix[][]){
        /**
         * Novo Modelo de Tabela
         */
        DefaultTableModel m = new DefaultTableModel();
        m.addColumn("");
        m.addColumn("");
        m.addColumn("");
        m.addColumn("");
        
        for (int i = 0; i < 4; i++) {
            m.addRow(new Object[]{
                matrix[i][0], matrix[i][1], matrix[i][2], matrix[i][3]
            });
        }
        
        return m;
    }
    
    /**
     * Multiplicação das matrizes da ArrayList
     */
    private void multiplyArray(){
        /**
         * Multiplicar primeiramente as duas tabelas da Array e o resultado
         * ir multiplicando pelas demais tabelas seguintes
         */

        /**
         * Criando a matriz auxiliar
         */
        double matrixAux[][] = toMatrix(0);
        
        /**
         * Para cada modelo na ArrayList faça a converção para matriz e multiplique
         * com o resultado anterior
         */
        for (int i = 1; i < arrTableModel.size(); i++) {
            //Converta o modelo atual em matriz
            double matrix[][] = toMatrix(i);
            
            //Multiplicando com o resultado anterior
            matrixAux = multiply(matrixAux, matrix);
            
        }
        
        /**
         * A matriz do resultado final é o último elemento da ArrayList
         */
        setTableModel(toTableModel(matrixAux));
        
    }
    
    /**
     * Converte o modelo de tabela em uma matriz.
     * @param index Inteiro do respectivo modelo para retornar.
     * @return Uma matriz double com os respectivos valores do index da ArrysList.
     */
    private double[][] toMatrix(int index){
        /**
         * Criando a nova tabela
         */
        JTable table = new JTable();
        
        /**
         * Criando a nova matriz
         */
        double matrix[][] = zeroMatrix();
        
        /**
         * Passando o modelo da ArrayList para a tabela
         */
        table.setModel(arrTableModel.get(index));
        
        /**
         * Copiando os valores da tabela para a matriz
         */
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                matrix[i][j] = Double.parseDouble(table.getValueAt(i, j).toString());
            }
        }
        
        return matrix;    
    }
    
    /**
     * Multiplica duas matrizes de mesmo tamanho.
     * @param matrixA
     * @param matrixB
     * @return Retorna a matriz resultado da multiplicação.
     */
    private double[][] multiply(double matrixA[][], double matrixB[][]){
        /**
         * Não há preoculpação com o tamanho das matrizes pois nessa classe
         * o tamanho das matrizes sempre são 4x4.
         */
        
        //Criando a matriz resultado
        double matrixR[][] = zeroMatrix();
        
        for (int i = 0; i < matrixA.length; i++) { //Linha
            for (int j = 0; j < matrixB[i].length; j++) { //Coluna
                for (int k = 0; k < matrixA[j].length; k++) { //Coluna
                    matrixR[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        
        return matrixR;
    }

    @Override
    public String toString() {
        return " == OpenSource Code ==";
    }
    
    
}
