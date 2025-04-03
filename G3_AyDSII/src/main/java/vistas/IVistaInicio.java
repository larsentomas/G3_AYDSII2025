package vistas;

import java.awt.event.ActionListener;

public interface IVistaInicio {
    void setActionListener(ActionListener actionListener);
    public void setVisibleVentana(boolean estado);
    public void limpiarcampos();

}
