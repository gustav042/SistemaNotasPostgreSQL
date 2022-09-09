package dados;

import java.util.ArrayList;
import java.util.List;

public class Semestre {
    private int id;
    private String numSemestre;
    private int id_aluno;

    public Semestre(){
    }

    public Semestre(int id, String numSemestre, int id_aluno){
        this.id = id;
        this.numSemestre = numSemestre;
        this.id_aluno = id_aluno;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_aluno() {
        return this.id_aluno;
    }

    public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return this.disciplinas;
    }

    public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    private ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();

    public String getNumSemestre() {
        return this.numSemestre;
    }

    public void setNumSemestre(String numSemestre) {
        this.numSemestre = numSemestre;
    }

    public String toString() {
        return "Semestre: " + getNumSemestre();
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }

    public void removerDisciplina(Disciplina disciplina) {
        disciplinas.remove(disciplina);
    }

    public List<Disciplina> mostraDisciplinas() {
        return disciplinas;
    }

    public void editarDisciplina(Disciplina disciplina) {
        int index = -1;
        for (int i = 0; i < disciplinas.size(); i++) {
            if (disciplina.equals(disciplinas.get(i)))
                index = i;
        }
        disciplinas.set(index, disciplina);
    }

    public void cadastrarAvaliacao(Disciplina disciplina, Avaliacao avaliacao) {
        disciplina.cadastrarAvaliacao(avaliacao);
    }

    public List<Avaliacao> mostraAvaliacoes(Disciplina disciplina) {
        return disciplina.mostraAvaliacoes();
    }

    public List<String> calcularNota() {
        List<String> notas = new ArrayList<String>();
        for (int i = 0; i < disciplinas.size(); i++) {
            notas.add(disciplinas.get(i) + " - " + disciplinas.get(i).calcularNota());
        }
        return notas;
    }

    public void insercaoDeNotas(Disciplina disciplina, Avaliacao avaliacao, float nota) {
        disciplina.insercaoDeNotas(avaliacao, nota);
    }
}