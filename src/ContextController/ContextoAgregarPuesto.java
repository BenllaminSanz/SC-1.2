package ContextController;

import Model.Certificado;
import Subviews.IFrmEditarCertificado;
import Subviews.IFrmAgregarPuestoCertificado;

public class ContextoAgregarPuesto {

    public IFrmAgregarPuestoCertificado ventanaPuestoCertificado;
    public IFrmEditarCertificado ventanaCertificado;
    public Certificado modelo;
    public String texto = null;

    public ContextoAgregarPuesto(Certificado mod, IFrmEditarCertificado frm, String texto) {
        this.ventanaPuestoCertificado = new IFrmAgregarPuestoCertificado();
        this.modelo = mod;
        this.ventanaCertificado = frm;
        this.texto = texto;
    }
}
