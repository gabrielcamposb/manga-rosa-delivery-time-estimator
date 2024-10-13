package br.com.mangarosa.collections;

import java.util.List;

// Classe que representa uma árvore binária
public class BinaryTree implements Tree<Integer> {

    protected BinaryTreeNode<Integer> root;
    protected int size;

    public BinaryTree() {
        this.root = null;
        this.size = 0;
    }

    // Adiciona um novo valor à árvore binária. Se a árvore estiver vazia, define o novo valor como raiz
    @Override
    public void add(Integer value) {
        root = addRecursive(root, value);
        size++;
    }

    // Encontra recursivamente a posição correta na árvore para inserir o novo valor
    private BinaryTreeNode<Integer> addRecursive(BinaryTreeNode<Integer> node, Integer value) {
        if (node == null) {
            return new BinaryTreeNode<>(value);
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeftChild(addRecursive(node.getLeftChild(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRightChild(addRecursive(node.getRightChild(), value));
        }

        return node;
    }

    // Remove um valor da árvore, se existir
    @Override
    public void remove(Integer value) {
        if (contains(value)) {
            root = removeRecursive(root, value);
            size--;
        }
    }

    // Encontra recursivamente o nó a ser removido e ajusta a árvore adequadamente
    private BinaryTreeNode<Integer> removeRecursive(BinaryTreeNode<Integer> node, Integer value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.getValue()) < 0) {
            node.setLeftChild(removeRecursive(node.getLeftChild(), value));
        } else if (value.compareTo(node.getValue()) > 0) {
            node.setRightChild(removeRecursive(node.getRightChild(), value));
        } else {
            // Caso o nó não tenha filhos
            if (node.getLeftChild() == null && node.getRightChild() == null) {
                return null;
            }
            // Caso o nó tenha apenas o filho direito
            if (node.getLeftChild() == null) {
                return node.getRightChild();
            }
            // Caso o nó tenha apenas o filho esquerdo
            if (node.getRightChild() == null) {
                return node.getLeftChild();
            }

            // Caso o nó tenha dois filhos, encontre o menor valor no lado direito
            BinaryTreeNode<Integer> smallestValue = findMin(node.getRightChild());
            node.setValue(smallestValue.getValue());
            node.setRightChild(removeRecursive(node.getRightChild(), smallestValue.getValue()));
        }

        return node;
    }

    // Encontra o nó com o menor valor em uma subárvore
    private BinaryTreeNode<Integer> findMin(BinaryTreeNode<Integer> node) {
        return node.getLeftChild() == null ? node : findMin(node.getLeftChild());
    }

    // Verifica se a árvore contém um valor específico
    @Override
    public boolean contains(Integer value) {
        return containsRecursive(value);
    }

    // Verifica se um nó com o valor especificado existe na árvore
    private boolean containsRecursive(Integer value) {
        return findNode(root, value) != null;
    }

    // Verifica se a árvore está vazia
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    // Verifica se o nó com o valor fornecido é uma folha (não possui filhos)
    @Override
    public boolean isLeaf(Integer value) {
        BinaryTreeNode<Integer> node = findNode(root, value);
        return node != null && node.getLeftChild() == null && node.getRightChild() == null;
    }

    // Encontra o nó que contém o valor fornecido
    private BinaryTreeNode<Integer> findNode(BinaryTreeNode<Integer> node, Integer value) {
        if (node == null) return null;
        if (value.equals(node.getValue())) return node;
        return value.compareTo(node.getValue()) < 0 ? findNode(node.getLeftChild(), value) : findNode(node.getRightChild(), value);
    }

    // Obtém o nó raiz da árvore
    @Override
    public BinaryTreeNode<Integer> root() {
        return root;
    }

    // Obtém o tamanho da árvore
    @Override
    public int size() {
        return size;
    }

    // Converte a árvore para um array, percorrendo-a em ordem
    @Override
    public List<Integer> toList() {
        if (root == null) {
            return null;
        }
        InOrderTraversal traversal = new InOrderTraversal();
        List<Integer> list = traversal.traverse(this);
        return list;
    }

    // Limpa a árvore, removendo todos os elementos
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
}
