package br.com.mangarosa.collections;

import java.util.ArrayList;
import java.util.List;

// A classe BalancedTree é uma implementação de uma árvore binária balanceada que herda da classe BinaryTree,
// essa classe tem métodos para adicionar, remover e balancear os nós automaticamente durante a inserção e remoção
public class BalancedTree extends BinaryTree {

    // Sobrescreve o método add da superclasse BinaryTree. Esse método adiciona um valor na árvore e garante
    // que a árvore continue balanceada
    @Override
    public void add(Integer value) {
        root = addRecursive(root, value);
        size++; // Incrementa o tamanho da árvore
    }

    // Sobrescreve o método remove da superclasse BinaryTree. Esse método remove um valor da árvore e
    // garante que a árvore continue balanceada
    @Override
    public void remove(Integer value) {
        if (contains(value)) {
            root = removeRecursive(root, value);
            size--; // Decrementa o tamanho da árvore
        }
    }

    // Método recursivo que insere um valor na árvore binária de busca. Depois da inserção
    // o método balanceia a árvore se necessário
    private BinaryTreeNode<Integer> addRecursive(BinaryTreeNode<Integer> current, Integer value) {
        if (current == null) {
            return new BinaryTreeNode<>(value);
        }

        // Se o valor for menor, insere na subárvore esquerda
        if (value.compareTo(current.getValue()) < 0) {
            current.setLeftChild(addRecursive(current.getLeftChild(), value));
        }
        // Se o valor for maior, insere na subárvore direita
        else if (value.compareTo(current.getValue()) > 0) {
            current.setRightChild(addRecursive(current.getRightChild(), value));
        }

        // Balanceia a árvore depois da inserção
        return balanceTree(current);
    }

    // Método recursivo que remove um valor da árvore binária de busca. Depois da remoção
    // o método balanceia a árvore se necessário
    private BinaryTreeNode<Integer> removeRecursive(BinaryTreeNode<Integer> current, Integer value) {
        if (current == null) {
            return null;
        }

        // Se o valor do nó atual é o que deve ser removido
        if (value.equals(current.getValue())) {
            // Nó sem filhos (nó folha)
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                return null;
            }
            // Nó com um filho
            if (current.getRightChild() == null) {
                return current.getLeftChild();
            }
            if (current.getLeftChild() == null) {
                return current.getRightChild();
            }
            // Nó com dois filhos, encontra o menor valor na subárvore direita
            Integer smallestValue = findSmallestValue(current.getRightChild());
            current.setValue(smallestValue);
            // Remove o menor valor da subárvore direita
            current.setRightChild(removeRecursive(current.getRightChild(), smallestValue));
        }
        // Se o valor a ser removido for menor que o valor atual, procura na subárvore esquerda
        else if (value.compareTo(current.getValue()) < 0) {
            current.setLeftChild(removeRecursive(current.getLeftChild(), value));
        }
        // Se o valor a ser removido for maior que o valor atual, procura na subárvore direita
        else {
            current.setRightChild(removeRecursive(current.getRightChild(), value));
        }

        // Balanceia a árvore após a remoção
        return balanceTree(current);
    }

    // Método para encontrar o menor valor em uma subárvore, usado durante a remoção
    // de um nó que tem dois filhos
    private Integer findSmallestValue(BinaryTreeNode<Integer> root) {
        // Continua indo para a esquerda até encontrar o menor valor
        return root.getLeftChild() == null ? root.getValue() : findSmallestValue(root.getLeftChild());
    }

    // Balanceia a árvore usando rotações se for necessário. Verifica o fator de balanceamento
    // e realiza rotações simples ou duplas para manter a árvore balanceada
    private BinaryTreeNode<Integer> balanceTree(BinaryTreeNode<Integer> node) {
        if (node == null) {
            return null;
        }

        // Calcula o fator de balanceamento (diferença de altura entre subárvores esquerda e direita)
        int balanceFactor = height(node.getLeftChild()) - height(node.getRightChild());

        // Se o fator de balanceamento for maior que 1, a árvore está desbalanceada para a esquerda
        if (balanceFactor > 1) {
            // Se o filho à esquerda está desbalanceado para a esquerda, realiza rotação simples à direita
            if (height(node.getLeftChild().getLeftChild()) >= height(node.getLeftChild().getRightChild())) {
                node = rotateRight(node);
            }
            // Caso contrário, realiza rotação dupla (esquerda-direita)
            else {
                node.setLeftChild(rotateLeft(node.getLeftChild()));
                node = rotateRight(node);
            }
        }

        // Se o fator de balanceamento for menor que -1, a árvore está desbalanceada para a direita
        if (balanceFactor < -1) {
            // Se o filho à direita está desbalanceado para a direita, realiza rotação simples à esquerda
            if (height(node.getRightChild().getRightChild()) >= height(node.getRightChild().getLeftChild())) {
                node = rotateLeft(node);
            }
            // Caso contrário, realiza rotação dupla (direita-esquerda)
            else {
                node.setRightChild(rotateRight(node.getRightChild()));
                node = rotateLeft(node);
            }
        }

        return node;
    }

    // Calcula a altura de um nó da árvore binária
    private int height(BinaryTreeNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        // A altura é o maior valor entre as alturas das subárvores esquerda e direita + 1 (para o nó atual)
        return Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1;
    }

    // Realiza uma rotação simples à direita
    private BinaryTreeNode<Integer> rotateRight(BinaryTreeNode<Integer> y) {
        // O novo nó raiz será o filho esquerdo de y
        BinaryTreeNode<Integer> x = y.getLeftChild();
        // O filho direito de x se torna o filho esquerdo de y
        y.setLeftChild(x.getRightChild());
        // x se torna o novo nó raiz e y se torna seu filho direito
        x.setRightChild(y);
        return x;
    }

    // Realiza um rotação simples à esquerda
    private BinaryTreeNode<Integer> rotateLeft(BinaryTreeNode<Integer> y) {
        // O novo nó raiz será o filho direito de y
        BinaryTreeNode<Integer> x = y.getRightChild();
        // O filho esquerdo de x se torna o filho direito de y
        y.setRightChild(x.getLeftChild());
        // x se torna o novo nó raiz e y se torna seu filho esquerdo
        x.setLeftChild(y);
        return x;
    }

    // Converte a árvore em uma lista in-order (ordem crescente)
    public List<Integer> toList() {
        // Se a árvore estiver vazia, retorna null
        if (root() == null) {
            return null;
        }
        // Cria uma lista para armazenar os valores
        List<Integer> list = new ArrayList<>();
        // Adiciona os valores da árvore à lista de forma recursiva
        toListRecursive(root(), list);
        return list;
    }

    // Método auxiliar para converter a árvore em uma lista in-order de forma recursiva
    private void toListRecursive(BinaryTreeNode<Integer> node, List<Integer> list) {
        if (node != null) {
            // Adiciona primeiro os valores da subárvore esquerda
            toListRecursive(node.getLeftChild(), list);
            // Depois adiciona o valor do nó atual
            list.add(node.getValue());
            // Finalmente, adiciona os valores da subárvore direita
            toListRecursive(node.getRightChild(), list);
        }
    }
}
