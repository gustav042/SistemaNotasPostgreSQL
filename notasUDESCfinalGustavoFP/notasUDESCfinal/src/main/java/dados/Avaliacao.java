package dados;

import java.util.Objects;

public class Avaliacao{
    private int id;
    private double nota=0;
    private double peso;
    private String nome;
    private String data;
    private int id_disciplina;

    public int getId_disciplina() {
        return this.id_disciplina;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Avaliacao(){}

    public Avaliacao(int id, double nota, double peso, String nome, String data, int id_disciplina){
        this.id = id;
        this.nota = nota;
        this.peso = peso;
        this.nome = nome;
        this.data = data;
        this.id_disciplina  = id_disciplina;
    }


    public Avaliacao(int id2, String nome2, int id_semestre) {
    }

    @Override
    public String toString() {
        return "\n\nProva: " + getNome() + "\n" +
            "Nota: " + getNota() + "\n" +
            "Peso: " + getPeso() + "\n" +
            "Data: " + getData();
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Avaliacao)) {
            return false;
        }
        Avaliacao avaliacao = (Avaliacao) o;
        return nota == avaliacao.nota && peso == avaliacao.peso && Objects.equals(nome, avaliacao.nome) && Objects.equals(data, avaliacao.data);
    }

    public double getNota() {
        return this.nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }


}