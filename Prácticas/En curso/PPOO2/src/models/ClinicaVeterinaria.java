package models;

import java.util.ArrayList;
import java.util.List;

public class ClinicaVeterinaria {

	private List<Animal> lista;
	private static List<String> chips;

	public ClinicaVeterinaria() {
		lista = new ArrayList<Animal>();
		chips = new ArrayList<String>();
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
	
	public void mostrarAnimal(String nombre) {
		for (Animal animal : lista) {
			if (animal.getNombre().equals(nombre))
				System.out.println(animal);
		}
	}

	public void modificarComentarioAnimal(String nombre, String comentario) {
		Animal temp = buscarAnimal(nombre);
		if(temp != null) temp.setComentarios(comentario);
	}
	
	public static String genChip() {
		boolean isValid = false;
		String chip;
		
		do {
			chip = Integer.toString((int)Math.random()*1000000);
			if(!chips.contains(chip)) {
				chips.add(chip);
				isValid = true;
			}
		} while(!isValid);
		
		return chip;
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
