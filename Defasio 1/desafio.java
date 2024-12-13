// Classe base ContaBancaria
public abstract class ContaBancaria {
    protected double saldo;

    public ContaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Método para realizar saque
    public abstract void sacar(double valor);
    
    // Getter para saldo
    public double getSaldo() {
        return saldo;
    }
}

// Classe ContaCorrente
public class ContaCorrente extends ContaBancaria {
    public ContaCorrente(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de " + valor + " realizado na Conta Corrente.");
        }
    }
}

// Classe ContaSalario
public class ContaSalario extends ContaBancaria {
    public ContaSalario(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de " + valor + " realizado na Conta Salário.");
        }
    }
}

// Classe ContaPoupanca
public class ContaPoupanca extends ContaBancaria {
    public ContaPoupanca(double saldoInicial) {
        super(saldoInicial);
    }

    @Override
    public void sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de " + valor + " realizado na Conta Poupança.");
        }
    }
}


// Aspecto que verifica saldo insuficiente
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class VerificacaoSaldoAspect {

    // Define um ponto de corte (join point) que é chamado antes da execução do método sacar em qualquer classe que herde de ContaBancaria
    @Pointcut("execution(* ContaBancaria.sacar(double)) && args(valor)")
    public void pontoDeCorte(double valor) {}

    // Antes de realizar o saque, verifica se o saldo é suficiente
    @Before("pontoDeCorte(valor)")
    public void verificarSaldoInsuficiente(double valor) {
        // Obter a instância da conta (poderia ser feito usando o contexto do AspectJ para obter o objeto da conta)
        ContaBancaria conta = (ContaBancaria) org.aspectj.lang.JoinPoint.getThis();
        if (conta.getSaldo() < valor) {
            System.out.println("Erro: Saldo insuficiente para o saque de " + valor);
        } else {
            System.out.println("Saldo suficiente para o saque de " + valor);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        ContaBancaria contaCorrente = new ContaCorrente(500.0);
        ContaBancaria contaSalario = new ContaSalario(300.0);
        ContaBancaria contaPoupanca = new ContaPoupanca(1000.0);
        
        // Testando saques
        contaCorrente.sacar(200.0);  // Sucesso
        contaSalario.sacar(350.0);   // Falha: Saldo insuficiente
        contaPoupanca.sacar(500.0);  // Sucesso
    }
}
