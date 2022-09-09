package dados;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aluno {
    private int id;
    private String nome;
    private String cpf;
    private String senha;
    private String curso;
    private ArrayList<Semestre> semestres = new ArrayList<Semestre>();

    public Aluno() {
    }

    public Aluno(int id, String nome, String cpf, String senha, String curso){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.curso = curso;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSemestres(ArrayList<Semestre> semestres) {
        this.semestres = semestres;
    }

    public ArrayList<Semestre> getSemestres() {
        return this.semestres;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Aluno)) {
            return false;
        }
        Aluno aluno = (Aluno) o;
        return Objects.equals(cpf, aluno.cpf) && Objects.equals(senha, aluno.senha);
    }

    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n" +
                "Cpf: " + getCpf() + "\n" +
                "Senha: " + getSenha() + "\n" +
                "Curso: " + getCurso() + "\n";
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCurso() {
        return this.curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void insercaoDeNotas(Semestre semestre, Disciplina disciplina, Avaliacao avaliacao, float nota) {
        semestre.insercaoDeNotas(disciplina, avaliacao, nota);
    }

    public List<String> calcularNota(Semestre semestre) {
        return semestre.calcularNota();
    }

    public void cadastrarSemestre(Semestre semestre) {
        semestres.add(semestre);
    }

    public void removerSemestre(Semestre semestre) {
        semestres.remove(semestre);
    }

    public List<Semestre> mostraSemestres() {
        return semestres;
    }

    public void editarSemestre(Semestre semestre) {
        int index = -1;
        for (int i = 0; i < semestres.size(); i++) {
            if (semestre.equals(semestres.get(i)))
                index = i;
        }
        semestres.set(index, semestre);
    }

    public void cadastrarDisciplina(Semestre semestre, Disciplina disciplina) {
        semestre.cadastrarDisciplina(disciplina);
    }

    public void removerDisciplina(Semestre semestre, Disciplina disciplina) {
        semestre.removerDisciplina(disciplina);
    }

    public List<Disciplina> mostraDisciplinas(Semestre semestre) {
        return semestre.mostraDisciplinas();
    }

    public void editarDisciplina(Semestre semestre, Disciplina disciplina) {
        semestre.editarDisciplina(disciplina);
    }

    public void cadastrarAvaliacao(Semestre semestre, Disciplina disciplina, Avaliacao avaliacao) {
        semestre.cadastrarAvaliacao(disciplina, avaliacao);
    }

    public List<Avaliacao> mostraAvaliacoes(Semestre semestre, Disciplina disciplina) {
        return semestre.mostraAvaliacoes(disciplina);
    }
}