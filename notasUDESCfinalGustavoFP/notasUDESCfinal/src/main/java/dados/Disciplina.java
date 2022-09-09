package dados;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private int id;
    private String nome;
    private int id_semestre;
    private ArrayList<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();


    public Disciplina(int id, String nome, int id_semestre){
        this.id = id;
        this.nome = nome;
        this.id_semestre = id_semestre;
    }

    public Disciplina(){}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getId_semestre() {
        return this.id_semestre;
    }

    public void setId_semestre(int id_semestre) {
        this.id_semestre = id_semestre;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return this.avaliacoes;
    }

    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public String toString() {
        return "nome: " + getNome();
    }

    public void cadastrarAvaliacao(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    public List<Avaliacao> mostraAvaliacoes() {
        return avaliacoes;
    }

    public void insercaoDeNotas(Avaliacao avaliacao, float nota) {
        avaliacao.setNota(nota);
    }

    public String calcularNota() {
        String retorno = "";
        double nota = 0;
        double notas = 0;
        float peso = 0;
        for (int i = 0; i < avaliacoes.size(); i++) {
            if (avaliacoes.get(i).getNota() != -1) {
                nota = avaliacoes.get(i).getNota() * avaliacoes.get(i).getPeso();
                notas += nota;
                peso += avaliacoes.get(i).getPeso();
            }
        }
        float media = (float) notas / peso;
        retorno += "Nota do aluno: " + media;
        if (media >= 7 || media < 1.7)
            retorno += " - Nao precisara do exame";
        else {
            float exame = (float) (-1.5 * media + 12.5);
            retorno += " - Nota necessaria no exame: " + String.format("%.2f", exame);
        }
        return retorno;
    }
}
