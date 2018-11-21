/**
 * Fonte: https://stackoverflow.com/questions/20346689/modify-text-alignment-in-a-jtable-cell
 * Edited
 */
package util;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRenderer extends DefaultTableCellRenderer {

    public TableCellRenderer() {        
    }
    
    public void setCellCenter(JTable tabTable){
        //Varrendo todas as colunas da tabela
        for (int i = 0; i < tabTable.getColumnCount(); i++) {
            tabTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        } 
    }

    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

        @Override
        public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
            Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
            int align = DefaultTableCellRenderer.CENTER;

            ((DefaultTableCellRenderer) tableCellRendererComponent).setHorizontalAlignment(align);
            return tableCellRendererComponent;
        }
    };
    
    
}
