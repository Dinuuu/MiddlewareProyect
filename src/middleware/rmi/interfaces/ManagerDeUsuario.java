package middleware.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import middleware.modelo.Perfil;
import middleware.modelo.Usuario;

public interface ManagerDeUsuario extends Remote {

	public int peticionAmistad(Usuario usu) throws RemoteException;

	public List<Usuario> listaAmigos() throws RemoteException;

	public Perfil getPerfil() throws RemoteException;
}
