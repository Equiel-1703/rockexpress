package TestesDasFuncionalidades;

import com.example.demo.model.*;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.service.CarrinhoService;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TesteCarrinho {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private CarrinhoService carrinhoService;

    private Cliente cliente;
    private Produto produto;
    private Carrinho carrinho;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEnderecos(new ArrayList<>());
        cliente.setPedidos(new ArrayList<>());
        cliente.setAvaliacoes(new ArrayList<>());

        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(new BigDecimal("100.00"));
        produto.setEstoque(10);
        produto.setItensPedido(new ArrayList<>());
        produto.setAvaliacoes(new ArrayList<>());

        carrinho = new Carrinho(cliente);
        carrinho.setId(1L);
        carrinho.setItens(new ArrayList<>()); // 👈 GARANTIR QUE A LISTA ESTÁ INICIALIZADA
    }

    @Test
    void testAdicionarItem_NovoCarrinho() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(carrinhoRepository.findByClienteId(1L)).thenReturn(Optional.empty());
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho result = carrinhoService.adicionarItem(1L, 1L, 2);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(carrinhoRepository, times(1)).save(any(Carrinho.class));
    }

    @Test
    void testAdicionarItem_ProdutoJaNoCarrinho() {
        // Configurar carrinho com item existente
        ItemCarrinho itemExistente = new ItemCarrinho(carrinho, produto, 1, new BigDecimal("100.00"));
        carrinho.getItens().add(itemExistente);

        when(carrinhoRepository.findByClienteId(1L)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho result = carrinhoService.adicionarItem(1L, 1L, 3);

        assertEquals(1, result.getItens().size());
        assertEquals(4, result.getItens().get(0).getQuantidade());
        assertEquals(new BigDecimal("400.00"), result.getValorTotal());
    }

    @Test
    void testAdicionarItem_EstoqueInsuficiente() {
        produto.setEstoque(2);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(carrinhoRepository.findByClienteId(1L)).thenReturn(Optional.of(carrinho));

        // 👈 AGORA VERIFICA A MENSAGEM ESPECÍFICA
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.adicionarItem(1L, 1L, 5);
        });

        assertTrue(exception.getMessage().contains("Estoque insuficiente"));
    }

    @Test
    void testAdicionarItem_ProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        when(carrinhoRepository.findByClienteId(1L)).thenReturn(Optional.of(carrinho));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.adicionarItem(1L, 1L, 1);
        });

        assertTrue(exception.getMessage().contains("Produto não encontrado"));
    }

    @Test
    void testAdicionarItem_ClienteNaoEncontrado() {
        when(carrinhoRepository.findByClienteId(1L)).thenReturn(Optional.empty());
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.adicionarItem(1L, 1L, 1);
        });

        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
    }

    @Test
    void testAdicionarItem_ErroBancoDados() {
        when(carrinhoRepository.findByClienteId(1L)).thenThrow(new DataAccessException("Erro de conexão") {});

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.adicionarItem(1L, 1L, 1);
        });

        assertTrue(exception.getMessage().contains("Erro temporário"));
    }

    @Test
    void testRemoverItem() {
        ItemCarrinho item = new ItemCarrinho(carrinho, produto, 2, new BigDecimal("100.00"));
        carrinho.getItens().add(item);
        carrinho.recalcularValorTotal();

        when(carrinhoRepository.findByClienteIdWithItens(1L)).thenReturn(Optional.of(carrinho));
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho result = carrinhoService.removerItem(1L, item.getId());

        assertTrue(result.getItens().isEmpty());
        assertEquals(BigDecimal.ZERO, result.getValorTotal());
    }

    @Test
    void testRemoverItem_ItemNaoEncontrado() {
        when(carrinhoRepository.findByClienteIdWithItens(1L)).thenReturn(Optional.of(carrinho));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.removerItem(1L, 999L);
        });

        assertTrue(exception.getMessage().contains("Item não encontrado"));
    }

    @Test
    void testAtualizarQuantidadeItem() {
        ItemCarrinho item = new ItemCarrinho(carrinho, produto, 2, new BigDecimal("100.00"));
        carrinho.getItens().add(item);
        carrinho.recalcularValorTotal();

        when(carrinhoRepository.findByClienteIdWithItens(1L)).thenReturn(Optional.of(carrinho));
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho result = carrinhoService.atualizarQuantidadeItem(1L, item.getId(), 5);

        assertEquals(5, result.getItens().get(0).getQuantidade());
        assertEquals(new BigDecimal("500.00"), result.getValorTotal());
    }

    @Test
    void testAtualizarQuantidadeItem_EstoqueInsuficiente() {
        produto.setEstoque(3); // Estoque menor que a quantidade solicitada
        ItemCarrinho item = new ItemCarrinho(carrinho, produto, 1, new BigDecimal("100.00"));
        carrinho.getItens().add(item);

        when(carrinhoRepository.findByClienteIdWithItens(1L)).thenReturn(Optional.of(carrinho));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.atualizarQuantidadeItem(1L, item.getId(), 5);
        });

        assertTrue(exception.getMessage().contains("Estoque insuficiente"));
    }

    // Teste para verificar inicialização de lista nula
    @Test
    void testListaItensNula() {
        carrinho.setItens(null); // Forçar lista nula

        when(carrinhoRepository.findByClienteId(1L)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho result = carrinhoService.adicionarItem(1L, 1L, 1);

        assertNotNull(result.getItens());
        assertFalse(result.getItens().isEmpty());
    }
}