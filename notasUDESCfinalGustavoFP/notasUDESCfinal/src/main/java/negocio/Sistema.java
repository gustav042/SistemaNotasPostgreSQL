package negocio;

import dados.*;
import exceptions.DeleteException;
import exceptions.InsertException;
import exceptions.JaCadastradoException;
import exceptions.SelectException;
import exceptions.UpdateException;
import persistencia.*;
import java.io.FileNotFoundException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.util.List;
import java.sql.SQLException;
import persistencia.AvaliacaoDAO;

public class Sistema {
    private static AvaliacaoDAO av;
    private static DisciplinaDAO dis;
    private static SemestreDAO sem;
    private static AlunoDAO alu;
    private static int idAluno = -1;
    private static int idSemestre = -1;
    private static int idDisciplina = -1;
    private static int idAvaliacao = -1;

    public int getIdAluno() {
        return idAluno;
    }

    public int getIdSemestre() {
        return idSemestre;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public void setIdSemestre(int idSemestre) {
        this.idSemestre = idSemestre;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Sistema() throws ClassNotFoundException, SQLException, SelectException {
        av = AvaliacaoDAO.getInstance();
        dis = DisciplinaDAO.getInstance();
        sem = SemestreDAO.getInstance();
        alu = AlunoDAO.getInstance();
    }

    public Aluno selecionaAluno() throws SelectException, InsertException {
        return alu.select(this.idAluno);
    }
    public void cadastrarAluno(Aluno aluno) throws InsertException, SelectException, JaCadastradoException {
        alu.jaCadastrado(aluno.getCpf());
        alu.insert(aluno);
    }

    public void deletaAluno() throws DeleteException {
        alu.delete(this.idAluno);
    }

    public void editaAluno() throws UpdateException {
        alu.update(this.idAluno);
    }

    public Aluno loginAluno(String cpf, String senha) throws InsertException, SelectException {
        Aluno a = alu.login(cpf, senha);
        return a;
    }

    public void cadastrarSemestre(Semestre semestre) throws InsertException, SelectException {
        sem.insert(semestre);

    }
    
    public Semestre selecionaSemestre() throws SelectException, InsertException {
        return sem.select(this.idSemestre);
    }

    public void removerSemestre() throws DeleteException {
        sem.delete(this.idSemestre);
        System.out.println(this.idSemestre);
        setIdSemestre(-1);
    }

    public List<Semestre> mostraSemestres() throws SelectException {
        return sem.selectAluno(this.idAluno);
    }

    public void editarSemestre(String periodo) throws UpdateException {
        sem.update(this.idSemestre, periodo);
    }

    public void cadastrarDisciplina(Disciplina disciplina) throws InsertException, SelectException {
        dis.insert(disciplina);
    }
    
    public Disciplina selecionaDisciplina() throws SelectException, InsertException {
        return dis.select(this.idDisciplina);
    }

    public List<Disciplina> mostraDisciplinas() throws SelectException {
        return dis.selectSemestre(this.idSemestre);
    }

    public void removerDisciplina() throws DeleteException {
        dis.delete(this.idDisciplina);
        setIdDisciplina(-1);

    }

    public void editarDisciplina(String nome) throws UpdateException {
        dis.update(this.idDisciplina, nome);
    }

    public void cadastrarAvaliacao(Avaliacao avaliacao) throws InsertException, SelectException {
        av.insert(avaliacao);
    }
    
    public Avaliacao selecionaAvaliacao() throws SelectException, InsertException {
        return av.select(this.idAvaliacao);
    }

    public List<Avaliacao> mostraAvaliacoes() throws SelectException {
        return av.selectDisciplina(this.idDisciplina);
    }

    public void removerAvaliacao() throws DeleteException {
        av.delete(this.idAvaliacao);
        setIdAvaliacao(-1);

    }

    public void editarAvaliacao(double peso, String nome, String dia ) throws UpdateException {
        av.update(this.idAvaliacao, peso, nome, dia);
    }

    public void insercaoDeNotas(double nota) throws UpdateException {
        av.updatenota(this.idAvaliacao, nota);
    }

    public String calcularNota() throws SelectException {
        String retorno = "";
        retorno += "Nota final: " + String.format("%.2f",av.calcularNota(this.idDisciplina));
        
        if (av.calcularNota(this.idDisciplina) < 1.7)
            retorno += "\nReprovado direto";
        else if (av.calcularNota(this.idDisciplina) >= 7)
            retorno += "\nPassado direto";
        else {
            retorno += "\nEm exame";
            retorno += "\nNota necessaria no exame: " + String.format("%.2f",notaexame(this.idDisciplina));
        }
        return retorno;
    }

    public double notaexame(int disciplina) throws SelectException {
        return -1.5 * av.calcularNota(disciplina) + 12.5;
    }

    public void geraPDF() throws SelectException, InsertException {
        try {
            // Creating a PdfDocument object
            Aluno aluno = selecionaAluno();
            String dest =  aluno.getNome().charAt(0) + aluno.getNome().charAt(1) + aluno.getNome().charAt(2)  + ".pdf";
            PdfWriter writer;
            writer = new PdfWriter(dest);
            // Creating a PdfDocument object
            PdfDocument pdf = new PdfDocument(writer);
            // Creating a Document object
            Document doc = new Document(pdf);
            // Creating a table
            doc.add(new Paragraph("-RELATORIO DE NOTAS"));
            doc.add(new Paragraph("Aluno: " + aluno.getNome()));
            doc.add(new Paragraph("Curso: " + aluno.getCurso()));
            doc.add(new Paragraph("\n"));

            for (int i = 0; i < mostraSemestres().size(); i++) {
                // Adding cells to the table
                Semestre s = mostraSemestres().get(i);
                setIdSemestre(s.getId());
                doc.add(new Paragraph(s.toString()));

                for (int j = 0; j < mostraDisciplinas().size(); j++) {
                    Disciplina d = mostraDisciplinas().get(j);
                    setIdDisciplina(d.getId());
                    doc.add(
                            new Paragraph(d.toString()));
                    doc.add(
                            new Paragraph("" + calcularNota()));
                }

                doc.add(new Paragraph("\n"));

            }

            // Closing the document
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/*
 * public List<Aluno> getAlunos() {
 * return alunos;
 * }
 * 
 * public void setAlunos(List<Aluno> alunos) {
 * this.alunos = alunos;
 * }
 */