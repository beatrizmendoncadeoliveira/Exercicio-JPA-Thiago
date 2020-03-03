
PKT Entity

 @SequenceGenerator(name="cod", sequenceName="SQ_T_VEICULO", allocationSize=1)
public class Veiculo(){
    @Entity
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cod")
    private int codigo;
	private String descricao;
	private String cor;
	private int ano;
}
---------------------------------------------------------------------------------
public class Motorista(){
    @Entity
    @Id
   private int numero;
   private String nome;
   private Date data;
   private Genero genero;
   private byte foto;
}
----------------------------------------------------------------------------------
ENUM
public ENUM Genero(){
    FEMININO, MASCULINO;
}
INTERFACE
public interface VeiculoDAO(){
    void adiciona(Veiculo v)
    Veiculo busca(int codigo)throws VeiculoNaoExisteException;;
    void atualiza(Veiculo v)throws VeiculoNaoExisteException;
    void remover(int codigo)throws VeiculoNaoExisteException;
    void commit()throws CommitExceptions;
}
----------------------------------------------------------------------------------
public interface MotoristaDAO(){
    void adiciona(Motorista m);
    Motorista busca(int numero)throws MotoristaNaoExisteException;
    void atualizar(Motorista m)throws MotoristaNaoExisteException;
    void remover(int numero)throws MotoristaNaoExisteException;
    void commit()throws CommitExceptions;

}
IMPLEMENTACAO DA INTERFACE
public class VeiculoDAOImpl() implements VeiculoDAO{
    private EntityManager em;
    public VeiculoDAOImpl(EntityManager em){
        this.em=em;
    }
    public void adiciona(Veiculo v){
        em.persist(v);
    }
    public Veiculo busca(int codigo)throws VeiculoNaoExisteException{

        Veiculo v=em.find(Veiculo.class,codigo);
        if(v==null){
            throw new VeiculoNaoExisteException();
        }
        return v;
    }
    public void atualiza(Veiculo v)throws VeiculoNaoExisteException{
        em.merge(v);
    }
    public void remover(int codigo)throws VeiculoNaoExisteException{
        Veiculo v=em.find(Veiculo.class,codigo);
        em.remove(v);   
    }
    public void commit()throws CommitExceptions{
        try{
            em.getTransition().begin();
            em.getTransition().coomit();
        }catch(Exception e){
            e.printStackTrace();
            em.getTransition().rollback();
            throw new CommitExceptions(e.getMessage());
        }
    }
}
-------------------------------------------------------------------------------
public class MotoristaDAOImpl()implements MotoristaDAO{
    private EntityManager em;
    public MotoristaDAOImpl(EntityManager em){
        this.em=em;
    }
    public void adiciona(Motorista m){
        em.persist();
    }
    public Motorista pesquisa(int numero)throws MotoristaNaoExisteException{
       Motorista m= em.find(Motorista.class,numero);
        if(m==null){
            throw new MotoristaNaoExisteException();
        }
       return m;
    }
    public void atualiza(Motorista m)throws MotoristaNaoExisteException{

        em.merge(m);
    }
    public void remove(int numero)throws MotoristaNaoExisteException{
        Motorista m=pesquisa(numero);
        em.remove(m);
    }
    public void commit()throws CommitExceptions{
        try{
            em.getTransition().begin();
            em.getTransition().commit();

        }catch(Exception e){
            e.printStackTrace();
            em.getTransition().rollback;
            throw new CommitExceptions(e.getMessage());

        }


    }

}
------------------------------------------------------------------------------------------
PKT Exception
public class VeiculoNaoExisteException extends Exception{
   public  VeiculoNaoExisteException(){
        super();
    }
}
------------------------------------------------------------------------------------------
public class MotoristaNaoExisteException extends Exception{
    public MotoristaNaoExisteException(){
        super();
    }
}
-----------------------------------------------------------------------------------------

MAIN

public class TesteDAOVeiculo(){
    main(){
        EntityManagerFactory f=Persistence.createEntityManagerFactory("oracle");
        EntityManager em=f.createEntityManager();
        VeiculoDAO dao=new VeiculoDAOImpl(em);
        Veiculo v=new V("null","null","null","null");


        try{
            dao.adiciona(v);
            dao.commit;
            sysout("cadastrado");
            }catch(CommitExceptions e){
               sysout(e.getMessage());
        }
        try{
            Veiculo busca=dao.pesquisa(v.getCodigo());
            sysout(busca.getCor());

        }catch(VeiculoNaoExisteException e){
           e.printStackTrace();
          
        }

        try{
            v.setCor("azul");
            dao.atualiza(v);
            dao.commit();
            sysout("atualizado");

        }catch(VeiculoNaoExisteException | CommitException e){
            E.printStackTrace();
        }

        try{
            dao.remover(v.getCodigo());
            dao.commit();
            sysout("Removido");
        }catch(VeiculoNaoExisteException e){
            e.printStackTrace();
        }catch(CommitExceptions e){
            e.printStackTrace();
        }

        em.close();
        f.close();

    }

}
