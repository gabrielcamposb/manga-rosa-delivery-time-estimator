package br.com.mangarosa.collections;

import java.util.ArrayList;
import java.util.List;

// Classe que implementa a travessia em pré-ordem de uma árvore binária.
// A travessia em pré-ordem visita os nós na sequência: raiz, esquerda, direita
public class PreOrderTraversal implements TreeTraversal<Integer> {

    // Percorre a árvore em pré-ordem e retorna uma lista dos valores dos nós
    @Override
    public List<Integer> traverse(Tree<Integer> tree) {
        List<Integer> result = new ArrayList<>();
        preOrder(tree.root(), result);
        return result;
    }

    // Método auxiliar que realiza a travessia em pré-ordem de forma recursiva
    private void preOrder(BinaryTreeNode<Integer> node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.getValue());
        preOrder(node.getLeftChild(), result);
        preOrder(node.getRightChild(), result);
    }
}
