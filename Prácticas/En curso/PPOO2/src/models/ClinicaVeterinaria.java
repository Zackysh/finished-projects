package models;

import java.util.ArrayList;
import java.util.List;

public class ClinicaVeterinaria {

	private List<Animal> lista;

	public ClinicaVeterinaria() {
		lista = new ArrayList<Animal>();
	}

	public void insertaAnimal(Animal animal) {
		this.lista.add(animal);
	}

	public Animal buscarAnimal(String nombre) {

		for (Animal animal : lista) {
			if (animal.getNombre().equals(nombre))
				return animal;
		}

		return null;
	}

	public void modificarComentarioAnimal(String nombre, String comentario) {
		Animal temp = buscarAnimal(nombre);
		if(temp != null) temp.setComentarios(comentario);
	}

	@Override
	public String toString() {
		String lista = "Lista de animales registrados:\n";
		for (Animal animal : this.lista) {
			lista += animal + "\n\n";
		}
		return lista;
	}
	
	
}
