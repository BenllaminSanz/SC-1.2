package Tables;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class TableArbolCursos extends DefaultTreeCellRenderer {

    private final Icon iconTipoCurso;
    private final Icon iconCurso;

    public TableArbolCursos(Icon iconTipoCurso, Icon iconCurso) {
        this.iconTipoCurso = iconTipoCurso;
        this.iconCurso = iconCurso;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                                                  boolean expanded, boolean leaf, int row, boolean hasFocus) {

        Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

            // Verificar el tipo de nodo y asignar el icono correspondiente
            if (node.getParent() != null && node.getParent().getParent() != null) {
                setIcon(iconCurso);
            } else {
                setIcon(iconTipoCurso);
            }
        }

        return component;
    }
}

